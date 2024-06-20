package com.example.entregaindividual;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class ServicioFirebase extends FirebaseMessagingService {
    public ServicioFirebase() {

    }

    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // Si el mensaje contiene datos se abre manda una notificacion, que al
        // pulsarla abre una actividad que muestra el mensaje enviado a traves
        // de Firebase

        if (remoteMessage.getData().size() > 0) {
            Map<String, String> data = remoteMessage.getData();

            Intent intent = new Intent(this, ActivityFCM2.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("mensaje", data.get("mensaje"));
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT | PendingIntent.FLAG_IMMUTABLE);

            // Se crea la notificacion y el canal
            NotificationManager elManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel elCanal = new NotificationChannel("IdCanal", "Canal1",
                    NotificationManager.IMPORTANCE_DEFAULT);
            elManager.createNotificationChannel(elCanal);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "IdCanal")
                    .setSmallIcon(android.R.drawable.ic_notification_clear_all)
                    .setContentTitle(data.get("titulo"))
                    .setContentText(data.get("cuerpo"))
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);

            // Se manda la notificación
            elManager.notify(0, notificationBuilder.build());
        }
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Data dataToken = new Data.Builder()
                .putString("token", token)
                .build();
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(TrabajoFCMGuardarToken.class)
                .setInputData(dataToken)
                .build();
        WorkManager.getInstance(this).enqueue(otwr);
    }
}
