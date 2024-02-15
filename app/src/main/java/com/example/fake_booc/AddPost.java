package com.example.fake_booc;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class AddPost extends AppCompatActivity {
    private Button addPostBtn;
    private ImageButton uploadImageBtn;
    private EditText postContentInput;
    private ImageView imagePreview = null;
    private Uri imageUri;
    private ImageButton removeImageBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        postContentInput = findViewById(R.id.post_content_input);

        addPostBtn = findViewById(R.id.add_post_btn);
        addPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postText = postContentInput.getText().toString();
                if (imagePreview == null) {
                    Post post = new Post(postText, User.getSignedIn());
                    Post.posts.add(post);
                } else {
                    Post post = new Post(postText, imageUri, User.getSignedIn());
                    Post.posts.add(post);
                }

                Intent i = new Intent(AddPost.this, HomePageActivity.class);
                startActivity(i);
            }
        });

        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri selectedImage) {
                imagePreview = findViewById(R.id.image_preview);
                imagePreview.setImageURI(selectedImage);
                imageUri = selectedImage;
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
    }
}