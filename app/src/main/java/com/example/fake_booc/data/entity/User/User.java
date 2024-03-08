package com.example.fake_booc.data.entity.User;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @PrimaryKey @NonNull
    private String id;
    private String displayName;
    private String profileImage;
    private String password;
    // Ignoring friends and friendRequests for direct database storage, handle through type converters
    @Ignore
    private List<User> friends;
    @Ignore
    private List<User> friendRequests;

    // Default constructor required for Room
    public User() {
    }

    public User(String id, String displayName, String profileImage, String password) {
        this.id = id;
        this.displayName = displayName;
        this.profileImage = profileImage;
        this.password = password;
        this.friends = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
    }

    // Constructor to create User from JSONObject
    public User(JSONObject jsonObject) {
        // Parse the jsonObject to set the properties of User
        this.id = jsonObject.optString("id");
        this.displayName = jsonObject.optString("displayName");
        this.profileImage = jsonObject.optString("profileImage");
        this.password = jsonObject.optString("password");
        // For friends and friendRequests, you would need to parse them if they are included in the jsonObject
        this.friends = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
    }

    // Convert User object to JSONObject
    public JSONObject toJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", this.id);
            jsonObject.put("displayName", this.displayName);
            jsonObject.put("profileImage", this.profileImage);
            jsonObject.put("password", this.password);
            // Handling of friends and friendRequests is more complex and would ideally be handled separately
            // since it involves nested serialization/deserialization
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getPassword() {
        return password;
    }

    public List<User> getFriends() {
        return friends;
    }

    public List<User> getFriendRequests() {
        return friendRequests;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public void setFriendRequests(List<User> friendRequests) {
        this.friendRequests = friendRequests;
    }

    public void addFriend(User friend) {
        friends.add(friend);
    }

    public void removeFriend(User friend) {
        friends.remove(friend);
    }

    public void addFriendRequest(User requester) {
        friendRequests.add(requester);
    }

    public void removeFriendRequest(User requester) {
        friendRequests.remove(requester);
    }
}
