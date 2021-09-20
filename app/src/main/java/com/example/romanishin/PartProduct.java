package com.example.romanishin;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.testromanishin.R;
import com.example.testromanishin.R.id;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

import static com.yandex.runtime.Runtime.getApplicationContext;

public class PartProduct extends RelativeLayout {

    private TextView product_nom;
    private TextView product_descr;
    private TextView product_code;
    private TextView product_add;
    private TextView product_price;
    private ImageView product_logo;
    //private String checks_num;
//    DisplayImageOptions defaultOptions;
//    ImageLoaderConfiguration config;

    private View view;
    private Context contextLocalVar;

    public PartProduct(Context context) {
        super(context);
        initComponents(context);
    }

    private void initComponents(Context context) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_product_layout, this);

        product_logo = view.findViewById(id.prod_logo);
        product_code  = view.findViewById(id.product_code);
        product_descr = view.findViewById(id.product_descr);
        product_price = view.findViewById(id.product_price);
        product_nom   = view.findViewById(id.product_nom);
        product_add   = view.findViewById(id.product_add);
        contextLocalVar = context;

//        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(contextLocalVar);
//        config.threadPriority(Thread.NORM_PRIORITY - 2);
//        config.denyCacheImageMultipleSizesInMemory();
//        //config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
//        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
//        //config.tasksProcessingOrder(QueueProcessingType.LIFO);
//        config.writeDebugLogs(); // Remove for release app
//
//        //ImageLoaderConfiguration.createDefault(contextLocalVar);
//        ImageLoader.getInstance().init(config.build());
    };

    public void setProduct_logo(String image_name) {

       String  image_url= "http://www.smc-romanishin.online/resource/" + image_name + ".jpg";
        Glide
                .with(this)
                .load(image_url)
                .into(product_logo);

       //Пробуем загрузить изображение в фоновом режиме, с кэшированием через стороннюю библиотеку
        //Попробуем использовать сторонние библиотеки для вывода картинок в фоновом задании
//        defaultOptions = new DisplayImageOptions.Builder()
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .build();
//
//        config = new ImageLoaderConfiguration.Builder(getApplicationContext()).defaultDisplayImageOptions(defaultOptions).build();



 //       ImageLoader.getInstance().displayImage(image_url, product_logo);

        //Загрузить изображение через самописный AsyncTask. Без кэширования сильно тупит.
        //new GetImageByHTTPAsyncTask(product_logo).execute(image_url);
    }

    public void clear_logo(String image_name) {
        if(product_logo != null) product_logo.setVisibility(View.INVISIBLE);
        else Toast.makeText(contextLocalVar, "prodduct_logo is null. ", Toast.LENGTH_LONG).show();
    }

    public void setProductCode(String text) {
        if(product_code != null) product_code.setText(text);
        else Toast.makeText(contextLocalVar, "product_code is null. ", Toast.LENGTH_LONG).show();
    }

    public void setProduct_descr(String text) {
        if(product_descr != null) product_descr.setText(text);
        else Toast.makeText(contextLocalVar, "product_descr is null. ", Toast.LENGTH_LONG).show();
    }

    public void setProduct_price(String text) {
        if(product_price != null) product_price.setText(text);
        //else Toast.makeText(contextLocalVar, "product_price is null. ", Toast.LENGTH_LONG).show();
    }

    public void setProduct_nom(String text) {
        if(product_nom != null) product_nom.setText(text);
        else Toast.makeText(contextLocalVar, "product_nom is null. ", Toast.LENGTH_LONG).show();
    }

    public void setProduct_add(String text) {
        if(product_add != null) product_add.setText(text);
        else Toast.makeText(contextLocalVar, "product_add is null. ", Toast.LENGTH_LONG).show();
    }

    public static Bitmap getImageBitmap(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
//    public String getChecks_num() {
//        return checks_num;
//    }
//
//    public String getChecks_date() {
//        return check_date.getText().toString();
//    }
}

class GetImageByHTTPAsyncTask extends AsyncTask<String, Void, Bitmap> {

    private String request="http://smc-romanishin.online/resource/p123.png";
    ImageView bm;

    public GetImageByHTTPAsyncTask(ImageView imageView){
        bm = imageView;
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

    public Bitmap getImageBitmap(String src) throws IOException {

        InputStream input               = null;
        HttpURLConnection connection    = null;
        URL url                         = null;

        try {
            url = new URL(src);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            input = connection.getInputStream();
        } catch (MalformedURLException e) {
            //Toast.makeText(contextLocalVar, "can-t url ", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        Bitmap myBitmap = BitmapFactory.decodeStream(input);
        return myBitmap;
    }
}