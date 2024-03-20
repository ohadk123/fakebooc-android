package com.example.myapplication.models.repositories.friend.data;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.models.database.entities.FriendReq;

public class FriendReqData extends MutableLiveData<FriendReq> {

    public FriendReqData() {
        super();
        setValue(null);
    }
}
