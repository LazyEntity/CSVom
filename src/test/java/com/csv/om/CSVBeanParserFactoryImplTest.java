package com.csv.om;

import org.junit.Test;

import static org.junit.Assert.*;

public class CSVBeanParserFactoryImplTest {

    @Test
    public void create() {
        CSVBeanParserFactoryImpl factory = new CSVBeanParserFactoryImpl(CSVFormatAdapterEnum.DEFAULT);

        CSVBeanParser<Person> beanParser = factory.newInstance(Person.class);

        assertNotNull(beanParser);
        assertEquals(Person.class, beanParser.getBeanClass());
        assertEquals(CSVFormatAdapterEnum.DEFAULT, beanParser.getDefaultFormat());
    }

    @Test
    public void default_CSV_format_adapter() {
        CSVBeanParserFactoryImpl factory = new CSVBeanParserFactoryImpl(CSVFormatAdapterEnum.DEFAULT);

        CSVFormatAdapter defaultCSVFormatAdapter = factory.getDefaultCSVFormatAdapter();

        assertEquals(CSVFormatAdapterEnum.DEFAULT, defaultCSVFormatAdapter);
    }

    private static class Person {}
}
