package sqindia.net.oonbux;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.rey.material.widget.Button;


/**
 * Created by Salman on 4/16/2016.
 */

public class ShipYourLuggage extends Fragment {

    TabHost tabHost;

    Button b, cc;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.asdf, container, false);

        Ship_Pkg_Pkg fragment = new Ship_Pkg_Pkg();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.asd_frame_container, fragment).commit();


        b = (Button) v.findViewById(R.id.button1);
        cc = (Button) v.findViewById(R.id.button2);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///   Toast.makeText(getActivity(),"app",Toast.LENGTH_LONG).show();

                Ship_Pkg_Pkg fragment = new Ship_Pkg_Pkg();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.asd_frame_container, fragment).commit();

            }
        });

        cc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getActivity(),"app",Toast.LENGTH_LONG).show();

                Send_Pal_Pal fragment = new Send_Pal_Pal();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.asd_frame_container, fragment).commit();

            }
        });


      /*  tabHost = (TabHost) v.findViewById(android.R.id.tabhost);
        TabHost.TabSpec tab1 = tabHost.newTabSpec("Ship Your Luggage");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Send your package to pal");


        tab1.setIndicator("Map");
        Intent igom = new Intent(MapView.this, MapView_Map.class);
        igom.putExtra("service", emgurl);
        tab1.setContent(igom);

        tab2.setIndicator("List");
        Intent igo = new Intent(MapView.this, MapView_List.class);
        igo.putExtra("service", emgurl);
        tab2.setContent(igo);

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);*/


        return v;
    }
}
