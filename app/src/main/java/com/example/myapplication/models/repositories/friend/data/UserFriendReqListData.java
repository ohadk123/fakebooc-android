package com.example.myapplication.models.repositories.friend.data;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class UserFriendReqListData extends MutableLiveData<List<String>> {

    public UserFriendReqListData() {
        super();
        setValue(new ArrayList<>());
    }
}
