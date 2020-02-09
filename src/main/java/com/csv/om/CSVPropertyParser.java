package com.csv.om;

import com.csv.om.annotations.CSVColumn;
import com.csv.om.annotations.CSVConvert;
import com.csv.om.annotations.enums.CSVAccessType;
import org.apache.log4j.Logger;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class CSVPropertyParser<T> {

    private static final Logger log = Logger.getLogger(CSVPropertyParser.class);


    private Class<T> beanClass;
    private CSVAnnotationExecutor annotationExecutor;

    CSVPropertyParser(Class<T> beanClass, CSVAccessType accessType) {
        this.beanClass = beanClass;
        this.annotationExecutor = AnnotationExecutorType.getByAccessType(accessType).getAnnotationExecutor();
    }

    List<CSVPropertyMetadata<T>> getCSVPropertiesMetadata() throws IntrospectionException {
        final Field[] fields = beanClass.getDeclaredFields();
        final Map<String, PropertyDescriptor> properties =
                Stream.of(Introspector.getBeanInfo(beanClass).getPropertyDescriptors())
                        .collect(Collectors.toMap(PropertyDescriptor::getName, property -> property));

        return Stream.of(fields).map(field -> {
                    PropertyDescriptor property = properties.get(field.getName());
                    final CSVAccessibleObject accessibleObject = annotationExecutor.getAccessibleObject(field, property);
                    return getCSVProperty(accessibleObject);
                }
        ).collect(Collectors.toList());
    }


    private CSVPropertyMetadata<T> getCSVProperty(CSVAccessibleObject accessibleObject) {
        final CSVPropertyMetadata<T> csvProperty = new CSVPropertyMetadata<>();

        csvProperty.setHeaderName(getHeaderName(accessibleObject));
        csvProperty.setPropertyConverter(value -> convertProperty(accessibleObject, value));

        log.debug(String.format("Created cvs property: %s for class %s", csvProperty, beanClass));
        return csvProperty;
    }

    private String convertProperty(CSVAccessibleObject accessibleObject, T value) {
        final Object invokedValue = getInvokedObject(accessibleObject, value);

        if (isAnnotated(accessibleObject, CSVConvert.class)) {
            CSVConvert csvConvert = getAnnotation(accessibleObject, CSVConvert.class);
            final Annotation converterAnnotation = getAnnotationAnnotated(accessibleObject, CSVConvert.class);
            return convertValue(invokedValue, csvConvert.converter(), converterAnnotation != null ? converterAnnotation : csvConvert);

        } else {
            return invokedValue.toString();
        }
    }

    // TODO вынести в отедльный класс
    private <A extends Annotation> A getAnnotation(CSVAccessibleObject accessibleObject, Class<A> annotationClass) {
        return accessibleObject.getAccessibleObject().getAnnotation(annotationClass);
    }

    // TODO отрефачить
    private <A extends Annotation> Annotation getAnnotationAnnotated(CSVAccessibleObject accessibleObject, Class<A> annotationClass) {
        final Annotation[] annotations = accessibleObject.getClass().getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().isAnnotationPresent(annotationClass)) {
                return annotation;
            }
        }
        return null;
    }


    // TODO возомжно вынести в отдельный класс
    private Object getInvokedObject(CSVAccessibleObject accessibleObject, T value) {
        Object invokedValue;
        try {
            invokedValue = accessibleObject.getMethod().invoke(value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            // TODO проверить как обрабатывается данная оишбка!!!
            throw new CSVException(String.format("Problems with invoke method %s of class %s",
                    accessibleObject.getMethod().getName(), beanClass.getName()), e);
        }
        return invokedValue;
    }

    @SuppressWarnings("unchecked")
    private String convertValue(Object value, Class<? extends AnnotationCSVConverter> converterClass, Annotation converterAnnotation) {

        try {
            // TODO возможно вынетси из вызова данного метода
            final AnnotationCSVConverter converter = converterClass.newInstance();
            return converter.toCSVColumn(value, converterAnnotation);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CSVException(String.format("Converter %s should contain public no arg constructor", converterClass.getName()), e);
        }
    }

    private String getHeaderName(CSVAccessibleObject accessibleObject) {
        if (isAnnotated(accessibleObject, CSVColumn.class)) {

            CSVColumn csvColumn = accessibleObject.getAccessibleObject().getAnnotation(CSVColumn.class);
            return getPropertyNameFromCSVColumn(accessibleObject, csvColumn);
        } else {
            return accessibleObject.getName();
        }
    }

    // TODO расширить, что бы можно было проверять дочернии аннотации
    private boolean isAnnotated(CSVAccessibleObject accessibleObject, Class<? extends Annotation> annotationClass) {
        return accessibleObject.getAccessibleObject().isAnnotationPresent(annotationClass);
    }

    private String getPropertyNameFromCSVColumn(CSVAccessibleObject accessibleObject, CSVColumn csvColumn) {
        // TODO устанавливать имя правильно
        if (csvColumn.value().equals(CSVColumn.DEFAULT_NAME)) {
            return accessibleObject.getName();
        } else {
            return csvColumn.value();
        }
    }

}
