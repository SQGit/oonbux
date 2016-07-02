package sqindia.net.oonbux;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.rey.material.widget.Button;
import com.rey.material.widget.EditText;
import com.rey.material.widget.TextView;

/**
 * Created by Salman on 6/29/2016.
 */
public class MyPalLists extends Activity {

    Button btn1, btn2, btn3, btn_submit;
    TextView tv_header, tv_subheader;
    LinearLayout lt_back, lt_add;
    EditText et_searchtxt;
    FrameLayout fl_search;
    String str_sts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypal_lists);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");
        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");

        btn1 = (Button) findViewById(R.id.tbtn1);
        btn2 = (Button) findViewById(R.id.tbtn2);
        btn3 = (Button) findViewById(R.id.tbtn3);

        tv_header = (TextView) findViewById(R.id.tv_hd_txt);
        tv_subheader = (TextView) findViewById(R.id.tv_shd_txt);
        lt_back = (LinearLayout) findViewById(R.id.layout_back);
        lt_add = (LinearLayout) findViewById(R.id.layout_add);
        fl_search = (FrameLayout) findViewById(R.id.layout_search);

       // btn_submit = (Button) findViewById(R.id.button_submit);
        //et_searchtxt = (EditText) findViewById(R.id.et_searchtxt);


        tv_header.setTypeface(tf1);
        tv_subheader.setTypeface(tf);
        btn1.setTypeface(tf);
        btn2.setTypeface(tf);
        btn3.setTypeface(tf);





        fl_search.setVisibility(View.INVISIBLE);
        Pal_Friends fragment = new Pal_Friends();
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frame_container, fragment).commit();
        tv_subheader.setText("List of Pals.");
        tv_header.setText("Pal");


        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        lt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Intent goSearch = new Intent(getApplicationContext(),SearchPal.class);
                startActivity(goSearch);*/
                btn1.setTextColor(getResources().getColor(R.color.text_color_hint_light));
                btn2.setTextColor(getResources().getColor(R.color.text_color_hint_light));
                btn3.setTextColor(getResources().getColor(R.color.text_color_hint_light));

                Drawable img = getResources().getDrawable(R.drawable.mypal_wt_ico);
                btn1.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                Drawable img1 = getResources().getDrawable(R.drawable.request_wt_ico);
                btn2.setCompoundDrawablesWithIntrinsicBounds(img1, null, null, null);
                Drawable img2 = getResources().getDrawable(R.drawable.pending_wt_ico);
                btn3.setCompoundDrawablesWithIntrinsicBounds(img2, null, null, null);


                fl_search.setVisibility(View.VISIBLE);
                tv_subheader.setVisibility(View.INVISIBLE);
                tv_header.setText("Search Pal");
                Pal_Search fragment = new Pal_Search();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frame_container, fragment).commit();

            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn1.setTextColor(getResources().getColor(R.color.colorAccent));
                btn2.setTextColor(getResources().getColor(R.color.text_color_hint_light));
                btn3.setTextColor(getResources().getColor(R.color.text_color_hint_light));
                tv_subheader.setText("List of Pals.");
                tv_header.setText("Pal");
                fl_search.setVisibility(View.INVISIBLE);
                tv_subheader.setVisibility(View.VISIBLE);
                Drawable img = getResources().getDrawable(R.drawable.mypal_ico);
                btn1.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                Drawable img1 = getResources().getDrawable(R.drawable.request_wt_ico);
                btn2.setCompoundDrawablesWithIntrinsicBounds(img1, null, null, null);
                Drawable img2 = getResources().getDrawable(R.drawable.pending_wt_ico);
                btn3.setCompoundDrawablesWithIntrinsicBounds(img2, null, null, null);
                Pal_Friends fragment = new Pal_Friends();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frame_container, fragment).commit();

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn1.setTextColor(getResources().getColor(R.color.text_color_hint_light));
                btn2.setTextColor(getResources().getColor(R.color.text_color));
                btn3.setTextColor(getResources().getColor(R.color.text_color_hint_light));
                tv_subheader.setText("List of Requests not Accepted by You.");
                tv_header.setText("Pal");
                fl_search.setVisibility(View.INVISIBLE);
                tv_subheader.setVisibility(View.VISIBLE);
                Drawable img = getResources().getDrawable(R.drawable.mypal_wt_ico);
                btn1.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                Drawable img1 = getResources().getDrawable(R.drawable.request_ico);
                btn2.setCompoundDrawablesWithIntrinsicBounds(img1, null, null, null);
                Drawable img2 = getResources().getDrawable(R.drawable.pending_wt_ico);
                btn3.setCompoundDrawablesWithIntrinsicBounds(img2, null, null, null);
                Pal_Requests fragment = new Pal_Requests();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frame_container, fragment).commit();
            }
        });


        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn1.setTextColor(getResources().getColor(R.color.text_color_hint_light));
                btn2.setTextColor(getResources().getColor(R.color.text_color_hint_light));
                btn3.setTextColor(getResources().getColor(R.color.text_color));
                tv_subheader.setText("List of Requests waiting for Approval");
                tv_header.setText("Pal");
                fl_search.setVisibility(View.INVISIBLE);
                tv_subheader.setVisibility(View.VISIBLE);
                Drawable img = getResources().getDrawable(R.drawable.mypal_wt_ico);
                btn1.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                Drawable img1 = getResources().getDrawable(R.drawable.request_wt_ico);
                btn2.setCompoundDrawablesWithIntrinsicBounds(img1, null, null, null);
                Drawable img2 = getResources().getDrawable(R.drawable.pending_ico);
                btn3.setCompoundDrawablesWithIntrinsicBounds(img2, null, null, null);
                Pal_Pendings fragment = new Pal_Pendings();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frame_container, fragment).commit();
            }
        });


    }
}
