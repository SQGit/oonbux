package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;

/**
 * Created by Salman on 5/18/2016.
 */
public class ShipmentNext extends Activity {

    public static ListView lsview;
    public static ShipmentNextAdpater shipadpt;
    ImageButton btn_next;
    TextView header;
    com.rey.material.widget.LinearLayout lt_back;
    Button btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipment_next);


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");

        header = (TextView) findViewById(R.id.tv_hd_txt);

        lsview = (ListView) findViewById(R.id.listview);
        header.setTypeface(tf);

        lt_back = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_back);

        btn_next = (ImageButton) findViewById(R.id.button_next);
        //  btn_delete = (Button) findViewById(R.id.button_delete);


        shipadpt = new ShipmentNextAdpater(getApplicationContext());
        lsview.setAdapter(shipadpt);


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


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent inte = new Intent(getApplicationContext(), ChooseAddress.class);
                startActivity(inte);

            }
        });






    }
}
