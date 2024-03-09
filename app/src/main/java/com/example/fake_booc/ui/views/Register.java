package com.example.fake_booc.ui.views;


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
import android.util.Log;
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

import com.example.fake_booc.R;
import com.example.fake_booc.ui.viewsmodels.RegisterViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Register extends AppCompatActivity {


    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private String currentPhotoPath;

    Button btnPickImage;
    Button signUpBtn ;
    ImageView imgV;
    ActivityResultLauncher<Intent> resultLauncher;
    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String tst="tst";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize ViewModel
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);


        btnPickImage=findViewById(R.id.btnPickImage);
        imgV=findViewById(R.id.img);
        signUpBtn = findViewById(R.id.AsignUpBtn);

        signUpBtn.setOnClickListener(view -> handleSignUp());
        btnPickImage.setOnClickListener(view -> pickImage());



        registerViewModel.getErr().observe(this, status -> {
            Log.d(tst, String.valueOf(status));
            if (!status.isEmpty()) {
                showErrorDialog(String.valueOf(status)); // Modify showErrorDialog to handle server response
            }else{
                // Navigate to login page
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
                finish(); // Finish the current activity to prevent going back to it from the login page
            }
        });
    }

    private void handleSignUp() {
        // Retrieve user inputs
        // Check for required fields' emptiness only, as an example

        String tst="tsthandle";
        // Retrieve data from EditText fields
        EditText usernameEditText = findViewById(R.id.registerUsernameEditText);
        EditText displayNameEditText = findViewById(R.id.displayNameEdit);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        EditText cPasswordEditText = findViewById(R.id.CPasswordEditText);

        String username = usernameEditText.getText().toString().trim();
        String displayName = displayNameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();
        String cPassword = cPasswordEditText.getText().toString();



        // Call registerUser on your ViewModel
        registerViewModel.registerUser(username, displayName, password,cPassword, imageViewToBase64(imgV));






    }

    private void showErrorDialog(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.custom_alert_dialog, null);
        TextView errorMessageTextView = dialogView.findViewById(R.id.errorMessageTextView);

        // Parse JSON error message
        try {
            JSONObject errorJson = new JSONObject(errorMessage);
            JSONArray errorsArray = errorJson.getJSONArray("errors");
            StringBuilder formattedErrorMessage = new StringBuilder();
            for (int i = 0; i < errorsArray.length(); i++) {
                String error = errorsArray.getString(i);
                if (!error.isEmpty()) {
                    formattedErrorMessage.append("• ").append(error).append("\n");
                }
            }
            errorMessageTextView.setText(formattedErrorMessage.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            errorMessageTextView.setText("Error parsing error message");
        }

        builder.setView(dialogView)
                .setTitle("Error")
                .setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static String imageViewToBase64(ImageView imageView) {
        // Get the drawable from the ImageView
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        if (drawable == null) {
            // Handle case where ImageView has no image
            return null;
        }

        // Get the Bitmap from the drawable
        Bitmap bitmap = drawable.getBitmap();

        // Convert the Bitmap to a byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        String entryB64=Base64.encodeToString(imageBytes, Base64.DEFAULT);
        // Encode the byte array to Base64 string
        String cleanedBase64 = entryB64.replaceAll("\n", "").replaceAll("\r", "").trim();

        return cleanedBase64;
    }

    private boolean checkCameraPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_PERMISSION_CODE);
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
    private void showDialog() {
        // Options to be displayed in the dialog
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        // Creating the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
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
                            Uri photoURI = FileProvider.getUriForFile(Register.this, "com.example.fake_booc.fileProvider", photoFile);
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            cameraLauncher.launch(takePictureIntent);
                        }
                    } catch (IOException ex) {
                        Toast.makeText(Register.this, "Error occurred while creating the file", Toast.LENGTH_SHORT).show();
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


    private void pickImage() {
        if (!checkCameraPermission()) {
            requestCameraPermission();
        }
        showDialog();
    }
    ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
            Uri selectedImageUri = result.getData().getData();
            imgV.setImageURI(selectedImageUri);
            // Handle the gallery image URI (e.g., display it or save it)
        }
    });

    ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            imgV.setImageURI(Uri.parse(currentPhotoPath));
            // Handle the camera image URI (e.g., display it or save it)
        }
    });

    // Your existing methods for image capture, permission handling, etc.
}