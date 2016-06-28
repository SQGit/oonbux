package sqindia.net.oonbux;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Salman on 6/28/2016.
 */
public class PalAccept extends Activity {

    String server_data,str_img_url,str_name,str_oonbux_id,str_email,str_phone,str_status;
    com.rey.material.widget.TextView tv_size, tv_price, tv_size_txt, tv_pickup, tv_pickup_txt, tv_price_txt, tv_phone;
    ImageView iview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pal_accept);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");

        tv_size = (com.rey.material.widget.TextView) findViewById(R.id.tv_name);
        tv_price = (com.rey.material.widget.TextView) findViewById(R.id.tv_price);
        tv_pickup = (com.rey.material.widget.TextView) findViewById(R.id.tv_oonbuxid);
        tv_pickup_txt = (com.rey.material.widget.TextView) findViewById(R.id.tv_pickup_txt);
        tv_price_txt = (com.rey.material.widget.TextView) findViewById(R.id.tv_mail);
        tv_size_txt = (com.rey.material.widget.TextView) findViewById(R.id.tv_size_txt);
        tv_phone = (com.rey.material.widget.TextView) findViewById(R.id.tv_phone);
        iview = (ImageView) findViewById(R.id.imgview);

        tv_size.setTypeface(tf);
        tv_price.setTypeface(tf);
        tv_pickup.setTypeface(tf);
        tv_size_txt.setTypeface(tf);
        tv_price_txt.setTypeface(tf);
        tv_pickup_txt.setTypeface(tf);


        server_data = getIntent().getStringExtra("get_Server");
        Log.e("tag",":  " + server_data);

        try {
            JSONObject json_serv = new JSONObject(server_data);

            str_name = json_serv.getString("firstname")+" "+ json_serv.getString("lastname");
            str_oonbux_id =  json_serv.getString("oonbux_id");
            str_email = json_serv.getString("email");
            str_phone = json_serv.getString("loc_phone");
            str_status = json_serv.getString("status");
            str_img_url = json_serv.getString("photourl");


        } catch (JSONException e) {
            e.printStackTrace();
        }





    }
}
