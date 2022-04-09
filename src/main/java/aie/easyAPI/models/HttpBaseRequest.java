package aie.easyAPI.models;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class HttpBaseRequest {
    protected int RequestCode;
    protected InputStream body;

    public HttpBaseRequest(int requestCode) {
        RequestCode = requestCode;
    }

    public HttpBaseRequest setBody(String body) {
        this.body = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
        return this;
    }

    public HttpBaseRequest setBody(File file) throws IOException {
        this.body = new FileInputStream(file);
        return this;
    }

    public HttpBaseRequest setBody(InputStream inputStream) {
        this.body = inputStream;
        return this;
    }

    public HttpBaseRequest setBody(Serializable object) {
        //Todo: Serializable Object
        return this;
    }
}
