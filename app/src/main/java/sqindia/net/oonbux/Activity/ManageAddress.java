package sqindia.net.oonbux.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.rey.material.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sqindia.net.oonbux.Dialog.Dialog_Anim_Loading;
import sqindia.net.oonbux.Profile.ProfilePhysicalDeliveryAddress;
import sqindia.net.oonbux.config.Config;
import sqindia.net.oonbux.Dialog.Dialog_Add_Address;
import sqindia.net.oonbux.Dialog.Dialog_Msg;
import sqindia.net.oonbux.Dialog.Dialog_new;
import sqindia.net.oonbux.config.HttpUtils;
import sqindia.net.oonbux.R;
import sqindia.net.oonbux.Adapter.listview_adapter;


/**
 * Created by Salman on 6/21/2016.
 */
//asdf
public class ManageAddress extends Activity {

    ExpandableListView elv_addresList;
    listview_adapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    HashMap<String, List<String>> expandableListDetail1;
    String str_session_id;
    ImageView lt_add, lt_back;
    TextView tv_header;
    HashMap<String, List<String>> list_datas = new HashMap<String, List<String>>();
    ArrayList<String> daa = new ArrayList<>();
   // Dialog loading_dialog;

    Dialog_Anim_Loading dialog_loading;


    HashMap<String, List<String>> list_datas1 = new HashMap<String, List<String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_address);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ManageAddress.this);

        str_session_id = sharedPreferences.getString("sessionid", "");

        Log.d("tag", "" + str_session_id);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");

        lt_back = (ImageView) findViewById(R.id.layout_back);
        lt_add = (ImageView) findViewById(R.id.layout_add);
        tv_header = (TextView) findViewById(R.id.tv_hd_txt);
        elv_addresList = (ExpandableListView) findViewById(R.id.elv_addr);

        tv_header.setTypeface(tf);


        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), ProfilePhysicalDeliveryAddress.class);
                startActivity(inte);
                finish();
            }
        });


        lt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog_Add_Address cdd = new Dialog_Add_Address(ManageAddress.this, 0, daa);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();

            }
        });





        if (!Config.isNetworkAvailable(ManageAddress.this)) {

            Dialog_new dialog_wifi_settings = new Dialog_new(ManageAddress.this, "No Network Avaliable!\nGo to Settings.", 5);
            dialog_wifi_settings.setCancelable(false);
            dialog_wifi_settings.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog_wifi_settings.show();


        } else {
            new getAddress().execute();

        }



    }


    class getAddress extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();


            dialog_loading = new Dialog_Anim_Loading(ManageAddress.this);
            dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
            dialog_loading.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog_loading.setCancelable(false);
            dialog_loading.show();
            WindowManager.LayoutParams lp = dialog_loading.getWindow().getAttributes();
            lp.dimAmount=1.80f;
            dialog_loading.getWindow().setAttributes(lp);
            dialog_loading.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";


            try {

                String aditional_addr_url = Config.SER_URL + "addshippingaddr/fetch";

                JSONObject jsonobject = HttpUtils.getVirtual(aditional_addr_url, str_session_id);

                Log.d("tag", "" + jsonobject.toString());

                if (jsonobject.toString() == "sam") {
                    Dialog_Msg dialog_fail = new Dialog_Msg(ManageAddress.this, "Try Again Later");
                    dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog_fail.show();
                }

                json = jsonobject.toString();

                return json;
            } catch (Exception e) {
                Log.e("InputStream", "" + e.getLocalizedMessage());
                jsonStr = "";
                dialog_loading.dismiss();
            }
            return jsonStr;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.e("tag", "<-----rerseres---->" + jsonStr);
            super.onPostExecute(jsonStr);
            dialog_loading.dismiss();


            try {

                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");
                int count = Integer.valueOf(jo.getString("count"));



                if (status.equals("success")) {

                    if(count > 0){

                    JSONArray additional_address = jo.getJSONArray("local");

                    Log.d("tag", "<-----address----->" + "" + additional_address);


                    for (int i = 0; i < additional_address.length(); i++) {
                        JSONObject datas = additional_address.getJSONObject(i);
                        Log.d("tag", "<----Location_address-----> " + i + " " + datas);
                        String address_id = datas.getString("addr_id");
                        String address_line1 = datas.getString("addr_line1");
                        String address_line2 = datas.getString("addr_line2");
                        String address_city = datas.getString("addr_city");
                        String address_state = datas.getString("addr_state");
                        String address_zip = datas.getString("addr_zip");
                        String address_country = datas.getString("addr_country");
                        String address_note = datas.getString("delivery_note");
                        String address_phone = datas.getString("addr_phone");
                        String address_type = datas.getString("addr_type");


                        Log.d("tag", "<--> " + i + "" + address_id + address_line1 + address_line2 + address_city + address_state + address_country + address_note + address_phone + address_type);


                        List<String> listitem = new ArrayList<String>();
                        List<String> address_headers = new ArrayList<>();

                        listitem.add(address_id);
                        address_headers.add("Address Id : "+address_id);
                        listitem.add(address_line1);
                        address_headers.add("Address Line 1 : "+address_line1);
                        listitem.add(address_line2);
                        address_headers.add("Address Line 2 : "+address_line2);
                        listitem.add(address_country);
                        address_headers.add("Country : "+address_country);
                        listitem.add(address_state);
                        address_headers.add("State : "+address_state);
                        listitem.add(address_city);
                        address_headers.add("City : "+address_city);
                        listitem.add(address_zip);
                        address_headers.add("zip  : "+address_zip);
                        listitem.add(address_note);
                        address_headers.add("Note : : "+address_note);
                        listitem.add(address_phone);
                        address_headers.add("Phone : "+address_phone);
                        listitem.add(address_type);
                        address_headers.add("Address Type : "+address_type);


                        list_datas.put(address_city + ", " + address_zip + " - " + address_country, listitem);

                        list_datas1.put(address_zip + " - " + address_country, address_headers);

                    }
                    }
                    else {

                        Dialog_Add_Address cdd = new Dialog_Add_Address(ManageAddress.this, 0, daa);
                        cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        cdd.show();


                    }

                    expandableListDetail = list_datas;
                    expandableListDetail1 = list_datas1;

                    expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
                    expandableListAdapter = new listview_adapter(ManageAddress.this, getApplicationContext(), expandableListTitle, expandableListDetail,expandableListDetail1);
                    elv_addresList.setAdapter(expandableListAdapter);


                }

                else {
                    Dialog_Msg dialog_fail = new Dialog_Msg(ManageAddress.this, "Try Again Later");
                    dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog_fail.show();

                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

                Dialog_Msg dialog_fail = new Dialog_Msg(ManageAddress.this, "Try Again Later");
                dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog_fail.show();
            }

        }

    }


}
