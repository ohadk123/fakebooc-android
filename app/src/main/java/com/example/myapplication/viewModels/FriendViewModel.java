package com.example.myapplication.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.models.database.entities.FriendReq;
import com.example.myapplication.models.database.entities.Friends;
import com.example.myapplication.models.repositories.friend.FriendRepository;

import java.util.List;

public class FriendViewModel extends ViewModel {
    private LiveData<List<String>> userFriendsListData;
    private LiveData<List<String>> userFriendReqListData;
    private LiveData<Friends> friendsData;
    private LiveData<FriendReq> friendReqData;
    private FriendRepository friendRepository;

    public FriendViewModel() {
        friendRepository = new FriendRepository();
        this.userFriendsListData = friendRepository.getUserFriendsListData();
        this.userFriendReqListData = friendRepository.getUserFriendReqListData();
        this.friendsData = friendRepository.getFriendsData();
        this.friendReqData = friendRepository.getFriendReqData();
    }

    public LiveData<List<String>> getUserFriendsListData() {
        return userFriendsListData;
    }

    public LiveData<List<String>> getUserFriendReqListData() {
        return userFriendReqListData;
    }

    public LiveData<Friends> getFriendsData() {
        return friendsData;
    }

    public LiveData<FriendReq> getFriendReqData() {
        return friendReqData;
    }

    public void reqUserFriends(String username) {
        friendRepository.reqUserFriends(username);
    }

    public void reqUserFriendReqList(String username) {
        friendRepository.reqUserFriendReqList(username);
    }

    public void reqSendFriendReq(String receiver) {
        friendRepository.reqSendFriendReq(receiver);
    }

    public void reqAcceptFriendReq(String sender) {
        friendRepository.reqAcceptFriendReq(sender);
    }

    public void reqRemoveFriend(String other) {
        friendRepository.reqRemoveFriend(other);
    }
}
