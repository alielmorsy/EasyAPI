package aie.easyAPI.utils;

import aie.easyAPI.core.ClassRegister;
import aie.easyAPI.interfaces.IContextWrapper;

import java.lang.reflect.Field;

public class ContextUtils {

    private ContextUtils() {

    }

    public static void addContextToObject(Object object, IContextWrapper context) {
        Class<?> clazz = object.getClass();
        try {
            Field field=clazz.getDeclaredField("context");
            field.setAccessible(true);
            field.set(object,context);
        } catch (NoSuchFieldException|IllegalAccessException e) {
            throw new RuntimeException("Object: "+clazz.getSimpleName()+" Not have context field");
        }
    }


}
