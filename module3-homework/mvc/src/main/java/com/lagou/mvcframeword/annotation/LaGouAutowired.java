package com.lagou.mvcframeword.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LaGouAutowired {
    String value() default "";
}
