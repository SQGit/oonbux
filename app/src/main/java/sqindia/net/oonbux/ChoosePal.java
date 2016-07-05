package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rey.material.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Salman on 5/18/2016.
 */
public class ChoosePal extends Activity {

    com.rey.material.widget.LinearLayout lt_back, lt_add;
    ImageButton btn_next, btn_addpal;
    MaterialAutoCompleteTextView met_palnames;
    String[] palnames;
    ListView lview;
    TextView tv_header;
    SweetAlertDialog sweetDialog;
    ChoosePal_Adapter adapter1;
    ArrayList<String> pals = new ArrayList<String>();
    String str_sessionid;
    HashMap<String, String> map ;
    ArrayList<HashMap<String, String>> pal_datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosepal);


        palnames = new String[]{"samson", "wincent", "ariz", "bonz", "criz", "dobrain"};


        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ChoosePal.this);
        str_sessionid = sharedPreferences.getString("sessionid", "");


        lt_back = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_back);
        lt_add = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_add);

        btn_next = (ImageButton) findViewById(R.id.button_next);
        btn_addpal = (ImageButton) findViewById(R.id.button_add_pal);

        lview = (ListView) findViewById(R.id.lview);

        tv_header = (TextView) findViewById(R.id.tv_hd_txt);

        met_palnames = (MaterialAutoCompleteTextView) findViewById(R.id.edittext_pal_names);


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");


        tv_header.setTypeface(tf);


        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_lists, R.id.text_spin, palnames);
        met_palnames.setAdapter(adapter);*/

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), ChooseAddress.class);
                startActivity(inte);
            }
        });

        lt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), AddPal.class);
                startActivity(inte);
            }
        });


        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                startActivity(inte);*/
                onBackPressed();
            }
        });


        // met_palnames.addTextChangedListener(palnamewatcher);


        met_palnames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = met_palnames.getText().toString();

                /*adapter1 = new ChoosePal_Adapter(getApplicationContext(), name);
                lview.setAdapter(adapter1);*/
            }
        });


        new getPalLists().execute();


    }








    class getPalLists extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            sweetDialog = new SweetAlertDialog(ChoosePal.this, SweetAlertDialog.PROGRESS_TYPE);
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
                    new SweetAlertDialog(ChoosePal.this, SweetAlertDialog.WARNING_TYPE)
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

                            pals.add(datas.getString("firstname")+" "+datas.getString("lastname"));


                            pal_datas.add(map);

                        }


                        Adapter_PalLists staff_adapter = new Adapter_PalLists(ChoosePal.this, pal_datas, count,0);

                        lview.setAdapter(staff_adapter);


                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_lists, R.id.text_spin, pals);
                        met_palnames.setAdapter(adapter);



                    } else {

                    }

                } else {

                    new SweetAlertDialog(ChoosePal.this, SweetAlertDialog.WARNING_TYPE)
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

















       /* private final TextWatcher palnamewatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String name = met_palnames.getText().toString();

                adapter = new ChoosePal_Adapter(getApplicationContext(),name);
                lview.setAdapter(adapter);

            }

        };*/





}
