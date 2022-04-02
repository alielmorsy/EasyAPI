package aie.easyAPI.interfaces;

import aie.easyAPI.context.Controller;
import aie.easyAPI.context.IMiddleware;
import aie.easyAPI.context.IService;
import aie.easyAPI.excepation.ControllerException;
import aie.easyAPI.excepation.ServiceException;

public interface IContextWrapper {
    /**
     * To Add Controller
     * Check {@link Controller}
     *
     * @param controller The Class Of The Container
     * @throws ControllerException
     */
    void addController(Class<? extends Controller> controller) throws ControllerException;

    /**
     * To Add Service To Application To Deal With Server
     * Check {@link IService}
     *
     * @param service the server class
     * @throws ServiceException
     */

    void registerService(Class<? extends IService> service) throws ServiceException;

    /**
     * To unregister a service that been added before Check {@link #registerService(Class)}
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


}
