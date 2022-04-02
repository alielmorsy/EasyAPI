package aie.easyAPI.models;

import aie.easyAPI.enums.HttpType;

public class HttpRequestSimpleData {
    private String route;
    private HttpType requestType;

    public HttpRequestSimpleData() {
    }

    public HttpRequestSimpleData(String route, HttpType requestType) {
        this.route = route;
        this.requestType = requestType;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public HttpType getRequestType() {
        return requestType;
    }

    public void setRequestType(HttpType requestType) {
        this.requestType = requestType;
    }
}
