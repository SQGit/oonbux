package sqindia.net.oonbux;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rey.material.widget.Button;


@SuppressWarnings("deprecation")
public class ShipPkgFragment extends Fragment {

    com.rey.material.widget.TextView tv_ship_header, tv_ship_tke_pic_nm, tv_ship_tke_pic_txt, tv_ship_destin_nm, tv_ship_destin_txt, tv_ship_pal_rq_nm, tv_ship_pal_rq_txt, tv_ship_we_tk_nm, tv_ship_we_tk_txt;
    Button btn_sp_lkg, btn_snd2_pal, btn_add_ship;

    //Button b1, b2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View getView = inflater.inflate(R.layout.asdf, container, false);


        //  tv_ship_header = (TextView) getView.findViewById(R.id.htext);
  /*      tv_ship_tke_pic_nm = (TextView) getView.findViewById(R.id.txt_1);
        tv_ship_tke_pic_txt = (TextView) getView.findViewById(R.id.txt_1_txt);
        tv_ship_destin_nm = (TextView) getView.findViewById(R.id.txt_2);
        tv_ship_destin_txt = (TextView) getView.findViewById(R.id.txt_2_txt);
        tv_ship_pal_rq_nm = (TextView) getView.findViewById(R.id.txt_3);
        tv_ship_pal_rq_txt = (TextView) getView.findViewById(R.id.txt_3_txt);
        tv_ship_we_tk_nm = (TextView) getView.findViewById(R.id.txt_4);
        tv_ship_we_tk_txt = (TextView) getView.findViewById(R.id.txt_4_txt);*/

        // btn_sp_lkg = (Button) getView.findViewById(R.id.tbtn1);
        // btn_snd2_pal = (Button) getView.findViewById(R.id.tbtn2);

        //  btn_add_ship = (Button) getView.findViewById(R.id.add_sp_btn);

        /*ShipLuggageFragment fragment = new ShipLuggageFragment();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment).commit();*/

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/prox.otf");

        // tv_ship_header.setTypeface(tf);
      /*  tv_ship_tke_pic_nm.setTypeface(tf);
        tv_ship_tke_pic_txt.setTypeface(tf);
        tv_ship_destin_nm.setTypeface(tf);
        tv_ship_destin_txt.setTypeface(tf);
        tv_ship_pal_rq_nm.setTypeface(tf);
        tv_ship_pal_rq_txt.setTypeface(tf);
        tv_ship_we_tk_nm.setTypeface(tf);
        tv_ship_we_tk_txt.setTypeface(tf);*/

        //btn_sp_lkg.setTypeface(tf);
        //   btn_snd2_pal.setTypeface(tf);
        // btn_add_ship.setTypeface(tf);


        btn_sp_lkg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_sp_lkg.setTextColor(getResources().getColor(R.color.colorAccent));
                btn_snd2_pal.setTextColor(getResources().getColor(R.color.text_color_hint_light));

              /*  ShipLuggageFragment fragment = new ShipLuggageFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();*/

            }
        });

        btn_snd2_pal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_sp_lkg.setTextColor(getResources().getColor(R.color.text_color_hint_light));
                btn_snd2_pal.setTextColor(getResources().getColor(R.color.text_color));

           /*     SendPkgtoPalFragment fragment = new SendPkgtoPalFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();*/

            }
        });


        return getView;
    }
}
