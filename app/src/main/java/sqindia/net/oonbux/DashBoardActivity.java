package sqindia.net.oonbux;


import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rey.material.widget.Button;


@SuppressWarnings("deprecation")
public class DashBoardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public ProgressBar progressBar;
    TextView tv_nav_hd_ship_txt, tv_nav_hd_ship_id, tv_nav_cont_loc, tv_nav_cont_address, tv_nav_cont_phone, tv_nav_cont_prof, tv_nav_cont_pal_req, tv_nav_cont_how_it, tv_nav_cont_help_line, tv_nav_cont_share, tv_nav_cont_rec_pkg, tv_nav_cont_payment, tv_nav_cont_d_wallet;
    Button btn_nav_cont_loc, btn_nav_cont_area, btn_nav_cont_add_loc, btn_dash_ship, btn_dash_deliver, btn_dash_shop, shop_online;
    com.rey.material.widget.TextView tv_dash_hd_txt;
    String str_oonbux_id;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DashBoardActivity.this);
        str_oonbux_id = sharedPreferences.getString("oonbuxid", "");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        tv_dash_hd_txt = (com.rey.material.widget.TextView) findViewById(R.id.tv_hd_txt);

        tv_nav_hd_ship_txt = (TextView) findViewById(R.id.htxt1);
        tv_nav_hd_ship_id = (TextView) findViewById(R.id.htxt2);
        tv_nav_cont_loc = (TextView) findViewById(R.id.txt1);
        tv_nav_cont_address = (TextView) findViewById(R.id.txt2);
        tv_nav_cont_phone = (TextView) findViewById(R.id.txt3);
        tv_nav_cont_prof = (TextView) findViewById(R.id.txt4);
        tv_nav_cont_pal_req = (TextView) findViewById(R.id.txt5);
        tv_nav_cont_how_it = (TextView) findViewById(R.id.txt6);
        tv_nav_cont_help_line = (TextView) findViewById(R.id.txt7);
        tv_nav_cont_share = (TextView) findViewById(R.id.txt8);
        tv_nav_cont_rec_pkg = (TextView) findViewById(R.id.txt9);
        tv_nav_cont_payment = (TextView) findViewById(R.id.txt10);
        tv_nav_cont_d_wallet = (TextView) findViewById(R.id.txt11);

        btn_nav_cont_loc = (Button) findViewById(R.id.btn1);
        btn_nav_cont_area = (Button) findViewById(R.id.btn2);
        btn_nav_cont_add_loc = (Button) findViewById(R.id.btn3);

        shop_online = (Button) findViewById(R.id.btn_dash_shop_online);

        btn_dash_ship = (Button) findViewById(R.id.btn_dash_ship);
        btn_dash_deliver = (Button) findViewById(R.id.btn_dash_deliver);
        btn_dash_shop = (Button) findViewById(R.id.btn_dash_shop_online);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");

        tv_dash_hd_txt.setTypeface(tf);
        tv_nav_hd_ship_txt.setTypeface(tf);
        tv_nav_hd_ship_id.setTypeface(tf);
        tv_nav_cont_loc.setTypeface(tf);
        tv_nav_cont_address.setTypeface(tf);
        tv_nav_cont_phone.setTypeface(tf);
        tv_nav_cont_prof.setTypeface(tf);
        tv_nav_cont_pal_req.setTypeface(tf);
        tv_nav_cont_how_it.setTypeface(tf);
        tv_nav_cont_help_line.setTypeface(tf);
        tv_nav_cont_share.setTypeface(tf);
        tv_nav_cont_rec_pkg.setTypeface(tf);
        tv_nav_cont_payment.setTypeface(tf);
        tv_nav_cont_d_wallet.setTypeface(tf);
        btn_nav_cont_loc.setTypeface(tf);
        btn_nav_cont_area.setTypeface(tf);
        btn_nav_cont_add_loc.setTypeface(tf);
        btn_dash_ship.setTypeface(tf);
        btn_dash_deliver.setTypeface(tf);
        shop_online.setTypeface(tf);

        tv_nav_hd_ship_id.setText(str_oonbux_id);


        ShipYourLuggage fragment = new ShipYourLuggage();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

        progressBar.setProgress(33);


        btn_dash_ship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setProgress(33);
                btn_dash_ship.setBackgroundColor(getResources().getColor(R.color.tab_brown));
                btn_dash_deliver.setBackgroundColor(getResources().getColor(R.color.tab_default));
                shop_online.setBackgroundColor(getResources().getColor(R.color.tab_default));

                ShipYourLuggage fragment = new ShipYourLuggage();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();


            }
        });

        btn_dash_deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setProgress(66);
                btn_dash_ship.setBackgroundColor(getResources().getColor(R.color.tab_default));
                btn_dash_deliver.setBackgroundColor(getResources().getColor(R.color.tab_brown));
                shop_online.setBackgroundColor(getResources().getColor(R.color.tab_default));

                DeliverPkgFragment fragment = new DeliverPkgFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();


            }
        });


        shop_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_dash_ship.setBackgroundColor(getResources().getColor(R.color.tab_default));
                btn_dash_deliver.setBackgroundColor(getResources().getColor(R.color.tab_default));
                shop_online.setBackgroundColor(getResources().getColor(R.color.tab_brown));


                DeliverPkgFragment fragment = new DeliverPkgFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();
                progressBar.setProgress(100);
            }
        });


    /*    btn_dash_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_dash_ship.setBackgroundColor(getResources().getColor(R.color.tab_default));
                btn_dash_deliver.setBackgroundColor(getResources().getColor(R.color.tab_default));
                btn_dash_shop.setBackgroundColor(getResources().getColor(R.color.tab_brown));


                ShopOnline_Fragment fragment = new ShopOnline_Fragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();
                progressBar.setProgress(100);

            }
        });*/


        tv_nav_cont_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(inte);
            }
        });

        tv_nav_cont_how_it.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), HowItWorks.class);
                startActivity(inte);
            }
        });

        tv_nav_cont_help_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), ContactUs.class);
                startActivity(inte);
            }
        });

        btn_nav_cont_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "button worked", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        return false;
    }


}
