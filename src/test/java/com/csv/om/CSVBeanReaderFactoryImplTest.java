package com.csv.om;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CSVBeanReaderFactoryImplTest {

    @SuppressWarnings("unchecked")
    @Test
    public void newInstance() {
        CSVBeanReaderFactoryImpl beanReaderFactory = new CSVBeanReaderFactoryImpl();
        CSVBeanMetadata<Person> beanMetadata = mock(CSVBeanMetadata.class);
        when(beanMetadata.getBeanClass()).thenReturn(Person.class);

        // call
        CSVBeanReader<Person> csvBeanReader = beanReaderFactory.newInstance(beanMetadata);

        assertNotNull(csvBeanReader);
        assertEquals(Person.class, csvBeanReader.getBeanClass());
    }

    static class Person {
    }
}
