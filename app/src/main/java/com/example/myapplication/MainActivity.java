package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.models.database.entities.User;
import com.example.myapplication.viewModels.UserViewModel;
import com.example.myapplication.views.HomePageActivity;
import com.example.myapplication.views.LoginActivity;
import com.example.myapplication.views.RegisterActivity;

public class MainActivity extends AppCompatActivity {
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
}
