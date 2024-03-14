package com.example.myapplication.viewModels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.MainActivity;
import com.example.myapplication.models.database.entities.User;
import com.example.myapplication.models.repositories.user.UserRepository;
import com.google.gson.JsonObject;

import java.util.List;

public class UserViewModel extends ViewModel {
    private LiveData<List<String>> createUserData;
    private LiveData<String> tokenData;
    private LiveData<User> userInfoData;
    private LiveData<User> connectedUserData;
    private UserRepository userRepository;

    public UserViewModel() {
        userRepository = new UserRepository();
        this.tokenData = userRepository.getTokenData();
        this.createUserData = userRepository.getCreateUserData();
        this.userInfoData = userRepository.getUserInfoData();
        this.connectedUserData = userRepository.getConnectedUserData();
        this.createUserData = userRepository.getCreateUserData();
    }

    public void reqCreateUser(String username, String displayName, String password, String cPassword, String imageB64) {
        JsonObject registerBodyJson = new JsonObject();
        registerBodyJson.addProperty("username", username);
        registerBodyJson.addProperty("displayName", displayName);
        registerBodyJson.addProperty("password", password);
        registerBodyJson.addProperty("cPassword", imageB64);
        registerBodyJson.addProperty("imageB64", imageB64);
        userRepository.reqCreateUser(registerBodyJson);
    }

    public void reqLoginUser(String username, String password) {
        JsonObject loginBodyJson = new JsonObject();
        loginBodyJson.addProperty("username", username);
        loginBodyJson.addProperty("password", password);
        userRepository.reqLoginUser(loginBodyJson);
    }

    public void reqUserInfo(String username) {
        userRepository.reqUserInfo(username);
    }

    public void reqConnectedUserInfo() {
        userRepository.reqConnectedUserInfo();
    }

    public void reqUpdateUser(String displayName, String profileImage) {
        JsonObject updateUserBody = new JsonObject();
        updateUserBody.addProperty("displayName", displayName);
        updateUserBody.addProperty("profileImage", profileImage);
        userRepository.reqUpdateUser(updateUserBody);
    }

    public void reqDeleteUser() {
        userRepository.reqDeleteUser();
    }

    public LiveData<User> getPostUser(String username) {
        UserRepository tempRepo = new UserRepository();
        tempRepo.reqUserInfo(username);
        return tempRepo.getUserInfoData();
    }

    public LiveData<List<String>> getCreateUserData() {
        return createUserData;
    }

    public LiveData<String> getTokenData()  {
        return tokenData;
    }

    public LiveData<User> getUserInfoData() {
        return userInfoData;
    }

    public LiveData<User> getConnectedUserData() {
        return connectedUserData;
    }

    public static String getConnectedUser() {
        return MainActivity.context.getSharedPreferences("signed_in", Context.MODE_PRIVATE)
                .getString("token", "");
    }
}
