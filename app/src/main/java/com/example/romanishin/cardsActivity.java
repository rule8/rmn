package com.example.romanishin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testromanishin.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static com.example.testromanishin.R.layout.activity_card;

public class cardsActivity extends AppCompatActivity {

    public SharedPreferences apppref;
    public static final String APP_PREFERENCES="apppref";
    public static String APP_TOKEN  = "";
    public static String APP_TEL    = "";

    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;
    private ImageView bar_code;
    public String ean_code ="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_card);

        apppref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        APP_TOKEN   = apppref.getString("token", "unknown");
        APP_TEL     = apppref.getString("tel", "unknown");

        TextView card_owner_name_tv     = findViewById(R.id.products);
        TextView card_balance_tv        = findViewById(R.id.card_balance);
        TextView card_scheme_value_tv   = findViewById(R.id.card_scheme_value);

        String card_name                = getIntent().getExtras().getString("card_name");
        String card_owner_name          = getIntent().getExtras().getString("card_owner_name");
        String card_scheme              = getIntent().getExtras().getString("card_scheme");
        String card_scheme_value        = getIntent().getExtras().getString("card_scheme_value");
        String card_balance             = getIntent().getExtras().getString("card_balance");

        card_balance_tv.setText(card_balance);
        card_owner_name_tv.setText(card_owner_name);
        card_scheme_value_tv.setText(card_scheme_value);

        LinearLayout liner = findViewById(R.id.discounts_list);
        int counter = 0;

//        // генерим EAN13
//        TextView card_shk = findViewById(R.id.card_shk);
//        ean13CodeBuilder bb = new ean13CodeBuilder("9898000090517");
//        card_shk.setText(bb.getCode());
//        //===============

        // пробуем генерить че-нить еще
        bar_code = findViewById(R.id.bar_code);
        ean_code = card_name;

        Bitmap barcode_bitmap;
        try {
            barcode_bitmap = encodeAsBitmap(ean_code, BarcodeFormat.QR_CODE, 200, 200);
            bar_code.setImageBitmap(barcode_bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        //=============================

        getProductsPromo(APP_TOKEN, APP_TEL);

//        for (int i = 1; i <= 1; i++) {
//            counter++;
//
//            PartFrame partFrame = new PartFrame(getApplicationContext());
//            //partFrame.setProdLogo(R.drawable.btn_ok); //Не удалять. Доработать, когда попросят картинку к акции
//            partFrame.setPromotion_bg(R.drawable.akciya_bg);
//            partFrame.setPromotion_period("Акция! C 21.05.20 по 21.06.20 (" + counter + ")");
//            partFrame.setPromotion_product("Сосиски МОЛОЧНЫЕ. 415гр. (" + counter + ")");
//            partFrame.setPromotion_price_old("147 р. (" + counter + ")");
//            partFrame.setPromotion_price_new("115 р. (" + counter + ")");
//
//            liner.addView(partFrame);
//            //Toast.makeText(this, "tV_name is null. ", Toast.LENGTH_LONG).show();
//        }
    }

    public void getProductsPromo(String token, String tel){

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

        HttpGetTask asT = new HttpGetTask("command="+"getProductsPromo"+"&token=" + APP_TOKEN + "&tel=" + APP_TEL);
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
        LinearLayout liner = findViewById(R.id.discounts_list);

        if(status.equals(ansOk)){
            JSONObject promoProduct = null;
            try {
                JSONArray promoList     = reader.getJSONArray("promosList"); //.getJSONArray(0);
                for (int i = 0; i < promoList.length(); ++i) {
                    //promoProduct = promoList.getJSONArray(i);
                    promoProduct = promoList.getJSONObject(i);

                    card_name = card_name + " " + promoProduct.getString("promo_code") + " " + promoProduct.getString("promo_name");

                    PartFrame partFrame = new PartFrame(getApplicationContext());
                    //partFrame.setProdLogo(R.drawable.btn_ok); //Не удалять. Доработать, когда попросят картинку к акции
                    partFrame.setPromotion_bg(R.drawable.akciya_bg);
                    partFrame.setPromotion_period("C " + promoProduct.getString("promo_beg") + " по " + promoProduct.getString("promo_end"));
                    partFrame.setPromotion_product(promoProduct.getString("promo_name"));
                    partFrame.setPromotion_price_old(promoProduct.getString("promo_price_old") + " руб.");
                    partFrame.setPromotion_price_new(promoProduct.getString("promo_price_new") + " руб.");

                    liner.addView(partFrame);
                }
                //Toast.makeText(this, "card_name: " + card_name, Toast.LENGTH_LONG).show();
//                card_owner_name     = reader.getString("card_owner_name");
//                card_scheme         = reader.getString("card_scheme");
//                card_scheme_value   = reader.getString("card_scheme_value");
//                card_balance        = reader.getString("card_balance");
            } catch (JSONException e) {
                Toast.makeText(this, "Не могу распарсить архив" + ans, Toast.LENGTH_LONG).show();
                e.printStackTrace();
                return;
            }

//            SharedPreferences.Editor editor = apppref.edit();
//            editor.putString("token", "aa352f58-ba40-11e7-b1db-0014d1205e26");
//            editor.putString("tel", "89206623797");
//            editor.apply();

//            Intent intent = new Intent(this, cardsActivity.class);
//            intent.putExtra("card_name", card_name);
//            intent.putExtra("card_owner_name", card_owner_name);
//            intent.putExtra("card_scheme", card_scheme);
//            intent.putExtra("card_scheme_value", card_scheme_value);
//            intent.putExtra("card_balance", card_balance);
//
//            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Wrong pass: " + ans, Toast.LENGTH_LONG).show();
            return;
        }

    }

    public void onClick_our_shop(View view) {

        Intent intent = new Intent(this, activity_onMap.class);
        //      intent.putExtra("name", "value");
        //      intent.putExtra("name2", "value2");
        startActivity(intent);

    }

    public void onClick_our_service(View view) {
        Intent intent = new Intent(this, activity_service.class);
        //Intent intent = new Intent(this, activityStartBanners.class);
        startActivity(intent);
    }

    public void onClick_myCard(View view) {
        //Intent intent = new Intent(this, checksActivity.class);
        Intent intent = new Intent(this, activityStartBanners.class);
        startActivity(intent);
    }

    public void onClick_catalog(View view) {
        Intent intent = new Intent(this, catalog_web.class);
        //Intent intent = new Intent(this, catalog.class);
        intent.putExtra("productType", "all");
        startActivity(intent);
    }

    private Bitmap encodeAsBitmap(String code, BarcodeFormat format, int img_width, int img_height) throws WriterException {
        if (code == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(code);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        MultiFormatWriter writer = new MultiFormatWriter();
        BitMatrix result;
        try {
            result = writer.encode(code, format, img_width, img_height, hints);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

}
