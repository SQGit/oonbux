package sqindia.net.oonbux;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Salman on 5/27/2016.
 */
public class Adap_Shipment extends BaseAdapter {

    Context c1;
    ArrayList<DbGclass> arrayList;

    LinearLayout layout;
    ImageView iview;
    com.rey.material.widget.TextView tv_size, tv_price, tv_size_txt, tv_pickup, tv_pickup_txt, tv_price_txt, opt;
    SQLiteDatabase db;
    Uri uri;
    Bitmap bitmap;

    DbC dbclass;


    public Adap_Shipment(Context c1, ArrayList<DbGclass> list) {
        this.c1 = c1;
        arrayList = list;

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        DbGclass dbc = arrayList.get(position);

        LayoutInflater inflat = (LayoutInflater) c1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflat.inflate(R.layout.shipment_next_apapter, null);

        String k = "sadsf";


        Typeface tf = Typeface.createFromAsset(c1.getAssets(), "fonts/prox.otf");


        dbclass = new DbC(c1);


        tv_size = (com.rey.material.widget.TextView) view.findViewById(R.id.tv_size);
        tv_price = (com.rey.material.widget.TextView) view.findViewById(R.id.tv_price);
        tv_pickup = (com.rey.material.widget.TextView) view.findViewById(R.id.tv_pickup);
        tv_pickup_txt = (com.rey.material.widget.TextView) view.findViewById(R.id.tv_pickup_txt);
        tv_price_txt = (com.rey.material.widget.TextView) view.findViewById(R.id.tv_price_txt);
        tv_size_txt = (com.rey.material.widget.TextView) view.findViewById(R.id.tv_size_txt);

        opt = (com.rey.material.widget.TextView) view.findViewById(R.id.tv_op);


        iview = (ImageView) view.findViewById(R.id.imgview);

        tv_size.setTypeface(tf);
        tv_price.setTypeface(tf);
        tv_pickup.setTypeface(tf);
        tv_size_txt.setTypeface(tf);
        tv_price_txt.setTypeface(tf);
        tv_pickup_txt.setTypeface(tf);


        opt.setText(dbc.get_id());
        opt.setVisibility(View.INVISIBLE);

        Log.d("tag", "" + dbc.get_id());

        tv_size.setText(dbc.get_size());
        tv_pickup.setText(dbc.get_pickup());

        uri = Uri.fromFile(new File(dbc.get_photo()));


        try {
            bitmap = null;
            bitmap = MediaStore.Images.Media.getBitmap(c1.getContentResolver(), uri);
            bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, false);
            iview.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }


        return view;
    }
}
