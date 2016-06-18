package sqindia.net.oonbux;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmListenerService;
import com.google.android.gms.gcm.GcmReceiver;
import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * Created by Salman on 6/16/2016.
 */
public class GcmNotificationIntentService extends IntentService {
    // Sets an ID for the notification, so it can be updated
    public static final int notifyID = 9001;
    public static final String TAG = "GCMNotificationIntentService";
    NotificationCompat.Builder builder;

    String notifi_body,notifi_title,gcm_data;
    public GcmNotificationIntentService() {
        super("GcmIntentService");
    }

    GcmListenerService gcmget;



    @Override
    protected void onHandleIntent(Intent intent) {

        Bundle extras = intent.getExtras();






        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);

        String asdf = intent.getExtras().toString();
        Log.e("tag"," "+asdf);

        notifi_body = extras.getString("gcm.notification.body");
        notifi_title = extras.getString("gcm.notification.title");
        gcm_data = extras.getString("somekey");


       if (gcm_data!= null) {
           sendNotification("Hi your message is: " + gcm_data); //When Message is received normally from GCM Cloud Server
       }
        else{
           sendNotification("");
       }
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
        GcmReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String greetMsg) {

        Log.e("tag","234"+greetMsg);

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
    }
}
