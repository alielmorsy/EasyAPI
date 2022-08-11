package aie.easyAPI.server;

import aie.easyAPI.excepation.RouteException;
import aie.easyAPI.interfaces.IContextWrapper;
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
    private IContextWrapper context;
    private boolean readyToWrite = false;
    private boolean readStarted = false;
    private int length = 0;
    private ByteBuffer currentRead;
    private ByteBuffer bufferToWrite;

    public ConnectionHandler(IContextWrapper context, SocketChannel channel) {
        this.channel = channel;
        this.context = context;
    }


    @Override
    public void read() throws IOException {
        if (!readStarted) {
            ByteBuffer tmp = ByteBuffer.allocate(4);
            channel.read(tmp);
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
    public boolean readToWrite() {
        return readyToWrite;
    }

    @Override
    public void write() throws IOException {
        channel.write(bufferToWrite);
    }

    private void checkLengthAndThrowException() throws IOException {
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

        } catch (IOException e) {
            logger.error("Failed To Parse String from client", e);
            try {
                sendBadRequest();
            } catch (IOException ex) {
                logger.error("Failed To Send Bad Request to client", ex);
            }
        } catch (RouteException e) {
            logger.error("Failed To Map Route: ".concat(requestString), e);
        }

    }


    private void sendBadRequest() throws IOException {
        channel.write(ByteBuffer.wrap("Bad Request: ".getBytes(StandardCharsets.UTF_8)));
    }

    @Override
    public void close() throws IOException {
        channel.close();
        bufferToWrite = null;
        currentRead = null;
        channel = null;

    }

}
