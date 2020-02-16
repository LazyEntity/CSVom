package com.csv.om;

import java.io.IOException;
import java.io.Writer;

public interface CSVWriterFactory {
    <T> CSVWriter<T> newInstance(Writer writer, CSVBeanReader<T> beanReader) throws IOException;
}
