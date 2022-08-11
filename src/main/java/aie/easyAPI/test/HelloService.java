package aie.easyAPI.test;

import aie.easyAPI.context.IService;

public class HelloService implements IService {
    public HelloService() {

    }

    public String sendKiss() {
        return "Hello we are sending to 'u a kiss";
    }
}
