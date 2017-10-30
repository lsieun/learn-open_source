package com.lsieun.gson.json2java;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class DatasetTypeAdapterExample8 {
    public static void main(String[] args) throws MalformedURLException, IOException {
        String url = "http://freemusicarchive.org/api/get/albums.json?api_key=60BLHNQCAOUFPIBZ&limit=5";
        String json = IOUtils.toString(new URL(url));
        // Create the custom type adapter and register it with the GsonBuilder
        // class.
        Gson gson = new GsonBuilder().registerTypeAdapter(Dataset.class, new DatasetTypeAdapter()).create();
        // deserialize the json to Albums class. The Dataset objects are part of
        // the Albums class. Whenever Gson encounters an object of type DataSet
        // it calls the DatasetTypeAdapter to read and write json.
        Albums albums = gson.fromJson(json, Albums.class);
        System.out.println(albums.getDataset()[1].getAlbum_title());
        // prints
        // http://freemusicarchive.org/music/The_Yes_Sirs/Through_The_Cracks_Mix_Vol_1/
    }
}
