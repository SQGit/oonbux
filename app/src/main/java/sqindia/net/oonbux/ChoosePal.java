package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by Salman on 5/18/2016.
 */
public class ChoosePal extends Activity {

    com.rey.material.widget.LinearLayout lt_back;
    ImageButton btn_next, btn_addpal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosepal);


        lt_back = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_back);

        btn_next = (ImageButton) findViewById(R.id.button_next);
        btn_addpal = (ImageButton) findViewById(R.id.button_add_pal);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), ChooseAddress.class);
                startActivity(inte);
            }
        });

        btn_addpal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), AddPal.class);
                startActivity(inte);
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
    }
}
