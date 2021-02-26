package com.example.geolocalized_image;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    LocationManager locationManager;
    String provider;
    Location l;
    ImageView imageview;
    private TextView editTextLatitude;
    private TextView editTextLongitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         imageview = findViewById(R.id.imageView);
        editTextLatitude = findViewById(R.id.lat);
        editTextLongitude =  findViewById(R.id.lon);
        ActivityCompat.requestPermissions( this, new String[]
                {  android.Manifest.permission.ACCESS_COARSE_LOCATION  }, 48 ); // i ask the permission to users to get her position
        LocationService.setTextLat( editTextLatitude);
        LocationService.setTextLong(editTextLongitude );
       LocationService ls = new LocationService(this);
        Button button = (Button)findViewById(R.id.btn);
        Button btnDownl = (Button)findViewById(R.id.btn);
        button.setOnClickListener(new OnClickListenerBtn(this));
        btnDownl = findViewById(R.id.btnDownl);
        btnDownl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                AsynBitmapDownload.myActivity = MainActivity.this;
                String lat = editTextLatitude.toString();
                String lon = editTextLongitude.toString();
                new AsyncFlickrJSONDataForList(MainActivity.this).execute("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=eec0b9e94054fb28b367397e6a9a5ade&has_geo=1&lat="+lat+"&lon="+lon+"&per_page=1&format=json");
                //i execute the asynchrone task with the url contains my lat and my long necessary to get the JSONObject
            }
        });
    }

}