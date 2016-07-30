package sqindia.net.oonbux;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.rey.material.widget.Button;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;


//asd

public class SplashActivity extends AppCompatActivity {
    Button btn_submit;
    String get_login_sts, get_register_sts, get_profile_sts;
   // private ProgressBar progress;
    DbC dbclass;
    Context context = this;
    public int version=0;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    AVLoadingIndicatorView loader;

    public int a,s,d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

       // progress = (ProgressBar) findViewById(R.id.progressbar);
        btn_submit = (Button) findViewById(R.id.button_submit);
        loader = (AVLoadingIndicatorView) findViewById(R.id.avloadingIndicatorView);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");
        btn_submit.setTypeface(tf);


        btn_submit.setVisibility(View.GONE);

        dbclass = new DbC(context);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
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
        editor.commit();


        if (!Config.isNetworkAvailable(SplashActivity.this)) {
            new SweetAlertDialog(SplashActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Oops!")
                    .setContentText("No network Available!")
                    .setConfirmText("OK")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                            finish();
                            sweetAlertDialog.dismiss();
                        }
                    })
                    .show();
        } else {


            if(!(sharedPreferences.getString("profile","").equals(""))){

                if(!(sharedPreferences.getString("login","").equals(""))) {
                    Intent profile_intent = new Intent(getApplicationContext(), DashBoardActivity.class);
                    startActivity(profile_intent);
                    finish();
                }
            }
            else {

                //progress.setVisibility(View.VISIBLE);
                loader.setVisibility(View.VISIBLE);
                btn_submit.setVisibility(View.GONE);

                new GetVersion().execute();
            }
            //get_RegionDatas();
        }









        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(sharedPreferences.getString("register","").equals("")){

                    Intent login_intent = new Intent(getApplicationContext(), RegisterClassNew.class);
                    startActivity(login_intent);
                    finish();

                }
                else
                {
                    if(sharedPreferences.getString("login","").equals("")){

                        Intent login_intent = new Intent(getApplicationContext(), NewLoginActivity.class);
                        startActivity(login_intent);
                        finish();

                    }
                    else{

                        if(sharedPreferences.getString("profile","").equals("")){
                            Intent profile_intent = new Intent(getApplicationContext(), ProfileInfo.class);
                            startActivity(profile_intent);
                            finish();
                        }
                        else {
                            Intent dash_intent = new Intent(getApplicationContext(), DashBoardActivity.class);
                            startActivity(dash_intent);
                            finish();
                        }
                    }

                }



           /*     if ((get_register_sts.equals(""))) {
                    Intent login_intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(login_intent);
                    finish();
                } else {
                    if ((get_login_sts.equals(""))) {
                        Intent login_intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(login_intent);
                        finish();
                    } else {

                        if (get_profile_sts.equals("")) {
                            Intent login_intent = new Intent(getApplicationContext(), ProfileInfo.class);
                            startActivity(login_intent);
                            finish();
                        } else {

                            Intent login_intent = new Intent(getApplicationContext(), DashBoardActivity.class);
                            startActivity(login_intent);
                            finish();
                        }
                    }
                }*/
            }
        });
    }

    private void get_RegionDatas() {
        Log.e("tag", "inside regions" );

        if(sharedPreferences.getString("my_version","").equals(""))
        {
            version = 0;

            check_datas();

        }
        else{
            version = Integer.valueOf(sharedPreferences.getString("my_version",""));
            check_datas();
        }


    }




    public void check_datas(){

        Log.e("tag", "inside regions@@" +version);
        if(Integer.valueOf(sharedPreferences.getString("db_version","")) == version)
        {
            //progress.setVisibility(View.GONE);
            loader.setVisibility(View.GONE);
            btn_submit.setVisibility(View.VISIBLE);

        }
        else{
           // progress.setVisibility(View.VISIBLE);
            loader.setVisibility(View.VISIBLE);
            btn_submit.setVisibility(View.GONE);

            new GetAllRegions().execute();
        }



    }







    class GetAllRegions extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {
                String country_url = Config.SER_URL + "region/all";
                JSONObject jsonobject = HttpUtils.getData(country_url);
                Log.e("tag", "" + jsonobject.toString());
                if (jsonobject.toString() == "sam") {
                    new SweetAlertDialog(SplashActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops!")
                            .setContentText("Try Check your Network")
                            .setConfirmText("OK")
                            .show();
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
            super.onPostExecute(jsonStr);


            try {

                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");
                String version = jo.getString("version");



                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("my_version", version);
                editor.commit();

                JSONArray json_arr_regions = jo.getJSONArray("region");

                //Log.e("tag", "<-----aasd----->" + status + msg + version + json_arr_regions);

                for (int i = 0; i < json_arr_regions.length(); i++) {
                    JSONObject json_cont = new JSONObject(new String(json_arr_regions.getString(i)));
                    String country = json_cont.getString("country");
                    JSONArray json_arr_state = json_cont.getJSONArray("state");
                   // Log.e("tag", "<-----country----->" + country + "\t" + json_arr_state);

                    Log.e("tag", "<-----0-----> " + json_arr_regions.length() );

                    for (int j = 0; j < json_arr_state.length(); j++) {
                        JSONObject json_stat = new JSONObject(new String(json_arr_state.getString(j)));
                        String state = json_stat.getString("statename");
                        JSONArray json_arr_zip = json_stat.getJSONArray("zip");
                     //   Log.e("tag", "<-----country----->" + state + "\t" + json_arr_zip);

                        Log.e("tag", state + j + " <-----1.1_s-----> " + s );

                        Log.e("tag", "<-----1.2-L---> " + json_arr_zip.length() );
                        s = s + json_arr_zip.length();
                        Log.e("tag", "<-----1.3-f---> " + s );

                       // dbclass.region_insert(country,state,"0000");



                            for (int k = 0; k < json_arr_zip.length(); k++) {
                                String zip = json_arr_zip.getString(k);
                                //  Log.e("tag", "<-----country----->" + zip);

                                //   Log.e("tag","regions:  "+country+state+zip);
                                 dbclass.region_insert(country,state,zip);
                                    //dbclass.region_insert1(country,state,zip);
                        }

                    }
                }

               // progress.setVisibility(View.GONE);
                loader.setVisibility(View.GONE);
                btn_submit.setVisibility(View.VISIBLE);




            } catch (JSONException e) {
                e.printStackTrace();
                Dialog_Msg dialog_fail = new Dialog_Msg(SplashActivity.this, "Network Error!,\nPlease Try Again Later.");
                dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog_fail.show();
            }
        }
    }





    class GetVersion extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {
                String country_url = Config.SER_URL + "region/version";
                JSONObject jsonobject = HttpUtils.getData(country_url);
                Log.e("tag", "" + jsonobject.toString());
                if (jsonobject.toString() == "sam") {
                    new SweetAlertDialog(SplashActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops!")
                            .setContentText("Try Check your Network")
                            .setConfirmText("OK")
                            .show();
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
                if(status.equals("success")){


                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("db_version", version);
                editor.commit();

                    get_RegionDatas();

                }
            } catch (JSONException e) {
                e.printStackTrace();
                new SweetAlertDialog(SplashActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Oops!")
                        .setContentText("Network Error,Try Again Later.")
                        .setConfirmText("OK")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                finish();
                                sweetAlertDialog.dismiss();
                            }
                        })
                        .show();
            }
        }
    }



}
