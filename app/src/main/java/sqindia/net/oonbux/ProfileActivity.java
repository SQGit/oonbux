package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Salman on 4/15/2016.
 */
public class ProfileActivity extends Activity {

    static final LatLng TutorialsPoint = new LatLng(21, 57);
    LinearLayout phy_layout, prof_layout;
    ImageButton btn_back;
    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        phy_layout = (LinearLayout) findViewById(R.id.pda_layout);
        prof_layout = (LinearLayout) findViewById(R.id.pi_layout);

        btn_back = (ImageButton) findViewById(R.id.btn_back);

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

        try {
            if (googleMap == null) {
                googleMap = ((MapFragment) getFragmentManager().
                        findFragmentById(R.id.map)).getMap();
            }
            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            Marker TP = googleMap.addMarker(new MarkerOptions().
                    position(TutorialsPoint).title("TutorialsPoint"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
