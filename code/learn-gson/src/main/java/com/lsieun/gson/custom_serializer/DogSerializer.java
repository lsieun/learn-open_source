package com.lsieun.gson.custom_serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class DogSerializer implements JsonSerializer<Dog> {
    @Override
    public JsonElement serialize(Dog src, Type typeOfSrc, JsonSerializationContext context) {
        // This method gets involved whenever the parser encounters the Dog
        // object (for which this serializer is registered)
        JsonObject object = new JsonObject();
        String name = src.getName().replaceAll(" ", "_");
        object.addProperty("name", name);
        // we create the json object for the dog and send it back to the
        // Gson serializer
        return object;
    }

    public static void main(String[] args) {
        Animal<Dog> animal = new Animal<Dog>();
        Dog dog = new Dog("I am a dog");
        animal.setAnimal(dog);
        // Create the GsonBuilder and register a serializer for the Dog class.
        // Whenever the Dog class is encountered Gson calls the DogSerializer
        // we set pretty printing own to format the json
        Gson gson = new GsonBuilder().registerTypeAdapter(Dog.class, new DogSerializer()).setPrettyPrinting().create();
        // Since Animal contains generic type create the type using TypeToken
        // class.
        Type animalType = new TypeToken<Animal<Dog>>() { }.getType();
        System.out.println(gson.toJson(animal, animalType));
    }
}
