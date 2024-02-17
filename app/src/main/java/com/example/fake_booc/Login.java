package com.example.fake_booc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private TextView incorrectLoginTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameEditText = findViewById(R.id.login_username);
        passwordEditText = findViewById(R.id.login_password);

        // Initialize TextView for displaying incorrect login message
        incorrectLoginTextView = findViewById(R.id.login_incorrect);

        final TextView signUpBtn=findViewById(R.id.signUpBtn);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
                finish();
            }
        });


        final TextView logInBtn=findViewById(R.id.logInBtn);
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comparePassword();
            }
        });
    }

    private boolean comparePassword() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        User tUser=User.getUserByUsername(username);
        // Check if user exists
        if(tUser==null) {
            incorrectLoginTextView.setVisibility(View.VISIBLE);
            return false;
        }

        // Check if password is correct
        if (password.equals(tUser.getPassword())) {
            tUser.signIn();
            startActivity(new Intent(Login.this, HomePageActivity.class));
            return true;
        }

        // Password is incorrect, show a message
        incorrectLoginTextView.setVisibility(View.VISIBLE);
        return false;
    }
}