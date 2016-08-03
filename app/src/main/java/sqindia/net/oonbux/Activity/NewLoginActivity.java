package sqindia.net.oonbux.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.Button;
import com.rey.material.widget.EditText;
import com.rey.material.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import sqindia.net.oonbux.Profile.ProfileInfo;
import sqindia.net.oonbux.config.Config;
import sqindia.net.oonbux.Dialog.Dialog_Msg;
import sqindia.net.oonbux.Dialog.Dialog_new;
import sqindia.net.oonbux.config.HttpUtils;
import sqindia.net.oonbux.R;

public class NewLoginActivity extends AppCompatActivity {

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    TextView tv_header, tv_sub_header, tv_sub_header1, tv_footer_txt1, tv_footer_txt2, tv_sub_footer;
    LinearLayout ll_register;
    Button btn_login;
    MaterialEditText et_email, et_pass;
    Typeface tf, tf1;
    Context applicationContext;
    GoogleCloudMessaging gcmObj;
    String get_gcmId, str_email, str_pass;
    Dialog loading_dialog;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginnew);

        tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");
        tf1 = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");

        tv_header = (TextView) findViewById(R.id.textview_header_login);
        tv_sub_header = (TextView) findViewById(R.id.textview_header);
        tv_sub_header1 = (TextView) findViewById(R.id.textview_sub_header);
        tv_footer_txt1 = (TextView) findViewById(R.id.textview_footer_txt1);
        tv_footer_txt2 = (TextView) findViewById(R.id.textview_footer_txt2);
        tv_sub_footer = (TextView) findViewById(R.id.textview_sub_footer);

        ll_register = (LinearLayout) findViewById(R.id.linear_register_text);

        btn_login = (Button) findViewById(R.id.button_login);

        et_email = (MaterialEditText) findViewById(R.id.edittext_email);
        et_pass = (MaterialEditText) findViewById(R.id.edittext_pass);





        btn_login.setTypeface(tf);
        tv_header.setTypeface(tf1);
        tv_sub_header.setTypeface(tf);
        tv_sub_header1.setTypeface(tf);
        tv_footer_txt1.setTypeface(tf);
        tv_footer_txt2.setTypeface(tf);
        tv_sub_footer.setTypeface(tf);
        et_email.setTypeface(tf);
        et_pass.setTypeface(tf);

        applicationContext = getApplicationContext();












        et_pass.setOnEditorActionListener(new android.widget.TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(android.widget.TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    if (!(et_email.getText().toString().isEmpty())) {
                        if (android.util.Patterns.EMAIL_ADDRESS.matcher(et_email.getText().toString()).matches()) {
                            if (!(et_pass.getText().toString().isEmpty())) {

                                str_email = et_email.getText().toString();
                                str_pass = et_pass.getText().toString();

                            } else {
                                et_pass.setError("Password Should Not be Empty");
                                et_pass.requestFocus();
                                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                            }
                        } else {
                            et_email.setError("Enter Valid Email");
                            et_email.requestFocus();
                        }
                    } else {
                        et_email.setError("Email Should Not be Empty");
                        et_email.requestFocus();
                    }
                }
                return false;
            }
        });


        ll_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_intent = new Intent(getApplicationContext(), RegisterClassNew.class);
                login_intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(login_intent);
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                finish();

            }
        });


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!(et_email.getText().toString().isEmpty())) {
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(et_email.getText().toString()).matches()) {
                        if (!(et_pass.getText().toString().isEmpty())) {

                            str_email = et_email.getText().toString();
                            str_pass = et_pass.getText().toString();


                            if (!Config.isNetworkAvailable(NewLoginActivity.this)) {

                                Dialog_new dialog_wifi_settings = new Dialog_new(NewLoginActivity.this, "No Network Avaliable!\nGo to Settings.", 5);
                                dialog_wifi_settings.setCancelable(false);
                                dialog_wifi_settings.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                                dialog_wifi_settings.show();

                            } else {
                                get_Gcm_Id();
                            }
                        } else {
                            et_pass.setError("Password Should Not be Empty");
                            et_pass.requestFocus();
                            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        }
                    } else {
                        et_email.setError("Enter Valid Email");
                        et_email.requestFocus();
                    }
                } else {
                    et_email.setError("Email Should Not be Empty");
                    et_email.requestFocus();
                }


            }
        });


        setupUI(findViewById(R.id.top));

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        btn_login.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //loading_dialog.dismiss();
    }

    public void setupUI(View view) {

        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(NewLoginActivity.this);
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    private void get_Gcm_Id() {

        loading_dialog = new Dialog(NewLoginActivity.this);
        loading_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        loading_dialog.setContentView(R.layout.dialog_loading);
        loading_dialog.setCancelable(false);
        loading_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        loading_dialog.show();


        if (checkPlayServices()) {
            registerInBackground();
        }
    }


    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        // When Play services not found in device
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                // Show Error dialog to install Play services
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(), "This device doesn't support Play services, App will not work normally", Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        } else {
            // Toast.makeText(getApplicationContext(),"This device supports Play services, App will work normally", Toast.LENGTH_LONG).show();
        }
        return true;
    }


    private void registerInBackground() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcmObj == null) {
                        gcmObj = GoogleCloudMessaging.getInstance(applicationContext);
                    }
                    get_gcmId = gcmObj.register(Config.GCM_PROJ_ID);
                    msg = "Registration ID :" + get_gcmId;
                    Log.e("tags", msg + get_gcmId);

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                    Log.e("tage", ex.getMessage());
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {


                Log.d("tag", "" + msg);

                if (!TextUtils.isEmpty(get_gcmId)) {


                    Log.d("tag1", get_gcmId);

                    new LoginTask().execute();


                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Reg ID Creation Failed.\n\nEither you haven't enabled Internet or GCM server is busy right now. Make sure you enabled Internet and try registering again after some time."
                                    + msg, Toast.LENGTH_LONG).show();

                    Log.d("tag", "" + msg);
                }
            }
        }.execute();
    }


    class LoginTask extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";

            try {

                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("email", str_email);
                jsonObject.accumulate("password", str_pass);
                jsonObject.accumulate("device_gcm_id", get_gcmId);
                // 4. convert JSONObject to JSON to String
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest(Config.SER_URL + "login", json);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("tag", "<-----rerseres---->" + s);
            super.onPostExecute(s);

            loading_dialog.dismiss();

            try {
                JSONObject jo = new JSONObject(s);

                String status = jo.getString("status");

                String msg = jo.getString("message");
                Log.d("tag", "<-----Status----->" + status);


                if (status.equals("success")) {
                    Log.d("tag", "<-----msg----->" + msg);


                    String gsessionid = jo.getString("session_id");
                    String goonbuxid = jo.getString("oonbux_id");
                    String gfname = jo.getString("firstname");
                    String glname = jo.getString("lastname");
                    String gmail = jo.getString("email");
                    String gphone = jo.getString("loc_phone");
                    String gstate = jo.getString("loc_addr_state");
                    String gcontry = jo.getString("loc_addr_country");
                    String gzip = jo.getString("loc_addr_zip");


                    String address = jo.getString("loc_addr_line1");


                    Log.d("tag", "" + address);
                    if (!(address.equals("null"))) {

                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(NewLoginActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("profile", "success");
                        editor.commit();
                        Log.d("tag", "1");

                    } else {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(NewLoginActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("profile", "");
                        editor.commit();
                        Log.d("tag", "1");
                    }


                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(NewLoginActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("login", "success");

                    editor.putString("register", "success");

                    editor.putString("sessionid", gsessionid);
                    editor.putString("oonbuxid", goonbuxid);
                    editor.putString("gcmid", jo.getString("device_gcm_id"));
                    editor.putString("fname", gfname);
                    editor.putString("lname", glname);
                    editor.putString("mail", gmail);
                    editor.putString("phone", gphone);
                    editor.putString("state", gstate);
                    editor.putString("zip", gzip);
                    editor.putString("country", gcontry);

                    editor.putString("default_adr", jo.getString("default_loc"));

                    editor.putString("loc_addr1", jo.getString("loc_addr_line1"));
                    editor.putString("loc_addr2", jo.getString("loc_addr_line2"));
                    editor.putString("loc_city", jo.getString("loc_addr_city"));
                    editor.putString("loc_state", jo.getString("loc_addr_state"));
                    editor.putString("loc_zip", jo.getString("loc_addr_zip"));
                    editor.putString("loc_phone", jo.getString("loc_phone"));
                    editor.putString("loc_note", jo.getString("loc_delivery_note"));

                    editor.putString("int_addr1", jo.getString("int_addr_line1"));
                    editor.putString("int_addr2", jo.getString("int_addr_line2"));
                    editor.putString("int_city", jo.getString("int_addr_city"));
                    editor.putString("int_state", jo.getString("int_addr_state"));
                    editor.putString("int_zip", jo.getString("int_addr_zip"));
                    editor.putString("int_phone", jo.getString("int_phone"));
                    editor.putString("int_note", jo.getString("int_delivery_note"));
                    Log.d("tag", "" + jo.getString("photourl"));
                    editor.putString("web_photo_url", jo.getString("photourl"));

                    editor.commit();



                   /* Dialog_new cdd = new Dialog_new(NewLoginActivity.this, msg,1);
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();*/


                    String sts = sharedPreferences.getString("profile", "");

                    Log.d("tag", "" + sts);
                    if (sts.equals("")) {
                        Intent goD = new Intent(getApplicationContext(), ProfileInfo.class);
                        startActivity(goD);
                        Log.d("tag", "2");
                        finish();

                    } else {
                        Intent goD = new Intent(getApplicationContext(), DashBoardActivity.class);
                        startActivity(goD);
                        Log.d("tag", "2");
                        finish();
                    }


                }
                else if (status.equals("fail")) {
                    Log.d("tag", "<-----msg----->" + msg);


                    if (msg.equals("Invalid login Credentials provided")) {


                        Dialog_Msg dialog_fail = new Dialog_Msg(NewLoginActivity.this, msg);
                        dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        dialog_fail.show();



                        et_pass.requestFocus();
                        int pos = et_pass.getText().length();
                        et_pass.setSelection(pos);
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    } else if (msg.equals("SERVER ERROR: MESSAGE: Data is Null. This method or property cannot be called on Null values.")) {

                        Dialog_Msg dialog_fail = new Dialog_Msg(NewLoginActivity.this, "Activation Email is Sent to mail ,Check email for further details");
                        dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        dialog_fail.show();

                    } else {
                        Dialog_Msg dialog_fail = new Dialog_Msg(NewLoginActivity.this, msg);
                        dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        dialog_fail.show();
                    }


                }
                else {
                    Dialog_Msg dialog_fail = new Dialog_Msg(NewLoginActivity.this, "Try Again Later");
                    dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog_fail.show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }


}
