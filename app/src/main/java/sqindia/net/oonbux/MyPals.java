package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Salman on 6/27/2016.
 */
public class MyPals extends Activity {

    ListView lv_mypals;
    SweetAlertDialog sweetDialog;
    String str_sessionid;
    HashMap<String, String> map ;
    LinearLayout lt_add,lt_back,lt_pending;
    ArrayList<HashMap<String, String>> pal_datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_pal);


        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MyPals.this);
        str_sessionid = sharedPreferences.getString("sessionid", "");

        lv_mypals = (ListView) findViewById(R.id.lview);
        lt_add = (LinearLayout) findViewById(R.id.layout_add);
        lt_back = (LinearLayout) findViewById(R.id.layout_back);
        lt_pending = (LinearLayout) findViewById(R.id.layout_pending);


        pal_datas = new ArrayList<>();

        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        lt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goSearch = new Intent(getApplicationContext(),SearchPal.class);
                startActivity(goSearch);

            }
        });

        lt_pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //  MyPals.this.openOptionsMenu();

                PopupMenu popup = new PopupMenu(MyPals.this, lt_pending);

                popup.getMenuInflater().inflate(R.menu.palmenu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {


                            case R.id.request: {
                                Intent i = new Intent(getApplicationContext(), PendingbyMe.class);
                                startActivity(i);
                                return true;
                            }

                            case R.id.pending: {
                                Intent i = new Intent(getApplicationContext(), PendingbyPal.class);
                                startActivity(i);
                                return true;
                            }

                            default: {
                                return true;
                            }


                        }


                    }
                });

                popup.show();


            }
        });





        new getPalLists().execute();

     /*   Adapter_PalLists staff_adapter = new Adapter_PalLists(MyPals.this, map, 5);
        lv_mypals.setAdapter(staff_adapter);*/

    }


    class getPalLists extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            sweetDialog = new SweetAlertDialog(MyPals.this, SweetAlertDialog.PROGRESS_TYPE);
            sweetDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            sweetDialog.setTitleText("Loading");
            sweetDialog.setCancelable(false);
            sweetDialog.show();
        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";


            try {

                String virtual_url = Config.SER_URL + "pal/friends";
                Log.e("tag",""+virtual_url);

                JSONObject jsonobject = HttpUtils.getPalLists(virtual_url, str_sessionid);

                Log.d("tag", "" + jsonobject.toString());

                if (jsonobject.toString() == "sam") {
                    new SweetAlertDialog(MyPals.this, SweetAlertDialog.WARNING_TYPE)
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
            sweetDialog.dismiss();


            try {

                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");
                int count = Integer.valueOf(jo.getString("count"));

                Log.e("tag", "<-----count---->" + count);

                if (status.equals("success")) {

                    if (count > 0) {


                        String subd = jo.getString("friends");

                        JSONArray friends_list = jo.getJSONArray("friends");
                        Log.e("tag", "<-----friends_list----->" + "" + friends_list);


                        for (int i = 0; i < friends_list.length(); i++) {

                            JSONObject datas = friends_list.getJSONObject(i);


                            map = new HashMap<String, String>();
                            map.put("oonbux_id", datas.getString("oonbux_id"));
                            map.put("firstname", datas.getString("firstname"));
                            map.put("lastname", datas.getString("lastname"));
                            map.put("email", datas.getString("email"));
                            map.put("loc_phone", datas.getString("loc_phone"));
                            map.put("photourl", datas.getString("photourl"));
                            map.put("status", datas.getString("status"));


                            pal_datas.add(map);

                        }


                        Adapter_PalLists staff_adapter = new Adapter_PalLists(MyPals.this, pal_datas, count);

                        lv_mypals.setAdapter(staff_adapter);
                    } else {

                    }

                } else {

                    new SweetAlertDialog(MyPals.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Oops!")
                            .setContentText("Try Check your Network")
                            .setConfirmText("OK")
                            .show();

                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.palmenu, menu);
      //  MenuItem pinMenuItem1 = menu.getItem(0).getSubMenu().getItem(0);
       // MenuItem pinMenuItem2 = menu.getItem(0).getSubMenu().getItem(1);

        // applyFontToMenuItem(pinMenuItem1);
        //applyFontToMenuItem(pinMenuItem2);





        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.request: {
                Intent i = new Intent(getApplicationContext(), PendingbyMe.class);
                startActivity(i);
                return true;
            }

            case R.id.pending: {
                Intent i = new Intent(getApplicationContext(), PendingbyPal.class);
                startActivity(i);
                return true;
            }


            default: {
                return true;
            }
        }
    }


}
