package com.example.myapplication.models.repositories.comment;

import androidx.lifecycle.LiveData;

import com.example.myapplication.models.api.CommentAPI;
import com.example.myapplication.models.database.entities.Comment;
import com.example.myapplication.models.repositories.comment.data.CommentsListData;
import com.example.myapplication.models.repositories.comment.data.CommentData;
import com.google.gson.JsonObject;

import java.util.List;

public class CommentRepository {
    private CommentsListData commentListData;
    private CommentData commentData;
    private CommentAPI commentAPI;

    public CommentRepository() {
        this.commentListData = new CommentsListData();
        this.commentData = new CommentData();
        this.commentAPI = new CommentAPI(commentListData, commentData);
    }

    public LiveData<List<Comment>> getCommentListData() {
        return commentListData;
    }

    public LiveData<Comment> getCommentData() {
        return commentData;
    }

    public void reqGetComments(String pid) {
        commentAPI.getComments(pid);
    }

    public void reqCreateComment(String pid, JsonObject createCommentBody) {
        commentAPI.createComment(pid, createCommentBody);
    }

    public void reqUpdateComment(String pid, String cid, JsonObject updateCommentBody) {
        commentAPI.updateComment(pid, cid, updateCommentBody);
    }

    public void reqDeleteComment(String pid, String cid) {
        commentAPI.deleteComment(pid, cid);
    }

    public void reqLikeComment(String pid, String cid) {
        commentAPI.likeComment(pid, cid);
    }

    public void reqUnlikeComment(String pid, String cid) {
        commentAPI.unlikeComment(pid, cid);
    }
}