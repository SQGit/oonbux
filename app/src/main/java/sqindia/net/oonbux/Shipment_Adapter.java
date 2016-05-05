package sqindia.net.oonbux;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.rey.material.widget.Button;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


public class Shipment_Adapter extends BaseAdapter {

    Context c1;
    TextView header, perpric;
    CheckBox ck_sml, ck_med, ck_lrg, ck_add;
    Button btn_addid;
    ArrayList<String> photos;
    ImageView imgview;
    Uri uri;
    Bitmap bitmap;


    public Shipment_Adapter(Context c1, ArrayList<String> photo) {
        this.c1 = c1;
        this.photos = photo;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {

        return 1;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflat = (LayoutInflater) c1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflat.inflate(R.layout.shipment_listview_adapter, null);

        header = (TextView) view.findViewById(R.id.tv_ship_header);
        perpric = (TextView) view.findViewById(R.id.tv_per);

        ck_sml = (CheckBox) view.findViewById(R.id.cb_small);
        ck_med = (CheckBox) view.findViewById(R.id.cb_medium);
        ck_lrg = (CheckBox) view.findViewById(R.id.cb_large);
        ck_add = (CheckBox) view.findViewById(R.id.cb_addto);
        //  btn_addid = (Button) view.findViewById(R.id.button_addid);

        imgview = (ImageView) view.findViewById(R.id.imgview);

        uri = Uri.fromFile(new File(photos.get(position)));


        try {
            bitmap = null;
            bitmap = MediaStore.Images.Media.getBitmap(c1.getContentResolver(), uri);
            bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        imgview.setImageBitmap(bitmap);


        ck_sml.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ck_med.setChecked(false);
                ck_lrg.setChecked(false);
                // ck_sml.setChecked(true);
            }
        });

        ck_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ck_sml.setChecked(false);
                ck_lrg.setChecked(false);
                //ck_med.setChecked(true);
            }
        });

        ck_lrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ck_med.setChecked(false);
                ck_sml.setChecked(false);
                //ck_lrg.setChecked(true);
            }
        });





    /*    ck_sml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ck_med.setChecked(false);
                ck_lrg.setChecked(false);
            }
        });

        ck_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ck_sml.setChecked(false);
                ck_lrg.setChecked(false);
            }
        });

        ck_lrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ck_med.setChecked(false);
                ck_sml.setChecked(false);
            }
        });*/


        Typeface tf = Typeface.createFromAsset(c1.getAssets(), "fonts/prox.otf");

        header.setTypeface(tf);
        perpric.setTypeface(tf);
        ck_sml.setTypeface(tf);
        ck_med.setTypeface(tf);
        ck_lrg.setTypeface(tf);
        ck_add.setTypeface(tf);
        // btn_addid.setTypeface(tf);


        return view;
    }


}
