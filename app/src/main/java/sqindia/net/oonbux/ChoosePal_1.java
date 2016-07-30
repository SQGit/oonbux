package sqindia.net.oonbux;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.rey.material.widget.Button;
import com.rey.material.widget.EditText;
import com.rey.material.widget.TextView;

/**
 * Created by Salman on 7/5/2016.
 */

//asdfasd

public class ChoosePal_1 extends Activity {

    TextView tv_header;
    LinearLayout lt_back, lt_add;
    Button btn_next,btn_submit;
    EditText et_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosepal1);


        tv_header = (TextView) findViewById(R.id.tv_hd_txt);
        lt_back = (LinearLayout) findViewById(R.id.layout_back);
        lt_add = (LinearLayout) findViewById(R.id.layout_add);
        et_search = (EditText) findViewById(R.id.et_searchtxt);
        btn_submit = (Button) findViewById(R.id.button_submit);
        btn_next = (Button) findViewById(R.id.button_next);

        lt_add.setVisibility(View.GONE);

        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");
        tv_header.setTypeface(tf1);
        btn_next.setTypeface(tf);
        btn_submit.setTypeface(tf);
        et_search.setTypeface(tf);

        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), ChooseAddress.class);
                startActivity(inte);
            }
        });



        lt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Pal_Search fragment = new Pal_Search();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frame_container, fragment).commit();

            }
        });


        Pal_Friends fragment = new Pal_Friends();
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frame_container, fragment).commit();



    }
}
