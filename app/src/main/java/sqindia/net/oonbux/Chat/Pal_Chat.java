package sqindia.net.oonbux.Chat;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.ImageButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sqindia.net.oonbux.R;
import sqindia.net.oonbux.config.Config;
import sqindia.net.oonbux.config.DbC;
import sqindia.net.oonbux.config.HttpUtils;


public class Pal_Chat extends Activity {

    public String asdf, server_data, str_get_message, str_session_id, str_pal_oonbux_id, str_snd_message, str_oonbux_id;
    public String ct_from_id, ct_to_id, ct_message, ct_time, ct_message_id, receiver, pal_photo;
    public int id;
    public String my_oonbux, pal_oonbux;
    EditText et_message;
    // Button btn_send;
    TextView txt;
    ListView lview;
    DbC dbclass;
    Context context = this;
    int sender;
    boolean isMine = true;
    MsgAdminAdapter messageAdapter;
    ArrayList<String> send_msg = new ArrayList<>();
    ArrayList<String> get_msg = new ArrayList<>();
    ArrayList<ChatMessage> chat_list = new ArrayList<ChatMessage>();
    ArrayList<String> chat_msg_id;
    com.rey.material.widget.TextView tv_header;
    HashMap<String, String> map;
    ArrayList<HashMap<String, String>> pal_datas;
    ImageButton btn_send;
    com.rey.material.widget.LinearLayout lt_back, lt_setting;
    SharedPreferences sharedPreferences;
    ChatMessage chatMessage;
    String str_sessionid, my_image, user_image;
    String photo_path;
    ProgressBar bar;
    private SQLiteDatabase db;
    private List<ChatMessage> chatMessages;
    BroadcastReceiver appendChatScreenMsgReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            String data;
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Pal_Chat.this);

            Bundle b = intent.getExtras();

            data = b.getString("get_Server");

            Log.e("tag0", "" + data);


            ct_from_id = b.getString("chat_from");
            ct_message = b.getString("chat_message");
            ct_time = b.getString("chat_time");
            ct_message_id = b.getString("message_id");
            pal_photo = b.getString("pal_photo");

            Log.e("tag`", "" + data + ct_from_id + ct_message + ct_time);
            str_get_message = data;

            sender = 1;

            ct_to_id = sharedPreferences.getString("pal_oonbuxid", "");
            photo_path = sharedPreferences.getString("photo_path", "");

            receiver = ct_from_id;

            get_img_path();

            get_data();


        }
    };

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pal_chat);

        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/prox.otf");

        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/nexa.otf");

        Pal_Chat.this.registerReceiver(this.appendChatScreenMsgReceiver, new IntentFilter("appendChatScreenMsg"));

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        str_sessionid = sharedPreferences.getString("sessionid", "");

        photo_path = sharedPreferences.getString("photo_path", "");


        lt_back = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_back);
        lt_setting = (com.rey.material.widget.LinearLayout) findViewById(R.id.layout_settings);

        tv_header = (com.rey.material.widget.TextView) findViewById(R.id.tv_hd_txt);

        et_message = (EditText) findViewById(R.id.message);
        btn_send = (ImageButton) findViewById(R.id.btn_send);
        // txt = (TextView) findViewById(R.id.txt);

        lview = (ListView) findViewById(R.id.lview);
        bar = (ProgressBar) findViewById(R.id.progress);

        bar.setVisibility(View.GONE);


        //bar.setProgressDrawable(getResources().getDrawable(R.color.white));

        tv_header.setTypeface(tf1);
        et_message.setTypeface(tf);
        //btn_send.setTypeface(tf);


        // final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Pal_Chat.this);
        str_session_id = sharedPreferences.getString("sessionid", "");
        str_pal_oonbux_id = sharedPreferences.getString("pal_oonbuxid", "");
        ct_to_id = sharedPreferences.getString("pal_oonbuxid", "");
        str_oonbux_id = sharedPreferences.getString("oonbuxid", "");
        createDatabase();

        receiver = sharedPreferences.getString("pal_oonbuxid", "");

        chatMessages = new ArrayList<>();

        dbclass = new DbC(context);

        final Intent getData = getIntent();

        str_get_message = getData.getStringExtra("get_Server");


        ct_from_id = getData.getStringExtra("chat_from");
        ct_message = getData.getStringExtra("chat_message");
        ct_time = getData.getStringExtra("chat_time");
        ct_message_id = getData.getStringExtra("message_id");
        pal_photo = getData.getStringExtra("pal_photo");


       /* if(sharedPreferences.getString("pal_oonbuxid","").isEmpty()){

            receiver = ct_from_id;
            Log.e("tag","0_das"+receiver);
        }
        else{

            receiver = sharedPreferences.getString("pal_oonbuxid","");
            Log.e("tag","1_das"+receiver);
        }*/


        if (!(ct_from_id == null)) {

            receiver = ct_from_id;
            Log.e("tag", "0_das" + receiver);
        } else {

            receiver = sharedPreferences.getString("pal_oonbuxid", "");
            Log.e("tag", "1_das" + receiver);
        }
        // receiver = ct_from_id;


        lview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("tag", "msg_id_click " + chat_msg_id.get(position));

                dbclass.chat_delete(chat_msg_id.get(position));
                get_data();
                return true;
            }
        });


    /*    lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               // final ChatMessage message = chatMessage;
              //  Log.e("tag",chat_msg_id.get(position)+""+position +"\t");
                //Log.e("tag",""+chatMessage.getMessage_id());

                Log.e("tag","msg_id_click "+ chat_msg_id.get(position));

                dbclass.chat_delete(chat_msg_id.get(position));
               get_data();

            }
        });*/


        get_data();

        lt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent inte = new Intent(getApplicationContext(), Pal_Chat_List.class);
                startActivity(inte);
                finish();*/
                onBackPressed();
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
                                Log.e("tag", "worked");
                                dbclass.del_table1();
                                createDatabase();
                                get_data();
                                messageAdapter.notifyDataSetChanged();
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

                str_snd_message = et_message.getText().toString();
                ct_message = et_message.getText().toString();
                // ct_from_id = str_oonbux_id;
                // ct_to_id = str_pal_oonbux_id;

            /*    if(sharedPreferences.getString("pal_oonbuxid","").isEmpty()){
                    str_pal_oonbux_id = ct_from_id;
                    receiver = ct_from_id;
                    Log.e("tag","0"+str_pal_oonbux_id+"\n"+ct_from_id);
                }
                else{

                    str_pal_oonbux_id = sharedPreferences.getString("pal_oonbuxid","");
                    Log.e("tag","1"+str_pal_oonbux_id+"\n"+ct_from_id);
                }*/


                if (!(ct_from_id == null)) {

                    str_pal_oonbux_id = ct_from_id;
                    Log.e("tag", "0_das" + receiver);
                } else {

                    str_pal_oonbux_id = sharedPreferences.getString("pal_oonbuxid", "");
                    Log.e("tag", "1_das" + receiver);
                }


                if (et_message.getText().toString().trim().equals("")) {
                    Toast.makeText(Pal_Chat.this, "Please input some text...", Toast.LENGTH_SHORT).show();
                } else {


                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.RESULT_HIDDEN);

                    new Send_Message().execute();
                }
            }
        });


        setupUI(findViewById(R.id.top));

    }

    @Override
    protected void onStop() {
        super.onStop();
        db.close();
    }

    protected void createDatabase() {

        Log.d("tag", "createdb");
        db = openOrCreateDatabase("oonbux", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS chat(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, sender VARCHAR, from_id VARCHAR, to_id VARCHAR, message VARCHAR, time VARCHAR, message_id VARCHAR, receiver VARCHAR );");

    }

    public void insert_data() {

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Pal_Chat.this);
        if (sharedPreferences.getString("pal_oonbuxid", "").isEmpty()) {
            receiver = ct_from_id;
            Log.e("tag", "1_receiver" + receiver);
        } else {
            receiver = sharedPreferences.getString("pal_oonbuxid", "");
            Log.e("tag", "0_receiver" + receiver);
        }

        dbclass.chat_insert(sender, ct_from_id, ct_to_id, ct_message, ct_time, ct_message_id, receiver);
        get_data();

    }

    public void get_data() {
        Log.e("tag", "get_data");

        my_image = sharedPreferences.getString("photo_path", "");

        messageAdapter = new MsgAdminAdapter(Pal_Chat.this);

        Cursor c = dbclass.chat_getdata(ct_from_id, ct_to_id, receiver);
        send_msg.clear();
        get_msg.clear();

        chat_msg_id = new ArrayList<>();
        chat_msg_id.clear();

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    id = Integer.valueOf(c.getString(c.getColumnIndex("sender")));

                    String message = String.valueOf(c.getString(c.getColumnIndex("message")));

                    //chat_msg_id.add(c.getString(c.getColumnIndex("message_id")));

                    String msg_id = String.valueOf(c.getString(c.getColumnIndex("message_id")));


                    if (id == 0) {

                        Log.e("tag", "msg_id_send " + msg_id);
                        chat_msg_id.add(msg_id);

                        send_msg.add(message);
                        // Log.e("tag", "send" + send_msg.size()+"\t"+ct_message_id);
                        chatMessage = new ChatMessage(message, 0, ct_message_id);
                        chatMessages.add(chatMessage);
                        chatMessage.setMessage(message);
                        chatMessage.setMessage_id(msg_id);
                        chatMessage.setMessegeBy("me");
                        chat_list.add(chatMessage);
                        messageAdapter.addMessage(chatMessage, MsgAdminAdapter.DIRECTION_OUTGOING, msg_id, photo_path, pal_photo);

                    } else {

                        Log.e("tag", "msg_id_get " + msg_id);
                        chat_msg_id.add(msg_id);

                        get_msg.add(message);
                        chatMessage = new ChatMessage(message, 1, ct_message_id);
                        chatMessages.add(chatMessage);
                        chatMessage.setMessage(message);
                        chatMessage.setMessage_id(msg_id);
                        chatMessage.setMessegeBy("user");
                        chat_list.add(chatMessage);
                        messageAdapter.addMessage(chatMessage, MsgAdminAdapter.DIRECTION_INCOMING, msg_id, photo_path, pal_photo);
                        //  Log.e("tag", "get" + get_msg.size()+"\t"+ct_message_id);
                    }
                } while (c.moveToNext());
            }
        }

        lview = (ListView) findViewById(R.id.lview);

        lview.setAdapter(messageAdapter);
        lview.setSelection(lview.getAdapter().getCount() - 1);

    }

    public void setupUI(View view) {

        if (!(view instanceof com.rey.material.widget.EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(Pal_Chat.this);
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.task_menus, menu);
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menru.task_menus, menu);
//        MenuItem pinMenuItem1 = menu.getItem(0).getSubMenu().getItem(0);
//        MenuItem pinMenuItem2 = menu.getItem(0).getSubMenu().getItem(1);
//
//       // applyFontToMenuItem(pinMenuItem1);
//        //applyFontToMenuItem(pinMenuItem2);
//        return true;


        MenuInflater inflater = getMenuInflater();
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
                Log.e("tag", "worked");

        }

        return super.onOptionsItemSelected(item);
    }


    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/merriweather.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        //mNewTitle.setSpan(new CustomTypefaceSpan("" , font), 0 , mNewTitle.length(),  Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        //  mi.setTitle(mNewTitle);
    }


    private void get_img_path() {


       /* new getPalLists().execute();

        for(int i =0 ;i<pal_datas.size();i++){
            HashMap<String, String> result = new HashMap<String, String>();

            result = pal_datas.get(i);
            if(result.get("oonbux_id") == receiver){
                Log.e("tag",""+result.get("photourl"));
            }
        }*/


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

            btn_send.setEnabled(false);

            //bar.setVisibility(View.VISIBLE);


            /*sweetDialog = new SweetAlertDialog(Pal_Chat.this, SweetAlertDialog.PROGRESS_TYPE);
            sweetDialog.getProgressHelper().setBarColor(Color.parseColor("#FFE64A19"));
            sweetDialog.setTitleText("Loading");
            sweetDialog.setCancelable(false);
            sweetDialog.show();*/

        }

        @Override
        protected String doInBackground(String... params) {
            String json = "", jsonStr = "";

            try {

                JSONObject jsonObject = new JSONObject();
                str_snd_message = str_snd_message.trim();
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
          //  sweetDialog.dismiss();
            btn_send.setEnabled(true);
            et_message.setText("");

            //  bar.setVisibility(View.GONE);

            /*getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            if(imm != null){
                imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }*/

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
