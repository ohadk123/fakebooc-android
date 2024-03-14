package com.example.myapplication.models.database.entities.converters;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LikesConverter {
    @TypeConverter
    public List<String> getList(String likes) {
        Type listType = new TypeToken<List<String>>(){}.getType();
        ArrayList<String> likesList = new Gson().fromJson(likes, listType);
        return likesList;
    }

    @TypeConverter
    public String getString(List<String> likes) {
        return new Gson().toJson(likes);
    }
}
