package com.example.myapplication.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.ImageHolder;
import com.example.myapplication.InBetweenHolder;
import com.example.myapplication.R;
import com.example.myapplication.models.database.entities.Post;
import com.example.myapplication.models.database.entities.User;
import com.example.myapplication.viewModels.FriendViewModel;
import com.example.myapplication.viewModels.PostViewModel;
import com.example.myapplication.viewModels.UserViewModel;
import com.example.myapplication.views.adapters.PostAdapter;

import java.util.List;

public class ProfilePageActivity extends AppCompatActivity {

    private RecyclerView postsRecyclerView;
    private ImageView profileImageView;
    private String profile_username;
    private String profile_displayName;

    private String ConnectedUsername;
    private User connectedUser;
    private TextView profileNameTextView;
    private Button friendStatusButton;
    private Button updateAccountButton;
    private UserViewModel userViewModel;
    private PostViewModel postViewModel;
    private FriendViewModel friendViewModel;
    int connectedFlag=0;
    int closedShit=0;
    int firstReqFlag=0;
    int secondReqFlag=0;
    int thirdReqFlag=0;
private SwipeRefreshLayout srl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_page);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        friendViewModel=new ViewModelProvider(this).get(FriendViewModel.class);

        // Initialize views
        postsRecyclerView = findViewById(R.id.profilePagePostRecyclerView);
        profileImageView = findViewById(R.id.profilePageImage);
        profileNameTextView = findViewById(R.id.profilePageName);
        friendStatusButton = findViewById(R.id.profilePageFriendStatusBtn);
        updateAccountButton = findViewById(R.id.profilePageUpdateAccountBtn);
        srl=findViewById(R.id.ProfilePageRefresh);

        TextView emptyView = findViewById(R.id.recycler_textView);
        emptyView.setVisibility(View.INVISIBLE);
        userViewModel.reqConnectedUserInfo();

        connectedUser=UserViewModel.connectedUser;
        ConnectedUsername=connectedUser.getUsername();

        setExtraIntent();

            if (profile_username != null) {
                    updateUI();
                if(connectedFlag==0) {
                    checkForPendingRequests();
                    setFriendStatusButton();
                }
                }

        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("test", "refresh");

                // Delay to simulate refresh operation
                srl.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Code to restart the activity with the same intent and extras
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);

                        // Remember to call setRefreshing(false) to signal the end of the refresh
                        srl.setRefreshing(false);
                    }
                }, 2000); // Delay of 2 seconds to simulate refresh operation
            }
        });





    }

    private void reecreate(){

        srl.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Code to restart the activity with the same intent and extras
                Intent intent = getIntent();
                finish();
                startActivity(intent);

                // Remember to call setRefreshing(false) to signal the end of the refresh
                srl.setRefreshing(false);
            }
        }, 2000); // Delay of 2 seconds to simulate refresh operation

    }

    private void updateUI() {
        // Set displayName and profileImage
        profileNameTextView.setText(profile_displayName);

        // Handle visibility and text of updateAccountBtn based on isConnected
        if(!profile_username.equals(ConnectedUsername)) {
            updateAccountButton.setVisibility(View.GONE);

        } else {
            setUpRecycleView();
            connectedFlag=1;
            updateAccountButton.setVisibility(View.VISIBLE);
            friendStatusButton.setText("Delete Account");
            // Set click listener for Delete Account
            friendStatusButton.setOnClickListener(v -> deleteAcc());
            updateAccountButton.setOnClickListener(v -> updateAcc());
        }

    }


    private void setExtraIntent() {
            profile_username = InBetweenHolder.username;
            profile_displayName =InBetweenHolder.displayName;
        if (ImageHolder.userImageDrawable != null) {
            profileImageView.setImageDrawable(ImageHolder.userImageDrawable);
        }
    }


    private void setFriendStatusButton() {

        TextView emptyView = findViewById(R.id.recycler_textView);
        // Observe the list of friends to check if ConnectedUsername and username are friends
        friendViewModel.reqUserFriends(ConnectedUsername);
        friendViewModel.getUserFriendsListData().observe(this, friends -> {
            if(firstReqFlag==0){
                firstReqFlag=1;
                return;
            }

            if(firstReqFlag>1) return;
            if (friends.contains(profile_username)) {
                setUpRecycleView();
                emptyView.setVisibility(View.INVISIBLE);
                friendStatusButton.setText("Delete Friend");
                friendStatusButton.setOnClickListener(v -> deleteFriend());
                closedShit=1;
            } else {
                Log.d("testcounter", "1 "+ConnectedUsername);
                emptyView.setVisibility(View.VISIBLE);
                checkForFriendRequests();
            }
            firstReqFlag++;
        });
    }

    private void checkForFriendRequests() {

        friendViewModel.reqUserFriendReqList(ConnectedUsername);
        friendViewModel.getUserFriendReqListData().observe(this, friendReqs1 -> {

            if(secondReqFlag==0){
                secondReqFlag=1;
                return;
            }

            if(secondReqFlag>1) return;

            Log.d("testfriendReqc "+ConnectedUsername, friendReqs1.toString());
            if (friendReqs1.contains(profile_username)) {
                friendStatusButton.setText("Accept Friend Request");
                friendStatusButton.setOnClickListener(v -> acceptFriendRequest());
            }
        });
    }

    private void checkForPendingRequests() {

        Log.d("testcount2222222222", "2 "+profile_username);
        friendViewModel.reqUserFriendReqList(profile_username);
        friendViewModel.getUserFriendReqListData().observe(this, pendingReqs2 -> {
            if( closedShit==1) return;
            if(thirdReqFlag>1) return;
            thirdReqFlag++;
            Log.d("test233", pendingReqs2.toString() + profile_username);
            if (pendingReqs2.contains(ConnectedUsername)) {
                friendStatusButton.setText("Pending...");
                friendStatusButton.setOnClickListener(null); // Do nothing on click
            } else {
                friendStatusButton.setText("Add Friend");
                friendStatusButton.setOnClickListener(v -> addFriend());
            }
        });

    }

    private void setUpRecycleView() {
        final PostAdapter feedPostAdapter = new PostAdapter(this, getApplicationContext());
        postsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postsRecyclerView.setAdapter(feedPostAdapter);
        postViewModel.reqUserPosts(profile_username);
        postViewModel.getUserPostsData().observe(this, posts -> {
            feedPostAdapter.setPosts(posts);
        });
    }



    // Method stubs for actions
    private void deleteAcc() {
        // Step 1: Request Account Deletion
        userViewModel.reqDeleteUser();

        // Step 2: Clear Shared Preferences
        SharedPreferences sharedPreferences = getSharedPreferences("YourSharedPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear(); // Remove all saved data
        editor.apply(); // Commit changes

        // Step 3 & 4: Navigate to Login Screen & Clear Activity Stack
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish(); // Close the current activity
    }
    private void updateAcc() {
        Log.d("test", "updateAcc: ");
        // Implementation for updating an account
        ImageHolder.userImageDrawable=profileImageView.getDrawable();
        Intent intent = new Intent(this, EditAccountActivity.class);

        // (Optional) Add any extras to the intent if you need to pass data to EditAccountActivity
        // intent.putExtra("key", "value");

        // Start the activity
        startActivity(intent);
    }

    private void deleteFriend() {
        friendViewModel.reqRemoveFriend(profile_username);
        reecreate();
        // You might want to update your UI here to reflect the change
    }

    private void acceptFriendRequest() {
        friendViewModel.reqAcceptFriendReq(profile_username);
        reecreate();
        // Again, update your UI to reflect the new friend status

    }

    private void addFriend() {
        friendViewModel.reqSendFriendReq(profile_username);
        reecreate();
        // Update UI to show that the friend request is pending
    }

}