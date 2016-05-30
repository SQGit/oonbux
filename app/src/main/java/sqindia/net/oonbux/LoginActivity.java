package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class LoginActivity extends Activity {
    Button btn_login;
    TextView tv_donthav, tv_register;
    LinearLayout ll_register;
    TelephonyManager tmanager;
    String str_email, str_pass, str_deviceid, get_profile_sts;
    SweetAlertDialog sweetAlertDialog;
    MaterialEditText et_email, et_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

/*
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        get_profile_sts = sharedPreferences.getString("login", "");*/

        btn_login = (Button) findViewById(R.id.button_login);
        ll_register = (LinearLayout) findViewById(R.id.linear_login_text);
        tv_donthav = (TextView) findViewById(R.id.text_dont);
        tv_register = (TextView) findViewById(R.id.text_login);
        et_email = (MaterialEditText) findViewById(R.id.edittext_email);
        et_pass = (MaterialEditText) findViewById(R.id.edittext_pass);


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");

        btn_login.setTypeface(tf);
        tv_donthav.setTypeface(tf);
        tv_register.setTypeface(tf);
        et_email.setTypeface(tf);
        et_pass.setTypeface(tf);

        tmanager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        str_deviceid = "EMU035Id45";//tmanager.getDeviceId();


       /* et_pass.setOnEditorActionListener(new MaterialEditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(android.widget.TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    validate_datas();
                    return true;
                }
                return false;
            }
        });*/


        ll_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register_intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(register_intent);
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {


                                             validate_datas();


                                         }
                                     }

        );
    }


    public void validate_datas() {


        str_email = et_email.getText().toString();
        str_pass = et_pass.getText().toString();

        if (!(str_email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(str_email).matches())) {
            if (!(str_pass.isEmpty() || str_pass.length() < 4 || str_pass.length() > 10)) {


                if (!Config.isNetworkAvailable(LoginActivity.this)) {

                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops!")
                            .setContentText("No network Available!")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.setCancelable(false);

                                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                                }
                            })

                            .show();


                } else {

                    new LoginTask().execute();


                   /* SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("login", "success");
                    editor.commit();


                    Intent login_intent = new Intent(getApplicationContext(), ProfileInfo.class);
                    startActivity(login_intent);
                    finish();*/


                }
            } else {
                et_pass.setError("between 4 and 10 alphanumeric characters");
                et_pass.requestFocus();
            }
        } else {
            et_email.setError("Enter a valid email address!");
            et_email.requestFocus();

        }


    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Do you want to exit the Application?")
                .setConfirmText("Yes!")
                .setCancelText("No")

                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        Intent i1 = new Intent(Intent.ACTION_MAIN);
                        i1.setAction(Intent.ACTION_MAIN);
                        i1.addCategory(Intent.CATEGORY_HOME);
                        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i1);
                        sDialog.dismiss();
                        finish();


                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                        sweetAlertDialog.cancel();

                    }
                })
                .show();


    }

    class LoginTask extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            sweetAlertDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            sweetAlertDialog.setTitleText("Loading");
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";

            try {

                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("email", str_email);
                jsonObject.accumulate("password", str_pass);
                jsonObject.accumulate("device_gcm_id", str_deviceid);
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

            sweetAlertDialog.dismiss();

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





                    /*Intent login_intent = new Intent(getApplicationContext(), DashBoardActivity.class);
                    startActivity(login_intent);
                    finish();*/


                    Log.d("tag", "" + address);
                    if (!(address.equals("null"))) {

                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("profile", "success");
                        editor.commit();
                        Log.d("tag", "1");

                    } else {
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("profile", "");
                        editor.commit();
                        Log.d("tag", "1");
                    }






                  /*  new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Message Alert")
                            .setContentText("Login Successful")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.cancel();

                                    if ((get_profile_sts.equals(""))) {
                                        Intent login_intent = new Intent(getApplicationContext(), ProfileInfo.class);
                                        startActivity(login_intent);
                                        finish();
                                    } else {

                                        Intent login_intent = new Intent(getApplicationContext(), DashBoardActivity.class);
                                        startActivity(login_intent);
                                        finish();

                                    }

                                }
                            })
                            .show();*/


                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
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
                    editor.commit();



                   /* Dialog_new cdd = new Dialog_new(LoginActivity.this, msg,1);
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




                } else if (status.equals("fail")) {
                    Log.d("tag", "<-----msg----->" + msg);


                    if (msg.equals("Invalid login Credentials provided")) {


                        Dialog_Msg cdd = new Dialog_Msg(LoginActivity.this, msg);
                        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        cdd.show();

                      /*  new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops!")
                                .setContentText("Invalid login Credentials provided")
                                .setConfirmText("OK")
                                .show();*/

                        et_pass.requestFocus();
                        int pos = et_pass.getText().length();
                        et_pass.setSelection(pos);
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        et_pass.setError("");
                    } else if (msg.equals("SERVER ERROR: MESSAGE: Data is Null. This method or property cannot be called on Null values.")) {

                        Dialog_Msg cdd = new Dialog_Msg(LoginActivity.this, "Activation Email is Sent to mail ,Check email for further details");
                        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        cdd.show();
                   /*     new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops!")
                                .setContentText("Account Not Activated! Please check your mail")
                                .setConfirmText("OK")
                                .show();*/
                    } else {
                        Dialog_Msg cdd = new Dialog_Msg(LoginActivity.this, msg);
                        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        cdd.show();
                    }


                } else {
                    Dialog_Msg cdd = new Dialog_Msg(LoginActivity.this, "Try Again Later");
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }


}
