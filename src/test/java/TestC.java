import aie.easyAPI.annotation.ControllerRoute;
import aie.easyAPI.annotation.HttpGet;
import aie.easyAPI.annotation.HttpHead;
import aie.easyAPI.annotation.HttpPost;
import org.junit.jupiter.api.Test;

@ControllerRoute("get")
public class TestC extends aie.easyAPI.context.Controller {
    @HttpGet("here")
    public void Lmao() {

    }
    @HttpGet("lmao/{lmao}")
    public void a(){

    }

}
