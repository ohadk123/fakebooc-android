package com.example.fake_booc;

import java.util.ArrayList;
import java.util.List;

public class Likeable {
    protected  List<User> likedBy = new ArrayList<>();

    public int getLikes() {
        return likedBy.size();
    }
    public void removeLike(User user) {
        this.likedBy.remove(user);
    }

    public void addLike(User user) {
        this.likedBy.add(user);
    }

    public List<User> getLikedBy() {
        return likedBy;
    }
}
