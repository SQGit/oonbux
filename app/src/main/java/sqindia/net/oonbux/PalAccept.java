package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rey.material.widget.Button;

import com.rey.material.widget.TextView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Salman on 6/28/2016.
 */
public class PalAccept extends Activity {

    String server_data, str_img_url, str_name, str_oonbux_id, str_email, str_phone, str_status, str_sessionid, str_sts, sss;
    com.rey.material.widget.TextView tv_name, tv_name_txt, tv_mail_txt, tv_oonbux_id, tv_oonbux_id_txt, tv_mail, tv_phone, tv_phone_txt,tv_header,tv_shd_txt;
    ImageView iview;
    LinearLayout lt_back, lt_submit;
    FrameLayout lt_ok;
    Button btn_accept, btn_reject, btn_ok;
    SweetAlertDialog sweetDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pal_accept);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");
        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");

        tv_header = (TextView) findViewById(R.id.tv_hd_txt);
        tv_shd_txt = (TextView) findViewById(R.id.tv_shd_txt);

        tv_name = (com.rey.material.widget.TextView) findViewById(R.id.tv_name);
        tv_name_txt = (com.rey.material.widget.TextView) findViewById(R.id.tv_name_txt);
        tv_oonbux_id = (com.rey.material.widget.TextView) findViewById(R.id.tv_oonbuxid);
        tv_oonbux_id_txt = (com.rey.material.widget.TextView) findViewById(R.id.tv_oonbuxid_txt);
        tv_mail = (com.rey.material.widget.TextView) findViewById(R.id.tv_mail);
        tv_mail_txt = (com.rey.material.widget.TextView) findViewById(R.id.tv_mail_txt);
        tv_phone = (com.rey.material.widget.TextView) findViewById(R.id.tv_phone);
        tv_phone_txt = (com.rey.material.widget.TextView) findViewById(R.id.tv_phone_txt);
        iview = (ImageView) findViewById(R.id.imgview);
        lt_back = (LinearLayout) findViewById(R.id.layout_back);

        lt_submit = (LinearLayout) findViewById(R.id.ll3);
        lt_ok = (FrameLayout) findViewById(R.id.ll4);

        btn_accept = (Button) findViewById(R.id.button_accept);
        btn_reject = (Button) findViewById(R.id.button_reject);
        btn_ok = (Button) findViewById(R.id.button_close);

        tv_header.setTypeface(tf1);
        tv_shd_txt.setTypeface(tf);
        tv_name.setTypeface(tf);
        tv_name_txt.setTypeface(tf);
        tv_oonbux_id.setTypeface(tf);
        tv_mail_txt.setTypeface(tf);
        tv_mail.setTypeface(tf);
        tv_oonbux_id_txt.setTypeface(tf);
        tv_phone_txt.setTypeface(tf);
        tv_phone.setTypeface(tf);

        btn_accept.setTypeface(tf);
        btn_reject.setTypeface(tf);
        btn_ok.setTypeface(tf);


        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        str_sessionid = sharedPreferences.getString("sessionid", "");


        server_data = getIntent().getStringExtra("get_Server");
        Log.e("tag", ":  " + server_data);


        sss = getIntent().getStringExtra("sts");
        Log.e("tag", ":  " + sss);

        if (sss.equals("Request")) {
            lt_submit.setVisibility(View.VISIBLE);
            lt_ok.setVisibility(View.INVISIBLE);
            tv_header.setText("Pal Request");
            tv_shd_txt.setText("You have to accept or reject");

        } else {
            lt_ok.setVisibility(View.VISIBLE);
            lt_submit.setVisibility(View.INVISIBLE);
            tv_header.setText("Pal Respond");
            tv_shd_txt.setText("Pal accepted your request");
        }


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


        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goDash = new Intent(getApplicationContext(), DashBoardActivity.class);
                startActivity(goDash);
                finish();
            }
        });

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_sts = "ACCEPTED";
                new acceptPal().execute();
            }
        });


        btn_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_sts = "IGNORE";
                new acceptPal().execute();

            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goDash = new Intent(getApplicationContext(), DashBoardActivity.class);
                startActivity(goDash);
                finish();

            }
        });


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


    class acceptPal extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            sweetDialog = new SweetAlertDialog(PalAccept.this, SweetAlertDialog.PROGRESS_TYPE);
            sweetDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            sweetDialog.setTitleText("Loading");
            sweetDialog.setCancelable(false);
            sweetDialog.show();
        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";


            try {

                String virtual_url = Config.SER_URL + "pal/respond";
                Log.e("tag", "" + virtual_url);

                //JSONObject jsonobject = HttpUtils.makeRequest34(virtual_url,str_palkey, str_sessionid);


                JSONObject jsonobject = new JSONObject();
                jsonobject.accumulate("oonbux_id", str_oonbux_id);
                jsonobject.accumulate("Action", str_sts);

                // 4. convert JSONObject to JSON to String
                Log.e("tag", "" + jsonobject.toString());
                json = jsonobject.toString();

                return jsonStr = HttpUtils.makeRequest34(virtual_url, json, str_sessionid);
            } catch (Exception e) {
                Log.e("InputStream", "" + e.getLocalizedMessage());
                jsonStr = "";
            }
            return jsonStr;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.e("tag", "<-----rerseres---->" + jsonStr);
            super.onPostExecute(jsonStr);
            sweetDialog.dismiss();


            try {

                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");


                if (status.equals("success")) {

                    Log.e("tag", "<-----result----->" + "" + status + msg);
                    Intent goDash = new Intent(getApplicationContext(), DashBoardActivity.class);
                    startActivity(goDash);


                } else {

                    new SweetAlertDialog(PalAccept.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops!")
                            .setContentText("Try Check your Network")
                            .setConfirmText("OK")
                            .show();

                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }


}
