package com.example.fake_booc;

import android.net.Uri;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Post extends Likeable {
    private User originalPoster;
    private String postText;
    private Uri postImage;
    private LocalDate uploadDate = LocalDate.now();
    private List<Comment> commmentsList = new ArrayList<>();
    public static List<Post> posts = new ArrayList<>();

    public Post(String postText, Uri postImage, User user) {
        this.postText = postText;
        this.postImage = postImage;
        this.originalPoster = user;
    }

    public Post(String postText, User user) {
        this.postText = postText;
        this.postImage = Uri.fromFile(new File("I:\\android\\app\\src\\main\\res\\drawable\\empty_image.png"));
        this.originalPoster = user;
    }

    public User getOriginalPoster() {
        return this.originalPoster;
    }

    public Uri getPostImage() {
        return postImage;
    }

    public void setPostImage(Uri postImage) {
        this.postImage = postImage;
    }

    public LocalDate getUploadDate() {
        return uploadDate;
    }

    public List<Comment> getCommmentsList() {
        return commmentsList;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public void addComment(Comment comment) {
        commmentsList.add(comment);
    }
}
