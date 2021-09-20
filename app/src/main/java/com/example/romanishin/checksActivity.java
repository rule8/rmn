package com.example.romanishin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testromanishin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

import static com.example.testromanishin.R.layout.checks_list;

public class checksActivity extends AppCompatActivity{

    public SharedPreferences apppref;
    public static final String APP_PREFERENCES="apppref";
    public static String APP_TOKEN  = "";
    public static String APP_TEL    = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(checks_list);

        apppref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        APP_TOKEN   = apppref.getString("APP_TOKEN", "unknown");
        APP_TEL     = apppref.getString("APP_TEL", "unknown");

        getChecks(APP_TOKEN, APP_TEL);
    }

    public void getChecks(String token, String tel){
        HttpGetTask asT = new HttpGetTask("command="+"getChecks"+"&token=" + APP_TOKEN + "&tel=" + APP_TEL);
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
        String card_name            = "";
        String card_owner_name      = "";
        String card_scheme          = "";
        String card_scheme_value    = "";
        String card_balance         = "";
        String card_uid             = "";

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

                    final PartFrameChecks partFrame = new PartFrameChecks(getApplicationContext());
                    partFrame.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getCheckDetails(partFrame.getChecks_num(), partFrame.getChecks_date());
                        }
                    });
                    //partFrame.setProdLogo(R.drawable.btn_ok); //Не удалять. Доработать, когда попросят картинку к акции
                    //partFrame.setPromotion_bg(R.drawable.akciya_bg);
                    partFrame.setCheck_date(checksObj.getString("checks_date"));
                    //partFrame.setSale_(checksObj.getString("Сумма покупки:"));
                    partFrame.setSale_("Сумма покупки:", checksObj.getString("checks_mag"), checksObj.getString("checks_num"));
                    partFrame.setSale_sum(checksObj.getString("checks_summ") + " руб.");
                    partFrame.setChecks_num(checksObj.getString("checks_num"));

                    liner.addView(partFrame);
                } catch (JSONException | ParseException e) {
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
        startActivity(intent);
    }

    public void onClick_our_service(View view) {
        Intent intent = new Intent(this, activity_service.class);
        startActivity(intent);
    }

    public void getCheckDetails(String check_num, String check_date) {
      //  Toast.makeText(this, "Ок: " + check_num, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, checksDetail.class);
        intent.putExtra("check_num", check_num);
        intent.putExtra("check_date", check_date);
        startActivity(intent);
    }

    public void onClick_myCard(View view) {
//        Intent intent = new Intent(this, checksActivity.class);
//        startActivity(intent);
    }
}
