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

public class SplashActivity extends AppCompatActivity {
    Button btn_submit;
    String get_sts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
        get_sts = sharedPreferences.getString("login", "");
        Log.d("tag", "status" + get_sts);

        btn_submit = (Button) findViewById(R.id.button_submit);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");
        btn_submit.setTypeface(tf);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ((get_sts == "")) {
                    Intent login_intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(login_intent);
                    finish();
                } else {

                    Intent login_intent = new Intent(getApplicationContext(), DashBoardActivity.class);
                    startActivity(login_intent);
                    finish();

                }


            }
        });
    }
}
