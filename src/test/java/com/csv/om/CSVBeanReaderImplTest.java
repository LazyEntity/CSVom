package com.csv.om;

import org.apache.commons.csv.CSVFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CSVBeanReaderImplTest {

    @Mock
    private CSVBeanMetadata<Person> beanMetadata;

    @InjectMocks
    private CSVBeanReaderImpl<Person> csvBeanReader;

    @Test
    public void is_write_header_true() {
        when(beanMetadata.isWriteHeader()).thenReturn(true);

        // call
        boolean writeHeader = csvBeanReader.isWriteHeader();

        assertTrue(writeHeader);
    }

    @Test
    public void is_write_header_false() {
        when(beanMetadata.isWriteHeader()).thenReturn(false);

        // call
        boolean writeHeader = csvBeanReader.isWriteHeader();

        assertFalse(writeHeader);
    }

    @Test
    public void get_format() {
        when(beanMetadata.getFormatAdapter()).thenReturn(CSVFormatAdapterEnum.DEFAULT);

        // call
        CSVFormat format = csvBeanReader.getFormat();

        assertEquals(CSVFormatAdapterEnum.DEFAULT.getFormat(), format);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void get_header() {
        CSVPropertyMetadata<Person> property1 = mock(CSVPropertyMetadata.class);
        when(property1.getHeaderName()).thenReturn("id");

        CSVPropertyMetadata<Person> property2 = mock(CSVPropertyMetadata.class);
        when(property2.getHeaderName()).thenReturn("name");

        when(beanMetadata.getProperties()).thenReturn(asList(property1, property2));

        // call
        List<String> header = csvBeanReader.getHeader();

        assertEquals("id", header.get(0));
        assertEquals("name", header.get(1));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void map_to_row() {
        CSVPropertyMetadata<Person> property1 = mock(CSVPropertyMetadata.class);
        when(property1.getPropertyConverter()).thenReturn(person -> "10");

        CSVPropertyMetadata<Person> property2 = mock(CSVPropertyMetadata.class);
        when(property2.getPropertyConverter()).thenReturn(person -> "Quentin Tarantino");

        when(beanMetadata.getProperties()).thenReturn(asList(property1, property2));

        // call
        List<String> row = csvBeanReader.mapToRow(new Person(10L, "Quentin Tarantino"));

        assertEquals("10", row.get(0));
        assertEquals("Quentin Tarantino", row.get(1));
    }

    @Test
    public void get_bean_class() {
        when(beanMetadata.getBeanClass()).thenReturn(Person.class);

        // call
        Class<Person> beanClass = csvBeanReader.getBeanClass();

        assertEquals(Person.class, beanClass);
    }

    static class Person {
        private Long id;

        private String name;

        public Person(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        public Long getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
