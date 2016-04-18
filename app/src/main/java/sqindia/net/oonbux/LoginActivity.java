package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
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
    String str_email, str_phone, str_pass, str_deviceid;
    SweetAlertDialog sweetAlertDialog;
    MaterialEditText et_email, et_phone, et_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = (Button) findViewById(R.id.button_login);
        ll_register = (LinearLayout) findViewById(R.id.linear_login_text);
        tv_donthav = (TextView) findViewById(R.id.text_dont);
        tv_register = (TextView) findViewById(R.id.text_login);
        et_email = (MaterialEditText) findViewById(R.id.edittext_email);
        et_phone = (MaterialEditText) findViewById(R.id.edittext_phone);
        et_pass = (MaterialEditText) findViewById(R.id.edittext_pass);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");

        btn_login.setTypeface(tf);
        tv_donthav.setTypeface(tf);
        tv_register.setTypeface(tf);
        et_email.setTypeface(tf);
        et_phone.setTypeface(tf);
        et_pass.setTypeface(tf);

        tmanager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        str_deviceid = "EMU035Id45";//tmanager.getDeviceId();


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

                                             str_email = et_email.getText().toString();
                                             str_phone = et_phone.getText().toString();
                                             str_pass = et_pass.getText().toString();

                                             if (!(str_email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(str_email).matches())) {
                                                 if (!(str_phone.isEmpty() || str_phone.length() < 10)) {
                                                     if (!(str_pass.isEmpty() || str_pass.length() < 4 || str_pass.length() > 10)) {
                                                         //new LoginTask().execute();

                                                         Intent login_intent = new Intent(getApplicationContext(), DashBoardActivity.class);
                                                         startActivity(login_intent);

                                                     } else {
                                                         et_pass.setError("between 4 and 10 alphanumeric characters");
                                                         et_pass.requestFocus();
                                                     }
                                                 } else {
                                                     et_phone.setError("Enter valid phone number");
                                                     et_phone.requestFocus();
                                                 }
                                             } else {
                                                 et_email.setError("Enter a valid email address!");
                                                 et_email.requestFocus();

                                             }
                                         }
                                     }

        );
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
                jsonObject.accumulate("phone", str_phone);
                jsonObject.accumulate("password", str_pass);
                jsonObject.accumulate("device_id", str_deviceid);
                // 4. convert JSONObject to JSON to String
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest(Config.REG_URL + "login", json);
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
                    String gname = jo.getString("name");
                    String gmail = jo.getString("email");
                    String gphone = jo.getString("phone");
                    String gstate = jo.getString("state");
                    String gcontry = jo.getString("country");
                    String gzip = jo.getString("zip");


                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("login", "12345");
                    editor.putString("sessionid", gsessionid);
                    editor.putString("oonbuxid", goonbuxid);
                    editor.putString("name", gname);
                    editor.putString("mail", gmail);
                    editor.putString("phone", gphone);
                    editor.putString("state", gstate);
                    editor.putString("zip", gzip);
                    editor.putString("country", gcontry);
                    editor.commit();


                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Message Alert")
                            .setContentText("Login Successful")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.cancel();
                                    Intent login_intent = new Intent(getApplicationContext(), DashBoardActivity.class);
                                    startActivity(login_intent);
                                    finish();
                                }
                            })
                            .show();

                } else if (status.equals("fail")) {
                    Log.d("tag", "<-----msg----->" + msg);
                    if (msg.equals("Invalid login Credentials provided")) {
                        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops!")
                                .setContentText("Invalid login Credentials provided")
                                .setConfirmText("OK")
                                .show();
                        et_pass.setText("");
                        et_pass.requestFocus();
                        et_pass.setError("");
                    } else if (msg.equals("Account not activated, Activation Email sent to you.")) {
                        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops!")
                                .setContentText("Account Not Activated! Please check your mail")
                                .setConfirmText("OK")
                                .show();
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}
