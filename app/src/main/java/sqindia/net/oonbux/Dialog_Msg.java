package sqindia.net.oonbux;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;

/**
 * Created by Salman on 4/27/2016.
 */
public class Dialog_Msg extends Dialog {

    public Activity c;
    TextView tv_cont;
    String ss;
    Button btn_close;
    Context context;

    public Dialog_Msg(Activity activity, String txt) {
        super(activity);
        this.c = activity;
        this.ss = txt;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_message);

        Typeface tf = Typeface.createFromAsset(c.getAssets(), "fonts/prox.otf");

        tv_cont = (TextView) findViewById(R.id.tv_cont);
        btn_close = (Button) findViewById(R.id.button_close);

        tv_cont.setTypeface(tf);
        btn_close.setTypeface(tf);

        tv_cont.setText(ss);


        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hide();
                //c.finish();
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
