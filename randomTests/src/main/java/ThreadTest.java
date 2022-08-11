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
import java.util.StringTokenizer;
import java.util.concurrent.CompletableFuture;

public class ThreadTest {

    public static void main(String[] args) throws IOException, NoSuchFieldException, IllegalAccessException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put((byte) 1);
        buffer.flip();
        System.out.println(Arrays.toString(buffer.slice().array()));
    }
}
