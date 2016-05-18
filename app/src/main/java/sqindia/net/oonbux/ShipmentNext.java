package sqindia.net.oonbux;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.rey.material.widget.TextView;

/**
 * Created by Salman on 5/18/2016.
 */
public class ShipmentNext extends Activity {

    ImageButton btn_back;
    TextView header;
    com.rey.material.widget.LinearLayout lt_back;
    ListView lsview;
    ShipmentNextAdpater shipadpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shipment_next);


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");

        header = (TextView) findViewById(R.id.tv_hd_txt);

        lsview = (ListView) findViewById(R.id.listview);
        header.setTypeface(tf);

        lt_back = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_back);


        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                startActivity(inte);*/
                onBackPressed();
            }
        });


        shipadpt = new ShipmentNextAdpater(getApplicationContext());
        lsview.setAdapter(shipadpt);


    }
}
