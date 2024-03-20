package com.example.myapplication.models.repositories.friend.data;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class UserFriendsData extends MutableLiveData<List<String>> {

    public UserFriendsData() {
        super();
        setValue(new ArrayList<>());
    }
}
