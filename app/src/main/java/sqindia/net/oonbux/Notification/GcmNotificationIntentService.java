package sqindia.net.oonbux.Notification;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.google.android.gms.gcm.GcmReceiver;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import sqindia.net.oonbux.Chat.Pal_Chat;
import sqindia.net.oonbux.Pal.PalAccept;
import sqindia.net.oonbux.R;
import sqindia.net.oonbux.config.DbC;

/**
 * Created by Salman on 6/16/2016.
 */
public class GcmNotificationIntentService extends IntentService {
    // Sets an ID for the notification, so it can be updated
    public static final int notifyID = 9001;
    public static final String TAG = "GCMNotificationIntentService";
    public String ct_from_id, ct_to_id, ct_message, ct_time, ct_message_id, receiver;
    NotificationCompat.Builder builder;
    String notifi_body, notifi_title, gcm_data, from;
    GcmListenerService gcmget;
    String sts, message, simage, str_oonbux_id,pal_photo;
    DbC dbclass;
    Context context = this;







    public GcmNotificationIntentService() {
        super("GcmIntentService");
    }

    public static boolean isAppIsInBackground(Context context) {

        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);

     /*  if (gcm_data!= null) {
           sendNotification("Hi your message is: " + gcm_data); //When Message is received normally from GCM Cloud Server
       }
        else{
           sendNotification("");
       }*/
        //Log.d("tag","keys"+ asdf1 +"__ \t"+ asdf2+"_\t"+asdf3);



     /*   Log.d("tag","gcm0"+messageType);
        Toast.makeText(getApplicationContext(),"gcm_"+messageType,Toast.LENGTH_LONG);

        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
                    .equals(messageType)) {
                sendNotification("Send error: " + extras.toString());
                Log.d("tag","gcm_ee1"+ extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                    .equals(messageType)) {
                sendNotification("Deleted messages on server: "
                        + extras.toString());
                Log.d("tag","gcm_e221"+ extras.toString());
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
                    .equals(messageType)) {

                sendNotification("" + extras.get(Config.MSG_KEY)); //When Message is received normally from GCM Cloud Server

                Log.d("tag","gcm1"+messageType + "" + extras.get(Config.MSG_KEY));
                Toast.makeText(getApplicationContext(),"gcm_"+messageType,Toast.LENGTH_LONG);

            }
        }*/

        Log.e("tagBundle ", "" + intent.getExtras());
        Log.e("tagstring ", "" + intent.getExtras().toString());

        if (intent.getExtras().toString().contains("gcm.notification.title")) {


            sts = extras.getString("type");
            notifi_body = extras.getString("gcm.notification.body");
            notifi_title = extras.getString("gcm.notification.title");
            gcm_data = extras.getString("data");
            from = extras.getString("from");


            Log.e("tag", " " + sts + notifi_body + " -" + notifi_title + "*" + gcm_data);


          /*  try {
                JSONObject json_serv = new JSONObject(gcm_data);

                simage = json_serv.getString("firstname") + " " + json_serv.getString("lastname");


            } catch (JSONException e) {
                e.printStackTrace();
            }*/


            if (sts.equals("PALREQUEST")) {


                Log.e("tag", " 0req");


                try {
                    JSONObject json_serv = new JSONObject(gcm_data);

                    simage = json_serv.getString("firstname") + " " + json_serv.getString("lastname");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                palRequest(gcm_data);

            } else if (sts.equals("PALRESPOND")) {

                Log.e("tag", " 1res");

                try {
                    JSONObject json_serv = new JSONObject(gcm_data);

                    simage = json_serv.getString("firstname") + " " + json_serv.getString("lastname");


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                palRespond(gcm_data);

            } else if (sts.equals("ONETOONECHAT")) {

                Log.e("tag", " 2chat");

                gcm_data = extras.getString("data");

                try {
                    JSONObject json_serv = new JSONObject(gcm_data);

                    message = json_serv.getString("message");
                    str_oonbux_id = json_serv.getString("pal_oonbux_id");

                    ct_from_id = json_serv.getString("pal_oonbux_id");
                    receiver = ct_from_id;
                    ct_message = json_serv.getString("message");
                    ct_time = json_serv.getString("sent_utc_time");
                    ct_message_id = json_serv.getString("message_id");
                    pal_photo = json_serv.getString("photo_url");

                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    ct_to_id =  sharedPreferences.getString("oonbuxid", "");




                    Log.e("tag", " chat_notification \n"+ct_from_id+"\n"+ct_to_id+"\n"+ct_message+"\n"+ct_time);


                    dbclass = new DbC(context);

                    dbclass.chat_insert(1, ct_from_id,ct_to_id, ct_message, ct_time, ct_message_id,receiver);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                onetoonechat(message);
            }
        }

        GcmReceiver.completeWakefulIntent(intent);
    }

   /* private void sendNotification(String greetMsg) {

        Log.e("tag", "234" + greetMsg);

        Intent resultIntent = new Intent(this, ChatPage.class);
        resultIntent.putExtra("get_Server", greetMsg);
        resultIntent.setAction(Intent.ACTION_MAIN);
        resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,
                resultIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder mNotifyBuilder;
        NotificationManager mNotificationManager;

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotifyBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(notifi_title)
                .setContentText(notifi_body)
                .setSmallIcon(R.mipmap.launcher);
        // Set pending intent
        mNotifyBuilder.setContentIntent(resultPendingIntent);

        // Set Vibrate, Sound and Light
        int defaults = 0;
        defaults = defaults | Notification.DEFAULT_LIGHTS;
        defaults = defaults | Notification.DEFAULT_VIBRATE;
        defaults = defaults | Notification.DEFAULT_SOUND;

        mNotifyBuilder.setDefaults(defaults);
        // Set the content for Notification
        // mNotifyBuilder.setContentText("New Alert from Palani");
        // Set autocancel
        mNotifyBuilder.setAutoCancel(true);
        // Post img_emg notification
        mNotificationManager.notify(notifyID, mNotifyBuilder.build());
    }*/

    private void palRequest(String data) {
        Intent resultIntent = new Intent(this, PalAccept.class);
        resultIntent.putExtra("get_Server", data);
        resultIntent.putExtra("sts", "Request");
        resultIntent.setAction(Intent.ACTION_MAIN);
        resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,
                resultIntent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder mNotifyBuilder;
        NotificationManager mNotificationManager;
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(notifi_title)
                .setContentText("Pal Request from - " + simage)
                .setSmallIcon(R.mipmap.launcher);
        mNotifyBuilder.setContentIntent(resultPendingIntent);
        int defaults = 0;
        defaults = defaults | Notification.DEFAULT_LIGHTS;
        defaults = defaults | Notification.DEFAULT_VIBRATE;
        defaults = defaults | Notification.DEFAULT_SOUND;
        mNotifyBuilder.setDefaults(defaults);
        mNotifyBuilder.setAutoCancel(true);
        mNotificationManager.notify(notifyID, mNotifyBuilder.build());
    }

    private void palRespond(String data) {
        Intent resultIntent = new Intent(this, PalAccept.class);
        resultIntent.putExtra("get_Server", data);
        resultIntent.putExtra("sts", "Respond");
        resultIntent.setAction(Intent.ACTION_MAIN);
        resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,
                resultIntent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder mNotifyBuilder;
        NotificationManager mNotificationManager;
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(notifi_title)
                .setContentText("Pal Respond from - " + simage)
                .setSmallIcon(R.mipmap.launcher);
        mNotifyBuilder.setContentIntent(resultPendingIntent);
        int defaults = 0;
        defaults = defaults | Notification.DEFAULT_LIGHTS;
        defaults = defaults | Notification.DEFAULT_VIBRATE;
        defaults = defaults | Notification.DEFAULT_SOUND;
        mNotifyBuilder.setDefaults(defaults);
        mNotifyBuilder.setAutoCancel(true);
        mNotificationManager.notify(notifyID, mNotifyBuilder.build());
    }

    private void onetoonechat(String gcm_data) {

/*
        if (!isAppIsInBackground(getApplicationContext())) {

            // app is in foreground, broadcast the push message


            Intent i = new Intent();
            i.setAction("appendChatScreenMsg");
            i.putExtra("get_Server", gcm_data);
            this.sendBroadcast(i);

        } else {

            palmessage(gcm_data);
        }*/
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        String activityName = taskInfo.get(0).topActivity.getClassName();
        Log.e("tag",""+activityName +Pal_Chat.class.getName());

        if (activityName.equals(Pal_Chat.class.getName())) {
            Intent i = new Intent();
            i.setAction("appendChatScreenMsg");
            i.putExtra("get_Server", gcm_data);
            i.putExtra("chat_from", ct_from_id);
            i.putExtra("chat_message", ct_message);
            i.putExtra("chat_time", ct_time);
            i.putExtra("message_id", ct_message_id);
            i.putExtra("pal_photo", pal_photo);
            this.sendBroadcast(i);
            Log.e("tag11", ""+ct_from_id+ct_message+ct_time);
        } else {
            Log.e("tag13", ""+ct_from_id+ct_message+ct_time);
            palmessage(gcm_data);
        }


    }

    private void palmessage(String data) {




        Log.e("tag", ""+ct_from_id+ct_message+ct_time);

        Intent resultIntent = new Intent(this, Pal_Chat.class);
        resultIntent.putExtra("get_Server", data);
        resultIntent.putExtra("sts", "Respond");

        resultIntent.putExtra("chat_from", ct_from_id);
        resultIntent.putExtra("chat_message", ct_message);
        resultIntent.putExtra("chat_time", ct_time);
        resultIntent.putExtra("message_id", ct_message_id);
        resultIntent.putExtra("pal_photo", pal_photo);
        resultIntent.setAction(Intent.ACTION_MAIN);
        resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,
                resultIntent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder mNotifyBuilder;
        NotificationManager mNotificationManager;
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(notifi_title)
                .setContentText("New Message From - " + str_oonbux_id)
                .setSmallIcon(R.mipmap.launcher);
        mNotifyBuilder.setContentIntent(resultPendingIntent);
        int defaults = 0;
        defaults = defaults | Notification.DEFAULT_LIGHTS;
        defaults = defaults | Notification.DEFAULT_VIBRATE;
        defaults = defaults | Notification.DEFAULT_SOUND;
        mNotifyBuilder.setDefaults(defaults);
        mNotifyBuilder.setAutoCancel(true);
        mNotificationManager.notify(notifyID, mNotifyBuilder.build());
    }


}
