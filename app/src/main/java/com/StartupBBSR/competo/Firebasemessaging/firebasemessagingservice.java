package com.StartupBBSR.competo.Firebasemessaging;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.Person;

import com.StartupBBSR.competo.Activity.MainActivity;
import com.StartupBBSR.competo.Fragments.EventMainFragment;
import com.StartupBBSR.competo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Calendar;
import java.util.Objects;

public class firebasemessagingservice extends FirebaseMessagingService {

    private FirebaseFirestore firestoreDB;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onMessageReceived(@NonNull @NotNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getData().get("category").equals("event"))
        {
                geteventmessage(Objects.requireNonNull(remoteMessage.getNotification()).getTitle(),remoteMessage.getNotification().getBody());
        }
        else if(remoteMessage.getData().get("category").equals("chat"))
        {
                getchatmessage(Objects.requireNonNull(remoteMessage.getNotification()).getTitle(),remoteMessage.getNotification().getBody());
        }
        else if(remoteMessage.getData().get("category").equals("request"))
        {
                getrequestmessage(Objects.requireNonNull(remoteMessage.getNotification()).getTitle(),remoteMessage.getNotification().getBody());
        }
        else if(remoteMessage.getData().get("category").equals("team"))
        {
                getteammessage(Objects.requireNonNull(remoteMessage.getNotification()).getTitle(),remoteMessage.getNotification().getBody());
        }
    }

    public void geteventmessage(String title, String body) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("notification","event");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationManager notificationmanager1 = getSystemService(NotificationManager.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel("event_notification", "Events", NotificationManager.IMPORTANCE_DEFAULT);
            channel1.setDescription("this is fcm event channel");

            notificationmanager1.createNotificationChannel(channel1);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "event_notification")
                .setSmallIcon(R.drawable.teamos_one_point_four_logo)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        int oneTimeID = (int) SystemClock.uptimeMillis();
        notificationmanager1.notify(oneTimeID, builder.build());
    }

    public void getrequestmessage(String title, String body) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("notification","request");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationManager notificationmanager2 = getSystemService(NotificationManager.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel2 = new NotificationChannel("request_notification", "Requests", NotificationManager.IMPORTANCE_DEFAULT);
            channel2.setDescription("this is fcm request channel");

            notificationmanager2.createNotificationChannel(channel2);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "request_notification")
                .setSmallIcon(R.drawable.teamos_one_point_four_logo)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        int oneTimeID = (int) SystemClock.uptimeMillis();
        notificationmanager2.notify(oneTimeID, builder.build());
    }

    public void getchatmessage(String title, String body) {

        Person user = new Person.Builder().setIcon(null).setName("Chat").build();
        NotificationCompat.MessagingStyle style = new NotificationCompat.MessagingStyle(user).addMessage(body, Calendar.getInstance().getTimeInMillis(),title);

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("notification","chat");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationManager notificationmanager3 = getSystemService(NotificationManager.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel3 = new NotificationChannel("chat_notification", "Chats", NotificationManager.IMPORTANCE_DEFAULT);
            channel3.setDescription("this is fcm chat channel");

            notificationmanager3.createNotificationChannel(channel3);
        }


        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "chat_notification")
                .setSmallIcon(R.drawable.teamos_one_point_four_logo)
                .setStyle(style)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        int oneTimeID = (int) SystemClock.uptimeMillis();
        notificationmanager3.notify(oneTimeID, builder.build());
    }

    public void getteammessage(String title, String body) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("notification","team");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationManager notificationmanager4 = getSystemService(NotificationManager.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel4 = new NotificationChannel("team_notification", "Teams", NotificationManager.IMPORTANCE_DEFAULT);
            channel4.setDescription("this is fcm team channel");

            notificationmanager4.createNotificationChannel(channel4);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "team_notification")
                .setSmallIcon(R.drawable.teamos_one_point_four_logo)
                .setContentTitle(title)
                .setContentText(body)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        int oneTimeID = (int) SystemClock.uptimeMillis();
        notificationmanager4.notify(oneTimeID, builder.build());
    }
}
