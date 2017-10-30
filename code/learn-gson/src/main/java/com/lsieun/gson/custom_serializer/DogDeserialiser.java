package com.lsieun.gson.custom_serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class DogDeserialiser implements JsonDeserializer<Dog> {
    @Override
    public Dog deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String name = json.getAsJsonObject().get("name").getAsString();
        name = name.replace(" ", "_");
        Dog dog = new Dog(name);

        return dog;
    }

    public static void main(String[] args) {
        String json = "{\"animal\":{\"name\":\"I am a dog\"}}";
        Gson gson = new GsonBuilder().registerTypeAdapter(Dog.class, new DogDeserialiser()).create();
        Type animalType = new TypeToken<Animal<Dog>>() { }.getType();
        Animal<Dog> animal = gson.fromJson(json, animalType);
        System.out.println(animal.get().getName());
    }

}
