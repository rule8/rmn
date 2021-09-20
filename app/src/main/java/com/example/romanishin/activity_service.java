package com.example.romanishin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.testromanishin.R;

public class activity_service extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
    }



/*
    public void pw_onClick(View view) {

        Intent intent = new Intent(this, cardsActivity.class);
        intent.putExtra("name", "value");
        intent.putExtra("name2", "value2");
        startActivity(intent);
    }

    public void onClick_loginText(View view) {
        TextView loginText = findViewById(R.id.loginText);
        loginText.setText("");
    }*/

    public void onClick_our_myCard(View view) {

        Intent intent = new Intent(this, cardsActivity.class);
        startActivity(intent);

    }

    public void onClick_our_onMap(View view) {
        Intent intent = new Intent(this, activity_onMap.class);
        startActivity(intent);
    }

    public void onClick_service(View view) {
//        Intent intent = new Intent(this, activity_service.class);
//        startActivity(intent);
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

    public void call_btn_onClick(View view) {
//        Intent intent = new Intent(this, checksActivity.class);
//        startActivity(intent);
    }


    public void mail_btn_onClick(View view) {
//        Intent intent = new Intent(this, checksActivity.class);
//        startActivity(intent);
    }

}
