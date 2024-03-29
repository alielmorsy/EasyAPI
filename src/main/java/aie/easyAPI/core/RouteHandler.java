package aie.easyAPI.core;

import aie.easyAPI.context.IService;
import aie.easyAPI.core.structure.Node;
import aie.easyAPI.excepation.ConnectionException;
import aie.easyAPI.excepation.ServiceException;
import aie.easyAPI.interfaces.IContextWrapper;
import aie.easyAPI.interfaces.IHandler;
import aie.easyAPI.models.BadRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class RouteHandler implements IHandler {
    private Node<String> routeNode;
    private JsonNode jsonNode;
    private IContextWrapper contextWrapper;
    private String value;

    public RouteHandler(IContextWrapper contextWrapper, Node<String> routeNode, JsonNode jsonNode) {
        this.routeNode = routeNode;
        this.jsonNode = jsonNode;
        this.contextWrapper = contextWrapper;
    }

    @Override
    public void handle() throws ConnectionException {

        var clazz = routeNode.controllerClass();
        var method = routeNode.method();
        var constructor = clazz.getDeclaredConstructors()[0];
        try {
            var methodTypes = method.getParameterTypes();

            var controller = constructor.newInstance(createInstancesOfParameters(constructor));
            Object result;
            if (methodTypes.length == 0) {
                result = method.invoke(controller);
            } else if (methodTypes.length == 1) {
                var param = contextWrapper.getDefaultObjectMapper().convertValue(jsonNode, methodTypes[0]);
                result = method.invoke(controller, param);
            } else {
                throw new IllegalArgumentException("For Now Method should have only 1 parameter");
            }
            if (result != null)
                value = contextWrapper.getDefaultObjectMapper().writeValueAsString(result);
        } catch (ServiceException | InvocationTargetException | InstantiationException | IllegalAccessException | JsonProcessingException e) {
            if (e.getCause() != null) {
                var badRequest = new BadRequest();
                badRequest.setMessage(e.toString());
                try {
                    value = contextWrapper.getDefaultObjectMapper().writeValueAsString(badRequest);
                    return;
                } catch (JsonProcessingException ex) {
                    //
                }
            }
            throw new ConnectionException("Failed To Create Instance for Controller: ".concat(clazz.getName()), e);
        }
    }

    private Object[] createInstancesOfParameters(Constructor<?> constructor) throws ServiceException {
        var classes = constructor.getParameterTypes();
        var objects = new Object[classes.length];
        for (int i = 0; i < classes.length; i++) {
            var clazz = classes[i];
            if (IService.class.isAssignableFrom(clazz)) {
                var c = (Class<? extends IService>) clazz;
                objects[i] = contextWrapper.getServiceInstance(c);
            }
        }
        return objects;
    }

    public String value() {
        return value;
    }
}
