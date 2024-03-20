package com.example.myapplication.models.api;

import android.content.Context;

import com.example.myapplication.MainActivity;
import com.example.myapplication.viewModels.UserViewModel;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TokenClient {
    private static OkHttpClient client = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + UserViewModel.getConnectedUser())
                    .build();
            return chain.proceed(newRequest);
        }
    })
            .build();

    public static OkHttpClient getClient() {
        return client;
    }

    public static String getTokenUser() {
        return MainActivity.context.getSharedPreferences("signed_in", Context.MODE_PRIVATE).getString("user", "");
    }
}
