package sqindia.net.oonbux;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.os.Bundle;

import com.rey.material.widget.TextView;

/**
 * Created by Salman on 4/20/2016.
 */
public class Dialog_Add_Loc extends Dialog {


    public Activity c;
    TextView tv_id_txt, tv_id, tv_hd, tv_name, tv_addr, tv_phone;


    public Dialog_Add_Loc(Activity activity) {
        super(activity);
        this.c = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_loc);

        Typeface tf = Typeface.createFromAsset(c.getAssets(), "fonts/prox.otf");

        tv_id_txt = (TextView) findViewById(R.id.tv_id_txt);
        tv_id = (TextView) findViewById(R.id.tv_id);
        tv_hd = (TextView) findViewById(R.id.tv_hd);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_addr = (TextView) findViewById(R.id.tv_addr);
        tv_phone = (TextView) findViewById(R.id.tv_phone);

        tv_hd.setTypeface(tf);
        tv_id.setTypeface(tf);
        tv_hd.setTypeface(tf);
        tv_name.setTypeface(tf);
        tv_addr.setTypeface(tf);
        tv_phone.setTypeface(tf);


    }


}
