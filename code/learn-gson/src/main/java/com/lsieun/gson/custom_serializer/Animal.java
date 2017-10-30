package com.lsieun.gson.custom_serializer;

public class Animal<T> {

    public T animal;

    public void setAnimal(T animal) {
        this.animal = animal;
    }

    public T get() {
        return animal;
    }

}
