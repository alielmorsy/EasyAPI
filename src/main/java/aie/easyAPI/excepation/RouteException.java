package aie.easyAPI.excepation;

public class RouteException extends Exception {
    public RouteException(String route) {
        this("", route);
    }

    public RouteException(String message, String route) {

        super("Route: ".concat(route).concat(message));

    }

    public RouteException(String message,String route, Throwable cause) {
        super("Route: ".concat(route).concat(message), cause);
    }

    public RouteException(Throwable cause) {
        super(cause);
    }
}
