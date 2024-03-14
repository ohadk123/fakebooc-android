package com.example.myapplication.models.database.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;
import androidx.room.TypeConverters;

import com.example.myapplication.models.database.entities.converters.LikesConverter;

import java.util.List;

@Entity
public class Post {
    @NonNull
    @PrimaryKey
    private String _id;
    private String date;
    private String uploader;
    private String contentImage;
    private String content;
    @TypeConverters({LikesConverter.class})
    private List<String> likes;

    public Post(String date, String uploader, String contentImage, String id, String content, List<String> likes) {
        this.date = date;
        this.uploader = uploader;
        this.contentImage = contentImage;
        this._id = id;
        this.content = content;
        this.likes = likes;
    }

    public String getDate() { return date; }
    public void setDate(String value) { this.date = value; }

    public String getUploader() { return uploader; }
    public void setUploader(String value) { this.uploader = value; }

    public String getContentImage() { return contentImage; }
    public void setContentImage(String value) { this.contentImage = value; }

    public String get_id() { return _id; }
    public void set_id(String value) { this._id = value; }

    public String getContent() { return content; }
    public void setContent(String value) { this.content = value; }

    public List<String> getLikes() { return likes; }
    public void setLikes(List<String> value) { this.likes = value; }
}
