package com.example.romanishin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testromanishin.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class registrationCheckSMS extends AppCompatActivity {

    public static final String APP_PREFERENCES="apppref";
    public String name1 = "";
    public String name2 = "";
    public String name3 = "";
    public String name4 = "";

    public static String APP_TOKEN  = "";
    public static String APP_TEL    = "";
    public String APP_SMS           = "";

    public SharedPreferences apppref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_check_sms);

        apppref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        APP_TEL = apppref.getString("APP_TEL", "unknown");

        name1   = getIntent().getExtras().getString("name1");
        name2   = getIntent().getExtras().getString("name2");
        name3   = getIntent().getExtras().getString("name3");
        name4   = getIntent().getExtras().getString("name4");
    }

    public void checkBySMS(){
        HttpGetTask asT = new HttpGetTask("command="+"checkSMS"+"&token=" + APP_SMS + "&tel=" + APP_TEL + "&name1=" + name1 + "&name2=" + name2 + "&name3=" + name3 + "&name4=" + name4);

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
        String card_u_id = "";
        String card_name            = "";
        String card_owner_name      = "";
        String card_scheme          = "";
        String card_scheme_value    = "";
        String card_balance         = "";
        String card_uid             = "";

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
            status              = reader.getString("status");
            card_u_id           = reader.getString("card_u_id");
            card_name           = reader.getString("card_name");
            card_owner_name     = reader.getString("card_owner_name");
            card_scheme         = reader.getString("card_scheme");
            card_scheme_value   = reader.getString("card_scheme_value");
            card_balance        = reader.getString("card_balance");
        }catch (JSONException e){
            Toast.makeText(this, "??????-???? ?????????? ???? ??????... " + ans, Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return;
        }

        //===============================================

        if((status.equals(ansOk)) && (!card_u_id.equals(""))){
            APP_TOKEN = card_u_id;
            SharedPreferences.Editor editor = apppref.edit();
            editor.putString("APP_TOKEN", APP_TOKEN);
            editor.putString("APP_TEL", APP_TEL);
            editor.apply();

            Intent intent = new Intent(this, cardsActivity.class);
            intent.putExtra("card_name", card_name);
            intent.putExtra("card_owner_name", card_owner_name);
            intent.putExtra("card_scheme", card_scheme);
            intent.putExtra("card_scheme_value", card_scheme_value);
            intent.putExtra("card_balance", card_balance);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "???? ?????????????? ???????????????? ???????????????????? ?????????? ???? ??????????????. ?????????????????? ???????????????? ?? ???????????????????? ??????????...", Toast.LENGTH_LONG).show();
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
       // getAccountData(APP_TOKEN, APP_TEL);
        TextView telText = findViewById(R.id.telText);
        APP_SMS = telText.getText().toString(); // ?????? ???? ??????

        if(hasConnection(this)) {
//            Toast.makeText(this, "?????????? ???????????????????? " + (hasConnection(this)) + " /" + (telText.getText()) + "/", Toast.LENGTH_LONG).show();
//            Toast.makeText(this, "command="+"checkSMS"+"&token=" + APP_TOKEN + "&tel=" + APP_TEL + "&name1" + name1 + "&name2" + name2 + "&name3" + name3 + "&name4" + name4, Toast.LENGTH_LONG).show();
            checkBySMS();
        }
        else Toast.makeText(this, "?????????????????? ????????????????????: " + (hasConnection(this)) + " /"  + (telText.getText()) + "/", Toast.LENGTH_LONG).show();
    }
}
