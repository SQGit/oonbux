package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.Button;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Salman on 4/29/2016.
 */
public class DeliveryAddress extends Activity {
    LinearLayout bottom_lt;
    TextView tv_header, tv_sub_hdr_loc, tv_sub_hdr_int;
    Button btn_save_loc, btn_save_int, btn_next, btn_add_loc, btn_add_int;
    com.rey.material.widget.LinearLayout lt_back;
    MaterialEditText et_loc_add1, et_loc_add2, et_loc_city, et_loc_state, et_loc_zip, et_loc_phone, et_loc_note, et_int_add1, et_int_add2, et_int_city, et_int_state, et_int_zip, et_int_phone, et_int_note;
    String get_profile_sts, str_def_adr, str_loc_add1, str_loc_add2, str_loc_city, str_loc_state, str_loc_zip, str_loc_phone,
            str_loc_note, str_int_add1, str_int_add2, str_int_city, str_int_state, str_int_zip, str_int_phone, str_int_note;

    CheckBox cb_loc, cb_int;
    boolean loc_adr, int_adr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DeliveryAddress.this);

        get_profile_sts = sharedPreferences.getString("profile", "");
        Log.e("tag", "pfs" + get_profile_sts);

        str_loc_add1 = sharedPreferences.getString("loc_addr1", "");
        str_loc_add2 = sharedPreferences.getString("loc_addr2", "");
        str_loc_city = sharedPreferences.getString("loc_city", "");
        str_loc_state = sharedPreferences.getString("loc_state", "");
        str_loc_zip = sharedPreferences.getString("loc_zip", "");
        str_loc_phone = sharedPreferences.getString("loc_phone", "");
        str_loc_note = sharedPreferences.getString("loc_note", "");

        str_int_add1 = sharedPreferences.getString("int_addr1", "");
        str_int_add2 = sharedPreferences.getString("int_addr2", "");
        str_int_city = sharedPreferences.getString("int_city", "");
        str_int_state = sharedPreferences.getString("int_state", "");
        str_int_zip = sharedPreferences.getString("int_zip", "");
        str_int_phone = sharedPreferences.getString("int_phone", "");
        str_int_note = sharedPreferences.getString("int_note", "");
        str_def_adr = sharedPreferences.getString("default_adr", "");


        // bck_lt = (LinearLayout) findViewById(R.id.bck_layout);

        tv_header = (TextView) findViewById(R.id.tv_hd_txt);
        tv_sub_hdr_loc = (TextView) findViewById(R.id.tv_shd_txt_loc);
        tv_sub_hdr_int = (TextView) findViewById(R.id.tv_shd_txt_int);

        btn_save_loc = (Button) findViewById(R.id.button_save_loc);
        btn_save_int = (Button) findViewById(R.id.button_save_int);
        btn_add_loc = (Button) findViewById(R.id.button_add_loc);
        btn_add_int = (Button) findViewById(R.id.button_add_int);
        btn_next = (Button) findViewById(R.id.button_next);

        et_loc_add1 = (MaterialEditText) findViewById(R.id.edittext_loc_address1);
        et_loc_add2 = (MaterialEditText) findViewById(R.id.edittext_loc_address2);
        et_loc_city = (MaterialEditText) findViewById(R.id.edittext_loc_city);
        et_loc_state = (MaterialEditText) findViewById(R.id.edittext_loc_state);
        et_loc_zip = (MaterialEditText) findViewById(R.id.edittext_loc_zip);
        et_loc_phone = (MaterialEditText) findViewById(R.id.edittext_loc_phone);
        et_loc_note = (MaterialEditText) findViewById(R.id.edittext_loc_note);

        et_int_add1 = (MaterialEditText) findViewById(R.id.edittext_int_address1);
        et_int_add2 = (MaterialEditText) findViewById(R.id.edittext_int_address2);
        et_int_city = (MaterialEditText) findViewById(R.id.edittext_int_city);
        et_int_state = (MaterialEditText) findViewById(R.id.edittext_int_state);
        et_int_zip = (MaterialEditText) findViewById(R.id.edittext_int_zip);
        et_int_phone = (MaterialEditText) findViewById(R.id.edittext_int_phone);
        et_int_note = (MaterialEditText) findViewById(R.id.edittext_int_note);

        lt_back = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_back);
        bottom_lt = (LinearLayout) findViewById(R.id.bottom);

        cb_loc = (CheckBox) findViewById(R.id.checkbox_local);
        cb_int = (CheckBox) findViewById(R.id.checkbox_int);


        if (str_def_adr == "LOCAL") {
            cb_loc.setChecked(true);
        } else if (str_def_adr == "INTERNATIONAL") {
            cb_int.setChecked(true);
        }


        getfromdata();


        if ((get_profile_sts.equals(""))) {
            bottom_lt.setVisibility(View.VISIBLE);
        } else {
            bottom_lt.setVisibility(View.GONE);
            disable_loc();
            disable_int();
            btn_save_loc.setVisibility(View.GONE);
            btn_save_int.setVisibility(View.GONE);


        }


        if (sharedPreferences.getBoolean("adrsts", false)) {
            loc_adr = true;
            int_adr = true;
            disable_loc();
            disable_int();
            btn_save_loc.setVisibility(View.GONE);
            btn_save_int.setVisibility(View.GONE);
        }


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");
        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");

        tv_header.setTypeface(tf);

        tv_sub_hdr_loc.setTypeface(tf1);
        tv_sub_hdr_int.setTypeface(tf1);

        btn_save_loc.setTypeface(tf1);
        btn_save_int.setTypeface(tf1);
        btn_add_loc.setTypeface(tf1);
        btn_add_int.setTypeface(tf1);
        btn_next.setTypeface(tf1);

        et_loc_add1.setTypeface(tf1);
        et_loc_add2.setTypeface(tf1);
        et_loc_city.setTypeface(tf1);
        et_loc_state.setTypeface(tf1);
        et_loc_zip.setTypeface(tf1);
        et_loc_phone.setTypeface(tf1);
        et_loc_note.setTypeface(tf1);

        et_int_add1.setTypeface(tf1);
        et_int_add2.setTypeface(tf1);
        et_int_city.setTypeface(tf1);
        et_int_state.setTypeface(tf1);
        et_int_zip.setTypeface(tf1);
        et_int_phone.setTypeface(tf1);
        et_int_note.setTypeface(tf1);


        cb_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!loc_adr) {
                    new SweetAlertDialog(DeliveryAddress.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Address is empty,fill address")
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
                }


            }
        });

        cb_int.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!int_adr) {
                    new SweetAlertDialog(DeliveryAddress.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Address is empty,fill address")
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
                }

            }
        });


        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((get_profile_sts.equals(""))) {
                  /*  Intent inte = new Intent(getApplicationContext(), ProfileInfo.class);
                    startActivity(inte);*/

                    onBackPressed();

                    // Toast.makeText(getApplicationContext(), "Please complete your profile", Toast.LENGTH_LONG).show();
                } else {
                    Intent inte = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(inte);
                }

            }
        });


        btn_add_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loc_adr = false;
                btn_save_loc.setVisibility(View.VISIBLE);
                enable_loc();

                bottom_lt.setVisibility(View.VISIBLE);


                et_loc_add1.setText("");
                et_loc_add1.requestFocus();
                et_loc_add2.setText("");
                et_loc_city.setText("");
                et_loc_state.setText("");
                et_loc_zip.setText("");
                et_loc_phone.setText("");
                et_loc_note.setText("");
            }
        });


        btn_add_int.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int_adr = false;
                btn_save_int.setVisibility(View.VISIBLE);
                enable_int();

                bottom_lt.setVisibility(View.VISIBLE);

                et_int_add1.setText("");
                et_int_add1.requestFocus();
                et_int_add2.setText("");
                et_int_city.setText("");
                et_int_state.setText("");
                et_int_zip.setText("");
                et_int_phone.setText("");
                et_int_note.setText("");
            }
        });


        btn_save_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_loc_add1 = et_loc_add1.getText().toString();
                str_loc_add2 = et_loc_add2.getText().toString();
                str_loc_city = et_loc_city.getText().toString();
                str_loc_state = et_loc_state.getText().toString();
                str_loc_zip = et_loc_zip.getText().toString();
                str_loc_phone = et_loc_phone.getText().toString();
                str_loc_note = et_loc_note.getText().toString();


                validate(0, str_loc_add1, str_loc_add2, str_loc_city, str_loc_state, str_loc_zip, str_loc_phone, str_loc_note);


            }
        });

        btn_save_int.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_int_add1 = et_int_add1.getText().toString();
                str_int_add2 = et_int_add2.getText().toString();
                str_int_city = et_int_city.getText().toString();
                str_int_state = et_int_state.getText().toString();
                str_int_zip = et_int_zip.getText().toString();
                str_int_phone = et_int_phone.getText().toString();
                str_int_note = et_int_note.getText().toString();

                validate(1, str_int_add1, str_int_add2, str_int_city, str_int_state, str_int_zip, str_int_phone, str_int_note);

            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            if (!(loc_adr || int_adr)) {
                                                //Toast.makeText(getApplicationContext(), "Fill addresses", Toast.LENGTH_LONG).show();

                                                new SweetAlertDialog(DeliveryAddress.this, SweetAlertDialog.WARNING_TYPE)
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

                                                new SweetAlertDialog(DeliveryAddress.this, SweetAlertDialog.WARNING_TYPE)
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

                                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DeliveryAddress.this);
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


                                                Intent inte = new Intent(getApplicationContext(), ProfileActivity.class);
                                                startActivity(inte);


                                            }
                                        }
                                    }


        );


    }


    public void validate(int i, String addr1, String addr2, String city, String state, String zip, String phone, String note)

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

                                    new SweetAlertDialog(DeliveryAddress.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("Local Address Updated")
                                            .setConfirmText("OK")

                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {

                                                    sDialog.dismiss();
                                                }
                                            })
                                            .show();

                                    loc_adr = true;
                                } else {
                                    btn_save_int.setVisibility(View.GONE);
                                    disable_int();

                                    new SweetAlertDialog(DeliveryAddress.this, SweetAlertDialog.SUCCESS_TYPE)
                                            .setTitleText("International Address Updated")
                                            .setConfirmText("OK")

                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {

                                                    sDialog.dismiss();
                                                }
                                            })
                                            .show();


                                    // Toast.makeText(getApplicationContext(), "International address Updated", Toast.LENGTH_LONG).show();
                                    int_adr = true;
                                }


                                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DeliveryAddress.this);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(location + "_addr1", addr1);
                                editor.putString(location + "_addr2", addr2);
                                editor.putString(location + "_city", city);
                                editor.putString(location + "_state", state);
                                editor.putString(location + "_zip", zip);
                                editor.putString(location + "_phone", phone);
                                editor.putString(location + "_note", note);
                                editor.putBoolean("adrsts", true);
                                editor.commit();




                                 /*   Intent inte = new Intent(getApplicationContext(), DeliveryAddress.class);
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


    private void getfromdata() {

        //  disable_loc();
        //  disable_int();

        et_loc_add1.setText(str_loc_add1);
        et_loc_add2.setText(str_loc_add2);
        et_loc_city.setText(str_loc_city);
        et_loc_state.setText(str_loc_state);
        et_loc_zip.setText(str_loc_zip);
        et_loc_phone.setText(str_loc_phone);
        et_loc_note.setText(str_loc_note);

        et_int_add1.setText(str_int_add1);
        et_int_add2.setText(str_int_add2);
        et_int_city.setText(str_int_city);
        et_int_state.setText(str_int_state);
        et_int_zip.setText(str_int_zip);
        et_int_phone.setText(str_int_phone);
        et_int_note.setText(str_int_note);

    }


    public void enable_loc() {

        et_loc_add1.setEnabled(true);
        et_loc_add2.setEnabled(true);
        et_loc_city.setEnabled(true);
        et_loc_state.setEnabled(true);
        et_loc_zip.setEnabled(true);
        et_loc_phone.setEnabled(true);
        et_loc_note.setEnabled(true);

    }


    public void enable_int() {
        et_int_add1.setEnabled(true);
        et_int_add2.setEnabled(true);
        et_int_city.setEnabled(true);
        et_int_state.setEnabled(true);
        et_int_zip.setEnabled(true);
        et_int_phone.setEnabled(true);
        et_int_note.setEnabled(true);

    }


    public void disable_loc() {

        et_loc_add1.setEnabled(false);
        et_loc_add2.setEnabled(false);
        et_loc_city.setEnabled(false);
        et_loc_state.setEnabled(false);
        et_loc_zip.setEnabled(false);
        et_loc_phone.setEnabled(false);
        et_loc_note.setEnabled(false);
    }

    public void disable_int() {
        et_int_add1.setEnabled(false);
        et_int_add2.setEnabled(false);
        et_int_city.setEnabled(false);
        et_int_state.setEnabled(false);
        et_int_zip.setEnabled(false);
        et_int_phone.setEnabled(false);
        et_int_note.setEnabled(false);
    }


}

