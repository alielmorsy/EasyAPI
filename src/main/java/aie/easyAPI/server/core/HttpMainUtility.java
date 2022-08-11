package aie.easyAPI.server.core;

public class HttpMainUtility {

    public static boolean shouldReadMainThread(String method) {
        return !(method.equalsIgnoreCase("POST") || method.equalsIgnoreCase("PUT") || method.equalsIgnoreCase("DELETE"));
    }
}
