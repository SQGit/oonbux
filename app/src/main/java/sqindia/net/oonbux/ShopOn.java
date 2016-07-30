package sqindia.net.oonbux;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Salman on 4/23/2016.
 */

//adsfasdfs
public class ShopOn extends Fragment {

        TextView tv_sample;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View getview = inflater.inflate(R.layout.fragment_shop_online, container, false);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/prox.otf");

            tv_sample = (TextView) getview.findViewById(R.id.text);

        tv_sample.setTypeface(tf);

        return getview;
    }
}
