package com.example.myapplication.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.models.database.entities.Comment;
import com.example.myapplication.models.repositories.comment.CommentRepository;
import com.google.gson.JsonObject;

import java.util.List;

public class CommentViewModel extends ViewModel {
    private LiveData<List<Comment>> commentListData;
    private LiveData<Comment> commentData;
    private CommentRepository commentRepository;

    public CommentViewModel() {
        this.commentRepository = new CommentRepository();
        this.commentListData = commentRepository.getCommentListData();
        this.commentData = commentRepository.getCommentData();
    }

    public LiveData<List<Comment>> getCommentListData() {
        return commentListData;
    }

    public LiveData<Comment> getCommentData() {
        return commentData;
    }

    public void reqGetComments(String pid) {
        commentRepository.reqGetComments(pid);
    }

    public void reqCreateComment(String pid, JsonObject createCommentBody) {
        commentRepository.reqCreateComment(pid, createCommentBody);
    }

    public void reqUpdateComment(String pid, String cid, JsonObject updateCommentBody) {
        commentRepository.reqUpdateComment(pid, cid, updateCommentBody);
    }

    public void reqDeleteComment(String pid, String cid) {
        commentRepository.reqDeleteComment(pid, cid);
    }

    public void reqLikeComment(String pid, String cid) {
        commentRepository.reqLikeComment(pid, cid);
    }

    public void reqUnlikeComment(String pid, String cid) {
        commentRepository.reqUnlikeComment(pid, cid);
    }
}
