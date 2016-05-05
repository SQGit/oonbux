package sqindia.net.oonbux;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;

/**
 * Created by Salman on 5/2/2016.
 */
public class Dialog_new extends Dialog {

    public Activity c;
    public int i;
    TextView tv_content;
    Button btn_close;
    String regoutput, get_profile_sts;
    ImageView imgview;


    public Dialog_new(Activity activity, String txt, int i) {
        super(activity);
        this.c = activity;
        this.regoutput = txt;
        this.i = i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_newmsg);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);

        get_profile_sts = sharedPreferences.getString("profile", "");
        Log.e("tag", "pfs" + get_profile_sts);


        Typeface tf = Typeface.createFromAsset(c.getAssets(), "fonts/prox.otf");

        tv_content = (TextView) findViewById(R.id.tv_cont);
        btn_close = (Button) findViewById(R.id.button_close);

        imgview = (ImageView) findViewById(R.id.imgview);

        tv_content.setTypeface(tf);
        btn_close.setTypeface(tf);

        tv_content.setText(regoutput);


        if (i == 1) {

            btn_close.setText("OK");
            imgview.setImageDrawable(c.getResources().getDrawable(R.drawable.tick_ico));
            Drawable img = c.getResources().getDrawable(R.drawable.done_ico);
            btn_close.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);

        } else if (i == 2) {

            btn_close.setText("OK");
            imgview.setImageDrawable(c.getResources().getDrawable(R.drawable.tick_ico));
            Drawable img = c.getResources().getDrawable(R.drawable.done_ico);
            btn_close.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);

        } else {

            btn_close.setText("Close");
            imgview.setImageDrawable(c.getResources().getDrawable(R.drawable.msg_ico));
            Drawable img = c.getResources().getDrawable(R.drawable.close_ico);
            btn_close.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);

        }


        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (i == 0) {
                    Intent goD = new Intent(c.getApplicationContext(), LoginActivity.class);
                    c.startActivity(goD);
                    c.finish();
                    dismiss();
                } else if (i == 1) {


                    if ((get_profile_sts.equals(""))) {
                        Intent goD = new Intent(c.getApplicationContext(), ProfileInfo.class);
                        c.startActivity(goD);
                        c.finish();
                        dismiss();
                    } else {
                        Intent goD = new Intent(c.getApplicationContext(), DashBoardActivity.class);
                        c.startActivity(goD);
                        c.finish();
                        dismiss();
                    }


                } else if (i == 2) {
                    Intent goD = new Intent(c.getApplicationContext(), DashBoardActivity.class);
                    c.startActivity(goD);
                    c.finish();
                    dismiss();
                }


            }
        });


    }
}
