package com.csv.om;

public interface CSVBeanReaderFactory {
    <T> CSVBeanReader<T> newInstance(CSVBeanMetadata<T> beanMetadata);
}
