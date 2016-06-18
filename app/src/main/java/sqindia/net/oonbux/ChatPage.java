package sqindia.net.oonbux;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Salman on 6/16/2016.
 */
public class ChatPage extends Activity {
    TextView txt;
    String txts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatpage);


        txts = getIntent().getStringExtra("get_Server");

        Log.d("tag","server:  " + txts);
        txt = (TextView) findViewById(R.id.txt_1);

        txt.setText("server Message: "+ txts);

    }
}
