package sqindia.net.oonbux;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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
 * Created by Salman on 6/28/2016.
 */
public class PendingbyMe extends Activity {


    String str_palkey, str_sessionid;
    SweetAlertDialog sweetDialog;
    HashMap<String, String> map;
    ListView lv_pallists;
    ArrayList<HashMap<String, String>> pal_datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pendingbyme);


        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(PendingbyMe.this);
        str_sessionid = sharedPreferences.getString("sessionid", "");


        lv_pallists = (ListView) findViewById(R.id.lview);
        pal_datas = new ArrayList<>();

        new getSearchData().execute();
    }




    class getSearchData extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            sweetDialog = new SweetAlertDialog(PendingbyMe.this, SweetAlertDialog.PROGRESS_TYPE);
            sweetDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            sweetDialog.setTitleText("Loading");
            sweetDialog.setCancelable(false);
            sweetDialog.show();
        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";


            try {

                String virtual_url = Config.SER_URL + "pal/requestpendingbyme";


                JSONObject jsonobject = HttpUtils.getVirtual(virtual_url, str_sessionid);

                Log.d("tag", "" + jsonobject.toString());

                if (jsonobject.toString() == "sam") {
                    new SweetAlertDialog(PendingbyMe.this, SweetAlertDialog.WARNING_TYPE)
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


                if (status.equals("success")) {

                    int count = Integer.valueOf(jo.getString("count"));

                    Log.e("tag", "<-----count---->" + count);

                    if (count > 0) {


                        String subd = jo.getString("pendingbyme");

                        JSONArray friends_list = jo.getJSONArray("pendingbyme");
                        Log.e("tag", "<-----result----->" + "" + friends_list);

                        map = new HashMap<String, String>();
                        map.clear();
                        pal_datas.clear();


                        for (int i = 0; i < friends_list.length(); i++) {

                            JSONObject datas = friends_list.getJSONObject(i);



                            map.put("oonbux_id", datas.getString("oonbux_id"));
                            map.put("firstname", datas.getString("firstname"));
                            map.put("lastname", datas.getString("lastname"));
                            map.put("email", datas.getString("email"));
                            map.put("loc_phone", datas.getString("loc_phone"));
                            map.put("photourl", datas.getString("photourl"));
                            map.put("status", datas.getString("status"));

                            pal_datas.add(map);

                        }


                        Adapter_PalLists staff_adapter = new Adapter_PalLists(PendingbyMe.this, pal_datas, count,1);
                        staff_adapter.notifyDataSetChanged();
                        lv_pallists.setAdapter(staff_adapter);

                    } else {

                    }

                } else {

                    new SweetAlertDialog(PendingbyMe.this, SweetAlertDialog.WARNING_TYPE)
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
