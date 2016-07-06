package sqindia.net.oonbux;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;

//asdfdf
public class First_Fragment extends Fragment {

    TextView htxt, txt1, txt_one, txt3, txt_two, txt5, txt_three, txt7, txt_four;
    Button btn1, btn2, gobtn;
    ImageView imgview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_ship_package, container, false);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("fromdash", "");
        editor.commit();



        htxt = (TextView) v.findViewById(R.id.htext);

        htxt = (TextView) v.findViewById(R.id.htext);
        txt1 = (TextView) v.findViewById(R.id.txt_1);
        txt_one = (TextView) v.findViewById(R.id.txt_1_txt);
        txt3 = (TextView) v.findViewById(R.id.txt_2);
        txt_two = (TextView) v.findViewById(R.id.txt_2_txt);
        txt5 = (TextView) v.findViewById(R.id.txt_3);
        txt_three = (TextView) v.findViewById(R.id.txt_3_txt);
        txt7 = (TextView) v.findViewById(R.id.txt_4);
        txt_four = (TextView) v.findViewById(R.id.txt_4_txt);

        btn1 = (Button) v.findViewById(R.id.tbtn1);
        btn2 = (Button) v.findViewById(R.id.tbtn2);

        imgview = (ImageView) v.findViewById(R.id.imgview_3);

        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/prox.otf");

        htxt.setTypeface(tf);
        txt1.setTypeface(tf);
        txt_one.setTypeface(tf);
        txt3.setTypeface(tf);
        txt_two.setTypeface(tf);
        txt5.setTypeface(tf);
        txt_three.setTypeface(tf);
        txt7.setTypeface(tf);
        txt_four.setTypeface(tf);

        btn1.setTypeface(tf);
        btn2.setTypeface(tf);
//        gobtn.setTypeface(tf);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1.setTextColor(getResources().getColor(R.color.colorAccent));
                btn2.setTextColor(getResources().getColor(R.color.text_color_hint_light));
                // btn1.setCompoundDrawables(getResources().getDrawable(R.drawable.ship_ico), null, null, null);
                // btn2.setCompoundDrawables(getResources().getDrawable(R.drawable.ship_ico_hover),null,null,null);


                Drawable img = getActivity().getResources().getDrawable(R.drawable.ship_ico);
                btn1.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                Drawable img1 = getActivity().getResources().getDrawable(R.drawable.pack_ico);
                btn2.setCompoundDrawablesWithIntrinsicBounds(img1, null, null, null);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    imgview.setBackground(getResources().getDrawable(R.drawable.doller));
                }

                txt_one.setText(getText(R.string.txt_1_1));
                txt_two.setText(getText(R.string.txt_1_2));
                txt_three.setText(getText(R.string.txt_1_3));
                txt_four.setText(getText(R.string.txt_1_4));


            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1.setTextColor(getResources().getColor(R.color.text_color_hint_light));
                btn2.setTextColor(getResources().getColor(R.color.text_color));
                // btn1.setCompoundDrawables(getResources().getDrawable(R.drawable.pack_ico), null, null, null);
                // btn2.setCompoundDrawables(getResources().getDrawable(R.drawable.pack_ico_hover), null, null, null);


                Drawable img = getActivity().getResources().getDrawable(R.drawable.ship_ico_hover);
                btn1.setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                Drawable img1 = getActivity().getResources().getDrawable(R.drawable.pack_ico_hover);
                btn2.setCompoundDrawablesWithIntrinsicBounds(img1, null, null, null);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    imgview.setBackground(getResources().getDrawable(R.drawable.loc_wt_ico));
                }

                txt_one.setText(getText(R.string.txt_1));
                txt_two.setText(getText(R.string.txt_2));
                txt_three.setText(getText(R.string.txt_3));
                txt_four.setText(getText(R.string.txt_4));


            }
        });


        return v;
    }
}
