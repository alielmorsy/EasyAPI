package aie.easyAPI.core.structure;

import aie.easyAPI.context.Controller;
import aie.easyAPI.enums.HttpMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Node<T> {

    private T value;

    private List<Node<T>> children;

    private Class<? extends Controller> controllerClass;

    private Method method;

    public T value() {
        return value;
    }

    public Node<T> setValue(T value) {
        this.value = value;
        return this;
    }


    public Class<? extends Controller> controllerClass() {
        return controllerClass;
    }

    public Node<T> setControllerClass(Class<? extends Controller> controllerClass) {
        this.controllerClass = controllerClass;
        return this;
    }

    public Method method() {
        return method;
    }

    public Node<T> setMethod(Method method) {
        this.method = method;
        return this;
    }

    public List<Node<T>> getNodes() {
        if (children == null) {
            children = new ArrayList<>();
        }
        return children;
    }

    public void addNode(Node<T> node) {
        if (children == null) {
            children = new ArrayList<>(12);
        }
        children.add(node);
    }
}
