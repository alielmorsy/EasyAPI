import aie.easyAPI.context.IService;

public class TestService2 implements IService {
    public TestService2(TestService service) {
        System.out.println("Test Service2");
    }
}
