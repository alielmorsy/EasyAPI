package aie.easyAPI.server;

import aie.easyAPI.excepation.ServerException;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public interface ClientService {


    void write() throws IOException;

    void read() throws IOException;

    boolean readToWrite();

}
