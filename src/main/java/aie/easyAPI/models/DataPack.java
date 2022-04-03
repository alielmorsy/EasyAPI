package aie.easyAPI.models;

import aie.easyAPI.enums.ContentType;

public class DataPack {
    private String connectionAddress;
    private ContentType contentType;
    private String body;
    private Header[] headers;
    private Cookie[] cookies;

    public String connectionAddress() {
        return connectionAddress;
    }

    public DataPack setConnectionAddress(String connectionAddress) {
        this.connectionAddress = connectionAddress;
        return this;
    }

    public ContentType contentType() {
        return contentType;
    }

    public DataPack setContentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public String body() {
        return body;
    }

    public DataPack setBody(String body) {
        this.body = body;
        return this;
    }

    public Header[] headers() {
        return headers;
    }

    public DataPack setHeaders(Header[] headers) {
        this.headers = headers;
        return this;
    }

    public Cookie[] cookies() {
        return cookies;
    }

    public DataPack setCookies(Cookie[] cookies) {
        this.cookies = cookies;
        return this;
    }
}
