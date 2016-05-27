package sqindia.net.oonbux;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.rey.material.widget.Button;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;


public class DeliverPackageFragment extends Fragment {


    public final static int REQUEST_CODE = 1;
    public ArrayList<String> shipment_photos;
    ListView lv_deliver_list;
    Button btn_addshipment, btn_nextshipment;
    ArrayList<String> selectedPhotos = new ArrayList<>();
    Shipment_Adapter ship_adapter;
    Adapter_Shipment adapt;
    ImageButton btn_nxt;
    ArrayList<String> ship_size = new ArrayList<>();
    SharedPreferences sharedPreferences;

    SharedPreferences.Editor edit;

    private SQLiteDatabase db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View getview = inflater.inflate(R.layout.fragment_deliver_package, container, false);


        createDatabase();


        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String sois = sharedPreferences.getString("shipment_photo", "");

        String cktis = sharedPreferences.getString("fromdash", "");


        Log.d("tag", "0" + cktis);


        shipment_photos = new ArrayList<>();

        shipment_photos.add(sois);
        Log.d("tag", "1" + shipment_photos.get(0));

        lv_deliver_list = (ListView) getview.findViewById(R.id.deliver_list);
        btn_addshipment = (Button) getview.findViewById(R.id.add_sp_btn);

        btn_nxt = (ImageButton) getview.findViewById(R.id.button_next);
        // btn_nextshipment = (Button) getview.findViewById(R.id.next_button);

      /*  ship_adapter = new Shipment_Adapter(getActivity(), shipment_photos);
        lv_deliver_list.setAdapter(ship_adapter);*/

        /*adapt = new Adapter_Shipment(getActivity(), shipment_photos);
        lv_deliver_list.setAdapter(adapt);*/


        if (cktis.equals("asdfg")) {
            btn_addshipment.setVisibility(View.INVISIBLE);
            adapt = new Adapter_Shipment(getActivity(), shipment_photos);
            lv_deliver_list.setAdapter(adapt);
        } else {
            btn_addshipment.setVisibility(View.VISIBLE);
            btn_nxt.setVisibility(View.INVISIBLE);
        }


        //adapt.notifyDataSetChanged();

        //  ship_adapter.notifyDataSetChanged();


        btn_addshipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shipment_photos.clear();
                PhotoPickerIntent intent = new PhotoPickerIntent(getActivity());
                intent.setPhotoCount(1);
                intent.setColumn(4);
                intent.setShowCamera(true);
                startActivityForResult(intent, REQUEST_CODE);
                btn_nxt.setVisibility(View.VISIBLE);

            }
        });

      /*  btn_nextshipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ioi = new Intent(getActivity(),ShipmentNext.class);
                startActivity(ioi);
            }
        });*/

        btn_nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String size = sharedPreferences.getString("ship_size", "");
                String pickup = sharedPreferences.getString("ship_pickup", "");
                String photo = sharedPreferences.getString("shipment_photo", "");

                insertIntoDB(size, pickup, photo);

                Log.e("tag", "" + size + "\t" + pickup + "\t" + photo);

                ship_size.add(size);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                Set<String> set = new HashSet<String>();
                set.add(size);
                //set.addAll(ship_size);
                editor.putStringSet("ship", set);
                editor.commit();

                editor.putString("s_size", size);
                editor.putString("s_photo", photo);
                editor.commit();

                Intent ioi = new Intent(getActivity(), ShipmentNext.class);
                startActivity(ioi);
            }
        });


        return getview;
    }


    protected void createDatabase() {
        Log.d("tag", "createdb");
        db = getActivity().openOrCreateDatabase("oonbux", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS cart(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, size VARCHAR, pickup VARCHAR, photo VARCHAR);");
    }

    protected void insertIntoDB(String a, String b, String c) {
        Log.d("tag", "insertdb " + a + b + c);
        String query = "INSERT INTO cart (size,pickup,photo) VALUES('" + a + "', '" + b + "', '" + c + "');";
        db.execSQL(query);
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
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("shipment_photo", selectedPhotos.get(0));
            editor.commit();
            shipment_photos.add(selectedPhotos.get(0));
            // ship_adapter.notifyDataSetChanged();
            Log.d("tag", "2" + shipment_photos.get(0));

            adapt = new Adapter_Shipment(getActivity(), shipment_photos);
            lv_deliver_list.setAdapter(adapt);
            adapt.notifyDataSetChanged();
            btn_nxt.setVisibility(View.VISIBLE);
            btn_addshipment.setVisibility(View.INVISIBLE);

        }
    }


}
