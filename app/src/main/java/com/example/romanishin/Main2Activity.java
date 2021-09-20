package com.example.romanishin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testromanishin.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import static java.lang.Thread.sleep;

public class Main2Activity extends AppCompatActivity {

    public static final String APP_PREFERENCES="apppref";

    public static String APP_TOKEN  = "";
    public static String APP_TEL    = "";

    public SharedPreferences apppref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        apppref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        String  token    = apppref.getString("APP_TOKEN", "unknown");
        String  tel      = apppref.getString("APP_TEL", "unknown");

        if((token.equals("unknown")) || (tel.equals("unknown"))) {
            Toast.makeText(this, "No token or rel: " + token + " " + tel, Toast.LENGTH_LONG).show();
            getRegisterForm();
        }
        else {
            if(hasConnection(this)) {
              //Toast.makeText(this, "Active networks OK " + token + " " + tel, Toast.LENGTH_LONG).show();
              getAccountData(token, tel);
            }
            else  Toast.makeText(this, "No active networks... ", Toast.LENGTH_LONG).show();
        }

//        SharedPreferences.Editor editor = apppref.edit();
//        editor.putString("token", "aa352f58-ba40-11e7-b1db-0014d1205e26");
//        editor.putString("tel", "89206623797");
//        editor.apply();
    }

    public void getRegisterForm(){
        Intent intent = new Intent(this, registrationForm.class);

        startActivity(intent);
    }

    public void getAccountData(String token, String tel){

//        EditText login_v    = findViewById(R.id.loginText);
//        EditText pass_v     = findViewById(R.id.passText);
//        APP_PREFERENCES_login   = login_v.getText().toString();
//        APP_PREFERENCES_pass    = pass_v.getText().toString();
//        MD5hash passhash = new MD5hash(APP_PREFERENCES_pass);
//        String passHashString = passhash.MakeHash();
//        HttpGetTask asT = new HttpGetTask("command="+"log"+"&login="+APP_PREFERENCES_login+"&pass="+passHashString);
//        HttpGetTask asT = new HttpGetTask("command="+"log"+"&login="+APP_PREFERENCES_login+"&pass="+APP_PREFERENCES_pass);

        APP_TOKEN   = token;
        APP_TEL     = tel;

        HttpGetTask asT = new HttpGetTask("command="+"logByToken"+"&token=" + APP_TOKEN + "&tel=" + APP_TEL);
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

        if(status.equals(ansOk)){
            try {
                card_name           = reader.getString("card_name");
                card_owner_name     = reader.getString("card_owner_name");
                card_scheme         = reader.getString("card_scheme");
                card_scheme_value   = reader.getString("card_scheme_value");
                card_balance        = reader.getString("card_balance");
            } catch (JSONException e) {
                Toast.makeText(this, "Не могу распарсить архив" + ans, Toast.LENGTH_LONG).show();
                e.printStackTrace();
                return;
            }

//            SharedPreferences.Editor editor = apppref.edit();
//            editor.putString("token", "aa352f58-ba40-11e7-b1db-0014d1205e26");
//            editor.putString("tel", "89206623797");
//            editor.apply();

            Intent intent = new Intent(this, cardsActivity.class);
            intent.putExtra("card_name", card_name);
            intent.putExtra("card_owner_name", card_owner_name);
            intent.putExtra("card_scheme", card_scheme);
            intent.putExtra("card_scheme_value", card_scheme_value);
            intent.putExtra("card_balance", card_balance);

            startActivity(intent);
            finish();

//            intent.putExtra(MainActivity.JsonURL, response.toString());
//            startNewIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//            finish();
        }
        else{
            Toast.makeText(this, "Wrong pass: " + ans, Toast.LENGTH_LONG).show();
            return;
        }

    }

    public void pw_onClick(View view) throws InterruptedException, JSONException {

//        EditText login_v    = findViewById(R.id.loginText);
//
//        EditText pass_v     = findViewById(R.id.passText);
//
//        APP_PREFERENCES_login = login_v.getText().toString();
//        APP_PREFERENCES_pass = pass_v.getText().toString();
//
//        MD5hash passhash = new MD5hash(APP_PREFERENCES_pass);
//        String passHashString = passhash.MakeHash();
//
//        //HttpGetTask asT = new HttpGetTask("command="+"log"+"&login="+APP_PREFERENCES_login+"&pass="+passHashString);
//        HttpGetTask asT = new HttpGetTask("command="+"log"+"&login="+APP_PREFERENCES_login+"&pass="+APP_PREFERENCES_pass);
//        asT.execute();
//        String ans="No result", ansOk="Ok";
//        try {
//            ans = asT.get();
//        } catch (InterruptedException e) {
//            ans = "InterruptedException";
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            ans = "ExecutionException";
//            e.printStackTrace();
//        }
//        //-----------------------------------------------
//
//        JSONObject reader = null;
//        if(ans.equals("null")){
//            Toast.makeText(this, "Null answer. Return... " + ans, Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        try {
//            reader = new JSONObject(ans);
//        } catch (JSONException e) {
//            Toast.makeText(this, "can't create JSON reader" + ans, Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        }
//
//        String status               = "";
//        String card_name            = "";
//        String card_owner_name      = "";
//        String card_scheme          = "";
//        String card_scheme_value    = "";
//        String card_balance         = "";
//        String card_uid             = "";
//
//
//        try {
//          status = reader.getString("status");
//        }catch (JSONException e){
//            Toast.makeText(this, "Что-то пошло не так... " + ans, Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        }
//
//        //===============================================
//
//        if(status.equals(ansOk)){
//            try {
//                card_name           = reader.getString("card_name");
//                card_owner_name     = reader.getString("card_owner_name");
//                card_scheme         = reader.getString("card_scheme");
//                card_scheme_value   = reader.getString("card_scheme_value");
//                card_balance        = reader.getString("card_balance");
//            } catch (JSONException e) {
//                Toast.makeText(this, "Не могу распарсить архив" + ans, Toast.LENGTH_LONG).show();
//                e.printStackTrace();
//            }
//            // Сохраним данные
////            SharedPreferences.Editor editor = apppref.edit();
////            editor.putString("login", APP_PREFERENCES_login);
////            editor.putString("pass", APP_PREFERENCES_pass);
////            editor.putString("uid", "123456789-987654321");
////            editor.apply();
//
//            Intent intent = new Intent(this, cardsActivity.class);
//            intent.putExtra("card_name", card_name);
//            intent.putExtra("card_owner_name", card_owner_name);
//            intent.putExtra("card_scheme", card_scheme);
//            intent.putExtra("card_scheme_value", card_scheme_value);
//            intent.putExtra("card_balance", card_balance);
//
//            startActivity(intent);
//        }
//        else{
//            Toast.makeText(this, "Wrong pass: " + ans, Toast.LENGTH_LONG).show();
//        }
    }

    public void onClick_loginText(View view) {
        TextView loginText = findViewById(R.id.loginText);
        loginText.setText("");
    }

    public boolean hasConnection(final Context context)
    {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNW = cm.getActiveNetworkInfo();
        if (activeNW != null && activeNW.isConnected())
        {
            return true;
        }
        return false;
    }

    public void rmn_onClick(View view) {
        getAccountData(APP_TOKEN, APP_TEL);
    }
}
