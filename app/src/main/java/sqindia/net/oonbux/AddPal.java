package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;

/**
 * Created by Salman on 5/18/2016.
 */
public class AddPal extends Activity {


    TextView header, msg, cont;
    MaterialEditText et_name;
    MaterialAutoCompleteTextView aet_mail;
    com.rey.material.widget.LinearLayout lt_back;
    ListView lsview;
    Button btn_submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpal);


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");

        header = (TextView) findViewById(R.id.tv_hd_txt);
        msg = (TextView) findViewById(R.id.tv_shd_txt1);
        cont = (TextView) findViewById(R.id.tv_shd_txt2);
        et_name = (MaterialEditText) findViewById(R.id.edittext_name);
        aet_mail = (MaterialAutoCompleteTextView) findViewById(R.id.edittext_email);

        header.setTypeface(tf);
        msg.setTypeface(tf);
        cont.setTypeface(tf);
        et_name.setTypeface(tf);
        aet_mail.setTypeface(tf);

        btn_submit = (Button) findViewById(R.id.button_submit);



        lt_back = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_back);


        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                startActivity(inte);*/
                onBackPressed();
            }
        });



        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), ChoosePal.class);
                startActivity(inte);
            }
        });

    }
}
