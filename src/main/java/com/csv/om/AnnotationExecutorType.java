package com.csv.om;

import com.csv.om.annotations.enums.CSVAccessType;

enum AnnotationExecutorType {
    FIELD(CSVAccessType.FIELD, new FieldCSVAnnotationExecutor()),
    PROPERTY(CSVAccessType.PROPERTY, new PropertyCSVAnnotationExecutor());

    static AnnotationExecutorType getByAccessType(CSVAccessType accessType) {
        for (AnnotationExecutorType executorType : values()) {
            if (executorType.type == accessType) {
                return executorType;
            }
        }
        throw new UnsupportedOperationException(String.format("Not found annotation executor for access type %s", accessType));
    }

    private final CSVAccessType type;

    private final CSVAnnotationExecutor annotationExecutor;

    AnnotationExecutorType(CSVAccessType type, CSVAnnotationExecutor annotationExecutor) {
        this.type = type;
        this.annotationExecutor = annotationExecutor;
    }

    CSVAnnotationExecutor getAnnotationExecutor() {
        return annotationExecutor;
    }
}
