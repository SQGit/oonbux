package sqindia.net.oonbux;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.rey.material.widget.Button;
import com.rey.material.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Salman on 6/30/2016.
 */
//adsf
public class Pal_Search extends Fragment {

    ListView lv_palsearch;
    SweetAlertDialog sweetDialog;
    String str_sessionid, data;
    HashMap<String, String> map;
    ArrayList<HashMap<String, String>> pal_datas;
    Adapter_PalLists staff_adapter;
    Button btn_submit;
    EditText et_searchtxt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.pal_search, container, false);

        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        str_sessionid = sharedPreferences.getString("sessionid", "");

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/prox.otf");

        lv_palsearch = (ListView) v.findViewById(R.id.lview);


        btn_submit = (Button) getActivity().findViewById(R.id.button_submit);
        et_searchtxt = (EditText) getActivity().findViewById(R.id.et_searchtxt);


        btn_submit.setTypeface(tf);
        et_searchtxt.setTypeface(tf);


        pal_datas = new ArrayList<>();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(et_searchtxt.getText().toString().isEmpty())) {
                    data = et_searchtxt.getText().toString();

                    new getSearchData().execute();
                }
            }
        });


        return v;
    }


    class getSearchData extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            sweetDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
            sweetDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            sweetDialog.setTitleText("Loading");
            sweetDialog.setCancelable(false);
            sweetDialog.show();
        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";


            try {

                String virtual_url = Config.SER_URL + "pal/search";
                Log.e("tag", "" + data);

                //JSONObject jsonobject = HttpUtils.makeRequest34(virtual_url,str_palkey, str_sessionid);


                JSONObject jsonobject = new JSONObject();
                jsonobject.accumulate("keyword", data);
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

                    et_searchtxt.setText("");
                    pal_datas = new ArrayList<>();
                    map = new HashMap<String, String>();


                    if (count > 0) {


                        String subd = jo.getString("result");

                        JSONArray friends_list = jo.getJSONArray("result");
                        Log.e("tag", "<-----result----->" + "" + friends_list);

                        map = new HashMap<String, String>();
                        map.clear();
                        pal_datas.clear();

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


                        staff_adapter = new Adapter_PalLists(getActivity(), pal_datas, count,3);
                        staff_adapter.notifyDataSetChanged();
                        lv_palsearch.setAdapter(staff_adapter);
                    }

                    else {


                        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Oops!")
                                .setContentText("No data Found!")
                                .setConfirmText("OK")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                })
                                .show();

                    }

                }
                else {

                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
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