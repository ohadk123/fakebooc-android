package com.example.fake_booc;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private Uri userImage;
    private String password;
    private String email;

    private static List<User> registeredUsers = new ArrayList<>();
    private static User signedIn = getUserByEmail("ohad@email.com");

    public User(String name, Uri pfp, String password, String email) {
        this.username = name;
        this.userImage = pfp;
        this.password = password;
        this.email = email;
    }

    /**
     * Returns the user that signed in to the app
     * @return User SignedIn to app
     */
    public static User getSignedIn() {
        return signedIn;
    }

    public static void addUsers(List<User> users) {
        registeredUsers.addAll(users);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Uri getUserImage() {
        return userImage;
    }

    public void setUserImage(Uri userImage) {
        this.userImage = userImage;
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
        if (getUserByEmail(this.email) != null)
            return false;
        registeredUsers.add(this);
        return true;
    }

    /**
     * Finds a registerd user by email
     * @param email - user email to look for
     * @return A user object with the same email, null if not in list
     */
    public static User getUserByEmail(String email) {
        for (User u : registeredUsers) {
            if (email.equals(u.email)) {
                return u;
            }
        }
        return null;
    }

    /**
     * Returns a list of registered users with the specified username
     * @param username - username to look for
     * @return A list of users with attribute "username" == username, empty list if non exist
     */
    public static List<User> getUsersByName(String username) {
        List<User> usersFit = new ArrayList<>();
        for (User u : registeredUsers) {
            if (username.equals(u.username)) {
                usersFit.add(u);
            }
        }

        return usersFit;
    }

    public boolean isUserInList(List<User> usersList) {
        for (User u : usersList) {
            if (u.email.equals(this.email))
                return true;
        }
        return false;
    }

    public static List<User> getRegisteredUsers() {
        return registeredUsers;
    }

    public boolean isSignedIn() {
        return User.signedIn.email.equals(this.email);
    }
}
