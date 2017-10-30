package com.lsieun.gson.json2java;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * The Dataset class contains the information about a particular Album.
 * album_title and album_url are two distinct fields in the json. The Dataset
 * object contains the field album_title. Normally Gson would map the
 * album_title property in the json the the album_title field in the Dataset
 * object. However, we dont want that. We want to use the album_url property
 * from the json object to populate the album_title field in the Dataset object.
 * we build a custom TypeAdapter to do that. This is just a trivial case, you
 * could also combine album_url and album_title properties and set it to the
 * album_title field of the Dataset Object.
 *
 */
public class DatasetTypeAdapter extends TypeAdapter<Dataset> {
    @Override
    public Dataset read(JsonReader reader) throws IOException {
        // the first token is the start object
        JsonToken token = reader.peek();
        Dataset dataset = new Dataset();
        if (token.equals(JsonToken.BEGIN_OBJECT)) {
            reader.beginObject();
            while (!reader.peek().equals(JsonToken.END_OBJECT)) {
                if (reader.peek().equals(JsonToken.NAME)) {
                    if (reader.nextName().equals("album_url"))
                        dataset.setAlbum_title(reader.nextString());
                    else
                        reader.skipValue();
                }
            }
            reader.endObject();
        }
        return dataset;
    }

    @Override
    public void write(JsonWriter out, Dataset value) throws IOException {

    }

}