package com.example.romanishin;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.testromanishin.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // setContentView(R.layout.activity_2);
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        intent.putExtra("name", "value");
        intent.putExtra("name2", "value2");
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void autoris_onClick(View view) {



        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        intent.putExtra("name", "value");
        intent.putExtra("name2", "value2");
        startActivity(intent);
    }

    public void btn_ok_click(View view) {
        Intent intent = new Intent(MainActivity.this, activity_onMap.class);
        intent.putExtra("name", "value");
        intent.putExtra("name2", "value2");
        startActivity(intent);
    }
}
