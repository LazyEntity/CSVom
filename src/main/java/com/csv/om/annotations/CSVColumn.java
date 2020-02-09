package com.csv.om.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to specify name of csv column
 * if name not chosen will be used property name
 * if property not market by this annotation, will be used property name
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface CSVColumn {

    String DEFAULT_NAME = "${fieldName}";

    /**
     * Name of csv column
     */
    String value() default DEFAULT_NAME;
}
