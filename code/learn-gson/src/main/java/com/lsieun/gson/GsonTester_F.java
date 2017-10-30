package com.lsieun.gson;

import com.google.gson.Gson;

import java.util.Arrays;

public class GsonTester_F {
    public static void main(String args[]) {
        Gson gson = new Gson();
        int[] marks = {100,90,85};
        String[] names = {"Ram","Shyam","Mohan"};

        //Serialization
        System.out.print("{");
        System.out.print("marks:" + gson.toJson(marks) + ",");
        System.out.print("names:" + gson.toJson(names));
        System.out.println("}");

        //De-serialization
        marks = gson.fromJson("[100,90,85]", int[].class);
        names = gson.fromJson("[\"Ram\",\"Shyam\",\"Mohan\"]", String[].class);
        System.out.println("marks:" + Arrays.toString(marks));
        System.out.println("names:" + Arrays.toString(names));
    }
}
