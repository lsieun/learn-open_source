package com.lsieun.gson.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Dataset {
    public String album_id;
    public String album_title;
    @SerializedName("album_images")
    public List<AlbumImages> images = new ArrayList<AlbumImages>();
}
