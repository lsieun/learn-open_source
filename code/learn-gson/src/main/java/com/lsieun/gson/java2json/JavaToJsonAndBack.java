package com.lsieun.gson.java2json;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lsieun.gson.entity.AlbumImages;
import com.lsieun.gson.entity.Albums;
import com.lsieun.gson.entity.Dataset;

import java.lang.reflect.Field;

public class JavaToJsonAndBack {
    public static void main(String[] args) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting().serializeNulls();


        builder.setFieldNamingStrategy(new FieldNamingStrategy() {
            @Override
            public String translateName(Field f) {
                if (f.getName().equals("albumId"))
                    return "album_id";
                else
                    return f.getName();
            }
        });

        Gson gson = builder.create();

        Albums albums = new Albums();
        albums.title = "Free Music Archive - Albums";
        albums.message = "";
        albums.total = "11259";
        albums.total_pages = 2252;
        albums.page = 1;
        albums.limit = "5";



        Dataset dataset = new Dataset();
        dataset.album_id = "7596";
        dataset.album_title = "Album 1";


        AlbumImages image = new AlbumImages();
        image.albumId = "10";
        image.image_id = "1";

        dataset.images.add(image);
        albums.dataset.add(dataset);
        System.out.println(gson.toJson(albums));
    }
}
