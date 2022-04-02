package aie.easyAPI.models;

import aie.easyAPI.context.Controller;
import aie.easyAPI.enums.HttpType;

import java.util.ArrayList;
import java.util.List;

public class ControllerRoutesMapping {
    private String route;
    private boolean hasSubData = false;
    private HttpType httpType = HttpType.GET;
    private Class<? extends Controller> mainClass;

    public List<ControllerRoutesMapping> getSubLocations() {
        return subLocations;
    }

    public List<String> getVariableRoutes() {
        return variableRoutes;
    }

    public List<ControllerRoutesMapping> subLocations;
    private List<String> variableRoutes;
    private String methodName = "index";

    public ControllerRoutesMapping(String location) {
        this.route = location;

    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public boolean isHasSubData() {
        return hasSubData;
    }

    public void setHasSubData(boolean hasSubData) {
        this.hasSubData = hasSubData;
    }

    public HttpType getHttpType() {
        return httpType;
    }

    public void setHttpType(HttpType httpType) {
        this.httpType = httpType;
    }

    public void addRoute(ControllerRoutesMapping mapping) {
        if (subLocations == null)
            subLocations = new ArrayList<>();
        mapping.setMainClass(mainClass);
        this.subLocations.add(mapping);
    }


    public void addVariableRoute(String variableRoute) {

        if (variableRoutes == null)
            variableRoutes = new ArrayList<>();
        variableRoutes.add(variableRoute);
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }


    public Class<? extends Controller> getMainClass() {
        return mainClass;
    }

    public void setMainClass(Class<? extends Controller> mainClass) {
        this.mainClass = mainClass;
    }
}
