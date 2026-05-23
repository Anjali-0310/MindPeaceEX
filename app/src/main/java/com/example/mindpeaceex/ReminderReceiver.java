package com.example.mindpeaceex;

import android.app.*;
import android.content.*;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;

public class ReminderReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String msg = intent.getStringExtra("msg");
        int id = intent.getIntExtra("id", 0);

        NotificationManager manager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "reminder_channel";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId, "Reminders",
                    NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }

        // 🔊 SOUND
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);

        // ACTION: DONE
        Intent doneIntent = new Intent(context, ActionReceiver.class);
        doneIntent.putExtra("id", id);

        PendingIntent donePending = PendingIntent.getBroadcast(
                context, id, doneIntent, PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(context, channelId)
                .setContentTitle("Reminder ⏰")
                .setContentText(msg)
                .setVibrate(new long[]{0, 500, 1000, 500})
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setSound(sound)
                .addAction(0, "Done", donePending)
                .setAutoCancel(true)
                .build();

        manager.notify(id, notification);
    }
}