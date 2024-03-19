package com.example.myapplication.models.repositories.user.data;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class CreateUserData extends MutableLiveData<List<String>> {

    public CreateUserData() {
        super();
        setValue(null);
    }
}
