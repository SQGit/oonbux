package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.rey.material.widget.Button;

/**
 * Created by Salman on 4/22/2016.
 */
public class AddLocation extends Activity{

    com.rey.material.widget.LinearLayout lt_back;
    Button btn_submit;
    GridView grid1,grid2;
    Intent intent = getIntent();
    int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addloction);


        status = intent.getIntExtra("sts", -1);

        lt_back = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_back);
        btn_submit = (Button) findViewById(R.id.button_submit);


        grid1 = (GridView) findViewById(R.id.grd_usa);
        grid2 = (GridView) findViewById(R.id.grd_nig);


        Gridview_adapter adapter = new Gridview_adapter(getApplicationContext());

        grid1.setAdapter(adapter);
        grid2.setAdapter(adapter);


        if (status == 0) {
            btn_submit.setText("Next");
        } else {
            btn_submit.setText("Submit");
        }





        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                    startActivity(inte);

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (status == 0) {
                    btn_submit.setText("Next");
                    Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                    startActivity(inte);
                } else {
                    btn_submit.setText("Submit");
                    Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                    startActivity(inte);
                }

            }
        });


    }
}
