package com.example.fake_booc;

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CommentAdapter extends EditableAdapter<CommentAdapter.CommentViewHolder> {
    private Context context;
    private List<Comment> comments;
    private EditText editTextInput;
    private TextView commentContent;
    private View itemView;

    public CommentAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentViewHolder(LayoutInflater.from(context).inflate(R.layout.comment_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        this.itemView = holder.itemView;

        Comment comment = comments.get(position);
        holder.commentUserImage.setImageURI(comment.getOriginalCommenter().getUserImage());
        holder.commentUserName.setText(comment.getOriginalCommenter().getUsername());
        holder.commentContent.setText(comment.getCommentContent());
        holder.likes.setText(String.valueOf(comment.getLikes()));
        holder.optionsBtn.setImageResource(R.drawable.options_btn);
        holder.editTextInput.setText(comment.getCommentContent());

        holder.likeBtn.setImageResource(R.drawable.like);
        holder.likeBtn.setOnClickListener(new LikeBtnListener(holder.likeBtn, comment, holder.likes));

        if (comment.getOriginalCommenter().isSignedIn()) {
            holder.optionsBtn.setVisibility(View.VISIBLE);
            holder.optionsBtn.setOnClickListener(new OptionsMenuListener<Comment>(comments, position, this));
        } else {
            holder.optionsBtn.setVisibility(View.GONE);
        }

        this.editTextInput = holder.editTextInput;
        this.commentContent = holder.commentContent;
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    @Override
    void editItem(int pos) {
        commentContent.setVisibility(View.GONE);
        editTextInput.setVisibility(View.VISIBLE);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        editTextInput.requestFocus();
        editTextInput.setSelection(editTextInput.getText().length());
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

        editTextInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String newComment = editTextInput.getText().toString();
                    commentContent.setText(newComment);
                    commentContent.setVisibility(View.VISIBLE);
                    editTextInput.setVisibility(View.GONE);

                    imm.hideSoftInputFromWindow(itemView.getWindowToken(), 0);
                }
                return true;
            }
        });
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder {

        private ImageView commentUserImage;
        private TextView commentUserName;
        private TextView commentContent;
        private TextView likes;
        private ImageView likeBtn;
        private ImageView optionsBtn;
        private EditText editTextInput;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            commentUserImage = itemView.findViewById(R.id.comment_user_image);
            commentUserName = itemView.findViewById(R.id.comment_user_name);
            commentContent = itemView.findViewById(R.id.comment_content);
            likes = itemView.findViewById(R.id.likes);
            likeBtn = itemView.findViewById(R.id.like_btn);
            optionsBtn = itemView.findViewById(R.id.options_comment);
            editTextInput = itemView.findViewById(R.id.comment_edit);
        }
    }
}
