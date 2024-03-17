package com.example.myapplication.models.api;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.models.database.entities.FriendReq;
import com.example.myapplication.models.database.entities.Friends;

import java.util.List;

public class FriendAPI {
    private MutableLiveData<List<String>> userFriendsListData;
    private MutableLiveData<List<String>> userFriendReqListData;
    private MutableLiveData<Friends> friendsData;
    private MutableLiveData<FriendReq> friendReq;
    private WebServiceApi webServiceApi;

    public FriendAPI(MutableLiveData<List<String>> userFriendsListData, MutableLiveData<List<String>> userFriendReqListData, MutableLiveData<Friends> friendsData, MutableLiveData<FriendReq> friendReq) {
        this.userFriendsListData = userFriendsListData;
        this.userFriendReqListData = userFriendReqListData;
        this.friendReq = friendReq;
        this.friendsData = friendsData;
        webServiceApi = WebServiceApi.getInstance();
    }

    public void getUserFriends(String username) {
        webServiceApi.getUserFriends(username).enqueue(new ObjectCallback<>(userFriendsListData));
    }

    public void getUserFriendReqList(String username) {
        webServiceApi.getUserFriendReqList(username).enqueue(new ObjectCallback<>(userFriendReqListData));
    }

    public void sendFriendReq(String receiver) {
        webServiceApi.sendFriendReq(receiver).enqueue(new ObjectCallback<>(friendReq));
    }

    public void acceptFriendReq(String sender) {
        String receiver = TokenClient.getTokenUser();
        webServiceApi.acceptFriendReq(receiver, sender).enqueue(new ObjectCallback<>(friendsData));
        getUserFriends(receiver);
        getUserFriendReqList(receiver);
    }

    public void removeFriend(String other) {
        String username = TokenClient.getTokenUser();
        webServiceApi.removeFriend(username, other).enqueue(new ObjectCallback<>(friendsData));
        getUserFriends(username);
    }
}
