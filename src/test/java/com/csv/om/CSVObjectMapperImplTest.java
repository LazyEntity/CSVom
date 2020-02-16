package com.csv.om;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CSVObjectMapperImplTest {


    @Mock
    private CSVWriterFactory factory;

    @Mock
    private CSVClassParser classParser;

    @Mock
    private CSVBeanReaderFactory beanReader;

    @InjectMocks
    private CSVObjectMapperImpl om;

    @Rule
    public ExpectedException exceptionCapture = ExpectedException.none();

    @Before
    public void init() {

    }

    @Test
    @SuppressWarnings("unchecked")
    public void write_values() throws IOException {
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

    @Test
    public void write_values_exception() throws IOException {
        exceptionCapture.expect(CSVException.class);
        exceptionCapture.expectMessage(format("Problems on writing csv file for class %s", Person.class.getName()));
        exceptionCapture.expectCause(instanceOf(IOException.class));

        when(factory.newInstance(any(), any())).thenThrow(new IOException("Test problem"));

        // call
        om.writeValues(new StringWriter(), emptyList(), Person.class);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void create_writer() throws IOException {
        when(classParser.getParsedCSVClass(Person.class)).thenReturn(new CSVBeanMetadata<>());
        CSVWriter<Person> csvWriter = (CSVWriter<Person>) mock(CSVWriter.class);
        when(factory.<Person>newInstance(any(), any())).thenReturn(csvWriter);

        // call
        CSVWriter<Person> writer = om.createCSVWriter(new StringWriter(), Person.class);

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
