/*
 * Copyright 2017 BangChen Information Technology Ltd., Co.
 * Licensed under the Apache License 2.0.
 */
package com.bc.pmpheep.annotation;

import com.bc.pmpheep.back.util.Const;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;

/**
 * Excel表头注解
 *
 * @author L.X <gugia@qq.com>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface  ExcelHeader {

    String header() default "";
    String cellType() default "";

    String alias_0() default "";
    String alias_1() default "";
    String alias_2() default "";
    String alias_3() default "";

    int headerTotalLevel() default 1;
    String usedPropertyName() default "";

}
