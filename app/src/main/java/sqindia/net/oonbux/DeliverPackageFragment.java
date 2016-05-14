package sqindia.net.oonbux;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.rey.material.widget.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;


public class DeliverPackageFragment extends Fragment {


    public final static int REQUEST_CODE = 1;
    public ArrayList<String> shipment_photos;
    ListView lv_deliver_list;
    Button btn_addshipment;
    ArrayList<String> selectedPhotos = new ArrayList<>();
    Shipment_Adapter ship_adapter;
    Adapter_Shipment adapt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View getview = inflater.inflate(R.layout.fragment_deliver_package, container, false);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String sois = sharedPreferences.getString("shipment_photo", "");


        Log.d("tag", "0" + sois);


        shipment_photos = new ArrayList<>();

        shipment_photos.add(sois);
        Log.d("tag", "1" + shipment_photos.get(0));

        lv_deliver_list = (ListView) getview.findViewById(R.id.deliver_list);
        btn_addshipment = (Button) getview.findViewById(R.id.add_sp_btn);

      /*  ship_adapter = new Shipment_Adapter(getActivity(), shipment_photos);
        lv_deliver_list.setAdapter(ship_adapter);*/

        adapt = new Adapter_Shipment(getActivity(), shipment_photos);
        lv_deliver_list.setAdapter(adapt);

        adapt.notifyDataSetChanged();

        //  ship_adapter.notifyDataSetChanged();


        btn_addshipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPickerIntent intent = new PhotoPickerIntent(getActivity());
                intent.setPhotoCount(1);
                intent.setColumn(4);
                intent.setShowCamera(true);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });


        return getview;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        List<String> photos = null;
        if (resultCode == getActivity().RESULT_OK && requestCode == REQUEST_CODE) {

            Log.d("tag", "worked");
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();

            if (photos != null) {

                selectedPhotos.addAll(photos);
            }


            Uri uri = Uri.fromFile(new File(selectedPhotos.get(0)));

            Log.d("tag", selectedPhotos.get(0));
            Log.d("tag", "" + uri);

            shipment_photos.add(selectedPhotos.get(0));
            // ship_adapter.notifyDataSetChanged();
            Log.d("tag", "2" + shipment_photos.get(0));
            adapt.notifyDataSetChanged();

        }
    }


}
