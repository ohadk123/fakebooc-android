package com.example.fake_booc.ui.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.fake_booc.MyApplication;
import com.example.fake_booc.R;
import com.example.fake_booc.ui.viewsmodels.LoginViewModel;

public class Login extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private TextView incorrectLoginTextView;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize ViewModel
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        usernameEditText = findViewById(R.id.login_username);
        passwordEditText = findViewById(R.id.login_password);
        incorrectLoginTextView = findViewById(R.id.login_incorrect);

        final TextView signUpBtn = findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(view -> {
            startActivity(new Intent(Login.this, Register.class));
            finish();
        });

        final TextView logInBtn = findViewById(R.id.logInBtn);
        logInBtn.setOnClickListener(view -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            loginViewModel.login(username, password);
        });

//         Observe the LiveData from ViewModel
        loginViewModel.getLoginToken().observe(this, loginToken -> {
            if (loginToken != null && !loginToken.isEmpty()) {
                // Login was successful, print the token
                System.out.println("Login successful, token: " + loginToken);

                SharedPreferences sharedPreferences = MyApplication.context.getSharedPreferences("signed_in", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("token", loginToken);
                editor.apply();

                // Optionally, navigate to the HomePageActivity or handle the successful login as needed
                // startActivity(new Intent(Login.this, HomePageActivity.class));
            } else {
                // Login failed, show incorrect login text view
                incorrectLoginTextView.setVisibility(View.VISIBLE);
            }
        });

    }
}
