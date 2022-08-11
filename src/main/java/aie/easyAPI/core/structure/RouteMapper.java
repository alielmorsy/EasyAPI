package aie.easyAPI.core.structure;

import aie.easyAPI.context.Controller;
import aie.easyAPI.excepation.RouteException;
import aie.easyAPI.interfaces.IRouteTree;
import aie.easyAPI.models.ControllerRoutesMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Objects;

public class RouteMapper implements IRouteTree {
    private static final Logger logger = LoggerFactory.getLogger(RouteMapper.class);
    public Node<String> root;

    public RouteMapper() {
        root = new Node<>();
        root.setValue("index");
    }


    @Override
    public Node<String> addController(String uri, Class<? extends Controller> clazz) {
        var tmpNode = getControllerNode(root, uri);
        if (tmpNode == null) {
            tmpNode = new Node<>();
            tmpNode.setValue(uri);
            tmpNode.setControllerClass(clazz);
            root.addNode(tmpNode);
        } else {
            var secondClass = tmpNode.controllerClass();
            tmpNode.setControllerClass(clazz);
            logger.warn("URI's controller changed from {} to {}", secondClass.getName(), clazz.getName());
        }
        return tmpNode;
    }

    @Override
    public void addMethodToStartupController(Node<String> node, Method method) {
        node.setMethod(method);
    }

    @Override
    public Node<String> addSubURI(Node<String> parentNode, String uri, Method method) {

        var subNode = getControllerNode(parentNode, uri);
        if (subNode == null) {
            subNode = new Node<>();
            subNode.setValue(uri);
            subNode.setMethod(method);
            parentNode.addNode(subNode);
        } else {
            logger.warn("Controller {} has route changed from {} to {}", parentNode.controllerClass().getName(), subNode.value(), uri);
            subNode.setValue(uri);
        }
        return subNode;
    }

    //TODO: Not Ready Yet
    @Override
    public void remove(String route) {

    }

    @Override
    public Node<String> search(String route) throws RouteException {
        var uris = route.split("/");
        if (uris.length > 2) {
            throw new RouteException("Currently no support to more than 1 sub route", route);
        }
        var node = getControllerNode(root, uris[0]);
        if (node == null) return null;
        if (uris.length == 1) {
            return node.method() == null ? null : node;
        }
        var clazz = node.controllerClass();
        node = getControllerNode(node, uris[1]);
        if (node == null) return null;
        node.setControllerClass(clazz);
        return node;
    }


    private static Node<String> getControllerNode(Node<String> parent, String uri) {
        if (parent.getNodes() == null) return null;
        for (Node<String> node : parent.getNodes()) {
            if (node.value().equals(uri)) {
                return node;
            }
        }
        return null;
    }
}
