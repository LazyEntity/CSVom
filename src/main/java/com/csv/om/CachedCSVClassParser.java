package com.csv.om;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

class CachedCSVClassParser implements CSVClassParser{

    private static final Logger log = Logger.getLogger(CachedCSVClassParser.class);

    private final CSVBeanParserFactory beanParserFactory;
    private final Map<Class<?>, CSVBeanMetadata<?>> parsedClasses;

    CachedCSVClassParser(CSVFormatAdapter defaultFormatAdapter) {
        this(new CSVBeanParserFactory(defaultFormatAdapter), new HashMap<>());
    }

    CachedCSVClassParser(CSVBeanParserFactory beanParserFactory, Map<Class<?>, CSVBeanMetadata<?>> parsedClasses) {
        this.beanParserFactory = beanParserFactory;
        this.parsedClasses = parsedClasses;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> CSVBeanMetadata<T> getParsedCSVClass(Class<T> beanClass) {
        CSVBeanMetadata<T> parsedCSVValue = (CSVBeanMetadata<T>) parsedClasses.get(beanClass);
        if (parsedCSVValue == null) {
            synchronized (parsedClasses) {
                parsedCSVValue = (CSVBeanMetadata<T>) parsedClasses.get(beanClass);
                if (parsedCSVValue == null) {
                    parsedCSVValue = parseClass(beanClass);

                    log.debug(format("Cache parsed csv class %s", beanClass.getName()));
                    parsedClasses.put(beanClass, parsedCSVValue);
                }
            }
        }
        return parsedCSVValue;
    }

    private <T> CSVBeanMetadata<T> parseClass(Class<T> valueClass) {
        CSVBeanParser<T> parser = beanParserFactory.newInstance(valueClass);
        return parser.parseValue();
    }
}