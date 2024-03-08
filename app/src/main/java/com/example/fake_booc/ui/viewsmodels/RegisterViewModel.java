package com.example.fake_booc.ui.viewsmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.fake_booc.repository.UserRepository;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<Boolean> registrationStatus = new MutableLiveData<>();
    private MutableLiveData<String> err = new MutableLiveData<>();
    private UserRepository userRepository = new UserRepository();


    public LiveData<Boolean> getRegistrationStatus() {
        return registrationStatus;
    }
    public LiveData<String> getErr(){return err;}

    public void registerUser(String username, String displayName, String password,String cPassword, String b64Img) {
         userRepository.registerUser(username, displayName, password,cPassword,b64Img, new UserRepository.RegistrationCallback(){
            @Override
            public void onRegistrationComplete(boolean status,String errors) {
                err.postValue(errors);
            }
        });
    }

}
