package sqindia.net.oonbux;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Salman on 7/1/2016.
 */
public class Pal_Chat extends Activity{

    public String asdf,server_data,str_get_message,str_session_id,str_oonbux_id,str_snd_message;
    EditText et_message;
    Button btn_send;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pal_chat);

        Pal_Chat.this.registerReceiver(this.appendChatScreenMsgReceiver, new IntentFilter("appendChatScreenMsg"));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Pal_Chat.this);
        str_session_id = sharedPreferences.getString("sessionid", "");
        str_oonbux_id = sharedPreferences.getString("oonbuxid", "");

        Log.e("tag","bbss "+str_session_id+str_oonbux_id);

        Intent getData = getIntent();

        str_get_message = getData.getStringExtra("get_Server");
        Log.e("tag","bb1 "+str_get_message);

        et_message = (EditText) findViewById(R.id.message);
        btn_send = (Button) findViewById(R.id.btn_send);
        txt = (TextView) findViewById(R.id.txt);

        if(!(str_get_message == null)){
            txt.setText(str_get_message);
        }


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                str_snd_message = et_message.getText().toString();
                new  Send_Message().execute();
            }
        });




    }




    BroadcastReceiver appendChatScreenMsgReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String data;

            Bundle b = intent.getExtras();

            data = b.getString("get_Server");

            Log.e("tag0",""+data);

            data = b.toString();

            Log.e("tag`",""+data);

            str_get_message  = data;




        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Pal_Chat.this.unregisterReceiver(appendChatScreenMsgReceiver);
    }








    class Send_Message extends AsyncTask<String, Void, String> {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Pal_Chat.this);

        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";

            try {

                JSONObject jsonObject = new JSONObject();

                jsonObject.accumulate("pal_oonbux_id",str_oonbux_id);
                jsonObject.accumulate("message",str_snd_message );



                // 4. convert JSONObject to JSON to String
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest2(Config.SER_URL + "profileupdate", json, str_session_id);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("tag", "<-----rerseres---->" + s);
            super.onPostExecute(s);



            try {
                JSONObject jo = new JSONObject(s);

                String status = jo.getString("status");

                String msg = jo.getString("message");
                Log.d("tag", "<-----Status----->" + status);


                if (status.equals("success")) {




                } else if (status.equals(null)) {
                    Toast.makeText(getApplicationContext(), "network not available", Toast.LENGTH_LONG).show();
                } else if (status.equals("fail")) {



                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }






















}
