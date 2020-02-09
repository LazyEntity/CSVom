package com.csv.om;

import org.junit.Test;

import static org.junit.Assert.*;

public class CSVBeanParserFactoryTest {

    @Test
    public void create() {
        CSVBeanParserFactory factory = new CSVBeanParserFactory(CSVFormatAdapter.DEFAULT);

        CSVBeanParser<Person> beanParser = factory.newInstance(Person.class);

        assertNotNull(beanParser);
        assertEquals(Person.class, beanParser.getBeanClass());
        assertEquals(CSVFormatAdapter.DEFAULT, beanParser.getDefaultFormat());
    }

    private static class Person {}
}
