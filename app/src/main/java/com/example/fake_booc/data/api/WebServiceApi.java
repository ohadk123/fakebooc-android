package com.example.fake_booc.data.api;

import com.example.fake_booc.model.LoginRequest;
import com.example.fake_booc.model.LoginResponse;
import com.example.fake_booc.model.RegisterServerResponse;
import com.example.fake_booc.model.UserCreationRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
// Define request and response models for user creation and JWT generation
public interface WebServiceApi {
    // Endpoint to create a new user
    @POST("/api/users")
    Call<RegisterServerResponse> createUser(@Body UserCreationRequest userCreationRequest);

    // Endpoint to generate JWT for an existing user
    @POST("/api/tokens")
    Call<LoginResponse> login(@Body LoginRequest jwtRequest);
}
