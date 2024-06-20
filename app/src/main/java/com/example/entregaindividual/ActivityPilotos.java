package com.example.entregaindividual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

public class ActivityPilotos extends AppCompatActivity implements FragmentPilotos.ListenerDelFragment {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilotos);

        // Se obtiene la oreientación
        int orientation = getResources().getConfiguration().orientation;

        // Se crea el gestor de fragmentos
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Si esta en apaisado crear el fragmento del lado izquierdo de la pantalla
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragmentTransaction.replace(R.id.fragmentListviewLand, new FragmentPilotos());
            Intent i = getIntent();

            // Si el intent cotiene información, crear el fragmento del lado derecho de
            // la pantalla con la información
            if (i.hasExtra("nombre")) {
                String nombre = i.getStringExtra("nombre");
                int puntos = i.getIntExtra("puntos", 0);
                int fondo = i.getIntExtra("fondo", 0);
                ItemListView itemSeleccionado = new ItemListView(fondo, nombre, puntos);
                FragmentPilotosInfo fragment = FragmentPilotosInfo.newInstance(itemSeleccionado);
                fragmentTransaction.replace(R.id.fragmentListviewInfoLand, fragment);
            }
        } else {
            // Si esta en vertical, crear solo el fragmento del lado izquierdo en su
            // correspondiente contenedor
            fragmentTransaction.replace(R.id.fragmentListview, new FragmentPilotos());
        }
        fragmentTransaction.commit();
    }

    // Implementación del método de la interfaz definida en el fragment
    // Que hacer cuando se haga click en un elemento de la lista
    @Override
    public void seleccionarElemento(ItemListView item) {
        int orientation = getResources().getConfiguration().orientation;

        // Si la orientación es apaisado reemplazar el fragment por uno con la
        // información del item seleccionado
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            FragmentPilotosInfo fragment = FragmentPilotosInfo.newInstance(item);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentListviewInfoLand, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        // Si esta en vertical, abrir otra actividad y pasarle la información del
        // elemento seleccionado
        } else {
            Intent i = new Intent(this, ActivityPilotosInfoExtra.class);
            i.putExtra("nombre", item.getNombre());
            i.putExtra("puntos", item.getPuntos());
            i.putExtra("fondo", item.getFondo());
            startActivity(i);
        }
    }
}