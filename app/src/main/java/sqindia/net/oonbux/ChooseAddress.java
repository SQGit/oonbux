package sqindia.net.oonbux;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rey.material.widget.Button;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Salman on 5/18/2016.
 */
public class ChooseAddress extends Activity {

    com.rey.material.widget.LinearLayout lt_back;
    Button btn_payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooseaddress);

        lt_back = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_back);

        btn_payment = (Button) findViewById(R.id.button_payment);


        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent inte = new Intent(getApplicationContext(), DashBoardActivity.class);
                startActivity(inte);*/
                onBackPressed();
            }
        });

        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new SweetAlertDialog(ChooseAddress.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Complete Payment?")
                        .setConfirmText("Yes!")
                        .setCancelText("No")

                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismiss();
                                new SweetAlertDialog(ChooseAddress.this, SweetAlertDialog.SUCCESS_TYPE)
                                        .setTitleText("Your shipment completed successfully")
                                        .setContentText("Shipment id: 25445XCO44\n Oonbux id: DFD343DS32")
                                        .setConfirmText("Ok")

                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {

                                                Intent newe = new Intent(getApplicationContext(), DashBoardActivity.class);
                                                startActivity(newe);
                                                sDialog.dismiss();

                                            }
                                        })
                                        .show();

                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();

                            }
                        })
                        .show();





            }
        });

    }
}
