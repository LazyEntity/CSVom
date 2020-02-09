package com.csv.om;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CSVObjectMapperImplTest {


    @Mock
    private CSVWriterFactory factory;

    @Mock
    private CSVClassParser classParser;

    @InjectMocks
    private CSVObjectMapperImpl om;

    @Test
    @SuppressWarnings("unchecked")
    public void writeValues() throws IOException {
        List<Person> persons = asList(
                new Person("Robin Williams"),
                new Person("Betty White")
        );
        when(classParser.getParsedCSVClass(Person.class)).thenReturn(new CSVBeanMetadata<>());
        CSVWriter<Person> csvWriter = (CSVWriter<Person>) mock(CSVWriter.class);
        when(factory.<Person>newInstance(any(), any())).thenReturn(csvWriter);

        // call
        om.writeValues(new StringWriter(), persons, Person.class);

        verify(classParser, times(1)).getParsedCSVClass(Person.class);
        verify(factory, times(1)).newInstance(any(), any());
        verify(csvWriter, times(1)).writeAll(persons);
        verify(csvWriter, times(1)).close();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void getWriter() throws IOException {
        when(classParser.getParsedCSVClass(Person.class)).thenReturn(new CSVBeanMetadata<>());
        CSVWriter<Person> csvWriter = (CSVWriter<Person>) mock(CSVWriter.class);
        when(factory.<Person>newInstance(any(), any())).thenReturn(csvWriter);

        // call
        CSVWriter<Person> writer = om.getCSVWriter(new StringWriter(), Person.class);

        assertEquals(csvWriter, writer);
        verify(classParser, times(1)).getParsedCSVClass(Person.class);
        verify(factory, times(1)).newInstance(any(), any());
    }


    static class Person {
        private final String name;

        public Person(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
