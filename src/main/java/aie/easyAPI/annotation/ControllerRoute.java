package aie.easyAPI.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)

public @interface ControllerRoute {
    String value();

}
