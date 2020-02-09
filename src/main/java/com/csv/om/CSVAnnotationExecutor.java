package com.csv.om;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

interface CSVAnnotationExecutor {
    CSVAccessibleObject getAccessibleObject(Field field, PropertyDescriptor property);
}
