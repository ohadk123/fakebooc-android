package com.example.myapplication.models.repositories.user.data;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.models.database.entities.User;

public class ConnectedUserData extends MutableLiveData<User> {
    public ConnectedUserData() {
        super();
        setValue(new User());
    }
}
