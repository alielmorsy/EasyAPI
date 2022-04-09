package aie.easyAPI.core;

import java.io.Serializable;

public class Serializer {

    public <T extends Serializable> String SerializeObject(T object) {
        return null;

    }

    public <T extends Serializable> T Deserialize(String json, Class<T> tClass) {
        return null;
    }

}
