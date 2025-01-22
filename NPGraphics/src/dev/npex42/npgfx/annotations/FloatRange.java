package dev.npex42.npgfx.annotations;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FloatRange {
    float Max() default 1.0f;
    float Min() default 0.0f;
    float Default() default 1.0f;
}
