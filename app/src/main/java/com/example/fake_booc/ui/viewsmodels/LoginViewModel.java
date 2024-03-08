package com.example.fake_booc.ui.viewsmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fake_booc.repository.UserRepository;
public class LoginViewModel extends ViewModel {

    private MutableLiveData<String> loginToken = new MutableLiveData<>(); // If you need to use the token

    private UserRepository userRepository = new UserRepository();


    public LiveData<String> getLoginToken() {
        return loginToken; // Provide a way to observe the token
    }
    public void login(String username, String password) {
        userRepository.login(username, password, new UserRepository.SimpleLoginCallback() {
            @Override
            public void onResult(boolean isSuccess, String token) {

                if (isSuccess) {
                    loginToken.postValue(token); // Update token LiveData on success
                } else {
                    loginToken.postValue(null); // Or handle error state appropriately
                }
            }
        });
    }

}

