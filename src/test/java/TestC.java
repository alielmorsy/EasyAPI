import aie.easyAPI.annotation.ControllerRoute;
import aie.easyAPI.annotation.APIRequest;

@ControllerRoute("get")
public class TestC extends aie.easyAPI.context.Controller {
    @APIRequest("here")
    public void Lmao() {

    }
    @APIRequest("lmao/{lmao}")
    public void a(){

    }

}
