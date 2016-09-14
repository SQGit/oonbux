package sqindia.net.oonbux.Profile;

import android.app.Activity;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.rey.material.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import sqindia.net.oonbux.Notification.GcmBroadcastReceiver;
import sqindia.net.oonbux.R;
import sqindia.net.oonbux.config.Config;
import sqindia.net.oonbux.config.HttpUtils;

/**
 * Created by Salman on 7/26/2016.
 */
public class Personal_Preferences extends Activity {

    ImageView iv_back;
    Switch sw_public, sw_private, sw_gcm;
    TextView tv_header, tv_prof_txt;
    Typeface tf, tf1;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String str_session_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_preferences);

        tf1 = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");
        tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Personal_Preferences.this);
        editor = sharedPreferences.edit();

        str_session_id = sharedPreferences.getString("sessionid", "");

        iv_back = (ImageView) findViewById(R.id.layout_back);

        sw_public = (Switch) findViewById(R.id.sw_prof_public);
        sw_private = (Switch) findViewById(R.id.sw_prof_private);
        sw_gcm = (Switch) findViewById(R.id.sw_gcm);

        tv_header = (TextView) findViewById(R.id.tv_hd_txt);
        tv_prof_txt = (TextView) findViewById(R.id.tv_prof_visi_txt);

        tv_header.setTypeface(tf1);
        tv_prof_txt.setTypeface(tf);
        sw_public.setTypeface(tf);
        sw_private.setTypeface(tf);
        sw_gcm.setTypeface(tf);


        if (sharedPreferences.getString("visible", "") == "PUBLIC") {
            sw_public.setChecked(true);
            sw_private.setChecked(false);
        } else {
            sw_private.setChecked(true);
            sw_public.setChecked(false);
        }


        if (sharedPreferences.getString("gcm", "") != "") {
            sw_gcm.setChecked(true);
        } else {
            sw_gcm.setChecked(false);
        }


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sw_public.isChecked()) {
                    editor.putString("visible", "PUBLIC");
                    editor.commit();
                } else {
                    editor.putString("visible", "PRIVATE");
                    editor.commit();
                }

                if (sw_gcm.isChecked()) {
                    editor.putString("gcm", "YES");
                    editor.commit();
                } else {
                    editor.putString("gcm", "");
                    editor.commit();
                }


                onBackPressed();
                finish();
            }
        });


        sw_public.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sw_public.isChecked()) {
                    sw_private.setChecked(false);
                } else {
                    sw_private.setChecked(true);

                }
            }
        });


        sw_private.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sw_private.isChecked()) {
                    sw_public.setChecked(false);

                } else {
                    sw_public.setChecked(true);

                }
            }
        });


        sw_public.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    sw_private.setChecked(false);


                } else {
                    sw_private.setChecked(true);

                }
            }
        });

        sw_private.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sw_public.setChecked(false);

                } else {
                    sw_public.setChecked(true);

                }
            }
        });


        sw_gcm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    enableBroadcastReceiver();
                } else {
                    disableBroadcastReceiver();
                }
            }
        });


        sw_gcm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sw_private.isChecked()) {
                    enableBroadcastReceiver();
                } else {
                    disableBroadcastReceiver();
                }
            }
        });





    }


    public void disableBroadcastReceiver() {
        ComponentName receiver = new ComponentName(this, GcmBroadcastReceiver.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
        Toast.makeText(this, "Notification Disabled", Toast.LENGTH_SHORT).show();
    }


    public void enableBroadcastReceiver() {
        ComponentName receiver = new ComponentName(this, GcmBroadcastReceiver.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
        Toast.makeText(this, "Notification Enabled", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {


        if (Config.isNetworkAvailable(Personal_Preferences.this)) {
            new Profile_Update_Task().execute();
        }

        if (sw_public.isChecked()) {
            editor.putString("visible", "PUBLIC");
            editor.commit();
        } else {
            editor.putString("visible", "PRIVATE");
            editor.commit();
        }

        if (sw_gcm.isChecked()) {
            editor.putString("gcm", "YES");
            editor.commit();
        } else {
            editor.putString("gcm", "");
            editor.commit();
        }

        super.onBackPressed();

    }


    class Profile_Update_Task extends AsyncTask<String, Void, String> {


        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";

            try {

                JSONObject jsonObject = new JSONObject();


                jsonObject.accumulate("user_privacy", sharedPreferences.getString("visible", ""));

                // 4. convert JSONObject to JSON to String
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest2(Config.SER_URL + "profileupdate", json, str_session_id);
            } catch (Exception e) {
                Log.e("InputStream", e.getLocalizedMessage());
            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.e("tag", "<-----rerseres---->" + s);
            super.onPostExecute(s);

            if (s != null) {

                try {
                    JSONObject jo = new JSONObject(s);

                    String status = jo.getString("status");

                    String msg = jo.getString("message");
                    Log.e("tag", "<-----Status----->" + status);


                    if (status.equals("success")) {


                    } else {


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

            }
        }
    }






}
