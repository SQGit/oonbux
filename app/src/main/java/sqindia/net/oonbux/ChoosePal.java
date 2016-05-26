package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.rengwuxian.materialedittext.MaterialAutoCompleteTextView;
import com.rey.material.widget.TextView;

/**
 * Created by Salman on 5/18/2016.
 */
public class ChoosePal extends Activity {

    com.rey.material.widget.LinearLayout lt_back, lt_add;
    ImageButton btn_next, btn_addpal;
    MaterialAutoCompleteTextView met_palnames;
    String[] palnames;
    ListView lview;
    TextView tv_header;

    ChoosePal_Adapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosepal);


        palnames = new String[]{"samson", "wincent", "ariz", "bonz", "criz", "dobrain"};

        lt_back = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_back);
        lt_add = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_add);

        btn_next = (ImageButton) findViewById(R.id.button_next);
        btn_addpal = (ImageButton) findViewById(R.id.button_add_pal);

        lview = (ListView) findViewById(R.id.lview);

        tv_header = (TextView) findViewById(R.id.tv_hd_txt);

        met_palnames = (MaterialAutoCompleteTextView) findViewById(R.id.edittext_pal_names);


        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");


        tv_header.setTypeface(tf);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.dropdown_lists, R.id.text_spin, palnames);
        met_palnames.setAdapter(adapter);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), ChooseAddress.class);
                startActivity(inte);
            }
        });

        lt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), AddPal.class);
                startActivity(inte);
            }
        });


        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                startActivity(inte);*/
                onBackPressed();
            }
        });


        // met_palnames.addTextChangedListener(palnamewatcher);


        met_palnames.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = met_palnames.getText().toString();

                adapter1 = new ChoosePal_Adapter(getApplicationContext(), name);
                lview.setAdapter(adapter1);
            }
        });



    }

       /* private final TextWatcher palnamewatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String name = met_palnames.getText().toString();

                adapter = new ChoosePal_Adapter(getApplicationContext(),name);
                lview.setAdapter(adapter);

            }

        };*/





}
