package com.csv.om;

import com.csv.om.annotations.CSVConvert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class AnnotationCSVConverterTest {

    private AnnotationCSVConverter<Person, CSVConvert> converter;

    @Rule
    public ExpectedException exceptionCaptor = ExpectedException.none();

    @Before
    public void init() {
        converter = new AnnotationCSVConverter<Person, CSVConvert>() {};
    }
    @Test
    public void to_csv_column() {
        exceptionCaptor.expect(UnsupportedOperationException.class);
        exceptionCaptor.expectMessage("Converting to csv column not supported");

        // call
        converter.toCSVColumn(new Person(), Mockito.mock(CSVConvert.class));
    }

    @Test
    public void to_object() {
        exceptionCaptor.expect(UnsupportedOperationException.class);
        exceptionCaptor.expectMessage("Converting to property not supported");

        // call
        converter.toObject("person", Mockito.mock(CSVConvert.class));
    }

    static class Person {}
}
