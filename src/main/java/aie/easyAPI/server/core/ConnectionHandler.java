package aie.easyAPI.server.core;

import aie.easyAPI.core.RouteHandler;
import aie.easyAPI.excepation.ConnectionException;
import aie.easyAPI.excepation.RouteException;
import aie.easyAPI.interfaces.IContextWrapper;
import aie.easyAPI.models.BadRequest;
import aie.easyAPI.server.ClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class ConnectionHandler implements ClientService, Closeable {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionHandler.class);
    private SocketChannel channel;
    private final IContextWrapper context;
    private boolean readStarted = false;
    private int length = 0;
    private ByteBuffer currentRead;

    public ConnectionHandler(IContextWrapper context, SocketChannel channel) {
        this.channel = channel;
        this.context = context;
    }


    @Override
    public void read() throws IOException {
        if (!readStarted) {
            ByteBuffer tmp = ByteBuffer.allocate(4);
            int read = channel.read(tmp);
            if (read == -1) {
                close();
                return;
            }
            tmp.flip();
            length = tmp.getInt();
            currentRead = ByteBuffer.allocate(length);
            readStarted = true;
            return;
        }
        int alloc = Math.min(length, 1024);

        var tmp = ByteBuffer.allocate(alloc);
        int read = channel.read(tmp);
        if (read == -1) {
            close();
            return;
        }
        length -= read;
        checkLengthAndThrowException();
        currentRead.put(tmp.array(), 0, read);
        if (length == 0) {
            readStarted = false;
            processRequest();
        }

    }

    @Override
    public void write(ByteBuffer buffer) throws IOException {
        channel.write(buffer);
    }

    private void checkLengthAndThrowException() {
        if (length < 0) sendBadRequest();
    }

    private void processRequest() {
        ObjectMapper mapper = new ObjectMapper();
        String requestString = null;
        try {
            var json = mapper.readValue(currentRead.array(), JsonNode.class);
            var request = json.get("request");
            if (request == null) {
                sendBadRequest();
                return;
            }
            requestString = request.textValue();
            var node = context.getRouteTree().search(requestString);
            var handler = new RouteHandler(context, node, json.get("data"));
            handler.handle();
            var value = handler.value();
            var buffer = value == null ? ByteBuffer.allocate(0) : ByteBuffer.wrap(value.getBytes(StandardCharsets.UTF_8));
            write(buffer);
        } catch (IOException e) {
            logger.error("Failed To Parse JSON from client", e);
            sendBadRequest();
        } catch (RouteException e) {
            logger.error("Failed To Map Route: ".concat(requestString), e);
        } catch (ConnectionException e) {
            e.printStackTrace();
            sendBadRequest();
        }

    }


    private void sendBadRequest() {
        logger.warn("Sending Bad Request");
        if (channel.isConnected()) {

            try {
                var buffer = context.getDefaultObjectMapper().writeValueAsBytes(new BadRequest("Bad Request", null));
                write(ByteBuffer.wrap(buffer));
            } catch (JsonProcessingException e) {
                //
            } catch (IOException e) {
                try {
                    close();
                } catch (IOException ex) {
                    //closed
                }
            }

        } else {
            try {
                close();
            } catch (IOException e) {
                //ignored
            }
        }
    }

    @Override
    public void close() throws IOException {
        logger.debug("Closing Connection");
        channel.close();
        channel = null;

    }

}
