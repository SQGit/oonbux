package sqindia.net.oonbux;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.rey.material.widget.Button;
import com.rey.material.widget.CheckBox;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

//asdfdsaf
public class SurfaceList extends BaseSwipeAdapter {

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
    String price;
    CheckBox cb_cart;
        SurfaceView surface;

    public ArrayList<String> choose_cart_from = new ArrayList<>();

    public SurfaceList(Context mContext, ArrayList<DbGclass> list) {

        this.arrayList = new ArrayList<>();
        this.arrayList.clear();
        this.mContext = mContext;
        this.arrayList = list;
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

        View v = LayoutInflater.from(mContext).inflate(R.layout.surfaceitem, null);
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/nexa.otf");

        SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));

/*        swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                Log.d("tag","worked");
            }
        });*/

        swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });


        bb = (Button) v.findViewById(R.id.delete);


        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "deleted", Toast.LENGTH_SHORT).show();
               /* arrayList.remove(arrayList.size() - 1);
                notifyDataSetChanged();*/
                Log.e("tag", asdf + position);
                dbclass.deletedata("aa", asdf);
                arrayList.remove(position);
                notifyDataSetChanged();
            }
        });


        tv_size = (com.rey.material.widget.TextView) v.findViewById(R.id.tv_size);
        tv_price = (com.rey.material.widget.TextView) v.findViewById(R.id.tv_price);
        tv_pickup = (com.rey.material.widget.TextView) v.findViewById(R.id.tv_pickup);
        tv_pickup_txt = (com.rey.material.widget.TextView) v.findViewById(R.id.tv_pickup_txt);
        tv_price_txt = (com.rey.material.widget.TextView) v.findViewById(R.id.tv_price_txt);
        tv_size_txt = (com.rey.material.widget.TextView) v.findViewById(R.id.tv_size_txt);

        cb_cart = (CheckBox) v.findViewById(R.id.cb_addto);
        opt = (com.rey.material.widget.TextView) v.findViewById(R.id.tv_op);
        iv_ship_img = (ImageView) v.findViewById(R.id.imgview);

        tv_size.setTypeface(tf);
        tv_price.setTypeface(tf);
        tv_pickup.setTypeface(tf);
        tv_size_txt.setTypeface(tf);
        tv_price_txt.setTypeface(tf);
        tv_pickup_txt.setTypeface(tf);
        bb.setTypeface(tf);


        opt.setText(dbc.get_id());
        opt.setVisibility(View.INVISIBLE);

        Log.e("tag", "" + dbc.get_id());

        tv_size.setText(dbc.get_size());
        tv_pickup.setText(dbc.get_pickup());

        tv_price.setText(dbc.get_cost());

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


        Picasso.with(mContext)
                .load(new File(dbc.get_photo()))
                .fit()
                .into(iv_ship_img);

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
}
