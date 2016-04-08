package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;


public class RegisterActivity extends Activity {
    Button btn_submit;
    TextView tv_donthav, tv_register, tv_footer;
    LinearLayout ll_register;
    MaterialEditText et_email, et_phone, et_pass, et_repass, et_zip, et_state;
    MaterialAutoCompleteTextView aet_cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_submit = (Button) findViewById(R.id.button_submit);
        ll_register = (LinearLayout) findViewById(R.id.linear_login_text);
        tv_donthav = (TextView) findViewById(R.id.text_dont);
        tv_register = (TextView) findViewById(R.id.text_login);
        tv_footer = (TextView) findViewById(R.id.terms);
        aet_cont = (MaterialAutoCompleteTextView) findViewById(R.id.edittext_country);
        et_email = (MaterialEditText) findViewById(R.id.edittext_email);
        et_pass = (MaterialEditText) findViewById(R.id.edittext_pass);
        et_repass = (MaterialEditText) findViewById(R.id.edittext_repass);
        et_phone = (MaterialEditText) findViewById(R.id.edittext_phone);
        et_zip = (MaterialEditText) findViewById(R.id.edittext_zipcode);
        et_state = (MaterialEditText) findViewById(R.id.edittext_state);


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");

        btn_submit.setTypeface(tf);
        tv_donthav.setTypeface(tf);
        tv_register.setTypeface(tf);
        tv_footer.setTypeface(tf);
        aet_cont.setTypeface(tf);
        et_email.setTypeface(tf);
        et_pass.setTypeface(tf);
        et_repass.setTypeface(tf);
        et_phone.setTypeface(tf);
        et_zip.setTypeface(tf);
        et_state.setTypeface(tf);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_lists,R.id.text_spin, Constants.state_lists);
        aet_cont.setAdapter(adapter1);


        ll_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(login_intent);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login_intent = new Intent(getApplicationContext(), DashBoardActivity.class);
                startActivity(login_intent);
            }
        });
    }
}
