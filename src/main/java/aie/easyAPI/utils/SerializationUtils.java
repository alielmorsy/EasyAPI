package aie.easyAPI.utils;

import aie.easyAPI.annotation.SerializeName;
import aie.easyAPI.annotation.SkipSerialize;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SerializationUtils {
    private static final List<Class<?>> primitiveClasses;

    static {
        primitiveClasses = new ArrayList<>();
        primitiveClasses.add(Boolean.class);
        primitiveClasses.add(Character.class);
        primitiveClasses.add(Byte.class);
        primitiveClasses.add(Short.class);
        primitiveClasses.add(Integer.class);
        primitiveClasses.add(Long.class);
        primitiveClasses.add(Float.class);
        primitiveClasses.add(Double.class);
        primitiveClasses.add(String.class);
    }

    /**
     * Check if the given class is a primitive class type or not check {@link  #primitiveClasses}
     *
     * @param clazz the class to be checked
     * @return true if the class is primitive else false
     */
    public static boolean isPrimitiveType(Class<?> clazz) {
        return primitiveClasses.contains(clazz);
    }

    /**
     * To Load All Fields from {@code Class<?> tClass} and filter them and allow access to them and return array contains all usable fields
     *
     * @param tClass the class which used to extract the fields
     * @return Array Contains All Fields can be used
     */
    public static Field[] loadFields(Class<?> tClass) {
        List<Field> fields = new ArrayList<>();
        for (Field field : tClass.getDeclaredFields()) {

            if (mustSkip(field)) continue;
            field.setAccessible(true);
            fields.add(field);
        }
        return fields.toArray(new Field[0]);
    }

    /**
     * Check if that field should be skipped from our serializable or not Check {@link SkipSerialize } Annotation For more details
     *
     * @param field the field to be checked
     * @return return true in case should skip that field else false
     */
    private static boolean mustSkip(Field field) {
        Annotation annotation = field.getAnnotation(SkipSerialize.class);
        return annotation != null;
    }

    public static String getFieldName(Field field) {
        SerializeName annotation = field.getAnnotation(SerializeName.class);
        if (annotation == null) {
            return field.getName();
        }
        return annotation.value();
    }
}
