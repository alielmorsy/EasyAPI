package aie.easyAPI.server.intenral;

import aie.easyAPI.ApplicationContextFactory;
import aie.easyAPI.excepation.ServerException;
import aie.easyAPI.server.interfaces.IServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class InternalServer implements IServer {
    private ApplicationContextFactory context;
    private boolean isStopped;
    private boolean closeCurrentConnections;

    public InternalServer(ApplicationContextFactory context) {
        this.context = context;
    }

    @Override
    public void bind() throws ServerException {
        internalBind();
    }


    @Override
    public void stop(boolean closeCurrentConnections) throws ServerException {
        isStopped = true;
        this.closeCurrentConnections = closeCurrentConnections;

    }

    private void internalBind() throws ServerException {
        try {
            Selector selector = Selector.open();
            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_ACCEPT);
            channel.bind(new InetSocketAddress(context.getPort()));
            startListening(selector, channel);
        } catch (Exception e) {
            throw new ServerException("Couldn't Bind Server With Exception", e);
        }
    }

    private void startListening(Selector selector, ServerSocketChannel channel) throws ServerException {
        while (!isStopped) {
            try {
                selector.select();
            } catch (IOException e) {
                isStopped = true;
                throw new ServerException("Exception While Selecting", e);
            }
            Iterable<SelectionKey> keys = selector.selectedKeys();
            try {
                handleKeys(keys.iterator(), channel);
            } catch (IOException e) {
                throw new ServerException("", e);
            }
        }
    }


    private void handleKeys(Iterator<SelectionKey> keys, ServerSocketChannel channel) throws IOException {
        while (keys.hasNext()) {
            SelectionKey selectionKey = keys.next();
            keys.remove();
            if (!selectionKey.isValid()) {
                continue;
            }
            if (selectionKey.isAcceptable()) {
                acceptConnection(channel);
            }
        }
    }

    private void acceptConnection(ServerSocketChannel channel) throws IOException {
        SocketChannel client = channel.accept();
        client.configureBlocking(false);
        SocketClientHandler.getInstance().writeClient(client);
    }

    public boolean isStopped() {
        return isStopped;
    }

}
