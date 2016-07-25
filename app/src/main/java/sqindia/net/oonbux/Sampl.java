package sqindia.net.oonbux;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by Salman on 7/23/2016.
 */
public class Sampl extends Activity{
    int i =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample1);


        final LinearLayout ll= (LinearLayout) findViewById(R.id.linear);
        ll.setOrientation(LinearLayout.VERTICAL);

        Button add_btn= (Button) findViewById(R.id.button);
        add_btn.setText("Click to add TextViiews and EditTexts");
        ll.addView(add_btn);

        add_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                i++;
                TextView tv= (TextView) findViewById(R.id.tv_vinno);
                tv.setText("Number"+i);
                ll.addView(tv);




            }
        });
        //this.setContentView(scrl);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_menus, menu);
        return true;
    }

}
