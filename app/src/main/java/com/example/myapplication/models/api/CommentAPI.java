package com.example.myapplication.models.api;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.models.database.entities.Comment;
import com.google.gson.JsonObject;

import java.util.List;

public class CommentAPI {
    private MutableLiveData<List<Comment>> comments;
    private MutableLiveData<Comment> commentData;
    private WebServiceApi webServiceApi;

    public CommentAPI(MutableLiveData<List<Comment>> comments, MutableLiveData<Comment> commentData) {
        this.comments = comments;
        this.commentData = commentData;
        webServiceApi = WebServiceApi.getInstance();
    }

    public void getComments(String pid) {
        webServiceApi.getComments(pid).enqueue(new ObjectCallback<>(comments));
    }

    public void createComment(String pid, JsonObject createCommentBody) {
        webServiceApi.createComment(pid, createCommentBody).enqueue(new ObjectCallback<>(commentData));
    }

    public void updateComment(String pid, String cid, JsonObject updateCommentBody) {
        webServiceApi.updateComment(pid, cid, updateCommentBody).enqueue(new ObjectCallback<>(commentData));
    }

    public void deleteComment(String pid, String cid) {
        webServiceApi.deleteComment(pid, cid).enqueue(new ObjectCallback<>(commentData));
    }

    public void likeComment(String pid, String cid) {
        webServiceApi.likeComment(pid, cid).enqueue(new ObjectCallback<>(commentData));
    }

    public void unlikeComment(String pid, String cid) {
        webServiceApi.unlikeComment(pid, cid).enqueue(new ObjectCallback<>(commentData));
    }
}
