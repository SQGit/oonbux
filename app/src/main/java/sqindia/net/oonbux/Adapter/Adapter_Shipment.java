package sqindia.net.oonbux.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.rey.material.widget.CheckBox;
import com.rey.material.widget.TextView;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import sqindia.net.oonbux.R;

/**
 * Created by Salman on 5/13/2016.
 */


//asdfasdf

public class Adapter_Shipment extends BaseAdapter {

    private static LayoutInflater inflater = null;
    public Resources res;
    Uri uri;
    Bitmap bitmap;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ArrayList<String> da;
    private Activity activity;
    private ArrayList<String> data;

    public Adapter_Shipment(Activity a, ArrayList<String> d) {

        this.activity = a;
        this.data = d;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
/*
        if (data.size() <= 0)
            return 1;
        return data.size();*/
        return 1;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        final ViewHolder holder;

        LayoutInflater inflat = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {

            vi = inflat.inflate(R.layout.shipment_listview_adapter, null);


            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
            editor = sharedPreferences.edit();



            // holder = (ViewHolder) vi.getTag();

            holder = new ViewHolder();

            holder.header = (TextView) vi.findViewById(R.id.tv_ship_header);
            holder.perpric = (TextView) vi.findViewById(R.id.tv_per);

            holder.ck_sml = (CheckBox) vi.findViewById(R.id.cb_small);
            holder.ck_med = (CheckBox) vi.findViewById(R.id.cb_medium);
            holder.ck_lrg = (CheckBox) vi.findViewById(R.id.cb_large);
            holder.ck_add = (CheckBox) vi.findViewById(R.id.cb_addto);

            holder.iv_ship_img = (ImageView) vi.findViewById(R.id.imgview);


            Typeface tf = Typeface.createFromAsset(activity.getAssets(), "fonts/prox.otf");

            holder.header.setTypeface(tf);
            holder.perpric.setTypeface(tf);
            holder.ck_sml.setTypeface(tf);
            holder.ck_med.setTypeface(tf);
            holder.ck_lrg.setTypeface(tf);
            holder.ck_add.setTypeface(tf);

           /* uri = Uri.fromFile(new File(data.get(position)));


            try {
                bitmap = null;
                bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
                bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }*/



            Picasso.with(activity)
                    .load(new File(data.get(position)))
                    .fit()
                    .into(holder.iv_ship_img);



            //holder.iv_ship_img.setImageBitmap(bitmap);


            holder.ck_sml.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                   /* if(holder.ck_sml.isChecked() || holder.ck_med.isChecked() || holder.ck_lrg.isChecked()){

                    }*/
                    if (isChecked) {

                        editor.putString("ship_size", "Small");
                        editor.commit();


                        if (holder.ck_add.isChecked()) {
                            holder.perpric.setText("$ 3.00 /Pickup");
                            editor.putString("ship_cost", "$3.00");
                            editor.commit();
                        } else {
                            holder.perpric.setText("$ 3.99 /Pickup");
                            editor.putString("ship_cost", "$3.99");
                            editor.commit();
                        }

                        holder.ck_med.setChecked(false);
                        holder.ck_lrg.setChecked(false);

                    } else if (!isChecked && !holder.ck_med.isChecked() && !holder.ck_lrg.isChecked()) {

                        editor.putString("ship_size", "nil");
                        editor.commit();

                       /* holder.ck_sml.setChecked(true);
                        holder.ck_med.setChecked(false);
                        holder.ck_lrg.setChecked(false);*/
                    }
                }
            });

            holder.ck_med.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                   /* if(holder.ck_sml.isChecked() || holder.ck_med.isChecked() || holder.ck_lrg.isChecked()){

                    }*/
                    if (isChecked) {

                        editor.putString("ship_size", "Medium");
                        editor.commit();

                        if (holder.ck_add.isChecked()) {
                            holder.perpric.setText("$ 4.00 /Pickup");
                            editor.putString("ship_cost", "$ 4.00");
                            editor.commit();
                        } else {
                            holder.perpric.setText("$ 4.99 /Pickup");
                            editor.putString("ship_cost", "$ 4.99");
                            editor.commit();
                        }
                        holder.ck_sml.setChecked(false);
                        holder.ck_lrg.setChecked(false);

                    } else if (!isChecked && !holder.ck_lrg.isChecked() && !holder.ck_sml.isChecked()) {

                        editor.putString("ship_size", "nil");
                        editor.commit();

                       /* holder.ck_sml.setChecked(true);
                        holder.ck_med.setChecked(false);
                        holder.ck_lrg.setChecked(false);*/
                    }
                }
            });

            holder.ck_lrg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                   /* if(holder.ck_sml.isChecked() || holder.ck_med.isChecked() || holder.ck_lrg.isChecked()){

                    }*/
                    if (isChecked) {

                        editor.putString("ship_size", "Large");
                        editor.commit();


                        if (holder.ck_add.isChecked()) {
                            holder.perpric.setText("$ 5.00 /Pickup");
                            editor.putString("ship_cost", "$ 5.00");
                            editor.commit();
                        } else {
                            holder.perpric.setText("$ 5.99 /Pickup");
                            editor.putString("ship_cost", "$ 5.99");
                            editor.commit();
                        }

                        holder.ck_sml.setChecked(false);
                        holder.ck_med.setChecked(false);
                    } else if (!isChecked && !holder.ck_sml.isChecked() && !holder.ck_med.isChecked()) {

                        editor.putString("ship_size", "nil");
                        editor.commit();

                       /* holder.ck_sml.setChecked(true);
                        holder.ck_med.setChecked(false);
                        holder.ck_lrg.setChecked(false);*/
                    }
                }
            });


            holder.ck_add.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                   /* if(holder.ck_sml.isChecked() || holder.ck_med.isChecked() || holder.ck_lrg.isChecked()){

                    }*/


                    if (isChecked) {
                        editor.putString("ship_pickup", "Yes");
                        editor.commit();
                    } else {
                        editor.putString("ship_pickup", "No");
                        editor.commit();
                    }


                    if (isChecked && holder.ck_sml.isChecked()) {
                        holder.perpric.setText("$3.00 /Pickup");
                        editor.putString("ship_cost", "$ 3.00");
                        editor.commit();
                    } else if ((!isChecked && holder.ck_sml.isChecked())) {
                        holder.perpric.setText("$3.99 /Pickup");
                        editor.putString("ship_cost", "$ 3.99");
                        editor.commit();
                    } else if (isChecked && holder.ck_med.isChecked()) {
                        holder.perpric.setText("$4.00 /Pickup");
                        editor.putString("ship_cost", "$ 4.00");
                        editor.commit();
                    } else if (!isChecked && holder.ck_med.isChecked()) {
                        holder.perpric.setText("$4.99 /Pickup");
                        editor.putString("ship_cost", "$ 4.99");
                        editor.commit();
                    } else if (isChecked && holder.ck_lrg.isChecked()) {
                        holder.perpric.setText("$5.00 /Pickup");
                        editor.putString("ship_cost", "$ 5.00");
                        editor.commit();
                    } else if (!isChecked && holder.ck_lrg.isChecked()) {
                        holder.perpric.setText("$5.99 /Pickup");
                        editor.putString("ship_cost", "$ 5.99");
                        editor.commit();
                    } else {
                        Toast.makeText(activity, "Please add Size", Toast.LENGTH_SHORT).show();
                        holder.ck_add.setChecked(false);
                    }
                }
            });


            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();


        return vi;
    }


    public static class ViewHolder {

        public TextView header;
        public TextView perpric;
        public CheckBox ck_sml;
        public CheckBox ck_med;
        public CheckBox ck_lrg;
        public CheckBox ck_add;
        public ImageView iv_ship_img;

    }

}

