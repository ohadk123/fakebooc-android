package com.example.myapplication.models.repositories.comment.data;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.models.database.entities.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentsListData extends MutableLiveData<List<Comment>> {

    public CommentsListData() {
        super();
        setValue(new ArrayList<>());
    }
}
