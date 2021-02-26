package com.example.geolocalized_image;

import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.core.content.ContextCompat;

public class OnClickListenerBtn implements View.OnClickListener {
    private MainActivity mainActivity;

    public OnClickListenerBtn(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onClick(View v) {
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(mainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(mainActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        } else {
            Log.i("CIO", "Permissions denied. SDK_INT=" + Build.VERSION.SDK_INT);
            Log.i("CIO", "ACCESS_COARSE_LOCATION: " + ContextCompat.checkSelfPermission(mainActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION));
            Log.i("CIO", "ACCESS_FINE_LOCATION: " + ContextCompat.checkSelfPermission(mainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION));
            Log.i("CIO", "PackageManager.PERMISSION_GRANTED=" + PackageManager.PERMISSION_GRANTED);
        }
    }
}