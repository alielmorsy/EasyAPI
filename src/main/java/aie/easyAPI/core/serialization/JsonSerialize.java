package aie.easyAPI.core.serialization;

import aie.easyAPI.excepation.SerializeException;
import aie.easyAPI.utils.SerializationUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class JsonSerialize implements ISerializable {

    @Override
    public <T extends Serializable> String DeSerialize(T object) {
        try {
            if (object instanceof ArrayList<?>) {
                JSONArray array = deserializeArray((ArrayList<? extends Serializable>) object);
                return array.toString();
            } else {
                return deserializeObject(object).toString();
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public <T extends Serializable> T serialize(String s, Class<?> tClass) throws SerializeException {

        if (s.startsWith("[")) {
            ArrayList<Object> list = new ArrayList<>();
            JSONArray array = new JSONArray(s);
            for (int i = 0; i < array.length(); i++) {
                Object o = array.get(i);
                if (SerializationUtils.isPrimitiveType(o.getClass())) {
                    list.add(o);
                } else {
                    list.add(serializeObject((JSONObject) o, tClass));
                }
            }
            return (T) list;
        } else if (s.startsWith("{")) {
            JSONObject jsonObject = new JSONObject(s);
            return serializeObject(jsonObject, tClass);
        } else
            throw new SerializeException("Bad Json Data");


    }

    private <T extends Serializable> JSONArray deserializeArray(ArrayList<T> list) throws IllegalAccessException {
        JSONArray array = new JSONArray();
        for (T t : list) {

            if (SerializationUtils.isPrimitiveType(t.getClass())) {
                array.put(t);
            } else if (t instanceof List<?>) {
                array.put(deserializeArray((ArrayList<T>) t));
            } else if (t instanceof Serializable) {
                array.put(deserializeObject(t));
            }
        }
        return array;
    }

    private <T extends Serializable> JSONObject deserializeObject(T object) throws IllegalAccessException {
        JSONObject jsonObject = new JSONObject();
        Field[] fields = SerializationUtils.loadFields(object.getClass());
        for (Field field : fields) {
            Object fieldValue = field.get(object);

            if (SerializationUtils.isPrimitiveType(fieldValue.getClass())) {
                jsonObject.put(field.getName(), field.get(object));
            } else if (fieldValue instanceof List<?>) {
                JSONArray array = deserializeArray((ArrayList<? extends Serializable>) fieldValue);
                jsonObject.put(field.getName(), array);
            } else if (fieldValue instanceof Serializable) {
                jsonObject.put(field.getName(), deserializeObject((T) fieldValue));
            } else {
                //TODO: Logging
            }

        }
        return jsonObject;
    }

    private <T extends Serializable> T serializeObject(JSONObject jsonObject, Class<?> clazz) throws SerializeException {
        Field[] fields = SerializationUtils.loadFields(clazz);
        T object;
        try {
            object = (T) clazz.getConstructor().newInstance();
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new SerializeException("Your Serialized Class should have empty constructor and public");
        }
        for (Field field : fields) {

            Object o = jsonObject.opt(field.getName());
            if (o == null) continue;
            try {
                field.set(object, o);
            } catch (IllegalAccessException e) {
                throw new SerializeException("UnMatching Data Type while Serializing Object: " + clazz.getName() + " And Field: " + field.getName());
            }
        }

        return object;
    }





}
