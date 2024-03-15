package com.example.myapplication.models.api;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.models.database.entities.Post;
import com.example.myapplication.models.database.entities.PostList;
import com.google.gson.JsonObject;

import java.util.List;

public class PostAPI {
    private MutableLiveData<PostList> feedPostsData;
    private MutableLiveData<List<Post>> userPostsData;
    private MutableLiveData<Post> postData;
    public WebServiceApi webServiceApi;

    public PostAPI(MutableLiveData<PostList> postListData, MutableLiveData<Post> postData, MutableLiveData<List<Post>> userPostsData) {
        this.feedPostsData = postListData;
        this.postData = postData;
        this.userPostsData = userPostsData;
        webServiceApi = WebServiceApi.getInstance();
    }

    public void getPostsForFeed() {
        webServiceApi.getPostsForFeed().enqueue(new ObjectCallback<>(feedPostsData));
    }

    public void getUserPosts(String username) {
        webServiceApi.getUserPosts(username).enqueue(new ObjectCallback<>(userPostsData));
    }

    public void createPost(JsonObject createPostBody) {
        String username = TokenClient.getTokenUser();
        webServiceApi.createPost(username, createPostBody).enqueue(new ObjectCallback<>(postData));
    }

    public void updatePost(String pid, JsonObject updatePostBody) {
        String username = TokenClient.getTokenUser();
        webServiceApi.updatePost(username, pid, updatePostBody).enqueue(new ObjectCallback<>(postData));
    }

    public void deletePost(String pid) {
        String username = TokenClient.getTokenUser();
        webServiceApi.deletePost(username, pid).enqueue(new ObjectCallback<>(postData));
    }

    public void likePost(String pid) {
        webServiceApi.likePost(pid).enqueue(new ObjectCallback<>(postData));
    }

    public void unlikePost(String pid) {
        webServiceApi.unlikePost(pid).enqueue(new ObjectCallback<>(postData));
    }
}