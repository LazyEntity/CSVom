package com.csv.om;

import org.apache.commons.csv.CSVPrinter;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

public class CSVWriterImpl<T> implements CSVWriter<T> {

    private static final Logger log = Logger.getLogger(CSVWriterImpl.class.getName());

    private final CSVPrinter printer;
    private final CSVBeanMetadata<T> beanMetadata;

    CSVWriterImpl(Writer writer, CSVBeanMetadata<T> beanMetadata) throws IOException {
        final CSVFormatAdapter formatAdapter = beanMetadata.getFormatAdapter();

        this.beanMetadata = beanMetadata;
        this.printer = new CSVPrinter(writer, formatAdapter.getFormat());

        if (beanMetadata.isWriteHeader()) {
            addHeader(printer, beanMetadata);
        }
    }

    /**
     * Write in csv file header with column names
     * <p>
     * It should be executed before {{@link #write )}} methods
     * to be correct written to the top of csv file
     */
    private static <T> void addHeader(CSVPrinter printer, CSVBeanMetadata<T> CSVBeanMetadata) throws IOException {
        List<String> headers = CSVBeanMetadata.getProperties()
                .stream().map(CSVPropertyMetadata::getHeaderName)
                .collect(Collectors.toList());

        printer.printRecord(headers);
    }

    @Override
    public void writeAll(Iterable<T> values) throws IOException {
        for (T value : values) {
            write(value);
        }
    }

    @Override
    public void write(T value) throws IOException {
        List<CSVPropertyMetadata<T>> properties = beanMetadata.getProperties();
        List<Object> csvValue = properties.stream().map(property -> getPropertyValue(value, property))
                .collect(Collectors.toList());
        printer.printRecord(csvValue);
    }

    private String getPropertyValue(T entity, CSVPropertyMetadata<T> property) {
        return property.getPropertyConverter().apply(entity);
    }

    @Override
    public void close() throws IOException {
        printer.close();
    }

    public CSVPrinter getPrinter() {
        return printer;
    }

    CSVBeanMetadata<T> getBeanMetadata() {
        return beanMetadata;
    }
}
