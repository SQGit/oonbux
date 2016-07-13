package sqindia.net.oonbux;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.rey.material.widget.Button;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;



public class Pal_Chat extends Activity{

    public String asdf,server_data,str_get_message,str_session_id,str_pal_oonbux_id,str_snd_message,str_oonbux_id;
    EditText et_message;
    Button btn_send;
    TextView txt;
    ListView lview;
    SweetAlertDialog sweetDialog;
    DbC dbclass;
    Context context = this;
    public String ct_from_id,ct_to_id,ct_message,ct_time,ct_message_id;
    int sender;
    private SQLiteDatabase db;

    boolean isMine = true;
    private List<ChatMessage> chatMessages;
    private ArrayAdapter<ChatMessage> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pal_chat);


        Pal_Chat.this.registerReceiver(this.appendChatScreenMsgReceiver, new IntentFilter("appendChatScreenMsg"));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Pal_Chat.this);
        str_session_id = sharedPreferences.getString("sessionid","");
        str_pal_oonbux_id = sharedPreferences.getString("pal_oonbuxid","");
        str_oonbux_id = sharedPreferences.getString("oonbuxid","");

        Log.e("tag","bbss "+str_session_id+str_oonbux_id);


        dbclass = new DbC(context);
        createDatabase();
        Intent getData = getIntent();

        str_get_message = getData.getStringExtra("get_Server");
        Log.e("tag","bb1 "+str_get_message);



        ct_from_id = getData.getStringExtra("chat_from");
        ct_message = getData.getStringExtra("chat_message");
        ct_time = getData.getStringExtra("chat_time");
        ct_message_id = getData.getStringExtra("chat_message_id");

        if(!(ct_message == null)){
            //update_data(str_get_message);
            sender = 1;

            ct_to_id = sharedPreferences.getString("oonbuxid", "");
            insert_data();
        }



        et_message = (EditText) findViewById(R.id.message);
        btn_send = (Button) findViewById(R.id.btn_send);
        txt = (TextView) findViewById(R.id.txt);

        lview = (ListView) findViewById(R.id.lview);

        if(!(str_get_message == null)){
            update_data(str_get_message);
    }



       // chatMessages = new ArrayList<>();



        //set ListView adapter first
       // adapter = new MessageAdapter(this, R.layout.item_chat_left, chatMessages);
       // lview.setAdapter(adapter);



        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                str_snd_message = et_message.getText().toString();



                if (et_message.getText().toString().trim().equals("")) {
                    Toast.makeText(Pal_Chat.this, "Please input some text...", Toast.LENGTH_SHORT).show();
                }
                else {


                    new  Send_Message().execute();
                    //add message to list
                  /*  ChatMessage chatMessage = new ChatMessage(et_message.getText().toString(), isMine);
                    chatMessages.add(chatMessage);
                    adapter.notifyDataSetChanged();
                    et_message.setText("");
                    if (isMine) {
                        isMine = false;
                    } else {
                        isMine = true;
                    }*/

                }

             /*   ChatMessage chatMessage = new ChatMessage(et_message.getText().toString(),isMine);
                chatMessages.add(chatMessage);
                adapter.notifyDataSetChanged();

                if (isMine) {
                    isMine = false;
                } else {
                    isMine = true;
                }*/


            }
        });




    }

    private void insert_data() {

        Log.e("tag","chatvalues"+sender+"\t"+ct_from_id+"\t"+ct_to_id+"\t"+ct_message+"\t"+ct_time);

        dbclass.chat_insert(sender,ct_from_id,ct_to_id,ct_message,ct_time,ct_message_id);
    }


    @Override
    protected void onStop() {
        super.onStop();
        db.close();
    }


    BroadcastReceiver appendChatScreenMsgReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String data;
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Pal_Chat.this);

            Bundle b = intent.getExtras();

            data = b.getString("get_Server");

            Log.e("tag0",""+data);
           // data = b.toString();

            ct_from_id = b.getString("chat_from");
            ct_message = b.getString("chat_message");
            ct_time = b.getString("chat_time");



            Log.e("tag`",""+data + ct_from_id+ct_message+ct_time);

            str_get_message  = data;

            sender = 1;
            ct_from_id = sharedPreferences.getString("oonbuxid", "");
            ct_to_id = sharedPreferences.getString("pal_oonbuxid", "");
            insert_data();
            update_data(data);


        }
    };

    private void update_data(String data) {

        this.str_get_message = data;
        Log.d("tag",str_get_message);
        txt.setText(str_get_message);
    }




    protected void createDatabase() {

        Log.d("tag", "createdb");
        db = openOrCreateDatabase("oonbux", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS chat(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, sender VARCHAR, from_id VARCHAR, to_id VARCHAR, message VARCHAR, time VARCHAR, message_id VARCHAR );");

    }






    @Override
    protected void onDestroy() {
        super.onDestroy();
        Pal_Chat.this.unregisterReceiver(appendChatScreenMsgReceiver);
    }


    class Send_Message extends AsyncTask<String, Void, String> {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Pal_Chat.this);

        protected void onPreExecute() {
            super.onPreExecute();

            sweetDialog = new SweetAlertDialog(Pal_Chat.this, SweetAlertDialog.PROGRESS_TYPE);
            sweetDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            sweetDialog.setTitleText("Loading");
            sweetDialog.setCancelable(false);
            sweetDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";

            try {

                JSONObject jsonObject = new JSONObject();

                jsonObject.accumulate("pal_oonbux_id",str_pal_oonbux_id);
                jsonObject.accumulate("message",str_snd_message );



                // 4. convert JSONObject to JSON to String
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest2(Config.SER_URL + "onetoonechat", json, str_session_id);
            } catch (Exception e) {
                Log.e("InputStream", e.getLocalizedMessage());
            }

            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            Log.e("tag", "<-----rerseres---->" + s);
            super.onPostExecute(s);
            sweetDialog.dismiss();


            try {
                JSONObject jo = new JSONObject(s);

                String status = jo.getString("status");

                String msg = jo.getString("message");
                Log.d("tag", "<-----Status----->" + status);


                if (status.equals("success")) {

                    sender = 0;
                    ct_from_id = sharedPreferences.getString("oonbuxid", "");
                    ct_to_id = sharedPreferences.getString("pal_oonbuxid", "");
                    insert_data();
                  //  et_message.setText("");
                   // et_message.requestFocus();

                }
                else if (status.equals(null)) {
                    Toast.makeText(getApplicationContext(), "network not available", Toast.LENGTH_LONG).show();
                }
                else if (status.equals("fail")) {

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }






















}
