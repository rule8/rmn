package com.example.romanishin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testromanishin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import static com.example.testromanishin.R.layout.checks_details;

public class checksDetail extends AppCompatActivity{

    public SharedPreferences apppref;
    public static final String APP_PREFERENCES="apppref";
    public static String APP_TOKEN  = "";
    public static String APP_TEL    = "";
    public static String CHECK_NUM  = "";
    public static String CHECK_DATE  = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(checks_details);

        apppref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        APP_TOKEN   = apppref.getString("APP_TOKEN", "unknown");
        APP_TEL     = apppref.getString("APP_TEL", "unknown");
        CHECK_NUM   = getIntent().getExtras().getString("check_num");
        CHECK_DATE  = getIntent().getExtras().getString("check_date");

        ScrollView nomList = findViewById(R.id.nom_list);

        TextView check_num_v = findViewById(R.id.products);

        check_num_v.setText(check_num_v.getText() + " " + CHECK_NUM);
        check_num_v.setHeight(200);
        getChecksDetails();
    }

    public void getChecksDetails(){

        HttpGetTask asT = new HttpGetTask("command="+"getChecksDetails"+"&token=" + APP_TOKEN + "&tel=" + APP_TEL+ "&checknum=" + CHECK_NUM);
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

        //-----------------------------------------------

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

        String status               = "";

        try {
            status = reader.getString("status");
        }catch (JSONException e){
            Toast.makeText(this, "Что-то пошло не так... " + ans, Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return;
        }

        //===============================================
        LinearLayout liner = findViewById(R.id.discounts_list);

        if(status.equals(ansOk)){
            JSONObject promoProduct = null;
            JSONObject checksObj   = null;
            JSONArray promoList     = null;
            try {
                promoList     = reader.getJSONArray("promosList"); //.getJSONArray(0);
            } catch (JSONException e) {
                Toast.makeText(this, "Не могу распарсить архив" + ans, Toast.LENGTH_LONG).show();
                e.printStackTrace();
                return;
            }

            for (int i = 0; i < promoList.length(); ++i) {
                //promoProduct = promoList.getJSONArray(i);
                try {
                    checksObj = promoList.getJSONObject(i);

                    PartFrameChecksDetails partFrame = new PartFrameChecksDetails(getApplicationContext());
                    //partFrame.setProdLogo(R.drawable.btn_ok); //Не удалять. Доработать, когда попросят картинку к акции
                    //partFrame.setPromotion_bg(R.drawable.akciya_bg);
                    partFrame.setDataParams(CHECK_DATE, checksObj.getString("nom_name"), checksObj.getString("nom_count"),
                                            checksObj.getString("nom_sum"), "", CHECK_NUM);
                    //setDataParams(String check_date_, String sale_nom_, String sale_kol_, String sale_sum_, String sale_text_, String p_checks_num)

                    liner.addView(partFrame);
                } catch (JSONException e) {
                    Toast.makeText(this, "Ошибка разборки объекта массива \n" + ans, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }
        else{
            Toast.makeText(this, "Wrong pass: " + ans, Toast.LENGTH_LONG).show();
            return;
        }
    }

    public void onClick_our_shop(View view) {

        Intent intent = new Intent(this, activity_onMap.class);
        //      intent.putExtra("name", "value");
        //      intent.putExtra("name2", "value2");
        startActivity(intent);
    }

    public void onClick_our_service(View view) {
        Intent intent = new Intent(this, activity_service.class);
        startActivity(intent);
    }

    public void getCheckDetails(View view) {
       // Toast.makeText(this, "Selected... ", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, checksDetail.class);
        startActivity(intent);
    }

    public void onClick_myCard(View view) {
        Intent intent = new Intent(this, checksActivity.class);
        startActivity(intent);
    }
}
