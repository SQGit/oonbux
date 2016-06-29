package sqindia.net.oonbux;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by Salman on 6/28/2016.
 */
public class PalAccept extends Activity {

    String server_data, str_img_url, str_name, str_oonbux_id, str_email, str_phone, str_status;
    com.rey.material.widget.TextView tv_name, tv_name_txt, tv_mail_txt, tv_oonbux_id, tv_oonbux_id_txt, tv_mail, tv_phone, tv_phone_txt;
    ImageView iview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pal_accept);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");

        tv_name = (com.rey.material.widget.TextView) findViewById(R.id.tv_name);
        tv_name_txt = (com.rey.material.widget.TextView) findViewById(R.id.tv_name_txt);
        tv_oonbux_id = (com.rey.material.widget.TextView) findViewById(R.id.tv_oonbuxid);
        tv_oonbux_id_txt = (com.rey.material.widget.TextView) findViewById(R.id.tv_oonbuxid_txt);
        tv_mail = (com.rey.material.widget.TextView) findViewById(R.id.tv_mail);
        tv_mail_txt = (com.rey.material.widget.TextView) findViewById(R.id.tv_mail_txt);
        tv_phone = (com.rey.material.widget.TextView) findViewById(R.id.tv_phone);
        tv_phone_txt = (com.rey.material.widget.TextView) findViewById(R.id.tv_phone_txt);
        iview = (ImageView) findViewById(R.id.imgview);

        tv_name.setTypeface(tf);
        tv_name_txt.setTypeface(tf);
        tv_oonbux_id.setTypeface(tf);
        tv_mail_txt.setTypeface(tf);
        tv_mail.setTypeface(tf);
        tv_oonbux_id_txt.setTypeface(tf);
        tv_phone_txt.setTypeface(tf);
        tv_phone.setTypeface(tf);


        server_data = getIntent().getStringExtra("get_Server");
        Log.e("tag", ":  " + server_data);

        try {
            JSONObject json_serv = new JSONObject(server_data);

            str_name = json_serv.getString("firstname") + " " + json_serv.getString("lastname");
            str_oonbux_id = json_serv.getString("oonbux_id");
            str_email = json_serv.getString("email");
            str_phone = json_serv.getString("loc_phone");
            str_status = json_serv.getString("status");
            str_img_url = json_serv.getString("photourl");


        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (!(str_img_url.isEmpty())) {

            Picasso.with(getApplicationContext())
                    .load(str_img_url)
                    .into(iview);
        }


        tv_name.setText(str_name);
        tv_oonbux_id.setText(str_oonbux_id);
        tv_mail.setText(str_email);
        tv_phone.setText(str_phone);


    }
}
