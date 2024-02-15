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

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Register extends AppCompatActivity {
    Button btnPickImage;
    Button signUpBtn ;
    String path;
    Uri uri;
    ImageView imgV;
    ActivityResultLauncher<Intent>resultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnPickImage=findViewById(R.id.btnPickImage);
        imgV=findViewById(R.id.img);
        registerResult();
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
        boolean isValidPassword = hasUpperCase && hasSpecialChar && hasNumber && isLengthValid;

        return isValidPassword;
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

    private void pickImage() {
        // Intent for picking an image from the gallery
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // Intent for taking a new picture with the camera
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        boolean canTakePhoto = takePictureIntent.resolveActivity(getPackageManager()) != null;

        // We will use a chooser to let the user pick between taking a photo or selecting from the gallery
        Intent chooserIntent = Intent.createChooser(pickPhotoIntent, "Select Image or Take Photo");

        // If the device has a camera app, add the take picture intent to the chooser
        if (canTakePhoto) {
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{takePictureIntent});
        }

        // Launch the chooser
        resultLauncher.launch(chooserIntent);
    }
    private void registerResult() {
        resultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == AppCompatActivity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null && data.getData() != null) {
                            // Image picked from gallery
                            uri = data.getData();
                            imgV.setImageURI(uri);

                        } else if (data != null && data.getExtras() != null) {
                            // Image captured from camera

                            Bundle extras = data.getExtras();
                            Bitmap imageBitmap = (Bitmap) extras.get("data");
                            uri = getImageUriFromBitmap(imageBitmap);
                            imgV.setImageBitmap(imageBitmap);



                        }
                    } else {
                        Toast.makeText(Register.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

}
