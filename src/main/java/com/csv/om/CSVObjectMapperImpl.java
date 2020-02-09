package com.csv.om;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.Writer;

public class CSVObjectMapperImpl implements CSVObjectMapper{

    private static final Logger log = LogManager.getLogger(CSVObjectMapperImpl.class);

    private final CSVClassParser parser;

    private final CSVWriterFactory writerFactory;

//    public CSVObjectMapperImpl() {
//        CSVFormatAdapter defaultFormatAdapter = CSVFormatAdapter.DEFAULT;
//        this.parser = new CachedCSVClassParser(defaultFormatAdapter);
//    }

    public CSVObjectMapperImpl(CSVClassParser parser, CSVWriterFactory writerFactory) {
        this.parser = parser;
        this.writerFactory = writerFactory;
    }

    @Override
    public <T> void writeValues(Writer writer,
                                Iterable<T> values,
                                Class<T> valueClass) {
        try (CSVWriter<T> csvWriter = getCSVWriter(writer, valueClass)) {
            csvWriter.writeAll(values);
        } catch (IOException e) {
            throw new CSVException("Problems on writing csv file", e);
        }
    }

    @Override
    public <T> CSVWriter<T> getCSVWriter(Writer writer, Class<T> valueClass) throws IOException {
        log.debug("Create writer for class {}", valueClass.getName());
        CSVBeanMetadata<T> parsedCSVValue = parser.getParsedCSVClass(valueClass);
        return writerFactory.newInstance(writer, parsedCSVValue);
    }
}
