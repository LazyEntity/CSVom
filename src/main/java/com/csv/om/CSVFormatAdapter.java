package com.csv.om;

import org.apache.commons.csv.CSVFormat;


/**
 * Used in {@link com.csv.om.annotations.CSVTable} to define {@link CSVFormat} for tune csv converter of chosen class
 */
public interface CSVFormatAdapter {
    CSVFormat getFormat();
}
