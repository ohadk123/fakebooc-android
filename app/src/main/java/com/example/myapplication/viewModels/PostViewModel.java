package com.example.myapplication.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.models.database.entities.Post;
import com.example.myapplication.models.database.entities.PostList;
import com.example.myapplication.models.database.entities.User;
import com.example.myapplication.models.repositories.post.PostRepository;
import com.example.myapplication.models.repositories.user.UserRepository;
import com.google.gson.JsonObject;

import java.util.List;

public class PostViewModel extends ViewModel {
    private LiveData<PostList> feedPostsData;
    private LiveData<Post> postData;
    private PostRepository postRepository;
    private LiveData<List<Post>> userPostsData;


    public PostViewModel() {
        postRepository = new PostRepository();
        this.feedPostsData = postRepository.getPostListData();
        this.postData = postRepository.getPostData();
        this.userPostsData = postRepository.getUserPostsData();

    }

    public LiveData<PostList> getFeedPostsData() {
        return feedPostsData;
    }

    public LiveData<Post> getPostData() {
        return postData;
    }

    public void reqPostsForFeed() {
        postRepository.reqPostsForFeed();
    }

    public void reqUserPosts(String username) {
        postRepository.reqUserPosts(username);
    }

    public void reqCreatePost(String content, String contentImage) {
        JsonObject createPostBody = new JsonObject();
        createPostBody.addProperty("content", content);
        createPostBody.addProperty("contentImage", contentImage);
        postRepository.reqCreatePost(createPostBody);
    }

    public void reqUpdatePost(String pid, String content, String contentImage) {
        JsonObject updatePostBody = new JsonObject();
        updatePostBody.addProperty("content", content);
        updatePostBody.addProperty("contentImage", contentImage);
        postRepository.reqUpdatePost(pid, updatePostBody);
    }

    public void reqDeletePost(String pid) {
        postRepository.reqDeletePost(pid);
    }

    public void reqLikePost(String pid) {
        postRepository.reqLikePost(pid);
    }

    public void reqUnlikePost(String pid) {
        postRepository.reqUnlikePost(pid);
    }

    private JsonObject getJsonForPost(String content, String contentImage) {
        JsonObject jsonPostBody = new JsonObject();
        jsonPostBody.addProperty("content", content);
        jsonPostBody.addProperty("contentImage", contentImage);
        return jsonPostBody;
    }
    public LiveData<List<Post>> getUserPostsData() {
        return userPostsData;
    }
}
