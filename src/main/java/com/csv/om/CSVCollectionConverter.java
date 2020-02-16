package com.csv.om;

import com.csv.om.annotations.CSVCollection;
import com.csv.om.annotations.CSVConvert;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;

/**
 * Collection converter
 */
public class CSVCollectionConverter implements AnnotationCSVConverter<Collection<Object>, CSVCollection> {

    @Override
    @SuppressWarnings("unchecked")
    public String toCSVColumn(Collection<Object> value, CSVCollection annotation) {
        final Class<? extends CSVConverter> converterClass = annotation.elementConverter();
        try {
            final CSVConverter<Object> elementConverter = converterClass.newInstance();

            final StringBuilder builder = new StringBuilder(annotation.prefix());

            final Iterator<Object> iterator = value.iterator();

            while (iterator.hasNext()) {
                final Object element = iterator.next();
                // TODO вероятно дубирование
                final String stringElement = elementConverter.toCSVColumn(element, CSVConvert.class.getDeclaredConstructor().newInstance());

                builder.append(stringElement);

                if (iterator.hasNext()) {
                    builder.append(annotation.delimiter());
                }
            }

            builder.append(annotation.suffix());
            return builder.toString();

        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new CSVException(String.format("Cant create %s instance", converterClass.getName()), e);
        }
    }
}
