package com.csv.om;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CSVClassParserImplTest {

    @Mock
    private CSVBeanParserFactory parserFactory;

    @InjectMocks
    private CSVClassParserImpl classParser;

    @SuppressWarnings("unchecked")
    @Test
    public void get_parsed_CSV_class() {
        CSVBeanMetadata<Person> beanMetadata = mock(CSVBeanMetadata.class);
        CSVBeanParser<Person> beanParser = mock(CSVBeanParser.class);
        when(beanParser.parseBean()).thenReturn(beanMetadata);
        when(parserFactory.newInstance(Person.class)).thenReturn(beanParser);

        // call
        CSVBeanMetadata<Person> resultBeanMetadata = classParser.getParsedCSVClass(Person.class);

        assertEquals(parserFactory, classParser.getBeanParserFactory());
        assertEquals(beanMetadata, resultBeanMetadata);
    }

    static class Person {
    }

}
