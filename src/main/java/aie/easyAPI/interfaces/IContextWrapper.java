package aie.easyAPI.interfaces;

import aie.easyAPI.context.Controller;
import aie.easyAPI.context.IMiddleware;
import aie.easyAPI.context.IService;
import aie.easyAPI.excepation.ControllerException;
import aie.easyAPI.excepation.ServiceException;
import aie.easyAPI.utils.StopWatch;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface IContextWrapper {
    /**
     * To Add Controller to Route Tree check {@link Controller},{@link IRouteTree}
     *
     * @param controller The Class Of The Container
     * @throws ControllerException if controller not mapped by {@link aie.easyAPI.annotation.ControllerRoute}
     */
    void addController(Class<? extends Controller> controller) throws ControllerException;

    /**
     * To Add Service To Application To Deal With Server
     * Check {@link IService}
     *
     * @param service the server class
     * @throws ServiceException if service already defined
     */

    void registerService(Class<? extends IService> service) throws ServiceException;

    /**
     * To unregister a service that been added before Check {@link #registerService(Class)}
     *
     * @param service the service class
     * @return return true only if the service removed
     */

    boolean unregisterService(Class<? extends IService> service);

    /**
     * The Main Reason of middlewares is to check request and response before work on it and before send to
     * client check {@link IMiddleware}
     *
     * @param middlewareClass the middleware class
     */
    void registerMiddleware(Class<? extends IMiddleware> middlewareClass);

    /**
     * To Remove Middleware added before Check {@link  #registerMiddleware(Class<IMiddleware>)}
     *
     * @param middlewareClass the middleware class
     * @return return true only if the middleware removed
     */
    boolean unregisterMiddleware(Class<? extends IMiddleware> middlewareClass);

    /**
     * Create Service instances to be used in controller class if needed
     *
     * @param serviceClass Class of the service required
     * @param <T>          Parameter of the subclass of {@link  IService}
     * @return instance of param T
     * @throws ServiceException if service not registered or it require unknown constructor parameter
     */
    <T extends IService> T getServiceInstance(Class<T> serviceClass) throws ServiceException;

    /**
     * Return Object of IRouteTree see {@link IRouteTree}
     */
    IRouteTree getRouteTree();

    /**
     * Return default instance of {@link  ObjectMapper}
     *
     * @return instance of ObjectMapper class
     */
    ObjectMapper getDefaultObjectMapper();

    /**
     * Returns object of stop watch used to count whole time used to run the server
     * @return StopWatch instance
     */
    StopWatch getDefaultStopWatch();
}
