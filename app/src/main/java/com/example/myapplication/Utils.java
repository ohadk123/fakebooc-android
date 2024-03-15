package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Utils {

    public static Bitmap base64ToBitmap(String base64) {
        String[] splitString = base64.split(",");
        String value = splitString[splitString.length - 1];
        byte[] imageByteArray = Base64.decode(value, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
    }
}
