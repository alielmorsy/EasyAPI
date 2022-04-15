package aie.easyAPI.server.intenral;

import aie.easyAPI.excepation.ServerException;
import aie.easyAPI.server.interfaces.INewClientHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SocketClientHandler implements Runnable, INewClientHandler {

    private Object lockObject;

    private static SocketClientHandler handler;

    public SocketClientHandler() throws IOException {
    }

    public static INewClientHandler getInstance() {

        if (handler == null) {
            try {
                handler = new SocketClientHandler();
            } catch (IOException e) {
                e.printStackTrace();
            }
            handler.lockObject = new Object();
            System.out.println(Thread.currentThread().getName());
            new Thread(handler).start();
        }
        return handler;
    }

    private Selector selector = Selector.open();
    private int numberOfConnected = 0;

    @Override
    public void run() {
        while (true) {
            synchronized (lockObject) {
                if (numberOfConnected <= 0) {
                    try {
                        System.out.println("Waiting");
                        lockObject.wait();
                        System.out.println("Waiting Done");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

            doSelect();

        }
    }

    @Override
    public void writeClient(SocketChannel client) {
        System.out.println(Thread.currentThread().getName());
        if (numberOfConnected == 0) {
            synchronized (lockObject) {
                lockObject.notifyAll();
            }
        }
        numberOfConnected++;
        try {
            client.register(selector, client.validOps());
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        }
    }

    private void doSelect() {
        try {
            selector.select();
        } catch (IOException e) {
            //
        }
        Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
        while (keys.hasNext()) {

            SelectionKey key = keys.next();
            keys.remove();
            if (!key.isValid()) continue;

            if (key.isReadable()) {
                System.out.println("Keys");
                read((SocketChannel) key.channel());
            }
            if (key.isWritable()) {

            }
        }
    }

    private void read(SocketChannel channel) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(5);
        try {
            channel.read(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(new String(byteBuffer.array()));
    }

    private void checkForWriting(SocketChannel channel) {

    }
}
