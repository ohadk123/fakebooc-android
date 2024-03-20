package com.example.myapplication.models.repositories.post.data;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.models.database.entities.Post;

public class PostData extends MutableLiveData<Post> {

    public PostData() {
        super();
        setValue(null);
    }
}
