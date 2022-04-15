import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.channels.spi.SelectorProvider;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.CompletableFuture;

public class ThreadTest {

    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException {

        System.exit(1);
        Selector selector = Selector.open();

        ServerSocketChannel channel = ServerSocketChannel.open();
        int conuter = 1;
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_ACCEPT);
        channel.bind(new InetSocketAddress("localhost", 8010));
        while (true) {
            selector.select();

            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey key = keys.next();
                if (!key.isValid()) {
                    keys.remove();
                    continue;
                }
                if (key.isAcceptable()) {
                    System.out.println("Accepted");

                    SocketChannel socket = channel.accept();
                    socket.configureBlocking(false);
                    socket.register(selector, SelectionKey.OP_READ, socket);
                    keys.remove();
                } else if (key.isReadable()) {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(4);
                    SocketChannel socket = (SocketChannel) key.attachment();
                    socket.read(byteBuffer);
                    System.out.println(new String(byteBuffer.array()));
                    keys.remove();
                    System.out.println("Sending To: " + conuter++);
                    CompletableFuture.runAsync(() -> {
                        ByteBuffer bytes;
                        for (int i = 0; i < 1024 * 1024 * 5; i++) {

                            bytes = ByteBuffer.allocate(1024);
                            try {
                                socket.write(bytes);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });


                }
            }
        }


    }
}
