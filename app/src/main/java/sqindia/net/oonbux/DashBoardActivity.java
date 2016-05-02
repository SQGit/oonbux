package sqindia.net.oonbux;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rey.material.widget.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;


@SuppressWarnings("deprecation")
public class DashBoardActivity extends Activity implements NavigationView.OnNavigationItemSelectedListener {

    public final static int REQUEST_CODE = 1;
    public ProgressBar progressBar;
    com.rey.material.widget.TextView tv_nav_hd_ship_txt, tv_nav_hd_ship_id, tv_nav_cont_loc, tv_nav_cont_address, tv_nav_cont_phone, tv_nav_cont_prof, tv_nav_cont_pal_req, tv_nav_cont_how_it, tv_nav_cont_help_line, tv_nav_cont_share, tv_nav_cont_rec_pkg, tv_nav_cont_payment, tv_nav_cont_d_wallet;
    Button btn_nav_cont_loc_adr, btn_nav_cont_int_adr, btn_nav_cont_add_loc, btn_dash_ship, btn_dash_deliver, btn_dash_shop, btn_shop_online, btn_add_shipment, btn_done_shipment;
    com.rey.material.widget.TextView tv_dash_hd_txt;
    String str_oonbux_id;
    Toolbar toolbar;
    Fragment fragment;
    Context context;
    ImageView btm_cam;
    ArrayList<String> selectedPhotos = new ArrayList<>();
    PhotoAdapter photoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        photoAdapter = new PhotoAdapter(this, selectedPhotos);

        try {
            btm_cam.setImageURI(photoAdapter.uri);
        } catch (NullPointerException e) {

            Log.d("tag", String.valueOf(e));

        }


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(DashBoardActivity.this);
        str_oonbux_id = sharedPreferences.getString("oonbuxid", "");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        tv_dash_hd_txt = (com.rey.material.widget.TextView) findViewById(R.id.tv_hd_txt);

        tv_nav_hd_ship_txt = (com.rey.material.widget.TextView) findViewById(R.id.textview_id_txt);
        tv_nav_hd_ship_id = (com.rey.material.widget.TextView) findViewById(R.id.textview_id);
        tv_nav_cont_loc = (com.rey.material.widget.TextView) findViewById(R.id.txt1);
        tv_nav_cont_address = (com.rey.material.widget.TextView) findViewById(R.id.txt2);
        tv_nav_cont_phone = (com.rey.material.widget.TextView) findViewById(R.id.txt3);
        tv_nav_cont_prof = (com.rey.material.widget.TextView) findViewById(R.id.txt4);
        //txt = (com.rey.material.widget.TextView) findViewById(R.id.txt4);
        tv_nav_cont_pal_req = (com.rey.material.widget.TextView) findViewById(R.id.txt5);
        tv_nav_cont_how_it = (com.rey.material.widget.TextView) findViewById(R.id.txt6);
        tv_nav_cont_help_line = (com.rey.material.widget.TextView) findViewById(R.id.txt7);
        tv_nav_cont_share = (com.rey.material.widget.TextView) findViewById(R.id.txt8);
        tv_nav_cont_rec_pkg = (com.rey.material.widget.TextView) findViewById(R.id.txt9);
        tv_nav_cont_payment = (com.rey.material.widget.TextView) findViewById(R.id.txt10);
        tv_nav_cont_d_wallet = (com.rey.material.widget.TextView) findViewById(R.id.txt11);

        btn_add_shipment = (Button) findViewById(R.id.add_ship_button);
        btn_done_shipment = (Button) findViewById(R.id.add_done_button);

        btn_nav_cont_loc_adr = (Button) findViewById(R.id.btn1);
        btn_nav_cont_int_adr = (Button) findViewById(R.id.btn2);
        btn_nav_cont_add_loc = (Button) findViewById(R.id.btn3);

        btn_shop_online = (Button) findViewById(R.id.btn_dash_shop_online);

        btn_dash_ship = (Button) findViewById(R.id.btn_dash_ship);
        btn_dash_deliver = (Button) findViewById(R.id.btn_dash_deliver);
        btn_dash_shop = (Button) findViewById(R.id.btn_dash_shop_online);

        btm_cam = (ImageView) findViewById(R.id.cam);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");


        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");


        tv_dash_hd_txt.setTypeface(tf1);
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
        btn_nav_cont_loc_adr.setTypeface(tf);
        btn_nav_cont_int_adr.setTypeface(tf);
        btn_nav_cont_add_loc.setTypeface(tf);
        btn_dash_ship.setTypeface(tf);
        btn_dash_deliver.setTypeface(tf);
        btn_shop_online.setTypeface(tf);
        btn_add_shipment.setTypeface(tf);

        tv_nav_hd_ship_id.setText(str_oonbux_id);
        btn_add_shipment.setVisibility(View.VISIBLE);
        btn_done_shipment.setVisibility(View.GONE);
        progressBar.setProgress(33);
        First_Fragment fragment = new First_Fragment();
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frame_container, fragment).commit();


        btn_dash_ship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_add_shipment.setVisibility(View.VISIBLE);
                btn_done_shipment.setVisibility(View.GONE);
                progressBar.setProgress(33);
                btn_dash_ship.setBackgroundColor(getResources().getColor(R.color.tab_brown));
                btn_dash_deliver.setBackgroundColor(getResources().getColor(R.color.tab_default));
                btn_shop_online.setBackgroundColor(getResources().getColor(R.color.tab_default));

                //ShipPackageFragment fragment = new ShipPackageFragment();
                First_Fragment fragment = new First_Fragment();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frame_container, fragment).commit();
            }
        });

        btn_dash_deliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_add_shipment.setVisibility(View.GONE);
                btn_done_shipment.setVisibility(View.VISIBLE);

                progressBar.setProgress(66);
                btn_dash_ship.setBackgroundColor(getResources().getColor(R.color.tab_default));
                btn_dash_deliver.setBackgroundColor(getResources().getColor(R.color.tab_brown));
                btn_shop_online.setBackgroundColor(getResources().getColor(R.color.tab_default));

                DeliverPackageFragment fragment = new DeliverPackageFragment();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frame_container, fragment).commit();
            }
        });


        btn_shop_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_add_shipment.setVisibility(View.GONE);
                btn_done_shipment.setVisibility(View.GONE);


                progressBar.setProgress(100);
                btn_dash_ship.setBackgroundColor(getResources().getColor(R.color.tab_default));
                btn_dash_deliver.setBackgroundColor(getResources().getColor(R.color.tab_default));
                btn_shop_online.setBackgroundColor(getResources().getColor(R.color.tab_brown));

                //ShopOnlineFragment fragment = new ShopOnlineFragment();


                ShopOn fragment = new ShopOn();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out, android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frame_container, fragment).commit();


            }
        });


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

        btn_nav_cont_loc_adr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "button worked", Toast.LENGTH_LONG).show();
            }
        });

        tv_nav_cont_pal_req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), PalRequest.class);
                startActivity(inte);
            }
        });


        btn_nav_cont_loc_adr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_nav_cont_loc.setText("Houston");
                tv_nav_cont_address.setText("5800,White Line Avenue,\n Houston,Texas 55372");
                tv_nav_cont_phone.setText("713-555-5555");

            }
        });


        btn_nav_cont_int_adr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_nav_cont_loc.setText("Lagos");
                tv_nav_cont_address.setText("11A,Adeniro Close,\nMambuza,Lagos 23433");
                tv_nav_cont_phone.setText("021-323-5353");

            }
        });


        tv_nav_cont_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), Invite_Pal.class);
                startActivity(inte);
            }
        });


        btn_nav_cont_add_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddLoc_Fragment fragment = new AddLoc_Fragment();
                FragmentManager fm = getFragmentManager();
                fm.beginTransaction().replace(R.id.nav_container, fragment).commit();

                toggle.syncState();
                drawer.closeDrawer(GravityCompat.START);

               /* Intent inte = new Intent(getApplicationContext(), AddLocation.class);
                startActivity(inte);*/
            }
        });


        btm_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "tsdf", Toast.LENGTH_LONG).show();

                PhotoPickerIntent intent = new PhotoPickerIntent(DashBoardActivity.this);
                intent.setPhotoCount(1);
                intent.setColumn(4);
                intent.setShowCamera(true);
                startActivityForResult(intent, REQUEST_CODE);

            }
        });


        btn_add_shipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPickerIntent intent = new PhotoPickerIntent(DashBoardActivity.this);
                intent.setPhotoCount(1);
                intent.setColumn(4);
                intent.setShowCamera(true);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        List<String> photos = null;
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {

            Log.d("tag", "worked");
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();

            if (photos != null) {

                selectedPhotos.addAll(photos);
            }
            photoAdapter.notifyDataSetChanged();

            Uri uri = Uri.fromFile(new File(selectedPhotos.get(0)));

            Log.d("tag", selectedPhotos.get(0));
            Log.d("tag", "" + uri);

            //btm_cam.setImageURI(uri);
        }
    }


    public void previewPhoto(Intent intent) {
        startActivityForResult(intent, REQUEST_CODE);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        return false;
    }


}
