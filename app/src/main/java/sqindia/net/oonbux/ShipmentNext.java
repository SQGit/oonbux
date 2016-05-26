package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
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


    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipment_next);


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


        if (!ship_size.isEmpty()) {

            Log.e("tag3", ship_size.get(0) + "\t" + ship_photo.get(0) + "\t" + ship_size.size());
            shipadpt = new ShipmentNextAdpater(ShipmentNext.this, getApplicationContext(), ship_size, ship_photo);
            lsview.setAdapter(shipadpt);
        }



        lsview.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {

                Log.d("tag", "hover");
                return true;
            }
        });


        lsview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int i = position;
                Log.d("tag", "select " + i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        lsview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int i = position;

                Log.d("tag", "click " + i);
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


     /*   btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("tag","btndelete");
                lsview.getChildAt(1).setEnabled(false);



            }
        });*/





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
}
