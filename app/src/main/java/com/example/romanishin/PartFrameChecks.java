package com.example.romanishin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testromanishin.R;
import com.example.testromanishin.R.id;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PartFrameChecks extends RelativeLayout {

    private TextView check_date;
    private TextView sale_;
    private TextView sale_sum;
    private TextView sale_mag;
    private String checks_num;

    private View view;
    private Context contextLocalVar;

    public PartFrameChecks(Context context) {
        super(context);
        initComponents(context);
    }

    private void initComponents(Context context) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_checks_layout, this);

        check_date  = view.findViewById(id.product_code);
        sale_       = view.findViewById(id.product_descr);
        sale_sum    = view.findViewById(id.product_price);
        sale_mag    = view.findViewById(id.product_nom);
        checks_num  = "";

        contextLocalVar = context;
    };

    public void setCheck_date(String text) throws ParseException {
        String frmText = "";
        //frmText = text.substring(8, 10) + "." + text.substring(5, 7) + "." + text.substring(0, 4) + " " + text.substring(11);
        Date date;
        SimpleDateFormat df         = new SimpleDateFormat("EEEE, d MMMM yyyy HH:mm:ss");
        SimpleDateFormat frmDate    = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = frmDate.parse(text);
            frmText = frmDate.format(date);
            frmText = df.format(date);
            //frmText = String.format(Locale.getDefault(), "%tr\n", date);
        }
        catch (ParseException e){
            e.printStackTrace();
        }
        if(frmText.equals("")) frmText = text.substring(8, 10) + "." + text.substring(5, 7) + "." + text.substring(0, 4) + " " + text.substring(11);

        if(check_date != null) check_date.setText(frmText);
        else Toast.makeText(contextLocalVar, "check_date is null. ", Toast.LENGTH_LONG).show();
    }

    public void setSale_(String text, String mag, String p_checks_num) {
        if((sale_ != null) && (sale_mag != null)) {
            checks_num = p_checks_num;
            sale_.setText(text);
            sale_mag.setText(mag + "\n" + checks_num);

        }
        else Toast.makeText(contextLocalVar, "sale_ is null. ", Toast.LENGTH_LONG).show();
    }

    public void setSale_sum(String text) {
        if(sale_sum!=null) sale_sum.setText(text);
        else Toast.makeText(contextLocalVar, "sale_sum is null. ", Toast.LENGTH_LONG).show();
    }

    public void setChecks_num(String text) {
        //checks_num = text;
    }

    public String getChecks_num() {
        return checks_num;
    }

    public String getChecks_date() {
        return check_date.getText().toString();
    }
}
