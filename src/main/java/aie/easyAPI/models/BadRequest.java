package aie.easyAPI.models;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BadRequest {
    @JsonProperty
    private String message;
    private Object data;

    public BadRequest(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public BadRequest() {
        this.message = message;
    }

    @JsonAnyGetter
    public String message() {
        return message;
    }

    @JsonAnySetter
    public BadRequest setMessage(String message) {
        this.message = message;
        return this;
    }

    @JsonAnyGetter
    public Object data() {
        return data;
    }

    @JsonAnySetter
    public BadRequest setData(Object data) {
        this.data = data;
        return this;
    }
}
