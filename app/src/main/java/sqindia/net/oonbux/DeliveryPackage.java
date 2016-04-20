package sqindia.net.oonbux;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.rey.material.widget.Button;

/**
 * Created by Salman on 4/20/2016.
 */
public class DeliveryPackage extends Fragment {

    ListView lv_deliver_list;
    Shipment_Adapter ship;
    Button bb;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.deliver_pkg_fragment, container, false);
        lv_deliver_list = (ListView) view.findViewById(R.id.deliver_list);

        bb = (Button) view.findViewById(R.id.add_sp_btn);

        bb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = new DeliveryPackage();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out,android.R.animator.fade_in, android.R.animator.fade_out);
                fragmentTransaction.replace(R.id.frame_container, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        //ship = new Shipment_Adapter(getActivity());
        ArrayAdapter adpt = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, Config.countries);
        lv_deliver_list.setAdapter(ship);


        return view;
    }



    /*
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.deliver_pkg_fragment,container,false);
        lv_deliver_list = (ListView) view.findViewById(R.id.deliver_list);

           Shipment_Adapter adapter = new Shipment_Adapter(getActivity());
       // ArrayAdapter adpt = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,Config.countries);
        //lv_deliver_list.setAdapter(adpt);

        lv_deliver_list.setAdapter(adapter);
        return view;
    }*/
}
