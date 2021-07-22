package com.aziza.santridear.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.aziza.santridear.R;
import com.aziza.santridear.intro.SplashActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class NotifReceiver extends BroadcastReceiver {
    public static final Integer NOTIFICATION_ID = 1;
    public static final String CHANNEL_ID = "channel_1";
    public static final String CHANNEL_NAME = "channel_alarm";
    private static final String EXTRA_TYPE = "type";

    FirebaseFirestore ft = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    private final static int ID_ONETIME = 100;
    private final static int ID_REPEATING = 101;

    Date c = Calendar.getInstance().getTime();

    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
    String formattedDate = df.format(c);

    @Override
    public void onReceive(Context context, Intent intent) {

        String title = context.getString(R.string.notif_title);

        getPresentSantri(context.getApplicationContext());
//        showAlarmNotification(context, "Kehadiran", "Anak anda hadir", 100);
    }

    private void getPresentSantri(Context context) {
        DocumentReference docref = ft.collection("santri").document("Kehadiran").collection(formattedDate).document(auth.getUid());
        docref.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document != null) {
                    Boolean hadir = document.getBoolean("hadir");
                    String name = document.getString("santri");
                    String present = "";

                    if (hadir) {
                        present = "Hadir";
                    } else {
                        present = "Tidak Hadir";
                    }

                    String title = "Kehadiran";
                    String message = "Ananda " + name + " " + present;
                    int notifId = 100;

                    showAlarmNotification(context, title, message, notifId);


                }

            }
        });
    }

    // Gunakan metode ini untuk menampilkan notifikasi
    private void showAlarmNotification(Context context, String title, String message,
                                       int notifId) {
        Intent intent = new Intent(context, SplashActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.putri)
                .setContentIntent(pi)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        /*
        Untuk android Oreo ke atas perlu menambahkan notification channel
        Materi ini akan dibahas lebih lanjut di modul extended
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }

    }

    // Metode ini digunakan untuk menjalankan alarm repeating
    public void setRepeatingAlarm(Context context) {

        // Validasi inputan waktu terlebih dahulu
//        if (isDateInvalid(time, TIME_FORMAT)) return;
        Toast.makeText(context, "Notif Aktif", Toast.LENGTH_SHORT).show();
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, NotifReceiver.class);


        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0);
        if (alarmManager != null) {
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

    }


}