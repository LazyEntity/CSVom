package com.csv.om;

import java.util.function.Function;

public class CSVPropertyMetadata<T> {

    private String headerName;
    private Function<T, String> propertyConverter;

    String getHeaderName() {
        return headerName;
    }

    void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public Function<T, String> getPropertyConverter() {
        return propertyConverter;
    }

    public void setPropertyConverter(Function<T, String> propertyConverter) {
        this.propertyConverter = propertyConverter;
    }

    @Override
    public String toString() {
        return "ParsedCSVProperty{" +
                "headerName='" + headerName + '\'' +
                '}';
    }
}
