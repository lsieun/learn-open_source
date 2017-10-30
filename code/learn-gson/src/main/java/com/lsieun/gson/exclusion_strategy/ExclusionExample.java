package com.lsieun.gson.exclusion_strategy;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.awt.*;

public class ExclusionExample {
    public static void main(String[] args) {
        // We create an instance of type CAT.
        Cat cat = new Cat();
        cat.setName("Cat");
        cat.setAge(1);
        cat.setColor(Color.BLACK);
        cat.setCountry("US");
        // we allow serializing null. therefore although the fields lazy is
        // null, it will be serialized. We add a CustomExclusionStrategy that
        // will exclude the Color class. We also allow only those fields that
        // have been exposed using the @Expore annotation
        Gson gson = new GsonBuilder().serializeNulls().setExclusionStrategies(new CustomExclusionStrategy(Color.class))
                .excludeFieldsWithoutExposeAnnotation().create();
        System.out.println(gson.toJson(cat));
        // prints {"name":"Cat","lazy":null}

    }
}
