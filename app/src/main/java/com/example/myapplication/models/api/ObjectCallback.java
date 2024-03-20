package com.example.myapplication.models.api;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ObjectCallback<T> implements Callback<T> {
    MutableLiveData<T> data;

    public ObjectCallback(MutableLiveData<T> data) {
        this.data = data;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful())
            data.postValue(response.body());
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.d("failure", t.getMessage());
    }
}
