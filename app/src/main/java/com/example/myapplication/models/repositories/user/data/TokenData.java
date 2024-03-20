package com.example.myapplication.models.repositories.user.data;

import androidx.lifecycle.MutableLiveData;

public class TokenData extends MutableLiveData<String> {

    public TokenData() {
        super();
        setValue(null);
    }
}
