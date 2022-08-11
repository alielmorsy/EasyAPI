package aie.easyAPI.server;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * Interface for Client Handler contains main responsibilities for client service like read and write
 */
public interface ClientService {

    /**
     * if result ready to be sent to client send it over {@link SocketChannel}
     *
     * @throws IOException if stream closed while writing
     */
    void write() throws IOException;

    /**
     * start reading from data from client
     *
     * @throws IOException if stream closed while reading
     */
    void read() throws IOException;

    /**
     * Check Whether data is ready to be sent or not
     * @return true if data ready otherwise false
     */
    boolean readToWrite();

}
