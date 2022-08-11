package aie.easyAPI.models;

import aie.easyAPI.context.Controller;
import aie.easyAPI.enums.HttpMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControllerRoutesMapping {
    private String route;
    private boolean hasSubData = false;
    private Class<? extends Controller> mainClass;


    public List<String> getVariableRoutes() {
        return variableRoutes;
    }

    private Map<String, String> variablesMap;
    public List<ControllerRoutesMapping> subLocations;
    private List<String> variableRoutes;
    private Map<String, Method> methodsMapping;

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

    public void addVariableForMap(String variableName, String value) {
        if (variablesMap == null) {
            variablesMap = new HashMap<>();
        }
        variablesMap.put(variableName, value);
    }



    public Class<? extends Controller> getMainClass() {
        return mainClass;
    }

    public void setMainClass(Class<? extends Controller> mainClass) {
        this.mainClass = mainClass;
    }

    public Map<String, String> variablesMap() {
        return variablesMap;
    }
}
