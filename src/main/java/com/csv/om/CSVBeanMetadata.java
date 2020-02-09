package com.csv.om;

import java.util.List;

class CSVBeanMetadata<T> {

    private boolean isWriteHeader;

    private CSVFormatAdapter formatAdapter;

    private List<CSVPropertyMetadata<T>> properties;

    CSVBeanMetadata() {
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
}
