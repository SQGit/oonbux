package sqindia.net.oonbux.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import sqindia.net.oonbux.config.Config;
import sqindia.net.oonbux.config.DbC;
import sqindia.net.oonbux.Dialog.Dialog_Msg;
import sqindia.net.oonbux.Dialog.Dialog_new;
import sqindia.net.oonbux.Adapter.Gridview_adapter;
import sqindia.net.oonbux.Adapter.Gridview_adapter1;
import sqindia.net.oonbux.config.HttpUtils;
import sqindia.net.oonbux.Profile.ProfileDashboard;
import sqindia.net.oonbux.Profile.ProfilePhysicalDeliveryAddress;
import sqindia.net.oonbux.R;


/**
 * Created by Salman on 4/22/2016.
 */
//asfd
public class AddLocation extends Activity {

   public static String _id = "vir_addr_id";
    public static String addr1 = "vir_addr_line1";
    public static String addr2 = "vir_addr_line2";
    public static String city = "vir_addr_city";
    public static String state = "vir_addr_state";
    public static String zip = "vir_addr_zip";
    public static String country = "vir_addr_country";
    //com.rey.material.widget.LinearLayout lt_back;
    ImageView btn_back;
    Button btn_submit;
    GridView grid1, grid2;
    Intent intent = getIntent();
    int status;
    String str_session_id, vir_sts;
    TextView tv_header, tv_sub_header1, tv_sub_header2;
    ArrayList<HashMap<String, String>> us_lists;
    ArrayList<HashMap<String, String>> nig_lists;
    DbC dbclass;
    Context context = this;
    Dialog loading_dialog;
    private SQLiteDatabase db;

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

        //lt_back = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_back);
        btn_submit = (Button) findViewById(R.id.button_submit);
        btn_back = (ImageView) findViewById(R.id.btn_back);
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

        dbclass = new DbC(context);
        createDatabase();

        us_lists = new ArrayList<>();
        nig_lists = new ArrayList<>();


        if (vir_sts == "") {
            btn_submit.setText("Next");
        } else {
            btn_submit.setText("Submit");
        }


        if (!Config.isNetworkAvailable(AddLocation.this)) {

            Dialog_new dialog_wifi_settings = new Dialog_new(AddLocation.this, "No Network Avaliable!\nGo to Settings.", 5);
            dialog_wifi_settings.setCancelable(false);
            dialog_wifi_settings.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog_wifi_settings.show();
        } else {
            new GetVirutal().execute();

        }


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (vir_sts == "") {

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


                if (vir_sts == "") {
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

    protected void createDatabase() {

        Log.d("tag", "createdb");
        db = openOrCreateDatabase("oonbux", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS virtual(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, addr_id VARCHAR, addr_line1 VARCHAR, addr_line2 VARCHAR, city VARCHAR, state VARCHAR, zip VARCHAR, country VARCHAR, loc VARCHAR );");

    }


    protected void deleteDatabase() {

        Log.d("tag", "createdb");
        db = openOrCreateDatabase("oonbux", Context.MODE_PRIVATE, null);
        // db.execSQL("TRUNCATE table virtual");

        db.delete("virtual", null, null);

    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
    }

    class GetVirutal extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            loading_dialog = new Dialog(AddLocation.this);
            loading_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            loading_dialog.setContentView(R.layout.dialog_loading);
            loading_dialog.setCancelable(false);
            loading_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            loading_dialog.show();
        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";


            try {

                String virtual_url = Config.SER_URL + "virtualaddr/fetch";

                JSONObject jsonobject = HttpUtils.getVirtual(virtual_url, str_session_id);

                Log.d("tag", "" + jsonobject.toString());

                if (jsonobject.toString() == "sam") {
                    Dialog_Msg dialog_fail = new Dialog_Msg(AddLocation.this, "Try Again Later");
                    dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog_fail.show();
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
            loading_dialog.dismiss();


            try {

                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");


                if (status.equals("success")) {

                    deleteDatabase();


                    JSONArray virtual_address = jo.getJSONArray("virtual_location");
                    Log.d("tag", "<-----virtual_address----->" + "" + virtual_address);

                    //GridviewDatas getSet = new GridviewDatas();
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

                                dbclass.virtual_insert(us_address.getString("vir_addr_id"), us_address.getString("vir_addr_line1"), us_address.getString("vir_addr_line2"), us_address.getString("vir_addr_city"), us_address.getString("vir_addr_state"), us_address.getString("vir_addr_zip"), us_address.getString("vir_addr_country"), "0");

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

                                dbclass.virtual_insert(nig_address.getString("vir_addr_id"), nig_address.getString("vir_addr_line1"), nig_address.getString("vir_addr_line2"), nig_address.getString("vir_addr_city"), nig_address.getString("vir_addr_state"), nig_address.getString("vir_addr_zip"), nig_address.getString("vir_addr_country"), "1");


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
            loading_dialog = new Dialog(AddLocation.this);
            loading_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            loading_dialog.setContentView(R.layout.dialog_loading);
            loading_dialog.setCancelable(false);
            loading_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            loading_dialog.show();

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

            loading_dialog.dismiss();

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
