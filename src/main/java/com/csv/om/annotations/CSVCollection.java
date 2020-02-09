package com.csv.om.annotations;

import com.csv.om.AnnotationCSVConverter;
import com.csv.om.CSVCollectionConverter;
import com.csv.om.CSVConverter;
import com.csv.om.ToStringCSVConverter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specialized realization of {@link CSVConvert}
 * that used to transform collection to csv cell value
 * and from csv cell value to collection
 * {@link CSVCollectionConverter}
 * Besides every element of this collection will be transformed by
 *
 * e.g.
 * annotation with prefix = "<", suffix = ">", separator = ", "
 * convert collection of numbers to:
 * <1, 2, 3, 4>
 */
@CSVConvert(converter = CSVCollectionConverter.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface CSVCollection {

    String prefix() default "";

    String suffix() default "";

    String delimiter() default ",";

    /**
     * Converter for elements of collections
     *
     * by default used {@link ToStringCSVConverter},
     * that doesn't have converter from csv to collection element
     */
    Class<? extends CSVConverter> elementConverter() default ToStringCSVConverter.class;
}
