package com.lsieun.gson.exclusion_strategy;

import com.google.gson.annotations.Expose;

import java.awt.*;

public class Cat {
    @Expose
    private String name;
    private int age;
    private Color color;
    @Expose
    @Country
    private String country;
    @Expose
    private Boolean lazy = null;

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setLazy(Boolean lazy) {
        this.lazy = lazy;
    }

    public Boolean getLazy() {
        return lazy;
    }
}
