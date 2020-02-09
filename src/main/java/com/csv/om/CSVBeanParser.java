package com.csv.om;

import com.csv.om.annotations.CSVAccess;
import com.csv.om.annotations.CSVTable;
import com.csv.om.annotations.enums.CSVAccessType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.beans.IntrospectionException;
import java.util.List;



class CSVBeanParser<T> {

    private static final Logger log = LogManager.getLogger(CSVBeanParser.class);
    private static final CSVAccessType DEFAULT_ACCESS_TYPE = CSVAccessType.FIELD;


    private final Class<T> beanClass;

    private final CSVFormatAdapter defaultFormat;

    CSVBeanParser(Class<T> beanClass, CSVFormatAdapter defaultFormatAdapter) {
        this.beanClass = beanClass;
        this.defaultFormat = defaultFormatAdapter;
    }

    CSVBeanMetadata<T> parseValue() {
        log.debug("Parse class {}", beanClass.getName());

        final CSVBeanMetadata<T> csvBeanMetadata = new CSVBeanMetadata<>();

        putFromCSVTable(csvBeanMetadata);

        final CSVAccessType accessType = getAccessType();
        final CSVPropertyParser<T> propertyParser = new CSVPropertyParser<>(beanClass, accessType);
        csvBeanMetadata.setProperties(getCsvPropertiesMetadata(propertyParser));
        return csvBeanMetadata;
    }

    private List<CSVPropertyMetadata<T>> getCsvPropertiesMetadata(CSVPropertyParser<T> propertyParser) {
        try {
            return propertyParser.getCSVPropertiesMetadata();
        } catch (IntrospectionException e) {
            throw new CSVException(String.format("Problems on parse class %s", beanClass.getName()), e);
        }
    }

    private void putFromCSVTable(CSVBeanMetadata<T> metadata) {
        if (beanClass.isAnnotationPresent(CSVTable.class)) {
            final CSVTable csvTable = beanClass.getAnnotation(CSVTable.class);
            metadata.setWriteHeader(csvTable.isWithHeader());

            final Class<? extends CSVFormatAdapter> format = csvTable.format();
            // TODO протестить
            if (format != DefaultFormatAdapter.class) {
                try {
                    metadata.setFormatAdapter(format.newInstance());
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new CSVException(String.format("CSV format adapter %s should contain public no arg constructor", format.getName()), e);
                }
            } else {
                metadata.setFormatAdapter(defaultFormat);
            }
        } else {
            metadata.setWriteHeader(CSVTable.DEFAULT_IS_WRITE_HEADER);
            metadata.setFormatAdapter(defaultFormat);
        }
    }

    private CSVAccessType getAccessType() {
        return beanClass.isAnnotationPresent(CSVAccess.class) ?
                beanClass.getAnnotation(CSVAccess.class).value() :
                DEFAULT_ACCESS_TYPE;
    }

    Class<T> getBeanClass() {
        return beanClass;
    }

    CSVFormatAdapter getDefaultFormat() {
        return defaultFormat;
    }
}
