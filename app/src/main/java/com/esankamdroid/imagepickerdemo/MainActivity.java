package com.esankamdroid.imagepickerdemo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.esankamdroid.imagepicker.ImagePicker;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements ImagePicker.OnGetBitmapListener {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.activity_main_iv);

        new ImagePicker.Builder().with(this).setAllowMultipleSelect(true).setOnGetBitmapListener(this).build();
    }

    private void openImagePicker() {

        final int finePermissionCheck = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (finePermissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        ImagePicker.REQUEST_CODE_ASK_PERMISSIONS);
            } else {
                ImagePicker.getInstance().createImageChooser();
            }
        } else {
            ImagePicker.getInstance().createImageChooser();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ImagePicker.REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    ImagePicker.getInstance().createImageChooser();
                } else {
                    Toast.makeText(this, "You are not able to pick photo from gallery", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
            ImagePicker.getInstance().onActivityResult(requestCode, data);
    }

    public void openPicker(View view) {
        openImagePicker();
    }

    @Override
    public void onGetBitmap(ArrayList<String> images) {
        if (!images.isEmpty()) {
            if (images.size() == 1) {
                final String path = images.get(0);
                Log.e(getClass().getSimpleName(), "onGetBitmap: " + path);
                Glide.with(this).load(path).into(imageView);
            } else Log.e(getClass().getSimpleName(), "multiple onGetBitmap: " + images.size());
        } else {
            Log.e(getClass().getSimpleName(), "onGetBitmap: images not found");
        }
    }
}
