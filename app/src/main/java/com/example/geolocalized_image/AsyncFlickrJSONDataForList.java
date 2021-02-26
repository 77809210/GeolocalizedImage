package com.example.geolocalized_image;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;

public class AsyncFlickrJSONDataForList extends AsyncTask<String, Void, JSONObject> {
    ListView ListImage;
    String url2= "";
    public static JSONObject json;
    ImageView imageView ;

    private AppCompatActivity myActivity;

    public AsyncFlickrJSONDataForList(AppCompatActivity mainActivity) {
        myActivity = mainActivity;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {

        URL url = null;

        HttpURLConnection urlConnection = null;
        String result = null;
        try {
            url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection(); // Open
            InputStream in = new BufferedInputStream(urlConnection.getInputStream()); // Stream

            result = readStream(in); // Read stream
            Log.e("result ",result);
            String r = result.toString();
            json = null;
            json = new JSONObject(result);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
        return json; // returns the result
    }
    @Override
    protected void onPostExecute(JSONObject s) {

        try {
            JSONArray items = s.getJSONArray("photo");
            JSONObject flickr_entry = items.getJSONObject(0);
            String id = flickr_entry.getJSONArray("id").toString();//i extract some variable necessary of JsonObject to dodnload image from Flick
            String secret = flickr_entry.getJSONArray("secret").toString();//these variable are id,secret,farm,server
            String farm = flickr_entry.getJSONArray("farm").toString();
            String server = flickr_entry.getJSONArray("server").toString();
            url2 = "https://farm"+farm+".staticflickr.com/"+server+"/"+id+"_"+secret+".jpg";
            new AsynBitmapDownload().execute(url2);// there i do a request HTTP to download image

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        is.close();

        // Extracting the JSON object from the String
        String jsonextracted = sb.substring("jsonFlickrFeed(".length(), sb.length() - 1);
        //Log.i("CIO", jsonextracted);

        return jsonextracted;
    }
}


