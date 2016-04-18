package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Salman on 4/15/2016.
 */
public class PhysicalDeliveryAddress extends Activity {

    ImageButton btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.physical_delivery_address);


        btn_back = (ImageButton) findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                startActivity(inte);
            }
        });

    }
}
