package sqindia.net.oonbux;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class DeliverPackageFragment extends Fragment {


    ListView lv_deliver_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View getview = inflater.inflate(R.layout.fragment_deliver_package, container, false);

        lv_deliver_list = (ListView) getview.findViewById(R.id.deliver_list);

        Shipment_Adapter adapter = new Shipment_Adapter(getActivity());
        lv_deliver_list.setAdapter(adapter);


        return getview;
    }
}
