package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class RegisterActivity extends Activity {
    Button btn_submit;
    TextView tv_donthav, tv_register, tv_footer;
    LinearLayout ll_register;
    MaterialEditText et_email, et_phone, et_pass, et_repass, et_zip, et_state;
    MaterialAutoCompleteTextView aet_cont;
    String str_country, str_email, str_phone, str_pass, str_repass, str_state, str_zip;
    SweetAlertDialog sweetAlertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_submit = (Button) findViewById(R.id.button_submit);
        ll_register = (LinearLayout) findViewById(R.id.linear_login_text);
        tv_donthav = (TextView) findViewById(R.id.text_dont);
        tv_register = (TextView) findViewById(R.id.text_login);
        tv_footer = (TextView) findViewById(R.id.terms);
        aet_cont = (MaterialAutoCompleteTextView) findViewById(R.id.edittext_country);
        et_email = (MaterialEditText) findViewById(R.id.edittext_email);
        et_pass = (MaterialEditText) findViewById(R.id.edittext_pass);
        et_repass = (MaterialEditText) findViewById(R.id.edittext_repass);
        et_phone = (MaterialEditText) findViewById(R.id.edittext_phone);
        et_zip = (MaterialEditText) findViewById(R.id.edittext_zipcode);
        et_state = (MaterialEditText) findViewById(R.id.edittext_state);


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");

        btn_submit.setTypeface(tf);
        tv_donthav.setTypeface(tf);
        tv_register.setTypeface(tf);
        tv_footer.setTypeface(tf);
        aet_cont.setTypeface(tf);
        et_email.setTypeface(tf);
        et_pass.setTypeface(tf);
        et_repass.setTypeface(tf);
        et_phone.setTypeface(tf);
        et_zip.setTypeface(tf);
        et_state.setTypeface(tf);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_lists, R.id.text_spin, Config.state_lists);
        aet_cont.setAdapter(adapter1);


        ll_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(login_intent);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_country = aet_cont.getText().toString();
                str_email = et_email.getText().toString();
                str_pass = et_pass.getText().toString();
                str_repass = et_repass.getText().toString();
                str_phone = et_phone.getText().toString();
                str_zip = et_zip.getText().toString();
                str_state = et_state.getText().toString();

                if (!str_country.isEmpty()) {
                    if (!(str_email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(str_email).matches())) {
                        if (!(str_pass.isEmpty() || str_pass.length() < 4 || str_pass.length() > 10)) {
                            if (str_repass.matches(str_pass)) {
                                if (!(str_phone.isEmpty() || str_phone.length() < 10)) {
                                    if (!(str_zip.isEmpty() || str_zip.length() < 3)) {
                                        if (!str_state.isEmpty()) {
                                            new RegisterTask().execute();
                                        } else {
                                            et_state.setError("Enter State");
                                            et_state.requestFocus();
                                        }
                                    } else {
                                        et_zip.setError("Enter zipcode");
                                        et_zip.requestFocus();
                                    }
                                } else {
                                    et_phone.setError("Enter valid phone number");
                                    et_phone.requestFocus();

                                }
                            } else {
                                et_repass.setError("Password not matches!");
                                et_repass.requestFocus();
                            }
                        } else {
                            et_pass.setError("between 4 and 10 alphanumeric characters");
                            et_pass.requestFocus();

                        }
                    } else {
                        et_email.setError("Enter a valid email address!");
                        et_email.requestFocus();
                    }
                } else {
                    aet_cont.setError("Enter a valid Country!");
                    aet_cont.requestFocus();
                }
            }
        });
    }


    class RegisterTask extends AsyncTask<String, Void, String> {

        protected void onPreExecute() {
            super.onPreExecute();

            sweetAlertDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.PROGRESS_TYPE);
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
                jsonObject.accumulate("name", "salman");
                jsonObject.accumulate("email", str_email);
                jsonObject.accumulate("phone", str_phone);
                jsonObject.accumulate("password", str_pass);
                jsonObject.accumulate("state", str_state);
                jsonObject.accumulate("country", str_country);
                jsonObject.accumulate("zip", str_zip);
                // 4. convert JSONObject to JSON to String
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest(Config.REG_URL + "registration", json);
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

                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Message Alert")
                            .setContentText(msg)
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.cancel();
                                    Intent goD = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(goD);
                                    finish();


                                }
                            })
                            .show();


                   /* Intent login_intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(login_intent);
                    finish();*/


                    // Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                } else if (status.equals(null)) {
                    //  Toast.makeText(getApplicationContext(), "network available", Toast.LENGTH_LONG).show();
                } else if (status.equals("fail")) {


                    if (msg.equals("Email ID is already registered")) {
                        new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops!")
                                .setContentText("Email Already Registered")
                                .setConfirmText("OK")
                                .show();
                        et_email.setText("");
                        et_email.requestFocus();
                        et_email.setError("");
                    }

                    // Toast.makeText(getApplicationContext(), "Check your internet or try again", Toast.LENGTH_LONG).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }


}
