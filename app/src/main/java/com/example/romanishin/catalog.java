package com.example.romanishin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testromanishin.R;
//import com.nostra13.universalimageloader.core.DisplayImageOptions;
//import com.nostra13.universalimageloader.core.ImageLoader;
//import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import static com.example.testromanishin.R.layout.catalog_activity;

public class catalog extends AppCompatActivity{

    public SharedPreferences apppref;
    public static final String APP_PREFERENCES="apppref";
    public static String APP_TOKEN  = "";
    public static String APP_TEL    = "";
    public static String PRODUCT_TYPE = "";
//    public DisplayImageOptions defaultOptions;
//    public ImageLoaderConfiguration config;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(catalog_activity);
        apppref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        APP_TOKEN   = apppref.getString("APP_TOKEN", "unknown");
        APP_TEL     = apppref.getString("APP_TEL", "unknown");
        PRODUCT_TYPE = getIntent().getExtras().getString("productType");

//        defaultOptions = new DisplayImageOptions.Builder()
//                .cacheInMemory(true)
//                .cacheOnDisk(true)
//                .build();
//
//        config = new ImageLoaderConfiguration.Builder(getApplicationContext())
//                .defaultDisplayImageOptions(defaultOptions)
//                .build();
//
//        ImageLoader.getInstance().init(config);

        getCatalog(APP_TOKEN, APP_TEL);
    }

    public void getCatalog(String token, String tel){
        String productType = "";
        if(PRODUCT_TYPE.equals(""))
            productType = "all";
        else productType = PRODUCT_TYPE;

        HttpGetTask asT = new HttpGetTask("command="+"getCatalog"+"&productType="+ productType +"&token=" + APP_TOKEN + "&tel=" + APP_TEL);
        asT.execute();
        String ans = "No result", ansOk = "Ok";
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
        //-----------------------------------------------

        JSONObject reader = null;
        if(ans.equals("null")){
            Toast.makeText(this, "Null answer. Return... " + ans, Toast.LENGTH_LONG).show();
            return;
        }

        try {
            reader = new JSONObject(ans);
        } catch (JSONException e) {
            Toast.makeText(this, "can't create JSON reader" + ans, Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return;
        }

        String status               = "";
        String card_name            = "";
        String card_owner_name      = "";
        String card_scheme          = "";
        String card_scheme_value    = "";
        String card_balance         = "";
        String card_uid             = "";

        try {
            status = reader.getString("status");
        }catch (JSONException e){
            Toast.makeText(this, "??????-???? ?????????? ???? ??????... " + ans, Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return;
        }

        //===============================================
        LinearLayout liner = findViewById(R.id.catProducts_list);
        ScrollView scrollView = findViewById(R.id.scrollView);

        Rect scrollBounds = new Rect();
        scrollView.getHitRect(scrollBounds);


        if(status.equals(ansOk)){
            JSONObject productObj   = null;
            JSONArray productList   = null;
            try {
                productList     = reader.getJSONArray("productList");

            } catch (JSONException e) {
                Toast.makeText(this, "???? ???????? ???????????????????? ??????????" + ans, Toast.LENGTH_LONG).show();
                e.printStackTrace();
                return;
            }

            for (int i = 0; i < productList.length(); ++i) {
                //promoProduct = promoList.getJSONArray(i);

                try {
                    productObj = productList.getJSONObject(i);

                    final PartProduct partFrame = new PartProduct(getApplicationContext());

                    partFrame.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //???????????????? ???????????????????? ?????????????? ???? ????????????????????????
                            message_m("In Focus");
                        }
                    });

                    partFrame.setProduct_logo(productObj.getString("nom_pic_lg"));
                    partFrame.setProductCode(productObj.getString("nom_code"));
                    partFrame.setProduct_nom(productObj.getString("nom_named"));
                    partFrame.setProduct_descr(productObj.getString("nom_describe"));
                    partFrame.setProduct_price(productObj.getString("nom_type"));
                    partFrame.setProduct_add("+ 1");

                    liner.addView(partFrame);
                } catch (JSONException e) {
                    Toast.makeText(this, "???????????? ???????????????? ?????????????? ?????????????? \n" + ans, Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
        else{
            Toast.makeText(this, "No OK status: " + ans, Toast.LENGTH_LONG).show();
            return;
        }

        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            }
        });
    }

    public void onClick_our_shop(View view) {
        Intent intent = new Intent(this, activity_onMap.class);
        startActivity(intent);
    }

    public void onClick_our_service(View view) {
        Intent intent = new Intent(this, activity_service.class);
        startActivity(intent);
    }

//    public void getCheckDetails(String check_num, String check_date) {
//      //  Toast.makeText(this, "????: " + check_num, Toast.LENGTH_LONG).show();
//
//        Intent intent = new Intent(this, checksDetail.class);
//        intent.putExtra("check_num", check_num);
//        intent.putExtra("check_date", check_date);
//        startActivity(intent);
//    }

    public void onClick_myCard(View view) {
//        Intent intent = new Intent(this, checksActivity.class);
//        startActivity(intent);
    }

    public void message_m(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }


}
