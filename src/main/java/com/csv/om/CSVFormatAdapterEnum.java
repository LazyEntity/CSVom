package com.csv.om;

import org.apache.commons.csv.CSVFormat;

public enum CSVFormatAdapterEnum implements CSVFormatAdapter {
    DEFAULT(CSVFormat.DEFAULT),
    EXCEL(CSVFormat.EXCEL);

    private CSVFormat csvFormat;

    CSVFormatAdapterEnum(CSVFormat csvFormat) {
        this.csvFormat = csvFormat;
    }

    @Override
    public CSVFormat getFormat() {
        return csvFormat;
    }
}

