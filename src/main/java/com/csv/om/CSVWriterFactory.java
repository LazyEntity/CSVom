package com.csv.om;

import java.io.IOException;
import java.io.Writer;

public class CSVWriterFactory {

    public <T> CSVWriter<T> newInstance(Writer writer, CSVBeanMetadata<T> metadata) throws IOException {
        return new CSVWriterImpl<T>(writer, metadata);
    }

}
