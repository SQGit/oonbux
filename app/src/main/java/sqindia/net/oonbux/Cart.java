package sqindia.net.oonbux;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;
import com.rey.material.widget.Button;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import sqindia.net.oonbux.Activity.ChooseAddress;
import sqindia.net.oonbux.Activity.Dashboard;
import sqindia.net.oonbux.config.Config;
import sqindia.net.oonbux.config.DbC;
import sqindia.net.oonbux.config.DbGclass;
import sqindia.net.oonbux.config.HttpUtils;

//asdfas
public class Cart extends Activity {

    ListView list;
    private CartData mAdapter;
    DbC dbclass;
    Context context = this;
    ArrayList<String> selectedPhotos = new ArrayList<>();
    public final static int REQUEST_CODE = 1;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private SQLiteDatabase db;
    public ArrayList<String> shipment_photos;
    ImageButton btn_next;
    TextView header;
    ImageView lt_back, lt_add;
    Button btn_delete, btn_addpal, btn_continue, btn_yes, btn_no, btn_ok;

    public ArrayList<String> ship_size = new ArrayList<>();
    public ArrayList<String> ship_pickup = new ArrayList<>();
    public ArrayList<String> ship_photo = new ArrayList<>();

    public String cartss, str_sessionid;
    public ArrayList<String> choose_cart = new ArrayList<>();


    public ArrayList<String> pal_lists = new ArrayList<>();
    ArrayList<HashMap<String, String>> pal_datas;
    HashMap<String, String> result = new HashMap<String, String>();

    Dialog dialog_Pickup, dialog_addpal;
    Typeface tf, tf1;
    HashMap<String, String> map;
    TextView tv_loader;
    ProgressBar di_progress;
    ListView lview;
    CheckBox ck_pikup;
    TextView tv_add_pickup;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surfaceview);

        shipment_photos = new ArrayList<>();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Cart.this);

        str_sessionid = sharedPreferences.getString("sessionid", "");

        String size = sharedPreferences.getString("ship_size", "");
        String pickup = sharedPreferences.getString("ship_pickup", "");
        String photo = sharedPreferences.getString("shipment_photo", "");

        list = (ListView) findViewById(R.id.listview);

        header = (TextView) findViewById(R.id.tv_hd_txt);
        lt_back = (ImageView) findViewById(R.id.layout_back);
        lt_add = (ImageView) findViewById(R.id.layout_add);
        btn_continue = (Button) findViewById(R.id.button_continue);
        //btn_next = (ImageButton) findViewById(R.id.button_next);
        btn_addpal = (Button) findViewById(R.id.button_addpal);

        pal_datas = new ArrayList<>();
//        mAdapter.setMode(Attributes.Mode.Single);

        dbclass = new DbC(context);

        tf = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");


        tf1 = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");

        header.setTypeface(tf);
        btn_addpal.setTypeface(tf1);
        btn_continue.setTypeface(tf1);

        // getFromDB();

        ship_size.add(size);
        ship_photo.add(photo);

        new GetCart_task().execute();


        btn_addpal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor = sharedPreferences.edit();
                editor.putString("shipment_photo", "");
                editor.commit();

                if (mAdapter.choose_cart_from.size() > 0) {


                    dialog_addpal = new Dialog(Cart.this);
                    dialog_addpal.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog_addpal.setContentView(R.layout.dialog_pal_payment);
                    dialog_addpal.setCancelable(true);


                    TextView tv_header = (TextView) dialog_addpal.findViewById(R.id.tv_header);
                    tv_add_pickup = (TextView) dialog_addpal.findViewById(R.id.tv_add_pickup);
                    ck_pikup = (CheckBox) dialog_addpal.findViewById(R.id.checkbox_pickup);
                    btn_yes = (Button) dialog_addpal.findViewById(R.id.button_yes);
                    lview = (ListView) dialog_addpal.findViewById(R.id.lview);
                    btn_no = (Button) dialog_addpal.findViewById(R.id.button_no);
                    btn_ok = (Button) dialog_addpal.findViewById(R.id.button_ok);

                    tv_loader = (TextView) dialog_addpal.findViewById(R.id.tv_data);
                    di_progress = (ProgressBar) dialog_addpal.findViewById(R.id.progress);

                    tv_header.setTypeface(tf1);
                    tv_add_pickup.setTypeface(tf1);
                    btn_yes.setTypeface(tf1);
                    btn_no.setTypeface(tf1);
                    tv_loader.setTypeface(tf1);
                    btn_ok.setTypeface(tf1);


                    tv_loader.setVisibility(View.GONE);
                    lview.setVisibility(View.GONE);
                    btn_yes.setVisibility(View.GONE);
                    btn_no.setVisibility(View.GONE);
                    btn_ok.setVisibility(View.GONE);

                    new getPalLists().execute();


                    // ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),R.layout.dropdown_lists,R.id.text_spin,pal_lists);


                    btn_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog_addpal.dismiss();
                            if(ck_pikup.isChecked()){

                                dialog_addpal.dismiss();
                                Intent newe = new Intent(getApplicationContext(), ChooseAddress.class);
                                newe.putExtra("pickup", "yes");
                                startActivity(newe);
                            }
                            else{

                                dialog_addpal.dismiss();
                                Intent newe = new Intent(getApplicationContext(), ChooseAddress.class);
                                newe.putExtra("pickup", "no");
                                startActivity(newe);

                            }
                        }
                    });

                    btn_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog_addpal.dismiss();
                          /*  Intent newe = new Intent(getApplicationContext(), ChooseAddress.class);
                            startActivity(newe);*/
                        }
                    });

                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(ck_pikup.isChecked()){

                                dialog_addpal.dismiss();
                                Intent newe = new Intent(getApplicationContext(), ChooseAddress.class);
                                newe.putExtra("pickup", "yes");
                                startActivity(newe);
                            }
                            else{

                                dialog_addpal.dismiss();
                                Intent newe = new Intent(getApplicationContext(), ChooseAddress.class);
                                newe.putExtra("pickup", "no");
                                startActivity(newe);

                            }
                        }
                    });


                    ck_pikup.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            Log.e("tag", "worked");
                        }
                    });


                    dialog_addpal.show();

                } else {
                    Toast.makeText(getApplicationContext(), "Please choose Any item", Toast.LENGTH_SHORT).show();
                }
            }
        });


        lt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("ship_pickup", "no");
                editor.commit();

                shipment_photos.clear();
                PhotoPickerIntent intent = new PhotoPickerIntent(getApplicationContext());
                intent.setPhotoCount(1);
                intent.setColumn(4);
                intent.setShowCamera(true);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor = sharedPreferences.edit();
                editor.putString("shipment_photo", "");
                editor.commit();

                if (mAdapter.choose_cart_from.size() > 0) {
                    Log.e("tag", "" + "\n" + mAdapter.choose_cart_from.size());


                    dialog_Pickup = new Dialog(Cart.this);
                    dialog_Pickup = new Dialog(Cart.this);
                    dialog_Pickup.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog_Pickup.setContentView(R.layout.dialog_pal_payment);
                    dialog_Pickup.setCancelable(true);


                    TextView tv_header = (TextView) dialog_Pickup.findViewById(R.id.tv_header);
                    tv_add_pickup = (TextView) dialog_Pickup.findViewById(R.id.tv_add_pickup);
                    ck_pikup = (CheckBox) dialog_Pickup.findViewById(R.id.checkbox_pickup);
                    btn_yes = (Button) dialog_Pickup.findViewById(R.id.button_yes);
                    lview = (ListView) dialog_Pickup.findViewById(R.id.lview);
                    btn_no = (Button) dialog_Pickup.findViewById(R.id.button_no);
                    btn_ok = (Button) dialog_Pickup.findViewById(R.id.button_ok);

                    tv_loader = (TextView) dialog_Pickup.findViewById(R.id.tv_data);
                    di_progress = (ProgressBar) dialog_Pickup.findViewById(R.id.progress);

                    tv_header.setTypeface(tf1);
                    tv_add_pickup.setTypeface(tf1);
                    btn_yes.setTypeface(tf1);
                    btn_no.setTypeface(tf1);
                    tv_loader.setTypeface(tf1);
                    btn_ok.setTypeface(tf1);


                    tv_header.setVisibility(View.GONE);
                    lview.setVisibility(View.GONE);
                    btn_ok.setVisibility(View.GONE);
                    lview.setVisibility(View.GONE);
                    tv_add_pickup.setVisibility(View.GONE);
                    ck_pikup.setVisibility(View.GONE);
                    di_progress.setVisibility(View.GONE);

                    tv_loader.setText("Do you want to Add this Packages to Pickup?");


                    btn_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog_Pickup.dismiss();
                            Intent newe = new Intent(getApplicationContext(), ChooseAddress.class);
                            newe.putExtra("pickup", "yes");
                            startActivity(newe);
                        }
                    });

                    btn_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog_Pickup.dismiss();
                            Intent newe = new Intent(getApplicationContext(), ChooseAddress.class);
                            newe.putExtra("pickup", "no");
                            startActivity(newe);
                        }
                    });


                    dialog_Pickup.show();
                } else {
                    Log.e("tag", "" + "\n" + mAdapter.choose_cart_from.size());
                    Toast.makeText(getApplicationContext(), "Please choose Any item", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void getFromDB() {
        ArrayList<DbGclass> dbdatalist = new ArrayList<DbGclass>();
        dbdatalist.clear();
        String query = " SELECT * FROM cart";
        Cursor c = dbclass.fetchdata(query);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    DbGclass dbc = new DbGclass();
                    dbc.set_id(String.valueOf(c.getInt(c.getColumnIndex("id"))));
                    dbc.set_size(c.getString(c.getColumnIndex("size")));
                    dbc.set_pickup(c.getString(c.getColumnIndex("pickup")));
                    dbc.set_photo(c.getString(c.getColumnIndex("photo")));
                    dbc.set_cost(c.getString(c.getColumnIndex("cost")));
                    dbdatalist.add(dbc);
                } while (c.moveToNext());
            }
        }
        mAdapter = new CartData(Cart.this, dbdatalist, "");
        list.setAdapter(mAdapter);
        mAdapter.setMode(Attributes.Mode.Single);
    }


    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        //dbclass.del_last_row();
        Intent inte = new Intent(getApplicationContext(), Dashboard.class);
        startActivity(inte);
        finish();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<String> photos = null;
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();
            if (photos != null) {
                selectedPhotos.addAll(photos);
            }
            Uri uri = Uri.fromFile(new File(selectedPhotos.get(0)));
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("shipment_photo1", "yes");
            editor.putString("shipment_photo", selectedPhotos.get(0));
            editor.commit();
            shipment_photos.add(selectedPhotos.get(0));
            Intent inte = new Intent(getApplicationContext(), Dashboard.class);
            inte.putExtra("add", "yes");
            startActivity(inte);
        }
    }


    public class PalListAdapter extends ArrayAdapter<HashMap<String, String>> {

        int resource;
        ArrayList<String> list_datas;
        ImageView img_view;
        TextView tv_name, tv_oonbuxid, tv_oonbuxid_txt;
        CheckBox cb_pal;
        ArrayList<HashMap<String, String>> datas = new ArrayList<>();
        HashMap<String, String> result = new HashMap<String, String>();

        private int selectedPosition = -1;

        public PalListAdapter(Context context, ArrayList<HashMap<String, String>> dat) {
            super(context, R.layout.pal_lists, dat);
            this.datas = dat;

        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();


        }

        public View getCustomView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();


            if (convertView == null) {
                convertView = inflater.inflate(R.layout.pal_lists, parent, false);
            } else {

            }


            Log.e("tag", "" + datas.size());
            result = datas.get(position);

            tv_name = (TextView) convertView.findViewById(R.id.tv_name);

            tv_oonbuxid = (TextView) convertView.findViewById(R.id.tv_oonbuxid);
            tv_oonbuxid_txt = (TextView) convertView.findViewById(R.id.tv_oonbuxid_txt);

            img_view = (ImageView) convertView.findViewById(R.id.imgview);
            cb_pal = (CheckBox) convertView.findViewById(R.id.cb_pal);

            tv_name.setTypeface(tf1);
            tv_oonbuxid.setTypeface(tf1);
            tv_oonbuxid_txt.setTypeface(tf1);


            if (position == selectedPosition) {
                cb_pal.setChecked(true);
            } else {
                cb_pal.setChecked(false);
            }



           /* cb_pal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 *//*  if(cb_pal.isChecked()){
                       selectedPosition = position;

                   }
                    else{
                       selectedPosition = -1;

                   }*//*
                    selectedPosition = position;
                    notifyDataSetChanged();
                    cb_pal.setChecked(true);
                }
            });*/


            cb_pal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        selectedPosition = position;

                    } else {
                        selectedPosition = -1;

                    }
                    notifyDataSetChanged();
                }
            });

          /*  cb_pal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        Log.e("tag", "ins" + position + isChecked);
                        selectedPosition = position;
                        notifyDataSetChanged();
                        cb_pal.setChecked(true);

                    }
                    else{
                        Log.e("tag", "ins" + position + isChecked);
                        selectedPosition = -1;
                       notifyDataSetChanged();

                    }

                }
            });*/

            String asd = result.get("firstname") + " " + result.get("lastname");


            tv_name.setText(asd.toUpperCase());
            tv_oonbuxid.setText(result.get("oonbux_id"));


            return convertView;
        }
    }


    class GetCart_task extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            try {
                String virtual_url = Config.SER_URL + "cart/fetch";
                JSONObject jsonobject = HttpUtils.logout(virtual_url, str_sessionid);
                Log.e("tag_", "0" + jsonobject.toString());
                if (jsonobject.toString() == "sam") {
                    Log.e("tag_", "1" + jsonobject.toString());
                }
                json = jsonobject.toString();
                return json;
            } catch (Exception e) {
                Log.e("InputStream", "2" + e.toString());
                jsonStr = "";
            }
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.e("tag_", "<-----rerseres---->" + jsonStr);
            super.onPostExecute(jsonStr);
            try {
                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");

                if (status.equals("success")) {
                    int count = Integer.valueOf(jo.getString("count"));

                    if (count > 0) {

                        ArrayList<DbGclass> dbdatalist = new ArrayList<DbGclass>();
                        dbdatalist.clear();

                        JSONArray jsara = jo.getJSONArray("cart");
                        Log.e("tag", "" + jsara);
                        for (int i = 0; i < jsara.length(); i++) {

                            JSONObject datas = jsara.getJSONObject(i);

                            DbGclass dbc = new DbGclass();
                            dbc.set_id(datas.getString("cart_id"));
                            dbc.set_size(datas.getString("shipment_size"));
                            dbc.set_pickup(datas.getString("shipment_pickup"));
                            //dbc.set_photo(datas.getString("shipment_photo"));
                            dbc.set_cost(datas.getString("shipment_cost"));
                            Log.e("tag", "" + dbc.get_cost());
                            dbdatalist.add(dbc);
                        }

                        mAdapter = new CartData(Cart.this, dbdatalist, str_sessionid);
                        list.setAdapter(mAdapter);
                        mAdapter.setMode(Attributes.Mode.Single);
                    }


                } else {
                    Log.e("tag_", "error");
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("tag_", "3" + e.getLocalizedMessage());

            }
        }
    }


    class getPalLists extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";


            try {

                String virtual_url = Config.SER_URL + "pal/friends";
                Log.e("tag", "" + virtual_url);

                JSONObject jsonobject = HttpUtils.getPalLists(virtual_url, str_sessionid);

                Log.d("tag", "" + jsonobject.toString());

                if (jsonobject.toString() == "sam") {
                   /* Dialog_Msg dialog_fail = new Dialog_Msg(Cart.this, "Try Again Later");
                    dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog_fail.show();*/


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

                        map = new HashMap<String, String>();
                        pal_datas = new ArrayList<>();


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

                        lview.setVisibility(View.VISIBLE);

                        di_progress.setVisibility(View.GONE);
                        tv_loader.setVisibility(View.GONE);
                        btn_yes.setVisibility(View.GONE);
                        btn_no.setVisibility(View.GONE);
                        btn_ok.setVisibility(View.VISIBLE);
                        btn_ok.setText("Next");

                        PalListAdapter adapter = new PalListAdapter(Cart.this, pal_datas);
                        lview.setAdapter(adapter);


                    } else {
                        lview.setVisibility(View.GONE);
                        di_progress.setVisibility(View.GONE);
                        tv_loader.setVisibility(View.VISIBLE);
                        btn_yes.setVisibility(View.GONE);
                        btn_no.setVisibility(View.GONE);
                        tv_loader.setText("You Don't Have any Friends! Ship Package to yourself?");
                        btn_ok.setVisibility(View.VISIBLE);
                        btn_ok.setText("Ok");
                    }

                } else {

                    /*Dialog_Msg dialog_fail = new Dialog_Msg(Cart.this, "Try Again Later");
                    dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog_fail.show();*/
                    di_progress.setVisibility(View.GONE);
                    tv_loader.setVisibility(View.GONE);
                    tv_loader.setText("No Network, Please Try Again.");
                    btn_yes.setVisibility(View.GONE);
                    btn_no.setVisibility(View.GONE);
                    btn_ok.setVisibility(View.VISIBLE);
                    btn_ok.setText("Ok");

                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

                di_progress.setVisibility(View.GONE);
                tv_loader.setVisibility(View.VISIBLE);
                tv_loader.setText("No Network, Please Try Again.");
                btn_yes.setVisibility(View.GONE);
                btn_no.setVisibility(View.GONE);
                btn_ok.setVisibility(View.VISIBLE);
                btn_ok.setText("Ok");
            }

        }

    }


}
