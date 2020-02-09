package com.csv.om;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

public class FieldCSVAnnotationExecutor implements CSVAnnotationExecutor {
    @Override
    public CSVAccessibleObject getAccessibleObject(Field field, PropertyDescriptor property) {
        return new CSVAccessibleObject(property.getReadMethod(), field.getName(), field);
    }
}
