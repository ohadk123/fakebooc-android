package com.example.fake_booc;

public class Comment extends Likeable {
    private User originalCommenter;
    private String content;

    public Comment(User user, String content) {
        this.originalCommenter = user;
        this.content = content;
    }

    public User getOriginalCommenter() {
        return originalCommenter;
    }

    public void setOriginalCommenter(User originalCommenter) {
        this.originalCommenter = originalCommenter;
    }

    public String getCommentContent() {
        return content;
    }

    public void setCommentContent(String content) {
        this.content = content;
    }
}
