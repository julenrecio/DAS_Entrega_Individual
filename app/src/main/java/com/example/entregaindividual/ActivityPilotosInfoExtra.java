package com.example.entregaindividual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

public class ActivityPilotosInfoExtra extends AppCompatActivity {

    // Atributo para guardar el item actual
    private ItemListView item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilotos_info_extra);

        // Recoger y crear un item con los datos del intent
        Intent i = getIntent();
        item = new ItemListView(
                i.getIntExtra("fondo", 0),
                i.getStringExtra("nombre"),
                i.getIntExtra("puntos", 0));

        // Crear el fragment con el item creado
        FragmentPilotosInfo fragment = FragmentPilotosInfo.newInstance(item);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentListviewInfo, fragment)
                .commit();
    }

    // Método para reaccionar al cambio de orientación, queremos que cuando se cambie
    // a apaisado se conserve la informacion extra del elemento seleccionado
    @SuppressLint("ChromeOsOnConfigurationChanged")
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i("nombre", item.getNombre());
            Intent i = new Intent(this, ActivityPilotos.class);
            i.putExtra("nombre", item.getNombre());
            i.putExtra("puntos", item.getPuntos());
            i.putExtra("fondo", item.getFondo());
            startActivity(i);
            finish();
        }
    }
}