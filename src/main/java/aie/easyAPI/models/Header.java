package aie.easyAPI.models;

import java.io.Serializable;

public class Header implements Serializable {
    private String name;
    private String value;

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


}
