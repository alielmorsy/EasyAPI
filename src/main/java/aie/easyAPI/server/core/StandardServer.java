package aie.easyAPI.server.core;

import aie.easyAPI.context.impelements.ApplicationContext;
import aie.easyAPI.excepation.ServerException;
import aie.easyAPI.interfaces.IContextWrapper;
import aie.easyAPI.server.Server;
import aie.easyAPI.server.ServerState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class StandardServer implements Server {
    private static final Logger logger = LoggerFactory.getLogger("Server");
    /**
     * Port used at server
     */
    private int port;

    private ServerState currentState;
    private IContextWrapper context;
    private Selector selector;

    public StandardServer(ApplicationContext context) {
        this.context = context;
    }

    public StandardServer(IContextWrapper context, int port) throws ServerException {
        this.context = context;
        setPort(port);
    }

    @Override
    public void start() throws ServerException {
        currentState = ServerState.Initializing;
        internalStart();
    }

    @Override
    public void shutdown() {

    }

    @Override
    public void setPort(int port) throws ServerException {

        this.port = port;
    }

    @Override
    public ServerState getServerState() {
        return currentState;
    }

    private void internalStart() throws ServerException {
        logger.info("Starting Server");
        try (ServerSocketChannel server = ServerSocketChannel.open()) {
            server.configureBlocking(false);
            server.bind(new InetSocketAddress("0.0.0.0", port));
            selector = Selector.open();
            server.register(selector, SelectionKey.OP_ACCEPT);
            currentState = ServerState.Started;
            context.getDefaultStopWatch().stop();
            logger.info("Server took {} to start", context.getDefaultStopWatch().getElapsedTime(TimeUnit.MILLISECONDS));
            context.getDefaultStopWatch().reset();
            waitForSelection(selector);
        } catch (IOException e) {
            throw new ServerException("Failed To Run Server", e);
        }
    }

    private void waitForSelection(Selector selector) throws IOException {
        while (!Thread.interrupted() && currentState == ServerState.Started) {
            if (selector.select(300) < 0) {
                continue;
            }
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = keys.next();
                keys.remove();
                if (!key.isValid()) continue;

                if (key.isAcceptable())
                    doAccept((ServerSocketChannel) key.channel());
                if (key.isReadable()) {
                    doRead(key);
                }
            }
        }
    }

    private void doAccept(ServerSocketChannel serverChannel) throws IOException {
        SocketChannel channel = serverChannel.accept();
        channel.configureBlocking(false);
        ConnectionHandler container = new ConnectionHandler(context, channel);
        channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, container);
    }

    private boolean doRead(SelectionKey key) throws IOException {
        ConnectionHandler clientService = (ConnectionHandler) key.attachment();
        try {
            clientService.read();
            return true;
        } catch (IOException e) {
            clientService.close();
        }
        return false;
    }


}
