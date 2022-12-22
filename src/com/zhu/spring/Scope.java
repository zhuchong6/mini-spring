package com.zhu.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author by zhuhcong
 * @descr scan component on specific uri
 * @date 2022/12/21 21:27
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {

    /**
     * define bean is singleton or prototype
     * @return
     */
    String value() default "";
}
