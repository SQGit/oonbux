package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.rey.material.widget.Button;
import com.rey.material.widget.CheckBox;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import sqindia.net.oonbux.Dialog.Dialog_Anim_Loading;
import sqindia.net.oonbux.Dialog.Dialog_Msg;
import sqindia.net.oonbux.config.Config;
import sqindia.net.oonbux.config.DbC;
import sqindia.net.oonbux.config.DbGclass;
import sqindia.net.oonbux.config.HttpUtils;

//asdfdsaf
public class CartData extends BaseSwipeAdapter {

    ArrayList<DbGclass> arrayList;
    LinearLayout layout;
    ImageView iv_ship_img;
    com.rey.material.widget.TextView tv_size, tv_price, tv_size_txt, tv_pickup, tv_pickup_txt, tv_price_txt, opt;
    SQLiteDatabase db;
    Uri uri;
    Bitmap bitmap;
    Button bb;
    DbC dbclass;
    String asdf;
    private Context mContext;
    Activity activity;
    String price;
    CheckBox cb_cart;
        Cart surface;
    ImageView img_delete;
    Dialog_Anim_Loading dialog_loading;
    String session_id;
    String cart_id;
    int poss;

    public ArrayList<String> choose_cart_from = new ArrayList<>();

    public CartData(Activity mContext, ArrayList<DbGclass> list,String session) {

        this.arrayList = new ArrayList<>();
        this.arrayList.clear();
        this.activity = mContext;
        this.arrayList = list;
        this.session_id = session;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.sample;
    }

    @Override
    public View generateView(final int position, ViewGroup parent) {


        DbGclass dbc = arrayList.get(position);
        dbclass = new DbC(mContext);
        asdf = dbc.get_id();

        View v = LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.surfaceitem, null);
        Typeface tf = Typeface.createFromAsset(activity.getApplicationContext().getAssets(), "fonts/prox.otf");

        final SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));

/*        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                Log.d("tag","worked");
            }
        });*/

        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
               // Toast.makeText(activity.getApplicationContext(), "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });



       // bb = (Button) v.findViewById(R.id.delete);

        img_delete = (ImageView) v.findViewById(R.id.img_delete);


        img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DbGclass dbc = arrayList.get(position);

               // dbclass.deletedata("aa", asdf);
                cart_id = dbc.get_id();
                Log.e("tag", asdf + position+"\t"+cart_id);
                poss = position;
                new CartDeleteTask().execute();



                notifyDataSetChanged();
                swipeLayout.close();
            }
        });


/*
        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "deleted", Toast.LENGTH_SHORT).show();
               *//**//* arrayList.remove(arrayList.size() - 1);
                notifyDataSetChanged();*//**//*
                Log.e("tag", asdf + position);
                dbclass.deletedata("aa", asdf);
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });*/


        tv_size = (com.rey.material.widget.TextView) v.findViewById(R.id.tv_size);
        tv_price = (com.rey.material.widget.TextView) v.findViewById(R.id.tv_price);
        tv_pickup = (com.rey.material.widget.TextView) v.findViewById(R.id.tv_pickup);
        tv_pickup_txt = (com.rey.material.widget.TextView) v.findViewById(R.id.tv_pickup_txt);
        tv_price_txt = (com.rey.material.widget.TextView) v.findViewById(R.id.tv_price_txt);
        tv_size_txt = (com.rey.material.widget.TextView) v.findViewById(R.id.tv_size_txt);

        cb_cart = (CheckBox) v.findViewById(R.id.cb_addto);

        iv_ship_img = (ImageView) v.findViewById(R.id.imgview);

        tv_size.setTypeface(tf);
        tv_price.setTypeface(tf);
        tv_pickup.setTypeface(tf);
        tv_size_txt.setTypeface(tf);
        tv_price_txt.setTypeface(tf);
        tv_pickup_txt.setTypeface(tf);
//        bb.setTypeface(tf);




        Log.e("tag", "" + dbc.get_id());

        tv_size.setText(dbc.get_size());
        tv_pickup.setText(dbc.get_id());



        String price = String.format("%.02f",Float.valueOf(dbc.get_cost()));


        tv_price.setText(price +" $");


        //tv_pickup.setVisibility(View.GONE);
        //tv_pickup_txt.setVisibility(View.GONE);



        cb_cart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    choose_cart_from.add(String.valueOf(position));
                    Log.e("tag",""+choose_cart_from.size());
                }
                else {
                    choose_cart_from.remove(choose_cart_from.size()-1);
                    Log.e("tag",""+choose_cart_from.size());
                }
            }
        });


    /*    Picasso.with(mContext)
                .load(new File(dbc.get_photo()))
                .fit()
                .into(iv_ship_img);*/

        return v;
    }


    @Override
    public void fillValues(int position, View convertView) {

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




    class CartDeleteTask extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            super.onPreExecute();



            dialog_loading = new Dialog_Anim_Loading(activity);
            dialog_loading.getWindow().setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(android.R.color.transparent)));
            dialog_loading.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            dialog_loading.setCancelable(false);
            dialog_loading.show();
            WindowManager.LayoutParams lp = dialog_loading.getWindow().getAttributes();
            lp.dimAmount=1.80f;
            dialog_loading.getWindow().setAttributes(lp);
            dialog_loading.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";
            Log.e("tag","e");
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("cart_id", cart_id);
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest2(Config.SER_URL + "cart/delete", json,session_id);
            } catch (Exception e) {
                Log.e("InputStream", e.getLocalizedMessage());

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

                        Toast.makeText(activity.getApplicationContext(), "Cart Deleted", Toast.LENGTH_SHORT).show();

                        arrayList.remove(poss);
                        notifyDataSetChanged();


                    }
                    else if (status.equals("fail")) {

                        Dialog_Msg dialog_reg_fail = new Dialog_Msg(activity, msg);
                        dialog_reg_fail.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog_reg_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        dialog_reg_fail.setCancelable(false);
                        dialog_reg_fail.show();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Dialog_Msg dialog_fail = new Dialog_Msg(activity, "Network Error,Please Try Again Later.");
                    dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialog_fail.show();
                }
            } else {
                Dialog_Msg dialog_fail = new Dialog_Msg(activity, "Network Error,Please Try Again Later.");
                dialog_fail.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                dialog_fail.show();
            }
        }
    }








}
