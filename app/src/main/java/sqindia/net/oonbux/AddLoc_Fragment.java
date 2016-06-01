package sqindia.net.oonbux;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.rey.material.widget.TextView;

/**
 * Created by Salman on 4/23/2016.
 */
public class AddLoc_Fragment extends Fragment {

    GridView grid1,grid2;
    TextView header;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.activity_addloction,container,false);




        grid1 = (GridView) view.findViewById(R.id.grd_usa);
        grid2 = (GridView) view.findViewById(R.id.grd_nig);
        header = (TextView) view.findViewById(R.id.tv_hd_txt);


        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/nexa.otf");

        header.setTypeface(tf);

       // Gridview_adapter adapter = new Gridview_adapter(getActivity());

       // grid1.setAdapter(adapter);
       // grid2.setAdapter(adapter);

        return view;
    }
}
