package aie.easyAPI.server.interfaces;

import java.nio.channels.SocketChannel;

public interface INewClientHandler {

    void writeClient(SocketChannel channel);
}
