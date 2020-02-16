package com.csv.om;

import org.apache.commons.csv.CSVFormat;

/**
 * Used to mark that current CSV class use default csv format, that indicated in {@link CSVObjectMapperImpl}
 */
public final class DefaultFormatAdapter implements CSVFormatAdapter {
    @Override
    public CSVFormat getFormat() {
        throw new UnsupportedOperationException("This method shouldn't be executed");
    }
}
