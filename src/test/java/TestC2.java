import aie.easyAPI.annotation.ControllerRoute;
import aie.easyAPI.annotation.APIRequest;
import aie.easyAPI.context.Controller;

@ControllerRoute("test2")
public class TestC2 extends Controller {
    @APIRequest("HelloWorld")
    void a() {

    }
}
