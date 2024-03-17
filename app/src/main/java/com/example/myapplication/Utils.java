package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class Utils {

    public static Bitmap base64ToBitmap(String base64) {
        String[] splitString = base64.split(",");
        String value = splitString[splitString.length - 1];
        byte[] imageByteArray = Base64.decode(value, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
    }

//    public static String imageViewToBase64(ImageView imageView) {
//        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
//        if (drawable == null) {
//            // Handle case where ImageView has no image
//            return "";
//        }
//
//        // Get the Bitmap from the drawable
//        Bitmap bitmap = drawable.getBitmap();
//
//        // Convert the Bitmap to a byte array
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//        byte[] imageBytes = byteArrayOutputStream.toByteArray();
//        String entryB64= Base64.encodeToString(imageBytes, Base64.DEFAULT);
//        // Encode the byte array to Base64 string
//        return entryB64.replaceAll("\n", "").replaceAll("\r", "").trim();
//    }


    public static String imageViewToBase64(ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        if (drawable == null) {
            // Handle case where ImageView has no image
            return "";
        }

        // Get the Bitmap from the drawable
        Bitmap bitmap = drawable.getBitmap();

        // Convert the Bitmap to a byte array in JPEG format
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);

        // Encode the byte array to Base64 string
        String base64String = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

        // Prefix the Base64 string with the image type (assuming it's JPEG)
        return "data:image/jpeg;base64," + base64String.replaceAll("\n", "").replaceAll("\r", "").trim();
    }

}
