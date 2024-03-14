package com.example.myapplication.models.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.util.List;

public class Comment {
    private String _id;
    private String date;
    private String post;
    private String uploader;
    private String content;
    private List<String> likes;

    public String getDate() { return date; }
    public void setDate(String value) { this.date = value; }

    public String getPost() { return post; }
    public void setPost(String value) { this.post = value; }

    public String getUploader() { return uploader; }
    public void setUploader(String value) { this.uploader = value; }

    public String getCid() { return _id; }
    public void setCid(String value) { this._id = value; }

    public String getContent() { return content; }
    public void setContent(String value) { this.content = value; }

    public List<String> getLikes() { return likes; }
    public void setLikes(List<String> value) { this.likes = value; }
}
