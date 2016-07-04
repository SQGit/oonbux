package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.Button;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.Spinner;
import com.rey.material.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ProfilePhysicalDeliveryAddress extends Activity {

    LinearLayout bottom_lt, lt_add_address;
    TextView tv_header, tv_sub_hdr_loc, tv_sub_hdr_int;
    Button btn_save_loc, btn_save_int, btn_next, btn_edit_loc, btn_edit_int;
    com.rey.material.widget.LinearLayout lt_back;
    MaterialEditText et_loc_add1, et_loc_add2, et_loc_city, et_loc_phone, et_loc_note, et_int_add1, et_int_add2, et_int_city, et_int_phone, et_int_note;
    String get_profile_sts, str_def_adr, str_loc_add1, str_loc_add2, str_loc_city, str_loc_zip, str_loc_phone,
            str_loc_note, str_int_add1, str_int_add2, str_int_city, str_int_zip, str_int_phone, str_int_note;
    CheckBox cb_loc, cb_int;
    boolean loc_adr, int_adr;
    Spinner spin_loc, spin_int;
    String[] countries;
    Typeface tf, tf1;
    SweetAlertDialog sweetAlertDialog;
    MyAdapter adapter_a, adapter_b, adapter_c;

    String str_country, str_state;

    ArrayList<String> country = new ArrayList<>();
    ArrayList<String> states = new ArrayList<>();
    ArrayList<String> zip = new ArrayList<>();

    MaterialAutoCompleteTextView aet_loc_state, aet_loc_zip, aet_int_state, aet_int_zip;

    String str_loc_country, str_loc_state, str_int_country, str_int_state;

    ArrayAdapter<String> adpater_states, adpater_states_, adapter_zips, adapter_zips_;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);

        countries = new String[]{"US", "NIGERIA", "GAANA"};
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfilePhysicalDeliveryAddress.this);
        get_profile_sts = sharedPreferences.getString("profile", "");
        Log.e("tag", "pfs" + get_profile_sts);

        str_loc_add1 = sharedPreferences.getString("loc_addr1", "");
        str_loc_add2 = sharedPreferences.getString("loc_addr2", "");
        str_loc_city = sharedPreferences.getString("loc_city", "");
        str_loc_state = sharedPreferences.getString("loc_state", "");
        str_loc_zip = sharedPreferences.getString("loc_zip", "");
        str_loc_country = sharedPreferences.getString("loc_country", "");
        str_loc_phone = sharedPreferences.getString("loc_phone", "");
        str_loc_note = sharedPreferences.getString("loc_note", "");
        str_int_add1 = sharedPreferences.getString("int_addr1", "");
        str_int_add2 = sharedPreferences.getString("int_addr2", "");
        str_int_city = sharedPreferences.getString("int_city", "");
        str_int_state = sharedPreferences.getString("int_state", "");
        str_int_zip = sharedPreferences.getString("int_zip", "");
        str_int_phone = sharedPreferences.getString("int_phone", "");
        str_int_country = sharedPreferences.getString("int_country", "");
        str_int_note = sharedPreferences.getString("int_note", "");
        str_def_adr = sharedPreferences.getString("default_adr", "");


        tv_header = (TextView) findViewById(R.id.tv_hd_txt);
        tv_sub_hdr_loc = (TextView) findViewById(R.id.tv_shd_txt_loc);
        tv_sub_hdr_int = (TextView) findViewById(R.id.tv_shd_txt_int);
        btn_save_loc = (Button) findViewById(R.id.button_save_loc);
        btn_save_int = (Button) findViewById(R.id.button_save_int);
        btn_edit_loc = (Button) findViewById(R.id.button_add_loc);
        btn_edit_int = (Button) findViewById(R.id.button_add_int);
        btn_next = (Button) findViewById(R.id.button_next);
        et_loc_add1 = (MaterialEditText) findViewById(R.id.edittext_loc_address1);
        et_loc_add2 = (MaterialEditText) findViewById(R.id.edittext_loc_address2);
        et_loc_city = (MaterialEditText) findViewById(R.id.edittext_loc_city);
        aet_loc_state = (MaterialAutoCompleteTextView) findViewById(R.id.edittext_loc_state);
        aet_loc_zip = (MaterialAutoCompleteTextView) findViewById(R.id.edittext_loc_zip);
        et_loc_phone = (MaterialEditText) findViewById(R.id.edittext_loc_phone);
        et_loc_note = (MaterialEditText) findViewById(R.id.edittext_loc_note);
        et_int_add1 = (MaterialEditText) findViewById(R.id.edittext_int_address1);
        et_int_add2 = (MaterialEditText) findViewById(R.id.edittext_int_address2);
        et_int_city = (MaterialEditText) findViewById(R.id.edittext_int_city);
        aet_int_state = (MaterialAutoCompleteTextView) findViewById(R.id.edittext_int_state);
        aet_int_zip = (MaterialAutoCompleteTextView) findViewById(R.id.edittext_int_zip);
        et_int_phone = (MaterialEditText) findViewById(R.id.edittext_int_phone);
        et_int_note = (MaterialEditText) findViewById(R.id.edittext_int_note);
        lt_back = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_back);
        bottom_lt = (LinearLayout) findViewById(R.id.bottom);
        cb_loc = (CheckBox) findViewById(R.id.checkbox_local);
        cb_int = (CheckBox) findViewById(R.id.checkbox_int);
        spin_loc = (Spinner) findViewById(R.id.loc_spin_country);
        spin_int = (Spinner) findViewById(R.id.int_spin_country);

        lt_add_address = (LinearLayout) findViewById(R.id.layout_add);


        if (!Config.isNetworkAvailable(ProfilePhysicalDeliveryAddress.this)) {
            new SweetAlertDialog(ProfilePhysicalDeliveryAddress.this, SweetAlertDialog.WARNING_TYPE)
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
            new GetCountry().execute();
        }

        spin_loc.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                str_loc_country = spin_loc.getSelectedItem().toString();
                new GetState(str_loc_country, 0).execute();
            }
        });

/*        spin_int.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                str_int_country = spin_int.getSelectedItem().toString();
                new GetState(str_int_country, 1).execute();
            }
        });*/


        spin_int.setOnItemClickListener(new Spinner.OnItemClickListener() {
            @Override
            public boolean onItemClick(Spinner parent, View view, int position, long id) {

                str_int_country = spin_int.getSelectedItem().toString();
                new GetState(str_int_country, 1).execute();
                return true;
            }
        });


        aet_loc_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_loc_country = spin_loc.getSelectedItem().toString();
                new GetState(str_loc_country, 0).execute();
                aet_loc_state.requestFocus();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });
        aet_int_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_int_country = spin_loc.getSelectedItem().toString();
                new GetState(str_int_country, 1).execute();
                aet_int_state.requestFocus();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });


        aet_loc_state.setOnEditorActionListener(new MaterialEditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(android.widget.TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (aet_loc_state.getText().toString() != null) {
                        str_loc_country = spin_loc.getSelectedItem().toString();
                        str_loc_state = aet_loc_state.getText().toString();
                        new GetZip(str_loc_country, str_loc_state, 0).execute();
                        aet_loc_zip.requestFocus();
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        return true;
                    } else {
                        aet_loc_state.requestFocus();
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        return true;
                    }
                }
                return false;
            }
        });
        aet_int_state.setOnEditorActionListener(new MaterialEditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(android.widget.TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (aet_int_state.getText().toString() != null) {
                        str_int_country = spin_int.getSelectedItem().toString();
                        str_int_state = aet_int_state.getText().toString();
                        new GetZip(str_int_country, str_int_state, 1).execute();
                        aet_int_zip.requestFocus();
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        return true;
                    } else {
                        aet_int_state.requestFocus();
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        return true;
                    }
                }
                return false;
            }
        });


        et_loc_city.setOnEditorActionListener(new MaterialEditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(android.widget.TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (aet_loc_state.getText().toString() != null) {
                        str_country = spin_loc.getSelectedItem().toString();
                        str_state = aet_loc_state.getText().toString();
                        new GetZip(str_country, str_state, 0).execute();
                        aet_loc_zip.requestFocus();
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        return true;
                    } else {
                        aet_loc_state.requestFocus();
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        return true;
                    }
                }
                return false;
            }
        });

        et_int_city.setOnEditorActionListener(new MaterialEditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(android.widget.TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    if (aet_int_state.getText().toString() != null) {
                        str_int_country = spin_int.getSelectedItem().toString();
                        str_int_state = aet_int_state.getText().toString();
                        new GetZip(str_int_country, str_int_state, 1).execute();
                        aet_int_zip.requestFocus();
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        return true;
                    } else {
                        aet_int_state.requestFocus();
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                        return true;
                    }
                }
                return false;
            }
        });


        aet_loc_zip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_loc_country = spin_loc.getSelectedItem().toString();
                str_loc_state = aet_loc_state.getText().toString();
                new GetZip(str_loc_country, str_loc_state, 0).execute();
                aet_loc_zip.requestFocus();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });

        aet_int_zip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_int_country = spin_int.getSelectedItem().toString();
                str_int_state = aet_int_state.getText().toString();
                new GetZip(str_int_country, str_int_state, 1).execute();
                aet_loc_zip.requestFocus();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        });


        lt_add_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addressPart = new Intent(getApplicationContext(), ManageAddress.class);
                startActivity(addressPart);
            }
        });


        if (str_def_adr.equals("LOCAL")) {
            cb_loc.setChecked(true);
        } else if (str_def_adr.equals("INTERNATIONAL")) {
            cb_int.setChecked(true);
        }


        if (!(str_loc_add1.equals("null"))) {

            getfromdata_loc();
        } else {
            enable_loc();
        }


        if (!(str_int_add1.equals("null"))) {

            getfromdata_int();
        } else {
            enable_int();
        }


        if ((get_profile_sts.equals(""))) {
            bottom_lt.setVisibility(View.VISIBLE);
        } else {
            bottom_lt.setVisibility(View.GONE);
            disable_loc();
            disable_int();
            btn_save_loc.setVisibility(View.GONE);
            btn_save_int.setVisibility(View.GONE);
        }


        if (sharedPreferences.getBoolean("adrsts0", false)) {
            loc_adr = true;
            disable_loc();
            btn_save_loc.setVisibility(View.GONE);

        }
        else{
               et_int_add1.setText("");
                et_int_add1.requestFocus();
                et_int_add2.setText("");
                et_int_city.setText("");
                aet_int_state.setText("");
                aet_int_zip.setText("");
                et_int_phone.setText("");
                et_int_note.setText("");
        }

        if (sharedPreferences.getBoolean("adrsts1", false)) {
            int_adr = true;
            disable_int();
            btn_save_int.setVisibility(View.GONE);
        }
        else{

              et_loc_add1.setText("");
                et_loc_add1.requestFocus();
                et_loc_add2.setText("");
                et_loc_city.setText("");
                aet_loc_state.setText("");
                aet_loc_zip.setText("");
                et_loc_phone.setText("");
                et_loc_note.setText("");

        }


        tf = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");
        tf1 = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");

        tv_header.setTypeface(tf);

        tv_sub_hdr_loc.setTypeface(tf1);
        tv_sub_hdr_int.setTypeface(tf1);

        btn_save_loc.setTypeface(tf1);
        btn_save_int.setTypeface(tf1);
        btn_edit_loc.setTypeface(tf1);
        btn_edit_int.setTypeface(tf1);
        btn_next.setTypeface(tf1);

        et_loc_add1.setTypeface(tf1);
        et_loc_add2.setTypeface(tf1);
        et_loc_city.setTypeface(tf1);
        aet_loc_state.setTypeface(tf1);
        aet_loc_zip.setTypeface(tf1);
        et_loc_phone.setTypeface(tf1);
        et_loc_note.setTypeface(tf1);

        et_int_add1.setTypeface(tf1);
        et_int_add2.setTypeface(tf1);
        et_int_city.setTypeface(tf1);
        aet_int_state.setTypeface(tf1);
        aet_int_zip.setTypeface(tf1);
        et_int_phone.setTypeface(tf1);
        et_int_note.setTypeface(tf1);


        cb_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!loc_adr) {
                    cb_loc.setChecked(false);


                    new SweetAlertDialog(ProfilePhysicalDeliveryAddress.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Save Address")
                            .setConfirmText("OK")

                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {

                                    sDialog.dismiss();
                                }
                            })
                            .show();
                } else {
                    cb_int.setChecked(false);

                    if (cb_loc.isChecked()) {
                        str_def_adr = "LOCAL";
                    } else if (cb_int.isChecked()) {
                        str_def_adr = "INTERNATIONAL";
                    }

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfilePhysicalDeliveryAddress.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("default_adr", str_def_adr);
                    editor.commit();


                }


            }
        });

        cb_int.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!int_adr) {

                    cb_int.setChecked(false);

                    new SweetAlertDialog(ProfilePhysicalDeliveryAddress.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Save Address")
                            .setConfirmText("OK")

                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {

                                    sDialog.dismiss();
                                }
                            })
                            .show();
                } else {
                    cb_loc.setChecked(false);


                    if (cb_loc.isChecked()) {
                        str_def_adr = "LOCAL";
                    } else if (cb_int.isChecked()) {
                        str_def_adr = "INTERNATIONAL";
                    }

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfilePhysicalDeliveryAddress.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("default_adr", str_def_adr);
                    editor.commit();


                }

            }
        });


        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((get_profile_sts.equals(""))) {
                  /*  Intent inte = new Intent(getApplicationContext(), ProfileInfo.class);
                    startActivity(inte);*/


                    Intent intes = new Intent(getApplicationContext(), ProfileInfo.class);
                    startActivity(intes);


                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfilePhysicalDeliveryAddress.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("profile", "");
                    editor.commit();

                    // Toast.makeText(getApplicationContext(), "Please complete your profile", Toast.LENGTH_LONG).show();
                } else {
                    Intent inte = new Intent(getApplicationContext(), ProfileDashboard.class);
                    startActivity(inte);
                }

            }
        });


        btn_edit_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loc_adr = false;
                btn_save_loc.setVisibility(View.VISIBLE);
                enable_loc();

                bottom_lt.setVisibility(View.VISIBLE);

                et_loc_add1.requestFocus();

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfilePhysicalDeliveryAddress.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("profile", "");
                editor.commit();

               /* et_loc_add1.setText("");
                et_loc_add1.requestFocus();
                et_loc_add2.setText("");
                et_loc_city.setText("");
                aet_loc_state.setText("");
                aet_loc_zip.setText("");
                et_loc_phone.setText("");
                et_loc_note.setText("");*/
            }
        });


        btn_edit_int.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int_adr = false;
                btn_save_int.setVisibility(View.VISIBLE);
                enable_int();

                bottom_lt.setVisibility(View.VISIBLE);

                et_int_add1.requestFocus();

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfilePhysicalDeliveryAddress.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("profile", "");
                editor.commit();

                /*et_int_add1.setText("");
                et_int_add1.requestFocus();
                et_int_add2.setText("");
                et_int_city.setText("");
                aet_int_state.setText("");
                aet_int_zip.setText("");
                et_int_phone.setText("");
                et_int_note.setText("");*/
            }
        });


        btn_save_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_loc_add1 = et_loc_add1.getText().toString();
                str_loc_add2 = et_loc_add2.getText().toString();
                str_loc_city = et_loc_city.getText().toString();
                str_loc_state = aet_loc_state.getText().toString();
                str_loc_zip = aet_loc_zip.getText().toString();
                str_loc_phone = et_loc_phone.getText().toString();
                str_loc_note = et_loc_note.getText().toString();
                str_loc_country = spin_loc.getSelectedItem().toString();


                validate(0, str_loc_add1, str_loc_add2, str_loc_city, str_loc_state, str_loc_zip, str_loc_phone, str_loc_note, str_loc_country);


            }
        });

        btn_save_int.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_int_add1 = et_int_add1.getText().toString();
                str_int_add2 = et_int_add2.getText().toString();
                str_int_city = et_int_city.getText().toString();
                str_int_state = aet_int_state.getText().toString();
                str_int_zip = aet_int_zip.getText().toString();
                str_int_phone = et_int_phone.getText().toString();
                str_int_note = et_int_note.getText().toString();
                str_int_country = spin_int.getSelectedItem().toString();

                validate(1, str_int_add1, str_int_add2, str_int_city, str_int_state, str_int_zip, str_int_phone, str_int_note, str_int_country);

            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            if (!(loc_adr || int_adr)) {
                                                //Toast.makeText(getApplicationContext(), "Fill addresses", Toast.LENGTH_LONG).show();

                                                new SweetAlertDialog(ProfilePhysicalDeliveryAddress.this, SweetAlertDialog.WARNING_TYPE)
                                                        .setTitleText("Fill Address")
                                                        .setConfirmText("OK")

                                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialog sDialog) {

                                                                sDialog.dismiss();
                                                            }
                                                        })
                                                        .show();


                                            } else if (!(cb_loc.isChecked() || cb_int.isChecked())) {

                                                // Toast.makeText(getApplicationContext(), "choose default address", Toast.LENGTH_LONG).show();

                                                new SweetAlertDialog(ProfilePhysicalDeliveryAddress.this, SweetAlertDialog.WARNING_TYPE)
                                                        .setTitleText("Choose Default Address")
                                                        .setConfirmText("OK")
                                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialog sDialog) {
                                                                sDialog.dismiss();
                                                            }
                                                        })
                                                        .show();

                                            } else {

                                                if (cb_loc.isChecked()) {
                                                    str_def_adr = "LOCAL";
                                                } else {
                                                    str_def_adr = "INTERNATIONAL";
                                                }

                                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfilePhysicalDeliveryAddress.this);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("default_adr", str_def_adr);
                                                editor.commit();


                                                Log.d("tag", sharedPreferences.getString("loc_addr1", ""));
                                                Log.d("tag", sharedPreferences.getString("loc_addr2", ""));
                                                Log.d("tag", sharedPreferences.getString("loc_city", ""));
                                                Log.d("tag", sharedPreferences.getString("loc_state", ""));
                                                Log.d("tag", sharedPreferences.getString("loc_zip", ""));
                                                Log.d("tag", sharedPreferences.getString("loc_phone", ""));
                                                Log.d("tag", sharedPreferences.getString("loc_note", ""));

                                                Log.d("tag", sharedPreferences.getString("int_addr1", ""));
                                                Log.d("tag", sharedPreferences.getString("int_addr2", ""));
                                                Log.d("tag", sharedPreferences.getString("int_city", ""));
                                                Log.d("tag", sharedPreferences.getString("int_state", ""));
                                                Log.d("tag", sharedPreferences.getString("int_zip", ""));
                                                Log.d("tag", sharedPreferences.getString("int_phone", ""));
                                                Log.d("tag", sharedPreferences.getString("int_note", ""));
                                                Log.d("tag", sharedPreferences.getString("default_adr", ""));
                                                Log.d("tag", sharedPreferences.getString("loc_country", ""));
                                                Log.d("tag", sharedPreferences.getString("int_country", ""));


                                                String virtualaddr = sharedPreferences.getString("virtul_addr", "");

                                                if (virtualaddr.equals("")) {

                                                    editor = sharedPreferences.edit();
                                                    editor.putString("vir_sts", "0");
                                                    editor.commit();

                                                    Intent inte = new Intent(getApplicationContext(), AddLocation.class);
                                                    inte.putExtra("sts", 0);
                                                    startActivity(inte);
                                                } else {
                                                    Intent inte = new Intent(getApplicationContext(), ProfileDashboard.class);
                                                    startActivity(inte);
                                                }

                                            }
                                        }
                                    }


        );


    }


    public void validate(int i, String addr1, String addr2, String city, String state, String zip, String phone, String note, String country)

    {
        String location;
        if (i == 0) {
            location = "loc";
        } else {
            location = "int";
        }


        if (!addr1.isEmpty()) {
            if (!(city.isEmpty())) {
                if (!state.isEmpty()) {
                    if (!zip.isEmpty()) {
                        if (!(phone.isEmpty() || phone.length() < 10)) {
                            if (!note.isEmpty()) {

                                if (i == 0) {
                                    btn_save_loc.setVisibility(View.GONE);
                                    disable_loc();
                                    //Toast.makeText(getApplicationContext(), "Local address Updated", Toast.LENGTH_LONG).show();

                                    new SweetAlertDialog(ProfilePhysicalDeliveryAddress.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Local Address Updated")
                                            .setConfirmText("OK")

                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {

                                                    sDialog.dismiss();
                                                }
                                            })
                                            .show();


                                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfilePhysicalDeliveryAddress.this);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(location + "_addr1", addr1);
                                    editor.putString(location + "_addr2", addr2);
                                    editor.putString(location + "_city", city);
                                    editor.putString(location + "_state", state);
                                    editor.putString(location + "_zip", zip);
                                    editor.putString(location + "_phone", phone);
                                    editor.putString(location + "_note", note);
                                    editor.putString(location + "_country", country);
                                    editor.putBoolean("adrsts0", true);
                                    editor.commit();


                                    loc_adr = true;
                                } else {
                                    btn_save_int.setVisibility(View.GONE);
                                    disable_int();

                                    new SweetAlertDialog(ProfilePhysicalDeliveryAddress.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("International Address Updated")
                                            .setConfirmText("OK")

                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {

                                                    sDialog.dismiss();
                                                }
                                            })
                                            .show();


                                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfilePhysicalDeliveryAddress.this);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(location + "_addr1", addr1);
                                    editor.putString(location + "_addr2", addr2);
                                    editor.putString(location + "_city", city);
                                    editor.putString(location + "_state", state);
                                    editor.putString(location + "_zip", zip);
                                    editor.putString(location + "_phone", phone);
                                    editor.putString(location + "_note", note);
                                    editor.putString(location + "_country", country);
                                    editor.putBoolean("adrsts1", true);
                                    editor.commit();


                                    // Toast.makeText(getApplicationContext(), "International address Updated", Toast.LENGTH_LONG).show();
                                    int_adr = true;
                                }


                                /*SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfilePhysicalDeliveryAddress.this);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(location + "_addr1", addr1);
                                editor.putString(location + "_addr2", addr2);
                                editor.putString(location + "_city", city);
                                editor.putString(location + "_state", state);
                                editor.putString(location + "_zip", zip);
                                editor.putString(location + "_phone", phone);
                                editor.putString(location + "_note", note);
                                editor.putBoolean("adrsts", true);
                                editor.commit();*/




                                 /*   Intent inte = new Intent(getApplicationContext(), ProfilePhysicalDeliveryAddress.class);
                                    startActivity(inte);*/

                            } else {
                                Toast.makeText(getApplicationContext(), "enter delivery note", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "enter Phone Number", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "enter zip", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(getApplicationContext(), "enter state", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "enter city", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "enter address", Toast.LENGTH_LONG).show();
        }
    }


    private void getfromdata_loc() {

        //  disable_loc();
        //  disable_int();

        et_loc_add1.setText(str_loc_add1);
        et_loc_add2.setText(str_loc_add2);
        et_loc_city.setText(str_loc_city);
        aet_loc_state.setText(str_loc_state);
        aet_loc_zip.setText(str_loc_zip);
        et_loc_phone.setText(str_loc_phone);
        et_loc_note.setText(str_loc_note);


    }


    private void getfromdata_int() {

        //  disable_loc();
        //  disable_int();

        et_int_add1.setText(str_int_add1);
        et_int_add2.setText(str_int_add2);
        et_int_city.setText(str_int_city);
        aet_int_state.setText(str_int_state);
        aet_int_zip.setText(str_int_zip);
        et_int_phone.setText(str_int_phone);
        et_int_note.setText(str_int_note);

    }


    public void enable_loc() {

        et_loc_add1.setEnabled(true);
        et_loc_add2.setEnabled(true);
        et_loc_city.setEnabled(true);
        aet_loc_state.setEnabled(true);
        aet_loc_zip.setEnabled(true);
        et_loc_phone.setEnabled(true);
        et_loc_note.setEnabled(true);
        spin_loc.setEnabled(true);

    }

    public void enable_int() {
        et_int_add1.setEnabled(true);
        et_int_add2.setEnabled(true);
        et_int_city.setEnabled(true);
        aet_int_state.setEnabled(true);
        aet_int_zip.setEnabled(true);
        et_int_phone.setEnabled(true);
        et_int_note.setEnabled(true);
        spin_int.setEnabled(true);

    }

    public void disable_loc() {

        et_loc_add1.setEnabled(false);
        et_loc_add2.setEnabled(false);
        et_loc_city.setEnabled(false);
        aet_loc_state.setEnabled(false);
        aet_loc_zip.setEnabled(false);
        et_loc_phone.setEnabled(false);
        et_loc_note.setEnabled(false);
        spin_loc.setEnabled(false);
    }

    public void disable_int() {
        et_int_add1.setEnabled(false);
        et_int_add2.setEnabled(false);
        et_int_city.setEnabled(false);
        aet_int_state.setEnabled(false);
        aet_int_zip.setEnabled(false);
        et_int_phone.setEnabled(false);
        et_int_note.setEnabled(false);
        spin_int.setEnabled(false);
    }


    public class MyAdapter extends ArrayAdapter<String> {

        public MyAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }


        public View getCustomView(int position, View row, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            row = inflater.inflate(R.layout.dropdown_lists1, parent, false);

            TextView label = (TextView) row.findViewById(R.id.text_spin);

            label.setTypeface(tf);

            label.setText(countries[position]);


            return row;
        }
    }

    class GetCountry extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            sweetAlertDialog = new SweetAlertDialog(ProfilePhysicalDeliveryAddress.this, SweetAlertDialog.PROGRESS_TYPE);
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
                    new SweetAlertDialog(ProfilePhysicalDeliveryAddress.this, SweetAlertDialog.WARNING_TYPE)
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
                adapter_a = new MyAdapter(ProfilePhysicalDeliveryAddress.this, R.layout.dropdown_lists1, country);
                spin_loc.setAdapter(adapter_a);

                adapter_b = new MyAdapter(ProfilePhysicalDeliveryAddress.this, R.layout.dropdown_lists1, country);
                spin_int.setAdapter(adapter_b);


            } catch (JSONException e) {
                e.printStackTrace();
                new SweetAlertDialog(ProfilePhysicalDeliveryAddress.this, SweetAlertDialog.WARNING_TYPE)
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
        String get_cont;
        int c_sts;

        GetState(String cont, int sts) {
            this.get_cont = cont;
            this.c_sts = sts;
        }

        protected void onPreExecute() {
            sweetAlertDialog = new SweetAlertDialog(ProfilePhysicalDeliveryAddress.this, SweetAlertDialog.PROGRESS_TYPE);
            sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            sweetAlertDialog.setTitleText("Loading");
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.show();
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {
                Log.d("tag", "" + get_cont);
                String state_url = Config.SER_URL + "region/state";
                JSONObject jsonobject = HttpUtils.getData2(state_url, get_cont);
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
                    String status = jo.getString("state");
                    String msg = jo.getString("message");
                    Log.d("tag", "<-----Statasdfus----->" + status);
                    if (status.equals("null")) {
                        Log.d("tag", "<--> state not available for this country");
                    } else {
                        states.clear();
                        JSONObject jaa = new JSONObject(jsonStr);
                        JSONArray jj = jaa.getJSONArray("state");
                        Log.d("tag", "<-----S---->" + jj);
                        for (int i1 = 0; i1 < jj.length(); i1++) {
                            String daa = jj.getString(i1);
                            states.add(daa);
                            Log.d("tag", "<-----Statusss----->" + states.get(i1));
                        }
                        Log.d("tag", "" + c_sts);
                        if (c_sts == 0) {

                            adpater_states = new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_lists6, R.id.text_spin, states);
                            aet_loc_state.setAdapter(adpater_states);
                           /* adapter_c = new MyAdapter(ProfilePhysicalDeliveryAddress.this, R.layout.dropdown_lists1, states);
                            aet_loc_state.setAdapter(adapter_c);
                            aet_loc_state.requestFocus();*/
                        } else {
                            adpater_states_ = new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_lists6, R.id.text_spin, states);
                            aet_int_state.setAdapter(adpater_states_);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    new SweetAlertDialog(ProfilePhysicalDeliveryAddress.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops!")
                            .setContentText("No state associated with given country \n try again")
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

        String get_cont, get_state;
        int get_sts;

        public GetZip(String cont, String state, int sts) {
            this.get_cont = cont;
            this.get_state = state;
            this.get_sts = sts;
        }


        protected void onPreExecute() {

            sweetAlertDialog = new SweetAlertDialog(ProfilePhysicalDeliveryAddress.this, SweetAlertDialog.PROGRESS_TYPE);
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
                JSONObject jsonobject = HttpUtils.getData3(zip_url, get_cont, get_state);
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
                    zip.clear();

                    //JSONArray ja = jo.getJSONArray(status);

                    for (int i1 = 0; i1 < jj.length(); i1++) {

                        // JSONObject data = jj.getJSONObject(i1);

                        String daa = jj.getString(i1);

                        //countries[jj.length()] =
                        zip.add(daa);
                        Log.d("tag", "<-----Statusss----->" + daa);
                        //adapter2.notifyDataSetChanged();
                    }

                    Log.d("tag", "<---->" + "" + get_sts);
                    if (get_sts == 0) {

                        adapter_zips = new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_lists6, R.id.text_spin, zip);
                        aet_loc_zip.setAdapter(adapter_zips);
                    } else {
                        adapter_zips_ = new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_lists6, R.id.text_spin, zip);
                        aet_int_zip.setAdapter(adapter_zips_);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                    new SweetAlertDialog(ProfilePhysicalDeliveryAddress.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops!")
                            .setContentText("No zips associated with given state \n try again")
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


}

