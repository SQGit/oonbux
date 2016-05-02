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

/**
 * Created by Salman on 4/29/2016.
 */
public class DeliveryAddress extends Activity {
    LinearLayout bck_lt;
    TextView tv_header, tv_sub_hdr1, tv_sub_hdr2;
    Button btn_upd_loc, btn_upd_int, btn_next;
    com.rey.material.widget.LinearLayout lt_back;
    MaterialEditText et_loc_add1, et_loc_addr2, et_loc_city, et_loc_state, et_loc_zip, et_loc_phone, et_int_add1, et_int_addr2, et_int_city, et_int_state, et_int_zip, et_int_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);


        // bck_lt = (LinearLayout) findViewById(R.id.bck_layout);

        tv_header = (TextView) findViewById(R.id.tv_hd_txt);
        tv_sub_hdr1 = (TextView) findViewById(R.id.tv_shd_txt1);
        tv_sub_hdr2 = (TextView) findViewById(R.id.tv_shd_txt2);

        btn_upd_loc = (Button) findViewById(R.id.button_update_loc);
        btn_upd_int = (Button) findViewById(R.id.button_update_int);
        btn_next = (Button) findViewById(R.id.button_next);

        et_loc_add1 = (MaterialEditText) findViewById(R.id.edittext_loc_address1);
        et_loc_addr2 = (MaterialEditText) findViewById(R.id.edittext_loc_address2);
        et_loc_city = (MaterialEditText) findViewById(R.id.edittext_loc_city);
        et_loc_state = (MaterialEditText) findViewById(R.id.edittext_loc_state);
        et_loc_zip = (MaterialEditText) findViewById(R.id.edittext_loc_zip);
        et_loc_phone = (MaterialEditText) findViewById(R.id.edittext_loc_phone);

        et_int_add1 = (MaterialEditText) findViewById(R.id.edittext_int_address1);
        et_int_addr2 = (MaterialEditText) findViewById(R.id.edittext_int_address2);
        et_int_city = (MaterialEditText) findViewById(R.id.edittext_int_city);
        et_int_state = (MaterialEditText) findViewById(R.id.edittext_int_state);
        et_int_zip = (MaterialEditText) findViewById(R.id.edittext_int_zip);
        et_int_phone = (MaterialEditText) findViewById(R.id.edittext_int_phone);

        lt_back = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_back);


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");

        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");

        tv_header.setTypeface(tf1);
        tv_sub_hdr1.setTypeface(tf);
        tv_sub_hdr2.setTypeface(tf);
        btn_upd_loc.setTypeface(tf);
        btn_upd_int.setTypeface(tf);

        et_loc_add1.setTypeface(tf);
        et_loc_addr2.setTypeface(tf);
        et_loc_city.setTypeface(tf);
        et_loc_state.setTypeface(tf);
        et_loc_zip.setTypeface(tf);
        et_loc_phone.setTypeface(tf);

        et_int_add1.setTypeface(tf);
        et_int_addr2.setTypeface(tf);
        et_int_city.setTypeface(tf);
        et_int_state.setTypeface(tf);
        et_int_zip.setTypeface(tf);
        et_int_phone.setTypeface(tf);


        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                startActivity(inte);
            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(inte);
            }
        });


    }
}

