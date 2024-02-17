package com.example.fake_booc;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Register extends AppCompatActivity {


    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private String currentPhotoPath;

    Button btnPickImage;
    Button signUpBtn ;
    ImageView imgV;
    ActivityResultLauncher<Intent>resultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnPickImage=findViewById(R.id.btnPickImage);
        imgV=findViewById(R.id.img);
//        registerResult();
        signUpBtn = findViewById(R.id.AsignUpBtn);

        signUpBtn.setOnClickListener(view -> handleSignUp());
        btnPickImage.setOnClickListener(view -> pickImage());


    }




    private boolean isValidEmail(String email) {
        // Validate email format
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        boolean hasUpperCase = password.matches(".*[A-Z].*");
        boolean hasSpecialChar = password.matches(".*[!@#$%^&*()_+{}].*");
        boolean hasNumber = password.matches(".*\\d.*");
        boolean isLengthValid = password.length() >= 8;

        String s1 = "hasUpperCase:" + hasUpperCase + "\n" +
                "hasSpecialChar:" + hasSpecialChar + "\n" +
                "hasNumber:" + hasNumber + "\n" +
                "isLengthValid:" + isLengthValid;
        System.out.println(s1);
        Log.d("test",s1);
        return hasUpperCase && hasSpecialChar && hasNumber && isLengthValid;
    }

    private boolean isValidCPassword(String cPassword,String password) {
        return password.equals(cPassword);
    }

    private void showErrorDialog(String errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.custom_alert_dialog, null);
        TextView errorMessageTextView = dialogView.findViewById(R.id.errorMessageTextView);
        errorMessageTextView.setText(errorMessage);

        builder.setView(dialogView)
                .setTitle("Error")
                .setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void handleSignUp() {
        String a="all";
        // Retrieve data from EditText fields
        EditText usernameEditText = findViewById(R.id.registerUsernameEditText);
        EditText profileNameEditText = findViewById(R.id.mailEditText);
        EditText passwordEditText = findViewById(R.id.passwordEditText);
        EditText cPasswordEditText = findViewById(R.id.CPasswordEditText);

        String username = usernameEditText.getText().toString().trim();
        String profileName = profileNameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();
        String cPassword = cPasswordEditText.getText().toString();


        boolean isValidPassword = isValidPassword(password);
        boolean isValidCPassword = isValidCPassword(cPassword,password);
        boolean isValidImage = imgV.getDrawable() != null;

        StringBuilder errorBuilder = new StringBuilder();

        if (!isValidPassword) {
            errorBuilder.append("Password must contain at least one uppercase letter, one special character, one number, and be at least 8 characters long.\n\n");
        }
        if (!isValidCPassword) {
            errorBuilder.append("Passwords do not match.\n\n");
        }
        if (!isValidImage) {
            errorBuilder.append("Please select an image.\n\n");
        }

        if (errorBuilder.length() > 0) {
            showErrorDialog(errorBuilder.toString());
            return;
        }



        Log.d("isValidPassword",isValidPassword?"true":"false");
        Log.d("isValidCPassword",isValidCPassword?"true":"false");
        Log.d("isValidImage",isValidImage?"true":"false");
        if ( !isValidPassword || !isValidImage || !isValidCPassword) {
            Log.d("doesnt work",a);
            return;
        }
        Uri imageUri = getImageUri(imgV);


        JSONObject userData = new JSONObject();
        try {
            userData.put("username", username);
            userData.put("profile name", profileName);
            userData.put("password", password);
            userData.put("imageUri", imageUri.toString());

            //todo create new user add to user list keep password in shared pref under the username
            User cUser=new User(profileName,imageUri,password,username);
            cUser.signUp();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Pass the JSON object to a function in Register.java
        processSignUp(userData);
    }



    private Uri getImageUri(ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }




    private void processSignUp(JSONObject userData) {
        // Write your logic to handle the SignUp process using the userData JSON object
        // For example, you can log the data:
        Log.d("SignUpData", userData.toString());
        // Or pass it to another function:
        // mySignUpFunction(userData);
        startActivity(new Intent(Register.this, Login.class));
        finish();
    }





    private Uri getImageUriFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
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


//        private void pickImage() {
//            // Check for camera permission
//            if (checkCameraPermission()) {
//
//            } else {
//                requestCameraPermission();
//            }
//
//            // Intent for picking an image from the gallery
//            Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//// Intent for taking a new picture with the camera
//            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            List<Intent> intentList = new ArrayList<>();
//            intentList.add(pickPhotoIntent);
//
//            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//                File photoFile = null;
//                try {
//                    photoFile = createImageFile();
//
//                    if (photoFile != null) {
//                        Uri photoURI = FileProvider.getUriForFile(this, "com.example.fake_booc.fileProvider", photoFile);
//                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                        intentList.add(takePictureIntent);
//
//                    }else{
//                        Toast.makeText(this, "Error occurred while creating the file", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (IOException ex) {
//                    Toast.makeText(this, "Error occurred while creating the file", Toast.LENGTH_SHORT).show();
//                }
//            }
//            // Check if we have at least one intent to show
//            if (!intentList.isEmpty()) {
//                // Create a chooser with the first intent
//                Intent chooserIntent = Intent.createChooser(intentList.remove(0), "Select Image or Take Photo");
//
//                // Convert the rest of the intents list into an array
//                Intent[] extraIntents = intentList.toArray(new Intent[0]);
//
//                // Add the rest of the intents as extra initial intents
//                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
//
//                // Launch the chooser
//                resultLauncher.launch(chooserIntent);
//        } else {
//            // Handle the case where no intents are available
//            Log.d("pickImage", "No intents available");
//        }
//    }


    enum Option {
        CAMERA,
        GALLERY
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


//    private void registerResult() {
//        resultLauncher = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    Log.d(currentPhotoPath,"thats the path");
//                    if (result.getResultCode() == AppCompatActivity.RESULT_OK) {
//                        Intent data = result.getData();
//                        if (data != null && data.getData() != null) {
//                            // Image picked from gallery
//                            uri = data.getData();
//                            imgV.setImageURI(uri);
//
//                        } else if (data != null && data.getExtras() != null) {
//                            // Image captured from camera
//                            imgV.setImageURI(Uri.parse(currentPhotoPath));
//                        }
//                    } else {
//                        Toast.makeText(Register.this, "No Image Selected", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );
//    }

}
