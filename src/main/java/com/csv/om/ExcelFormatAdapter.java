package com.csv.om;

import org.apache.commons.csv.CSVFormat;

public class ExcelFormatAdapter implements CSVFormatAdapter {
    @Override
    public CSVFormat getFormat() {
        return CSVFormat.EXCEL;
    }
}

