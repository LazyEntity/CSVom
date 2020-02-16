package com.csv.om;

public class CSVBeanReaderFactoryImpl implements CSVBeanReaderFactory{

    @Override
    public <T> CSVBeanReader<T> newInstance(CSVBeanMetadata<T> beanMetadata) {
        return new CSVBeanReaderImpl<>(beanMetadata);
    }
}
