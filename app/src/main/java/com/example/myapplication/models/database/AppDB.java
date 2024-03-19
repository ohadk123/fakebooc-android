package com.example.myapplication.models.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.myapplication.MainActivity;
import com.example.myapplication.models.database.daos.PostDao;
import com.example.myapplication.models.database.entities.Post;

@Database(entities = {Post.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    public abstract PostDao postDao();

    public static AppDB getInstance() {
        return Room.databaseBuilder(MainActivity.context, AppDB.class, "AppDB").build();
    }
}
