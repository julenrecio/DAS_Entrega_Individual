package com.example.entregaindividual;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ActivityFCM2 extends AppCompatActivity {

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