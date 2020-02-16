package com.csv.om;

import org.apache.commons.csv.CSVFormat;

import java.util.List;

interface CSVBeanReader<T> {
    boolean isWriteHeader();

    CSVFormat getFormat();

    List<String> getHeader();

    List<String> mapToRow(T bean);

    Class<T> getBeanClass();
}
