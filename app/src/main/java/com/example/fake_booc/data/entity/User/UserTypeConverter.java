package com.example.fake_booc.data.entity.User;

import androidx.room.TypeConverter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserTypeConverter {

    @TypeConverter
    public static List<User> stringToUserList(String data) {
        List<User> users = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                // Assuming User has a constructor that accepts a JSONObject or similar method to populate itself from JSON
                User user = new User(jsonObject);
                users.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @TypeConverter
    public static String userListToString(List<User> users) {
        JSONArray jsonArray = new JSONArray();
        try {
            for (User user : users) {
                // Assuming User has a method to convert itself to JSONObject
                JSONObject jsonObject = user.toJsonObject();
                jsonArray.put(jsonObject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray.toString();
    }
}
