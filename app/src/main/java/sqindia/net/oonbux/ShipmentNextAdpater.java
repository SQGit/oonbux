package sqindia.net.oonbux;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.rey.material.widget.Button;
import com.rey.material.widget.CheckBox;

import java.util.ArrayList;


public class ShipmentNextAdpater extends BaseAdapter {

    Context c1;
    CheckBox ck_sml;
    Button btn_add_pal;
    ArrayList<String> sam;
    int ls = 3;


    public ShipmentNextAdpater(Context c1) {
        this.c1 = c1;

    }


    @Override
    public int getCount() {
        return ls;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {

        return position;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LinearLayout layout;

        ls = 5;

        LayoutInflater inflat = (LayoutInflater) c1.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflat.inflate(R.layout.shipment_next_apapter, null);


        layout = (LinearLayout) view.findViewById(R.id.linearlayout);


        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ls = ls - 1;
                notifyDataSetChanged();
            }
        });

   /*     btn_add_pal = (Button) view.findViewById(R.id.button_next);

        btn_add_pal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newe = new Intent(c1, ChoosePal.class);
                newe.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                c1.startActivity(newe);
            }
        });*/

//        ck_sml = (CheckBox) view.findViewById(R.id.cb_small);


        // Typeface tf = Typeface.createFromAsset(c1.getAssets(), "fonts/prox.otf");
//

//        btn_add_pal.setTypeface(tf);


        return view;
    }


}
