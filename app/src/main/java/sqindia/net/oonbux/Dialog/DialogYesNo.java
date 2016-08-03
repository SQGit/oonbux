package sqindia.net.oonbux.Dialog;

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

import sqindia.net.oonbux.Activity.NewLoginActivity;
import sqindia.net.oonbux.R;

/**
 * Created by Salman on 7/26/2016.
 */
public class DialogYesNo extends Dialog {

    public Activity c;
    public int i;
    TextView tv_content;
    Button btn_yes,btn_no;
    String content, get_profile_sts;
    ImageView imgview;


    public DialogYesNo(Activity activity, String txt, int i) {
        super(activity);
        this.c = activity;
        this.content = txt;
        this.i = i;
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_yesno);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);

        get_profile_sts = sharedPreferences.getString("profile", "");
        Log.e("tag", "pfs" + get_profile_sts);


        Typeface tf = Typeface.createFromAsset(c.getAssets(), "fonts/prox.otf");

        tv_content = (TextView) findViewById(R.id.tv_cont);
        btn_yes = (Button) findViewById(R.id.button_yes);
        btn_no = (Button) findViewById(R.id.button_no);

        imgview = (ImageView) findViewById(R.id.imgview);

        tv_content.setTypeface(tf);
        btn_yes.setTypeface(tf);
        btn_no.setTypeface(tf);


        tv_content.setText(content);


        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(i ==0) {
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("login", "");
                    editor.commit();
                    Intent inte = new Intent(c.getApplicationContext(), NewLoginActivity.class);
                    c.startActivity(inte);
                    c.finish();
                    dismiss();
                }
                else if(i ==1){

                    Intent exit = new Intent(Intent.ACTION_MAIN);
                    exit.setAction(Intent.ACTION_MAIN);
                    exit.addCategory(Intent.CATEGORY_HOME);
                    exit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    exit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    c.startActivity(exit);
                    c.finish();
                    dismiss();

                }

            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        dismiss();
    }
}
