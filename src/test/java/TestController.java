import aie.easyAPI.Application;
import aie.easyAPI.context.impelements.ApplicationContext;
import aie.easyAPI.context.impelements.ControllerMapper;
import aie.easyAPI.excepation.ControllerException;
import aie.easyAPI.server.core.StandardServer;
import org.junit.jupiter.api.Test;


public class TestController {
    @Test
    public void test() throws Exception {
        long time = System.currentTimeMillis();
        Application.build(new String[0]).setPort(1234).start();
        System.out.println((System.currentTimeMillis() - time));

    }
}


