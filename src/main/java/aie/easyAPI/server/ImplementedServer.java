package aie.easyAPI.server;

import aie.easyAPI.ApplicationContextFactory;
import aie.easyAPI.context.impelements.ApplicationContext;
import aie.easyAPI.excepation.ServerException;
import aie.easyAPI.server.intenral.InternalServer;
import aie.easyAPI.server.interfaces.IServer;

import java.net.SocketException;
import java.nio.channels.SocketChannel;

public class ImplementedServer implements IServer {
    private final ApplicationContextFactory context;
    private final IServer factory;
    private boolean isBind = false;

    public ImplementedServer(ApplicationContextFactory context) {
        this.context = context;
        factory = new InternalServer(this.context);
    }

    @Override
    public void bind() throws ServerException {
        if (isBind)
            throw new ServerException("Server Already Bind");
        factory.bind();
    }

    @Override
    public void stop(boolean closeCurrentConnections) throws ServerException {

    }


}
