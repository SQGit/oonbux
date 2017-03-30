package sqindia.net.oonbux.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;

import com.rey.material.widget.CheckBox;
import com.rey.material.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import sqindia.net.oonbux.Activity.AddLocation;
import sqindia.net.oonbux.R;

/**
 * Created by Salman on 4/23/2016.
 */

//asdfas
public class Gridview_adapter extends BaseAdapter {

    Context c1;
    //ArrayList<GridviewDatas> vir_addrs;
    TextView tv_addr1, tv_addr2, tv_city, tv_state, tv_zip, tv_country;


    CheckBox cb_choose;
    ArrayList<HashMap<String, String>> vir_addrs;
    HashMap<String, String> resultp = new HashMap<String, String>();
    int count;
    ArrayList<String> lss;
    Integer selected_position = -1;
    boolean array[];

    String[] adatas;


    public Gridview_adapter(Context c1, ArrayList<HashMap<String, String>> datas) {
        this.c1 = c1;
        this.vir_addrs = datas;
        this.array = new boolean[vir_addrs.size()];

    }

    @Override
    public int getCount() {
        return vir_addrs.size();
    }

    @Override
    public Object getItem(int position) {
        return vir_addrs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {

        // final GridviewDatas getSet = vir_addrs.get(position);



        LayoutInflater inflat = (LayoutInflater) c1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflat.inflate(R.layout.gridview_adpater, null);

        resultp = vir_addrs.get(position);

        cb_choose = (CheckBox) view.findViewById(R.id.chkbox);

        tv_addr1 = (TextView) view.findViewById(R.id.tv_addr_line1);
        tv_addr2 = (TextView) view.findViewById(R.id.tv_addr_line2);
        tv_city = (TextView) view.findViewById(R.id.tv_city);
        tv_state = (TextView) view.findViewById(R.id.tv_state);
        tv_zip = (TextView) view.findViewById(R.id.tv_zip);
        tv_country = (TextView) view.findViewById(R.id.tv_country);


        tv_addr1.setText(resultp.get(AddLocation.addr1));
        tv_addr2.setText(resultp.get(AddLocation.addr2));
        tv_city.setText(resultp.get(AddLocation.city));
        tv_state.setText(resultp.get(AddLocation.state));
        tv_zip.setText(resultp.get(AddLocation.zip));
        tv_country.setText(resultp.get(AddLocation.country));

        //String id = resultp.get(AddLocation._id);


        count = 0;

        lss = new ArrayList<>();


        if (position == selected_position) {
            cb_choose.setChecked(true);
        } else {
            cb_choose.setChecked(false);
        }


        cb_choose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {
                    selected_position = position;
                } else {
                    selected_position = -1;
                }

                notifyDataSetChanged();


            }
        });













    /*    cb_choose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(cb_choose.isChecked())
                {
                    array[position]=true;


                }else{
                    array[position]=false;
                }
            }
        });
*/


        //cb_choose.setChecked(array[position]);


       /* cb_choose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {

                    String uslists = resultp.get(AddLocation._id);
                    String addrline1 = resultp.get(AddLocation.addr1);
                    String addrline2 = resultp.get(AddLocation.addr2);
                    String city = resultp.get(AddLocation.city);
                    String state = resultp.get(AddLocation.state);
                    String zip = resultp.get(AddLocation.zip);
                    String country = resultp.get(AddLocation.country);

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c1);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("virtual_address1", uslists);
                    editor.putString("va1_line1", addrline1);
                    editor.putString("va1_line2", addrline2);
                    editor.putString("va1_city", city);
                    editor.putString("va1_state", state);
                    editor.putString("va1_zip", zip);
                    editor.putString("va1_country", country);
                    editor.commit();

                    Log.d("tag", position + "us_yes" + sharedPreferences.getString("virtual_address1", ""));

                 *//*   selected_position =  position;
                    array[position] = true;
                    lss.add(resultp.get(AddLocation._id));

                    if(lss.size() == 1){

                        adatas = new String[lss.size()];

                        StringBuilder sb = new StringBuilder();
                        String[] playlists;
                        for (int i = 0; i < adatas.length; i++) {
                            adatas[i] = lss.get(i);
                            Log.d("tag",""+adatas[i]);
                            sb.append(adatas[i]).append(",");
                        }


                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c1);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("virtual_address", sb.toString());
                        editor.commit();*//*


                } else {

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(c1);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("virtual_address1", "");
                    editor.commit();
                    Log.d("tag", position + "us_no" + sharedPreferences.getString("virtual_address1", ""));


                    //array[position] = false;
                }

            }
        });*/




/*        cb_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cb_choose.isChecked()){

                    array[position] = true;

                    Log.d("tag",""+array[position]);
                }
                else {
                    array[position] = false;

                    Log.d("tag",""+array[position]);
                }
            }
        });



        cb_choose.setOnCheckedChangeListener(null);


        cb_choose.setChecked(array[position]);*/


        return view;
    }





}
