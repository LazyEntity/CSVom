package com.csv.om;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

class CSVAccessibleObject {

    private Method method;

    private String name;

    private AccessibleObject accessibleObject;

    CSVAccessibleObject(Method method, String name, AccessibleObject accessibleObject) {
        this.method = method;
        this.name = name;
        this.accessibleObject = accessibleObject;
    }

    Method getMethod() {
        return method;
    }

    String getName() {
        return name;
    }

    AccessibleObject getAccessibleObject() {
        return accessibleObject;
    }
}
