import aie.easyAPI.context.impelements.ApplicationContext;
import aie.easyAPI.core.ClassRegister;
import aie.easyAPI.excepation.ControllerException;
import aie.easyAPI.excepation.RouteException;
import aie.easyAPI.excepation.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class TestControllerMapper {

    @Test
    void testMappingManually() throws ControllerException, RouteException, ServiceException {
        long start = System.nanoTime();
        var contextWrapper = new ApplicationContext();
        contextWrapper.addController(TestC.class);
        contextWrapper.addController(TestC2.class);
        contextWrapper.addController(TestC3.class);
        contextWrapper.registerService(TestService.class);
        contextWrapper.registerService(TestService2.class);
        Assertions.assertNotNull(contextWrapper.getRouteTree().search("test1/hello"));
        Assertions.assertDoesNotThrow(() -> contextWrapper.getServiceInstance(TestService2.class));
        System.out.println("Time Used" + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start));
    }

    @Test
    void testAutoMapper() throws RouteException {
        long start = System.nanoTime();
        var contextWrapper = new ApplicationContext();
        ClassRegister.getInstance().setContext(contextWrapper);
        ClassRegister.getInstance().findClasses();
        Assertions.assertNotNull(contextWrapper.getRouteTree().search("test1/hello"));
        //Assertions.assertDoesNotThrow(() -> contextWrapper.getServiceInstance(TestService.class));
        Assertions.assertDoesNotThrow(() -> contextWrapper.getServiceInstance(TestService2.class));
        System.out.println("Time Used" + TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start));
    }
}
