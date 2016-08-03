package sqindia.net.oonbux.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import sqindia.net.oonbux.R;

/**
 * Created by Salman on 7/19/2016.
 */
public class AdapterGridView extends BaseAdapter {


    ArrayList<HashMap<String, String>> vir_address;
    HashMap<String, String> resultp = new HashMap<String, String>();
    Context context;


    AdapterGridView(Context c, ArrayList<HashMap<String, String>> datas){
        this.context = c;
        this.vir_address = datas;
    }

    @Override
    public int getCount() {
        return 0;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflat = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflat.inflate(R.layout.gridview_adpater, null);

        resultp = vir_address.get(position);




        return convertView;
    }
}
