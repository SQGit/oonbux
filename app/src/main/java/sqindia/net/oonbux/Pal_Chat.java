package sqindia.net.oonbux;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
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


public class Pal_Chat extends Activity {

    public String asdf, server_data, str_get_message, str_session_id, str_pal_oonbux_id, str_snd_message, str_oonbux_id;
    public String ct_from_id, ct_to_id, ct_message, ct_time, ct_message_id;
    EditText et_message;
    Button btn_send;
    TextView txt;
    ListView lview;
    SweetAlertDialog sweetDialog;
    DbC dbclass;
    Context context = this;
    int sender;
    boolean isMine = true;

    MsgAdminAdapter messageAdapter;

    ArrayList<String> send_msg = new ArrayList<>();
    ArrayList<String> get_msg = new ArrayList<>();

    ArrayList<ChatMessage> chat_list = new ArrayList<ChatMessage>();
    ArrayList<ChatMessage> lv2= new ArrayList<ChatMessage>();

    public int id;



    BroadcastReceiver appendChatScreenMsgReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String data;
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Pal_Chat.this);

            Bundle b = intent.getExtras();

            data = b.getString("get_Server");

            Log.e("tag0", "" + data);


            ct_from_id = b.getString("chat_from");
            ct_message = b.getString("chat_message");
            ct_time = b.getString("chat_time");


            Log.e("tag`", "" + data + ct_from_id + ct_message + ct_time);

            str_get_message = data;


            sender = 1;
           // ct_from_id = sharedPreferences.getString("oonbuxid", "");
           // ct_to_id = sharedPreferences.getString("pal_oonbuxid", "");

           // update_data(data);


            ct_to_id = sharedPreferences.getString("pal_oonbuxid", "");

            get_data();


        }
    };
    private SQLiteDatabase db;
    private List<ChatMessage> chatMessages;
    private ArrayAdapter<ChatMessage> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pal_chat);


        Pal_Chat.this.registerReceiver(this.appendChatScreenMsgReceiver, new IntentFilter("appendChatScreenMsg"));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Pal_Chat.this);
        str_session_id = sharedPreferences.getString("sessionid", "");
        str_pal_oonbux_id = sharedPreferences.getString("pal_oonbuxid", "");
        ct_to_id = sharedPreferences.getString("pal_oonbuxid", "");
        str_oonbux_id = sharedPreferences.getString("oonbuxid", "");
        ct_from_id = sharedPreferences.getString("oonbuxid", "");


        chatMessages = new ArrayList<>();

        dbclass = new DbC(context);
        createDatabase();
        Intent getData = getIntent();

        str_get_message = getData.getStringExtra("get_Server");


        ct_from_id = getData.getStringExtra("chat_from");
        ct_message = getData.getStringExtra("chat_message");
        ct_time = getData.getStringExtra("chat_time");
        ct_message_id = getData.getStringExtra("chat_message_id");

        if (!(ct_message == null)) {

            sender = 1;

            ct_to_id = sharedPreferences.getString("oonbuxid", "");
            insert_data();
            update_data(str_get_message);
        }


        et_message = (EditText) findViewById(R.id.message);
        btn_send = (Button) findViewById(R.id.btn_send);
        txt = (TextView) findViewById(R.id.txt);

        lview = (ListView) findViewById(R.id.lview);


        get_data();



        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_snd_message =et_message.getText().toString();
                ct_message = et_message.getText().toString();
                ct_from_id = str_oonbux_id;
                ct_to_id = str_pal_oonbux_id;
                if (et_message.getText().toString().trim().equals("")) {
                    Toast.makeText(Pal_Chat.this, "Please input some text...", Toast.LENGTH_SHORT).show();
                } else {
                    new Send_Message().execute();
                }
            }
        });


    }

    public void insert_data() {
        dbclass.chat_insert(sender, ct_from_id, ct_to_id, ct_message, ct_time, ct_message_id);
        get_data();
    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
    }

    public void get_data() {
        Log.e("tag", "get_data");

        messageAdapter = new MsgAdminAdapter(Pal_Chat.this);

        Cursor c = dbclass.chat_getdata(ct_from_id, ct_to_id);
        send_msg.clear();
        get_msg.clear();

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    id = Integer.valueOf(c.getString(c.getColumnIndex("sender")));

                    String message = String.valueOf(c.getString(c.getColumnIndex("message")));


                    if (id == 0) {
                        send_msg.add(message);
                        Log.e("tag", "send" + send_msg.size());
                        ChatMessage chatMessage = new ChatMessage(message, 0);
                        chatMessages.add(chatMessage);
                        chatMessage.setMessage(message);
                        chatMessage.setMessegeBy("me");
                        chat_list.add(chatMessage);
                        messageAdapter.addMessage(chatMessage, MsgAdminAdapter.DIRECTION_OUTGOING);

                    }
                    else {
                        get_msg.add(message);

                        ChatMessage chatMessage = new ChatMessage(message, 1);
                        chatMessages.add(chatMessage);
                        chatMessage.setMessage(message);
                        chatMessage.setMessegeBy("user");
                        chat_list.add(chatMessage);
                        messageAdapter.addMessage(chatMessage, MsgAdminAdapter.DIRECTION_INCOMING);
                        Log.e("tag", "get" + get_msg.size());
                    }
                } while (c.moveToNext());
            }
        }

        lview = (ListView) findViewById(R.id.lview);
        lview.setAdapter(messageAdapter);


      //  getmessagedata();

    }


    public void getmessagedata(){
        messageAdapter = new MsgAdminAdapter(Pal_Chat.this);

        if(send_msg.size()>0){


        for(int i = 0 ;i< send_msg.size();i++){
            Log.e("tag","values"+send_msg.get(i));

                ChatMessage chatMessage = new ChatMessage(send_msg.get(i), 0);
                chatMessages.add(chatMessage);
                chatMessage.setMessage(send_msg.get(i));
                chatMessage.setMessegeBy("me");
                chat_list.add(chatMessage);
                messageAdapter.addMessage(chatMessage, MsgAdminAdapter.DIRECTION_OUTGOING);

        }
        }

        if(get_msg.size()>0) {
            for (int i = 0; i < get_msg.size(); i++) {
                Log.e("tag",""+get_msg.get(i));

                    ChatMessage chatMessage = new ChatMessage(get_msg.get(i), 1);
                    chatMessages.add(chatMessage);
                    chatMessage.setMessage(get_msg.get(i));
                    chatMessage.setMessegeBy("user");
                    chat_list.add(chatMessage);
                    messageAdapter.addMessage(chatMessage, MsgAdminAdapter.DIRECTION_INCOMING);

            }
        }



        lview.setAdapter(messageAdapter);
//        messageAdapter.notifyDataSetChanged();



    }


    private void update_data(String data) {

        this.str_get_message = data;
        Log.d("tag", str_get_message);
       // txt.setText(str_get_message);

        insert_data();
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

                jsonObject.accumulate("pal_oonbux_id", str_pal_oonbux_id);
                jsonObject.accumulate("message", str_snd_message);


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
                    //ct_from_id = sharedPreferences.getString("oonbuxid", "");
                   // ct_to_id = sharedPreferences.getString("pal_oonbuxid", "");
                    insert_data();
                    et_message.setText("");
                    //  et_message.setText("");
                    // et_message.requestFocus();

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
