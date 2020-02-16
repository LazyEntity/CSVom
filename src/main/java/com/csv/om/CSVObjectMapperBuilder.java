package com.csv.om;

import org.apache.commons.csv.CSVFormat;

import java.util.HashMap;

public final class CSVObjectMapperBuilder {

    private boolean isCacheParsedClasses = true;

    private CSVFormat csvFormat;

    CSVObjectMapperBuilder() {
    }

    public static CSVObjectMapperBuilder csvObjectMapper() {
        return new CSVObjectMapperBuilder();
    }


    /**
     * If true then all classes will be parsed once
     * By default is true
     */
    public CSVObjectMapperBuilder cacheParsedClasses(boolean isCacheParsedClasses) {
        this.isCacheParsedClasses = isCacheParsedClasses;
        return this;
    }

    public CSVObjectMapperBuilder defaultCSVFormat(CSVFormat csvFormat) {
        this.csvFormat = csvFormat;
        return this;
    }

    public CSVObjectMapper build() {
        CSVFormatAdapter formatAdapter = getCsvFormatAdapter();

        CSVBeanParserFactory beanParserFactory = new CSVBeanParserFactoryImpl(formatAdapter);

        CSVClassParser classParser = getParser(beanParserFactory);

        CSVWriterFactory writerFactory = new CSVWriterFactoryImpl();
        CSVBeanReaderFactory beanReaderFactory = new CSVBeanReaderFactoryImpl();

        return new CSVObjectMapperImpl(classParser, writerFactory, beanReaderFactory);
    }

    private CSVFormatAdapter getCsvFormatAdapter() {
        CSVFormatAdapter formatAdapter;
        if (csvFormat != null) {
            formatAdapter = () -> csvFormat;
        } else {
            formatAdapter = CSVFormatAdapterEnum.DEFAULT;
        }
        return formatAdapter;
    }

    private CSVClassParser getParser(CSVBeanParserFactory beanParserFactory) {
        CSVClassParser classParser;
        if (isCacheParsedClasses) {
            classParser = new CachedCSVClassParser(beanParserFactory, new HashMap<>());
        } else {
            classParser = new CSVClassParserImpl(beanParserFactory);
        }
        return classParser;
    }
}
