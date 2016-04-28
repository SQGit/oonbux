package sqindia.net.oonbux;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.rey.material.widget.CheckBox;
import com.rey.material.widget.TextView;

/**
 * Created by Salman on 4/26/2016.
 */
public class Invite_Pal_Adapter extends BaseAdapter {

    Context c1;
    TextView name, oonbuxid, join;
    CheckBox ck_bk;


    public Invite_Pal_Adapter(Context c1) {
        this.c1 = c1;
    }


    @Override
    public int getCount() {
        return 9;
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


        LayoutInflater inflat = (LayoutInflater) c1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflat.inflate(R.layout.invitepal_adapter, null);

        name = (TextView) view.findViewById(R.id.tv_name);
        oonbuxid = (TextView) view.findViewById(R.id.tv_left);
        join = (TextView) view.findViewById(R.id.tv_join);


        Typeface tf = Typeface.createFromAsset(c1.getAssets(), "fonts/prox.otf");

        name.setTypeface(tf);
        oonbuxid.setTypeface(tf);
        join.setTypeface(tf);


        return view;
    }
}
