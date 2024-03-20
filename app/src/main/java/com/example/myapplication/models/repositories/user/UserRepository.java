package com.example.myapplication.models.repositories.user;

import androidx.lifecycle.LiveData;

import com.example.myapplication.models.api.TokenClient;
import com.example.myapplication.models.api.UserAPI;
import com.example.myapplication.models.database.entities.User;
import com.example.myapplication.models.repositories.user.data.ConnectedUserData;
import com.example.myapplication.models.repositories.user.data.CreateUserData;
import com.example.myapplication.models.repositories.user.data.TokenData;
import com.example.myapplication.models.repositories.user.data.UserInfoData;
import com.example.myapplication.viewModels.UserViewModel;
import com.google.gson.JsonObject;

import java.util.List;

public class UserRepository {

    private CreateUserData createUserData;
    private TokenData tokenData;
    private ConnectedUserData connectedUserData;
    private UserInfoData userInfoData;
    private UserAPI userAPI;

    public UserRepository() {
        this.createUserData = new CreateUserData();
        this.tokenData = new TokenData();
        this.userInfoData = new UserInfoData();
        this.connectedUserData = new ConnectedUserData();
        userAPI = new UserAPI(createUserData, tokenData, userInfoData, connectedUserData);
    }

    public void reqCreateUser(JsonObject registerBodyJson) {
        userAPI.createUser(registerBodyJson);
    }

    public void reqLoginUser(JsonObject loginBodyJson) {
        userAPI.loginUser(loginBodyJson);
    }

    public void reqUserInfo(String username) {
        userAPI.getUserInfo(username);
    }

    public void reqUpdateUser(JsonObject updateUserBody) {
        userAPI.updateUser(updateUserBody);
    }

    public void reqDeleteUser() {
        userAPI.deleteUser();
    }

    public void reqConnectedUserInfo() {
        userAPI.getConnectedUser();
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

    public ConnectedUserData getConnectedUserData() {
        return connectedUserData;
    }
}