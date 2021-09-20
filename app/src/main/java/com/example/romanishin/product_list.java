package com.example.romanishin;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.testromanishin.R;

import java.util.List;

import static java.lang.Thread.sleep;

public class product_list extends AppCompatActivity {

    private List<View> allEd;
    private int counter = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list);

        LinearLayout liner = findViewById(R.id.linear);

        for (int i = 1; i <= 3; i++) {
            counter++;

//            View view = getLayoutInflater().inflate(R.layout.custom_layout, null , true);
//            //allEd = new ArrayList<>();
//            ImageView imV = view.findViewById(R.id.custom_image_3);
//
//            TextView tV_name = view.findViewById(R.id.textV_name);
//            tV_name.setText("Молочные" + counter);
//            TextView tV_des = view.findViewById(R.id.textV_descr);
//            tV_des.setText("Такие сосиски" + counter);
//
//            liner.addView(view);

//            PartFrame partFrame = new PartFrame(getApplicationContext());
//            partFrame.setProdLogo(R.drawable.btn_ok);
//            partFrame.setNameText("Milky " + counter);
//            partFrame.setDescrText("somthing like this " + counter);
//            liner.addView(partFrame);
            //Toast.makeText(this, "tV_name is null. ", Toast.LENGTH_LONG).show();
        }
    }

    public void onClick_btn(View v) throws InterruptedException {
//        counter++;
//
//        LinearLayout liner = findViewById(R.id.linear);
//
//        View view = getLayoutInflater().inflate(R.layout.custom_layout, null, false);
//
//        //allEd = new ArrayList<>();
//        ImageView imV = view.findViewById(R.id.custom_image_3);
//
//        TextView tV_name = view.findViewById(R.id.textV_name);
//        if (tV_name == null) {
//            Toast.makeText(this, "tV_name is null. ", Toast.LENGTH_LONG).show();
//        }
//        try {
//            tV_name.setText("Молочные" + counter);
//        } catch (Exception e) {
//            Toast.makeText(this, "Wrong name: " + e, Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        }
//
//
//        TextView tV_des = view.findViewById(R.id.textV_descr);
//        try {
//            tV_des.setText("Такие сосиски" + counter);
//        } catch (Exception e) {
//            Toast.makeText(this, "Wrong descr: " + e, Toast.LENGTH_LONG).show();
//            e.printStackTrace();
//        }
//
//
//        liner.addView(view);
//        //allEd.add(view);

    }
}

