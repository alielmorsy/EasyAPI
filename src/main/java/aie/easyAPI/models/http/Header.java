package aie.easyAPI.models.http;

import java.io.Serializable;
import java.nio.charset.Charset;

public class Header implements Serializable {
    private String name;
    private String value;
    private Charset charset;
    public Header() {

    }
    public Header(String name, String value) {
        this.name = name;
        this.value = value;
    }
    public String name() {
        return name;
    }

    public Header setName(String name) {
        this.name = name;
        return this;
    }

    public String value() {
        return value;
    }

    public Header setValue(String value) {
        this.value = value;
        return this;
    }


    public Charset charset() {
        return charset;
    }

    public Header setCharset(Charset charset) {
        this.charset = charset;
        return this;
    }
}
