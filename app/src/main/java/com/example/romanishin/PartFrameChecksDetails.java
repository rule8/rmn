package com.example.romanishin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testromanishin.R;
import com.example.testromanishin.R.id;

public class PartFrameChecksDetails extends RelativeLayout {
    //Не удалять. Доработать, когда попросят картинку к акции
    // private ImageView prod_logo;
   // private ImageView promotion_bg;
    private TextView check_date;
    private TextView sale_nom;
    private TextView sale_kol;
    private TextView sale_sum;
    private TextView sale_text;
    private String checks_num;

    private View view;
    private Context contextLocalVar;

    public PartFrameChecksDetails(Context context) {
        super(context);
        initComponents(context);
    }

    private void initComponents(Context context) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.custom_checks_details_layout, this);

        //Не удалять. Доработать, когда попросят картинку к акции
        //prod_logo   = view.findViewById(R.id.);
        //promotion_bg        = view.findViewById(id.promotion_bg);
        check_date  = view.findViewById(id.product_code);
        sale_nom     = view.findViewById(id.product_nom);
        sale_kol     = view.findViewById(id.product_add);
        sale_sum     = view.findViewById(id.product_price);
        sale_text    = view.findViewById(id.product_descr);
        checks_num  = "";

        contextLocalVar = context;
    };
//      Не удалять. Доработать, когда попросят картинку к акции
//    public void setProdLogo(int resID) {
//        if (prod_logo != null) {
//            prod_logo.setImageResource(resID);
//        } else Toast.makeText(contextLocalVar, "prod_logo is null. ", Toast.LENGTH_LONG).show();
//    }

//    public void setPromotion_bg(int resID) {
//        if (promotion_bg != null) {
//            promotion_bg.setImageResource(resID);
//        } else Toast.makeText(contextLocalVar, "prod_logo is null. ", Toast.LENGTH_LONG).show();
//    }

//    public void setDataParams(String text) {
//        if(check_date != null) check_date.setText(text);
//        else Toast.makeText(contextLocalVar, "check_date is null. ", Toast.LENGTH_LONG).show();
//    }

    public void setDataParams(String check_date_, String sale_nom_, String sale_kol_, String sale_sum_, String sale_text_, String p_checks_num) {
        if((check_date != null) && (sale_nom != null) && (sale_kol != null) && (sale_sum != null) && (sale_text != null)) {
            checks_num = p_checks_num;
            check_date.setText(check_date_);
            sale_nom.setText(sale_nom_);
            sale_kol.setText("x " + sale_kol_);
            sale_sum.setText(sale_sum_);
        }
        else Toast.makeText(contextLocalVar, "sale_ is null. ", Toast.LENGTH_LONG).show();
    }

    public String getChecks_num() {
        return checks_num;
    }

//    public void setPromotion_price_old(String text) {
//        String old_price_text = text;
//        promotion_price_old.setPaintFlags(promotion_price_old.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
//        promotion_price_old.setText(old_price_text);
//        if(promotion_price_old!=null) promotion_price_old.setText(text);
//        else Toast.makeText(contextLocalVar, "name_text is null. ", Toast.LENGTH_LONG).show();
//    }


}
