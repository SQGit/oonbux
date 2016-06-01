package sqindia.net.oonbux;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.rey.material.widget.TextView;

/**
 * Created by Salman on 5/26/2016.
 */
public class ChoosePal_Adapter extends BaseAdapter {

    Context c1;
    String name;


    public ChoosePal_Adapter(Context c, String ss) {
        this.c1 = c;
        this.name = ss;
    }

    @Override
    public int getCount() {
        return 1;
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

        Typeface tf = Typeface.createFromAsset(c1.getAssets(), "fonts/prox.otf");

        TextView tv_name;

        LayoutInflater inflat = (LayoutInflater) c1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflat.inflate(R.layout.choosepal_adpater, null);

        tv_name = (TextView) view.findViewById(R.id.tv_name);

        tv_name.setTypeface(tf);

        tv_name.setText(name);


        return view;

    }
}
