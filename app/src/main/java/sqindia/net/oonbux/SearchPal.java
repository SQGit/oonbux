package sqindia.net.oonbux;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Salman on 6/27/2016.
 */
public class SearchPal extends Activity {

    Button btn_submit;
    MaterialEditText et_searchtxt;
    String str_palkey, str_sessionid;
    SweetAlertDialog sweetDialog;
    HashMap<String, String> map;
    ListView lv_pallists;
    ArrayList<HashMap<String, String>> pal_datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchpal);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SearchPal.this);
        str_sessionid = sharedPreferences.getString("sessionid", "");

        et_searchtxt = (MaterialEditText) findViewById(R.id.edittext_pal_name);
        btn_submit = (Button) findViewById(R.id.button_submit);
        lv_pallists = (ListView) findViewById(R.id.lview);

        pal_datas = new ArrayList<>();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_palkey = et_searchtxt.getText().toString();

                new getSearchData().execute();
            }
        });


    }


    class getSearchData extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            sweetDialog = new SweetAlertDialog(SearchPal.this, SweetAlertDialog.PROGRESS_TYPE);
            sweetDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            sweetDialog.setTitleText("Loading");
            sweetDialog.setCancelable(false);
            sweetDialog.show();
        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";


            try {

                String virtual_url = Config.SER_URL + "pal/search";
                Log.e("tag", "" + virtual_url);

                //JSONObject jsonobject = HttpUtils.makeRequest34(virtual_url,str_palkey, str_sessionid);


                JSONObject jsonobject = new JSONObject();
                jsonobject.accumulate("keyword", str_palkey);
                // 4. convert JSONObject to JSON to String
                Log.e("tag", "" + jsonobject.toString());
                json = jsonobject.toString();

                return jsonStr = HttpUtils.makeRequest34(virtual_url, json, str_sessionid);
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


                if (status.equals("success")) {

                    int count = Integer.valueOf(jo.getString("count"));

                    Log.e("tag", "<-----count---->" + count);

                    if (count > 0) {


                        String subd = jo.getString("result");

                        JSONArray friends_list = jo.getJSONArray("result");
                        Log.e("tag", "<-----result----->" + "" + friends_list);


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


                        Adapter_PalLists staff_adapter = new Adapter_PalLists(SearchPal.this, pal_datas, count);

                        lv_pallists.setAdapter(staff_adapter);
                    } else {

                    }

                } else {

                    new SweetAlertDialog(SearchPal.this, SweetAlertDialog.WARNING_TYPE)
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


}
