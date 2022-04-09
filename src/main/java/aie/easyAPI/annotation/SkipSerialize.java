package aie.easyAPI.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used by Serialization used on fields in serialized fields to determine if u have field don't want to be serialized by serializable
 * if it used on a field it will be skipped
 * Usage {@code @SkipSerialize private String name;}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SkipSerialize {
}
