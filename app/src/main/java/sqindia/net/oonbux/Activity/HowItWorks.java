package sqindia.net.oonbux.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.rey.material.widget.TextView;

import sqindia.net.oonbux.R;

/**
 * Created by Salman on 4/18/2016.
 */
//asfsadf
public class HowitWorks extends Activity {

    TextView header,tv_content;
    ImageView lt_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.howitworks);



        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");
        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");

        header = (TextView) findViewById(R.id.tv_hd_txt);
        tv_content = (TextView) findViewById(R.id.tv_shd_txt);

        header.setTypeface(tf);
        tv_content.setTypeface(tf1);

        lt_back = (ImageView) findViewById(R.id.layout_back);


        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(inte);
            }
        });
    }
}
