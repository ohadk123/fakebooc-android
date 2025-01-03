package com.example.myapplication.views.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ImageHolder;
import com.example.myapplication.InBetweenHolder;
import com.example.myapplication.R;
import com.example.myapplication.Utils;
import com.example.myapplication.models.api.TokenClient;
import com.example.myapplication.models.database.entities.Post;
import com.example.myapplication.viewModels.PostViewModel;
import com.example.myapplication.viewModels.UserViewModel;
import com.example.myapplication.views.EditPostActivity;
import com.example.myapplication.views.ProfilePageActivity;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostFeedViewHolder> {
    private Context context;
    private List<Post> posts;
    private ViewModelStoreOwner viewModelStoreOwner;
    private List<UserViewModel> userViewModels;
    private LifecycleOwner lifecycleOwner;

    public PostAdapter(AppCompatActivity owner, Context context) {
        this.context = context;
        this.viewModelStoreOwner = owner;
        this.lifecycleOwner = owner;
        this.userViewModels = new ArrayList<>();
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;

        int size = posts.size();
        List<UserViewModel> newViewModels = new ArrayList<>();
        for (int i = 0; i < size; i++)
            newViewModels.add(new UserViewModel());
        this.userViewModels = newViewModels;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostFeedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostFeedViewHolder(LayoutInflater.from(context).inflate(R.layout.post_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostFeedViewHolder holder, int position) {
        UserViewModel userViewModel = new ViewModelProvider(viewModelStoreOwner).get(UserViewModel.class);
        PostViewModel postViewModel = new ViewModelProvider(viewModelStoreOwner).get(PostViewModel.class);

        Post post = posts.get(position);
        holder.postTextView.setText(post.getContent());
        holder.uploadDate.setText(post.getDate());
        holder.likes.setText(String.valueOf(post.getLikes().size()));
        holder.shareBtn.setImageResource(R.drawable.share);
        holder.addCommentLayout.setVisibility(View.GONE);
        holder.optionsBtn.setImageResource(R.drawable.options_btn);
        holder.postImage.setImageBitmap(Utils.base64ToBitmap(post.getContentImage()));

        userViewModel.getPostUser(post.getUploader()).observe(lifecycleOwner, user -> {
            if (user != null && post.getUploader().equals(user.getUsername())) {
                holder.userName.setText(user.getDisplayName());
                holder.userImage.setImageBitmap(Utils.base64ToBitmap(user.getProfileImage()));
            }
        });

        holder.userName.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProfilePageActivity.class);

            // Assuming holder.username is a TextView

            Drawable imageDrawable = holder.userImage.getDrawable();
            ImageHolder.userImageDrawable = imageDrawable;
            // Get uploader's username from the post object
            String uploaderUsername = post.getUploader();
            String displayName = holder.userName.getText().toString();
            InBetweenHolder.username=uploaderUsername;
            InBetweenHolder.displayName=displayName;


            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        });



        holder.likeBtn.setImageResource(post.getLikes().contains(TokenClient.getTokenUser()) ?
                R.drawable.like_fill : R.drawable.like);
        holder.likeBtn.setOnClickListener(v -> {
            if (post.getLikes().contains(TokenClient.getTokenUser())) {
                holder.likeBtn.setImageResource(R.drawable.like);
                List<String> likes = post.getLikes();
                likes.remove(TokenClient.getTokenUser());
                post.setLikes(likes);
                postViewModel.reqUnlikePost(post.get_id());
            } else {
                holder.likeBtn.setImageResource(R.drawable.like_fill);
                List<String> likes = post.getLikes();
                likes.add(TokenClient.getTokenUser());
                post.setLikes(likes);
                postViewModel.reqLikePost(post.get_id());
            }
            notifyItemChanged(position);
        });

        if (post.getUploader().equals(TokenClient.getTokenUser())) {
            holder.optionsBtn.setVisibility(View.VISIBLE);
            holder.optionsBtn.setOnClickListener(v -> {
                PopupMenu optionsMenu = new PopupMenu(v.getContext(), v);
                optionsMenu.inflate(R.menu.options_menu);
                optionsMenu.setOnMenuItemClickListener(item -> {
                    if (item.getItemId() == R.id.remove_item) {
                        postViewModel.reqDeletePost(post.get_id());
                        posts.remove(post);
                        postViewModel.reqPostsForFeed();
                        notifyDataSetChanged();
                        return true;
                    }
                    if (item.getItemId() == R.id.edit_item) {
                        Intent editPostIntent = new Intent(context, EditPostActivity.class);
                        editPostIntent.putExtra("pid", post.get_id());
                        editPostIntent.putExtra("content", post.getContent());
                        ImageHolder.userImageDrawable=holder.postImage.getDrawable();
                        editPostIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(editPostIntent);
                        return true;
                    }
                    return false;
                });
                optionsMenu.show();
            });
        } else {
            holder.optionsBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class PostFeedViewHolder extends RecyclerView.ViewHolder {

        private TextView postTextView;
        private ImageView postImage;
        private TextView userName;
        private ImageView userImage;
        private TextView uploadDate;
        private TextView likes;
        private RecyclerView commentsRecyclerView;
        private ImageView likeBtn;
        private ImageView shareBtn;
        private ImageView addCommentBtn;
        private RelativeLayout addCommentLayout;
        private EditText addCommentInput;
        private ImageView optionsBtn;

        public PostFeedViewHolder(@NonNull View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.post_content_image);
            postTextView = itemView.findViewById(R.id.post_content_text);
            userName = itemView.findViewById(R.id.post_user);
            userImage = itemView.findViewById(R.id.post_user_image);
            uploadDate = itemView.findViewById(R.id.upload_date);
            likes = itemView.findViewById(R.id.likes);
            commentsRecyclerView = itemView.findViewById(R.id.comments_recycle_view);
            addCommentLayout = itemView.findViewById(R.id.add_comment_layout);
            addCommentInput = itemView.findViewById(R.id.comment_content_input);
            optionsBtn = itemView.findViewById(R.id.options_post);

            likeBtn = itemView.findViewById(R.id.like_btn);
            shareBtn = itemView.findViewById(R.id.share_btn);
            addCommentBtn = itemView.findViewById(R.id.add_comment_btn);
        }
    }
}
