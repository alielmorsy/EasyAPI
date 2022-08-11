package aie.easyAPI.test;

import aie.easyAPI.annotation.APIRequest;
import aie.easyAPI.annotation.ControllerRoute;
import aie.easyAPI.context.Controller;

@ControllerRoute("hello")
public class HelloController extends Controller {
    private HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @APIRequest("/")
    public Hello hello(Hello request) {
        Hello hello = new Hello();
        hello.setMessage(helloService.sendKiss());
        return hello;
    }

    @APIRequest("world")
    public Hello world(Hello request) {
        Hello hello = new Hello();
        hello.setMessage("Ola, World saying hi to you and he decided to send you your message be nice :). ".concat(request.message()));
        return hello;
    }
}
