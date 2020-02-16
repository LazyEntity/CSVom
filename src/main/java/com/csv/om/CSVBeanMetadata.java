package com.csv.om;

import java.util.List;

class CSVBeanMetadata<T> {

    private Class<T> beanClass;

    private boolean isWriteHeader;

    private CSVFormatAdapter formatAdapter;

    private List<CSVPropertyMetadata<T>> properties;

    CSVBeanMetadata() {
    }

    CSVBeanMetadata(Class<T> beanClass) {
        this.beanClass = beanClass;
    }

    boolean isWriteHeader() {
        return isWriteHeader;
    }

    void setWriteHeader(boolean writeHeader) {
        isWriteHeader = writeHeader;
    }

    List<CSVPropertyMetadata<T>> getProperties() {
        return properties;
    }

    void setProperties(List<CSVPropertyMetadata<T>> properties) {
        this.properties = properties;
    }

    CSVFormatAdapter getFormatAdapter() {
        return formatAdapter;
    }

    void setFormatAdapter(CSVFormatAdapter formatAdapter) {
        this.formatAdapter = formatAdapter;
    }

    public Class<T> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<T> beanClass) {
        this.beanClass = beanClass;
    }
}
