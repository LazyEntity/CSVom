package com.csv.om;

public final class CSVClassParserImpl implements CSVClassParser {

    private final CSVBeanParserFactory beanParserFactory;

    public CSVClassParserImpl(CSVBeanParserFactory beanParserFactory) {
        this.beanParserFactory = beanParserFactory;
    }

    @Override
    public <T> CSVBeanMetadata<T> getParsedCSVClass(Class<T> beanClass) {
        CSVBeanParser<T> parser = beanParserFactory.newInstance(beanClass);
        return parser.parseBean();
    }

    public CSVBeanParserFactory getBeanParserFactory() {
        return beanParserFactory;
    }
}
