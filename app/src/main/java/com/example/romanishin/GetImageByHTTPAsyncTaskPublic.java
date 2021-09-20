package com.example.romanishin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class GetImageByHTTPAsyncTaskPublic extends AsyncTask<String, Void, Bitmap> {

    private String request="http://www.smc-romanishin.online/resource/p123.png";
    ImageView bm;

    public GetImageByHTTPAsyncTaskPublic(String string){
        request = string;
    };

    @Override
    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

   // @Override
    protected void onPostExecute(Bitmap result){
        bm.setImageBitmap(result);
    }

//    private String readStream(InputStream in) {
//        BufferedReader reader = null;
//        StringBuffer data = new StringBuffer();
//        reader = new BufferedReader(new InputStreamReader(in));
//        String line = "";
//
//        try {
//            while((line=reader.readLine())!=null){
//                data.append(line);
//            }
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        finally{
//            if(reader!=null){
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }
//        }
//        return data.toString();
//    }
}