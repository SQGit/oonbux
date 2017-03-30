package sqindia.net.oonbux.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.rey.material.widget.Button;
import com.rey.material.widget.Spinner;
import com.rey.material.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import sqindia.net.oonbux.R;
import sqindia.net.oonbux.config.Config;
import sqindia.net.oonbux.config.DbC;
import sqindia.net.oonbux.config.HttpUtils;

/**
 * Created by Salman on 5/18/2016.
 */
//adsfasd


public class ChooseAddress extends Activity {

    ImageView lt_back;
    Button btn_payment;
    Spinner spn_pickup_address, spn_to_address, spn_from_address;
    Typeface tf, tf1;
    TextView tv_from, tv_from_address, tv_to, tv_pickup, tv_sub1, tv_pickup_address, tv_to_address, tv_header, tv_carttxt, tv_cart;
    String str_name, str_oonbux_id, str_phone, str_state, str_zip, str_country, str_session_id, phy_def_address;

    Button btn_pickup_loc, btn_pickup_intl, btn_to_loc, btn_to_intl, btn_from_loc, btn_from_intl;
    SweetAlertDialog sweetAlertDialog;
    ArrayList<HashMap<String, String>> us_lists;
    ArrayList<HashMap<String, String>> nig_lists;
    Dialog dialog_payment;
    String query0, query, query1;
    DbC dbclass;
    Context context = this;

    LinearLayout from_lt;
    ImageView spn_drop;

    ArrayList<String> loc_lists = new ArrayList<>();
    ArrayList<String> int_lists = new ArrayList<>();

    HashMap<String, String> virtual_address = new HashMap<>();
    ArrayList<String> pickup_address = new ArrayList<>();

    HashMap<Integer, ArrayList<String>> virtual_address1 = new HashMap<>();
    ArrayList<String> pickup_address1 = new ArrayList<>();
    int rs;

    ArrayList<String> pickup_address_id = new ArrayList<>();
    ArrayList<String> pickup_address_line1 = new ArrayList<>();
    ArrayList<String> pickup_address_line2 = new ArrayList<>();
    ArrayList<String> pickup_address_city = new ArrayList<>();
    ArrayList<String> pickup_address_state = new ArrayList<>();
    ArrayList<String> pickup_address_zip = new ArrayList<>();
    ArrayList<String> pickup_address_country = new ArrayList<>();
    ArrayList<String> pickup_address_loc = new ArrayList<>();


    ArrayList<String> to_address_line1 = new ArrayList<>();
    ArrayList<String> to_address_line2 = new ArrayList<>();
    ArrayList<String> to_address_city = new ArrayList<>();
    ArrayList<String> to_address_state = new ArrayList<>();
    ArrayList<String> to_address_zip = new ArrayList<>();
    ArrayList<String> to_address_phone = new ArrayList<>();
    ArrayList<String> to_address_country = new ArrayList<>();
    ArrayList<String> to_address_note = new ArrayList<>();
    ArrayList<String> to_address_loc = new ArrayList<>();

    ArrayList<String> from_address_id = new ArrayList<>();
    ArrayList<String> from_address_line1 = new ArrayList<>();
    ArrayList<String> from_address_line2 = new ArrayList<>();
    ArrayList<String> from_address_city = new ArrayList<>();
    ArrayList<String> from_address_state = new ArrayList<>();
    ArrayList<String> from_address_zip = new ArrayList<>();
    ArrayList<String> from_address_phone = new ArrayList<>();
    ArrayList<String> from_address_country = new ArrayList<>();
    ArrayList<String> from_address_note = new ArrayList<>();
    ArrayList<String> from_address_loc = new ArrayList<>();


    HashMap<Integer, ArrayList<String>> physical_address = new HashMap<>();
    HashMap<Integer, ArrayList<String>> from_address_hash = new HashMap<>();
    ArrayList<String> to_address = new ArrayList<>();
    ArrayList<String> to_address1 = new ArrayList<>();
    ArrayList<String> to_address2 = new ArrayList<>();
    ArrayList<String> from_address = new ArrayList<>();

    String addr_line1, addr_line2, addr_city, addr_state, addr_zip, addr_country, addr_loc, addr_id, pickup;


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooseaddress_new);
        tf1 = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");
        tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ChooseAddress.this);
        editor = sharedPreferences.edit();

        str_name = sharedPreferences.getString("fname", "") + " " + sharedPreferences.getString("lname", "");
        str_phone = sharedPreferences.getString("phone", "");
        str_zip = sharedPreferences.getString("zip", "");
        str_state = sharedPreferences.getString("state", "");
        str_oonbux_id = sharedPreferences.getString("oonbuxid", "");
        str_country = sharedPreferences.getString("country", "");
        str_session_id = sharedPreferences.getString("sessionid", "");
        phy_def_address = sharedPreferences.getString("default_adr", "");


        dbclass = new DbC(context);

        btn_from_loc = (Button) findViewById(R.id.btn_on_from);
        btn_from_intl = (Button) findViewById(R.id.btn_off_from);
        btn_pickup_loc = (Button) findViewById(R.id.btn_on_pickup);
        btn_pickup_intl = (Button) findViewById(R.id.btn_off_pickup);
        btn_to_loc = (Button) findViewById(R.id.btn_on_to);
        btn_to_intl = (Button) findViewById(R.id.btn_off_to);


        lt_back = (ImageView) findViewById(R.id.btn_back);

        btn_payment = (Button) findViewById(R.id.button_payment);

        spn_from_address = (Spinner) findViewById(R.id.spin_zero);
        spn_pickup_address = (Spinner) findViewById(R.id.spin_one);
        spn_to_address = (Spinner) findViewById(R.id.spin_two);


        tv_from = (TextView) findViewById(R.id.tv_frm_txt);
        tv_pickup = (TextView) findViewById(R.id.tv_pickup_txt);
        tv_to = (TextView) findViewById(R.id.tv_to_txt);

        tv_sub1 = (TextView) findViewById(R.id.tv_shd_txt1);
        tv_from_address = (TextView) findViewById(R.id.tv_from_addr);
        tv_pickup_address = (TextView) findViewById(R.id.tv_shd_txt2);
        tv_to_address = (TextView) findViewById(R.id.tv_shd_txt3);

        tv_header = (TextView) findViewById(R.id.tv_hd_txt);

        from_lt = (LinearLayout) findViewById(R.id.from_layout);
        spn_drop = (ImageView) findViewById(R.id.from_spinner);


        tv_sub1.setText(str_name + "\n" + str_oonbux_id);


        tv_header.setTypeface(tf1);

        tv_from.setTypeface(tf);
        tv_pickup.setTypeface(tf);
        tv_to.setTypeface(tf);
        tv_sub1.setTypeface(tf);
        tv_from_address.setTypeface(tf);
        tv_pickup_address.setTypeface(tf);
        tv_to_address.setTypeface(tf);
        btn_payment.setTypeface(tf);
        btn_pickup_loc.setTypeface(tf);
        btn_pickup_intl.setTypeface(tf);
        btn_to_loc.setTypeface(tf);
        btn_to_intl.setTypeface(tf);
        btn_from_loc.setTypeface(tf);
        btn_from_intl.setTypeface(tf);


        us_lists = new ArrayList<>();
        nig_lists = new ArrayList<>();


        Intent intent = getIntent();
        pickup = intent.getStringExtra("pickup");
        Log.e("tag", "" + pickup);

        if (pickup.equals("no")) {
            from_lt.setVisibility(View.GONE);
            spn_from_address.setVisibility(View.GONE);
            spn_drop.setVisibility(View.GONE);
            Log.e("tag", "inside");
        }


        btn_pickup_loc.setBackgroundResource(R.drawable.thumb);
        btn_pickup_intl.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        btn_pickup_intl.setTextColor(getResources().getColor(R.color.text_divider1));
        query = "select * from virtual where loc = 0";
        getVirtualDB(query);

        Log.e("tag","def "+phy_def_address);

        if (phy_def_address.equals("LOCAL")) {

            Log.e("tag", "locala");


            btn_to_loc.setBackgroundResource(R.drawable.thumb);
            btn_to_intl.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btn_to_intl.setTextColor(getResources().getColor(R.color.text_divider1));
            query1 = "select * from physical where loc = 0";
            getToDB(query1);


            btn_from_loc.setBackgroundResource(R.drawable.thumb);
            btn_from_intl.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btn_from_intl.setTextColor(getResources().getColor(R.color.text_divider1));
            query0 = "select * from physical where loc = 0";
            getFromDB(query0);

        } else if (phy_def_address.equals("INTERNATIONAL")) {


            Log.e("tag", "intla");

            btn_to_intl.setBackgroundResource(R.drawable.thumb);
            btn_to_loc.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btn_to_loc.setTextColor(getResources().getColor(R.color.text_divider1));
            query1 = "select * from physical where loc = 1";
            getToDB(query1);


            btn_from_intl.setBackgroundResource(R.drawable.thumb);
            btn_from_loc.setBackgroundColor(getResources().getColor(android.R.color.transparent));
            btn_from_loc.setTextColor(getResources().getColor(R.color.text_divider1));
            query0 = "select * from physical where loc = 1";
            getFromDB(query0);


        }















        btn_from_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_from_loc.setBackgroundResource(R.drawable.thumb);
                btn_from_intl.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                btn_from_intl.setTextColor(getResources().getColor(R.color.text_divider1));
                query0 = "select * from physical where loc = 0";
                getFromDB(query0);
            }
        });


        btn_from_intl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_from_intl.setBackgroundResource(R.drawable.thumb);
                btn_from_loc.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                btn_from_loc.setTextColor(getResources().getColor(R.color.text_divider1));
                query0 = "select * from physical where loc = 1";
                getFromDB(query0);
            }
        });


        btn_pickup_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_pickup_loc.setBackgroundResource(R.drawable.thumb);
                btn_pickup_intl.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                btn_pickup_intl.setTextColor(getResources().getColor(R.color.text_divider1));
                query0 = "select * from virtual where loc = 0";
                getVirtualDB(query0);
            }
        });
        btn_pickup_intl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_pickup_intl.setBackgroundResource(R.drawable.thumb);
                btn_pickup_loc.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                btn_pickup_loc.setTextColor(getResources().getColor(R.color.text_divider1));
                query0 = "select * from virtual where loc = 1";
                getVirtualDB(query0);
            }
        });


        btn_to_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_to_loc.setBackgroundResource(R.drawable.thumb);
                btn_to_intl.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                btn_to_intl.setTextColor(getResources().getColor(R.color.text_divider1));
                query1 = "select * from physical where loc = 0";
                getToDB(query1);

            }
        });
        btn_to_intl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_to_intl.setBackgroundResource(R.drawable.thumb);
                btn_to_loc.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                btn_to_loc.setTextColor(getResources().getColor(R.color.text_divider1));
                query1 = "select * from physical where loc = 1";
                getToDB(query1);

            }
        });








        spn_from_address.setOnItemClickListener(new Spinner.OnItemClickListener() {
            @Override
            public boolean onItemClick(Spinner parent, View view, int position, long id) {

                Log.e("tag", "pos" + position);

                ArrayList<String> address = from_address_hash.get(position);

                Log.e("tag", "siz" + address.size());

                for (int i = 0; i < address.size(); i++) {
                    Log.e("tag", "" + address.get(i));
                }

                spn_from_address.setSelection(position);

                addr_line1 = from_address_line1.get(position);
                addr_line2 = from_address_line2.get(position);
                addr_city = from_address_city.get(position);
                addr_state = from_address_state.get(position);
                addr_zip = from_address_zip.get(position);
                addr_country = from_address_country.get(position);
                addr_loc = from_address_loc.get(position);


                tv_from_address.setText(addr_line1 + "\n" + addr_line2 + ", " + addr_city + "\n" + addr_state + "\n" + addr_zip + "\n" + addr_country);


                return false;
            }
        });


        spn_pickup_address.setOnItemClickListener(new Spinner.OnItemClickListener() {
            @Override
            public boolean onItemClick(Spinner parent, View view, int position, long id) {

                Log.e("tag", "pos" + position);

                ArrayList<String> address = virtual_address1.get(position);

                Log.e("tag", "siz" + address.size());

                for (int i = 0; i < address.size(); i++) {
                    Log.e("tag", "" + address.get(i));
                }

                spn_pickup_address.setSelection(position);

                addr_id = pickup_address_id.get(position);
                addr_line1 = pickup_address_line1.get(position);
                addr_line2 = pickup_address_line2.get(position);
                addr_city = pickup_address_city.get(position);
                addr_state = pickup_address_state.get(position);
                addr_zip = pickup_address_zip.get(position);
                addr_country = pickup_address_country.get(position);
                addr_loc = pickup_address_loc.get(position);


                tv_pickup_address.setText(addr_line1 + "\n" + addr_line2 + ", " + addr_city + "\n" + addr_state + "\n" + addr_zip + "\n" + addr_country);


                return false;
            }
        });


        spn_to_address.setOnItemClickListener(new Spinner.OnItemClickListener() {
            @Override
            public boolean onItemClick(Spinner parent, View view, int position, long id) {


                ArrayList<String> address = physical_address.get(position);

                Log.e("tag", "siz" + address.size());

                for (int i = 0; i < address.size(); i++) {
                    Log.e("tag", "" + address.get(i));
                }

                spn_to_address.setSelection(position);


                addr_line1 = to_address_line1.get(position);
                addr_line2 = to_address_line2.get(position);
                addr_city = to_address_city.get(position);
                addr_state = to_address_state.get(position);
                addr_zip = to_address_zip.get(position);
                addr_country = to_address_country.get(position);
                addr_loc = to_address_loc.get(position);


                tv_to_address.setText(addr_line1 + "\n" + addr_line2 + ", " + addr_city + "\n" + addr_state + "\n" + addr_zip + "\n" + addr_country);

                return false;
            }
        });


        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent inte = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(inte);*/
                onBackPressed();
            }
        });

        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog_payment = new Dialog(ChooseAddress.this);
                dialog_payment.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog_payment.setContentView(R.layout.dialog_confirm_payment);
                dialog_payment.setCancelable(true);


                final TextView tv_header = (TextView) dialog_payment.findViewById(R.id.tv_header);
                final TextView tv_add_pickup = (TextView) dialog_payment.findViewById(R.id.tv_add_pickup);
                final Button btn_yes = (Button) dialog_payment.findViewById(R.id.button_yes);
                final Button btn_no = (Button) dialog_payment.findViewById(R.id.button_no);
                final Button btn_ok = (Button) dialog_payment.findViewById(R.id.button_ok);

                tv_header.setTypeface(tf);
                tv_add_pickup.setTypeface(tf);
                btn_yes.setTypeface(tf);
                btn_no.setTypeface(tf);
                btn_ok.setTypeface(tf);

                tv_add_pickup.setVisibility(View.GONE);
                btn_ok.setVisibility(View.GONE);

                btn_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        tv_add_pickup.setVisibility(View.VISIBLE);
                        btn_ok.setVisibility(View.VISIBLE);
                        tv_header.setText("Your shipment completed successfully");
                        tv_header.setTextSize(13);
                        btn_yes.setVisibility(View.GONE);
                        btn_no.setVisibility(View.GONE);
                        tv_add_pickup.setText("Shipment id: 25445XCO44.\n Oonbux id: DFD343DS32");

                    }
                });

                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dbclass.del_table();

                        editor.putString("shipment_photo", "");
                        editor.commit();

                        dialog_payment.dismiss();

                        Intent newe = new Intent(getApplicationContext(), Dashboard.class);
                        startActivity(newe);
                    }
                });

                btn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_payment.dismiss();
                    }
                });

                dialog_payment.show();
            }
        });

    }


    private void getFromDB(String query0) {


        // ArrayList<DbGclass> dbdatalist = new ArrayList<DbGclass>();
        // dbdatalist.clear();

        from_address_hash.clear();
        to_address2.clear();


        from_address_line1.clear();
        from_address_line2.clear();
        from_address_city.clear();
        from_address_state.clear();
        from_address_zip.clear();
        from_address_country.clear();
        from_address_loc.clear();


        //  String query = " SELECT * FROM cart";


        Cursor c = dbclass.fetchdata(query0);

        Log.e("tag", query0 + "\n" + c);

        rs = 0;

        if (c != null) {
            if (c.moveToFirst()) {
                do {


                    Log.e("tag", "" + c.getString(c.getColumnIndex("zip")));

                    to_address2.add(c.getString(c.getColumnIndex("city")) + " - " + c.getString(c.getColumnIndex("zip")));


                    from_address.add(c.getString(c.getColumnIndex("addr_line1")));
                    from_address.add(c.getString(c.getColumnIndex("addr_line2")));
                    from_address.add(c.getString(c.getColumnIndex("city")));
                    from_address.add(c.getString(c.getColumnIndex("state")));
                    from_address.add(c.getString(c.getColumnIndex("zip")));
                    from_address.add(c.getString(c.getColumnIndex("phone")));
                    from_address.add(c.getString(c.getColumnIndex("country")));
                    from_address.add(c.getString(c.getColumnIndex("note")));
                    from_address.add(c.getString(c.getColumnIndex("loc")));


                    from_address_line1.add(c.getString(c.getColumnIndex("addr_line1")));
                    from_address_line2.add(c.getString(c.getColumnIndex("addr_line2")));
                    from_address_city.add(c.getString(c.getColumnIndex("city")));
                    from_address_state.add(c.getString(c.getColumnIndex("state")));
                    from_address_zip.add(c.getString(c.getColumnIndex("zip")));
                    from_address_phone.add(c.getString(c.getColumnIndex("phone")));
                    from_address_country.add(c.getString(c.getColumnIndex("country")));
                    from_address_note.add(c.getString(c.getColumnIndex("note")));
                    from_address_loc.add(c.getString(c.getColumnIndex("loc")));


                    from_address_hash.put(rs, from_address);
                    from_address.clear();
                    rs = rs + 1;


                } while (c.moveToNext());
            }
        }

        MyAdapter adapter0 = new MyAdapter(ChooseAddress.this, R.layout.dropdown_lists3, to_address2);
        spn_from_address.setAdapter(adapter0);
        adapter0.notifyDataSetChanged();


        if (from_address_line1.size() > 0) {

            spn_from_address.setEnabled(true);

            addr_line1 = from_address_line1.get(0);
            addr_line2 = from_address_line2.get(0);
            addr_city = from_address_city.get(0);
            addr_state = from_address_state.get(0);
            addr_zip = from_address_zip.get(0);
            addr_country = from_address_country.get(0);
            addr_loc = from_address_loc.get(0);


            //   tv_pickup_address.setText(pickup_address_line1.get(0) + "\n" + pickup_address_line2.get(0) + ", " + pickup_address_city.get(0) + "\n" + pickup_address_state.get(0) + "\n" + pickup_address_zip.get(0) + "\n" + pickup_address_country.get(0));

            tv_from_address.setText(from_address_line1.get(0) + ",\t" + from_address_line2.get(0) + "\n" + from_address_city.get(0) + "\n" + from_address_state.get(0) + ",\t" + from_address_zip.get(0) + "\n" + from_address_country.get(0));

            // adapter1 = new Adap_Shipment(ShipmentNext.this, dbdatalist);
            //mAdapter = new CartData(Cart.this,dbdatalist);
            // list.setAdapter(mAdapter);
            //  mAdapter.setMode(Attributes.Mode.Single);
        } else {
            spn_from_address.setEnabled(false);


            if (phy_def_address.equals("LOCAL")) {

                Log.e("tag", "00locala");

                btn_from_loc.setBackgroundResource(R.drawable.thumb);
                btn_from_intl.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                btn_from_intl.setTextColor(getResources().getColor(R.color.text_divider1));

            } else if (phy_def_address.equals("INTERNATIONAL")) {

                Log.e("tag", "00intla");

                btn_from_intl.setBackgroundResource(R.drawable.thumb);
                btn_from_loc.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                btn_from_loc.setTextColor(getResources().getColor(R.color.text_divider1));
            }

        }

    }


    private void getVirtualDB(String query) {


        // ArrayList<DbGclass> dbdatalist = new ArrayList<DbGclass>();
        // dbdatalist.clear();

        // virtual_address.clear();
        virtual_address1.clear();

        pickup_address.clear();


        pickup_address_id.clear();
        pickup_address_line1.clear();
        pickup_address_line2.clear();
        pickup_address_city.clear();
        pickup_address_state.clear();
        pickup_address_zip.clear();
        pickup_address_country.clear();
        pickup_address_loc.clear();


        //  String query = " SELECT * FROM cart";


        Cursor c = dbclass.fetchdata(query);

        rs = 0;

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                 /*   DbGclass dbc = new DbGclass();

                    dbc.set_id(String.valueOf(c.getInt(c.getColumnIndex("id"))));

                    dbc.set_size(c.getString(c.getColumnIndex("size")));
                    dbc.set_pickup(c.getString(c.getColumnIndex("pickup")));
                    dbc.set_photo(c.getString(c.getColumnIndex("photo")));
                    dbdatalist.add(dbc);*/

                    //(addr_id,addr_line1,addr_line2,city,state,zip,country,loc)

             /*       virtual_address.put("address_id", c.getString(c.getColumnIndex("addr_id")));
                    virtual_address.put("addr_line1", c.getString(c.getColumnIndex("addr_line1")));
                    virtual_address.put("addr_line2", c.getString(c.getColumnIndex("addr_line2")));
                    virtual_address.put("city", c.getString(c.getColumnIndex("city")));
                    virtual_address.put("state", c.getString(c.getColumnIndex("state")));
                    virtual_address.put("zip", c.getString(c.getColumnIndex("zip")));
                    virtual_address.put("country", c.getString(c.getColumnIndex("country")));
                    virtual_address.put("loc", c.getString(c.getColumnIndex("loc")));*/


                    pickup_address.add(c.getString(c.getColumnIndex("city")) + " - " + c.getString(c.getColumnIndex("zip")));


                    pickup_address1.add(c.getString(c.getColumnIndex("addr_id")));
                    pickup_address1.add(c.getString(c.getColumnIndex("addr_line1")));
                    pickup_address1.add(c.getString(c.getColumnIndex("addr_line2")));
                    pickup_address1.add(c.getString(c.getColumnIndex("city")));
                    pickup_address1.add(c.getString(c.getColumnIndex("state")));
                    pickup_address1.add(c.getString(c.getColumnIndex("zip")));
                    pickup_address1.add(c.getString(c.getColumnIndex("country")));
                    pickup_address1.add(c.getString(c.getColumnIndex("loc")));


                    pickup_address_id.add(c.getString(c.getColumnIndex("addr_id")));
                    pickup_address_line1.add(c.getString(c.getColumnIndex("addr_line1")));
                    pickup_address_line2.add(c.getString(c.getColumnIndex("addr_line2")));
                    pickup_address_city.add(c.getString(c.getColumnIndex("city")));
                    pickup_address_state.add(c.getString(c.getColumnIndex("state")));
                    pickup_address_zip.add(c.getString(c.getColumnIndex("zip")));
                    pickup_address_country.add(c.getString(c.getColumnIndex("country")));
                    pickup_address_loc.add(c.getString(c.getColumnIndex("loc")));


                    virtual_address1.put(rs, pickup_address1);
                    pickup_address1.clear();
                    rs = rs + 1;


                } while (c.moveToNext());
            }
        }

        MyAdapter adapter = new MyAdapter(ChooseAddress.this, R.layout.dropdown_lists3, pickup_address);
        spn_pickup_address.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        if (pickup_address_line1.size() > 0) {

            addr_id = pickup_address_id.get(0);
            addr_line1 = pickup_address_line1.get(0);
            addr_line2 = pickup_address_line2.get(0);
            addr_city = pickup_address_city.get(0);
            addr_state = pickup_address_state.get(0);
            addr_zip = pickup_address_zip.get(0);
            addr_country = pickup_address_country.get(0);
            addr_loc = pickup_address_loc.get(0);


            tv_pickup_address.setText(pickup_address_line1.get(0) + "\n" + pickup_address_line2.get(0) + ", " + pickup_address_city.get(0) + "\n" + pickup_address_state.get(0) + ",\t" + pickup_address_zip.get(0) + "\n" + pickup_address_country.get(0));

            // tv_to_address.setText(pickup_address_line1.get(1) + "\n" + pickup_address_line2.get(1) + ", " + pickup_address_city.get(1) + "\n" + pickup_address_state.get(1) + "\n" + pickup_address_zip.get(1) + "\n" + pickup_address_country.get(1));

            // adapter1 = new Adap_Shipment(ShipmentNext.this, dbdatalist);
            //mAdapter = new CartData(Cart.this,dbdatalist);
            // list.setAdapter(mAdapter);
            //  mAdapter.setMode(Attributes.Mode.Single);

        } else {
            spn_to_address.setEnabled(false);
        }
    }


    private void getToDB(String query1) {


        // ArrayList<DbGclass> dbdatalist = new ArrayList<DbGclass>();
        // dbdatalist.clear();

        physical_address.clear();
        to_address1.clear();


        to_address_line1.clear();
        to_address_line2.clear();
        to_address_city.clear();
        to_address_state.clear();
        to_address_zip.clear();
        to_address_country.clear();
        to_address_loc.clear();


        //  String query = " SELECT * FROM cart";


        Cursor c = dbclass.fetchdata(query1);

        Log.e("tag", query1 + "\n" + c);

        rs = 0;

        if (c != null) {
            if (c.moveToFirst()) {
                do {


                    Log.e("tag", "" + c.getString(c.getColumnIndex("zip")));

                    to_address1.add(c.getString(c.getColumnIndex("city")) + " - " + c.getString(c.getColumnIndex("zip")));


                    to_address.add(c.getString(c.getColumnIndex("addr_line1")));
                    to_address.add(c.getString(c.getColumnIndex("addr_line2")));
                    to_address.add(c.getString(c.getColumnIndex("city")));
                    to_address.add(c.getString(c.getColumnIndex("state")));
                    to_address.add(c.getString(c.getColumnIndex("zip")));
                    to_address.add(c.getString(c.getColumnIndex("phone")));
                    to_address.add(c.getString(c.getColumnIndex("country")));
                    to_address.add(c.getString(c.getColumnIndex("note")));
                    to_address.add(c.getString(c.getColumnIndex("loc")));


                    to_address_line1.add(c.getString(c.getColumnIndex("addr_line1")));
                    to_address_line2.add(c.getString(c.getColumnIndex("addr_line2")));
                    to_address_city.add(c.getString(c.getColumnIndex("city")));
                    to_address_state.add(c.getString(c.getColumnIndex("state")));
                    to_address_zip.add(c.getString(c.getColumnIndex("zip")));
                    to_address_phone.add(c.getString(c.getColumnIndex("phone")));
                    to_address_country.add(c.getString(c.getColumnIndex("country")));
                    to_address_note.add(c.getString(c.getColumnIndex("note")));
                    to_address_loc.add(c.getString(c.getColumnIndex("loc")));


                    physical_address.put(rs, to_address);
                    to_address.clear();
                    rs = rs + 1;


                } while (c.moveToNext());
            }
        }

        MyAdapter adapter1 = new MyAdapter(ChooseAddress.this, R.layout.dropdown_lists3, to_address1);
        spn_to_address.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();


        if (to_address_line1.size() > 0) {

            spn_to_address.setEnabled(true);

            addr_line1 = to_address_line1.get(0);
            addr_line2 = to_address_line2.get(0);
            addr_city = to_address_city.get(0);
            addr_state = to_address_state.get(0);
            addr_zip = to_address_zip.get(0);
            addr_country = to_address_country.get(0);
            addr_loc = to_address_loc.get(0);


            //   tv_pickup_address.setText(pickup_address_line1.get(0) + "\n" + pickup_address_line2.get(0) + ", " + pickup_address_city.get(0) + "\n" + pickup_address_state.get(0) + "\n" + pickup_address_zip.get(0) + "\n" + pickup_address_country.get(0));

            tv_to_address.setText(to_address_line1.get(0) + ",\t" + to_address_line2.get(0) + "\n" + to_address_city.get(0) + "\n" + to_address_state.get(0) + ",\t" + to_address_zip.get(0) + "\n" + to_address_country.get(0));

            // adapter1 = new Adap_Shipment(ShipmentNext.this, dbdatalist);
            //mAdapter = new CartData(Cart.this,dbdatalist);
            // list.setAdapter(mAdapter);
            //  mAdapter.setMode(Attributes.Mode.Single);
        } else {
            spn_to_address.setEnabled(false);

        }

    }


    class GetVirutal extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            sweetAlertDialog = new SweetAlertDialog(ChooseAddress.this, SweetAlertDialog.PROGRESS_TYPE);
            sweetAlertDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            sweetAlertDialog.setTitleText("Loading");
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.show();
        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";


            try {

                String virtual_url = Config.SER_URL + "virtualaddr/fetch";

                JSONObject jsonobject = HttpUtils.getVirtual(virtual_url, str_session_id);

                Log.d("tag", "" + jsonobject.toString());

                if (jsonobject.toString() == "sam") {
                    new SweetAlertDialog(ChooseAddress.this, SweetAlertDialog.WARNING_TYPE)
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
            sweetAlertDialog.dismiss();


            try {

                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");


                if (status.equals("success")) {
                    JSONArray virtual_address = jo.getJSONArray("virtual_location");
                    // Log.d("tag", "<-----virtual_address----->" + "" + virtual_address);

                    //GridviewDatas getSet = new GridviewDatas();
                    for (int i = 0; i < virtual_address.length(); i++) {
                        JSONObject location_address = virtual_address.getJSONObject(i);
                        // Log.d("tag", "<----Location_address-----> " + i + " " + location_address);
                        String country = location_address.getString("country");
                        // Log.d("tag", "<----country-----> " + i + "" + country);

                        if (country.equals("US")) {

                            JSONArray us_location = location_address.getJSONArray("location");
                            // Log.d("tag", "<----us-----> " + i + "" + us_location);

                            for (int j = 0; j < us_location.length(); j++) {
                                JSONObject us_address = us_location.getJSONObject(j);
                                //  Log.d("tag", "<----us_addr-----> " + j + " " + us_address);


                                HashMap<String, String> map = new HashMap<String, String>();
                                us_address = us_location.getJSONObject(j);
                                map.put("vir_addr_id", us_address.getString("vir_addr_id"));
                                map.put("vir_addr_line1", us_address.getString("vir_addr_line1"));
                                map.put("vir_addr_line2", us_address.getString("vir_addr_line2"));
                                map.put("vir_addr_city", us_address.getString("vir_addr_city"));
                                map.put("vir_addr_state", us_address.getString("vir_addr_state"));
                                map.put("vir_addr_zip", us_address.getString("vir_addr_zip"));
                                map.put("vir_addr_country", us_address.getString("vir_addr_country"));

                                us_lists.add(map);


                            }

                        }


                        if (country.equals("NIGERIA")) {

                            JSONArray nig_location = location_address.getJSONArray("location");
                            // Log.d("tag", "<----nigeria-----> " + i + "" + nig_location);

                            for (int j = 0; j < nig_location.length(); j++) {
                                JSONObject nig_address = nig_location.getJSONObject(i);
                                //  Log.d("tag", "<----nige_addr-----> " + j + " " + nig_address);

                                HashMap<String, String> map = new HashMap<String, String>();
                                nig_address = nig_location.getJSONObject(j);
                                map.put("vir_addr_id", nig_address.getString("vir_addr_id"));
                                map.put("vir_addr_line1", nig_address.getString("vir_addr_line1"));
                                map.put("vir_addr_line2", nig_address.getString("vir_addr_line2"));
                                map.put("vir_addr_city", nig_address.getString("vir_addr_city"));
                                map.put("vir_addr_state", nig_address.getString("vir_addr_state"));
                                map.put("vir_addr_zip", nig_address.getString("vir_addr_zip"));
                                map.put("vir_addr_country", nig_address.getString("vir_addr_country"));

                                nig_lists.add(map);
                            }
                        }


                        Log.d("tag", "" + us_lists.size());

                        // ss1 = new String[us_lists.size()];

                        for (int ii = 0; ii < us_lists.size(); ii++) {

                            HashMap<String, String> hash = new HashMap<>();
                            hash = us_lists.get(ii);

                            String a = hash.get("vir_addr_city");
                            String b = hash.get("vir_addr_state");
                            String c = hash.get("vir_addr_zip");

                            //ss1[ii] = a + "," + b;
                            loc_lists.add(a + "," + b);

                            Log.d("tag", a + " " + b + " " + c);
                            //Log.d("tag", ss1[ii]);

                        }


                        for (int iii = 0; iii < nig_lists.size(); iii++) {

                            HashMap<String, String> hash = new HashMap<>();
                            hash = nig_lists.get(iii);

                            String a = hash.get("vir_addr_city");
                            String b = hash.get("vir_addr_state");
                            String c = hash.get("vir_addr_zip");

                            //ss1[ii] = a + "," + b;
                            int_lists.add(a + "," + b);

                            Log.d("tag", a + " " + b + " " + c);
                            //Log.d("tag", ss1[ii]);

                        }


                    }

                    // spn_pickup_address.setAdapter(new MyAdapter(ChooseAddress.this, R.layout.dropdown_lists3, loc_lists));

                    spn_to_address.setAdapter(new MyAdapter(ChooseAddress.this, R.layout.dropdown_lists3, int_lists));


                } else {

                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

    }

    public class MyAdapter extends ArrayAdapter<String> {

        int resource;
        ArrayList<String> list_datas;

        public MyAdapter(Context context, int textViewResourceId, ArrayList<String> objects) {
            super(context, textViewResourceId, objects);

            this.resource = textViewResourceId;
            this.list_datas = objects;

        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(resource, parent, false);

            TextView label = (TextView) row.findViewById(R.id.text_spin);
            label.setTypeface(tf);
            label.setText(list_datas.get(position));
            return row;
        }
    }

    private class CustomAdapter extends ArrayAdapter {

        private Context context;
        private List<String> itemList;

        public CustomAdapter(Context context, int textViewResourceId, List<String> itemList) {

            super(context, textViewResourceId, itemList);
            this.context = context;
            this.itemList = itemList;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.dropdown_lists1, parent,
                    false);
            TextView make = (TextView) row.findViewById(R.id.text_spin);
            make.setText(itemList.get(position));
            return row;
        }

        public View getDropDownView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.dropdown_lists1, parent,
                    false);
            TextView make = (TextView) row.findViewById(R.id.text_spin);
            make.setText(itemList.get(position));
            return row;
        }
    }
}
