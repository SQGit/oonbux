package sqindia.net.oonbux;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;

import com.rey.material.widget.TextView;

/**
 * Created by Salman on 4/19/2016.
 */
public class Dialog_Palreq extends Dialog {

    public Activity c;
    TextView tv_hd, tv_cont, tv_foot;

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

        tv_hd.setTypeface(tf);
        tv_cont.setTypeface(tf);
        tv_foot.setTypeface(tf);


    }


}
