package com.example.fake_booc;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class PostAdapter extends EditableAdapter<PostAdapter.PostViewHolder> {
    private Context context;
    private List<Post> posts;

    public PostAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PostViewHolder(LayoutInflater.from(context).inflate(R.layout.post_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.postTextView.setText(post.getPostText());
        holder.postImage.setImageURI(post.getPostImage());
        holder.userName.setText(post.getOriginalPoster().getUsername());
        holder.userImage.setImageURI(post.getOriginalPoster().getUserImage());
        holder.uploadDate.setText(post.getUploadDate().format(DateTimeFormatter.ofPattern("MMM d uuuu")));
        holder.likes.setText(String.valueOf(post.getLikes()));
        holder.addCommentLayout.setVisibility(View.GONE);
        holder.optionsBtn.setImageResource(R.drawable.options_btn);

        holder.likeBtn.setImageResource(R.drawable.like);
        holder.likeBtn.setOnClickListener(new LikeBtnListener(holder.likeBtn, post, holder.likes));

        holder.shareBtn.setImageResource(R.drawable.share);
        holder.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_TEXT, post.getPostText());

                Intent shareIntent = Intent.createChooser(sendIntent, "Share");
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(shareIntent);
            }
        });

        // Adding comments
        // ---------------------------------------------------------------------------------------------------------------------------
        holder.addCommentBtn.setImageResource(R.drawable.baseline_add_comment_24);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(holder.itemView.getWindowToken(), 0);
        holder.addCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.addCommentLayout.getVisibility() == View.GONE) {
                    holder.addCommentLayout.setVisibility(View.VISIBLE);
                    holder.addCommentBtn.setImageResource(R.drawable.cancel_add_comment);
                    holder.addCommentInput.requestFocus();
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                } else {
                    holder.addCommentLayout.setVisibility(View.GONE);
                    holder.addCommentBtn.setImageResource(R.drawable.baseline_add_comment_24);
                    imm.hideSoftInputFromWindow(holder.itemView.getWindowToken(), 0);
                }
            }
        });
        holder.addCommentInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String newComment = holder.addCommentInput.getText().toString();
                    post.addComment(new Comment(User.getSignedIn(), newComment));
                    updateComments(holder, post);
                    holder.addCommentLayout.setVisibility(View.GONE);
                    holder.addCommentBtn.setImageResource(R.drawable.baseline_add_comment_24);
                    holder.addCommentInput.setText("");

                    View view = holder.itemView;
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                return true;
            }
        });

        if (post.getOriginalPoster().isSignedIn()) {
            holder.optionsBtn.setVisibility(View.VISIBLE);
            holder.optionsBtn.setOnClickListener(new OptionsMenuListener<Post>(posts, position, this));
        } else {
            holder.optionsBtn.setVisibility(View.GONE);
        }

        updateComments(holder, post);
    }

    private void updateComments(@NonNull PostViewHolder holder, Post post) {
        holder.commentsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.commentsRecyclerView.setAdapter(new CommentAdapter(context, post.getCommmentsList()));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    @Override
    void editItem(int pos) {
        Intent editIntent = new Intent(context, EditPost.class);
        editIntent.putExtra("pos", pos);
        editIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(editIntent);
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        private TextView postTextView;
        private ImageView postImage;
        private TextView userName;
        private ImageView userImage;
        private TextView uploadDate;
        private RecyclerView commentsView;
        private TextView likes;
        private RecyclerView commentsRecyclerView;
        private ImageView likeBtn;
        private ImageView shareBtn;
        private ImageView addCommentBtn;
        private RelativeLayout addCommentLayout;
        private EditText addCommentInput;
        private ImageView optionsBtn;

        public PostViewHolder(@NonNull View itemView) {
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