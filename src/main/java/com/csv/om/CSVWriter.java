package com.csv.om;

import java.io.Closeable;
import java.io.IOException;

/**
 * There is core a class, that write objects to cvs format file
 * Object of {@link CSVWriterImpl} type can be obtained using {@link CSVObjectMapperImpl#getCSVWriter}
 *
 * @param <T> type of objects that will be converted to csv
 */
public interface CSVWriter<T> extends Closeable {

    void writeAll(Iterable<T> values) throws IOException;

    /**
     * Write in csv file new row with data from sent entity
     *
     * @param value object of converted to csv class
     */
    void write(T value) throws IOException;

}
