package com.csv.om;

public interface CSVBeanParserFactory {
    <T> CSVBeanParser<T> newInstance(Class<T> beanClass);
}
