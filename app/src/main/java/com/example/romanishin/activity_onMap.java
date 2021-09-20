package com.example.romanishin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testromanishin.R;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.runtime.image.AnimatedImageProvider;
import com.yandex.runtime.image.ImageProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class activity_onMap extends AppCompatActivity {

    public SharedPreferences apppref;
    public static final String APP_PREFERENCES="apppref";
    public static String APP_TOKEN  = "";
    public static String APP_TEL    = "";
    private MapView mapView;
    private final String YA_MAP_API_KEY = "4b3c6ef5-cc4c-47b5-baf0-b842e36cee82";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        MapKitFactory.setApiKey("4b3c6ef5-cc4c-47b5-baf0-b842e36cee82");
        MapKitFactory.initialize(this);

        // Укажите имя activity вместо map.
        //setContentView(R.layout.);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onmap);

        apppref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        APP_TOKEN   = apppref.getString("token", "unknown");
        APP_TEL     = apppref.getString("tel", "unknown");

        HttpGetTask asT = new HttpGetTask("command="+"getAdreses"+"&token=" + APP_TOKEN + "&tel=" + APP_TEL);
        asT.execute();
        String ans="No result", ansOk="Ok";
        try {
            ans = asT.get();
        } catch (InterruptedException e) {
            ans = "InterruptedException";
            e.printStackTrace();
            return;
        } catch (ExecutionException e) {
            ans = "ExecutionException";
            e.printStackTrace();
            return;
        }

        if(ans.equals("")){
            Toast.makeText(this, "No any data from server! " + ans, Toast.LENGTH_LONG).show();
        }

        JSONObject reader = null;
        if(ans.equals("null")){
            //Toast.makeText(this, "Null answer. Return... " + ans, Toast.LENGTH_LONG).show();
            return;
        }

        try {
            reader = new JSONObject(ans);
        } catch (JSONException e) {
            // Toast.makeText(this, "can't create JSON reader" + ans, Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return;
        }

        String status          = "";
        String addr_name       = "";
        String addr_latit      = "";
        String addr_longit     = "";

        try {
            status = reader.getString("status");
        }catch (JSONException e){
            Toast.makeText(this, "Что-то пошло не так... " + ans, Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return;
        }

        mapView = (MapView)findViewById(R.id.mapview);
        mapView.getMap().move(
                new CameraPosition(new Point(54.781818, 32.040156), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 5),
                null);

        AnimatedImageProvider animatedImage = AnimatedImageProvider.fromAsset(this, "mark.png");
        IconStyle iconStyle = new IconStyle();
        iconStyle.setAnchor(new PointF(1f, 1f));

        if(status.equals(ansOk)) {
            JSONObject adressObj    = null;
            JSONArray addrList      = null;
            try {
                addrList = reader.getJSONArray("addrList"); //.getJSONArray(0);
            } catch (JSONException e) {
                Toast.makeText(this, "Не могу получить массив адресов! \n" + ans, Toast.LENGTH_LONG).show();
                e.printStackTrace();
                return;
            }

            for (int i = 0; i < addrList.length(); ++i) {
                //promoProduct = promoList.getJSONArray(i);
                try {
                    adressObj = addrList.getJSONObject(i);
                    Double latit = 0.00;
                    Double longit = 0.00;

                    addr_latit = adressObj.getString("adress_latit");
                    addr_longit = adressObj.getString("adress_longit");

                    if(addr_latit.length()>0) latit = Double.parseDouble(addr_latit);
                    else continue;
                    if(addr_longit.length()>0) longit = Double.parseDouble(addr_longit);
                    else continue;

                    Point point1 = new Point(latit, longit);
                    mapView.getMap().getMapObjects().addPlacemark(point1, ImageProvider.fromResource(this, R.drawable.mark), iconStyle);

                } catch (JSONException e) {
                    Toast.makeText(this, "Ошибка разборки объекта массива \n" + ans, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

        }

        //-----------------------------------------------

        //mapView.geoOObjects().addPlacemark()
//        Point point1 = new Point(54.781818, 32.040156);
//        Point point2 = new Point(54.705111, 32.104567);
//        Point point3 = new Point(54.769206, 32.042870);
//        Point point4 = new Point(54.769393, 32.030518);
//        Point point5 = new Point(54.780145, 32.017300);
//
//
//
//        mapView.getMap().getMapObjects().addPlacemark(point1, ImageProvider.fromResource(this, R.drawable.mark), iconStyle);
//        mapView.getMap().getMapObjects().addPlacemark(point2, ImageProvider.fromResource(this, R.drawable.mark), iconStyle);
//        mapView.getMap().getMapObjects().addPlacemark(point3, ImageProvider.fromResource(this, R.drawable.mark), iconStyle);
//        mapView.getMap().getMapObjects().addPlacemark(point4, ImageProvider.fromResource(this, R.drawable.mark), iconStyle);
//        mapView.getMap().getMapObjects().addPlacemark(point5, ImageProvider.fromResource(this, R.drawable.mark), iconStyle);
    }


    @Override
    protected void onStop() {
        // Вызов onStop нужно передавать инстансам MapView и MapKit.
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        // Вызов onStart нужно передавать инстансам MapView и MapKit.
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    public void onClick_our_service(View view) {
        Intent intent = new Intent(this, activity_service.class);
        startActivity(intent);
    }

    public void onClick_our_shop(View view) {
        // Toast.makeText(this, "Selected... ", Toast.LENGTH_LONG).show();
//        Intent intent = new Intent(this, checksDetail.class);
//        startActivity(intent);
    }

    public void onClick_myCard(View view) {
        Intent intent = new Intent(this, checksActivity.class);
        startActivity(intent);
    }
}
