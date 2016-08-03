package sqindia.net.oonbux.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.rey.material.widget.TextView;

import java.util.ArrayList;

import sqindia.net.oonbux.R;

/**
 * Created by Salman on 6/6/2016.
 */
public class ListAdapter_Class extends ArrayAdapter<String> {

    Context cc;
    ArrayList<String> data_lists;
    int resourceid;

    public ListAdapter_Class(Context context, int textViewResourceId, ArrayList<String> objects) {
        super(context, textViewResourceId, objects);
        this.cc = context;
        this.data_lists = objects;
        this.resourceid = textViewResourceId;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }


    public View getCustomView(int position, View row, ViewGroup parent) {

        Typeface tf = Typeface.createFromAsset(cc.getAssets(), "fonts/prox.otf");


        LayoutInflater inflater = (LayoutInflater) cc.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        //LayoutInflater inflater = cc.getLayoutInflater();
        View arow = inflater.inflate(resourceid, parent, false);

        TextView label = (TextView) arow.findViewById(R.id.text_spin);

        label.setTypeface(tf);


        label.setText(data_lists.get(position));


        return arow;
    }
}
