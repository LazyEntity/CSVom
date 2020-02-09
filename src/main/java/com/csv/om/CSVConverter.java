package com.csv.om;

import com.csv.om.annotations.CSVConvert;


/**
 * Used with {@link CSVConvert} to define converter algorithm
 *
 * @param <T> class of converted element
 */
public interface CSVConverter<T> extends AnnotationCSVConverter<T, CSVConvert> {
}
