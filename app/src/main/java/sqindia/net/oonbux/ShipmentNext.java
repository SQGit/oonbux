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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.rey.material.widget.Button;
import com.rey.material.widget.LinearLayout;
import com.rey.material.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Salman on 5/18/2016.
 */
public class ShipmentNext extends Activity {

    public static ListView lsview;
    public static ShipmentNextAdpater shipadpt;
    public ArrayList<String> ship_size = new ArrayList<>();
    public ArrayList<String> ship_pickup = new ArrayList<>();
    public ArrayList<String> ship_photo = new ArrayList<>();
    ImageButton btn_next;
    TextView header;
    com.rey.material.widget.LinearLayout lt_back, lt_add;
    Button btn_delete, btn_addpal, btn_continue;
    Set<String> sets = new HashSet<String>();
    DbC dbclass;
    Context context = this;
    Adap_Shipment adapter1;
    SharedPreferences sharedPreferences;
    private SQLiteDatabase db;


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        dbclass.del_last_row();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipment_next);

        dbclass = new DbC(context);

        // ship_size = new ArrayList<>();
        //ship_photo = new ArrayList<>();

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ShipmentNext.this);

        String size = sharedPreferences.getString("ship_size", "");
        String pickup = sharedPreferences.getString("ship_pickup", "");
        String photo = sharedPreferences.getString("shipment_photo", "");

       /* Set<String> set = sharedPreferences.getStringSet("ship", null);
        ship_size=new ArrayList<String>(set);*/

        ship_size.add(size);
        ship_photo.add(photo);

/*       // Log.e("tag2",ship_size.get(0)+"\t"+ship_photo.get(0));
        SharedPreferences.Editor editor = sharedPreferences.edit();


        String s_siz = sharedPreferences.getString("s_size","");
        String s_poto = sharedPreferences.getString("s_photo","");*/

     /*   sets.add(s_siz);
        //set.addAll(ship_size);
       // editor.putStringSet("ship", set);

        editor.putStringSet("ships",sets);
        editor.commit();





        Set<String> set_new = sharedPreferences.getStringSet("ships", null);
        ship_size=new ArrayList<String>(set_new);


        Log.e("tagsiz",""+ship_size.size());*/




        header = (TextView) findViewById(R.id.tv_hd_txt);

        lsview = (ListView) findViewById(R.id.listview);


        lt_back = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_back);

        lt_add = (LinearLayout) findViewById(R.id.layout_add);

        btn_continue = (Button) findViewById(R.id.button_continue);

        btn_next = (ImageButton) findViewById(R.id.button_next);
        btn_addpal = (Button) findViewById(R.id.button_addpal);
        //  btn_delete = (Button) findViewById(R.id.button_delete);


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");


        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");

        header.setTypeface(tf);
        btn_addpal.setTypeface(tf1);
        btn_continue.setTypeface(tf1);


      /*  if (!ship_size.isEmpty()) {

            Log.e("tag3", ship_size.get(0) + "\t" + ship_photo.get(0) + "\t" + ship_size.size());
            shipadpt = new ShipmentNextAdpater(ShipmentNext.this, getApplicationContext(), ship_size, ship_photo);
            lsview.setAdapter(shipadpt);
        }*/


        getFromDB();




        lsview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String ads = lsview.getSelectedItem().toString();

                //  String msg = view.findViewById(R.id.tv_size).getText().toString;

                TextView txxx = (TextView) view.findViewById(R.id.tv_size);

                TextView tx = (TextView) view.findViewById(R.id.tv_op);

                final String asd = tx.getText().toString();
                Log.d("tag", "" + asd);

                final String sss = txxx.getText().toString();


                SweetAlertDialog dialog = new SweetAlertDialog(ShipmentNext.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Do you want to Delete this \n Shipment?")
                        .setConfirmText("Yes!")
                        .setCancelText("No")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                dbclass.deletedata(sss, asd);
                                getFromDB();
                                adapter1.notifyDataSetChanged();
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


        adapter1 = new Adap_Shipment(ShipmentNext.this, dbdatalist);
        lsview.setAdapter(adapter1);


    }


}
