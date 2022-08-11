package aie.easyAPI.models;

import aie.easyAPI.enums.HttpMethod;

public class HttpRequestSimpleData {
    private String route;
    private HttpMethod requestType;

    public HttpRequestSimpleData() {
    }

    public HttpRequestSimpleData(String route, HttpMethod requestType) {
        this.route = route;
        this.requestType = requestType;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public HttpMethod getRequestType() {
        return requestType;
    }

    public void setRequestType(HttpMethod requestType) {
        this.requestType = requestType;
    }
}
