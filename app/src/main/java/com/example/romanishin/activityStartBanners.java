package com.example.romanishin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.testromanishin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.concurrent.ExecutionException;

public class activityStartBanners extends FragmentActivity {

    static String JSON_STR = "";
    static final String TAG = "myLogs";
    static final int PAGE_COUNT = 10;

    public SharedPreferences apppref;
    public static final String APP_PREFERENCES="apppref";
    public static String APP_TOKEN  = "";
    public static String APP_TEL    = "";

    ViewPager pager;
    PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_banners);

        apppref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        APP_TOKEN   = apppref.getString("APP_TOKEN", "unknown");
        APP_TEL     = apppref.getString("APP_TEL", "unknown");

        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), 3);
        pager.setAdapter(pagerAdapter);
        pager.setPageTransformer(true, new DepthPageTransformer());

        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected, position = " + position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                Log.d(TAG, "onPageScrolled, position = " + position + "/" + positionOffset + " / " + positionOffsetPixels);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "onPageScrollStateChanged, posistatetion = " + state);
            }
        });

        getChecks(APP_TOKEN, APP_TEL);
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        @Override
        public CharSequence getPageTitle(int position) {
            int posTitle = position + 1;
            return "--- " + posTitle;
        }

        public MyFragmentPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @Override
        public Fragment getItem(int position) {
            //return checksActivity.newInstance(position); //newInstance(position);
            return fragmentStartBanners.newInstance(position, JSON_STR);
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
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

        JSON_STR = ans;
        //-----------------------------------------------

    }


}