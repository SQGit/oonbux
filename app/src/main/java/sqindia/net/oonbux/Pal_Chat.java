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
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.Button;
import com.rey.material.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import android.view.LayoutInflater.Factory;


public class Pal_Chat extends Activity {

    public String asdf, server_data, str_get_message, str_session_id, str_pal_oonbux_id, str_snd_message, str_oonbux_id;
    public String ct_from_id, ct_to_id, ct_message, ct_time, ct_message_id,receiver;
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

    private List<ChatMessage> chatMessages;
    public String my_oonbux,pal_oonbux;

    com.rey.material.widget.LinearLayout lt_back, lt_setting;


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

            ct_to_id = sharedPreferences.getString("pal_oonbuxid", "");

            receiver = ct_from_id;


            get_data();


        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pal_chat);

        Pal_Chat.this.registerReceiver(this.appendChatScreenMsgReceiver, new IntentFilter("appendChatScreenMsg"));

        lt_back = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_back);
        lt_setting = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_settings);


        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Pal_Chat.this);
        str_session_id = sharedPreferences.getString("sessionid", "");
        str_pal_oonbux_id = sharedPreferences.getString("pal_oonbuxid", "");
        ct_to_id = sharedPreferences.getString("pal_oonbuxid", "");
        str_oonbux_id = sharedPreferences.getString("oonbuxid", "");


        receiver = sharedPreferences.getString("pal_oonbuxid","");

        chatMessages = new ArrayList<>();

        dbclass = new DbC(context);

        Intent getData = getIntent();

        str_get_message = getData.getStringExtra("get_Server");


        ct_from_id = getData.getStringExtra("chat_from");
        ct_message = getData.getStringExtra("chat_message");
        ct_time = getData.getStringExtra("chat_time");
        ct_message_id = getData.getStringExtra("chat_message_id");


        if(sharedPreferences.getString("pal_oonbuxid","").isEmpty()){

            receiver = ct_from_id;
            Log.e("tag","0_das"+receiver);
        }
        else{

            receiver = sharedPreferences.getString("pal_oonbuxid","");
            Log.e("tag","1_das"+receiver);
        }
       // receiver = ct_from_id;


        et_message = (EditText) findViewById(R.id.message);
        btn_send = (Button) findViewById(R.id.btn_send);
        txt = (TextView) findViewById(R.id.txt);

        lview = (ListView) findViewById(R.id.lview);


        get_data();

        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(getApplicationContext(), Pal_Chat_List.class);
                startActivity(inte);
                finish();
            }
        });


        lt_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // openOptionsMenu();



                PopupMenu popup = new PopupMenu(Pal_Chat.this, lt_setting);

                popup.getMenuInflater().inflate(R.menu.task_menus, popup.getMenu());


                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {


                            case R.id.delete: {
                                Log.e("tag","worked");
                                return true;
                            }


                            default: {
                                return true;
                            }


                        }


                    }
                });

                popup.show();






            }
        });


        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str_snd_message =et_message.getText().toString();
                ct_message = et_message.getText().toString();
               // ct_from_id = str_oonbux_id;
               // ct_to_id = str_pal_oonbux_id;

                if(sharedPreferences.getString("pal_oonbuxid","").isEmpty()){
                    str_pal_oonbux_id = ct_from_id;
                    receiver = ct_from_id;
                    Log.e("tag","0"+str_pal_oonbux_id+"\n"+ct_from_id);
                }
                else{

                    str_pal_oonbux_id = sharedPreferences.getString("pal_oonbuxid","");
                    Log.e("tag","1"+str_pal_oonbux_id+"\n"+ct_from_id);
                }


                if (et_message.getText().toString().trim().equals("")) {
                    Toast.makeText(Pal_Chat.this, "Please input some text...", Toast.LENGTH_SHORT).show();
                } else {
                    new Send_Message().execute();
                }
            }
        });


    }






    public void insert_data() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Pal_Chat.this);
        if(sharedPreferences.getString("pal_oonbuxid","").isEmpty()){
            receiver = ct_from_id;
            Log.e("tag", "1_receiver"+receiver);
        }
        else{
            receiver = sharedPreferences.getString("pal_oonbuxid","");
            Log.e("tag", "0_receiver"+receiver);
        }

        dbclass.chat_insert(sender, ct_from_id, ct_to_id, ct_message, ct_time, ct_message_id,receiver);
        get_data();

    }


    public void get_data() {
        Log.e("tag", "get_data");


        messageAdapter = new MsgAdminAdapter(Pal_Chat.this);

        Cursor c = dbclass.chat_getdata(ct_from_id, ct_to_id,receiver);
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

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.task_menus, menu);
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.task_menus, menu);
//        MenuItem pinMenuItem1 = menu.getItem(0).getSubMenu().getItem(0);
//        MenuItem pinMenuItem2 = menu.getItem(0).getSubMenu().getItem(1);
//
//       // applyFontToMenuItem(pinMenuItem1);
//        //applyFontToMenuItem(pinMenuItem2);
//        return true;


        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.task_menus, menu);
        return super.onCreateOptionsMenu(menu);






    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        switch (id) {
            case R.id.delete:
               Log.e("tag","worked");

        }

        return super.onOptionsItemSelected(item);
    }


    private void applyFontToMenuItem(MenuItem mi)
    {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/merriweather.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        //mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
      //  mi.setTitle(mNewTitle);
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

                    ct_message = jo.getString("message");
                    ct_message_id = jo.getString("message_id");
                    ct_time = jo.getString("sent_utc_time");
                    sender = 0;
                    insert_data();
                    et_message.setText("");

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
