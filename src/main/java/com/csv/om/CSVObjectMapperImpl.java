package com.csv.om;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Writer;

public final class CSVObjectMapperImpl implements CSVObjectMapper {

    private static final Logger log = LogManager.getLogger(CSVObjectMapperImpl.class);

    private final CSVClassParser parser;

    private final CSVWriterFactory writerFactory;

    private final CSVBeanReaderFactory beanReaderFactory;

    public CSVObjectMapperImpl(CSVClassParser parser, CSVWriterFactory writerFactory, CSVBeanReaderFactory beanReaderFactory) {
        this.parser = parser;
        this.writerFactory = writerFactory;
        this.beanReaderFactory = beanReaderFactory;
    }

    @Override
    public <T> void writeValues(Writer writer,
                                Iterable<T> values,
                                Class<T> valueClass) {
        try (CSVWriter<T> csvWriter = createCSVWriter(writer, valueClass)) {
            csvWriter.writeAll(values);
        } catch (IOException e) {
            throw new CSVException(String.format("Problems on writing csv file for class %s", valueClass.getName()), e);
        }
    }

    @Override
    public <T> CSVWriter<T> createCSVWriter(Writer writer, Class<T> valueClass) throws IOException {
        log.debug("Create writer for class {}", valueClass.getName());

        CSVBeanMetadata<T> parsedCSVValue = parser.getParsedCSVClass(valueClass);
        CSVBeanReader<T> beanReader = beanReaderFactory.newInstance(parsedCSVValue);
        return writerFactory.newInstance(writer, beanReader);
    }

    public CSVClassParser getParser() {
        return parser;
    }

    public CSVWriterFactory getWriterFactory() {
        return writerFactory;
    }

    public CSVBeanReaderFactory getBeanReaderFactory() {
        return beanReaderFactory;
    }
}
