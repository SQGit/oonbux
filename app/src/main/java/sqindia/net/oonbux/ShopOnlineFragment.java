package sqindia.net.oonbux;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ShopOnlineFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View getview = inflater.inflate(R.layout.shop_online_fragment, container, false);
        return getview;
    }
}
