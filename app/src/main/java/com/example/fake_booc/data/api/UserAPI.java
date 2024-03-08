package com.example.fake_booc.data.api;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.fake_booc.model.LoginRequest;
import com.example.fake_booc.model.LoginResponse;
import com.example.fake_booc.model.RegisterServerResponse;
import com.example.fake_booc.model.UserCreationRequest;
import com.example.fake_booc.repository.UserRepository;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class UserAPI {

     Retrofit retrofit;
    WebServiceApi webServiceApi;
    public UserAPI(){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        webServiceApi=retrofit.create(WebServiceApi.class);
    }

    public void login(String username, String password, UserRepository.SimpleLoginCallback callback){
        String answer;
        String tst="tst";
        Log.d(tst, "login: "+username+"  "+password);

        Call<LoginResponse> call= webServiceApi.login(new LoginRequest(username,password));
        Log.d(tst, call.toString());

        call.enqueue(new Callback<LoginResponse>(){

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful() && response.body() != null) {
                    callback.onResult(true, response.body().getToken());
                } else {
                    callback.onResult(false, null);
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d(tst, t.toString());
            }
        });

    }



    public void createUser(String username, String displayName, String password,String cPassword, String  imageB64, @NonNull UserRepository.RegistrationCallback callback){
        String tst="tst";


        Call <RegisterServerResponse> call= webServiceApi.createUser(new UserCreationRequest(username,displayName,password,cPassword,imageB64));

        call.enqueue(new Callback<RegisterServerResponse>() {
            private boolean registrationCompleted = false; // Add this flag
            @Override

            public void onResponse(Call<RegisterServerResponse> call, Response<RegisterServerResponse> response) {
                if (!registrationCompleted) { // Check if registration is already completed
                    if (response.isSuccessful() && response.body() != null) {
                        RegisterServerResponse serverResponse = response.body();
                        if (serverResponse.isSuccess()) {
                            Log.d(tst, "Registration successful");
                            // Invoke callback for successful registration
                            registrationCompleted = true; // Update the flag
                            callback.onRegistrationComplete(true,"");
                        } else {
                            // Handle case where isSuccess is false but request technically succeeded
                            Log.d(tst, "Registration failed: " + serverResponse.getErrors());
                            registrationCompleted = true; // Update the flag
                            callback.onRegistrationComplete(false,"");
                        }
                    } else {
                        // Server responded with error status code (e.g., 409 Conflict)
                        Log.d(tst, "Server responded with failure: " + response.code());
                        if (response.errorBody() != null) {
                            try {
                                // Attempt to parse error body if present
                                String errorString = response.errorBody().string();
                                Log.d(tst, "Error body: " + errorString);
                                // Handle error condition, possibly by displaying error to the user
                                registrationCompleted = true; // Update the flag
                                callback.onRegistrationComplete(false,errorString);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            registrationCompleted = true; // Update the flag
                            callback.onRegistrationComplete(false,"");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterServerResponse> call, Throwable t) {
                Log.d(tst, "Registration call failed: " + t.getMessage());
                // Notify caller of failure, possibly due to network issues
                callback.onRegistrationComplete(false,"");
            }
        });


    }
}


