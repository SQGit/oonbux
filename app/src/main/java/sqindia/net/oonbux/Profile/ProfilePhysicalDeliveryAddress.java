package sqindia.net.oonbux.Profile;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.Button;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.EditText;
import com.rey.material.widget.Spinner;
import com.rey.material.widget.TextView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import sqindia.net.oonbux.Activity.AddLocation;
import sqindia.net.oonbux.Dialog.Dialog_Anim_Loading;
import sqindia.net.oonbux.config.Config;
import sqindia.net.oonbux.config.DbC;
import sqindia.net.oonbux.Dialog.Dialog_new;
import sqindia.net.oonbux.config.HttpUtils;
import sqindia.net.oonbux.Adapter.ListAdapter_Class;
import sqindia.net.oonbux.Activity.ManageAddress;
import sqindia.net.oonbux.R;


public class ProfilePhysicalDeliveryAddress extends Activity {

    LinearLayout bottom_lt;
    TextView tv_header, tv_sub_hdr_loc, tv_sub_hdr_int;
    Button btn_save_loc, btn_save_int, btn_next, btn_edit_loc, btn_edit_int;
    ImageView lt_back,lt_add,loc_map,int_map;
    MaterialEditText et_loc_add1, et_loc_add2, et_loc_city, et_loc_phone, et_loc_note, et_int_add1, et_int_add2, et_int_city, et_int_phone, et_int_note;
    String get_profile_sts, str_def_adr, str_loc_add1, str_loc_add2, str_loc_city, str_loc_zip, str_loc_phone,
            str_loc_note, str_int_add1, str_int_add2, str_int_city, str_int_zip, str_int_phone, str_int_note, str_session_id;
    CheckBox cb_loc, cb_int;
    boolean loc_adr, int_adr;
    Spinner spin_loc_country, spin_int_country, spin_loc_state, spin_int_state, spin_loc_zip, spin_int_zip;
    String[] countries;
    Typeface tf, tf1;
    MyAdapter adapter_a, adapter_b, adapter_c;
    ImageView img_loc_country, img_loc_state, img_loc_zip,img_int_country, img_int_state, img_int_zip;

    int i = 0;
    String str_country, str_state, cont, stat, zp;
    ArrayList<String> country = new ArrayList<>();
    ArrayList<String> states = new ArrayList<>();
    ArrayList<String> zip = new ArrayList<>();

    ListAdapter_Class adapter_loc_country, adapter_int_country, adapter_loc_state, adapter_int_state, adapter_loc_zip, adapter_int_zip;

    ///MaterialAutoCompleteTextView  aet_loc_zip,  aet_int_zip;

    String str_loc_country, str_loc_state, str_int_country, str_int_state;

    ArrayAdapter<String> adpater_states, adpater_states_, adapter_zips, adapter_zips_, adpater_states1, adapter_zips1;
    DbC dbclass;
    Context context = this;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    private SQLiteDatabase db;
  //  Dialog loading_dialog;

    Dialog_Anim_Loading dialog_loading;





    @Override
    public void onBackPressed() {
        // super.onBackPressed();


        if ((get_profile_sts.equals(""))) {
                  /*  Intent inte = new Intent(getApplicationContext(), ProfileInfo.class);
                    startActivity(inte);*/

            if(sharedPreferences.getString("from_profile","") != ""){
                super.onBackPressed();
                finish();
            }
            else {


                Intent intes = new Intent(getApplicationContext(), ProfileInfo.class);
                startActivity(intes);
                finish();


                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfilePhysicalDeliveryAddress.this);
                editor = sharedPreferences.edit();
                editor.putString("profile", "");
                editor.commit();

            }
            // Toast.makeText(getApplicationContext(), "Please complete your profile", Toast.LENGTH_LONG).show();
        } else {
            /*Intent inte = new Intent(getApplicationContext(), ProfileDashboard.class);
            startActivity(inte);*/
            super.onBackPressed();
            finish();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_delivery_address);

        tf = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");
        tf1 = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfilePhysicalDeliveryAddress.this);
        get_profile_sts = sharedPreferences.getString("profile", "");
        str_session_id = sharedPreferences.getString("sessionid", "");
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
        et_loc_phone = (MaterialEditText) findViewById(R.id.edittext_loc_phone);
        et_loc_note = (MaterialEditText) findViewById(R.id.edittext_loc_note);
        et_int_add1 = (MaterialEditText) findViewById(R.id.edittext_int_address1);
        et_int_add2 = (MaterialEditText) findViewById(R.id.edittext_int_address2);
        et_int_city = (MaterialEditText) findViewById(R.id.edittext_int_city);
        et_int_phone = (MaterialEditText) findViewById(R.id.edittext_int_phone);
        et_int_note = (MaterialEditText) findViewById(R.id.edittext_int_note);
        lt_back = (ImageView) findViewById(R.id.layout_back);
        bottom_lt = (LinearLayout) findViewById(R.id.bottom);
        cb_loc = (CheckBox) findViewById(R.id.checkbox_local);
        cb_int = (CheckBox) findViewById(R.id.checkbox_int);
        spin_loc_country = (Spinner) findViewById(R.id.loc_spin_country);
        spin_int_country = (Spinner) findViewById(R.id.int_spin_country);
        spin_loc_state = (Spinner) findViewById(R.id.loc_spin_states);
        spin_int_state = (Spinner) findViewById(R.id.int_spin_states);
        spin_loc_zip = (Spinner) findViewById(R.id.loc_spin_zip);
        spin_int_zip = (Spinner) findViewById(R.id.int_spin_zip);
        lt_add = (ImageView) findViewById(R.id.layout_add);
        img_loc_country = (ImageView) findViewById(R.id.imageView_loc_country);
        img_loc_state = (ImageView) findViewById(R.id.imageView_loc_state);
        img_loc_zip = (ImageView) findViewById(R.id.imageView_loc_zip);
        img_int_country = (ImageView) findViewById(R.id.imageView_int_country);
        img_int_state = (ImageView) findViewById(R.id.imageView_int_state);
        img_int_zip = (ImageView) findViewById(R.id.imageView_int_zip);

        loc_map = (ImageView) findViewById(R.id.imageViedw);
        int_map = (ImageView) findViewById(R.id.image_view2);


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
        et_loc_phone.setTypeface(tf1);
        et_loc_note.setTypeface(tf1);

        et_int_add1.setTypeface(tf1);
        et_int_add2.setTypeface(tf1);
        et_int_city.setTypeface(tf1);
        et_int_phone.setTypeface(tf1);
        et_int_note.setTypeface(tf1);


        dbclass = new DbC(context);
        createDatabase();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if ((sharedPreferences.getString("profile", "").equals(""))) {

            bottom_lt.setVisibility(View.VISIBLE);
            lt_add.setVisibility(View.GONE);
            btn_next.setText("Next");

        } else {
            bottom_lt.setVisibility(View.GONE);
            lt_add.setVisibility(View.VISIBLE);
            btn_next.setText("Update");
        }



        if (sharedPreferences.getString("loc_address","").equals("success")) {

            Log.e("tag", "true");
            loc_adr = true;
            disable_loc();
            get_loc_from_Db();
            btn_save_loc.setVisibility(View.GONE);
            btn_edit_loc.setVisibility(View.VISIBLE);
            getfromdata_loc();

        } else {

            Log.e("tag", "false");
            loc_adr = false;
            enable_loc();
            get_loc_from_Db();
            btn_save_loc.setVisibility(View.VISIBLE);
            btn_edit_loc.setVisibility(View.GONE);
            img_loc_country.setVisibility(View.VISIBLE);
            img_loc_state.setVisibility(View.VISIBLE);
            img_loc_zip.setVisibility(View.VISIBLE);

        }



        if (sharedPreferences.getString("int_address","").equals("success")) {
            Log.e("tag", "insdie_int");

            int_adr = true;
            disable_int();
            get_intl_from_Db();
            btn_save_int.setVisibility(View.GONE);
            btn_edit_int.setVisibility(View.VISIBLE);
            getfromdata_int();

        } else {
            Log.e("tag", "outsid_int");

            int_adr = false;
            enable_int();
            btn_save_int.setVisibility(View.VISIBLE);
            btn_edit_int.setVisibility(View.GONE);
            get_intl_from_Db();
            img_int_country.setVisibility(View.VISIBLE);
            img_int_state.setVisibility(View.VISIBLE);
            img_int_zip.setVisibility(View.VISIBLE);

        }


        if (str_def_adr.equals("LOCAL")) {
            cb_loc.setChecked(true);
        }
        else if (str_def_adr.equals("INTERNATIONAL")) {
            cb_int.setChecked(true);
        }





        spin_loc_country.setOnItemClickListener(new Spinner.OnItemClickListener() {
            @Override
            public boolean onItemClick(Spinner parent, View view, int position, long id) {

                spin_loc_country.setSelection(position);

                str_loc_country = spin_loc_country.getSelectedItem().toString();
                get_LocState_from_Db();
                Log.e("tag", "itemclick_loc");


                editor = sharedPreferences.edit();
                editor.putString("loc_country", str_loc_country);
                editor.commit();

                return false;
            }
        });

        spin_loc_state.setOnItemClickListener(new Spinner.OnItemClickListener() {
            @Override
            public boolean onItemClick(Spinner parent, View view, int position, long id) {

                spin_loc_state.setSelection(position);

                str_loc_state = spin_loc_state.getSelectedItem().toString();


                get_LocZip_from_Db();

                editor = sharedPreferences.edit();
                editor.putString("loc_state", str_loc_state);
                editor.commit();

                return false;
            }
        });

        spin_loc_zip.setOnItemClickListener(new Spinner.OnItemClickListener() {
            @Override
            public boolean onItemClick(Spinner parent, View view, int position, long id) {

                spin_loc_zip.setSelection(position);

                str_loc_country = spin_loc_country.getSelectedItem().toString();
                str_loc_state = spin_loc_state.getSelectedItem().toString();
                str_loc_zip = spin_loc_zip.getSelectedItem().toString();

                Log.e("tag", "" + str_loc_country + str_loc_state + str_loc_zip);
                dbclass.region_update(str_loc_country, str_loc_state, str_loc_zip);

                editor = sharedPreferences.edit();
                editor.putString("loc_zip", str_loc_zip);
                editor.commit();


                new getCity("35235",0).execute();

                return false;
            }
        });


        spin_int_country.setOnItemClickListener(new Spinner.OnItemClickListener() {
            @Override
            public boolean onItemClick(Spinner parent, View view, int position, long id) {

                spin_int_country.setSelection(position);

                str_int_country = spin_int_country.getSelectedItem().toString();
                get_intl_State_from_Db();
                Log.e("tag", "itemclick_int");
                editor = sharedPreferences.edit();
                editor.putString("int_country", str_int_country);
                editor.commit();

                return false;
            }
        });


        spin_int_state.setOnItemClickListener(new Spinner.OnItemClickListener() {
            @Override
            public boolean onItemClick(Spinner parent, View view, int position, long id) {

                spin_int_state.setSelection(position);

                str_int_state = spin_int_state.getSelectedItem().toString();

                get_IntlZip_from_Db();

                editor = sharedPreferences.edit();
                editor.putString("int_state", str_int_state);
                editor.commit();

                return false;
            }
        });

        spin_int_zip.setOnItemClickListener(new Spinner.OnItemClickListener() {
            @Override
            public boolean onItemClick(Spinner parent, View view, int position, long id) {

                spin_int_zip.setSelection(position);

                str_int_country = spin_int_country.getSelectedItem().toString();
                str_int_state = spin_int_state.getSelectedItem().toString();
                str_int_zip = spin_int_zip.getSelectedItem().toString();

                dbclass.region_update(str_int_country, str_int_state, str_int_zip);

                editor = sharedPreferences.edit();
                editor.putString("int_zip", str_int_zip);
                editor.commit();

                new getCity("96035",1).execute();

                return false;
            }
        });


        lt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addressPart = new Intent(getApplicationContext(), ManageAddress.class);




                ActivityOptions options = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    options = ActivityOptions.makeScaleUpAnimation(lt_add, 0,
                            0, lt_add.getWidth(),
                            lt_add.getHeight());
                    startActivity(addressPart, options.toBundle());
                } else {
                    addressPart.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(addressPart);
                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                }

            }
        });




        cb_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!loc_adr) {
                    cb_loc.setChecked(false);
                    Toast.makeText(getApplicationContext(), "Save Local Address.", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getApplicationContext(), "Save International Address.", Toast.LENGTH_LONG).show();
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



                    if(sharedPreferences.getString("from_profile","") != ""){
                       onBackPressed();
                        finish();
                    }

                    else {


                        Intent intes = new Intent(getApplicationContext(), ProfileInfo.class);


                        ActivityOptions options = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            options = ActivityOptions.makeScaleUpAnimation(lt_back, 0,
                                    0, lt_back.getWidth(),
                                    lt_back.getHeight());
                            startActivity(intes, options.toBundle());
                        } else {
                            intes.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                            startActivity(intes);
                            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                        }
                        finish();


                        editor = sharedPreferences.edit();
                        editor.putString("profile", "");
                        editor.commit();
                    }

                } else {
                    Intent goto_profile = new Intent(getApplicationContext(), ProfileDashboard.class);

                    ActivityOptions options = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        options = ActivityOptions.makeScaleUpAnimation(lt_back, 0,
                                0, lt_back.getWidth(),
                                lt_back.getHeight());
                        startActivity(goto_profile, options.toBundle());
                    } else {
                        goto_profile.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(goto_profile);
                        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    }
                    finish();
                }

            }
        });


        btn_edit_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ((get_profile_sts.equals(""))) {
                    loc_adr = false;
                    btn_save_loc.setVisibility(View.VISIBLE);
                    enable_loc();

                    bottom_lt.setVisibility(View.VISIBLE);

                    et_loc_add1.requestFocus();

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfilePhysicalDeliveryAddress.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("profile", "");
                    editor.commit();


                } else {
                    btn_edit_loc.setVisibility(View.GONE);
                    img_loc_country.setVisibility(View.VISIBLE);
                    img_loc_state.setVisibility(View.VISIBLE);
                    img_loc_zip.setVisibility(View.VISIBLE);



                    loc_adr = false;
                    btn_save_loc.setVisibility(View.VISIBLE);
                    enable_loc();
                    bottom_lt.setVisibility(View.VISIBLE);
                    et_loc_add1.requestFocus();

                    /*SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfilePhysicalDeliveryAddress.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("loc_address", "");
                    editor.commit();*/
                    btn_next.setText("Update");
                }

            }
        });


        btn_edit_int.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ((get_profile_sts.equals(""))) {

                    int_adr = false;
                    btn_save_int.setVisibility(View.VISIBLE);
                    enable_int();

                    bottom_lt.setVisibility(View.VISIBLE);

                    et_int_add1.requestFocus();

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfilePhysicalDeliveryAddress.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("profile", "");
                    editor.commit();

                } else {
                    btn_edit_int.setVisibility(View.GONE);
                    img_int_country.setVisibility(View.VISIBLE);
                    img_int_state.setVisibility(View.VISIBLE);
                    img_int_zip.setVisibility(View.VISIBLE);

                    int_adr = false;
                    btn_save_int.setVisibility(View.VISIBLE);
                    enable_int();

                    bottom_lt.setVisibility(View.VISIBLE);

                    et_int_add1.requestFocus();

                    /*SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfilePhysicalDeliveryAddress.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("int_address", "");
                    editor.commit();*/
                    btn_next.setText("Update");

                }


            }
        });


        btn_save_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_loc_add1 = et_loc_add1.getText().toString();
                str_loc_add2 = et_loc_add2.getText().toString();
                str_loc_city = et_loc_city.getText().toString();
                str_loc_state = spin_loc_state.getSelectedItem().toString();         //aet_loc_state.getText().toString();
                str_loc_zip = spin_loc_zip.getSelectedItem().toString();
                str_loc_phone = et_loc_phone.getText().toString();
                str_loc_note = et_loc_note.getText().toString();
                str_loc_country = spin_loc_country.getSelectedItem().toString();




                validate(0, str_loc_add1, str_loc_add2, str_loc_city, str_loc_state, str_loc_zip, str_loc_phone, str_loc_note, str_loc_country);


            }
        });

        btn_save_int.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_int_add1 = et_int_add1.getText().toString();
                str_int_add2 = et_int_add2.getText().toString();
                str_int_city = et_int_city.getText().toString();
                str_int_state = spin_int_state.getSelectedItem().toString();
                str_int_zip = spin_int_zip.getSelectedItem().toString();
                str_int_phone = et_int_phone.getText().toString();
                str_int_note = et_int_note.getText().toString();
                str_int_country = spin_int_country.getSelectedItem().toString();

                validate(1, str_int_add1, str_int_add2, str_int_city, str_int_state, str_int_zip, str_int_phone, str_int_note, str_int_country);

            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {


                                            if ((get_profile_sts.equals(""))) {


                                                if (!(loc_adr || int_adr)) {
                                                    Toast.makeText(getApplicationContext(), "Save Address.", Toast.LENGTH_LONG).show();

                                                } else if (!(cb_loc.isChecked() || cb_int.isChecked())) {
                                                    Toast.makeText(getApplicationContext(), "Choose Default Address.", Toast.LENGTH_LONG).show();

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

                                               /* if (virtualaddr.equals("")) {

                                                    editor = sharedPreferences.edit();
                                                    editor.putString("vir_sts", "0");
                                                    editor.commit();

                                                    Intent inte = new Intent(getApplicationContext(), AddLocation.class);
                                                    inte.putExtra("sts", 0);
                                                    startActivity(inte);
                                                } */

                                                    if (sharedPreferences.getString("vir_sts", "").equals("")) {

                                                        Intent inte = new Intent(getApplicationContext(), AddLocation.class);
                                                        inte.putExtra("sts", 0);


                                                        ActivityOptions options = null;
                                                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                                            options = ActivityOptions.makeScaleUpAnimation(btn_next, 0,
                                                                    0, btn_next.getWidth(),
                                                                    btn_next.getHeight());
                                                            startActivity(inte, options.toBundle());
                                                        } else {
                                                            inte.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                                            startActivity(inte);
                                                            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                                                        }
                                                        finish();



                                                    } else {
                                                        Intent inte = new Intent(getApplicationContext(), ProfileDashboard.class);

                                                        ActivityOptions options = null;
                                                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                                                            options = ActivityOptions.makeScaleUpAnimation(btn_next, 0,
                                                                    0, btn_next.getWidth(),
                                                                    btn_next.getHeight());
                                                            startActivity(inte, options.toBundle());
                                                        } else {
                                                            inte.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                                            startActivity(inte);
                                                            overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                                                        }
                                                        finish();
                                                    }

                                                }


                                            }

                                            else {


                                                if (!(loc_adr || int_adr)) {
                                                    Toast.makeText(getApplicationContext(), "Save  Address.", Toast.LENGTH_LONG).show();
                                                } else if (!(cb_loc.isChecked() || cb_int.isChecked())) {
                                                    Toast.makeText(getApplicationContext(), "Choose Default Address.", Toast.LENGTH_LONG).show();
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


                                                    new Profile_Update_Task().execute();


                                                }


                                            }


                                        }
                                    }


        );



        setupUI(findViewById(R.id.top));



    }






    public void setupUI(View view) {

        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(ProfilePhysicalDeliveryAddress.this);
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






    private void get_loc_from_Db() {

        country.clear();

        Cursor cont_cursor = dbclass.getCountry();
        if (cont_cursor != null) {
            if (cont_cursor.moveToFirst()) {
                do {

                    String cont = cont_cursor.getString(cont_cursor.getColumnIndex("country"));
                    country.add(cont);

                    adapter_loc_country = new ListAdapter_Class(getApplicationContext(), R.layout.dropdown_lists1, country);
                    spin_loc_country.setAdapter(adapter_loc_country);

                } while (cont_cursor.moveToNext());
            }
        }


        if (sharedPreferences.getString("loc_address","").equals("success")) {
            str_loc_country = sharedPreferences.getString("loc_country", "");
            int spinnerPosition = adapter_loc_country.getPosition(str_loc_country);
            spin_loc_country.setSelection(spinnerPosition);
            get_LocState_from_Db();
        }
        else {
            get_LocState_from_Db();
        }

    }

    private void get_intl_from_Db() {

        country.clear();

        Cursor cont_cursor = dbclass.getCountry();
        if (cont_cursor != null) {
            if (cont_cursor.moveToFirst()) {
                do {
                    String cont = cont_cursor.getString(cont_cursor.getColumnIndex("country"));
                    country.add(cont);
                    adapter_int_country = new ListAdapter_Class(getApplicationContext(), R.layout.dropdown_lists1, country);
                    spin_int_country.setAdapter(adapter_int_country);
                } while (cont_cursor.moveToNext());
            }
        }

        if (sharedPreferences.getString("int_address","").equals("success")) {
            str_int_country = sharedPreferences.getString("int_country", "");
            int spinnerPosition = adapter_int_country.getPosition(str_int_country);
            spin_int_country.setSelection(spinnerPosition);
            get_intl_State_from_Db();
        }
        else {
            get_intl_State_from_Db();
        }
    }

    private void get_LocState_from_Db() {
        states.clear();
        str_loc_country = spin_loc_country.getSelectedItem().toString();
        Cursor cont_cursor = dbclass.getState(str_loc_country);
        if (cont_cursor != null) {
            if (cont_cursor.moveToFirst()) {
                do {
                    String stat = cont_cursor.getString(cont_cursor.getColumnIndex("state"));
                    states.add(stat);
                } while (cont_cursor.moveToNext());
            }
        }

        adapter_loc_state = new ListAdapter_Class(getApplicationContext(), R.layout.dropdown_lists1, states);
        spin_loc_state.setAdapter(adapter_loc_state);

        if (sharedPreferences.getString("loc_address","").equals("success")) {
            str_loc_state = sharedPreferences.getString("loc_state", "");
            int stateposition = adapter_loc_state.getPosition(str_loc_state);
            spin_loc_state.setSelection(stateposition);
            get_LocZip_from_Db();
        }
        else {
            get_LocZip_from_Db();
        }
    }

    private void get_intl_State_from_Db() {
        states.clear();
        str_int_country = spin_int_country.getSelectedItem().toString();
        Cursor cont_cursor = dbclass.getState(str_int_country);
        if (cont_cursor != null) {
            if (cont_cursor.moveToFirst()) {
                do {
                    String stat = cont_cursor.getString(cont_cursor.getColumnIndex("state"));
                    states.add(stat);
                } while (cont_cursor.moveToNext());
            }
        }

        adapter_int_state = new ListAdapter_Class(getApplicationContext(), R.layout.dropdown_lists1, states);
        spin_int_state.setAdapter(adapter_int_state);

        if (sharedPreferences.getString("int_address","").equals("success")) {
            str_int_state = sharedPreferences.getString("int_state", "");
            int stateposition = adapter_int_state.getPosition(str_int_state);
            spin_int_state.setSelection(stateposition);
            get_IntlZip_from_Db();
        }
        else {
            get_IntlZip_from_Db();
        }
    }

    public void get_LocZip_from_Db(){

        zip.clear();
        str_loc_country = spin_loc_country.getSelectedItem().toString();
        str_loc_state = spin_loc_state.getSelectedItem().toString();
        Cursor cont_cursor = dbclass.getZip(str_loc_country,str_loc_state);
        if (cont_cursor != null) {
            if (cont_cursor.moveToFirst()) {
                do {
                    String zips = cont_cursor.getString(cont_cursor.getColumnIndex("zip"));
                    zip.add(zips);
                } while (cont_cursor.moveToNext());
            }
        }

        adapter_loc_zip = new ListAdapter_Class(getApplicationContext(), R.layout.dropdown_lists1, zip);
        spin_loc_zip.setAdapter(adapter_loc_zip);

        if (sharedPreferences.getString("loc_address","").equals("success")) {
            str_loc_zip = sharedPreferences.getString("loc_state", "");
            int stateposition = adapter_loc_zip.getPosition(str_loc_zip);
            spin_loc_zip.setSelection(stateposition);
        }
    }

    public void get_IntlZip_from_Db(){

        zip.clear();
        str_int_country = spin_int_country.getSelectedItem().toString();
        str_int_state = spin_int_state.getSelectedItem().toString();
        Cursor cont_cursor = dbclass.getZip(str_int_country,str_int_state);
        if (cont_cursor != null) {
            if (cont_cursor.moveToFirst()) {
                do {
                    String zips = cont_cursor.getString(cont_cursor.getColumnIndex("zip"));
                    zip.add(zips);
                } while (cont_cursor.moveToNext());
            }
        }

        adapter_int_zip = new ListAdapter_Class(getApplicationContext(), R.layout.dropdown_lists1, zip);
        spin_int_zip.setAdapter(adapter_int_zip);

        if (sharedPreferences.getString("int_address","").equals("success")) {
            str_int_zip = sharedPreferences.getString("int_state", "");
            int stateposition = adapter_int_zip.getPosition(str_int_zip);
            spin_int_zip.setSelection(stateposition);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        db.close();
    }

    protected void createDatabase() {

        Log.e("tag", "createdb");
        db = openOrCreateDatabase("oonbux", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS physical(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, addr_line1 VARCHAR, addr_line2 VARCHAR, city VARCHAR, state VARCHAR, zip VARCHAR, phone VARCHAR, country VARCHAR, note VARCHAR, loc VARCHAR );");
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
                                    img_loc_country.setVisibility(View.GONE);
                                    img_loc_state.setVisibility(View.GONE);
                                    img_loc_zip.setVisibility(View.GONE);
                                    btn_edit_loc.setVisibility(View.VISIBLE);

                                    disable_loc();
                                    Toast.makeText(getApplicationContext(), "Local address Updated.", Toast.LENGTH_LONG).show();


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
                                    editor.putString("loc_address", "success");
                                    editor.commit();

                                    if(str_loc_zip != ""){
                                        Picasso.with(getApplicationContext())
                                                .load("https://maps.googleapis.com/maps/api/staticmap?center=02817&zoom=14&scale=2&size=200x200")
                                                .fit()
                                                .into(loc_map);
                                    }




                                    if ((get_profile_sts.equals(""))) {
                                        dbclass.physical_insert(addr1, addr2, city, state, zip, phone, country, note, String.valueOf(i));
                                    } else {
                                        dbclass.physical_update(addr1, addr2, city, state, zip, phone, country, note, String.valueOf(i));
                                    }


                                    loc_adr = true;
                                } else {

                                    btn_save_int.setVisibility(View.GONE);
                                    img_int_country.setVisibility(View.GONE);
                                    img_int_state.setVisibility(View.GONE);
                                    img_int_zip.setVisibility(View.GONE);
                                    btn_edit_int.setVisibility(View.VISIBLE);
                                    disable_int();
                                    Toast.makeText(getApplicationContext(), "International address Updated.", Toast.LENGTH_LONG).show();

                                  /*  new SweetAlertDialog(ProfilePhysicalDeliveryAddress.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("International Address Updated")
                                            .setConfirmText("OK")

                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {

                                                    sDialog.dismiss();
                                                }
                                            })
                                            .show();*/


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
                                    editor.putString("int_address", "success");
                                    editor.putBoolean("adrsts1", true);
                                    editor.commit();


                                    if(str_int_zip != ""){
                                        Picasso.with(getApplicationContext())
                                                .load("https://maps.googleapis.com/maps/api/staticmap?center=98424&zoom=14&scale=2&size=200x200")
                                                .fit()
                                                .into(int_map);
                                    }




                                    if ((get_profile_sts.equals(""))) {
                                        dbclass.physical_insert(addr1, addr2, city, state, zip, phone, country, note, String.valueOf(i));
                                    } else {
                                        dbclass.physical_update(addr1, addr2, city, state, zip, phone, country, note, String.valueOf(i));
                                    }

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

        et_loc_add1.setText(str_loc_add1);
        et_loc_add2.setText(str_loc_add2);
        et_loc_city.setText(str_loc_city);
        et_loc_phone.setText(str_loc_phone);
        et_loc_note.setText(str_loc_note);

        img_loc_country.setVisibility(View.GONE);
        img_loc_state.setVisibility(View.GONE);
        img_loc_zip.setVisibility(View.GONE);

    }


    private void getfromdata_int() {

        et_int_add1.setText(str_int_add1);
        et_int_add2.setText(str_int_add2);
        et_int_city.setText(str_int_city);
        et_int_phone.setText(str_int_phone);
        et_int_note.setText(str_int_note);
        img_int_country.setVisibility(View.GONE);
        img_int_state.setVisibility(View.GONE);
        img_int_zip.setVisibility(View.GONE);
    }


    public void enable_loc() {

        et_loc_add1.setEnabled(true);
        et_loc_add2.setEnabled(true);
        et_loc_city.setEnabled(true);
        et_loc_phone.setEnabled(true);
        et_loc_note.setEnabled(true);
        spin_loc_country.setEnabled(true);
        spin_loc_state.setEnabled(true);
        spin_loc_zip.setEnabled(true);

    }

    public void enable_int() {
        et_int_add1.setEnabled(true);
        et_int_add2.setEnabled(true);
        et_int_city.setEnabled(true);
        et_int_phone.setEnabled(true);
        et_int_note.setEnabled(true);
        spin_int_country.setEnabled(true);
        spin_int_state.setEnabled(true);
        spin_int_zip.setEnabled(true);

    }

    public void disable_loc() {

        et_loc_add1.setEnabled(false);
        et_loc_add2.setEnabled(false);
        et_loc_city.setEnabled(false);
        et_loc_phone.setEnabled(false);
        et_loc_note.setEnabled(false);
        spin_loc_country.setEnabled(false);
        spin_loc_state.setEnabled(false);
        spin_loc_zip.setEnabled(false);

        if(str_loc_zip != ""){
            Picasso.with(getApplicationContext())
                    .load("https://maps.googleapis.com/maps/api/staticmap?center=78582&zoom=14&scale=2&size=200x200")
                    .fit()
                    .into(loc_map);
        }


    }

    public void disable_int() {
        et_int_add1.setEnabled(false);
        et_int_add2.setEnabled(false);
        et_int_city.setEnabled(false);
        et_int_phone.setEnabled(false);
        et_int_note.setEnabled(false);
        spin_int_country.setEnabled(false);
        spin_int_state.setEnabled(false);
        spin_int_zip.setEnabled(false);


        if(str_int_zip != ""){
            Picasso.with(getApplicationContext())
                    .load("https://maps.googleapis.com/maps/api/staticmap?center=98424&zoom=14&scale=2&size=200x200")
                    .fit()
                    .into(int_map);
        }

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


    class Profile_Update_Task extends AsyncTask<String, Void, String> {

        //SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfilePhysicalDeliveryAddress.this);

        protected void onPreExecute() {
            super.onPreExecute();


            dialog_loading = new Dialog_Anim_Loading(ProfilePhysicalDeliveryAddress.this);
            dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
            dialog_loading.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog_loading.setCancelable(false);
            dialog_loading.show();
            WindowManager.LayoutParams lp = dialog_loading.getWindow().getAttributes();
            lp.dimAmount=1.80f;
            dialog_loading.getWindow().setAttributes(lp);
            dialog_loading.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";

            try {

                JSONObject jsonObject = new JSONObject();


                jsonObject.accumulate("loc_addr_line1", sharedPreferences.getString("loc_addr1", ""));
                jsonObject.accumulate("loc_addr_line2", sharedPreferences.getString("loc_addr2", ""));
                jsonObject.accumulate("loc_addr_city", sharedPreferences.getString("loc_city", ""));
                jsonObject.accumulate("loc_addr_state", sharedPreferences.getString("loc_state", ""));
                jsonObject.accumulate("loc_addr_zip", sharedPreferences.getString("loc_zip", ""));
                jsonObject.accumulate("loc_phone", sharedPreferences.getString("loc_phone", ""));
                jsonObject.accumulate("loc_delivery_note", sharedPreferences.getString("loc_note", ""));
                jsonObject.accumulate("loc_addr_country", sharedPreferences.getString("loc_country", ""));

                jsonObject.accumulate("int_addr_line1", sharedPreferences.getString("int_addr1", ""));
                jsonObject.accumulate("int_addr_line2", sharedPreferences.getString("int_addr2", ""));
                jsonObject.accumulate("int_addr_city", sharedPreferences.getString("int_city", ""));
                jsonObject.accumulate("int_addr_state", sharedPreferences.getString("int_state", ""));
                jsonObject.accumulate("int_addr_zip", sharedPreferences.getString("int_zip", ""));
                jsonObject.accumulate("int_phone", sharedPreferences.getString("int_phone", ""));
                jsonObject.accumulate("int_delivery_note", sharedPreferences.getString("int_note", ""));
                jsonObject.accumulate("int_addr_country", sharedPreferences.getString("int_country", ""));

                // 4. convert JSONObject to JSON to String
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest2(Config.SER_URL + "profileupdate", json, str_session_id);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("tag", "<-----rerseres---->" + s);
            super.onPostExecute(s);

            dialog_loading.dismiss();

            try {
                JSONObject jo = new JSONObject(s);

                String status = jo.getString("status");

                String msg = jo.getString("message");
                Log.d("tag", "<-----Status----->" + status);


                if (status.equals("success")) {

                    btn_next.setVisibility(View.GONE);


                    Dialog_new cdd = new Dialog_new(ProfilePhysicalDeliveryAddress.this, "Profile Updated Successfully", 7);
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    cdd.show();


                } else {

                    Dialog_new cdd = new Dialog_new(ProfilePhysicalDeliveryAddress.this, "Profile not uploaded \nTry Again Later.", 8);
                    cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    cdd.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    cdd.show();

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }




    class getCity extends AsyncTask<String, Void, String> {
        String zip_code;
        int stat;

        String web_p1 ="http://maps.googleapis.com/maps/api/geocode/json?components=postal_code:32030";

        public getCity(String vinno,int stat) {
            this.zip_code = vinno;
            this.stat = stat;

        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";

            try {
                String virtual_url = web_p1 + zip_code;
                Log.e("tag", "<---urlll--->" + zip_code);
                Log.e("tag", "<---urlll--->" + virtual_url);
                JSONObject jsonobject = HttpUtils.getCity(web_p1);
                Log.d("tag", "" + jsonobject.toString());
                if (jsonobject.toString() == "sam") {
                    new SweetAlertDialog(getApplicationContext(), SweetAlertDialog.WARNING_TYPE)
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
            Log.e("tag", "<-----outttttt---->" + jsonStr);
            super.onPostExecute(jsonStr);

            try {
                JSONObject jo = new JSONObject(jsonStr);

                Log.e("tag",""+jo.getString("status"));

                if (jo.getString("status").equals("OK")) {

                    Log.e("tag","inside");

                    JSONArray jsona = jo.getJSONArray("results");

                    Log.e("tag","inside "+jsona);

                    JSONObject jos = jsona.getJSONObject(0);

                    Log.e("tag",""+jos.toString());

                    JSONArray jsoar = jos.getJSONArray("types");

                            for (int i = 0;i <jsoar.length();i++){

                                Log.e("tag",""+jsoar.getString(i).toString());

                                if(jsoar.getString(i).contains("postal_code")){

                                    String address= jos.getString("formatted_address");
                                    Log.e("tag",""+address);
                                    String[] parts = address.split(",");
                                    String city = parts[0];
                                    Log.e("tag",""+city);
                                    if(stat == 0){
                                        et_loc_city.setText(city);
                                        et_loc_phone.requestFocus();
                                    }
                                    else if (stat ==1){
                                        et_int_city.setText(city);
                                        et_int_phone.requestFocus();
                                    }

                                }
                            }


                } else {
                    if(stat == 0){
                        et_loc_city.requestFocus();
                    }
                    else if (stat ==1){
                        et_int_city.requestFocus();
                    }

                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }






}

