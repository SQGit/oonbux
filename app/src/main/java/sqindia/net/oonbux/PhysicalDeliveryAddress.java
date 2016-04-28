package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;


public class PhysicalDeliveryAddress extends Activity {

    LinearLayout bck_lt;
    TextView tv_header, tv_sub_hdr1, tv_sub_hdr2;
    Button btn_sav_loc, btn_sav_int;
    MaterialEditText et_lc_str, et_lc_apr, et_lc_zip, et_lc_not, et_int_str, et_int_apr, et_int_zip, et_int_not;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.physical_delivery_address);


        bck_lt = (LinearLayout) findViewById(R.id.bck_layout);

        tv_header = (TextView) findViewById(R.id.tv_hd_txt);
        tv_sub_hdr1 = (TextView) findViewById(R.id.tv_shd_txt1);
        tv_sub_hdr2 = (TextView) findViewById(R.id.tv_shd_txt2);

        btn_sav_loc = (Button) findViewById(R.id.button_save_lc);
        btn_sav_int = (Button) findViewById(R.id.button_save_ic);

        et_lc_str = (MaterialEditText) findViewById(R.id.et_lc_str);
        et_lc_apr = (MaterialEditText) findViewById(R.id.et_lc_apr);
        et_lc_zip = (MaterialEditText) findViewById(R.id.et_lc_zip);
        et_lc_not = (MaterialEditText) findViewById(R.id.et_lc_note);

        et_int_str = (MaterialEditText) findViewById(R.id.et_int_str);
        et_int_apr = (MaterialEditText) findViewById(R.id.et_int_apr);
        et_int_zip = (MaterialEditText) findViewById(R.id.et_int_zip);
        et_int_not = (MaterialEditText) findViewById(R.id.et_int_note);


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");

        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");

        tv_header.setTypeface(tf1);
        tv_sub_hdr1.setTypeface(tf);
        tv_sub_hdr2.setTypeface(tf);
        btn_sav_loc.setTypeface(tf);
        btn_sav_int.setTypeface(tf);
        et_lc_str.setTypeface(tf);
        et_lc_apr.setTypeface(tf);
        et_lc_zip.setTypeface(tf);
        et_lc_not.setTypeface(tf);
        et_int_str.setTypeface(tf);
        et_int_apr.setTypeface(tf);
        et_int_zip.setTypeface(tf);
        et_int_not.setTypeface(tf);


        bck_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                startActivity(inte);
            }
        });

    }
}
