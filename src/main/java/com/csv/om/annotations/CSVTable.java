package com.csv.om.annotations;

import com.csv.om.CSVFormatAdapter;
import com.csv.om.DefaultFormatAdapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to tune csv table connected with marked class
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CSVTable {
    boolean DEFAULT_IS_WRITE_HEADER = true;

    /**
     * Allied to set specific {@link org.apache.commons.csv.CSVFormat}, by implement {@link CSVFormatAdapter}
     */
    Class<? extends CSVFormatAdapter> format() default DefaultFormatAdapter.class;

    /**
     * Mark, that header with column names is written
     */
    boolean isWithHeader() default DEFAULT_IS_WRITE_HEADER;
}


