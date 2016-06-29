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

    String notifi_body,notifi_title,gcm_data,from;
    public GcmNotificationIntentService() {
        super("GcmIntentService");
    }

    GcmListenerService gcmget;
    String sts;



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

        Log.e("tagBundle ",""+intent.getExtras());
        Log.e("tagstring ",""+intent.getExtras().toString());

        if(intent.getExtras().toString().contains("gcm.notification.title")){


            sts = extras.getString("type");
            notifi_body = extras.getString("gcm.notification.body");
            notifi_title = extras.getString("gcm.notification.title");
            gcm_data = extras.getString("data");
            from = extras.getString("from");


            Log.e("tag"," "+notifi_body +" -"+notifi_title+"*"+gcm_data);

            if (sts.equals("PALREQUEST")) {
                palRequest(gcm_data);
            }
            else if (sts.equals("PALRESPOND")) {


            }
        }






       /* if(!(extras.containsKey("type"))) {

            if (extras.getString("type") == null) {

                sts = extras.getString("type");

                if (sts.equals("PALREQUEST")) {
                    palRequest(gcm_data);
                } else if (sts.equals("PALRESPOND")) {

                }
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



    private void palRequest(String data) {
        Intent resultIntent = new Intent(this, PalAccept.class);
        resultIntent.putExtra("get_Server", data);
        resultIntent.setAction(Intent.ACTION_MAIN);
        resultIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,
                resultIntent, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder mNotifyBuilder;
        NotificationManager mNotificationManager;
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(notifi_title)
                .setContentText("Pal Request from - "+from)
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
