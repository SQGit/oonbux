package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.rey.material.widget.TextView;

/**
 * Created by Salman on 4/18/2016.
 */
public class HowItWorks extends Activity {

    ImageButton btn_back;
    TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.howitworks);

        btn_back = (ImageButton) findViewById(R.id.btn_back);


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");

        header = (TextView) findViewById(R.id.tv_hd_txt);

        header.setTypeface(tf);




        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                startActivity(inte);
            }
        });
    }
}
