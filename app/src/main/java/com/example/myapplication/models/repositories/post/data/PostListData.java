package com.example.myapplication.models.repositories.post.data;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.models.database.entities.Post;
import com.example.myapplication.models.database.entities.PostList;

import java.util.ArrayList;
import java.util.List;

public class PostListData extends MutableLiveData<PostList> {

    public PostListData() {
        super();
        setValue(new PostList(new ArrayList<>()));
    }
}
