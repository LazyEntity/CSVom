package com.csv.om;

public final class CSVBeanParserFactoryImpl implements CSVBeanParserFactory{

    private final CSVFormatAdapter defaultCSVFormatAdapter;

    CSVBeanParserFactoryImpl(CSVFormatAdapter defaultCSVFormatAdapter) {
        this.defaultCSVFormatAdapter = defaultCSVFormatAdapter;
    }

    @Override
    public <T> CSVBeanParser<T> newInstance(Class<T> beanClass) {
        return new CSVBeanParser<>(beanClass, defaultCSVFormatAdapter);
    }

    public CSVFormatAdapter getDefaultCSVFormatAdapter() {
        return defaultCSVFormatAdapter;
    }
}
