package com.csv.om;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CacheCSVClassParserTest {

    private static class Person {
    }

    @Mock
    private Map<Class<?>, CSVBeanMetadata<?>> parsedClasses;

    @Mock
    private CSVBeanParserFactory beanParserFactory;

    @InjectMocks
    private CachedCSVClassParser parser;

    @Test
    @SuppressWarnings({"unchecked", "SuspiciousMethodCalls"})
    public void parse_class() {
        CSVBeanMetadata<Person> beanMetadata = new CSVBeanMetadata<>();

        CSVBeanParser<Person> beanParser = mock(CSVBeanParser.class);

        when(parsedClasses.get(any())).thenReturn(null);
        when(beanParserFactory.newInstance(Person.class)).thenReturn(beanParser);

        when(beanParser.parseValue()).thenReturn(beanMetadata);

        // call
        CSVBeanMetadata<Person> parsedMetadata = parser.getParsedCSVClass(Person.class);

        assertEquals(beanMetadata, parsedMetadata);
        verify(parsedClasses, times(2)).get(any());
        verify(beanParserFactory, times(1)).newInstance(Person.class);
        verify(parsedClasses, times(1)).put(Person.class, beanMetadata);
    }

    @Test
    @SuppressWarnings({"unchecked", "SuspiciousMethodCalls", "rawtypes"})
    public void bean_already_created() {
        CSVBeanMetadata beanMetadata = new CSVBeanMetadata();
        when(parsedClasses.get(Person.class)).thenReturn(beanMetadata);

        // call
        CSVBeanMetadata<Person> parsedMetadata = parser.getParsedCSVClass(Person.class);

        assertEquals(beanMetadata, parsedMetadata);
        verify(parsedClasses, times(1)).get(any());
        verify(beanParserFactory, times(0)).newInstance(any());
        verify(parsedClasses, times(0)).put(any(), any());
    }

    @Test
    @SuppressWarnings({"SuspiciousMethodCalls", "rawtypes"})
    public void bean_created_in_other_thread() {
        final boolean[] isCreated = {false};
        CSVBeanMetadata beanMetadata = new CSVBeanMetadata();
        when(parsedClasses.get(Person.class)).then(invocation -> {
                    if (!isCreated[0]) {
                        isCreated[0] = true;
                        return null;
                    } else {
                        return beanMetadata;
                    }
                });

        // call
        CSVBeanMetadata<Person> parsedMetadata = parser.getParsedCSVClass(Person.class);

        assertEquals(beanMetadata, parsedMetadata);
        verify(parsedClasses, times(2)).get(any());
        verify(beanParserFactory, times(0)).newInstance(any());
        verify(parsedClasses, times(0)).put(any(), any());
    }
}
