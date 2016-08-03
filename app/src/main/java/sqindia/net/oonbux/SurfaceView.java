package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.swipe.util.Attributes;
import com.rey.material.widget.Button;
import com.rey.material.widget.LinearLayout;
import com.rey.material.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import sqindia.net.oonbux.Activity.ChooseAddress;
import sqindia.net.oonbux.Activity.ChoosePal_1;
import sqindia.net.oonbux.Activity.DashBoardActivity;
import sqindia.net.oonbux.Adapter.Adapter_Shipment;
import sqindia.net.oonbux.config.DbC;

//asdfas
public class SurfaceView extends Activity {

    ListView list;
    private SurfaceList mAdapter;
    Adapter_Shipment adapt;
    DbC dbclass;
    Context context = this;
    ArrayList<String> selectedPhotos = new ArrayList<>();
    public final static int REQUEST_CODE = 1;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private SQLiteDatabase db;
    public ArrayList<String> shipment_photos;
    ImageButton btn_next;
    TextView header;
    ImageView lt_back, lt_add;
    Button btn_delete, btn_addpal, btn_continue;

    public ArrayList<String> ship_size = new ArrayList<>();
    public ArrayList<String> ship_pickup = new ArrayList<>();
    public ArrayList<String> ship_photo = new ArrayList<>();

    public String[] cartss ;
    public ArrayList<String> choose_cart = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surfaceview);

        shipment_photos = new ArrayList<>();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SurfaceView.this);

        String size = sharedPreferences.getString("ship_size", "");
        String pickup = sharedPreferences.getString("ship_pickup", "");
        String photo = sharedPreferences.getString("shipment_photo", "");

        list = (ListView) findViewById(R.id.listview);

        header = (TextView) findViewById(R.id.tv_hd_txt);
        lt_back = (ImageView) findViewById(R.id.layout_back);
        lt_add = (ImageView) findViewById(R.id.layout_add);
        btn_continue = (Button) findViewById(R.id.button_continue);
        //btn_next = (ImageButton) findViewById(R.id.button_next);
        btn_addpal = (Button) findViewById(R.id.button_addpal);


//        mAdapter.setMode(Attributes.Mode.Single);

        dbclass = new DbC(context);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");


        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");

        header.setTypeface(tf);
        btn_addpal.setTypeface(tf1);
        btn_continue.setTypeface(tf1);

        getFromDB();

        ship_size.add(size);
        ship_photo.add(photo);


        btn_addpal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor = sharedPreferences.edit();
                editor.putString("shipment_photo", "");
                editor.commit();

                if(mAdapter.choose_cart_from.size() > 0){
                    Intent newe = new Intent(getApplicationContext(), ChoosePal_1.class);
                    startActivity(newe);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please choose items",Toast.LENGTH_SHORT).show();
                }
            }
        });


        lt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("ship_pickup", "no");
                editor.commit();

                shipment_photos.clear();
                PhotoPickerIntent intent = new PhotoPickerIntent(getApplicationContext());
                intent.setPhotoCount(1);
                intent.setColumn(4);
                intent.setShowCamera(true);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editor = sharedPreferences.edit();
                editor.putString("shipment_photo", "");
                editor.commit();

                if(mAdapter.choose_cart_from.size() > 0){
                    Log.e("tag",""+"\n"+mAdapter.choose_cart_from.size());
                    Intent inte = new Intent(getApplicationContext(), ChooseAddress.class);
                    startActivity(inte);
                }
                else{
                    Log.e("tag",""+"\n"+mAdapter.choose_cart_from.size());
                    Toast.makeText(getApplicationContext(),"Please choose Any item",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void getFromDB() {
        ArrayList<DbGclass> dbdatalist = new ArrayList<DbGclass>();
        dbdatalist.clear();
        String query = " SELECT * FROM cart";
        Cursor c = dbclass.fetchdata(query);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    DbGclass dbc = new DbGclass();
                    dbc.set_id(String.valueOf(c.getInt(c.getColumnIndex("id"))));
                    dbc.set_size(c.getString(c.getColumnIndex("size")));
                    dbc.set_pickup(c.getString(c.getColumnIndex("pickup")));
                    dbc.set_photo(c.getString(c.getColumnIndex("photo")));
                    dbc.set_cost(c.getString(c.getColumnIndex("cost")));
                    dbdatalist.add(dbc);
                } while (c.moveToNext());
            }
        }
        mAdapter = new SurfaceList(SurfaceView.this,dbdatalist);
        list.setAdapter(mAdapter);
        mAdapter.setMode(Attributes.Mode.Single);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dbclass.del_last_row();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<String> photos = null;
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();
            if (photos != null) {
                selectedPhotos.addAll(photos);
            }
            Uri uri = Uri.fromFile(new File(selectedPhotos.get(0)));
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("shipment_photo1", selectedPhotos.get(0));
            editor.putString("shipment_photo", selectedPhotos.get(0));
            editor.commit();
            shipment_photos.add(selectedPhotos.get(0));
            Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
            startActivity(inte);
        }
    }









}
