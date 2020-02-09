package com.csv.om;

import java.lang.annotation.Annotation;

/**
 * Used to define converter with custom annotation, marked {@link com.csv.om.annotations.CSVConvert}
 * @param <T> class of converted element
 * @param <S> annotation that marked {@link com.csv.om.annotations.CSVConvert}
 */
public interface AnnotationCSVConverter<T, S extends Annotation> {

    default String toCSVColumn(T value, S annotation) {
        throw new UnsupportedOperationException("Converting to csv column not supported");
    }

    default T toObject(String column, S annotation) {
        throw new UnsupportedOperationException("Converting to property not supported");
    }

}
