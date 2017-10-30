package com.lsieun.gson.exclusion_strategy;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * This class defines custom exclusion policy. We want to ignore all fields that
 * have been annotated with the Country annotation. Note that we can also ignore
 * fields based on name or type. This same policy can be applied to any class.
 * In this example we apply to the CAT class, but it is not limited to the cat
 * class.
 *
 */
public class CustomExclusionStrategy implements ExclusionStrategy {

    private Class classToExclude;

    public CustomExclusionStrategy(Class classToExclude) {
        this.classToExclude = classToExclude;
    }

    // This method is called for all fields. if the method returns false the
    // field is excluded from serialization
    @Override
    public boolean shouldSkipField(FieldAttributes f) {
        if (f.getAnnotation(Country.class) == null)
            return false;

        return true;
    }

    // This method is called for all classes. If the method returns false the
    // class is excluded.
    @Override
    public boolean shouldSkipClass(Class<?> clazz) {
        if (clazz.equals(classToExclude))
            return true;
        return false;
    }

}
