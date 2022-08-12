package aie.easyAPI.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BadRequest {
    @JsonProperty
    private String message;
    @JsonProperty
    private Object data;
    @JsonProperty
    private  boolean success = false;

    public BadRequest(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public BadRequest() {
        this.message = message;
    }


    public String message() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object data() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public boolean success() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
