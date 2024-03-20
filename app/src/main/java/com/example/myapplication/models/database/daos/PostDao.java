package com.example.myapplication.models.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.myapplication.models.database.entities.Post;

import java.util.List;

@Dao
public interface PostDao {
    @Query("SELECT * FROM post")
    List<Post> getPosts();

    @Insert
    void insert(Post... posts);

    @Insert
    void insertList(List<Post> posts);

    @Update
    void update(Post... posts);

    @Delete
    void delete(Post... posts);

    @Query("DELETE FROM post")
    void clear();
}
