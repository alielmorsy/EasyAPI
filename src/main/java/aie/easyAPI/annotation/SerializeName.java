package aie.easyAPI.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used by Serialization to add a custom name to field while serializing instead of the original name of that field
 * Can be used by {@code @SerializeName("newName")}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SerializeName {
    String value();
}
