package com.csv.om;

import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class CSVWriterImpl<T> implements CSVWriter<T> {

    private static final Logger log = LogManager.getLogger(CSVWriterImpl.class);

    private final CSVPrinter printer;

    private final CSVBeanReader<T> beanReader;

    CSVWriterImpl(CSVBeanReader<T> beanReader, CSVPrinter printer) throws IOException {
        this.beanReader = beanReader;
        this.printer = printer;

        if (beanReader.isWriteHeader()) {
            writeHeader();
        }
    }

    /**
     * Write in csv file header with column names
     * <p>
     * It should be executed before {{@link #write )}} methods
     * to be correct written to the top of csv file
     */
    private void writeHeader() throws IOException {
        log.debug("Write header for class {}", beanReader.getBeanClass().getName());
        List<String> header = beanReader.getHeader();
        printer.printRecord(header);
    }

    @Override
    public void writeAll(Iterable<T> values) throws IOException {
        log.debug("Write all values for class {}", beanReader.getBeanClass().getName());
        for (T value : values) {
            write(value);
        }
    }

    @Override
    public void write(T value) throws IOException {
        List<String> row = beanReader.mapToRow(value);
        printer.printRecord(row);
    }

    @Override
    public void close() throws IOException {
        log.debug("Close CSV writer for class {}", beanReader.getBeanClass().getName());
        printer.close();
    }
}
