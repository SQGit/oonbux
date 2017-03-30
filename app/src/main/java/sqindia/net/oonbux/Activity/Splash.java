package sqindia.net.oonbux.Activity;

import android.app.ActivityOptions;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.rey.material.widget.Button;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sqindia.net.oonbux.Dialog.Dialog_Msg;
import sqindia.net.oonbux.Dialog.Dialog_new;
import sqindia.net.oonbux.Profile.ProfileInfo;
import sqindia.net.oonbux.R;
import sqindia.net.oonbux.config.Config;
import sqindia.net.oonbux.config.DbC;
import sqindia.net.oonbux.config.HttpUtils;

public class Splash extends AppCompatActivity {
    public int version = 0;
    public boolean bl_version;
    public int a, s, d;
    Button btn_submit;
    String get_login_sts, get_register_sts, get_profile_sts;
    DbC dbclass;
    Context context = this;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    AVLoadingIndicatorView loader;
    SQLiteDatabase db;
    ContentValues values;

    ArrayList<String> cont = new ArrayList<>();
    ArrayList<String> stat = new ArrayList<>();
    ArrayList<String> zi = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        btn_submit = (Button) findViewById(R.id.button_submit);
        loader = (AVLoadingIndicatorView) findViewById(R.id.avloadingIndicatorView);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");
        btn_submit.setTypeface(tf);
        btn_submit.setVisibility(View.GONE);

        dbclass = new DbC(context);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Splash.this);
        editor = sharedPreferences.edit();

        get_login_sts = sharedPreferences.getString("login", "");
        get_register_sts = sharedPreferences.getString("register", "");
        get_profile_sts = sharedPreferences.getString("profile", "");

        Log.e("tag", "login_status" + get_login_sts);
        Log.e("tag", "register_status" + get_register_sts);
        Log.e("tag", "profile_status" + get_profile_sts);

        editor.putString("reg_country", "");
        editor.putString("reg_state", "");
        editor.putString("reg_zip", "");
        editor.putString("reg_fname", "");
        editor.putString("reg_lname", "");
        editor.putString("reg_email", "");
        editor.putString("reg_phone", "");
        editor.putString("reg_pass", "");
        editor.putString("reg_pass", "");
        editor.commit();

        if (!Config.isNetworkAvailable(Splash.this)) {
            Dialog_new dialog_wifi_settings = new Dialog_new(Splash.this, "No Network Avaliable!\nGo to Settings.", 5);
            dialog_wifi_settings.setCancelable(false);
            dialog_wifi_settings.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog_wifi_settings.show();
        } else {
            new GetVersion().execute();
        }


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sharedPreferences.getString("register", "").equals("")) {

                    Intent reg_intent = new Intent(getApplicationContext(), Register.class);

                    ActivityOptions options = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        options = ActivityOptions.makeScaleUpAnimation(btn_submit, 0,
                                0, btn_submit.getWidth(),
                                btn_submit.getHeight());
                        startActivity(reg_intent, options.toBundle());
                    } else {
                        reg_intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(reg_intent);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    }
                    finish();

                } else {
                    if (sharedPreferences.getString("login", "").equals("")) {

                        Intent login_intent = new Intent(getApplicationContext(), Login.class);

                        ActivityOptions options = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            options = ActivityOptions.makeScaleUpAnimation(btn_submit, 0,
                                    0, btn_submit.getWidth(),
                                    btn_submit.getHeight());
                            startActivity(login_intent, options.toBundle());
                        } else {
                            login_intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(login_intent);
                            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                        }
                        finish();

                    } else {
                        if (sharedPreferences.getString("profile", "").equals("")) {

                            Intent profile_intent = new Intent(getApplicationContext(), ProfileInfo.class);

                            ActivityOptions options = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                options = ActivityOptions.makeScaleUpAnimation(btn_submit, 0,
                                        0, btn_submit.getWidth(),
                                        btn_submit.getHeight());
                                startActivity(profile_intent, options.toBundle());
                            } else {
                                profile_intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(profile_intent);
                                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                            }
                            finish();

                        } else {

                            Intent profile_intent = new Intent(getApplicationContext(), Dashboard.class);

                            ActivityOptions options = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                options = ActivityOptions.makeScaleUpAnimation(btn_submit, 0,
                                        0, btn_submit.getWidth(),
                                        btn_submit.getHeight());
                                startActivity(profile_intent, options.toBundle());
                            } else {
                                profile_intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(profile_intent);
                                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                            }
                            finish();

                        }
                    }
                }
            }
        });
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if (!Config.isNetworkAvailable(Splash.this)) {
            Dialog_new dialog_wifi_settings = new Dialog_new(Splash.this, "No Network Avaliable!\nGo to Settings.", 5);
            dialog_wifi_settings.setCancelable(false);
            dialog_wifi_settings.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog_wifi_settings.show();
        } else {
            new GetVersion().execute();
        }
    }

    public void check_version() {
        if (sharedPreferences.getString("my_version", "").equals("")) {
            loader.setVisibility(View.VISIBLE);
            btn_submit.setVisibility(View.GONE);
            bl_version = false;
            get_all();
        } else {
            if (Integer.valueOf(sharedPreferences.getString("db_version", "")) == Integer.valueOf(sharedPreferences.getString("my_version", ""))) {
                loader.setVisibility(View.GONE);
                btn_submit.setVisibility(View.VISIBLE);
                bl_version = true;
                get_all();
            } else {
                loader.setVisibility(View.VISIBLE);
                btn_submit.setVisibility(View.GONE);
                bl_version = false;
                get_all();
            }
        }
    }

    public void get_all() {
        if (bl_version) {
            if (!(sharedPreferences.getString("profile", "").equals(""))) {
                if (!(sharedPreferences.getString("login", "").equals(""))) {
                    Intent profile_intent = new Intent(getApplicationContext(), Dashboard.class);
                    startActivity(profile_intent);
                    finish();
                }
            }
        } else {
            new GetAllRegions().execute();
        }
    }

    class GetAllRegions extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            loader.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {
                String country_url = Config.SER_URL + "region/all";
                JSONObject jsonobject = HttpUtils.getData(country_url);
                Log.e("tag", "" + jsonobject.toString());
                if (jsonobject.toString() == "sam") {
                    Dialog_Msg dialog_fail = new Dialog_Msg(Splash.this, "Please Try Again Later.");
                    dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog_fail.show();
                }
                json = jsonobject.toString();
                return json;
            } catch (Exception e) {
                Log.e("InputStream", "" + e.getLocalizedMessage());
                jsonStr = "";
            }
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.e("tag", "<-----rerseres---->" + jsonStr);
            loader.setVisibility(View.GONE);
            super.onPostExecute(jsonStr);
            try {
                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");
                String version = jo.getString("version");
                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Splash.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("my_version", version);
                editor.commit();
                JSONArray json_arr_regions = jo.getJSONArray("region");
                for (int i = 0; i < json_arr_regions.length(); i++) {
                    loader.setVisibility(View.VISIBLE);
                    JSONObject json_cont = new JSONObject(new String(json_arr_regions.getString(i)));
                    String country = json_cont.getString("country");
                    JSONArray json_arr_state = json_cont.getJSONArray("state");
                    //  Log.e("tag", "<-----0-----> " + json_arr_regions.length());
                    for (int j = 0; j < json_arr_state.length(); j++) {
                        JSONObject json_stat = new JSONObject(new String(json_arr_state.getString(j)));
                        String state = json_stat.getString("statename");
                        JSONArray json_arr_zip = json_stat.getJSONArray("zip");
                        // Log.e("tag", state + j + " <-----1.1_s-----> " + s);
                        // Log.e("tag", "<-----1.2-L---> " + json_arr_zip.length());
                        s = s + json_arr_zip.length();
                        // Log.e("tag", "<-----1.3-f---> " + s);

                        // values = new ContentValues();
                        //  db = dbclass.getWritableDatabase();

                        for (int k = 0; k < json_arr_zip.length(); k++) {
                            String zip = json_arr_zip.getString(k);
                            // dbclass.region_insert(country, state, zip);


                           /* values.put("country", country);
                            values.put("state", state);
                            values.put("zip", zip);


                            db.insert("region", null, values);*/

                            cont.add(country);
                            stat.add(state);
                            zi.add(zip);


                        }
                        // db.close();
                    }
                }

                insertee();


                check_version();
                loader.setVisibility(View.GONE);
                btn_submit.setVisibility(View.VISIBLE);
            } catch (JSONException e) {
                e.printStackTrace();
                Dialog_Msg dialog_fail = new Dialog_Msg(Splash.this, "Network Error!,\nPlease Try Again Later.");
                dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog_fail.show();
            }
        }


    }

    private void insertee() {


        SQLiteDatabase db = dbclass.getWritableDatabase(); // get a writable database here
        values = new ContentValues();
        db.beginTransaction();
        // Log.e("tag","begin");
        Log.e("tag", "" + zi.size());
        try {
            for (int i = 0; i < zi.size(); i++) {  // loop through your records

                values.put("country", cont.get(i));
                values.put("state", stat.get(i));
                values.put("zip", zi.get(i));

                // Log.e("tag","insert");
                db.insert("region", null, values);
            }


            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            Log.e("tag", "" + cont.size());
        }

        db.close();


    }

    class GetVersion extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            loader.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {
                String country_url = Config.SER_URL + "region/version";
                JSONObject jsonobject = HttpUtils.getData(country_url);
                Log.e("tag", "" + jsonobject.toString());
                if (jsonobject.toString() == "sam") {
                    Dialog_Msg dialog_fail = new Dialog_Msg(Splash.this, "Please Try Again Later.");
                    dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog_fail.show();
                }
                json = jsonobject.toString();
                return json;
            } catch (Exception e) {
                Log.e("InputStream", "" + e.getLocalizedMessage());
                jsonStr = "";
            }
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.e("tag", "<-----rerseres---->" + jsonStr);
            loader.setVisibility(View.GONE);
            super.onPostExecute(jsonStr);
            try {
                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");
                String version = jo.getString("version");
                Log.e("tag", "" + version);
                if (status.equals("success")) {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Splash.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("db_version", version);
                    editor.commit();
                    check_version();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Dialog_Msg dialog_fail = new Dialog_Msg(Splash.this, "Network Error,Please Try Again Later.");
                dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog_fail.show();
            }
        }
    }
}