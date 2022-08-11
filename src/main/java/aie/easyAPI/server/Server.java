package aie.easyAPI.server;

import aie.easyAPI.excepation.ServerException;

public interface Server {
    /**
     * add specific port to run on it.
     * Supported Ports from 1024 to 65325
     * @param port port server should run at
     */

    void setPort(int port) throws ServerException;

    ServerState getServerState();

    void start() throws ServerException;


    void shutdown();


}
