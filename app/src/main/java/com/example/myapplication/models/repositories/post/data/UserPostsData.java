package com.example.myapplication.models.repositories.post.data;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.models.database.entities.Post;

import java.util.ArrayList;
import java.util.List;

public class UserPostsData extends MutableLiveData<List<Post>> {
    public UserPostsData() {
        super();
        setValue(new ArrayList<>());
    }
}
