package aie.easyAPI.core.structure;

import aie.easyAPI.context.Controller;
import aie.easyAPI.enums.HttpType;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {

    private T value;

    private boolean isVariable;

    private Class<? extends Controller> controllerClass;
    private String methodName;
    private HttpType httpType;

    public HttpType getHttpType() {
        return httpType;
    }

    public void setHttpType(HttpType httpType) {
        this.httpType = httpType;
    }

    final List<Node<T>> nodes = new ArrayList<>();


    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean isVariable() {
        return isVariable;
    }

    public void setVariable(boolean variable) {
        isVariable = variable;
    }

    public List<Node<T>> getNodes() {
        return nodes;
    }

    public Class<? extends Controller> getControllerClass() {
        return controllerClass;
    }

    public void setControllerClass(Class<? extends Controller> controllerClass) {
        this.controllerClass = controllerClass;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
