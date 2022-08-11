package aie.easyAPI.models;

import aie.easyAPI.enums.ContentCoding;
import aie.easyAPI.enums.ContentType;
import aie.easyAPI.enums.HttpMethod;
import aie.easyAPI.models.http.Header;

import java.io.InputStream;
import java.util.List;

/**
 * A Capsule class contains all data about request and everything behind it like time userAgent, cookies, copy of the body stream, and much more
 */
public class HttpRequest {

    /**
     * method used in that request. Check {@link HttpMethod}
     */
    private HttpMethod method;

    /**
     * content coding of the post body weather compressed or just normal body
     */
    private ContentCoding contentCoding = ContentCoding.NORMAL;

    /**
     * Request path and sub path
     */
    private String path;

    /**
     * Query string in that request, will be null if no  query
     */
    private String queryString;


    /**
     * list contains all cookies in request might be empty if no cookies send in request. Check {@link  Cookie}
     */
    private List<Cookie> cookies;

    /**
     * list contains all headers came in that request. Check {@link Header}
     */
    private List<Header> headers;


    /**
     * content length of the body
     */
    private int contentLength;

    /**
     * input stream for the body of the request if found, maybe null if the request is get of head
     */
    private InputStream body;

    /**
     * Content Type of body check {@link ContentType}
     */
    private ContentType contentType;

    public HttpMethod method() {
        return method;
    }

    public HttpRequest setMethod(HttpMethod method) {
        this.method = method;
        return this;
    }

    public String path() {
        return path;
    }

    public HttpRequest setPath(String path) {
        this.path = path;
        return this;
    }

    public String queryString() {
        return queryString;
    }

    public HttpRequest setQueryString(String queryString) {
        this.queryString = queryString;
        return this;
    }

    public List<Cookie> cookies() {
        return cookies;
    }

    public HttpRequest setCookies(List<Cookie> cookies) {
        this.cookies = cookies;
        return this;
    }

    public List<Header> headers() {
        return headers;
    }

    public HttpRequest setHeaders(List<Header> headers) {
        this.headers = headers;
        return this;
    }

    public int contentLength() {
        return contentLength;
    }

    public HttpRequest setContentLength(int contentLength) {
        this.contentLength = contentLength;
        return this;
    }

    public InputStream body() {
        return body;
    }

    public HttpRequest setBody(InputStream body) {
        this.body = body;
        return this;
    }

    public ContentType contentType() {
        return contentType;
    }

    public HttpRequest setContentType(ContentType contentType) {
        this.contentType = contentType;
        return this;
    }

    public ContentCoding contentCoding() {
        return contentCoding;
    }

    public HttpRequest setContentCoding(ContentCoding contentCoding) {
        this.contentCoding = contentCoding;
        return this;
    }
}
