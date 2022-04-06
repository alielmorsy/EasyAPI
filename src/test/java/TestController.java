import aie.easyAPI.Application;
import aie.easyAPI.excepation.ControllerException;
import org.junit.jupiter.api.Test;


public class TestController {
    @Test
    public void test() throws ControllerException, IllegalAccessException {
        long time = System.currentTimeMillis();
        Application.run(new String[0]);
        System.out.println((System.currentTimeMillis() - time));
    }
}


