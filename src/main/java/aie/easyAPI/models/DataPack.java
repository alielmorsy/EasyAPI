package aie.easyAPI.models;

import aie.easyAPI.enums.ContentType;

import java.io.InputStream;

/**
 * Container for both Request and response contains {@link ContentType} and the Ip of the client, Headers, Cookies, and Input Stream
 * of the Request of Response once is written {@link #body}
 */
public class DataPack {
    private String connectionAddress;
    private ContentType contentType;
    private InputStream body;
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

    public InputStream body() {
        return body;
    }

    public DataPack setBody(InputStream body) {
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
