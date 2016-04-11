package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;


public class LoginActivity extends Activity {
    Button btn_login;
    TextView tv_donthav, tv_register;
    LinearLayout ll_register;

    MaterialEditText et_email, et_phone, et_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = (Button) findViewById(R.id.button_login);
        ll_register = (LinearLayout) findViewById(R.id.linear_login_text);
        tv_donthav = (TextView) findViewById(R.id.text_dont);
        tv_register = (TextView) findViewById(R.id.text_login);
        et_email = (MaterialEditText) findViewById(R.id.edittext_email);
        et_phone = (MaterialEditText) findViewById(R.id.edittext_phone);
        et_pass = (MaterialEditText) findViewById(R.id.edittext_pass);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");

        btn_login.setTypeface(tf);
        tv_donthav.setTypeface(tf);
        tv_register.setTypeface(tf);
        et_email.setTypeface(tf);
        et_phone.setTypeface(tf);
        et_pass.setTypeface(tf);


        ll_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register_intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(register_intent);
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {

                                             String email = et_email.getText().toString();
                                             String phone = et_phone.getText().toString();
                                             String password = et_pass.getText().toString();

                                             if (!(email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())) {
                                                 if (!(phone.isEmpty() || phone.length() < 10)) {
                                                     if (!(password.isEmpty() || password.length() < 4 || password.length() > 10)) {
                                                         Intent login_intent = new Intent(getApplicationContext(), DashBoardActivity.class);
                                                         startActivity(login_intent);
                                                         finish();
                                                     } else {
                                                         et_pass.setError("between 4 and 10 alphanumeric characters");
                                                     }
                                                 } else {
                                                     et_phone.setError("Enter valid phone number");
                                                 }
                                             } else {
                                                 et_email.setError("Enter a valid email address!");

                                             }
                                         }
                                     }

        );
    }
}
