package sqindia.net.oonbux;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.rey.material.widget.Button;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.TextView;


public class Shipment_Adapter extends BaseAdapter {

    Context c1;
    TextView header,perpric;
    CheckBox ck_sml,ck_med,ck_lrg,ck_add;
    Button btn_addid;


    public Shipment_Adapter(Context c1) {
        this.c1 = c1;
    }

    @Override
    public int getCount() {
        return 3;
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
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflat = (LayoutInflater) c1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflat.inflate(R.layout.shipment_listview_adapter, null);

        header = (TextView) view.findViewById(R.id.tv_ship_header);
        perpric = (TextView) view.findViewById(R.id.tv_per);

        ck_sml = (CheckBox) view.findViewById(R.id.cb_small);
        ck_med = (CheckBox) view.findViewById(R.id.cb_medium);
        ck_lrg = (CheckBox) view.findViewById(R.id.cb_large);
        ck_add = (CheckBox) view.findViewById(R.id.cb_addto);
        btn_addid = (Button) view.findViewById(R.id.button_addid);


        Typeface tf = Typeface.createFromAsset(c1.getAssets(), "fonts/prox.otf");

        header.setTypeface(tf);
        perpric.setTypeface(tf);
        ck_sml.setTypeface(tf);
        ck_med.setTypeface(tf);
        ck_lrg.setTypeface(tf);
        ck_add.setTypeface(tf);
        btn_addid.setTypeface(tf);


        return view;
    }
}
