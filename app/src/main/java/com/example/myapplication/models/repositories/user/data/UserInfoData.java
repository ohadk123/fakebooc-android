package com.example.myapplication.models.repositories.user.data;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.models.database.entities.User;

public class UserInfoData extends MutableLiveData<User> {

    public UserInfoData() {
        super();
        setValue(null);
    }
}
