package com.example.myapplication.views;

import android.media.Image;
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
import com.example.myapplication.InBetweenHolder;
import com.example.myapplication.R;
import com.example.myapplication.Utils;
import com.example.myapplication.viewModels.PostViewModel;
import com.example.myapplication.viewModels.UserViewModel;

public class EditAccountActivity extends AppCompatActivity {
    private Button updateBtn;
    private ImageButton uploadImageBtn;
    private EditText newDisplayName;
    private ImageView imagePreview;
    private ImageButton removeImageBtn;
    private UserViewModel userViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        newDisplayName = findViewById(R.id.edit_account_displayName);

        updateBtn = findViewById(R.id.update_account_btn);
        setUpdateBtn();

        imagePreview = findViewById(R.id.Update_image_preview);
        imagePreview.setImageDrawable(ImageHolder.userImageDrawable);
        ActivityResultLauncher<String> mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(), selectedImage -> {
            imagePreview.setImageURI(selectedImage);
        });


        uploadImageBtn = findViewById(R.id.update_account_upload_image_btn);

        uploadImageBtn.setOnClickListener(v -> mGetContent.launch("image/*"));

    }

    private void setUpdateBtn() {
        updateBtn.setOnClickListener(v -> {
            String imgBase64;
            // Check if the tag indicates the image is "empty"
            if (imagePreview.getTag() != null && imagePreview.getTag().equals("empty")) {
                imgBase64 = ""; // Set to empty string if the tag is "empty"
            } else {
                imgBase64 = Utils.imageViewToBase64(imagePreview);

                Log.d("testupdate", imgBase64);
            }
            String displayName = newDisplayName.getText().toString();
            userViewModel.reqUpdateUser(displayName, imgBase64);
            // TODO: Update user then finish

            userViewModel.reqConnectedUserInfo();
            ImageHolder.userImageDrawable=imagePreview.getDrawable();
            InBetweenHolder.displayName=displayName;
            InBetweenHolder.username=UserViewModel.connectedUser.getUsername();
            finish();
        });
    }
}