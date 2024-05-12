package com.example.myapplication;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class NotificationHandler {
    private static final String TAG = NotificationHandler.class.getName();
    private static final String channel_ID = "buszjegy_notification_channel";
    private NotificationManager manager;
    private Context mcontext;

    private int id = 0;
    public NotificationHandler(Context context){
        this.mcontext = context;
        this.manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Log.d(TAG, "NotidicationHandler: belepve");

        createChannel();
    }

    private void createChannel(){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            Log.d(TAG, "createChannel: rossz verzió");
            return;
        }

        NotificationChannel channel = new NotificationChannel(channel_ID,"Mobiljegy notification",NotificationManager.IMPORTANCE_HIGH);

//        channel.enableLights(true);
//        channel.enableVibration(true);
        channel.setDescription("Mobiljegy ertesitesei");

        Log.d(TAG, "createChannel: belepve");
        this.manager.createNotificationChannel(channel);
    }

    public void send(String message){
        NotificationCompat.Builder builder  = new NotificationCompat.Builder(mcontext,channel_ID)
                .setContentTitle("Mobil buszjegy")
                .setContentText(message)
                .setSmallIcon(R.drawable.bus_24);

        Log.d(TAG, "send: belepve " +id);
        this.manager.notify(id,builder.build());
        id++;
    }
}
