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
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.Button;
import com.rey.material.widget.Spinner;
import com.rey.material.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class RegisterActivity extends Activity {
    Button btn_submit;
    TextView tv_donthav, tv_register, tv_footer;
    LinearLayout ll_login;
    MaterialEditText et_fname, et_lname, et_email, et_phone, et_pass, et_repass, et_zip, et_state;
    MaterialAutoCompleteTextView aet_cont, aet_state, aet_zip;
    String str_fname, str_lname, str_country, str_email, str_phone, str_pass, str_repass, str_state, str_zip;
    SweetAlertDialog sweetAlertDialog;
    ArrayList<String> country = new ArrayList<>();
    ArrayList<String> states = new ArrayList<>();
    ArrayList<String> zip = new ArrayList<>();

    String[] ar_states;
    Spinner spin;
    String[] countries;
    ArrayAdapter<String> adapter2;


    GlobalDatas gdatas = new GlobalDatas();
    GlobalDatas gs = (GlobalDatas) getApplication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        spin = (com.rey.material.widget.Spinner) findViewById(R.id.spin_country);


        str_country = "us";


        btn_submit = (Button) findViewById(R.id.button_submit);
        ll_login = (LinearLayout) findViewById(R.id.linear_login_text);
        tv_donthav = (TextView) findViewById(R.id.text_dont);
        tv_register = (TextView) findViewById(R.id.text_login);
        tv_footer = (TextView) findViewById(R.id.terms);

        et_fname = (MaterialEditText) findViewById(R.id.edittext_fname);
        et_lname = (MaterialEditText) findViewById(R.id.edittext_lname);
        et_email = (MaterialEditText) findViewById(R.id.edittext_email);
        et_pass = (MaterialEditText) findViewById(R.id.edittext_pass);
        et_repass = (MaterialEditText) findViewById(R.id.edittext_repass);
        et_phone = (MaterialEditText) findViewById(R.id.edittext_phone);


        // aet_cont = (MaterialAutoCompleteTextView) findViewById(R.id.edittext_country);
        aet_state = (MaterialAutoCompleteTextView) findViewById(R.id.edittext_state);
        aet_zip = (MaterialAutoCompleteTextView) findViewById(R.id.edittext_zipcode);


        final Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");

        btn_submit.setTypeface(tf);
        tv_donthav.setTypeface(tf);
        tv_register.setTypeface(tf);
        tv_footer.setTypeface(tf);

        et_fname.setTypeface(tf);
        et_lname.setTypeface(tf);
        et_email.setTypeface(tf);
        et_pass.setTypeface(tf);
        et_repass.setTypeface(tf);
        et_phone.setTypeface(tf);

//        aet_cont.setTypeface(tf);
        aet_zip.setTypeface(tf);
        aet_state.setTypeface(tf);


        countries = new String[]{"US", "NIGERIA", "CANADA"};




       /* spin.setOnItemClickListener(new Spinner.OnItemClickListener() {
            @Override
            public boolean onItemClick(Spinner parent, View view, int position, long id) {

                TextView stsx = (TextView) view.findViewById(R.id.text_spin);
                stsx.setTypeface(tf);


                str_country = spin.getSelectedItem().toString();
                Log.d("tag",str_country);
                new GetState().execute();
                return true;
            }
        });*/


        spin.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {


                str_country = spin.getSelectedItem().toString();
                Log.d("tag", str_country);
                new GetState().execute();


            }
        });



        if (!Config.isNetworkAvailable(RegisterActivity.this)) {

            new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Oops!")
                    .setContentText("No network Available!")
                    .setConfirmText("OK")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {

                            // sweetAlertDialog.setCancelable(false);

                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                            finish();
                        }
                    })

                    .show();


        } else {
            new GetCountry().execute();
        }


        adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_lists2, R.id.text_spin, states);
        aet_state.setAdapter(adapter2);


        /*ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_lists, R.id.text_spin, country);
        aet_cont.setAdapter(adapter1);*/






       /* et_phone.setOnEditorActionListener(new MaterialEditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(android.widget.TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    //str_country = aet_cont.getText().toString();
                    new GetState().execute();
                    aet_state.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

                    *//*ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_lists, R.id.text_spin, ar_states);
                    aet_state.setAdapter(adapter2);*//*
                    return true;
                }
                return false;
            }
        });*/

        aet_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetState().execute();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });


        aet_state.setOnEditorActionListener(new MaterialEditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(android.widget.TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    //str_country = aet_cont.getText().toString();
                    str_state = aet_state.getText().toString();
                    new GetZip().execute();
                    aet_zip.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                    return true;
                }
                return false;
            }
        });


        aet_zip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetZip().execute();
                aet_zip.requestFocus();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });


       /* aet_zip.setOnEditorActionListener(new MaterialEditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(android.widget.TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                    validatedatas();
                    return true;
                }
                return false;
            }
        });*/


        ll_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(login_intent);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                validatedatas();

            }
        });
    }


    public void validatedatas() {


        //   str_country = aet_cont.getText().toString();
        str_fname = et_fname.getText().toString();
        str_lname = et_lname.getText().toString();
        str_email = et_email.getText().toString();
        str_pass = et_pass.getText().toString();
        str_repass = et_repass.getText().toString();
        str_phone = et_phone.getText().toString();
        str_zip = aet_zip.getText().toString();
        str_state = aet_state.getText().toString();

        //  if (!str_country.isEmpty()) {

            if (!str_fname.isEmpty()) {
                if (!str_lname.isEmpty()) {
                    if (!(str_email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(str_email).matches())) {
                        if (!(str_pass.isEmpty() || str_pass.length() < 4 || str_pass.length() > 10)) {
                            if (str_repass.matches(str_pass)) {
                                if (!(str_phone.isEmpty() || str_phone.length() < 10)) {
                                    if (!str_state.isEmpty()) {
                                        if (!(str_zip.isEmpty() || str_zip.length() < 3)) {

                                            new RegisterTask().execute();

                                                 /*   SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this);
                                                    SharedPreferoonbuxidences.Editor editor = sharedPreferences.edit();
                                                    editor.putString("register", "success");


                                                    editor.putString("", "535SM3");
                                                    editor.putString("fname", str_fname);
                                                    editor.putString("lname", str_lname);
                                                    editor.putString("mail", str_email);
                                                    editor.putString("phone", str_phone);
                                                    editor.putString("state", str_state);
                                                    editor.putString("zip", str_zip);
                                                    editor.putString("country", str_country);


                                                    editor.commit();


                                                    Dialog_new cdd = new Dialog_new(RegisterActivity.this, "Check your mail for oonbux activation");
                                                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                                    cdd.show();*/


                                        } else {
                                            aet_zip.setError("Enter zipcode");
                                            aet_zip.requestFocus();

                                        }
                                    } else {
                                        aet_state.setError("Enter State");
                                        aet_state.requestFocus();
                                    }
                                } else {
                                    et_phone.setError("Enter valid phone number");
                                    et_phone.requestFocus();

                                }
                            } else {
                                et_repass.setError("Password not matching!");
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
                    et_lname.setError("Enter a Last Name!");
                    et_lname.requestFocus();
                }
            } else {
                et_fname.setError("Enter a First Name!");
                et_fname.requestFocus();
            }
        /*} else {
            aet_cont.setError("Enter a valid Country!");
            aet_cont.requestFocus();
        }*/

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
                jsonObject.accumulate("firstname", str_fname);
                jsonObject.accumulate("lastname", str_lname);
                Log.d("TAG", "lastname" + str_lname);
                jsonObject.accumulate("email", str_email);
                jsonObject.accumulate("phone", str_phone);
                jsonObject.accumulate("password", str_pass);
                jsonObject.accumulate("state", str_state);
                jsonObject.accumulate("country", str_country);
                jsonObject.accumulate("zip", str_zip);
                // 4. convert JSONObject to JSON to String
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest(Config.SER_URL + "registration", json);
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


                    Dialog_new cdd = new Dialog_new(RegisterActivity.this, "Thank you for Signing Up Oonbux.\nAn Activation Email Sent to " + str_email + "\nPlease check your mail for Activation details.", 0);
                    cdd.setCancelable(false);
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();


                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("register", "SUCCESS");
                    editor.commit();



                /*    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Message Alert")
                            .setContentText(msg)
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    sweetAlertDialog.cancel();
                                    Intent goD = new Intent(getApplicationContext(), ProfileInfo.class);
                                    startActivity(goD);
                                    finish();


                                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("register", "SUCCESS");
                                    editor.commit();


                                }
                            })
                            .show();*/


                   /* Intent login_intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(login_intent);
                    finish();*/


                    // Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                } else if (status.equals(null)) {
                    Toast.makeText(getApplicationContext(), "network not available", Toast.LENGTH_LONG).show();
                } else if (status.equals("fail")) {


                    Dialog_Msg cdd = new Dialog_Msg(RegisterActivity.this, msg);
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.setCancelable(false);
                    cdd.show();





   /*                 if (msg.equals("Email ID is already registered")) {*/
                   /* new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops!")
                            .setContentText(msg)
                            .setConfirmText("OK")
                            .show();*/
                    //et_email.setText("");
                    if (msg.contains("Email")) {
                        et_email.requestFocus();
                    } else if (msg.contains("Region")) {
                        aet_cont.requestFocus();
                    }
                    // et_email.setError("");


                    // Toast.makeText(getApplicationContext(), "Check your internet or try again", Toast.LENGTH_LONG).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }


    class GetCountry extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            sweetAlertDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            sweetAlertDialog.setTitleText("Loading");
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.show();
        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";
            try {

                String country_url = Config.SER_URL + "region/country";
                JSONObject jsonobject = HttpUtils.getData(country_url);

             /*   if (jsonobject.toString().equals(null)) {
                    Log.e("tag", "jj" + jsonobject);
                    json = "";
                } else {

*/
                Log.d("tag", "" + jsonobject.toString());

                if (jsonobject.toString() == "sam") {
                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops!")
                            .setContentText("Try Check your Network")
                            .setConfirmText("OK")
                            .show();
                }

                json = jsonobject.toString();
                //}
                return json;
            } catch (Exception e) {
                Log.e("InputStream", "" + e.getLocalizedMessage());
                jsonStr = "";
                sweetAlertDialog.dismiss();
            }
            return jsonStr;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.e("tag", "<-----rerseres---->" + jsonStr);
            super.onPostExecute(jsonStr);
            sweetAlertDialog.dismiss();

            try {

                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("country");
                String msg = jo.getString("message");
                Log.d("tag", "<-----aasd----->" + status);
                String json = jo.toString();

               /* if (status.equals("success")) {*/

                JSONObject jaa = new JSONObject(jsonStr);
                JSONArray jj = jaa.getJSONArray("country");
                Log.d("tag", "<-----S---->" + jj);

                for (int i1 = 0; i1 < jj.length(); i1++) {

                    String daa = jj.getString(i1);
                    country.add(daa);
                    Log.d("tag", "<-----Statusss----->" + daa);

                }


                ArrayAdapter<String> adapter1_spin = new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_lists, R.id.text_spin, country);
                spin.setAdapter(adapter1_spin);
                adapter1_spin.notifyDataSetChanged();


                gdatas.setCountry_Datas(country);



                //} /*else if (status.equals("")) {
/*
                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops!")
                            .setContentText("failed to fetch country")
                            .setConfirmText("OK")
                            .show();

                } else if (status.equals("fail")) {

                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops!")
                            .setContentText(msg)
                            .setConfirmText("OK")
                            .show();

                }*/

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }


    class GetState extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {

            sweetAlertDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            sweetAlertDialog.setTitleText("Loading");
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.show();
            super.onPreExecute();


        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";

            try {


                Log.d("tag", "" + str_country);

                String state_url = Config.SER_URL + "region/state";

                JSONObject jsonobject = HttpUtils.getData2(state_url, str_country);

                Log.e("tag", "jj" + jsonobject);

                json = jsonobject.toString();

                return json;
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

            return null;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.e("tag", "<-----rerseres---->" + jsonStr);
            sweetAlertDialog.dismiss();
            // aet_state.requestFocus();
            // et_fname.requestFocus();

            super.onPostExecute(jsonStr);
            if (jsonStr == "") {

            } else {

                try {
                    JSONObject jo = new JSONObject(jsonStr);
                    String status = jo.getString("state");
                    String msg = jo.getString("message");
                    Log.d("tag", "<-----Statasdfus----->" + status);


                    if (status.equals("null")) {


                        Log.d("tag", "<--> state not available for this country");


                    } else {


                        String json = jo.toString();


                        JSONObject jaa = new JSONObject(jsonStr);
                        JSONArray jj = jaa.getJSONArray("state");
                        Log.d("tag", "<-----S---->" + jj);

                        //JSONArray ja = jo.getJSONArray(status);

                        for (int i1 = 0; i1 < jj.length(); i1++) {


                            ar_states = new String[jj.length()];

                            // JSONObject data = jj.getJSONObject(i1);

                            String daa = jj.getString(i1);

                            ar_states[i1] = daa;
                            //countries[jj.length()] =
                            states.add(daa);
                            Log.d("tag", "<-----Statusss----->" + ar_states[i1]);

                        }

                        adapter2.notifyDataSetChanged();


                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }


    class GetZip extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {

            sweetAlertDialog = new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            sweetAlertDialog.setTitleText("Loading");
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.show();

            super.onPreExecute();
        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";

            try {



                String zip_url = Config.SER_URL + "region/zip";
                JSONObject jsonobject = HttpUtils.getData3(zip_url, str_country, str_state);

                Log.e("tag", "jj" + jsonobject);

                json = jsonobject.toString();

                return json;
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

            return null;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.e("tag", "<-----rerseres---->" + jsonStr);
            sweetAlertDialog.dismiss();
            super.onPostExecute(jsonStr);
            if (jsonStr == "") {

            } else {

                try {
                    JSONObject jo = new JSONObject(jsonStr);
                    String status = jo.getString("zip");
                    String msg = jo.getString("message");
                    Log.d("tag", "<-----Statasdfus----->" + status);

                    String json = jo.toString();


                    JSONObject jaa = new JSONObject(jsonStr);
                    JSONArray jj = jaa.getJSONArray("zip");
                    Log.d("tag", "<-----S---->" + jj);

                    //JSONArray ja = jo.getJSONArray(status);

                    for (int i1 = 0; i1 < jj.length(); i1++) {

                        // JSONObject data = jj.getJSONObject(i1);

                        String daa = jj.getString(i1);

                        //countries[jj.length()] =
                        zip.add(daa);
                        Log.d("tag", "<-----Statusss----->" + daa);
                        adapter2.notifyDataSetChanged();
                    }


                    ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_lists2, R.id.text_spin, zip);
                    aet_zip.setAdapter(adapter3);


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }


}
