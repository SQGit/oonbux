package sqindia.net.oonbux;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

/**
 * Created by Salman on 4/27/2016.
 */
public class Dialog_Msg extends Dialog {


    public Dialog_Msg(Activity activity) {
        super(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_message);
    }
}
