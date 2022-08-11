package aie.easyAPI;

import aie.easyAPI.context.*;
import aie.easyAPI.context.impelements.ControllerMapper;
import aie.easyAPI.core.structure.RouteMapper;
import aie.easyAPI.excepation.ControllerException;
import aie.easyAPI.excepation.ServiceException;
import aie.easyAPI.interfaces.IContextWrapper;
import aie.easyAPI.interfaces.IRouteTree;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Application Context Factor is abstract class contains all global data to be within the main scope of the Applications it contains access to all using classes across teh project
 * to add controllers or register services or middle wares or Database Handlers. and get cache folder...Etc
 */

public abstract class ApplicationContextFactory implements IContextWrapper {

    protected List<Class<? extends IService>> _services;
    protected List<Class<? extends IMiddleware>> _middleWares;
    private int port;
    private final File cacheFolder = new File(".cache");
    private final ControllerMapper controllerMapper;

    private final IRouteTree controllerRouteMapper;
    private final ObjectMapper mapper;

    protected ApplicationContextFactory() {
        if (!cacheFolder.exists() || cacheFolder.isDirectory()) {
            cacheFolder.mkdir();
        }
        _services = new ArrayList<>();
        _middleWares = new ArrayList<>();
        controllerMapper = new ControllerMapper(this);
        controllerRouteMapper = new RouteMapper();
        mapper = new ObjectMapper();
    }


    public File getCacheFolder() {
        return cacheFolder;
    }


    protected void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void addController(Class<? extends Controller> controller) throws ControllerException {
        controllerMapper.addController(controller);

    }

    @Override
    public void registerService(Class<? extends IService> service) throws ServiceException {
        _services.add(service);
    }

    @Override
    public boolean unregisterService(Class<? extends IService> service) {
        return _services.remove(service);
    }

    @Override
    public void registerMiddleware(Class<? extends IMiddleware> middlewareClass) {
        _middleWares.add(middlewareClass);
    }

    @Override
    public boolean unregisterMiddleware(Class<? extends IMiddleware> middlewareClass) {
        return _middleWares.remove(middlewareClass);
    }

    @Override
    public IRouteTree getRouteTree() {
        return controllerRouteMapper;
    }

    @Override
    public ObjectMapper getDefaultObjectMapper() {
        return mapper;
    }
}
