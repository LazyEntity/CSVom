package com.csv.om;

public interface CSVClassParser {

    <T> CSVBeanMetadata<T> getParsedCSVClass(Class<T> beanClass);
}
