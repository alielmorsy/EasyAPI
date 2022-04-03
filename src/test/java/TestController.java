import aie.easyAPI.context.impelements.ApplicationContext;
import aie.easyAPI.context.impelements.ControllerMapper;
import aie.easyAPI.excepation.ControllerException;
import org.junit.jupiter.api.Test;


public class TestController {
    @Test
    public void test() throws ControllerException {
        long time=System.nanoTime();
        ControllerMapper c = new ControllerMapper(new ApplicationContext());
        c.addController(TestC.class);
        c.addController(TestC2.class);
        c.addController(TestC3.class);
        System.out.println((System.nanoTime()-time));
    }
}


