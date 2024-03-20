package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.models.api.TokenClient;
import com.example.myapplication.views.HomePageActivity;
import com.example.myapplication.views.LoginActivity;

public class MainActivity extends AppCompatActivity {
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        startActivity(new Intent(MainActivity.this, TokenClient.getTokenUser().isEmpty() ?
                LoginActivity.class : HomePageActivity.class));
        finish();
    }
}
