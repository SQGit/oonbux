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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.rey.material.widget.Button;
import com.rey.material.widget.Spinner;
import com.rey.material.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Salman on 5/18/2016.
 */
public class ChooseAddress extends Activity {

    com.rey.material.widget.LinearLayout lt_back;
    Button btn_payment;
    Spinner spin_one, spin_two;
    String[] sting,ss1;
    ListAdapter_Class country_list_adapter;
    Typeface tf, tf1;
    ArrayList<String> sss = new ArrayList<>();

    TextView tv_from, tv_to, tv_pickup, tv_sub1, tv_sub2, tv_sub3, tv_header;

    String str_name, str_oonbux_id, str_phone, str_state, str_zip, str_country,str_session_id;

    Button on, off, on1, off1;
    SweetAlertDialog sweetAlertDialog;
    ArrayList<HashMap<String, String>> us_lists;
    ArrayList<HashMap<String, String>> nig_lists;

    DbC dbclass;
    Context context = this;

    ArrayList<String> loc_lists = new ArrayList<>();
    ArrayList<String> int_lists = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooseaddress_new);
        tf1 = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");
        tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ChooseAddress.this);
        str_name = sharedPreferences.getString("fname", "") + " " + sharedPreferences.getString("lname", "");
        str_phone = sharedPreferences.getString("phone", "");
        str_zip = sharedPreferences.getString("zip", "");
        str_state = sharedPreferences.getString("state", "");
        str_oonbux_id = sharedPreferences.getString("oonbuxid", "");
        str_country = sharedPreferences.getString("country", "");
        str_session_id = sharedPreferences.getString("sessionid", "");


        dbclass = new DbC(context);

        on = (Button) findViewById(R.id.btn_on_pickup);
        off = (Button) findViewById(R.id.btn_off_pickup);
        on1 = (Button) findViewById(R.id.btn_on_to);
        off1 = (Button) findViewById(R.id.btn_off_to);


        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on.setBackgroundColor(getResources().getColor(R.color.tab_brown));
                on.setBackgroundResource(R.drawable.thumb);
                //on.setBackground(getResources().getDrawable(R.drawable.thumb));
                off.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                off.setTextColor(getResources().getColor(R.color.text_divider1));
                // off.setBackgroundColor(getResources().getColor(R.color.tab_default));

            }
        });
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                off.setBackgroundResource(R.drawable.thumb);
                on.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                on.setTextColor(getResources().getColor(R.color.text_divider1));
                /*off.setBackgroundColor(getResources().getColor(R.color.tab_brown));
                on.setBackgroundColor(getResources().getColor(R.color.tab_default));*/

            }
        });


        on1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // on.setBackgroundColor(getResources().getColor(R.color.tab_brown));
                on1.setBackgroundResource(R.drawable.thumb);
                //on.setBackground(getResources().getDrawable(R.drawable.thumb));
                off1.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                off1.setTextColor(getResources().getColor(R.color.text_divider1));
                // off.setBackgroundColor(getResources().getColor(R.color.tab_default));

            }
        });


        off1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                off1.setBackgroundResource(R.drawable.thumb);
                on1.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                on1.setTextColor(getResources().getColor(R.color.text_divider1));
                /*off.setBackgroundColor(getResources().getColor(R.color.tab_brown));
                on.setBackgroundColor(getResources().getColor(R.color.tab_default));*/

            }
        });


        lt_back = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_back);

        btn_payment = (Button) findViewById(R.id.button_payment);
        spin_one = (Spinner) findViewById(R.id.spin_one);

        spin_two = (Spinner) findViewById(R.id.spin_two);


        tv_from = (TextView) findViewById(R.id.tv_frm_txt);
        tv_pickup = (TextView) findViewById(R.id.tv_pickup_txt);
        tv_to = (TextView) findViewById(R.id.tv_to_txt);

        tv_sub1 = (TextView) findViewById(R.id.tv_shd_txt1);
        tv_sub2 = (TextView) findViewById(R.id.tv_shd_txt2);
        tv_sub3 = (TextView) findViewById(R.id.tv_shd_txt3);


        tv_header = (TextView) findViewById(R.id.tv_hd_txt);


        tv_sub1.setText(str_name + "\n" + str_oonbux_id + "\n" + str_state + "\t" + str_zip + "\n" + str_country);


        tv_header.setTypeface(tf1);

        tv_from.setTypeface(tf);
        tv_pickup.setTypeface(tf);
        tv_to.setTypeface(tf);

        tv_sub1.setTypeface(tf);
        tv_sub2.setTypeface(tf);
        tv_sub3.setTypeface(tf);

        btn_payment.setTypeface(tf);

        us_lists = new ArrayList<>();
        nig_lists = new ArrayList<>();



        if (!Config.isNetworkAvailable(ChooseAddress.this)) {

            new SweetAlertDialog(ChooseAddress.this, SweetAlertDialog.WARNING_TYPE)
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
            new GetVirutal().execute();


        }









        sting = new String[]{"72,Boorks,NY", "235,Mesa,CR",
                "1655,Ooae,VV", "5,Virginia,NW", "234255,oklahoma,SR"};

        sss.add("72,Boorks,NY");
        sss.add("asdf,DownTown,PR");
        sss.add("235,Mesa,CR");
        sss.add("1655,Ooae,VV");




     //   spin_two.setAdapter(new MyAdapter(ChooseAddress.this, R.layout.dropdown_lists3, sting));

        /*country_list_adapter = new ListAdapter_Class(getApplicationContext(), R.layout.dropdown_lists, sss);
        spin_one.setAdapter(country_list_adapter);*/


        // mySpinnerArrayAdapter = new   CustomAdapter(getApplicationContext(),R.layout.dropdown_lists1,sss);
        //spin_one.setAdapter(mySpinnerArrayAdapter);















        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                startActivity(inte);*/
                onBackPressed();
            }
        });

        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new SweetAlertDialog(ChooseAddress.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Complete Payment?")
                        .setConfirmText("Yes!")
                        .setCancelText("No")

                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismiss();
                                new SweetAlertDialog(ChooseAddress.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Your shipment completed successfully")
                                        .setContentText("Shipment id: 25445XCO44\n Oonbux id: DFD343DS32")
                                        .setConfirmText("Ok")

                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {

                                                dbclass.del_table();

                                                Intent newe = new Intent(getApplicationContext(), DashBoardActivity.class);
                                                startActivity(newe);
                                                sDialog.dismiss();

                                            }
                                        })
                                        .show();

                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();

                            }
                        })
                        .show();


            }
        });

    }




    class GetVirutal extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            sweetAlertDialog = new SweetAlertDialog(ChooseAddress.this, SweetAlertDialog.PROGRESS_TYPE);
            sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            sweetAlertDialog.setTitleText("Loading");
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.show();
        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";




            try {

                String virtual_url = Config.SER_URL + "virtualaddr/fetch";

                JSONObject jsonobject = HttpUtils.getVirtual(virtual_url, str_session_id);

                Log.d("tag", "" + jsonobject.toString());

                if (jsonobject.toString() == "sam") {
                    new SweetAlertDialog(ChooseAddress.this, SweetAlertDialog.WARNING_TYPE)
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
            Log.e("tag", "<-----rerseres---->" + jsonStr);
            super.onPostExecute(jsonStr);
            sweetAlertDialog.dismiss();



            try {

                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");


                if (status.equals("success")) {
                    JSONArray virtual_address = jo.getJSONArray("virtual_location");
                   // Log.d("tag", "<-----virtual_address----->" + "" + virtual_address);

                    GridviewDatas getSet = new GridviewDatas();
                    for (int i = 0; i < virtual_address.length(); i++) {
                        JSONObject location_address = virtual_address.getJSONObject(i);
                       // Log.d("tag", "<----Location_address-----> " + i + " " + location_address);
                        String country = location_address.getString("country");
                       // Log.d("tag", "<----country-----> " + i + "" + country);

                        if (country.equals("US")) {

                            JSONArray us_location = location_address.getJSONArray("location");
                           // Log.d("tag", "<----us-----> " + i + "" + us_location);

                            for (int j = 0; j < us_location.length(); j++) {
                                JSONObject us_address = us_location.getJSONObject(j);
                              //  Log.d("tag", "<----us_addr-----> " + j + " " + us_address);


                                HashMap<String, String> map = new HashMap<String, String>();
                                us_address = us_location.getJSONObject(j);
                                map.put("vir_addr_id", us_address.getString("vir_addr_id"));
                                map.put("vir_addr_line1", us_address.getString("vir_addr_line1"));
                                map.put("vir_addr_line2", us_address.getString("vir_addr_line2"));
                                map.put("vir_addr_city", us_address.getString("vir_addr_city"));
                                map.put("vir_addr_state", us_address.getString("vir_addr_state"));
                                map.put("vir_addr_zip", us_address.getString("vir_addr_zip"));
                                map.put("vir_addr_country", us_address.getString("vir_addr_country"));

                                us_lists.add(map);


                            }

                        }


                        if (country.equals("NIGERIA")) {

                            JSONArray nig_location = location_address.getJSONArray("location");
                           // Log.d("tag", "<----nigeria-----> " + i + "" + nig_location);

                            for (int j = 0; j < nig_location.length(); j++) {
                                JSONObject nig_address = nig_location.getJSONObject(i);
                              //  Log.d("tag", "<----nige_addr-----> " + j + " " + nig_address);

                                HashMap<String, String> map = new HashMap<String, String>();
                                nig_address = nig_location.getJSONObject(j);
                                map.put("vir_addr_id", nig_address.getString("vir_addr_id"));
                                map.put("vir_addr_line1", nig_address.getString("vir_addr_line1"));
                                map.put("vir_addr_line2", nig_address.getString("vir_addr_line2"));
                                map.put("vir_addr_city", nig_address.getString("vir_addr_city"));
                                map.put("vir_addr_state", nig_address.getString("vir_addr_state"));
                                map.put("vir_addr_zip", nig_address.getString("vir_addr_zip"));
                                map.put("vir_addr_country", nig_address.getString("vir_addr_country"));

                                nig_lists.add(map);
                            }
                        }




                        Log.d("tag",""+us_lists.size());

                       // ss1 = new String[us_lists.size()];

                        for(int ii =0;ii<us_lists.size();ii++) {

                            HashMap<String, String> hash = new HashMap<>();
                            hash = us_lists.get(ii);

                            String a = hash.get("vir_addr_city");
                            String b = hash.get("vir_addr_state");
                            String c = hash.get("vir_addr_zip");

                            //ss1[ii] = a + "," + b;
                            loc_lists.add(a+","+b);

                            Log.d("tag", a + " " + b + " " + c);
                            //Log.d("tag", ss1[ii]);

                        }




                        for(int iii =0;iii<nig_lists.size();iii++) {

                            HashMap<String, String> hash = new HashMap<>();
                            hash = nig_lists.get(iii);

                            String a = hash.get("vir_addr_city");
                            String b = hash.get("vir_addr_state");
                            String c = hash.get("vir_addr_zip");

                            //ss1[ii] = a + "," + b;
                           int_lists.add(a+","+b);

                            Log.d("tag", a + " " + b + " " + c);
                            //Log.d("tag", ss1[ii]);

                        }






                    }

                    spin_one.setAdapter(new MyAdapter(ChooseAddress.this, R.layout.dropdown_lists3, loc_lists));

                    spin_two.setAdapter(new MyAdapter(ChooseAddress.this, R.layout.dropdown_lists3, int_lists));


                } else {

                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }












    public class MyAdapter extends ArrayAdapter<String> {

        int resource;
       ArrayList<String> list_datas;

        public MyAdapter(Context context, int textViewResourceId,ArrayList<String> objects) {
            super(context, textViewResourceId, objects);

            this.resource = textViewResourceId;
            this.list_datas = objects;

        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(resource, parent, false);

            TextView label = (TextView) row.findViewById(R.id.text_spin);
            label.setTypeface(tf);

            label.setText(list_datas.get(position));
            return row;
        }
    }


    private class CustomAdapter extends ArrayAdapter {

        private Context context;
        private List<String> itemList;

        public CustomAdapter(Context context, int textViewResourceId, List<String> itemList) {

            super(context, textViewResourceId, itemList);
            this.context = context;
            this.itemList = itemList;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.dropdown_lists1, parent,
                    false);
            TextView make = (TextView) row.findViewById(R.id.text_spin);
            make.setText(itemList.get(position));
            return row;
        }

        public View getDropDownView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.dropdown_lists1, parent,
                    false);
            TextView make = (TextView) row.findViewById(R.id.text_spin);
            make.setText(itemList.get(position));
            return row;
        }
    }
}
