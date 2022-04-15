package aie.easyAPI.server.interfaces;

import aie.easyAPI.excepation.ServerException;

import java.net.SocketException;
import java.nio.channels.SocketChannel;


/**
 *
 */
public interface IServer {
    /**
     * The main method to the serer its used to bind the server and start it
     *
     * @throws ServerException if a problem occurred while binding
     */
    void bind() throws ServerException;


    void stop(boolean closeCurrentConnections) throws ServerException;

}
