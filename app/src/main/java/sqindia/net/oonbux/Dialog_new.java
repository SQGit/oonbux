package sqindia.net.oonbux;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;

/**
 * Created by Salman on 5/2/2016.
 */
public class Dialog_new extends Dialog {

    public Activity c;
    TextView tv_content;
    Button btn_close;
    String regoutput;


    public Dialog_new(Activity activity, String txt) {
        super(activity);
        this.c = activity;
        this.regoutput = txt;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_newmsg);

        Typeface tf = Typeface.createFromAsset(c.getAssets(), "fonts/prox.otf");

        tv_content = (TextView) findViewById(R.id.tv_cont);
        btn_close = (Button) findViewById(R.id.button_close);

        tv_content.setTypeface(tf);
        btn_close.setTypeface(tf);

        tv_content.setText(regoutput);


        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goD = new Intent(c.getApplicationContext(), LoginActivity.class);
                c.startActivity(goD);
                c.finish();
            }
        });


    }
}
