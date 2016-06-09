package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.rey.material.widget.Button;
import com.rey.material.widget.Spinner;
import com.rey.material.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Salman on 5/18/2016.
 */
public class ChooseAddress extends Activity {

    com.rey.material.widget.LinearLayout lt_back;
    Button btn_payment;
    Spinner spin_one, spin_two;
    String[] sting;
    ListAdapter_Class country_list_adapter;
    Typeface tf, tf1;
    ArrayList<String> sss = new ArrayList<>();

    TextView tv_from, tv_to, tv_pickup, tv_sub1, tv_sub2, tv_sub3, tv_header;

    String str_name, str_oonbux_id, str_phone, str_state, str_zip, str_country;

    Button on, off, on1, off1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooseaddress_new);

        tf1 = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");
        tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ChooseAddress.this);

        str_name = sharedPreferences.getString("fname", "") + " " + sharedPreferences.getString("lname", "");
        str_phone = sharedPreferences.getString("phone", "");
        str_zip = sharedPreferences.getString("zip", "");
        str_state = sharedPreferences.getString("state", "");
        str_oonbux_id = sharedPreferences.getString("oonbuxid", "");
        str_country = sharedPreferences.getString("country", "");


        on = (Button) findViewById(R.id.btn_on_pickup);
        off = (Button) findViewById(R.id.btn_off_pickup);

        on1 = (Button) findViewById(R.id.btn_on_to);
        off1 = (Button) findViewById(R.id.btn_off_to);


        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // on.setBackgroundColor(getResources().getColor(R.color.tab_brown));
                on.setBackgroundResource(R.drawable.thumb);
                //on.setBackground(getResources().getDrawable(R.drawable.thumb));
                off.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                // off.setBackgroundColor(getResources().getColor(R.color.tab_default));

            }
        });


        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                off.setBackgroundResource(R.drawable.thumb);
                on.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                /*off.setBackgroundColor(getResources().getColor(R.color.tab_brown));
                on.setBackgroundColor(getResources().getColor(R.color.tab_default));*/

            }
        });


        lt_back = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_back);

        btn_payment = (Button) findViewById(R.id.button_payment);
        spin_one = (Spinner) findViewById(R.id.spin_one);

        spin_two = (Spinner) findViewById(R.id.spin_two);


        tv_from = (TextView) findViewById(R.id.tv_frm_txt);
        tv_pickup = (TextView) findViewById(R.id.tv_pickup_txt);
        tv_to = (TextView) findViewById(R.id.tv_to_txt);

        tv_sub1 = (TextView) findViewById(R.id.tv_shd_txt1);
        tv_sub2 = (TextView) findViewById(R.id.tv_shd_txt2);
        tv_sub3 = (TextView) findViewById(R.id.tv_shd_txt3);


        tv_header = (TextView) findViewById(R.id.tv_hd_txt);


        tv_sub1.setText(str_name + "\n" + str_oonbux_id + "\n" + str_state + "\t" + str_zip + "\n" + str_country);


        tv_header.setTypeface(tf1);

        tv_from.setTypeface(tf);
        tv_pickup.setTypeface(tf);
        tv_to.setTypeface(tf);

        tv_sub1.setTypeface(tf);
        tv_sub2.setTypeface(tf);
        tv_sub3.setTypeface(tf);

        btn_payment.setTypeface(tf);




        sting = new String[]{"72,Boorks,NY", "235,Mesa,CR",
                "1655,Ooae,VV", "5,Virginia,NW", "234255,oklahoma,SR"};

        sss.add("72,Boorks,NY");
        sss.add("asdf,DownTown,PR");
        sss.add("235,Mesa,CR");
        sss.add("1655,Ooae,VV");


        spin_one.setAdapter(new MyAdapter(ChooseAddress.this, R.layout.dropdown_lists3, sting));

        spin_two.setAdapter(new MyAdapter(ChooseAddress.this, R.layout.dropdown_lists3, sting));

        /*country_list_adapter = new ListAdapter_Class(getApplicationContext(), R.layout.dropdown_lists, sss);
        spin_one.setAdapter(country_list_adapter);*/


        // mySpinnerArrayAdapter = new   CustomAdapter(getApplicationContext(),R.layout.dropdown_lists1,sss);
        //spin_one.setAdapter(mySpinnerArrayAdapter);


        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                startActivity(inte);*/
                onBackPressed();
            }
        });

        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new SweetAlertDialog(ChooseAddress.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Complete Payment?")
                        .setConfirmText("Yes!")
                        .setCancelText("No")

                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismiss();
                                new SweetAlertDialog(ChooseAddress.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Your shipment completed successfully")
                                        .setContentText("Shipment id: 25445XCO44\n Oonbux id: DFD343DS32")
                                        .setConfirmText("Ok")

                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {

                                                Intent newe = new Intent(getApplicationContext(), DashBoardActivity.class);
                                                startActivity(newe);
                                                sDialog.dismiss();

                                            }
                                        })
                                        .show();

                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();

                            }
                        })
                        .show();


            }
        });

    }


    public class MyAdapter extends ArrayAdapter<String> {

        int resource;

        public MyAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);

            this.resource = textViewResourceId;
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

            label.setText(sting[position]);
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
