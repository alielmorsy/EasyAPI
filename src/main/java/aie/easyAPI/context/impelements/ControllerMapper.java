package aie.easyAPI.context.impelements;

import aie.easyAPI.ApplicationContextFactory;
import aie.easyAPI.annotation.*;
import aie.easyAPI.context.Controller;
import aie.easyAPI.core.structure.Node;
import aie.easyAPI.interfaces.IControllersMapper;
import aie.easyAPI.excepation.ControllerException;
import aie.easyAPI.models.ControllerRoutesMapping;

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


        var node = context.getRouteTree().addController(mapAnnotation.value(), controllerClass);
        addController(controllerClass, node);

    }


    private void addController(Class<? extends Controller> controllerClass, Node<String> node) throws ControllerException {

        Method[] methods = controllerClass.getDeclaredMethods();
        for (Method method : methods) {

            UpdateBasedOnNotation(method, node);
        }

    }

    private void UpdateBasedOnNotation(Method method, Node<String> node) throws ControllerException {
        Annotation[] annotations = method.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            String data = extractDataFromMethod(annotation);
            if (data == null) {
                //TODO Logger
                continue;
            }
            if (data.isEmpty() || data.trim().isEmpty()) {
                //TODO logger
                break;
            }
            if (data.equals("/")) {
                context.getRouteTree().addMethodToStartupController(node, method);
            } else {
                context.getRouteTree().addSubURI(node, data, method);
            }
        }
    }

    private static String extractDataFromMethod(Annotation annotation) {
        Class<? extends Annotation> clazz = annotation.annotationType();

        if (APIRequest.class.equals(clazz)) {
            APIRequest get = (APIRequest) annotation;
            return get.value();
        }
        return null;
    }


}
