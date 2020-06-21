package com.lagou.mvcframeword.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LaGouRequestMapping {
    String value() default "";
}
