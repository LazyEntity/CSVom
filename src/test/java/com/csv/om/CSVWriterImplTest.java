package com.csv.om;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Objects;

import static java.util.Arrays.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CSVWriterImplTest {

    private CSVWriterImpl<Person> csvWriter;

    private CSVBeanReader<Person> beanReader;

    private CSVPrinter printer;

    private StringWriter stringWriter;

    @Before
    @SuppressWarnings("unchecked")
    public void init() throws IOException {
        stringWriter = spy(new StringWriter());
        printer = new CSVPrinter(stringWriter, CSVFormat.DEFAULT);

        beanReader = mock(CSVBeanReader.class);
        when(beanReader.isWriteHeader()).thenReturn(false);
        when(beanReader.getBeanClass()).thenReturn(Person.class);

        csvWriter = new CSVWriterImpl<>(beanReader, printer);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void not_write_header() throws IOException {
        CSVBeanReader<Person> beanReader = mock(CSVBeanReader.class);
        when(beanReader.isWriteHeader()).thenReturn(false);

        // call
        new CSVWriterImpl<>(beanReader, printer);

        verify(beanReader, times(1)).isWriteHeader();
        verify(beanReader, times(0)).getHeader();
        String header = stringWriter.toString();
        assertEquals("", header);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void write_header() throws IOException {
        CSVBeanReader<Person> beanReader = mock(CSVBeanReader.class);
        when(beanReader.isWriteHeader()).thenReturn(true);
        when(beanReader.getHeader()).thenReturn(asList("id", "name"));
        when(beanReader.getBeanClass()).thenReturn(Person.class);

        // call
        new CSVWriterImpl<>(beanReader, printer);

        verify(beanReader, times(1)).isWriteHeader();
        verify(beanReader, times(1)).getHeader();
        String header = stringWriter.toString();
        assertEquals("id,name\r\n", header);
    }

    @Test
    public void writeAll() throws IOException {
        Person darthSidious = new Person(1L, "Darth Sidious");
        Person darthMaul = new Person(2L, "Darth Maul");
        when(beanReader.mapToRow(darthSidious)).thenReturn(asList("1", "Darth Sidious"));
        when(beanReader.mapToRow(darthMaul)).thenReturn(asList("2", "Darth Maul"));

        // call
        csvWriter.writeAll(asList(darthSidious, darthMaul));

        assertEquals("1,Darth Sidious\r\n2,Darth Maul\r\n", stringWriter.toString());
    }

    @Test
    public void write() throws IOException {
        Person darthVader = new Person(1L, "Darth Vader");
        when(beanReader.mapToRow(darthVader)).thenReturn(asList("1", "Darth Vader"));

        // call
        csvWriter.write(darthVader);

        assertEquals("1,Darth Vader\r\n", stringWriter.toString());
    }

    @Test
    public void close() throws IOException {
        // call
        csvWriter.close();

        verify(stringWriter, times(1)).close();
    }

    static class Person {
        private Long id;
        private String name;

        public Person(Long id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return Objects.equals(id, person.id) &&
                    Objects.equals(name, person.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }
    }
}
