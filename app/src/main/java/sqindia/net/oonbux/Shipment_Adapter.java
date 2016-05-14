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

   /* @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }*/

    @Override
    public int getCount() {
        return 1;
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

        LayoutInflater inflat = (LayoutInflater) c1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflat.inflate(R.layout.shipment_listview_adapter, null);

        header = (TextView) view.findViewById(R.id.tv_ship_header);
        perpric = (TextView) view.findViewById(R.id.tv_per);

        ck_sml = (CheckBox) view.findViewById(R.id.cb_small);
        ck_med = (CheckBox) view.findViewById(R.id.cb_medium);
        ck_lrg = (CheckBox) view.findViewById(R.id.cb_large);
        ck_add = (CheckBox) view.findViewById(R.id.cb_addto);

        imgview = (ImageView) view.findViewById(R.id.imgview);


        Typeface tf = Typeface.createFromAsset(c1.getAssets(), "fonts/prox.otf");

        header.setTypeface(tf);
        perpric.setTypeface(tf);
        ck_sml.setTypeface(tf);
        ck_med.setTypeface(tf);
        ck_lrg.setTypeface(tf);
        ck_add.setTypeface(tf);


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


 /*       ck_sml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perpric.setText("$3.99 /pickup");
            }
        });

        ck_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perpric.setText("$4.99 /pickup");
            }
        });


        ck_sml.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                              @Override
                                              public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                  if (isChecked) {
                                                      ck_med.setChecked(false);
                                                      ck_lrg.setChecked(false);
                                                      perpric.setText("$3.99 /pickup");
                                                      Log.e("tag", "s11");

                                                  }



                                                 *//*  if(ck_med.isChecked()) {
                                                       ck_med.setChecked(false);
                                                   }
                                                   else if (ck_lrg.isChecked()) {
                                                       ck_lrg.setChecked(false);
                                                   }*//*
                                                  perpric.setText("$3.99 /pickup");
                                                  Log.e("tag", "s1");

                                              }
                                          }
        );

        ck_med.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                              @Override
                                              public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                                                  if (ck_sml.isChecked()) {
                                                      ck_sml.setChecked(false);
                                                  } else if (ck_lrg.isChecked()) {
                                                      ck_lrg.setChecked(false);
                                                  }

                                                  perpric.setText("$4.99 /pickup");
                                                  Log.e("tag", "s2");
                                              }
                                          }
        );
        ck_lrg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                                              @Override
                                              public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                  if (ck_sml.isChecked()) {
                                                      ck_sml.setChecked(false);
                                                  } else if (ck_med.isChecked()) {
                                                      ck_med.setChecked(false);
                                                  }
                                                  perpric.setText("$5.99 /pickup");
                                                  Log.e("tag", "s3");
                                              }
                                          }
        );


        ck_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ck_sml.isChecked()) {
                    perpric.setText("$3 /pickup");
                } else if (ck_med.isChecked()) {
                    perpric.setText("$4 /pickup");
                } else if (ck_lrg.isChecked()) {
                    perpric.setText("$5 /pickup");
                } else {
                    Toast.makeText(c1, "Please add quantity", Toast.LENGTH_LONG).show();
                }
                //ck_lrg.setChecked(true);
            }
        });
*/

/*

        ck_sml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ck_sml.setChecked(true);
                ck_med.setChecked(false);
                ck_lrg.setChecked(false);
                perpric.setText("$3.99 /pickup");
            }
        });

        ck_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ck_med.setChecked(true);
                ck_sml.setChecked(false);
                ck_lrg.setChecked(false);
                perpric.setText("$4.99 /pickup");
            }
        });

        ck_lrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ck_lrg.setChecked(true);
                ck_med.setChecked(false);
                ck_sml.setChecked(false);
                perpric.setText("$5.99 /pickup");
            }
        });


        ck_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ck_sml.isChecked()) {
                    perpric.setText("$3 /pickup");
                } else if (ck_med.isChecked()) {
                    perpric.setText("$4 /pickup");
                } else if (ck_lrg.isChecked()) {
                    perpric.setText("$5 /pickup");
                } else {
                    Toast.makeText(c1, "Please add quantity", Toast.LENGTH_LONG).show();
                }

            }
        });
*/
/*


        ck_sml.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    perpric.setText("$3.99 /pickup");
                    ck_med.setChecked(false);
                    ck_lrg.setChecked(false);
                }
            }
        });

        ck_med.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                perpric.setText("$4.99 /pickup");
                ck_sml.setChecked(false);
                ck_lrg.setChecked(false);
            }
        });


        ck_lrg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                perpric.setText("$5.99 /pickup");
                ck_med.setChecked(false);
                ck_lrg.setChecked(false);
            }
        });
*/







        return view;
    }


}
