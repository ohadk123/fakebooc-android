package com.example.myapplication.models.repositories.post;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.models.api.PostAPI;
import com.example.myapplication.models.database.AppDB;
import com.example.myapplication.models.database.daos.PostDao;
import com.example.myapplication.models.database.entities.Post;
import com.example.myapplication.models.database.entities.PostList;
import com.example.myapplication.models.repositories.post.data.PostData;
import com.example.myapplication.models.repositories.post.data.UserPostsData;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class PostRepository {
    private PostListData postListData;
    private UserPostsData userPostsData;
    private PostData postData;
    private PostDao postDao;
    private PostAPI postAPI;

    public PostRepository() {
        this.postListData = new PostListData();
        this.userPostsData = new UserPostsData();
        this.postData = new PostData();
        this.postDao = AppDB.getInstance().postDao();
        this.postAPI = new PostAPI(postListData, postData, userPostsData, postDao);
    }

    public LiveData<PostList> getPostListData() {
        return postListData;
    }

    public UserPostsData getUserPostsData() {
        return userPostsData;
    }

    public LiveData<Post> getPostData() {
        return postData;
    }

    public void reqPostsForFeed() {
        postAPI.getPostsForFeed();
    }

    public void reqUserPosts(String username) {
        postAPI.getUserPosts(username);
    }

    public void reqCreatePost(JsonObject createPostBody) {
        postAPI.createPost(createPostBody);
    }

    public void reqUpdatePost(String pid, JsonObject updatePostBody) {
        postAPI.updatePost(pid, updatePostBody);
    }

    public void reqDeletePost(String pid) {
        postAPI.deletePost(pid);
    }

    public void reqLikePost(String pid) {
        postAPI.likePost(pid);
    }

    public void reqUnlikePost(String pid) {
        postAPI.unlikePost(pid);
    }

    class PostListData extends MutableLiveData<PostList> {

        public PostListData() {
            super();
            setValue(new PostList(new ArrayList<>()));
        }

        @Override
        protected void onActive() {
            super.onActive();

            new Thread(() -> postListData.postValue(new PostList(postDao.getPosts()))).start();
        }
    }
}
