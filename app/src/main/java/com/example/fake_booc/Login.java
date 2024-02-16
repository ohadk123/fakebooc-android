package com.example.fake_booc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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


    //todo implement password db
    private boolean comparePassword() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        //todo check username in shared pref match password  then add to user signed in
        // Compare the password

        User tUser=User.getUserByEmail(username);
        if(tUser==null){
            Log.d(password, "comparePassword: ");
            // Password is incorrect, show a message
            incorrectLoginTextView.setVisibility(View.VISIBLE);
            return false;
        }
        if (password.equals(tUser.getPassword())) {
            tUser.signIn();
            startActivity(new Intent(Login.this, HomePageActivity.class));
            return true;
            // Password is correct, do something (e.g., navigate to another activity)


        }else{
            Log.d(password, "comparePassword: ");
            // Password is incorrect, show a message
            incorrectLoginTextView.setVisibility(View.VISIBLE);
            return false;
        }


    }

}