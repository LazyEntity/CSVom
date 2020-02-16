package com.csv.om;

import org.apache.commons.csv.CSVFormat;

import java.util.List;
import java.util.stream.Collectors;

public final class CSVBeanReaderImpl<T> implements CSVBeanReader<T> {

    private final CSVBeanMetadata<T> beanMetadata;

    CSVBeanReaderImpl(CSVBeanMetadata<T> beanMetadata) {
        this.beanMetadata = beanMetadata;
    }

    @Override
    public boolean isWriteHeader() {
        return beanMetadata.isWriteHeader();
    }

    @Override
    public CSVFormat getFormat() {
        CSVFormatAdapter formatAdapter = beanMetadata.getFormatAdapter();
        return formatAdapter.getFormat();
    }

    @Override
    public List<String> getHeader() {
        return beanMetadata.getProperties()
                .stream().map(CSVPropertyMetadata::getHeaderName)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> mapToRow(T bean) {
        List<CSVPropertyMetadata<T>> properties = beanMetadata.getProperties();
        return properties.stream().map(property -> getPropertyValue(bean, property))
                .collect(Collectors.toList());
    }

    private String getPropertyValue(T entity, CSVPropertyMetadata<T> property) {
        return property.getPropertyConverter().apply(entity);
    }

    @Override
    public Class<T> getBeanClass() {
        return beanMetadata.getBeanClass();
    }
}
