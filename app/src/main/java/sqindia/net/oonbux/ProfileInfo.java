package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;

/**
 * Created by Salman on 4/15/2016.
 */
public class ProfileInfo extends Activity {

    ImageButton btn_back;
    LinearLayout bck_lt;
    Button btn_save;
    TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profileinfo);

        btn_back = (ImageButton) findViewById(R.id.btn_back);

        bck_lt = (LinearLayout) findViewById(R.id.bck_layout);
        btn_save = (Button) findViewById(R.id.button_submit);
        header = (TextView) findViewById(R.id.tv_hd_txt);

        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");

        header.setTypeface(tf1);

        bck_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                startActivity(inte);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_Add_Loc cdd = new Dialog_Add_Loc(ProfileInfo.this);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }
        });



    }
}
