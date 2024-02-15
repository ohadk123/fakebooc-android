package com.example.fake_booc;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;

public class EditPost extends AppCompatActivity {
    private Button editPostBtn;
    private EditText postContentInput;
    private ImageView imagePreview;
    private Uri newImageUri;
    private ImageButton uploadImageBtn;
    private ImageButton removeImageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        int pos = extras.getInt("pos");
        Post post = Post.posts.get(pos);

        String postContent = post.getPostText();
        Uri imageUri = post.getPostImage();

        postContentInput = findViewById(R.id.post_content_input);
        postContentInput.setText(postContent);

        imagePreview = findViewById(R.id.image_preview);
        imagePreview.setImageURI(imageUri);

        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri selectedImage) {
                imagePreview = findViewById(R.id.image_preview);
                imagePreview.setImageURI(selectedImage);
                newImageUri = selectedImage;
            }
        });

        uploadImageBtn = findViewById(R.id.upload_image_btn);
        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent imagePicker = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                mGetContent.launch("image/*");
            }
        });

        removeImageBtn = findViewById(R.id.remove_image_btn);
        removeImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePreview.setImageResource(R.drawable.empty_image);
            }
        });

        editPostBtn = findViewById(R.id.edit_post_btn);
        editPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                post.setPostImage(newImageUri);
                post.setPostText(postContentInput.getText().toString());

                Intent i = new Intent(EditPost.this, HomePageActivity.class);
                startActivity(i);
            }
        });
    }
}