package aie.easyAPI.interfaces;

import aie.easyAPI.context.Controller;
import aie.easyAPI.excepation.ControllerException;

public interface IControllersMapper {
    /**
     * Check {@link aie.easyAPI.interfaces.IContextWrapper#addController(Class)}
     */
    void addController(Class<? extends Controller> controllerClass) throws ControllerException;
}
