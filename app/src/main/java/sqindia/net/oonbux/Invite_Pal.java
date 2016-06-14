package sqindia.net.oonbux;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;

import com.rey.material.widget.ListView;
import com.rey.material.widget.TextView;

/**
 * Created by Salman on 4/26/2016.
 */
public class Invite_Pal extends Activity {

    ListView lv_deliver_list;
    TextView header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invitepal);

        lv_deliver_list = (ListView) findViewById(R.id.lview);
        header = (TextView) findViewById(R.id.tv_hd_txt);

        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");

        header.setTypeface(tf1);

        Invite_Pal_Adapter adapter = new Invite_Pal_Adapter(getApplicationContext());
        lv_deliver_list.setAdapter(adapter);
















    }
}
