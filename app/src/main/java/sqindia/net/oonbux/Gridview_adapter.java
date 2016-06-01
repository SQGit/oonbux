package sqindia.net.oonbux;

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

/**
 * Created by Salman on 4/23/2016.
 */
public class Gridview_adapter extends BaseAdapter {

    Context c1;
    //ArrayList<GridviewDatas> vir_addrs;
    TextView tv_addr1, tv_addr2, tv_city, tv_state, tv_zip, tv_country;
    CheckBox cb_choose;
    ArrayList<HashMap<String, String>> vir_addrs;
    HashMap<String, String> resultp = new HashMap<String, String>();
    int count;
    ArrayList<String> lss;


    public Gridview_adapter(Context c1, ArrayList<HashMap<String, String>> datas) {
        this.c1 = c1;
        this.vir_addrs = datas;

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
    public View getView(int position, View view, ViewGroup parent) {

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

        cb_choose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (lss.size() > 1) {
                    cb_choose.setChecked(false);
                } else {
                    lss.add(resultp.get(AddLocation._id));
                    cb_choose.setChecked(true);
                }

           /*     if (isChecked) {
                    if (count < 1) {

                        count++;
                        Log.d("tag", "+" + count);
                    } else {
                        cb_choose.setChecked(false);
                        Log.d("tag", "+i" + count);
                    }
                } else if (!isChecked) {
                    count = count - 1;
                    Log.d("tag", "-" + count);
                }*/
            }
        });


        return view;
    }
}
