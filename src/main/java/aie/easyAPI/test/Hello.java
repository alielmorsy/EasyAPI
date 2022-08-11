package aie.easyAPI.test;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Hello {
    @JsonProperty
    private String message;

    @JsonCreator
    public Hello(@JsonProperty String message) {
        this.message = message;
    }

    public Hello() {
    }

    public String message() {
        return message;
    }

    public Hello setMessage(String message) {
        this.message = message;
        return this;
    }
}
