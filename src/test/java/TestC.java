import aie.easyAPI.annotation.ControllerRoute;
import aie.easyAPI.annotation.APIRequest;

@ControllerRoute("test1")
public class TestC extends aie.easyAPI.context.Controller {
    @APIRequest("hello")
    public void Lmao() {

    }
    @APIRequest("hi")
    public void a(){

    }

}
