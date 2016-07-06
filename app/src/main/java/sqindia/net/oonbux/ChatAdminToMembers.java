package sqindia.net.oonbux;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.LogRecord;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Ramya on 13-05-2016.
 */
public class ChatAdminToMembers extends AppCompatActivity {
    public static String URL_GET_HEADID = "http://sqindia01.cloudapp.net/clubhouse/api/v1/chat/getChatHeadId";
    public static String URL_MESSAGE = "http://sqindia01.cloudapp.net/clubhouse/api/v1/chat/sendMessage";
    public static String URL_CHAT_MESSAGE = "http://sqindia01.cloudapp.net/clubhouse/api/v1/chat/getChatMessages";
    public static String URL_GET_CHATS_NEW = "http://sqindia01.cloudapp.net/clubhouse/api/v1/chat/getNewMessage";
    View v;
    ArrayList<HashMap<String, String>> contactList;
    String msg, token, userId, chatId, user_Name;
    private ListView listView;
    private View btnSend;
    private EditText editText;
    private List<ChatMessage> chatMessages;
    ArrayList<ChatMessage> lv1 = new ArrayList<ChatMessage>();
    ArrayList<ChatMessage> lv2= new ArrayList<ChatMessage>();
    Typeface tf;
    static TextView TextViewNewFont;
    MsgAdminAdapter messageAdapter;
    Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forums_chat);
        tf = Typeface.createFromAsset(getAssets(), "fonts/ubundu.ttf");
   mHandler = new Handler();
        listView = (ListView) findViewById(R.id.list_msg);
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        token = sharedPreferences.getString("token", "");
        userId = sharedPreferences.getString("id", "");
        Log.e("tag","<---userId---->"+userId);

        user_Name = sharedPreferences.getString("user_Name", "");
        new GetAllCitiesAsync().execute();
        chatMessages = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list_msg);
        btnSend = findViewById(R.id.btn_chat_send);
        editText = (EditText) findViewById(R.id.msg_type);
        editText.setTypeface(tf);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        contactList = new ArrayList<>();
        mHandler.postDelayed(new Runnable() {
            public void run() {
                new GetNewChatsAsync(chatId).execute();
                Log.e("tag","time dalay");
                mHandler.postDelayed(this,3000); //now is every 2 minutes
            }
        }, 3000);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msg = editText.getText().toString().trim();
                if (editText.getText().toString().trim().equals("")) {
                    Toast.makeText(ChatAdminToMembers.this, "Please input some text...", Toast.LENGTH_SHORT).show();
                } else
                {
                    new MessageAsync().execute();
                    messageAdapter.notifyDataSetChanged();
                    editText.setText("");

                }

            }
        });


    }

    class GetAllCitiesAsync extends AsyncTask<String, Void, String> {

        public GetAllCitiesAsync() {
            String json = "", jsonStr = "";}
        protected void onPreExecute() {
            super.onPreExecute();
        }
        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("userId", "2");
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest1(URL_GET_HEADID, json, token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.e("tag", "<-----chat---->" + jsonStr);
            super.onPostExecute(jsonStr);

            try {
                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");

                if (status.equals("SUCCESS")) {
                    JSONObject data = jo.getJSONObject("data");
                    Log.e("tag", "<-----data----->" + data.length());
                    chatId = data.getString("chatHeaderId");
                    Log.e("tag", "chatHeaderIddd" + chatId);
                    new GetAllChatsAsync(chatId).execute();


                } else {
                    new SweetAlertDialog(ChatAdminToMembers.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("MESSAGE!!!")
                            .setContentText(msg)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            })
                            .show();

                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }


    class MessageAsync extends AsyncTask<String, Void, String> {

        String bookingId;

        public MessageAsync() {
            String json = "", jsonStr = "";
        }

        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("to", "2");
                jsonObject.accumulate("message", msg);
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest1(URL_MESSAGE, json, token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.e("tag", "<-----resulttt---->" + jsonStr);
            super.onPostExecute(jsonStr);


            try {
                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");

                if (status.equals("SUCCESS")) {
                   lv1.clear();
                    new GetAllChatsAsync(chatId).execute();

                } else {
                    new SweetAlertDialog(ChatAdminToMembers.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("MESSAGE!!!")
                            .setContentText(msg)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                }
                            })
                            .show();
                }



            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    class GetAllChatsAsync extends AsyncTask<String, Void, String> {

        String chatId;

        public GetAllChatsAsync(String chatId) {
            this.chatId = chatId;

            String json = "", jsonStr = "";
        }

        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";
            try {
                JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("chatHeadId", chatId);
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest1(URL_CHAT_MESSAGE, json, token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.e("tag", "<-----resulttt---->" + jsonStr);
            super.onPostExecute(jsonStr);
            messageAdapter = new MsgAdminAdapter(ChatAdminToMembers.this);

            try {
                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");

                if (status.equals("SUCCESS")) {
                    JSONArray data = jo.getJSONArray("data");
                    Log.e("tag", "<-----data----->" + data.length());
                    if (data.length() > 0) {
                        for(int i1 = data.length() - 1; i1 >= 0; i1--){
                            JSONObject joo = data.getJSONObject(i1);
                            String s = joo.getString("messageBy");
                            if (s.equals("Other")) {
                                ChatMessage chatMessage = new ChatMessage(joo.getString("message"), s);
                                chatMessages.add(chatMessage);
                                chatMessage.setMessage(joo.getString("message"));
                                chatMessage.setMessegeBy(joo.getString("messageBy"));
                                // chatMessage.setName(joo.getString("userName"));
                                //chatMessage.setUserImage(joo.getString("userImage"));
                                lv1.add(chatMessage);
                                messageAdapter.addMessage(chatMessage, MsgAdminAdapter.DIRECTION_OUTGOING);
                            } else {
                                ChatMessage chatMessage = new ChatMessage(joo.getString("message"), s);
                                chatMessages.add(chatMessage);
                                chatMessage.setMessage(joo.getString("message"));
                                chatMessage.setMessegeBy(joo.getString("messageBy"));
                                //chatMessage.setName(joo.getString("userName"));
                                // chatMessage.setUserImage(joo.getString("userImage"));
                                lv1.add(chatMessage);
                                messageAdapter.addMessage(chatMessage, MsgAdminAdapter.DIRECTION_INCOMING);
                            }
                        }
                        listView.setAdapter(messageAdapter);

                    } else {
                        new SweetAlertDialog(ChatAdminToMembers.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("MESSAGE!!!")
                                .setContentText(msg)
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();
                                    }
                                })
                                .show();

                    }

                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    class GetNewChatsAsync extends AsyncTask<String, Void, String> {

        String chatId;

        public GetNewChatsAsync(String chatId) {
            this.chatId = chatId;

            String json = "", jsonStr = "";
        }

        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... params) {

            String json = "", jsonStr = "";
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("chatHeadId", chatId);
                json = jsonObject.toString();
                return jsonStr = HttpUtils.makeRequest1(URL_GET_CHATS_NEW, json, token);
            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());
            }
            return null;

        }

        @Override
        protected void onPostExecute(String jsonStr) {
            Log.e("tag", "<-----new message---->" + jsonStr);

            Log.e("tag", "<-----listview111111---->" + lv1);
            super.onPostExecute(jsonStr);
            //messageAdapter = new MsgAdminAdapter(ChatAdminToMembers.this);

            try {
                JSONObject jo = new JSONObject(jsonStr);
                String status = jo.getString("status");
                String msg = jo.getString("message");

                if (status.equals("SUCCESS")) {
                    JSONArray data = jo.getJSONArray("data");
                    Log.e("tag", "<-----data----->" + data.length());
                    if (data.length() > 0) {
                        for(int i1 = data.length() - 1; i1 >= 0; i1--){
                            JSONObject joo = data.getJSONObject(i1);
                            String s = joo.getString("messageBy");
                            if (s.equals("Other")) {
                                ChatMessage chatMessage = new ChatMessage(joo.getString("message"), s);
                                chatMessages.add(chatMessage);
                                chatMessage.setMessage(joo.getString("message"));
                                chatMessage.setMessegeBy(joo.getString("messageBy"));
                                lv2.addAll(lv1);
                                lv2.add(chatMessage);

                                messageAdapter.addMessage(chatMessage, MsgAdminAdapter.DIRECTION_OUTGOING);
                            } else {
                                ChatMessage chatMessage = new ChatMessage(joo.getString("message"), s);
                                chatMessages.add(chatMessage);
                                chatMessage.setMessage(joo.getString("message"));
                                chatMessage.setMessegeBy(joo.getString("messageBy"));
                                lv2.addAll(lv1);
                                lv2.add(chatMessage);
                                messageAdapter.addMessage(chatMessage, MsgAdminAdapter.DIRECTION_INCOMING);

                            }
                        }
                        listView.setAdapter(messageAdapter);
                     messageAdapter.notifyDataSetChanged();


                    }
                    else
                    {

                    }

                }


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

}
