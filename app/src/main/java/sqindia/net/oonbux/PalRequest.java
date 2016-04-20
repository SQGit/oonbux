package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.Button;
import com.rey.material.widget.Spinner;
import com.rey.material.widget.TextView;


/**
 * Created by Salman on 4/19/2016.
 */
public class PalRequest extends Activity {

    ImageButton btn_back;
    Button btn_send;
    TextView tv_header, tv_sheader, tv_comment;
    MaterialEditText et_fname, et_lname, et_email, et_phone, et_comment;
    Spinner spin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.palrequest);

        spin = (com.rey.material.widget.Spinner) findViewById(R.id.spinner);

        btn_back = (ImageButton) findViewById(R.id.btn_back);
        btn_send = (Button) findViewById(R.id.button_send);

        et_fname = (MaterialEditText) findViewById(R.id.edittext_fname);
        et_lname = (MaterialEditText) findViewById(R.id.edittext_lname);
        et_email = (MaterialEditText) findViewById(R.id.edittext_email);
        et_phone = (MaterialEditText) findViewById(R.id.edittext_phone);
        et_comment = (MaterialEditText) findViewById(R.id.edittext_note);

        tv_header = (TextView) findViewById(R.id.tv_hd_txt);
        tv_sheader = (TextView) findViewById(R.id.tv_shd_txt);
        tv_comment = (TextView) findViewById(R.id.tv_commt);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");

        btn_send.setTypeface(tf);
        et_fname.setTypeface(tf);
        et_lname.setTypeface(tf);
        et_email.setTypeface(tf);
        et_phone.setTypeface(tf);
        et_comment.setTypeface(tf);
        tv_comment.setTypeface(tf);
        tv_header.setTypeface(tf);
        tv_sheader.setTypeface(tf);


        Spinner_Adapter adapter = new Spinner_Adapter(getApplicationContext(), R.layout.dropdown_lists, Config.countries);
        adapter.setDropDownViewResource(R.layout.dropdown_lists);
        spin.setAdapter(adapter);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                startActivity(inte);
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_Palreq cdd = new Dialog_Palreq(PalRequest.this);
                cdd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cdd.show();
            }
        });


    }


    public class Spinner_Adapter extends ArrayAdapter<String> {

        String[] objs;

        public Spinner_Adapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
            this.objs = objects;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.dropdown_lists, parent, false);


            // Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(),
            //   "Fonts/mont.ttf");
            Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");


            android.widget.TextView label = (android.widget.TextView) row.findViewById(R.id.text_spin);

            label.setTypeface(tf);

            label.setText(objs[position]);


            return row;
        }
    }


}
