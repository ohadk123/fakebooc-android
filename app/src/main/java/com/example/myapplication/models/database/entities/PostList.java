package com.example.myapplication.models.database.entities;

import java.util.List;

public class PostList {
    List<Post> posts;

    public PostList(List<Post> posts) {
        this.posts = posts;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
