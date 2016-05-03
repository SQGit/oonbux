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

/**
 * Created by Salman on 4/29/2016.
 */
public class DeliveryAddress extends Activity {
    LinearLayout bck_lt;
    TextView tv_header, tv_sub_hdr_loc, tv_sub_hdr_int;
    Button btn_save_loc, btn_save_int, btn_next, btn_add_loc, btn_add_int;
    com.rey.material.widget.LinearLayout lt_back;
    MaterialEditText et_loc_add1, et_loc_add2, et_loc_city, et_loc_state, et_loc_zip, et_loc_phone, et_loc_note, et_int_add1, et_int_add2, et_int_city, et_int_state, et_int_zip, et_int_phone, et_int_note;
    String get_profile_sts, str_def_adr, str_loc_add1, str_loc_add2, str_loc_city, str_loc_state, str_loc_zip, str_loc_phone, str_loc_note, str_int_add1, str_int_add2, str_int_city, str_int_state, str_int_zip, str_int_phone, str_int_note;

    CheckBox cb_loc, cb_int;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DeliveryAddress.this);

        get_profile_sts = sharedPreferences.getString("profile", "");
        Log.e("tag", "pfs" + get_profile_sts);

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

        cb_loc = (CheckBox) findViewById(R.id.checkbox_local);
        cb_int = (CheckBox) findViewById(R.id.checkbox_int);

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
                cb_int.setChecked(false);

            }
        });

        cb_int.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cb_loc.setChecked(false);
            }
        });


        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((get_profile_sts.equals(""))) {
                    Toast.makeText(getApplicationContext(), "Please complete your profile", Toast.LENGTH_LONG).show();
                } else {
                    Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                    startActivity(inte);
                }

            }
        });


        btn_add_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_loc_add1.setText("");
                et_loc_add1.requestFocus();
                et_int_add2.setText("");
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

                                            if (!(cb_loc.isChecked() || cb_int.isChecked())) {
                                                Toast.makeText(getApplicationContext(), "choose default address", Toast.LENGTH_LONG).show();
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
            if (!addr2.isEmpty()) {
                if (!(city.isEmpty())) {
                    if (!state.isEmpty()) {
                        if (!zip.isEmpty()) {
                            if (!(phone.isEmpty() || phone.length() < 10)) {
                                if (!note.isEmpty()) {

                                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DeliveryAddress.this);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(location + "_address1", addr1);
                                    editor.putString(location + "_address2", addr2);
                                    editor.putString(location + "_city", city);
                                    editor.putString(location + "_state", state);
                                    editor.putString(location + "_zip", zip);
                                    editor.putString(location + "_phone", phone);
                                    editor.putString(location + "_note", note);
                                    editor.commit();

                                    Toast.makeText(getApplicationContext(), location + "address Updated", Toast.LENGTH_LONG).show();

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
        } else {
            Toast.makeText(getApplicationContext(), "enter address", Toast.LENGTH_LONG).show();
        }
    }

}

