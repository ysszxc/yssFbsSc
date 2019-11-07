package com.qf.aop;

import java.lang.annotation.*;

/**
 * @author Yss
 * @Date 2019/10/25 0025
 */

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Islogin {

    boolean mustLogin() default  false;
}
