package com.lsieun.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonTester_A {
    public static void main(String[] args) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        //json-->java object
        String jsonString = "{\"name\":\"lucy\", \"age\":12}";
        Student student = gson.fromJson(jsonString, Student.class);
        System.out.println(student);

        //java object --> json
        jsonString = gson.toJson(student);
        System.out.println(jsonString);
    }
}

