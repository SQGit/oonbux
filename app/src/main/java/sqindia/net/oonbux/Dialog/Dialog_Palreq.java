package sqindia.net.oonbux.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.rey.material.widget.TextView;

import sqindia.net.oonbux.Activity.DashBoardActivity;
import sqindia.net.oonbux.R;

/**
 * Created by Salman on 4/19/2016.
 */
public class Dialog_Palreq extends Dialog {

    public Activity c;
    TextView tv_hd, tv_cont, tv_foot;
    ImageView imgok;

    public Dialog_Palreq(Activity activity) {
        super(activity);
        this.c = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_palreq);
        //es = (Button) findViewById(R.id.btn_yes);
        Typeface tf = Typeface.createFromAsset(c.getAssets(), "fonts/prox.otf");

        tv_hd = (TextView) findViewById(R.id.tv_hd);
        tv_cont = (TextView) findViewById(R.id.tv_cont);
        tv_foot = (TextView) findViewById(R.id.tv_foot);
        imgok = (ImageView) findViewById(R.id.img_ok);

        tv_hd.setTypeface(tf);
        tv_cont.setTypeface(tf);
        tv_foot.setTypeface(tf);

        imgok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(c, DashBoardActivity.class);
                c.startActivity(inte);
                dismiss();
                c.finish();
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        dismiss();
    }
}
