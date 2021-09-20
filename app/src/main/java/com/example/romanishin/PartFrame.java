package com.example.romanishin;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testromanishin.R;
import com.example.testromanishin.R.id;

public class PartFrame extends RelativeLayout {
    //Не удалять. Доработать, когда попросят картинку к акции
    // private ImageView prod_logo;
    private ImageView promotion_bg;
    private TextView promotion_period;
    private TextView promotion_product;
    private TextView promotion_price_old;
    private TextView promotion_price_new;
    private View view;
    private Context contextLocalVar;

    public PartFrame(Context context) {
        super(context);
        initComponents(context);
    }

    private void initComponents(Context context) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_layout, this);

        //Не удалять. Доработать, когда попросят картинку к акции
        //prod_logo   = view.findViewById(R.id.);
        promotion_bg        = view.findViewById(id.promotion_bg);
        promotion_period   = view.findViewById(id.promotion_period);
        promotion_product  = view.findViewById(R.id.promotion_product);
        promotion_price_old  = view.findViewById(R.id.promotion_price_old);
        promotion_price_new  = view.findViewById(R.id.promotion_price_new);
        contextLocalVar = context;
    }
//      Не удалять. Доработать, когда попросят картинку к акции
//    public void setProdLogo(int resID) {
//        if (prod_logo != null) {
//            prod_logo.setImageResource(resID);
//        } else Toast.makeText(contextLocalVar, "prod_logo is null. ", Toast.LENGTH_LONG).show();
//    }

    public void setPromotion_bg(int resID) {
        if (promotion_bg != null) {
            promotion_bg.setImageResource(resID);
        } else Toast.makeText(contextLocalVar, "prod_logo is null. ", Toast.LENGTH_LONG).show();
    }

    public void setPromotion_period(String text) {
        if(promotion_period != null) promotion_period.setText(text);
        else Toast.makeText(contextLocalVar, "name_text is null. ", Toast.LENGTH_LONG).show();
    }

    public void setPromotion_product(String text) {
        if(promotion_product!=null) promotion_product.setText(text);
        else Toast.makeText(contextLocalVar, "name_text is null. ", Toast.LENGTH_LONG).show();
    }

    public void setPromotion_price_old(String text) {
        String old_price_text = text;
        promotion_price_old.setPaintFlags(promotion_price_old.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        promotion_price_old.setText(old_price_text);
        if(promotion_price_old!=null) promotion_price_old.setText(text);
        else Toast.makeText(contextLocalVar, "name_text is null. ", Toast.LENGTH_LONG).show();
    }

    public void setPromotion_price_new(String text) {
        if(promotion_price_new!=null) promotion_price_new.setText(text);
        else Toast.makeText(contextLocalVar, "name_text is null. ", Toast.LENGTH_LONG).show();
    }

}
