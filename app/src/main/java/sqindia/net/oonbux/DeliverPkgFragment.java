package sqindia.net.oonbux;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class DeliverPkgFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View getview = inflater.inflate(R.layout.deliver_pkg_fragment, container, false);
        return getview;
    }
}
