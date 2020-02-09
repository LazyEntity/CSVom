package com.csv.om;

class CSVBeanParserFactory {

    private final CSVFormatAdapter defaultCSVFormatAdapter;

    CSVBeanParserFactory(CSVFormatAdapter defaultCSVFormatAdapter) {
        this.defaultCSVFormatAdapter = defaultCSVFormatAdapter;
    }

    <T> CSVBeanParser<T> newInstance(Class<T> beanClass) {
        return new CSVBeanParser<>(beanClass, defaultCSVFormatAdapter);
    }
}
