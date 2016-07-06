package sqindia.net.oonbux;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.rey.material.widget.Button;


//asd

public class SplashActivity extends AppCompatActivity {
    Button btn_submit;
    String get_login_sts, get_register_sts, get_profile_sts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
        get_login_sts = sharedPreferences.getString("login", "");
        get_register_sts = sharedPreferences.getString("register", "");
        get_profile_sts = sharedPreferences.getString("profile", "");

        Log.d("tag", "login_status" + get_login_sts);
        Log.d("tag", "register_status" + get_register_sts);
        Log.d("tag", "profile_status" + get_profile_sts);
        btn_submit = (Button) findViewById(R.id.button_submit);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");
        btn_submit.setTypeface(tf);
        if (!get_profile_sts.equals("")) {
            Intent login_intent = new Intent(getApplicationContext(), DashBoardActivity.class);
            startActivity(login_intent);
            finish();
        }
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((get_register_sts.equals(""))) {
                    Intent login_intent = new Intent(getApplicationContext(), RegisterActivity.class);
                    startActivity(login_intent);
                    finish();
                } else {
                    if ((get_login_sts.equals(""))) {
                        Intent login_intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(login_intent);
                        finish();
                    } else {

                        if (get_profile_sts.equals("")) {
                            Intent login_intent = new Intent(getApplicationContext(), ProfileInfo.class);
                            startActivity(login_intent);
                            finish();
                        } else {

                            Intent login_intent = new Intent(getApplicationContext(), DashBoardActivity.class);
                            startActivity(login_intent);
                            finish();
                        }
                    }
                }
            }
        });
    }
}
