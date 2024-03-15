package com.example.myapplication.views;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.viewModels.UserViewModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private String currentPhotoPath;
    Button btnPickImage;
    Button signUpBtn ;
    ImageView imgV;
    ActivityResultLauncher<Intent> resultLauncher;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        btnPickImage = findViewById(R.id.btnPickImage);
        imgV = findViewById(R.id.img);
        signUpBtn = findViewById(R.id.AsignUpBtn);

        signUpBtn.setOnClickListener(view -> handleSignUp());
        btnPickImage.setOnClickListener(view -> pickImage());

        userViewModel.getCreateUserData().observe(this, strings -> {
            if (strings != null) {
                if (!strings.isEmpty())
                    showErrorDialog(strings);
                else {
                    Intent loginIntent = new Intent(this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }
            }
        });
    }

    private void showErrorDialog(List<String> strings) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.custom_alert_dialog, null);
        TextView errorMessageTextView = dialogView.findViewById(R.id.errorMessageTextView);
        StringBuilder formattedErrorMessage = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            String error = strings.get(i);
            if (!error.isEmpty())
                formattedErrorMessage.append("• ").append(error).append('\n');
        }
        errorMessageTextView.setText(formattedErrorMessage.toString());

        builder.setView(dialogView).setTitle("Error").setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void pickImage() {
        if (!checkCameraPermission()) {
            requestCameraPermission();
        }
        showDialog();
    }

    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CODE);
    }

    ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            imgV.setImageURI(Uri.parse(currentPhotoPath));
            // Handle the camera image URI (e.g., display it or save it)
        }
    });
    ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
            Uri selectedImageUri = result.getData().getData();
            imgV.setImageURI(selectedImageUri);
            // Handle the gallery image URI (e.g., display it or save it)
        }
    });

    private void showDialog() {
        // Options to be displayed in the dialog
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        // Creating the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("Select Option");

        // Setting the options and handling clicks
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    // User chose to take a picture
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                        if (photoFile != null) {
                            Uri photoURI = FileProvider.getUriForFile(RegisterActivity.this, "com.example.myapplication.fileProvider", photoFile);
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            cameraLauncher.launch(takePictureIntent);
                        }
                    } catch (IOException ex) {
                        Toast.makeText(RegisterActivity.this, "Error occurred while creating the file", Toast.LENGTH_SHORT).show();
                    }
                } else if (options[item].equals("Choose from Gallery")) {
                    // User chose to pick an image from the gallery
                    Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    galleryLauncher.launch(pickPhotoIntent);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });

        // Showing the AlertDialog
        builder.show();
    }

    private File createImageFile() throws IOException {

//         Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(imageFileName, ".jpg", storageDir);


        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void handleSignUp() {
        EditText usernameEditText = findViewById(R.id.usernameEditText);
        EditText profileNameEditText = findViewById(R.id.displayNameEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        EditText cPasswordEditText = findViewById(R.id.CPasswordEditText);

        String username = usernameEditText.getText().toString().trim();
        String displayName = profileNameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();
        String cPassword = cPasswordEditText.getText().toString();
        String imageB64 = imageViewToBase64(imgV);

        userViewModel.reqCreateUser(username, displayName, password, cPassword, imageB64);
    }

    public static String imageViewToBase64(ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        if (drawable == null) {
            // Handle case where ImageView has no image
            return "";
        }

        // Get the Bitmap from the drawable
        Bitmap bitmap = drawable.getBitmap();

        // Convert the Bitmap to a byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        String entryB64= Base64.encodeToString(imageBytes, Base64.DEFAULT);
        // Encode the byte array to Base64 string
        return entryB64.replaceAll("\n", "").replaceAll("\r", "").trim();
    }
}