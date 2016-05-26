package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Context;
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

import com.rey.material.widget.Button;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class ShipmentNextAdpater extends BaseAdapter {

    Context c1;
    CheckBox ck_sml;
    Button btn_add_pal;
    ArrayList<String> shipment_size, shipment_photo;
    int ls = 3;
    Uri uri;
    Bitmap bitmap;
    Activity act;


    public ShipmentNextAdpater(Activity aaa, Context c1, ArrayList<String> size, ArrayList<String> photo) {
        this.c1 = c1;
        this.shipment_size = size;
        this.shipment_photo = photo;
        this.act = aaa;

    }


    @Override
    public int getCount() {
        Log.e("tagin", "" + shipment_size.size());
        return shipment_size.size();

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
    public View getView(int position, View view, ViewGroup parent) {

        LinearLayout layout;
        ImageView iview;
        TextView tv_size, tv_price, tv_size_txt, tv_pickup, tv_pickup_txt, tv_price_txt;


        LayoutInflater inflat = (LayoutInflater) c1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflat.inflate(R.layout.shipment_next_apapter, null);

        Typeface tf = Typeface.createFromAsset(c1.getAssets(), "fonts/prox.otf");

        layout = (LinearLayout) view.findViewById(R.id.linearlayout);
        iview = (ImageView) view.findViewById(R.id.imgview);

        tv_size = (TextView) view.findViewById(R.id.tv_size);
        tv_price = (TextView) view.findViewById(R.id.tv_price);
        tv_pickup = (TextView) view.findViewById(R.id.tv_pickup);
        tv_pickup_txt = (TextView) view.findViewById(R.id.tv_pickup_txt);
        tv_price_txt = (TextView) view.findViewById(R.id.tv_price_txt);
        tv_size_txt = (TextView) view.findViewById(R.id.tv_size_txt);

        tv_size.setTypeface(tf);
        tv_price.setTypeface(tf);
        tv_pickup.setTypeface(tf);
        tv_size_txt.setTypeface(tf);
        tv_price_txt.setTypeface(tf);
        tv_pickup_txt.setTypeface(tf);


        for (int i = 0; i < shipment_size.size(); i++) {

            tv_size.setText(shipment_size.get(i));


            uri = Uri.fromFile(new File(shipment_photo.get(i)));


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


        }


        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SweetAlertDialog dialog = new SweetAlertDialog(act, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Do you want to Delete this Shipment?")
                        .setConfirmText("Yes!")
                        .setCancelText("No")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                shipment_size.remove(shipment_size.size() - 1);
                                notifyDataSetChanged();
                                sDialog.dismiss();
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        });
                dialog.show();
            }
        });





   /*     btn_add_pal = (Button) view.findViewById(R.id.button_next);

        btn_add_pal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newe = new Intent(c1, ChoosePal.class);
                newe.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                c1.startActivity(newe);
            }
        });*/

//        ck_sml = (CheckBox) view.findViewById(R.id.cb_small);


        // Typeface tf = Typeface.createFromAsset(c1.getAssets(), "fonts/prox.otf");
//

//        btn_add_pal.setTypeface(tf);


        return view;
    }


}
