package com.example.myapplication.views;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.ImageHolder;
import com.example.myapplication.R;
import com.example.myapplication.Utils;
import com.example.myapplication.viewModels.PostViewModel;

public class EditPostActivity extends AppCompatActivity {
    private Button editPostBtn;
    private EditText postContentInput;
    private ImageView imagePreview;
    private ImageButton uploadImageBtn;
    private ImageButton removeImageBtn;
    private PostViewModel postViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            Log.d("edit", "bye");
            return;
        }

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);

        String pid = extras.getString("pid");
        String content = extras.getString("content");
        Drawable image = ImageHolder.userImageDrawable;

        postContentInput = findViewById(R.id.post_content);
        postContentInput.setText(content);

        imagePreview = findViewById(R.id.Update_image_preview);
        if (image != null)
            imagePreview.setImageDrawable(image);
        else
            imagePreview.setImageResource(R.drawable.empty_image);

        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), selectedImage -> imagePreview.setImageURI(selectedImage));

        uploadImageBtn = findViewById(R.id.upload_image_btn);
        uploadImageBtn.setOnClickListener(v -> mGetContent.launch("image/*"));

        removeImageBtn = findViewById(R.id.remove_image_btn);
        removeImageBtn.setOnClickListener(v -> imagePreview.setImageResource(R.drawable.empty_image));

        editPostBtn = findViewById(R.id.edit_post_btn);
        editPostBtn.setOnClickListener(v -> {
            String imgBase64 = Utils.imageViewToBase64(imagePreview);
            String postText = postContentInput.getText().toString();

            postViewModel.reqUpdatePost(pid, postText, imgBase64);
            postViewModel.getPostData().observe(EditPostActivity.this, post -> {
                if (post != null) {
                    finish();
                }
            });
        });
    }
}
