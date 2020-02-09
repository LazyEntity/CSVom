package com.csv.om;

import java.io.IOException;
import java.io.Writer;

/**
 * Core class to work with writing objects to csv files and reading csv files to objects
 */
public interface CSVObjectMapper {

    <T> void writeValues(Writer writer,
                         Iterable<T> values,
                         Class<T> valueClass);

    <T> CSVWriter<T> getCSVWriter(Writer writer, Class<T> valueClass) throws IOException;
}
