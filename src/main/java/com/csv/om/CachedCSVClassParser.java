package com.csv.om;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

final class CachedCSVClassParser implements CSVClassParser{

    private static final Logger log = LogManager.getLogger(CachedCSVClassParser.class);

    private final CSVBeanParserFactory beanParserFactory;
    private final Map<Class<?>, CSVBeanMetadata<?>> parsedClasses;

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

                    log.debug("Cache parsed csv class {}", beanClass.getName());
                    parsedClasses.put(beanClass, parsedCSVValue);
                }
            }
        }
        return parsedCSVValue;
    }

    private <T> CSVBeanMetadata<T> parseClass(Class<T> valueClass) {
        CSVBeanParser<T> parser = beanParserFactory.newInstance(valueClass);
        return parser.parseBean();
    }

    public CSVBeanParserFactory getBeanParserFactory() {
        return beanParserFactory;
    }
}
