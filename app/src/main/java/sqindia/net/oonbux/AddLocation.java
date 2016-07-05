package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;

import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Salman on 4/22/2016.
 */
public class AddLocation extends Activity {

    static String _id = "vir_addr_id";
    static String addr1 = "vir_addr_line1";
    static String addr2 = "vir_addr_line2";
    static String city = "vir_addr_city";
    static String state = "vir_addr_state";
    static String zip = "vir_addr_zip";
    static String country = "vir_addr_country";
    com.rey.material.widget.LinearLayout lt_back;
    Button btn_submit;
    GridView grid1, grid2;
    Intent intent = getIntent();
    int status;
    String str_session_id,vir_sts;
    SweetAlertDialog sweetAlertDialog;
    TextView tv_header, tv_sub_header1, tv_sub_header2;
    ArrayList<GridviewDatas> us_datas = new ArrayList<GridviewDatas>();
    ArrayList<GridviewDatas> nig_datas = new ArrayList<GridviewDatas>();
    ArrayList<HashMap<String, String>> us_lists;
    ArrayList<HashMap<String, String>> nig_lists;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        if (vir_sts == "0") {

            Intent inte = new Intent(getApplicationContext(), ProfilePhysicalDeliveryAddress.class);
            startActivity(inte);
            finish();
        } else {
            Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
            startActivity(inte);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addloction);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AddLocation.this);

        str_session_id = sharedPreferences.getString("sessionid", "");
        vir_sts = sharedPreferences.getString("vir_sts", "");


        Intent getData = new Intent();
        status = getData.getIntExtra("sts", 0);
        Log.d("tag", "" + status);

        lt_back = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_back);
        btn_submit = (Button) findViewById(R.id.button_submit);

        tv_header = (TextView) findViewById(R.id.tv_hd_txt);
        tv_sub_header1 = (TextView) findViewById(R.id.tv_shd_txt1);
        tv_sub_header2 = (TextView) findViewById(R.id.tv_shd_txt2);

        grid1 = (GridView) findViewById(R.id.grd_usa);
        grid2 = (GridView) findViewById(R.id.grd_nig);

        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");

        tv_header.setTypeface(tf);
        tv_sub_header1.setTypeface(tf1);
        tv_sub_header2.setTypeface(tf1);
        btn_submit.setTypeface(tf1);


        us_lists = new ArrayList<>();
        nig_lists = new ArrayList<>();


        if (vir_sts == "0") {
            btn_submit.setText("Next");
        } else {
            btn_submit.setText("Submit");
        }


        if (!Config.isNetworkAvailable(AddLocation.this)) {

            new SweetAlertDialog(AddLocation.this, SweetAlertDialog.WARNING_TYPE)
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




        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (vir_sts == "0") {

                    Intent inte = new Intent(getApplicationContext(), ProfilePhysicalDeliveryAddress.class);
                    startActivity(inte);
                    finish();
                } else {
                    Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                    startActivity(inte);
                }

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (vir_sts == "0") {
                    btn_submit.setText("Next");

                  /*  SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AddLocation.this);
                    String getvirtual = sharedPreferences.getString("virtual_address","");

                    String[] playlists = getvirtual.split(",");

                    Log.d("tag",""+playlists[0] + "\t" +playlists[1])*/
                    ;


                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AddLocation.this);
                    String getvirtual1 = sharedPreferences.getString("virtual_address1", "");
                    String getvirtual2 = sharedPreferences.getString("virtual_address2", "");

                    if (getvirtual1.equals("") && getvirtual2.equals("")) {
                        Log.d("tag", "" + getvirtual1 + "\t" + getvirtual2);
                        Toast.makeText(getApplicationContext(), "Choose Virtual Address", Toast.LENGTH_LONG).show();

                    } else {
                        Log.d("tag", "1" + getvirtual1 + "\t" + getvirtual2);
                        Intent inte = new Intent(getApplicationContext(), ProfileDashboard.class);
                        startActivity(inte);
                    }




                    /*Intent inte = new Intent(getApplicationContext(), ProfileDashboard.class);
                    startActivity(inte);*/
                } else {
                    btn_submit.setText("Submit");
                   /* Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                    startActivity(inte);*/

                    new Virtual_Address_Task().execute();
                }

            }
        });



       /* grid1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tag","onclick");
            }
        });*/















    }


    class GetVirutal extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            sweetAlertDialog = new SweetAlertDialog(AddLocation.this, SweetAlertDialog.PROGRESS_TYPE);
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
                    new SweetAlertDialog(AddLocation.this, SweetAlertDialog.WARNING_TYPE)
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
                    Log.d("tag", "<-----virtual_address----->" + "" + virtual_address);

                    GridviewDatas getSet = new GridviewDatas();
                    for (int i = 0; i < virtual_address.length(); i++) {
                        JSONObject location_address = virtual_address.getJSONObject(i);
                        Log.d("tag", "<----Location_address-----> " + i + " " + location_address);
                        String country = location_address.getString("country");
                        Log.d("tag", "<----country-----> " + i + "" + country);

                        if (country.equals("US")) {

                            JSONArray us_location = location_address.getJSONArray("location");
                            Log.d("tag", "<----us-----> " + i + "" + us_location);

                            for (int j = 0; j < us_location.length(); j++) {
                                JSONObject us_address = us_location.getJSONObject(j);
                                Log.d("tag", "<----us_addr-----> " + j + " " + us_address);


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
                            Log.d("tag", "<----nigeria-----> " + i + "" + nig_location);

                            for (int j = 0; j < nig_location.length(); j++) {
                                JSONObject nig_address = nig_location.getJSONObject(i);
                                Log.d("tag", "<----nige_addr-----> " + j + " " + nig_address);

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
                    }


                    Gridview_adapter adapter1 = new Gridview_adapter(getApplicationContext(), us_lists);

                    Gridview_adapter1 adapter2 = new Gridview_adapter1(getApplicationContext(), nig_lists);

                    grid1.setAdapter(adapter1);


                    grid2.setAdapter(adapter2);


                } else {

                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }






    class Virtual_Address_Task extends AsyncTask<String, Void, String> {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AddLocation.this);

        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";


            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AddLocation.this);
            String getvirtual1 = sharedPreferences.getString("virtual_address1", "");
            String getvirtual2 = sharedPreferences.getString("virtual_address2", "");

            try {

                JSONObject jsonObject = new JSONObject();


                jsonObject.accumulate("selected_location", getvirtual1);
                jsonObject.accumulate("selected_location", getvirtual2);


                // 4. convert JSONObject to JSON to String
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest2(Config.SER_URL + "virtualaddr/update", json, str_session_id);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("tag", "<-----rerseres---->" + s);
            super.onPostExecute(s);

//            sweetAlertDialog.dismiss();

            try {
                JSONObject jo = new JSONObject(s);

                String status = jo.getString("status");

                String msg = jo.getString("message");
                Log.d("tag", "<-----Status----->" + status);


                if (status.equals("success")) {



                    Log.d("tag", "<-----Status----->" + msg);

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AddLocation.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("virtul_addr", "success");
                    editor.commit();

                    Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                    startActivity(inte);



                } else if (status.equals(null)) {
                    Toast.makeText(getApplicationContext(), "network not available", Toast.LENGTH_LONG).show();
                } else if (status.equals("fail")) {
                    Log.d("tag", "<-----Status----->" + msg);


                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(AddLocation.this);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("virtul_addr", "success");
                    editor.commit();

                    Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                    startActivity(inte);




                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }








}
