package com.csv.om.annotations;

import com.csv.om.AnnotationCSVConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to
 * 1) Mark property to define converter, that will transform object to csv cell value or csv cell value to object
 *    In this case you can use {@link com.csv.om.CSVConverter} to write your own converter algorithm
 * 2) Mark another annotation to define it as converter annotation
 *    In this case you can use {@link AnnotationCSVConverter} to write your own converter algorithm
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
public @interface CSVConvert {
    Class<? extends AnnotationCSVConverter<?, ?>> converter();
}
