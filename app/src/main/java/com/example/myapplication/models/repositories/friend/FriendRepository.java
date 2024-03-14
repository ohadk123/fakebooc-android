package com.example.myapplication.models.repositories.friend;

import androidx.lifecycle.LiveData;

import com.example.myapplication.models.api.FriendAPI;
import com.example.myapplication.models.database.entities.FriendReq;
import com.example.myapplication.models.database.entities.Friends;
import com.example.myapplication.models.repositories.friend.data.FriendReqData;
import com.example.myapplication.models.repositories.friend.data.FriendsData;
import com.example.myapplication.models.repositories.friend.data.UserFriendReqListData;
import com.example.myapplication.models.repositories.friend.data.UserFriendsData;

import java.util.List;

public class FriendRepository {
    private UserFriendsData userFriendsListData;
    private UserFriendReqListData userFriendReqListData;
    private FriendsData friendsData;
    private FriendReqData friendReqData;
    private FriendAPI friendAPI;

    public FriendRepository() {
        this.userFriendsListData = new UserFriendsData();
        this.userFriendReqListData = new UserFriendReqListData();
        this.friendsData = new FriendsData();
        this.friendReqData = new FriendReqData();
        this.friendAPI = new FriendAPI(userFriendsListData, userFriendReqListData, friendsData, friendReqData);
    }

    public UserFriendsData getUserFriendsListData() {
        return userFriendsListData;
    }

    public UserFriendReqListData getUserFriendReqListData() {
        return userFriendReqListData;
    }

    public FriendsData getFriendsData() {
        return friendsData;
    }

    public FriendReqData getFriendReqData() {
        return friendReqData;
    }

    public void reqUserFriends(String username) {
        friendAPI.getUserFriends(username);
    }

    public void reqUserFriendReqList(String username) {
        friendAPI.getUserFriendReqList(username);
    }

    public void reqSendFriendReq(String receiver) {
        friendAPI.sendFriendReq(receiver);
    }

    public void reqAcceptFriendReq(String sender) {
        friendAPI.acceptFriendReq(sender);
    }

    public void reqRemoveFriend(String other) {
        friendAPI.removeFriend(other);
    }
}
