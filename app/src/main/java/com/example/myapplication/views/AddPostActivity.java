package com.example.myapplication.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.viewModels.PostViewModel;

public class AddPostActivity extends AppCompatActivity {
    private Button addPostBtn;
    private ImageButton uploadImageBtn;
    private EditText postContentInput;
    private ImageView imagePreview;
    private Uri image;
    private ImageButton removeImageBtn;
    private PostViewModel postViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        postViewModel = new ViewModelProvider(this).get(PostViewModel.class);
        postContentInput = findViewById(R.id.post_content_input);

        imagePreview = findViewById(R.id.image_preview);
        addPostBtn = findViewById(R.id.add_post_btn);
        uploadImageBtn = findViewById(R.id.upload_image_btn);
        removeImageBtn = findViewById(R.id.remove_image_btn);

        setAddPostBtn();

        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), selectedImage -> {
            imagePreview.setImageURI(selectedImage);
        });

        uploadImageBtn.setOnClickListener(v -> {
            Intent imagePicker = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            mGetContent.launch("image/*");
        });

        removeImageBtn.setOnClickListener(v -> imagePreview.setImageResource(R.drawable.empty_image));
    }

    private void setAddPostBtn() {
        addPostBtn.setOnClickListener(v -> {
            String imgBase64 = RegisterActivity.imageViewToBase64(imagePreview);
            String postText = postContentInput.getText().toString();

            postViewModel.reqCreatePost(postText, imgBase64);
            postViewModel.getPostData().observe(AddPostActivity.this, post -> {
                if (post != null) {
                    finish();
                }
            });
        });
    }
}
