package com.csv.om;

import com.csv.om.annotations.CSVConvert;

/**
 * Used to convert Object to String by calling method {@link Object#toString()}
 */
public class ToStringCSVConverter implements CSVConverter<Object> {

    @Override
    public String toCSVColumn(Object value, CSVConvert annotation) {
        return value.toString();
    }
}
