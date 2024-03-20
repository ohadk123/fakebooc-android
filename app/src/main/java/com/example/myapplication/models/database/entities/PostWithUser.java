package com.example.myapplication.models.database.entities;

import java.util.List;

public class PostWithUser {
    private String _id;
    private String date;
    private User uploader;
    private String contentImage;
    private String content;
    private List<String> likes;

    public PostWithUser(String _id, String date, User uploader, String contentImage, String content, List<String> likes) {
        this._id = _id;
        this.date = date;
        this.uploader = uploader;
        this.contentImage = contentImage;
        this.content = content;
        this.likes = likes;
    }

    public PostWithUser(Post post, User uploader) {
        this._id = post.get_id();
        this.date = post.getDate();
        this.uploader = uploader;
        this.contentImage = post.getContentImage();
        this.content = post.getContent();
        this.likes = post.getLikes();
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    public String getContentImage() {
        return contentImage;
    }

    public void setContentImage(String contentImage) {
        this.contentImage = contentImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }
}
