package com.example.myapplication.models.api;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.models.database.entities.Comment;
import com.example.myapplication.models.database.entities.FriendReq;
import com.example.myapplication.models.database.entities.Friends;
import com.example.myapplication.models.database.entities.Post;
import com.example.myapplication.models.database.entities.PostList;
import com.example.myapplication.models.database.entities.User;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface WebServiceApi {
    @POST("users")
    Call<JsonObject> createUser(@Body JsonObject userCreationRequest);

    @POST("tokens")
    Call<JsonObject> loginUser(@Body JsonObject loginRequestBody);

    @GET("posts")
    Call<PostList> getPostsForFeed();

    @GET("users/{username}")
    Call<User> getUserInfo(@Path("username") String username);

    @PUT("users/{username}")
    Call<User> updateUser(@Path("username") String username, @Body JsonObject updateUserBody);

    @DELETE("users/{username}")
    Call<User> deleteUser(@Path("username") String username);

    @GET("users/{username}/posts")
    Call<List<Post>> getUserPosts(@Path("username") String username);

    @POST("users/{username}/posts")
    Call<Post> createPost(@Path("username") String username, @Body JsonObject createPostBody);

    @PUT("users/{username}/posts/{pid}")
    Call<Post> updatePost(@Path("username") String username, @Path("pid") String pid, @Body JsonObject updatePostBody);

    @DELETE("users/{username}/posts/{pid}")
    Call<Post> deletePost(@Path("username") String username, @Path("pid") String pid);

    @GET("users/{username}/friends")
    Call<List<String>> getUserFriends(@Path("username") String username);

    @GET("users/{username}/friends/requests")
    Call<List<String>> getUserFriendReqList(@Path("username") String username);

    @POST("users/{username}/friends")
    Call<FriendReq> sendFriendReq(@Path("username") String receiver);

    @PATCH("users/{username}/friends/{fUsername}")
    Call<Friends> acceptFriendReq(@Path("username") String receiver, @Path("fUsername") String sender);

    @DELETE("users/{username}/friends/{fUsername}")
    Call<Friends> removeFriend(@Path("username") String username, @Path("fUsername") String other);

    @POST("posts/{pid}/likes")
    Call<Post> likePost(@Path("pid") String pid);

    @DELETE("posts/{pid}/likes")
    Call<Post> unlikePost(@Path("pid") String pid);

    @POST("posts/{pid}/comments/{cid}/likes")
    Call<Comment> likeComment(@Path("pid") String pid, @Path("cid") String cid);

    @DELETE("posts/{pid}/comments/{cid}/likes")
    Call<Comment> unlikeComment(@Path("pid") String pid, @Path("cid") String cid);

    @GET("posts/{pid}/comments")
    Call<List<Comment>> getComments(@Path("pid") String pid);

    @POST("posts/{pid}/comments")
    Call<Comment> createComment(@Path("pid") String pid, @Body JsonObject createCommentBody);

    @PUT("posts/{pid}/comments/{cid}")
    Call<Comment> updateComment(@Path("pid") String pid, @Path("cid") String cid, @Body JsonObject updateCommentBody);

    @DELETE("posts/{pid}/comments/{cid}")
    Call<Comment> deleteComment(@Path("pid") String pid, @Path("cid") String cid);

    static WebServiceApi getInstance() {
        return new Retrofit.Builder().baseUrl(MainActivity.context.getString(R.string.BaseUrl))
                .client(TokenClient.getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WebServiceApi.class);
    }
}
