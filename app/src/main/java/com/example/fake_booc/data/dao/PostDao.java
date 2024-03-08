package com.example.fake_booc.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fake_booc.data.entity.Post.Post;

import java.util.List;

@Dao
public interface PostDao {
    @Query("SELECT * FROM post")
    List<Post> index();

    @Query("SELECT * FROM post WHERE id=:id")
    Post get(int id);

    @Insert
    void insert(Post... posts);

    @Update
    void update(Post... posts);

    @Delete
    void delete(Post... posts);
}
