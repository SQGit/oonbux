package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Salman on 4/18/2016.
 */
public class ContactUs extends Activity {


    com.rey.material.widget.LinearLayout lt_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactus);


        //  btn_back = (ImageButton) findViewById(R.id.btn_back);

        //  bck_lt = (LinearLayout) findViewById(R.id.bck_layout);

        lt_back = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_back);

        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                startActivity(inte);
            }
        });
    }
}
