
package com.example.fake_booc.repository;

import androidx.annotation.NonNull;

import com.example.fake_booc.data.api.UserAPI;

public class UserRepository {

    public UserRepository() {

    }
    public void login(String username, String password,SimpleLoginCallback callback) {
        UserAPI userAPI=new UserAPI();
        userAPI.login(username,password,callback);

    }


    public void registerUser(String username, String displayName, String password,String cPassword, String  imageB64, @NonNull RegistrationCallback callback) {

        UserAPI userAPI=new UserAPI();
        userAPI.createUser(username,displayName,password,cPassword,imageB64,callback);
        return;
    }

    public interface RegistrationCallback {
        void onRegistrationComplete(boolean status, String errors);
    }


    public interface SimpleLoginCallback {
        void onResult(boolean isSuccess, String token);
    }
}
