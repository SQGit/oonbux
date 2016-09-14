package sqindia.net.oonbux.Fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.rey.material.widget.Button;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.TextView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import sqindia.net.oonbux.Cart;
import sqindia.net.oonbux.Dialog.Dialog_Anim_Loading;
import sqindia.net.oonbux.Dialog.Dialog_Msg;
import sqindia.net.oonbux.R;
import sqindia.net.oonbux.config.Config;
import sqindia.net.oonbux.config.HttpUtils;


public class DeliverPackageFragment extends Fragment {


    public final static int REQUEST_CODE = 1;
    public ArrayList<String> shipment_photos;
    //  ListView lv_deliver_list;
    Button btn_addshipment, btn_nextshipment;
    ArrayList<String> selectedPhotos = new ArrayList<>();
    Button btn_nxt;
    ArrayList<String> ship_size = new ArrayList<>();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SharedPreferences.Editor edit;
    TextView tv_ship_txt, tv_ship_cost;
    CheckBox ck_small, ck_medium, ck_large;
    String shipment_photo;
    Typeface tf;
    ImageView iv_ship_img;
    Dialog_Anim_Loading dialog_loading;
    String size, pickup, photo, cost, session_id;
    Float k;
    String encode_img;
    private SQLiteDatabase db;

    public static byte[] inputStreamToByteArray(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View getview = inflater.inflate(R.layout.fragment_deliver_package, container, false);
        createDatabase();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sharedPreferences.edit();
        editor.putString("ship_size", "nil");
        editor.commit();
        shipment_photo = sharedPreferences.getString("shipment_photo", "");
        String cktis = sharedPreferences.getString("fromdash", "");

        session_id = sharedPreferences.getString("sessionid", "");

        tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/prox.otf");
        Log.e("tag", "0" + cktis);


        shipment_photos = new ArrayList<>();
        shipment_photos.add(shipment_photo);
        Log.e("tag", "1" + shipment_photos.get(0));

        //  lv_deliver_list = (ListView) getview.findViewById(R.id.deliver_list);
        btn_addshipment = (Button) getview.findViewById(R.id.add_sp_btn);
        btn_nxt = (Button) getview.findViewById(R.id.button_next);


        tv_ship_txt = (TextView) getview.findViewById(R.id.tv_ship_header);
        tv_ship_cost = (TextView) getview.findViewById(R.id.tv_per);

        ck_small = (CheckBox) getview.findViewById(R.id.cb_small);
        ck_medium = (CheckBox) getview.findViewById(R.id.cb_medium);
        ck_large = (CheckBox) getview.findViewById(R.id.cb_large);

        iv_ship_img = (ImageView) getview.findViewById(R.id.imgview);




        btn_nxt.setTypeface(tf);
        btn_addshipment.setTypeface(tf);
        ck_small.setTypeface(tf);
        ck_medium.setTypeface(tf);
        ck_large.setTypeface(tf);
        tv_ship_cost.setTypeface(tf);
        tv_ship_txt.setTypeface(tf);

        // btn_nextshipment = (Button) getview.findViewById(R.id.next_button);

      /*  ship_adapter = new Shipment_Adapter(getActivity(), shipment_photos);
        lv_deliver_list.setAdapter(ship_adapter);*/

        /*adapt = new Adapter_Shipment(getActivity(), shipment_photos);
        lv_deliver_list.setAdapter(adapt);*/


        if (!ck_small.isChecked() && !ck_medium.isChecked() && !ck_large.isChecked()) {
            Log.e("tag", "cksmall");
            btn_nxt.setVisibility(View.GONE);
        } else {
            btn_nxt.setVisibility(View.VISIBLE);
        }


        ck_small.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ck_medium.setChecked(false);
                    ck_large.setChecked(false);

                    tv_ship_cost.setText("$ 3.99 /Pickup");

                    editor.putString("ship_size", "Small");
                    editor.putString("ship_cost", "$ 3.99");
                    editor.commit();
                    Log.e("tag", "small_if");

                } else {
                    editor.putString("ship_size", "nil");
                    editor.commit();
                    Log.e("tag", "small_else");

                    tv_ship_cost.setText("$ 3.00 /Pickup");
                }


                if (!ck_small.isChecked() && !ck_medium.isChecked() && !ck_large.isChecked()) {
                    Log.e("tag", "cksmall");
                    btn_nxt.setVisibility(View.GONE);
                } else {
                    btn_nxt.setVisibility(View.VISIBLE);
                }


            }
        });


        ck_medium.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ck_small.setChecked(false);
                    ck_large.setChecked(false);

                    tv_ship_cost.setText("$ 4.99 /Pickup");

                    editor.putString("ship_size", "Medium");
                    editor.putString("ship_cost", "$ 4.99");
                    editor.commit();
                    Log.e("tag", "medium_if");
                } else {
                    editor.putString("ship_size", "nil");
                    editor.commit();
                    Log.e("tag", "medium_else");

                    tv_ship_cost.setText("$ 3.00 /Pickup");
                }

                if (!ck_small.isChecked() && !ck_medium.isChecked() && !ck_large.isChecked()) {
                    Log.e("tag", "cksmall");
                    btn_nxt.setVisibility(View.GONE);
                } else {
                    btn_nxt.setVisibility(View.VISIBLE);
                }


            }
        });

        ck_large.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ck_small.setChecked(false);
                    ck_medium.setChecked(false);

                    tv_ship_cost.setText("$ 5.99 /Pickup");


                    editor.putString("ship_size", "Large");
                    editor.putString("ship_cost", "$ 5.99");
                    editor.commit();
                    Log.e("tag", "large_if");
                } else {

                    editor.putString("ship_size", "nil");
                    editor.commit();
                    Log.e("tag", "large_else");

                    tv_ship_cost.setText("$ 3.00 /Pickup");
                }


                if (!ck_small.isChecked() && !ck_medium.isChecked() && !ck_large.isChecked()) {
                    Log.e("tag", "cksmall");
                    btn_nxt.setVisibility(View.GONE);
                } else {
                    btn_nxt.setVisibility(View.VISIBLE);
                }


            }
        });









        if (cktis.equals("asdfg")) {

            Log.e("tag",""+"dash0");

            btn_addshipment.setVisibility(View.VISIBLE);
            // adapt = new Adapter_Shipment(getActivity(), shipment_photos);
            // lv_deliver_list.setAdapter(adapt);

            Picasso.with(getActivity())
                    .load(new File(shipment_photos.get(0)))
                    .fit()
                    .into(iv_ship_img);


        } else {


            if(sharedPreferences.getString("shipment_photo","")!= null) {

                shipment_photos.add(sharedPreferences.getString("shipment_photo",""));
                Log.e("tag", "2" + shipment_photos.get(0));

                Log.e("tag",""+"potos");


                Picasso.with(getActivity())
                        .load(new File(shipment_photos.get(0)))
                        .fit()
                        .into(iv_ship_img);

                //  btn_nxt.setVisibility(View.VISIBLE);
                btn_addshipment.setVisibility(View.VISIBLE);


            }
            else {

                Log.e("tag", "" + "dash1");
                btn_addshipment.setVisibility(View.VISIBLE);
                // btn_nxt.setVisibility(View.VISIBLE);
            }
        }


        //adapt.notifyDataSetChanged();

        //  ship_adapter.notifyDataSetChanged();


        btn_addshipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shipment_photos.clear();
                PhotoPickerIntent intent = new PhotoPickerIntent(getActivity());
                intent.setPhotoCount(1);
                intent.setColumn(4);
                intent.setShowCamera(true);
                startActivityForResult(intent, REQUEST_CODE);
                //btn_nxt.setVisibility(View.VISIBLE);

            }
        });

      /*  btn_nextshipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ioi = new Intent(getActivity(),ShipmentNext.class);
                startActivity(ioi);
            }
        });*/

        btn_nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                size = sharedPreferences.getString("ship_size", "");
                pickup = "YES";//sharedPreferences.getString("ship_pickup", "");
                photo = sharedPreferences.getString("shipment_photo", "");
                cost = sharedPreferences.getString("ship_cost", "");

                cost = cost.trim();
                k = Float.valueOf(cost.substring(2));
                Log.e("tag", "asd " + k);


                if (size.equals("nil")) {

                    Toast.makeText(getActivity(), "Please add Size for Shipment", Toast.LENGTH_SHORT).show();

                    /*new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Please Add size for shipment")
                            .setConfirmText("Ok")

                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismiss();
                                }
                            })
                            .show();*/


                } else {


                    Random rand = new Random();
                    Calendar ca = Calendar.getInstance();
                    int m = ca.get(Calendar.MONTH);
                    int d = ca.get(Calendar.DATE);
                    int y = ca.get(Calendar.YEAR);
                    int cHour = ca.get(Calendar.HOUR);
                    int cMinute = ca.get(Calendar.MINUTE);
                    int cSecond = ca.get(Calendar.SECOND);
                    int min = cHour + cMinute + cSecond;
                    int randomNum = rand.nextInt((1000 - min)) + min;
                    String cart_id = "OO" + d + "CR" + m + "T" + randomNum;
                    Log.e("tag", min + "rand" + randomNum + "ad" + cart_id);


                    pickup = cart_id;

                    insertIntoDB(size, pickup, photo,cost);

                    Log.e("tag", size + pickup + photo + cost);

                    encode_img = null;

                 /*   Bitmap bm = BitmapFactory.decodeFile(photo);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
                    byte[] b = baos.toByteArray();*/


                    FileInputStream fis = null;
                    try {
                        fis = new FileInputStream(photo);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }


                    byte[] byteArray = new byte[0];
                    try {
                        byteArray = inputStreamToByteArray(fis);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    Log.e("tag", "arr: " + byteArray);

                    encode_img = Base64.encodeToString(byteArray, Base64.DEFAULT);


                    // encode_img = Base64.encodeToString(b, Base64.DEFAULT);
                    new CartAddTask().execute();

                    Log.e("tag", "encode: " + encode_img);


                    editor.putString("shipment_photo1", "");
                    editor.commit();

                }


            }
        });


        return getview;
    }

    protected void createDatabase() {
        Log.d("tag", "createdb");
        db = getActivity().openOrCreateDatabase("oonbux", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS cart(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, size VARCHAR, pickup VARCHAR, photo VARCHAR, cost VARCHAR);");
    }

    protected void insertIntoDB(String a, String b, String c,String d) {
        Log.d("tag", "insertdb " + a + b + c);
        String query = "INSERT INTO cart (size,pickup,photo,cost) VALUES('" + a + "', '" + b + "', '" + c + "', '" + d + "');";
        db.execSQL(query);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        List<String> photos = null;
        if (resultCode == getActivity().RESULT_OK && requestCode == REQUEST_CODE) {

            Log.d("tag", "worked");
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();

            if (photos != null) {

                selectedPhotos.addAll(photos);
            }


            Uri uri = Uri.fromFile(new File(selectedPhotos.get(0)));

            Log.d("tag", selectedPhotos.get(0));
            Log.d("tag", "" + uri);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("shipment_photo", selectedPhotos.get(0));
            editor.commit();
            shipment_photos.add(selectedPhotos.get(0));
            // ship_adapter.notifyDataSetChanged();
            Log.d("tag", "2" + shipment_photos.get(0));

            Picasso.with(getActivity())
                    .load(new File(shipment_photos.get(0)))
                    .fit()
                    .into(iv_ship_img);

            //lv_deliver_list.setAdapter(adapt);
            //   adapt.notifyDataSetChanged();
            //btn_nxt.setVisibility(View.VISIBLE);
            btn_addshipment.setVisibility(View.VISIBLE);

        }
    }


    class CartAddTask extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();


            dialog_loading = new Dialog_Anim_Loading(getActivity());
            dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
            dialog_loading.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog_loading.setCancelable(false);
            dialog_loading.show();
            WindowManager.LayoutParams lp = dialog_loading.getWindow().getAttributes();
            lp.dimAmount = 1.80f;
            dialog_loading.getWindow().setAttributes(lp);
            dialog_loading.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            Log.e("tag", "e" + size + cost + pickup);
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("shipment_size", size);
                jsonObject.accumulate("shipment_cost", k);
                jsonObject.accumulate("shipment_image", encode_img);

                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest2(Config.SER_URL + "cart/add", json, session_id);
            } catch (Exception e) {
                Log.e("InputStream", e.getLocalizedMessage());
                dialog_loading.dismiss();

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("tag", "<-----rerseres---->" + s);
            dialog_loading.dismiss();
            super.onPostExecute(s);

            if (s != null) {
                try {
                    JSONObject jo = new JSONObject(s);
                    String status = jo.getString("status");
                    String msg = jo.getString("message");
                    Log.d("tag", "<-----Status----->" + status);
                    if (status.equals("success")) {


                        String cart_id = jo.getString("cart_id");

                        Intent ioi = new Intent(getActivity(), Cart.class);
                        startActivity(ioi);


                    } else if (status.equals("fail")) {

                        Dialog_Msg dialog_reg_fail = new Dialog_Msg(getActivity(), msg);
                        dialog_reg_fail.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog_reg_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        dialog_reg_fail.setCancelable(false);
                        dialog_reg_fail.show();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Dialog_Msg dialog_fail = new Dialog_Msg(getActivity(), "Network Error,Please Try Again Later.");
                    dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog_fail.show();
                }
            } else {
                Dialog_Msg dialog_fail = new Dialog_Msg(getActivity(), "Network Error,Please Try Again Later.");
                dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog_fail.show();
            }
        }
    }



}
