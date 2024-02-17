package com.example.fake_booc;

import androidx.annotation.AnyRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        User.addUsers(createDefaultUsers());
        if (User.getSignedIn() == null)
            startActivity(new Intent(MainActivity.this, Login.class));
        else
            startActivity(new Intent(MainActivity.this, HomePageActivity.class));
    }

    public List<User> createDefaultUsers() {
        List<User> defaultUsers = new ArrayList<>();
        defaultUsers.add(new User("Ohad Katz", getUriToDrawable(R.drawable.ohad), "ohad1234", "ohad"));
        defaultUsers.add(new User("Adam Celer", getUriToDrawable(R.drawable.adam), "adam1234", "adam"));
        defaultUsers.add(new User("Gal Mansuryan", getUriToDrawable(R.drawable.gal), "gal1234", "gal"));
        return defaultUsers;
    }

    public Uri getUriToDrawable(@AnyRes int drawableId) {
        Context context = this.getApplicationContext();
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + context.getResources().getResourcePackageName(drawableId)
                + '/' + context.getResources().getResourceTypeName(drawableId)
                + '/' + context.getResources().getResourceEntryName(drawableId) );
    }
}
