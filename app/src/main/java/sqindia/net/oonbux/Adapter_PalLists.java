package sqindia.net.oonbux;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.rey.material.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Salman on 6/27/2016.
 */
public class Adapter_PalLists extends BaseAdapter {
    Context context;
    HashMap<String, String> data = new HashMap<>();
    int count;
    TextView tv_name,tv_oonbuxid,tv_mail,tv_phone,tv_name_txt,tv_oonbuxid_txt,tv_mail_txt,tv_phone_txt;
    ImageView img_view;
    ArrayList<HashMap<String, String>> datas = new ArrayList<>();
    HashMap<String, String> result = new HashMap<String, String>();
    SweetAlertDialog sweetDialog;
    String str_oonbux_id,str_sessionid;

    public Adapter_PalLists(Context cont, ArrayList<HashMap<String, String>> dat, int k) {
        this.context = cont;
        this.datas = dat;
        this.count = k;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflat = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflat.inflate(R.layout.pal_list_adapter, null);

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/prox.otf");

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        str_sessionid = sharedPreferences.getString("sessionid", "");

        result = datas.get(position);

        tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        tv_name_txt = (TextView) convertView.findViewById(R.id.tv_name_txt);
        tv_oonbuxid = (TextView) convertView.findViewById(R.id.tv_oonbuxid);
        tv_oonbuxid_txt = (TextView) convertView.findViewById(R.id.tv_oonbuxid_txt);
        tv_mail = (TextView) convertView.findViewById(R.id.tv_mail);
        tv_mail_txt = (TextView) convertView.findViewById(R.id.tv_mail_txt);
        tv_phone = (TextView) convertView.findViewById(R.id.tv_phone);
        tv_phone_txt = (TextView) convertView.findViewById(R.id.tv_phone_txt);
        img_view = (ImageView) convertView.findViewById(R.id.imgview);

        tv_name.setTypeface(tf);
        tv_name_txt.setTypeface(tf);
        tv_oonbuxid.setTypeface(tf);
        tv_oonbuxid_txt.setTypeface(tf);
        tv_mail.setTypeface(tf);
        tv_mail_txt.setTypeface(tf);
        tv_phone.setTypeface(tf);
        tv_phone_txt.setTypeface(tf);


        tv_name.setText(result.get("firstname")+" "+result.get("lastname"));
        tv_oonbuxid.setText(result.get("oonbux_id"));
        tv_mail.setText(result.get("email"));
        tv_phone.setText(result.get("loc_phone"));




        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_oonbux_id = result.get("oonbux_id");
                new sendRequestPal().execute();
            }
        });


        return convertView;
    }






    class sendRequestPal extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            sweetDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            sweetDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            sweetDialog.setTitleText("Loading");
            sweetDialog.setCancelable(false);
            sweetDialog.show();
        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";


            try {

                String virtual_url = Config.SER_URL + "pal/request";
                Log.e("tag", "" + virtual_url);

                //JSONObject jsonobject = HttpUtils.makeRequest34(virtual_url,str_palkey, str_sessionid);


                JSONObject jsonobject = new JSONObject();
                jsonobject.accumulate("oonbux_id", str_oonbux_id);
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

                    /*int count = Integer.valueOf(jo.getString("count"));

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


                        Adapter_PalLists staff_adapter = new Adapter_PalLists(context, pal_datas, count);

                        lv_pallists.setAdapter(staff_adapter);
                    } else {

                    }
*/
                } else {

                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
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
