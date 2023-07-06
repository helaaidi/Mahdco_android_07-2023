package com.example.loginregister;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        // get the bundles in the message
        final Bundle bundle = intent.getExtras();
        Toast.makeText(context,bundle.getString("msg"),Toast.LENGTH_SHORT).show();
        // check the action equal to the action we fire in broadcast,
        /*if (intent.getAction().equalsIgnoreCase("com.example.Broadcast")) {
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_android)
                    .setContentTitle("call")
                    .setContentText(bundle.getString("msg"))
                    .build();
            notificationManager.notify(1, notification);

        }
*/
    }
}
