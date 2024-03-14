package com.example.myapplication.models.repositories.comment.data;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.models.database.entities.Comment;

public class CommentData extends MutableLiveData<Comment> {

    public CommentData() {
        super();
        setValue(null);
    }
}
