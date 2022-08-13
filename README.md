# Easy API

Easy API is a tool can be used to connect java to other programming languages together without concerns about handling
JSON objects or mapping anything

## how to use

`Currently its Server Side only` u can build your client side as u want its simple but let's start with server

```java
public class Main {
    public static void main(String[] args) throws Exception {
        Application.build(args)
                .setPort(5555)
                .searchForClasses(true)
                .start();
    }
}
```

It can also accept arguments instead of add data view code

| argument | useFor                                                                                                                                                      |
|----------|-------------------------------------------------------------------------------------------------------------------------------------------------------------|
| -p       | u can add port via arguments by `-p PORT`                                                                                                                   |
| -s       | u can use it to start auto mapping to search for controllers and service other wise u have to add controllers and service manually via `ApplicationContext` |

### Now let's create simple Controller
```java
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
```
- as u can see we give it a Controller as super class and annotation `@ControllerRoute` 
  - Controller is using for now to identify the class as controller, but it has also future work
  - `ControllerRoute` it return name of the controller. Note without it controller won't be mapped
- on each method u can find `APIRequest` it has the sub path to the current route. Note if its  "/" the route will be just `Hello`
  - u can return anything at each method even `void` all acceptable
###Services
- as u saw above in controller `HelloService` it's a service. u can do all heavy work there and pass the response to controller
- Services must be defined also at a controller field to be passed to the class.
- Let's Create a simple service.
```java
public class HelloService implements IService {
    public HelloService() {

    }

    public String sendKiss() {
        return "Hello we are sending to 'u a kiss";
    }
}
```
- here it implements IService it's what tell the mapper that is Service. and I am planning for future work for it too