package com.example.romanishin;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testromanishin.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class registrationForm extends AppCompatActivity {

    public static final String APP_PREFERENCES="apppref";

    public static String APP_TOKEN  = "";
    public static String APP_TEL    = "";
    private int mYear, mMonth, mDay, mHour, mMinute;

    public SharedPreferences apppref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        apppref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        final TextView dateTV = findViewById(R.id.name4Text);
        View dateBtn = findViewById(R.id.imageButtonCalendar);

        dateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // получаем текущую дату
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(registrationForm.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                dateTV.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
            }
        });

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // получаем текущую дату
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dpd = new DatePickerDialog(registrationForm.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Display Selected date in textbox
                                dateTV.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        }, mYear, mMonth, mDay);
                dpd.show();
            }
        });
    }

    public void checkBySMS(){

        TextView telText = findViewById(R.id.telText);
        String telNum = "";
        try {
             telNum = telText.getText().toString();
        }catch (Exception e) {
            Toast.makeText(this, "Can't convert to string. " + e, Toast.LENGTH_LONG).show();
        }

        if(telNum.length() == 11){
            try {
                Double i = Double.parseDouble(telNum);
            }catch (Exception e){
                Toast.makeText(this, "Can't deparse INT " + telNum, Toast.LENGTH_LONG).show();
                return;
            }
            String eight = telNum.substring(0,  1);
            if(!eight.equals("8")) {
                Toast.makeText(this, "11 simb, but no 8 " + telNum, Toast.LENGTH_LONG).show();
                return;
            }
            APP_TEL = telNum;

        }else if(telNum.length() == 12){
            try {
                Double i = Double.parseDouble(telNum);
            }catch (Exception e){
                Toast.makeText(this, "Can't deparse INT " + telNum, Toast.LENGTH_LONG).show();
                return;
            }
            String plusSeven = telNum.substring(0,  2);
            if(!plusSeven.equals("+7")) {
                Toast.makeText(this, "12 simb, but no +7 " + telNum, Toast.LENGTH_LONG).show();
                return;
            }
            APP_TEL = "8" + telNum.substring(2);
            Toast.makeText(this, "APP_TEL " + APP_TEL, Toast.LENGTH_LONG).show();
        }else{
            return;
        }

        SharedPreferences.Editor editor = apppref.edit();
        editor.putString("APP_TEL", APP_TEL);
        editor.apply();

        HttpGetTask asT = new HttpGetTask("command="+"checkBySMS"+"&token=" + APP_TOKEN + "&tel=" + APP_TEL);
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
            Toast.makeText(this, "No connection to server! " + ans, Toast.LENGTH_LONG).show();
        }

        //-----------------------------------------------
        String status   = "";
        String statusCode = "";
        String ansMessage = "";

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

        try {
            status      = reader.getString("status");
            statusCode  = reader.getString("success");
            ansMessage  = reader.getString("message");
        }catch (JSONException e){
            Toast.makeText(this, "Что-то пошло не так... " + ans, Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return;
        }

        //===============================================

        if(status.equals(ansOk)){
            TextView name1Text_tv   = findViewById(R.id.name1Text);
            String name1Text        = name1Text_tv.getText().toString();

            TextView name2Text_tv   = findViewById(R.id.name2Text);
            String name2Text        = name2Text_tv.getText().toString();

            TextView name3Text_tv   = findViewById(R.id.name3Text);
            String name3Text        = name3Text_tv.getText().toString();

            TextView name4Text_tv   = findViewById(R.id.name4Text);
            String name4Text        = name4Text_tv.getText().toString();

            Intent intent = new Intent(this, registrationCheckSMS.class);
            intent.putExtra("name1", name1Text);
            intent.putExtra("name2", name2Text);
            intent.putExtra("name3", name3Text);
            intent.putExtra("name4", name4Text);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Не удалось получить корректный ответ от сервера. Проверьте Интернет и попробуйте позже... \n" + status + "\n" + statusCode  + "\n" + ansMessage, Toast.LENGTH_LONG).show();
            return;
        }
    }

    public boolean hasConnection(final Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNW = cm.getActiveNetworkInfo();
        if (activeNW != null && activeNW.isConnected())
        {
            return true;
        }
        return false;
    }

    public void snd_onClick(View view) {

        TextView telText = findViewById(R.id.telText);

        if(hasConnection(this) && (telText.getText().length() > 10) && (telText.getText().length() < 13)) {
            Toast.makeText(this, "Можно отправлять " + (hasConnection(this)) + " /" + (telText.getText()) + "/", Toast.LENGTH_LONG).show();
            checkBySMS();
        }
        else Toast.makeText(this, "Отправить невозвожно: " + (hasConnection(this)) + " /"  + (telText.getText()) + "/", Toast.LENGTH_LONG).show();
    }


}
