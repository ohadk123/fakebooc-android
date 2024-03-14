package com.example.myapplication.models.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.models.database.entities.User;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAPI {
    private MutableLiveData<List<String>> errorsListData;
    private MutableLiveData<String> token;
    private MutableLiveData<User> userInfo;
    private MutableLiveData<User> connectedUserData;
    private WebServiceApi webServiceApi;

    public UserAPI(MutableLiveData<List<String>> errorsListData, MutableLiveData<String> token, MutableLiveData<User> userInfo, MutableLiveData<User> connectedUserData) {
        this.errorsListData = errorsListData;
        this.token = token;
        this.userInfo = userInfo;
        this.connectedUserData = connectedUserData;
        webServiceApi = WebServiceApi.getInstance();
    }

    public void createUser(JsonObject registerBodyJson) {
        webServiceApi.createUser(registerBodyJson).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null)
                    Log.d("register", "Registered Successfully!");
                else if (response.errorBody() != null)
                    sendErrors(response);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("register", t.getMessage());
            }
        });
    }

    public void loginUser(JsonObject loginBodyJson) {
        webServiceApi.loginUser(loginBodyJson).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful() && response.body() != null)
                    token.postValue(response.body().get("token").getAsString());
                else if (response.errorBody() != null)
                    token.postValue(null);
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("register", t.getMessage());
            }
        });
    }

    public void getUserInfo(String username) {
        webServiceApi.getUserInfo(username).enqueue(new ObjectCallback<>(userInfo));
    }

    public void getConnectedUser() {
        webServiceApi.getUserInfo(TokenClient.getTokenUser()).enqueue(new ObjectCallback<>(connectedUserData));
    }

    public void updateUser(JsonObject updateUserBody) {
        String username = TokenClient.getTokenUser();
        webServiceApi.updateUser(username, updateUserBody).enqueue(new ObjectCallback<>(userInfo));
        getUserInfo(username);
    }

    public void deleteUser() {
        String username = TokenClient.getTokenUser();
        webServiceApi.deleteUser(username).enqueue(new ObjectCallback<>(userInfo));
    }

    private void sendErrors(Response<JsonObject> response) {
        try {
            JSONObject errorsJsonObject = new JSONObject(response.errorBody().string());
            JSONArray errorsJsonArray = errorsJsonObject.getJSONArray("errors");

            List<String> errorsList = new ArrayList<>();
            for (int i = 0; i < errorsJsonArray.length(); i ++) {
                errorsList.add(errorsJsonArray.getString(i));
                Log.d("test", errorsList.get(i));
            }

            errorsListData.postValue(errorsList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
