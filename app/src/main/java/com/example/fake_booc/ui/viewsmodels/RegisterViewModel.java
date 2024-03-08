package com.example.fake_booc.ui.viewsmodels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fake_booc.MyApplication;
import com.example.fake_booc.repository.UserRepository;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<Boolean> registrationStatus = new MutableLiveData<>();
    private MutableLiveData<String> err = new MutableLiveData<>();
    private UserRepository userRepository = new UserRepository();


    public LiveData<Boolean> getRegistrationStatus() {
        return registrationStatus;
    }
    public LiveData<String> getErr(){return err;}

    public void registerUser(String username, String profileName, String password,String cPassword, Uri imageUri) {

        userRepository.registerUser(username, profileName, password,cPassword, convertImageUriToBase64(imageUri), new UserRepository.RegistrationCallback(){
            @Override
            public void onRegistrationComplete(boolean status,String errors) {
                registrationStatus.postValue(status);
                err.postValue(errors);
            }
        });
    }


    public String convertImageUriToBase64(Uri imageUri) {
        Context context= MyApplication.context;
        try (InputStream imageStream = context.getContentResolver().openInputStream(imageUri)) {
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            byte[] imageBytes = outputStream.toByteArray();
            return Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
