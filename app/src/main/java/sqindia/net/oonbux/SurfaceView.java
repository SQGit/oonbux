package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.daimajia.swipe.util.Attributes;
import com.rey.material.widget.Button;
import com.rey.material.widget.LinearLayout;
import com.rey.material.widget.TextView;

import java.util.ArrayList;


public class SurfaceView extends Activity {

    ListView list;
    private SurfaceList mAdapter;

    DbC dbclass;
    Context context = this;

    SharedPreferences sharedPreferences;
    private SQLiteDatabase db;

    ImageButton btn_next;
    TextView header;
    com.rey.material.widget.LinearLayout lt_back, lt_add;
    Button btn_delete, btn_addpal, btn_continue;

    public ArrayList<String> ship_size = new ArrayList<>();
    public ArrayList<String> ship_pickup = new ArrayList<>();
    public ArrayList<String> ship_photo = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surfaceview);


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SurfaceView.this);

        String size = sharedPreferences.getString("ship_size", "");
        String pickup = sharedPreferences.getString("ship_pickup", "");
        String photo = sharedPreferences.getString("shipment_photo", "");

        list = (ListView) findViewById(R.id.listview);

        header = (TextView) findViewById(R.id.tv_hd_txt);
        lt_back = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_back);
        lt_add = (LinearLayout) findViewById(R.id.layout_add);
        btn_continue = (Button) findViewById(R.id.button_continue);
        btn_next = (ImageButton) findViewById(R.id.button_next);
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

                Intent newe = new Intent(getApplicationContext(), ChoosePal.class);
                startActivity(newe);

            }
        });


        lt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent newe = new Intent(getApplicationContext(), DashBoardActivity.class);
                startActivity(newe);

            }
        });


        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                startActivity(inte);*/
                onBackPressed();
            }
        });


        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent inte = new Intent(getApplicationContext(), ChooseAddress.class);
                startActivity(inte);

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
                    dbdatalist.add(dbc);
                } while (c.moveToNext());
            }
        }


       // adapter1 = new Adap_Shipment(ShipmentNext.this, dbdatalist);



        mAdapter = new SurfaceList(SurfaceView.this,dbdatalist);
        list.setAdapter(mAdapter);

        mAdapter.setMode(Attributes.Mode.Single);


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        dbclass.del_last_row();


    }
}
