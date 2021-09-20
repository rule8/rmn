package com.example.romanishin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.testromanishin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Random;

public class fragmentStartBanners extends Fragment {

    static final String TAG = "romLogs";
    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    static final String ARGUMENT_JSON_TEXT = "";
    static String SRV_ANS = "";
    int pageNumber;
    int backColor;

    static fragmentStartBanners newInstance(int page, String JSON_STR) {
        fragmentStartBanners fragmentStBan = new fragmentStartBanners();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        arguments.putString(ARGUMENT_JSON_TEXT, JSON_STR);

        fragmentStBan.setArguments(arguments);
        return fragmentStBan;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
        SRV_ANS = getArguments().getString(ARGUMENT_JSON_TEXT);
        //pageNumber = 5;
        Random rnd = new Random();
        backColor = Color.argb(40, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_banners, null);

//        TextView tvPage = (TextView) view.findViewById(R.id.tvPage);
//        tvPage.setText("Page " + SRV_ANS);
//        tvPage.setBackgroundColor(backColor);

        //===== Начало тестового блока
        String ans="No result", ansOk="Ok";

        JSONObject reader = null;
        if(SRV_ANS.equals("null")){
            //Toast.makeText(this, "Null answer. Return... " + SRV_ANS, Toast.LENGTH_LONG).show();
            //return;
        }

        try {
            reader = new JSONObject(SRV_ANS);
        } catch (JSONException e) {
            // Toast.makeText(this, "can't create JSON reader" + ans, Toast.LENGTH_LONG).show();
            e.printStackTrace();
            //return;
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
            //Toast.makeText(this, "Что-то пошло не так... ", Toast.LENGTH_LONG).show();
            e.printStackTrace();
            //return;
        }

        //===============================================
        LinearLayout liner = (LinearLayout) view.findViewById(R.id.discounts_list);
        liner.removeAllViews();

        if(status.equals(ansOk)){
            JSONObject promoProduct = null;
            JSONObject checksObj   = null;
            JSONArray promoList     = null;
            try {
                promoList     = reader.getJSONArray("promosList"); //.getJSONArray(0);

            } catch (JSONException e) {
                //Toast.makeText(this, "Не могу распарсить архив" + ans, Toast.LENGTH_LONG).show();
                e.printStackTrace();
                //return;
            }

            for (int i = 0; i < promoList.length(); ++i) {
                //promoProduct = promoList.getJSONArray(i);
                try {
                    checksObj = promoList.getJSONObject(i);

                    final PartFrameChecks partFrame = new PartFrameChecks(view.getContext()); //getApplicationContext()
                    partFrame.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getCheckDetails(partFrame.getChecks_num(), partFrame.getChecks_date());
                        }
                    });
                    //partFrame.setProdLogo(R.drawable.btn_ok); //Не удалять. Доработать, когда попросят картинку к акции
                    //partFrame.setPromotion_bg(R.drawable.akciya_bg);
                    try {
                        partFrame.setCheck_date(checksObj.getString("checks_date"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    //partFrame.setSale_(checksObj.getString("Сумма покупки:"));
                    partFrame.setSale_("Сумма покупки:", checksObj.getString("checks_mag"), checksObj.getString("checks_num"));
                    partFrame.setSale_sum(checksObj.getString("checks_summ") + " руб.");
                    partFrame.setChecks_num(checksObj.getString("checks_num"));

                    int pageNumberCnt = pageNumber + 1;

                    if (((pageNumber * 10) < i) && (i <= (pageNumberCnt*10))) {
                        Log.d(TAG, "onPageSelected, position = " + pageNumber);
                        liner.addView(partFrame);
                    }
                } catch (JSONException e) {
                    //Toast.makeText(this, "Ошибка разборки объекта массива \n" + ans, Toast.LENGTH_LONG).show();
                    Log.d(TAG, "onPageSelected, position = " + pageNumber);
                    e.printStackTrace();
                }
            }
        }
        else{
            //Toast.makeText(this, "Wrong pass: " + ans, Toast.LENGTH_LONG).show();
            //return;
        }
        //==== Конец тестового блока

        return view;
    }

    public void getCheckDetails(String check_num, String check_date) {
        //  Toast.makeText(this, "Ок: " + check_num, Toast.LENGTH_LONG).show();

        Intent intent = new Intent(getView().getContext(), checksDetail.class);
        intent.putExtra("check_num", check_num);
        intent.putExtra("check_date", check_date);
        startActivity(intent);
    }
}