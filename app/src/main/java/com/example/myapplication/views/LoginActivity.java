package com.example.myapplication.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.viewModels.UserViewModel;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private TextView incorrectLoginTextView;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        usernameEditText = findViewById(R.id.login_username);
        passwordEditText = findViewById(R.id.login_password);
        incorrectLoginTextView = findViewById(R.id.login_incorrect);

        final TextView signUpBtn = findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(view -> {
            Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(registerIntent);
        });

        final TextView logInBtn = findViewById(R.id.logInBtn);
        logInBtn.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            userViewModel.reqLoginUser(username, password);
        });

        userViewModel.getTokenData().observe(this, loginToken -> {
            if(loginToken==null) return;
            if (!loginToken.isEmpty()) {
                Log.d("login", loginToken);
                SharedPreferences sharedPreferences = MainActivity.context.getSharedPreferences("signed_in", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("token", loginToken);
                editor.putString("user", usernameEditText.getText().toString());
                editor.apply();
                Intent homePageIntent = new Intent(LoginActivity.this, HomePageActivity.class);
                startActivity(homePageIntent);
                finish();
            } else {
                incorrectLoginTextView.setVisibility(View.VISIBLE);
            }
        });
    }
}