package aie.easyAPI.context.impelements;

import aie.easyAPI.ApplicationContextFactory;
import aie.easyAPI.annotation.*;
import aie.easyAPI.context.Controller;
import aie.easyAPI.interfaces.IControllersMapper;
import aie.easyAPI.enums.HttpType;
import aie.easyAPI.excepation.ControllerException;
import aie.easyAPI.models.ControllerRoutesMapping;
import aie.easyAPI.models.HttpRequestSimpleData;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerMapper implements IControllersMapper {
    private ApplicationContextFactory context;


    public ControllerMapper(ApplicationContextFactory context) {
        this.context = context;

    }

    @Override
    public void addController(Class<? extends Controller> controllerClass) throws ControllerException {
        ControllerRoute mapAnnotation = controllerClass.getDeclaredAnnotation(ControllerRoute.class);
        if (mapAnnotation == null) {
            throw new ControllerException("Controller: " + controllerClass.getName() + " Not Mapped");
        }

        ControllerRoutesMapping map = new ControllerRoutesMapping(mapAnnotation.value());
        map.setMainClass(controllerClass);
        addController(controllerClass, map);
        context.getControllerTree().add(map);
    }



    private void addController(Class<? extends Controller> controllerClass, ControllerRoutesMapping map) throws ControllerException {

        Method[] methods = controllerClass.getDeclaredMethods();
        for (Method method : methods) {

            UpdateBasedOnNotation(method, map);
        }

    }

    private void UpdateBasedOnNotation(Method method, ControllerRoutesMapping mapping) throws ControllerException {
        Annotation[] annotations = method.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {

            HttpRequestSimpleData data = extractDataFromMethod(annotation);
            if (data == null)
                continue;

            if (data.getRoute().isEmpty() || data.getRoute().trim().isEmpty()) {
                //TODO: Logger Thing
                continue;
            }
            if (data.getRoute().equals("/")) {

                if (data.getRequestType() != HttpType.GET) {
                    ControllerRoutesMapping customMap = new ControllerRoutesMapping("/");
                    customMap.setHttpType(data.getRequestType());
                    customMap.setMethodName(method.getName());
                    mapping.addRoute(customMap);

                } else
                    mapping.setMethodName(method.getName());
            } else {
                addRouteVariables(data.getRoute(), method, mapping, data.getRequestType());
            }
        }
    }

    private boolean addRouteVariables(String value, Method method, ControllerRoutesMapping map, HttpType requestType) throws ControllerException {
        String[] routes = value.split("/");
        if (routes.length == 1) {
            ControllerRoutesMapping subRoute = new ControllerRoutesMapping(value);
            subRoute.setMethodName(method.getName());
            subRoute.setHttpType(requestType);
            map.addRoute(subRoute);
            return true;
        }

        ControllerRoutesMapping newControllerMapping;
        if (routes[0].isEmpty()) {
            newControllerMapping = map;
        } else {
            newControllerMapping = new ControllerRoutesMapping(routes[0]);
            newControllerMapping.setHttpType(requestType);
            newControllerMapping.setMethodName(method.getName());
            map.addRoute(newControllerMapping);
        }
        boolean foundVariables = false;
        for (int i = 1; i < routes.length; i++) {
            String route = routes[i];

            route = checkVariable(route);
            if (route == null) {
                if (foundVariables) {
                    throw new ControllerException("Can't Use Routes After Variables");
                }
                ControllerRoutesMapping newMap = new ControllerRoutesMapping(routes[i]);
                newControllerMapping.addRoute(newMap);
                newMap.setHttpType(requestType);
                newMap.setMethodName(method.getName());
                newControllerMapping = newMap;

            } else {
                foundVariables = true;
                newControllerMapping.addVariableRoute(route);
            }

        }

        return true;
    }


    private static HttpRequestSimpleData extractDataFromMethod(Annotation annotation) {
        Class<? extends Annotation> clazz = annotation.annotationType();

        HttpRequestSimpleData data = new HttpRequestSimpleData();
        if (HttpGet.class.equals(clazz)) {
            HttpGet get = (HttpGet) annotation;

            data.setRoute(get.value());
            data.setRequestType(HttpType.GET);
        } else if (HttpPost.class.equals(clazz)) {
            HttpPost post = (HttpPost) annotation;
            data.setRoute(post.value());
            data.setRequestType(HttpType.POST);
        } else if (HttpDelete.class.equals(clazz)) {
            HttpDelete delete = (HttpDelete) annotation;
            data.setRoute(delete.value());
            data.setRequestType(HttpType.DELETE);
        } else if (HttpHead.class.equals(clazz)) {
            HttpHead head = (HttpHead) annotation;
            data.setRoute(head.value());
            data.setRequestType(HttpType.HEAD);
        } else
            return null;
        return data;
    }

    private static String checkVariable(String value) {
        Pattern pattern = Pattern.compile("\\{([A-Za-z_][A-Za-z1-9]*)}");
        Matcher matcher = pattern.matcher(value);
        return matcher.find() ? matcher.group(1) : null;
    }

}
