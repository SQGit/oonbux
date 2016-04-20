package sqindia.net.oonbux;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
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


public class MapFrag extends FragmentActivity implements OnMapReadyCallback {

    LinearLayout phy_layout, prof_layout, bck_lt;
    ImageButton btn_back;
    TextView tv_htxt1, tv_htxt2, tv_htxt3, tv_htxt4, tv_phy_adr, tv_prof, tv_pers, tv_ver;
    com.rey.material.widget.TextView tv_header;
    Button btn_logout;
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
        tv_htxt1 = (TextView) findViewById(R.id.htxt1);
        tv_htxt2 = (TextView) findViewById(R.id.htxt2);
        tv_htxt3 = (TextView) findViewById(R.id.htxt3);
        tv_htxt4 = (TextView) findViewById(R.id.htxt4);
        tv_phy_adr = (TextView) findViewById(R.id.tv_phy_adr);
        tv_prof = (TextView) findViewById(R.id.tv_prf_in);
        tv_pers = (TextView) findViewById(R.id.tv_pers);
        tv_ver = (TextView) findViewById(R.id.tv_ver);
        btn_logout = (Button) findViewById(R.id.button_logout);
        et_fname = (MaterialEditText) findViewById(R.id.edittext_fname);
        et_lname = (MaterialEditText) findViewById(R.id.edittext_lname);
        bck_lt = (LinearLayout) findViewById(R.id.bck_layout);


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");

        tv_header.setTypeface(tf);
        tv_htxt1.setTypeface(tf);
        tv_htxt2.setTypeface(tf);
        tv_htxt3.setTypeface(tf);
        tv_htxt4.setTypeface(tf);
        tv_phy_adr.setTypeface(tf);
        tv_prof.setTypeface(tf);
        tv_pers.setTypeface(tf);
        tv_ver.setTypeface(tf);
        btn_logout.setTypeface(tf);
        et_fname.setTypeface(tf);
        et_lname.setTypeface(tf);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                startActivity(inte);
            }
        });

        phy_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goPhy = new Intent(getApplicationContext(), PhysicalDeliveryAddress.class);
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

