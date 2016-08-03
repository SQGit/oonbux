package sqindia.net.oonbux.Chat;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import sqindia.net.oonbux.Adapter.Adapter_PalLists;
import sqindia.net.oonbux.config.Config;
import sqindia.net.oonbux.Activity.DashBoardActivity;
import sqindia.net.oonbux.Dialog.Dialog_Msg;
import sqindia.net.oonbux.config.HttpUtils;
import sqindia.net.oonbux.R;


public class Pal_Chat_List extends Activity {

    ListView lv_mypals;
    String str_sessionid;
    HashMap<String, String> map ;
    ArrayList<HashMap<String, String>> pal_datas;
    com.rey.material.widget.LinearLayout lt_back;
    Dialog loading_dialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pal_chat_list);


        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        str_sessionid = sharedPreferences.getString("sessionid", "");

        lv_mypals = (ListView) findViewById(R.id.lview);
        lt_back = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_back);

        pal_datas = new ArrayList<>();
        //map = new HashMap<String, String>();


        pal_datas.clear();

        new getPalLists().execute();


        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                startActivity(inte);
                finish();
            }
        });



    }





    class getPalLists extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            loading_dialog = new Dialog(Pal_Chat_List.this);
            loading_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            loading_dialog.setContentView(R.layout.dialog_loading);
            loading_dialog.setCancelable(false);
            loading_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            loading_dialog.show();
        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";


            try {

                String virtual_url = Config.SER_URL + "pal/friends";
                Log.e("tag",""+virtual_url);

                JSONObject jsonobject = HttpUtils.getPalLists(virtual_url, str_sessionid);

                Log.d("tag", "" + jsonobject.toString());

                if (jsonobject.toString() == "sam") {
                    Dialog_Msg dialog_fail = new Dialog_Msg(Pal_Chat_List.this, "Network Error.\nTry Again Later");
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
                int count = Integer.valueOf(jo.getString("count"));

                Log.e("tag", "<-----count---->" + count);

                if (status.equals("success")) {

                    if (count > 0) {


                        String subd = jo.getString("friends");

                        JSONArray friends_list = jo.getJSONArray("friends");
                        Log.e("tag", "<-----friends_list-chat---->" + "" + friends_list);


                        map = new HashMap<String, String>();
                        pal_datas.clear();
                        map.clear();

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

                            Log.e("tag",""+map.get("firstname"));

                            pal_datas.add(map);

                           // map.clear();

                        }

                        for(int i = 0;i <pal_datas.size();i++){
                            Log.e("tag",""+pal_datas.get(i));

                        }
                        Adapter_PalLists staff_adapter = new Adapter_PalLists(Pal_Chat_List.this,getApplicationContext(), pal_datas, count,4);

                        lv_mypals.setAdapter(staff_adapter);
                    } else {

                    }

                } else {

                    Dialog_Msg dialog_fail = new Dialog_Msg(Pal_Chat_List.this, "Try Again Later");
                    dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog_fail.show();

                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }
    
    
    
    
    
    
    
    
    
    
}
