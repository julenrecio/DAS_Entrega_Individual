package com.example.entregaindividual;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ActivityFCM2 extends AppCompatActivity {

    //Esta actividad se crea cuando se abre la notificación enviada por Firebase
    //Se recoje el mensaje del intent enviado desde el metodo onMessageReceived

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcm2);

        TextView textViewMensaje = findViewById(R.id.mensaje);

        if (getIntent().getExtras() != null) {
            String mensaje = getIntent().getExtras().getString("mensaje");
            textViewMensaje.setText(mensaje);
        }

    }
}