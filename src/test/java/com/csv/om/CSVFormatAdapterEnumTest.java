package com.csv.om;

import org.apache.commons.csv.CSVFormat;
import org.junit.Test;

import static org.junit.Assert.*;

public class CSVFormatAdapterEnumTest {

    @Test
    public void excel_format() {
        CSVFormatAdapterEnum excel = CSVFormatAdapterEnum.EXCEL;
        assertEquals(CSVFormat.EXCEL, excel.getFormat());
    }

    @Test
    public void default_format() {
        CSVFormatAdapterEnum excel = CSVFormatAdapterEnum.DEFAULT;
        assertEquals(CSVFormat.DEFAULT, excel.getFormat());
    }
}
