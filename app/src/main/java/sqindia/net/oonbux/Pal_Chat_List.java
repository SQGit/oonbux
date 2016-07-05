package sqindia.net.oonbux;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Salman on 7/1/2016.
 */
public class Pal_Chat_List extends Activity {

    ListView lv_mypals;
    SweetAlertDialog sweetDialog;
    String str_sessionid;
    HashMap<String, String> map ;
    ArrayList<HashMap<String, String>> pal_datas;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pal_chat_list);


        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        str_sessionid = sharedPreferences.getString("sessionid", "");

        lv_mypals = (ListView) findViewById(R.id.lview);

        pal_datas = new ArrayList<>();
        map = new HashMap<String, String>();

        new getPalLists().execute();
    }





    class getPalLists extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            sweetDialog = new SweetAlertDialog(Pal_Chat_List.this, SweetAlertDialog.PROGRESS_TYPE);
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


                            map.put("oonbux_id", datas.getString("oonbux_id"));
                            map.put("firstname", datas.getString("firstname"));
                            map.put("lastname", datas.getString("lastname"));
                            map.put("email", datas.getString("email"));
                            map.put("loc_phone", datas.getString("loc_phone"));
                            map.put("photourl", datas.getString("photourl"));
                            map.put("status", datas.getString("status"));


                            pal_datas.add(map);

                        }


                        Adapter_PalLists staff_adapter = new Adapter_PalLists(getApplicationContext(), pal_datas, count,4);

                        lv_mypals.setAdapter(staff_adapter);
                    } else {

                    }

                } else {

                    new SweetAlertDialog(Pal_Chat_List.this, SweetAlertDialog.WARNING_TYPE)
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
