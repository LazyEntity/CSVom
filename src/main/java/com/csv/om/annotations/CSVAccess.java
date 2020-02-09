package com.csv.om.annotations;

import com.csv.om.annotations.enums.CSVAccessType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to specify an access type to be applied to an entity class
 * By default is used field-based access
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CSVAccess {

    /**
     * Specification of field-based or property-based access.
     */
    CSVAccessType value();
}
