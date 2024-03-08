package com.example.fake_booc.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.fake_booc.data.entity.User.User;
import com.example.fake_booc.data.dao.UserDao;

@Database(entities = { User.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
//    public abstract PostDao postDao();
    public abstract UserDao userDao();
}
