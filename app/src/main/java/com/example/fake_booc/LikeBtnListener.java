package com.example.fake_booc;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class LikeBtnListener implements View.OnClickListener {
    private ImageView likeBtn;
    private Likeable item;
    private TextView likes;

    public LikeBtnListener(ImageView likeBtn, Likeable item, TextView likes) {
        this.likeBtn = likeBtn;
        this.item = item;
        this.likes = likes;
    }

    @Override
    public void onClick(View v) {
        if (User.getSignedIn().isUserInList(item.getLikedBy())) {
            likeBtn.setImageResource(R.drawable.like);
            item.removeLike(User.getSignedIn());
        } else {
            likeBtn.setImageResource(R.drawable.like_fill);
            item.addLike(User.getSignedIn());
        }

        likes.setText(String.valueOf(item.getLikes()));
    }
}
