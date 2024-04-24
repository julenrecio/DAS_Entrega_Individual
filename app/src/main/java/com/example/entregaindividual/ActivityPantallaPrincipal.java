package com.example.entregaindividual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.view.View;
import android.os.Bundle;

public class ActivityPantallaPrincipal extends AppCompatActivity {

    // Se lanzan las distintas actividades dependiendo del boton que se pulse
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
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

    public void onClickBotonFCM(View view) {
        Intent i = getIntent();
        String usuario = i.getStringExtra("usuario");
        String contraseña = i.getStringExtra("contraseña");
        Intent i2 = new Intent(this, ActivityFCM.class);
        i2.putExtra("usuario", usuario);
        i2.putExtra("contraseña", contraseña);
        startActivity(i2);
    }

    public void onClickBotonImagenes(View view) {
        Intent i = new Intent(this, ActivityImagenes.class);
        startActivity(i);
    }
}