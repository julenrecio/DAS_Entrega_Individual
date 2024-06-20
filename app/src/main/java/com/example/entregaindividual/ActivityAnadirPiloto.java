package com.example.entregaindividual;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ActivityAnadirPiloto extends AppCompatActivity {

    String s1;
    String s2;
    String s3;
    String s4;
    String s5;
    String s6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_piloto);

        // Recuperación de información en caso de perdida
        if (savedInstanceState != null) {
            s1 = savedInstanceState.getString("campo1");
            s2 = savedInstanceState.getString("campo2");
            s3 = savedInstanceState.getString("campo3");
            s4 = savedInstanceState.getString("campo4");
            s5 = savedInstanceState.getString("campo5");
            s6 = savedInstanceState.getString("campo6");
        }

    }

    // Al pulsar guardar se recogen todos los datos de los EditText y se insertan
    // en la base de datos
    public void onClickBotonGuardar (View view) {

        EditText e1 = findViewById(R.id.nombreEditText);
        EditText e2 = findViewById(R.id.ppcEditText);
        EditText e3 = findViewById(R.id.reaccionEditText);
        EditText e4 = findViewById(R.id.defensaEditText);
        EditText e5 = findViewById(R.id.precisionEditText);
        EditText e6 = findViewById(R.id.valoracionEditText);

        s1 = e1.getText().toString();
        s2 = e2.getText().toString();
        s3 = e3.getText().toString();
        s4 = e4.getText().toString();
        s5 = e5.getText().toString();
        s6 = e6.getText().toString();

        // Se instancia la clase que gestiona la base de datos
        SQLiteDatabase bd;
        try (Database gestorBD = new Database(this, "Pilotos Formula 1", null, 1)) {
            bd = gestorBD.getWritableDatabase();
        }

        // Insercion de los datos
        String sentence = "INSERT INTO Pilotos VALUES (null, '" + s1 + "', " + s2 + ", " + s3 + ", " + s4 + ", " + s5 + ", " + s6 + ")";
        bd.execSQL(sentence);

        // comprobar si tiene permisos de lanzar notificaciones
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        // Crear la notifiación
        NotificationManager elManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder elBuilder = new NotificationCompat.Builder(this, "idCanal");

        // Crear el canal
        NotificationChannel elCanal = new NotificationChannel("idCanal", "canal1", NotificationManager.IMPORTANCE_DEFAULT);

        // Configurar el canal
        elCanal.setDescription("Descripción del canal");
        elCanal.enableLights(true);
        elCanal.setLightColor(Color.RED);
        elCanal.setVibrationPattern(new long[]{0, 1000, 500, 1000});
        elCanal.enableVibration(true);

        // Crear el canal
        elManager.createNotificationChannel(elCanal);

        // Configurar el builder
        elBuilder.setSmallIcon(android.R.drawable.stat_sys_warning);
        elBuilder.setContentTitle("Mensaje de Alerta");
        elBuilder.setContentText("Se ha añadido un nuevo piloto");
        elBuilder.setVibrate(new long[]{0, 1000, 500, 1000});
        elBuilder.setAutoCancel(true);

        // Lanzar la notificación
        elManager.notify(1, elBuilder.build());
    }

    // Guardar los datos para poder recuperalos en caso de perdida
    @Override
    public void onSaveInstanceState(@NonNull Bundle saved) {
        super.onSaveInstanceState(saved);
        saved.putString("campo1", s1);
        saved.putString("campo2", s1);
        saved.putString("campo3", s1);
        saved.putString("campo4", s1);
        saved.putString("campo5", s1);
        saved.putString("campo6", s1);
    }
}