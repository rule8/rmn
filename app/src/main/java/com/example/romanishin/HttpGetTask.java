package com.example.romanishin;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class HttpGetTask extends AsyncTask<Void, Void, String> {

    private String request="https://smc-romanishin.online/?";

    public HttpGetTask(String string){
        request = request+string;
    };

    @Override
    protected String doInBackground(Void... params) {
        InputStream ddd;
        String data="";
        int rc=0;
        try {
            URL ur = new URL(request);
            URLConnection connection = ur.openConnection();
            HttpURLConnection huc = (HttpURLConnection) connection;
            rc = huc.getResponseCode();
            if(rc==huc.HTTP_OK){
                ddd = huc.getInputStream();
                data=readStream(ddd);
            }
            else data="Ne prokatilo v doInBackground";

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally {
        }
        return data;
    }

    @Override
    protected void onPostExecute(String result){
        if(null!=result){

        }else{

        }
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer data = new StringBuffer();
        reader = new BufferedReader(new InputStreamReader(in));
        String line = "";

        try {
            while((line=reader.readLine())!=null){
                data.append(line);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return data.toString();
    }
}