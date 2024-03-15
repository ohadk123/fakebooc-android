package com.example.myapplication.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapplication.R;
import com.example.myapplication.Utils;
import com.example.myapplication.models.database.entities.Post;
import com.example.myapplication.models.database.entities.PostWithUser;
import com.example.myapplication.viewModels.PostViewModel;
import com.example.myapplication.viewModels.UserViewModel;
import com.example.myapplication.views.adapters.PostAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private FloatingActionButton fab;
    private SwitchCompat modeSwitch;
    private boolean isNightMode = false;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private UserViewModel userViewModel;
    private PostViewModel postViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);

        findViews();
        fabClickListener();
        setSupportActionBar(toolbar);
        setUpDrawer();
        setUpRecycleView();
        setModeSwitch();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshLayout.post(() -> {
            refreshLayout.setRefreshing(true);
            postViewModel.reqPostsForFeed();
        });
    }

    private void fabClickListener() {
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(HomePageActivity.this, AddPostActivity.class);
            startActivity(intent);
        });
    }

    private void setUpRecycleView() {
        final PostAdapter feedPostAdapter = new PostAdapter(this, getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(feedPostAdapter);

        refreshLayout.setOnRefreshListener(() -> {
            postViewModel.reqPostsForFeed();
        });

        postViewModel.getFeedPostsData().observe(this, posts -> {
            feedPostAdapter.setPosts(posts.getPosts());
            refreshLayout.setRefreshing(false);
        });
    }

    private void findViews() {
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.feed);
        refreshLayout = findViewById(R.id.refreshLayout);
        fab = findViewById(R.id.add_fab);
        modeSwitch = findViewById(R.id.mode_switch);
    }

    private void setModeSwitch() {
        sharedPreferences = getSharedPreferences("MODE", MODE_PRIVATE);
        isNightMode = sharedPreferences.getBoolean("nightmode", false);

        if (isNightMode) {
            modeSwitch.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        modeSwitch.setOnClickListener(v -> {
            if (isNightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor = sharedPreferences.edit();
                editor.putBoolean("nightmode", false);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor = sharedPreferences.edit();
                editor.putBoolean("nightmode", true);
            }
            editor.apply();
        });
    }

    private void setUpDrawer() {
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
            }
        };
        getOnBackPressedDispatcher().addCallback(this,onBackPressedCallback);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        TextView drawerUsername = headerView.findViewById(R.id.drawer_username);
        ImageView drawerPFP = headerView.findViewById(R.id.drawer_pfp);

        userViewModel.reqConnectedUserInfo();
        userViewModel.getConnectedUserData().observe(this, user -> {
            drawerUsername.setText(user.getDisplayName());
            drawerPFP.setImageBitmap(Utils.base64ToBitmap(user.getProfileImage()));
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.nav_home) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (menuItem.getItemId() == R.id.nav_logout) {
            Intent intent = new Intent(HomePageActivity.this, LoginActivity.class);
            startActivity(intent);
        } else
            Toast.makeText(this, "This feature is not yet implemented", Toast.LENGTH_SHORT).show();

        return true;
    }
}
