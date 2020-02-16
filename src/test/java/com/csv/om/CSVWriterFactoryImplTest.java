package com.csv.om;

import org.apache.commons.csv.CSVFormat;
import org.junit.Test;

import java.io.IOException;
import java.io.Writer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CSVWriterFactoryImplTest {

    @Test
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void new_instance() throws IOException {
        CSVWriterFactoryImpl writerFactory = new CSVWriterFactoryImpl();

        //call
        CSVBeanReader beanReader = mock(CSVBeanReader.class);
        when(beanReader.getFormat()).thenReturn(CSVFormat.DEFAULT);
        when(beanReader.getBeanClass()).thenReturn(Person.class);
        CSVWriter csvWriter = writerFactory.newInstance(mock(Writer.class), beanReader);

        assertNotNull(csvWriter);
        assertTrue(csvWriter instanceof CSVWriterImpl);
    }

    static class Person {}
}
