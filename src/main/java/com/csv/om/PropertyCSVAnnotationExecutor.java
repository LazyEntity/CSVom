package com.csv.om;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PropertyCSVAnnotationExecutor implements CSVAnnotationExecutor {
    @Override
    public CSVAccessibleObject getAccessibleObject(Field field, PropertyDescriptor property) {
        final Method readMethod = property.getReadMethod();
        return new CSVAccessibleObject(readMethod, field.getName(), readMethod);
    }
}
