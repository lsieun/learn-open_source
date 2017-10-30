package com.lsieun.gson.json2java;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class GenericTypesExample8 {
    public static void main(String[] args) {
        // create an animal class that is of type dog.
        Animal<Dog> animal = new Animal<Dog>();
        // Create a Dog instance
        Dog dog = new Dog("I am a dog");

        animal.setAnimal(dog);
        Gson gson = new Gson();
        // Define a Type that is an Animal of type dog.
        Type animalType = new TypeToken<Animal<Dog>>() {}.getType();

        // we first convert the animal object to a json and then read the json
        // back. However we define the json to be of Animal type
        Animal animal1 = gson.fromJson(gson.toJson(animal, animalType), Animal.class);
        System.out.println(animal1.get().getClass()); // prints class
        // com.google.gson.internal.LinkedTreeMap

        // In contrast to above where we read the json back using the Animal
        // type, here we read the json back as the custom animalType Type. This
        // gives Gson an idea of what
        // the generic type should be.
        Animal animal2 = gson.fromJson(gson.toJson(animal), animalType);
        System.out.println(animal2.get().getClass());
        // prints class com.studytrails.json.gson.Dog

    }
}
