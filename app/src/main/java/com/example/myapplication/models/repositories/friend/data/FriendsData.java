package com.example.myapplication.models.repositories.friend.data;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.models.database.entities.Friends;

public class FriendsData extends MutableLiveData<Friends> {

    public FriendsData() {
        super();
        setValue(null);
    }
}
