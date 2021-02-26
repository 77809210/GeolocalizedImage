package com.example.geolocalized_image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsynBitmapDownload extends AsyncTask<String, Void, Bitmap>
{
    HttpURLConnection httpURLConnection;
    public static  AppCompatActivity myActivity;
    ImageView imageview;

    public AsynBitmapDownload() {

    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        URL url = null;
        try {
            url = new URL(strings[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            Bitmap temp = BitmapFactory.decodeStream(inputStream);
            return temp;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally
        {
            httpURLConnection.disconnect();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if(bitmap != null) {
            imageview = myActivity.findViewById(R.id.imageView);
            imageview.setImageBitmap(bitmap);
            Toast.makeText(myActivity, "Download Successfull", Toast.LENGTH_SHORT);
        }else {
            Toast.makeText(myActivity, "Download failed", Toast.LENGTH_SHORT);
        }
    }
}