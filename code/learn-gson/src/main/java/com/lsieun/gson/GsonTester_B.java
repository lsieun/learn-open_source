package com.lsieun.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class GsonTester_B {
    public static void main(String args[]) {
        try {
            Student student = new Student();
            student.setAge(12);
            student.setName("lily");
            writeJSON(student);

            Student student1 = readJSON();
            System.out.println(student1);
        }
        catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    private static void writeJSON(Student student) throws IOException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        FileWriter writer = new FileWriter("D:/tmp/student.json");
        writer.write(gson.toJson(student));
        writer.close();
    }
    private static Student readJSON() throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        BufferedReader bufferedReader = new BufferedReader(
                new FileReader("D:/tmp/student.json"));

        Student student = gson.fromJson(bufferedReader, Student.class);
        return student;
    }
}

