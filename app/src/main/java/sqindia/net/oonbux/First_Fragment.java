package sqindia.net.oonbux;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;


public class First_Fragment extends Fragment {

    com.rey.material.widget.TextView htxt,txt1,txt2,txt3,txt4,txt5,txt6,txt7,txt8;
    Button btn1,btn2,gobtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tab1,container,false);


        htxt = (TextView) v.findViewById(R.id.htext);

        htxt = (TextView) v.findViewById(R.id.htext);
        txt1 = (TextView) v.findViewById(R.id.txt_1);
        txt2 = (TextView) v.findViewById(R.id.txt_1_txt);
        txt3 = (TextView) v.findViewById(R.id.txt_2);
        txt4 = (TextView) v.findViewById(R.id.txt_2_txt);
        txt5 = (TextView) v.findViewById(R.id.txt_3);
        txt6 = (TextView) v.findViewById(R.id.txt_3_txt);
        txt7 = (TextView) v.findViewById(R.id.txt_4);
        txt8 = (TextView) v.findViewById(R.id.txt_4_txt);

        btn1 = (Button) v.findViewById(R.id.tbtn1);
        btn2 = (Button) v.findViewById(R.id.tbtn2);

        gobtn = (Button) v.findViewById(R.id.add_btn);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/prox.otf");

        htxt.setTypeface(tf);
        txt1.setTypeface(tf);
        txt2.setTypeface(tf);
        txt3.setTypeface(tf);
        txt4.setTypeface(tf);
        txt5.setTypeface(tf);
        txt6.setTypeface(tf);
        txt7.setTypeface(tf);
        txt8.setTypeface(tf);

        btn1.setTypeface(tf);
        btn2.setTypeface(tf);
        gobtn.setTypeface(tf);



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1.setTextColor(getResources().getColor(R.color.colorAccent));
                btn2.setTextColor(getResources().getColor(R.color.text_color_hint_light));
               // btn1.setCompoundDrawables(getResources().getDrawable(R.drawable.ship_ico), null, null, null);
               // btn2.setCompoundDrawables(getResources().getDrawable(R.drawable.ship_ico_hover),null,null,null);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1.setTextColor(getResources().getColor(R.color.text_color_hint_light));
                btn2.setTextColor(getResources().getColor(R.color.text_color));
               // btn1.setCompoundDrawables(getResources().getDrawable(R.drawable.pack_ico), null, null, null);
               // btn2.setCompoundDrawables(getResources().getDrawable(R.drawable.pack_ico_hover), null, null, null);
            }
        });







        return v;
    }
}
