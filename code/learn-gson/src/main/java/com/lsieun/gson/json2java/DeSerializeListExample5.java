package com.lsieun.gson.json2java;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;

public class DeSerializeListExample5 {
    public static void main(String[] args) {
        String json = "[{album_id:1,album_title:'album1'},{album_id:2,album_title:'album2'}]";

        Gson gson = new Gson();
        // create the type for the collection. In this case define that the collection is of type Dataset
        Type datasetListType = new TypeToken<Collection<Dataset>>() {}.getType();
        List<Dataset> datasets = gson.fromJson(json, datasetListType);
        for (Dataset dataset : datasets) {
            System.out.println(dataset.getAlbum_title());
            System.out.println(dataset.getAlbum_id());
        }
        // Prints
        //album1
        //1
        //album2
        //2

    }
}
