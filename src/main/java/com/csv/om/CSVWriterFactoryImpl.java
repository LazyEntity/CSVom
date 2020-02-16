package com.csv.om;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Writer;

public final class CSVWriterFactoryImpl implements CSVWriterFactory {

    private static final Logger log = LogManager.getLogger(CSVWriterFactoryImpl.class);

    @Override
    public <T> CSVWriter<T> newInstance(Writer writer, CSVBeanReader<T> beanReader) throws IOException {
        log.debug("Create CSV writer for class {}", beanReader.getBeanClass().getName());

        CSVFormat format = beanReader.getFormat();
        CSVPrinter printer = new CSVPrinter(writer, format);
        return new CSVWriterImpl<>(beanReader, printer);
    }

}
