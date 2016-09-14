package sqindia.net.oonbux.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.rey.material.widget.CheckBox;
import com.rey.material.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import sqindia.net.oonbux.Activity.AddLocation;
import sqindia.net.oonbux.R;

/**
 * Created by Salman on 8/22/2016.
 */

public class GridAdapterNig extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    ArrayList<HashMap<String, String>> address;
    HashMap<String, String> result = new HashMap<String, String>();
    String pos;
    private int selectedPosition = -1;


    public GridAdapterNig(Context context, ArrayList<HashMap<String, String>> datas) {

        this.context = context;
        this.address = datas;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return address.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        final MyViewHolder mViewHolder;
        TextView tv_addr1, tv_addr2, tv_city, tv_state, tv_zip, tv_country;
        final CheckBox cb_choose;


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        if (convertView == null) {

            convertView = inflater.inflate(R.layout.gridview_adpater, parent, false);
            mViewHolder = new MyViewHolder(convertView);

            tv_addr1 = (TextView) convertView.findViewById(R.id.tv_addr_line1);
            tv_addr2 = (TextView) convertView.findViewById(R.id.tv_addr_line2);
            tv_city = (TextView) convertView.findViewById(R.id.tv_city);
            tv_state = (TextView) convertView.findViewById(R.id.tv_state);
            tv_zip = (TextView) convertView.findViewById(R.id.tv_zip);
            tv_country = (TextView) convertView.findViewById(R.id.tv_country);
            cb_choose = (CheckBox) convertView.findViewById(R.id.chkbox);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (MyViewHolder) convertView.getTag();

            tv_addr1 = mViewHolder.tv_addr1;
            tv_addr2 = mViewHolder.tv_addr2;
            tv_city = mViewHolder.tv_city;
            tv_state = mViewHolder.tv_state;
            tv_zip = mViewHolder.tv_zip;
            tv_country = mViewHolder.tv_country;
            cb_choose = mViewHolder.cb_choose;
        }

        result = address.get(position);

        pos = sharedPreferences.getString("nig", "");

        if (pos != "") {
            selectedPosition = Integer.valueOf(pos);
        }


        if (position == selectedPosition) {
            cb_choose.setChecked(true);

        } else {
            cb_choose.setChecked(false);

        }


        tv_addr1.setText(result.get(AddLocation.addr1));
        tv_addr2.setText(result.get(AddLocation.addr2));
        tv_city.setText(result.get(AddLocation.city));
        tv_state.setText(result.get(AddLocation.state));
        tv_zip.setText(result.get(AddLocation.zip));
        tv_country.setText(result.get(AddLocation.country));


        cb_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CheckBox checkBox = (CheckBox) v;
                if (checkBox.isChecked()) {
                    cb_choose.setTag(position);
                    selectedPosition = position;

                    result = address.get(position);

                    Log.e("tag", position + "true" + result.get(AddLocation._id));


                    editor.putString("virtual_address2", result.get(AddLocation._id));
                    editor.putString("va2_line1", result.get(AddLocation.addr1));
                    editor.putString("va2_line2", result.get(AddLocation.addr2));
                    editor.putString("va2_city", result.get(AddLocation.city));
                    editor.putString("va2_state", result.get(AddLocation.state));
                    editor.putString("va2_zip", result.get(AddLocation.zip));
                    editor.putString("va2_country", result.get(AddLocation.country));
                    editor.putString("nig", String.valueOf(position));
                    editor.commit();


                } else {
                    selectedPosition = -1;
                    Log.e("tag", position + "false" + result.get(AddLocation._id));

                    editor.putString("virtual_address2", "");
                    editor.commit();

                }
                notifyDataSetChanged();
            }
        });


        return convertView;
    }


    private class MyViewHolder {

        TextView tv_addr1, tv_addr2, tv_city, tv_state, tv_zip, tv_country;
        CheckBox cb_choose;

        public MyViewHolder(View item) {
            tv_addr1 = (TextView) item.findViewById(R.id.tv_addr_line1);
            tv_addr2 = (TextView) item.findViewById(R.id.tv_addr_line2);
            tv_city = (TextView) item.findViewById(R.id.tv_city);
            tv_state = (TextView) item.findViewById(R.id.tv_state);
            tv_zip = (TextView) item.findViewById(R.id.tv_zip);
            tv_country = (TextView) item.findViewById(R.id.tv_country);
            cb_choose = (CheckBox) item.findViewById(R.id.chkbox);
        }
    }
}
