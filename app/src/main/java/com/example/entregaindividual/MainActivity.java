package com.example.entregaindividual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickBotonPuntos (View view) {
        Intent i = new Intent(this, ActivityPuntos.class);
        startActivity(i);
    }

    public void onClickBotonPilotos (View view) {
        Intent i = new Intent(this, ActivityPilotos.class);
        startActivity(i);
    }

    public void onClickBotonInstrucciones (View view) {
        DialogFragment dialogo = new DialogoInstrucciones();
        dialogo.show(getSupportFragmentManager(),"etiqueta1");
    }

    public void onClickBotonEstadisticas(View view) {
        Intent i = new Intent(this, ActivityRecordsCircuitos.class);
        startActivity(i);
    }
}