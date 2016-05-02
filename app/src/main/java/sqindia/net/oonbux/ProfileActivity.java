package sqindia.net.oonbux;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.Button;


public class ProfileActivity extends FragmentActivity implements OnMapReadyCallback {

    LinearLayout phy_layout, prof_layout;
    ImageButton btn_back;
    TextView tv_oonbuxid_txt, tv_oonbux_id, tv_location, tv_phone, tv_fname, tv_lname, tv_phy_adr, tv_prof, tv_pers, tv_ver;
    com.rey.material.widget.TextView tv_header;
    Button btn_logout, btn_finish;

    com.rey.material.widget.LinearLayout lt_back;

    // EditText et_fname,et_lname;

    MaterialEditText et_fname, et_lname;


    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        phy_layout = (LinearLayout) findViewById(R.id.pda_layout);
        prof_layout = (LinearLayout) findViewById(R.id.pi_layout);
        btn_back = (ImageButton) findViewById(R.id.btn_back);

        tv_header = (com.rey.material.widget.TextView) findViewById(R.id.tv_hd_txt);
        tv_oonbuxid_txt = (TextView) findViewById(R.id.textview_id_txt);
        tv_oonbux_id = (TextView) findViewById(R.id.textview_id);
        tv_location = (TextView) findViewById(R.id.textview_location);
        tv_phone = (TextView) findViewById(R.id.textview_phone);
        tv_fname = (TextView) findViewById(R.id.textview_fname);
        tv_lname = (TextView) findViewById(R.id.textview_lname);
        tv_phy_adr = (TextView) findViewById(R.id.tv_phy_adr);
        tv_prof = (TextView) findViewById(R.id.tv_prf_in);
        tv_pers = (TextView) findViewById(R.id.tv_pers);
        tv_ver = (TextView) findViewById(R.id.tv_ver);
        btn_logout = (Button) findViewById(R.id.button_logout);
        btn_finish = (Button) findViewById(R.id.button_finish);
        //  et_fname = (MaterialEditText) findViewById(R.id.edittext_fname);
        //  et_lname = (MaterialEditText) findViewById(R.id.edittext_lname);
//        bck_lt = (LinearLayout) findViewById(R.id.bck_layout);

        lt_back = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_back);


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");

        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");

        tv_header.setTypeface(tf1);

        tv_oonbuxid_txt.setTypeface(tf);
        tv_oonbux_id.setTypeface(tf);
        tv_location.setTypeface(tf);
        tv_fname.setTypeface(tf);
        tv_lname.setTypeface(tf);
        tv_phone.setTypeface(tf);

        tv_phy_adr.setTypeface(tf);
        tv_prof.setTypeface(tf);
        tv_pers.setTypeface(tf);
        tv_ver.setTypeface(tf);
        btn_logout.setTypeface(tf);
        btn_finish.setTypeface(tf);


        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                startActivity(inte);
            }
        });

        phy_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goPhy = new Intent(getApplicationContext(), DeliveryAddress.class);
                startActivity(goPhy);
            }
        });


        prof_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goPhy = new Intent(getApplicationContext(), ProfileInfo.class);
                startActivity(goPhy);
            }
        });

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goPhy = new Intent(getApplicationContext(), DashBoardActivity.class);
                startActivity(goPhy);
                finish();


                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("profile", "12345");
                editor.commit();



            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng sydney = new LatLng(29.6396935, -95.6845927);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //  mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        // mMap.animateCamera(CameraUpdateFactory.zoomTo( 10.0f ));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 14));


    }
}

