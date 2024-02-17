package com.example.fake_booc;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String displayName;
    private Uri userImage;
    private String password;
    private String username;

    private static List<User> registeredUsers = new ArrayList<>();
    private static User signedIn;

    public User(String name, Uri pfp, String password, String username) {
        this.displayName = name;
        this.userImage = pfp;
        this.password = password;
        this.username = username;
    }

    /**
     * Returns the user that signed in to the app
     * @return User SignedIn to app
     */
    public static User getSignedIn() {
        return signedIn;
    }

    /**
     * Add a List of users object to the registered users List
     * @param users - List of user to add
     */
    public static void addUsers(List<User> users) {
        registeredUsers.addAll(users);
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Uri getUserImage() {
        return userImage;
    }

    /**
     * Logs out the user
     */
    public static void logOut() {
        signedIn = null;
    }

    /**
     * Signs in the user
     * @return true if sign in is available (signedIn is null), false otherwise
     */
    public boolean signIn() {
        if (signedIn == null) {
            signedIn = this;
            return true;
        }
        return false;
    }

    /**
     * Signs up the user
     * @return true if user is not already signed up, false otherwise
     */
    public boolean signUp() {
        if (getUserByUsername(this.username) != null)
            return false;
        registeredUsers.add(this);
        return true;
    }

    /**
     * Finds a registerd user by email
     * @param username - user email to look for
     * @return A user object with the same email, null if not in list
     */
    public static User getUserByUsername(String username) {
        for (User u : registeredUsers) {
            if (username.equals(u.username)) {
                return u;
            }
        }
        return null;
    }

    public boolean isUserInList(List<User> usersList) {
        for (User u : usersList) {
            if (u.username.equals(this.username))
                return true;
        }
        return false;
    }

    public boolean isSignedIn() {
        return User.signedIn.username.equals(this.username);
    }
}
