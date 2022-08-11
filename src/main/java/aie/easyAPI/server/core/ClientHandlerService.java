package aie.easyAPI.server.core;

import aie.easyAPI.Application;
import aie.easyAPI.context.impelements.ApplicationContext;
import aie.easyAPI.excepation.ServerException;
import aie.easyAPI.interfaces.IContextWrapper;
import aie.easyAPI.server.ClientService;
import aie.easyAPI.server.ConnectionHandler;

import java.io.Closeable;
import java.io.IOException;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class ClientHandlerService implements  Closeable {


    private final IContextWrapper context;


    private Thread runningThread;
    private Selector selector;
    private boolean isWorking = true;

    public ClientHandlerService(IContextWrapper context) throws ServerException {
        this.context = context;
        try {
            init();
        } catch (IOException e) {
            throw new ServerException("", e);
        }
    }

    private void init() throws IOException {

        selector = Selector.open();

    }


    public synchronized void addClient(SocketChannel channel) throws IOException {
        ConnectionHandler container = new ConnectionHandler(context, channel);
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ, container);
        if (!runningThread.isAlive()) {
            runningThread.start();
        }
    }

    public  void run() throws ServerException {
        while (!Thread.interrupted() && isWorking) {
            try {
                doSelect();
            } catch (IOException e) {
                throw new ServerException("", e);
            }
        }
    }



    public void read(SelectionKey key) throws IOException {
        ConnectionHandler handler = (ConnectionHandler) key.attachment();
        handler.read();
    }


    public void sendError() {

    }


    public void doWrite(SelectionKey key) {

    }


    public void shutDown() {
        isWorking = false;
    }

    private void doSelect() throws IOException {
        selector.select();
        var keys = selector.selectedKeys().iterator();
        while (keys.hasNext()) {
            SelectionKey key = keys.next();
            if (!key.isValid())
                continue;

            if (key.isReadable()) {
                read(key);
            }
            if (key.isWritable()) {
                doWrite(key);
            }
            keys.remove();
        }
    }

    @Override
    public void close() throws IOException {
        runningThread.interrupt();
    }
}
