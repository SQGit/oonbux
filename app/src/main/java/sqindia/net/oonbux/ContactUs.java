package sqindia.net.oonbux;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;

/**
 * Created by Salman on 4/18/2016.
 */
public class ContactUs extends Activity {

    ImageButton btn_back;
    LinearLayout bck_lt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smks);

/*
        btn_back = (ImageButton) findViewById(R.id.btn_back);

        bck_lt = (LinearLayout) findViewById(R.id.bck_layout);

        bck_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                startActivity(inte);
            }
        });*/
    }
}
