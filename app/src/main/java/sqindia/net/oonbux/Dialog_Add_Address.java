package sqindia.net.oonbux;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Salman on 6/21/2016.
 */
public class Dialog_Add_Address extends Dialog {

    public Activity c;
    public String address;
    Button save;
    MaterialEditText et_addr_line1, et_addr_line2, et_city, et_state, et_zip, et_country, et_note, et_phone;
    String str_addr_line1, str_addr_line2, str_city, str_state, str_zip, str_country, str_note, str_phone, str_session_id;
    ArrayList<String> daa = new ArrayList<>();
    int sts;
    ImageView close;
    SweetAlertDialog sweetDialog;

    public Dialog_Add_Address(Activity activity, int k, ArrayList<String> ddd) {
        super(activity);
        this.c = activity;
        this.daa = ddd;
        this.sts = k;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_address);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);

        str_session_id = sharedPreferences.getString("sessionid", "");

        Typeface tf = Typeface.createFromAsset(c.getAssets(), "fonts/prox.otf");


        save = (Button) findViewById(R.id.button_save);

        et_addr_line1 = (MaterialEditText) findViewById(R.id.edittext_address1);
        et_addr_line2 = (MaterialEditText) findViewById(R.id.edittext_address2);
        et_city = (MaterialEditText) findViewById(R.id.edittext_city);
        et_state = (MaterialEditText) findViewById(R.id.edittext_state);
        et_zip = (MaterialEditText) findViewById(R.id.edittext_zip);
        et_country = (MaterialEditText) findViewById(R.id.edittext_country);
        et_note = (MaterialEditText) findViewById(R.id.edittext_note);
        et_phone = (MaterialEditText) findViewById(R.id.edittext_phone);
        close = (ImageView) findViewById(R.id.img_close);


        et_addr_line1.setTypeface(tf);
        et_addr_line2.setTypeface(tf);
        et_city.setTypeface(tf);
        et_state.setTypeface(tf);
        et_zip.setTypeface(tf);
        et_country.setTypeface(tf);
        et_note.setTypeface(tf);
        et_phone.setTypeface(tf);


        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        if (sts == 0) {

            address = Config.SER_URL + "addshippingaddr/add";
            save.setText("Save");

        } else {
            address = Config.SER_URL + "addshippingaddr/update";
            save.setText("Update");
            et_addr_line1.setText(daa.get(1));
            et_addr_line2.setText(daa.get(2));
            et_country.setText(daa.get(3));
            et_state.setText(daa.get(4));
            et_city.setText(daa.get(5));
            et_zip.setText(daa.get(6));
            et_phone.setText(daa.get(7));
            et_note.setText(daa.get(8));
        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_addr_line1 = et_addr_line1.getText().toString();
                str_addr_line2 = et_addr_line2.getText().toString();
                str_city = et_city.getText().toString();
                str_state = et_state.getText().toString();
                str_zip = et_zip.getText().toString();
                str_country = et_country.getText().toString();
                str_note = et_note.getText().toString();
                str_phone = et_phone.getText().toString();


                if (!str_addr_line1.isEmpty()) {
                    if (!(str_country.isEmpty())) {
                        if (!(str_state.isEmpty())) {
                            if (!(str_city.isEmpty())) {
                                if (!(str_zip.isEmpty())) {
                                    if (!(str_phone.isEmpty())) {
                                        if (!(str_note.isEmpty())) {
                                            new add_Address_Task(address).execute();
                                        } else {
                                            et_note.setError("Enter Note");
                                            et_note.requestFocus();
                                        }

                                    } else {
                                        et_phone.setError("Enter Phone");
                                        et_phone.requestFocus();
                                    }

                                } else {
                                    et_zip.setError("Enter Zip");
                                    et_zip.requestFocus();
                                }
                            } else {
                                et_city.setError("Enter City");
                                et_city.requestFocus();
                            }
                        } else {
                            et_state.setError("Enter State");
                            et_state.requestFocus();
                        }
                    } else {
                        et_country.setError("Enter Country");
                        et_country.requestFocus();
                    }
                } else {
                    et_addr_line1.setError("Enter Street/Area");
                    et_addr_line1.requestFocus();
                }


            }
        });


    }


    class add_Address_Task extends AsyncTask<String, Void, String> {

        String address;

        public add_Address_Task(String adr) {
            this.address = adr;

        }

        protected void onPreExecute() {
            super.onPreExecute();

            sweetDialog = new SweetAlertDialog(c, SweetAlertDialog.PROGRESS_TYPE);
            sweetDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            sweetDialog.setTitleText("Loading");
            sweetDialog.setCancelable(false);
            sweetDialog.show();


        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";

            try {

                JSONObject jsonObject = new JSONObject();

                if (sts == 1) {
                    jsonObject.accumulate("addr_id", daa.get(0));
                }

                jsonObject.accumulate("addr_type", "LOCAL");
                jsonObject.accumulate("addr_line1", str_addr_line1);
                jsonObject.accumulate("addr_line2", str_addr_line2);
                jsonObject.accumulate("addr_city", str_city);
                jsonObject.accumulate("addr_state", str_state);
                jsonObject.accumulate("addr_zip", str_zip);
                jsonObject.accumulate("addr_country", str_country);
                jsonObject.accumulate("delivery_note", str_note);
                jsonObject.accumulate("addr_phone", str_phone);


                // 4. convert JSONObject to JSON to String
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest2(address, json, str_session_id);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
                sweetDialog.dismiss();
            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("tag", "<-----rerseres---->" + s);
            super.onPostExecute(s);

            sweetDialog.dismiss();

            try {
                JSONObject jo = new JSONObject(s);

                String status = jo.getString("status");

                String msg = jo.getString("message");
                Log.d("tag", "<-----Status----->" + status);


                if (status.equals("success")) {


                    Log.d("tag", "<-----Status----->" + msg);

                    dismiss();

                    Intent itsad = new Intent(c, ManageAddress.class);
                    c.startActivity(itsad);


                } else if (status.equals(null)) {

                } else if (status.equals("fail")) {
                    Log.d("tag", "<-----Status----->" + msg);


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }


}
