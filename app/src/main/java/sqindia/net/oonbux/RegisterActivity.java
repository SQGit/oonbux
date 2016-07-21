package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

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

//afssadf

public class RegisterActivity extends Activity {
    Button btn_submit;
    TextView tv_donthav, tv_register, tv_footer;
    LinearLayout ll_login;
    MaterialEditText et_fname, et_lname, et_email, et_phone, et_pass, et_repass;
    MaterialAutoCompleteTextView   aet_zips;
    String str_fname, str_lname, str_country, str_email, str_phone, str_pass, str_repass, str_state, str_zip;
    SweetAlertDialog sweetAlertDialog;
    ArrayList<String> country = new ArrayList<>();
    ArrayList<String> states = new ArrayList<>();
    ArrayList<String> zip = new ArrayList<>();
    Spinner spin_country,spin_states,spin_zip;
    ListAdapter_Class country_list_adapter, state_list_adapter, zip_list_adapter;

    ArrayAdapter<String> adpater_states;
    ArrayAdapter<String> adapter_zip;

    DbC dbclass;
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getIds_Fonts();
        str_country = "us";
        dbclass = new DbC(context);

      /*  if (!Config.isNetworkAvailable(RegisterActivity.this)) {
            new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Oops!")
                    .setContentText("No network Available!")
                    .setConfirmText("OK")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                            finish();
                        }
                    })
                    .show();
        } else {
            new GetCountry().execute();


        }*/


        get_CountryDB();

        spin_country.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                //str_country = spin_country.getSelectedItem().toString();
                states.clear();
                get_StateDB();
                //new GetState(str_country).execute();

            }
        });

        spin_states.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {

                zip.clear();
               // get_ZipDB();
                get_zipServ();
                //str_country = spin_country.getSelectedItem().toString();
                //states.clear();
                //get_StateDB();
                //new GetState(str_country).execute();

            }
        });

       /* aet_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //str_country = spin_country.getSelectedItem().toString();
                states.clear();
                get_StateDB();
                //new GetState(str_country).execute();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });
        aet_state.setOnEditorActionListener(new MaterialEditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(android.widget.TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (aet_state.getText().toString() != null) {
                        str_country = spin_country.getSelectedItem().toString();
                        str_state = aet_state.getText().toString();
                        zip.clear();

                        get_ZipDB();

                      //  new GetZip(str_country, str_state).execute();
                        aet_zip.requestFocus();
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        return true;
                    } else {
                        aet_state.requestFocus();
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        return true;
                    }
                }
                return false;
            }
        });*/

/*

        aet_zip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aet_state.getText().toString() != null) {
                    str_country = spin_country.getSelectedItem().toString();
                    str_state = aet_state.getText().toString();
                    zip.clear();
                    get_ZipDB();
                    //new GetZip(str_country, str_state).execute();
                    aet_zip.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                } else {
                    aet_state.requestFocus();
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

                }
            }
        });
*/

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


                if (!Config.isNetworkAvailable(RegisterActivity.this)) {
                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops!")
                            .setContentText("No network Available!")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                                    finish();
                                }
                            })
                            .show();
                } else {
                    validatedatas();


                }




            }
        });





    }

    private void get_CountryDB() {

        Cursor cont_cursor = dbclass.getCountry();

        if (cont_cursor != null) {
            if (cont_cursor.moveToFirst()) {
                do {

                    String cont = cont_cursor.getString(cont_cursor.getColumnIndex("country"));
                    Log.e("tag",""+cont);
                    country.add(cont);

                    country_list_adapter = new ListAdapter_Class(getApplicationContext(), R.layout.dropdown_lists, country);
                    spin_country.setAdapter(country_list_adapter);


                } while (cont_cursor.moveToNext());
            }
        }

        get_StateDB();

    }


    private void get_StateDB() {

        String country = spin_country.getSelectedItem().toString();

        Cursor cont_cursor = dbclass.getState(country);

        if (cont_cursor != null) {
            if (cont_cursor.moveToFirst()) {
                do {

                    String stat = cont_cursor.getString(cont_cursor.getColumnIndex("state"));
                    Log.e("tag",""+stat);
                    states.add(stat);

                    adpater_states = new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_lists2, R.id.text_spin, states);

                    state_list_adapter = new ListAdapter_Class(getApplicationContext(), R.layout.dropdown_lists, states);
                    spin_states.setAdapter(state_list_adapter);

                } while (cont_cursor.moveToNext());
            }
        }

        get_zipServ();


    }


    private void get_zipServ(){
        str_country = spin_country.getSelectedItem().toString();
        str_state = spin_states.getSelectedItem().toString();
        zip.clear();
        new GetZip(str_country, str_state).execute();
        spin_zip.requestFocus();
    }


    private void get_ZipDB() {

        String country = spin_country.getSelectedItem().toString();
        String state = spin_states.getSelectedItem().toString();

        Cursor cont_cursor = dbclass.getZip(country,state);

        if (cont_cursor != null) {
            if (cont_cursor.moveToFirst()) {
                do {

                    String zp = cont_cursor.getString(cont_cursor.getColumnIndex("zip"));
                    Log.e("tag",""+zp);
                    zip.add(zp);

                    adapter_zip = new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_lists2, R.id.text_spin, zip);
                    spin_zip.setAdapter(adapter_zip);

                } while (cont_cursor.moveToNext());
            }
        }
    }


    private void getIds_Fonts() {
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
      //  aet_state = (MaterialAutoCompleteTextView) findViewById(R.id.edittext_state);
       // aet_zip = (MaterialAutoCompleteTextView) findViewById(R.id.edittext_zipcode);
        spin_country = (com.rey.material.widget.Spinner) findViewById(R.id.spin_country);
        spin_states = (com.rey.material.widget.Spinner) findViewById(R.id.spin_states);
        spin_zip = (com.rey.material.widget.Spinner) findViewById(R.id.spin_zip);

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
       // aet_zip.setTypeface(tf);
       // aet_state.setTypeface(tf);
    }

    public void validatedatas() {
        str_fname = et_fname.getText().toString();
        str_lname = et_lname.getText().toString();
        str_email = et_email.getText().toString();
        str_pass = et_pass.getText().toString();
        str_repass = et_repass.getText().toString();
        str_phone = et_phone.getText().toString();
        str_zip = spin_zip.getSelectedItem().toString();
        //aet_zip.getText().toString();
        str_state = spin_states.getSelectedItem().toString();
        //aet_state.getText().toString();

        if (!str_fname.isEmpty()) {
            if (!str_lname.isEmpty()) {
                if (!(str_email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(str_email).matches())) {
                    if (!(str_pass.isEmpty() || str_pass.length() < 4 || str_pass.length() > 10)) {
                        if (str_repass.matches(str_pass)) {
                            if (!(str_phone.isEmpty() || str_phone.length() < 10)) {


                                        new RegisterTask().execute();


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
                jsonObject.accumulate("email", str_email);
                jsonObject.accumulate("phone", str_phone);
                jsonObject.accumulate("password", str_pass);
                jsonObject.accumulate("state", str_state);
                jsonObject.accumulate("country", str_country);
                jsonObject.accumulate("zip", str_zip);
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
                    String sus_txt = "Thank you for Signing Up Oonbux.\nAn Activation Email Sent to " + str_email + "\nPlease check your mail for Activation details.";
                    Dialog_new cdd = new Dialog_new(RegisterActivity.this, sus_txt, 0);
                    cdd.setCancelable(false);
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.show();
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("register", "SUCCESS");
                    editor.commit();
                } else if (status.equals("fail")) {
                    Dialog_Msg cdd = new Dialog_Msg(RegisterActivity.this, msg);
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.setCancelable(false);
                    cdd.show();
                    if (msg.contains("Email")) {
                        et_email.requestFocus();
                    } else if (msg.contains("Region")) {
                       // aet_state.requestFocus();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.WARNING_TYPE)
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
                Log.d("tag", "" + jsonobject.toString());
                if (jsonobject.toString() == "sam") {
                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.WARNING_TYPE)
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
                JSONObject jaa = new JSONObject(jsonStr);
                JSONArray jj = jaa.getJSONArray("country");
                Log.d("tag", "<-----S---->" + jj);
                for (int i1 = 0; i1 < jj.length(); i1++) {
                    String daa = jj.getString(i1);
                    country.add(daa);
                    Log.d("tag", "<-----Statusss----->" + daa);
                }
                country_list_adapter = new ListAdapter_Class(getApplicationContext(), R.layout.dropdown_lists, country);
                spin_country.setAdapter(country_list_adapter);


            } catch (JSONException e) {
                e.printStackTrace();
                new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.WARNING_TYPE)
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

    class GetState extends AsyncTask<String, Void, String> {
        String country;

        public GetState(String con) {
            this.country = con;
        }

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
                Log.d("tag", "" + country);
                String state_url = Config.SER_URL + "region/state";
                JSONObject jsonobject = HttpUtils.getData2(state_url, country);
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
                    String status = jo.getString("state");
                    String msg = jo.getString("message");
                    Log.d("tag", "<-----Statasdfus----->" + status);
                    if (status.equals("null")) {
                        Log.d("tag", "<--> state not available for this country");
                    } else {
                        String json = jo.toString();
                        JSONObject jaa = new JSONObject(jsonStr);
                        JSONArray jj = jaa.getJSONArray("state");

                        for (int i1 = 0; i1 < jj.length(); i1++) {
                            String daa = jj.getString(i1);
                            states.add(daa);
                            Log.d("tag", states.get(i1));
                        }

                        adpater_states.notifyDataSetChanged();
                        Log.d("tag", "" + String.valueOf(states.size()));

                        /*state_list_adapter = new ListAdapter_Class(getApplicationContext(), R.layout.dropdown_lists2, states);
                        aet_state.setAdapter(state_list_adapter);
                        state_list_adapter.notifyDataSetChanged();*/

                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops!")
                            .setContentText("Network Error,Try Again Later.")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            })
                            .show();
                }
            }
        }

    }

    class GetZip extends AsyncTask<String, Void, String> {
        String country, state;

        public GetZip(String cont, String sta) {
            this.country = cont;
            this.state = sta;
        }

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
                JSONObject jsonobject = HttpUtils.getData3(zip_url, country, state);
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
                    for (int i1 = 0; i1 < jj.length(); i1++) {
                        String daa = jj.getString(i1);
                        zip.add(daa);
                        Log.d("tag", "<-----Statusss----->" + daa);
                    }


                //    adapter_zip = new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_lists2, R.id.text_spin, zip);

                    zip_list_adapter = new ListAdapter_Class(getApplicationContext(), R.layout.dropdown_lists, zip);
                    spin_zip.setAdapter(zip_list_adapter);

                   //ArrayAdapter<String> adapter_zip = new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_lists2, R.id.text_spin, zip);
                  //  aet_zip.setAdapter(adapter_zip);

                   /* zip_list_adapter = new ListAdapter_Class(getApplicationContext(), R.layout.dropdown_lists2, zip);
                    aet_zip.setAdapter(zip_list_adapter);*/

                } catch (JSONException e) {
                    e.printStackTrace();
                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops!")
                            .setContentText("No zip associated with given state \n try again")
                            .setConfirmText("OK")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    //aet_state.requestFocus();
                                }
                            })
                            .show();

                }
            }
        }

    }


}
