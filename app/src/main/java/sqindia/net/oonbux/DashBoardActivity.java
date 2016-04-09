package sqindia.net.oonbux;

import android.app.FragmentManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.Button;



public class DashBoardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView txt1, txt2, txt3, txt4, txt5, txt6, txt7, txt8, txt9, txt10, txt11;
    Button btn1, btn2, btn3,dbtn1,dbtn2,dbtn3;
    com.rey.material.widget.TextView htxt1, htxt2,header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, R.string.app_name, R.string.app_name);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        header = (com.rey.material.widget.TextView) findViewById(R.id.header);
        htxt1 = (com.rey.material.widget.TextView) findViewById(R.id.htxt1);
        htxt2 = (com.rey.material.widget.TextView) findViewById(R.id.htxt2);
        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
        txt3 = (TextView) findViewById(R.id.txt3);
        txt4 = (TextView) findViewById(R.id.txt4);
        txt5 = (TextView) findViewById(R.id.txt5);
        txt6 = (TextView) findViewById(R.id.txt6);
        txt7 = (TextView) findViewById(R.id.txt7);
        txt8 = (TextView) findViewById(R.id.txt8);
        txt9 = (TextView) findViewById(R.id.txt9);
        txt10 = (TextView) findViewById(R.id.txt10);
        txt11 = (TextView) findViewById(R.id.txt11);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);

        dbtn1 = (Button) findViewById(R.id.dbtn1);
        dbtn2 = (Button) findViewById(R.id.dbtn2);
        dbtn3 = (Button) findViewById(R.id.dbtn3);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");

        header.setTypeface(tf);
        htxt1.setTypeface(tf);
        htxt2.setTypeface(tf);
        txt1.setTypeface(tf);
        txt2.setTypeface(tf);
        txt3.setTypeface(tf);
        txt4.setTypeface(tf);
        txt5.setTypeface(tf);
        txt6.setTypeface(tf);
        txt7.setTypeface(tf);
        txt8.setTypeface(tf);
        txt9.setTypeface(tf);
        txt10.setTypeface(tf);
        txt11.setTypeface(tf);
        btn1.setTypeface(tf);
        btn2.setTypeface(tf);
        btn3.setTypeface(tf);
        dbtn1.setTypeface(tf);
        dbtn2.setTypeface(tf);
        dbtn3.setTypeface(tf);

        First_Fragment fragment = new First_Fragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment).commit();

        dbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbtn1.setBackgroundColor(getResources().getColor(R.color.tab_brown));
                dbtn2.setBackgroundColor(getResources().getColor(R.color.tab_default));
                dbtn3.setBackgroundColor(getResources().getColor(R.color.tab_default));

                First_Fragment fragment = new First_Fragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();
                //Toast.makeText(getApplicationContext(), "dbtn", Toast.LENGTH_LONG).show();

            }
        });

        dbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbtn1.setBackgroundColor(getResources().getColor(R.color.tab_default));
                dbtn2.setBackgroundColor(getResources().getColor(R.color.tab_brown));
                dbtn3.setBackgroundColor(getResources().getColor(R.color.tab_default));

               // Toast.makeText(getApplicationContext(), "dbtn", Toast.LENGTH_LONG).show();
                Second_Fragment fragment = new Second_Fragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();

            }
        });


        dbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dbtn1.setBackgroundColor(getResources().getColor(R.color.tab_default));
                dbtn2.setBackgroundColor(getResources().getColor(R.color.tab_default));
                dbtn3.setBackgroundColor(getResources().getColor(R.color.tab_brown));

               // Toast.makeText(getApplicationContext(), "dbtn", Toast.LENGTH_LONG).show();
                Second_Fragment fragment = new Second_Fragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();

            }
        });




        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"button worked",Toast.LENGTH_LONG).show();
            }
        });




    }




    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        return false;
    }



}
