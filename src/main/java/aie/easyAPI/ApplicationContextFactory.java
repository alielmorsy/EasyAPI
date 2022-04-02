package aie.easyAPI;

import aie.easyAPI.annotation.ControllerRoute;
import aie.easyAPI.annotation.HttpGet;
import aie.easyAPI.context.*;
import aie.easyAPI.context.impelements.ControllerMapper;
import aie.easyAPI.core.structure.Tree;
import aie.easyAPI.enums.HttpType;
import aie.easyAPI.excepation.ControllerException;
import aie.easyAPI.excepation.ServiceException;
import aie.easyAPI.interfaces.IContextWrapper;
import aie.easyAPI.models.ControllerRoutesMapping;
import aie.easyAPI.server.T;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class ApplicationContextFactory implements IContextWrapper {
    protected final Map<Mapper, Controller> loadedControllers;
    private int port;
    private final File cacheFolder = new File(".cache");
    private final ControllerMapper controllerMapper;
    private boolean OnSameThread = false;
    private final Tree controllerTree;

    protected ApplicationContextFactory() {
        loadedControllers = new HashMap<>();
        if (!cacheFolder.exists() || cacheFolder.isDirectory()) {
            cacheFolder.mkdir();
        }
        controllerMapper = new ControllerMapper(this);
        controllerTree = new Tree();
    }

    public File getCacheFolder() {
        return cacheFolder;
    }


    protected void setPort(int port) {
        this.port = port;
    }


    public void setOnSameThread(boolean onSameThread) {
        OnSameThread = onSameThread;
    }

    public int getPort() {
        return port;
    }

    boolean isOnSameThread() {
        return OnSameThread;
    }


    public void addController(Class<? extends Controller> controller) throws ControllerException {
        controllerMapper.addController(controller);

    }

    @Override
    public void registerService(Class<? extends IService> service) throws ServiceException {

    }

    @Override
    public boolean unregisterService(Class<? extends IService> service) {
        return false;
    }

    @Override
    public void registerMiddleware(Class<? extends IMiddleware> middlewareClass) {

    }

    @Override
    public boolean unregisterMiddleware(Class<? extends IMiddleware> middlewareClass) {
        return false;
    }


    public Tree getControllerTree() {
        return controllerTree;
    }
}
