import aie.easyAPI.annotation.ControllerRoute;
import aie.easyAPI.annotation.HttpGet;
import aie.easyAPI.context.Controller;

@ControllerRoute("index")
public class TestC2 extends Controller {
    @HttpGet("Hello/World")
    void a() {

    }
}
