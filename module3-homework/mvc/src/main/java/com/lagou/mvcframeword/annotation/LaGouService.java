package com.lagou.mvcframeword.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LaGouService {
    String value() default "";
}
