package com.example.entregaindividual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.os.Bundle;

import java.util.Objects;

public class ActivityMain extends AppCompatActivity {

    // Se lanzan las distintas actividades dependiendo del boton que se pulse
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Se obtiene el nombre de usuario de las preferencias
        String sessionId = Preferencias.getPreferencias(this).getSessionId();

        // Si no hay usuario en las preferencias significa que el usuario nunca
        // ha iniciado sesioón y se le redirige a ActivityLoginRegister
        if (sessionId.equals("")) {
            Intent intent = new Intent(ActivityMain.this, ActivityLoginRegister.class);
            startActivity(intent);
        }

        // Se aplica el idioma guardado en las preferencias
        Preferencias.getPreferencias(this).applyLanguage(this);

        // Se establece el layout de la actividad
        setContentView(R.layout.activity_main);

        // Configuración de la action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }

    // Método para inflar la action bar
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // Método para lanzar la actividad de configuración cuando se pulse
    // la opción en la action bar
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_configuracion) {
            startActivity(new Intent(ActivityMain.this, ActivityConfiguracion.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Abrir la ActivityPilotos cuando se haga click en el botón
    public void onClickBotonPilotos (View view) {
        Intent i = new Intent(this, ActivityPilotos.class);
        startActivity(i);
    }

    // Abrir el diálogo de instrucciones
    public void onClickBotonInstrucciones (View view) {
        DialogFragment dialogo = new DialogoInstrucciones();
        dialogo.show(getSupportFragmentManager(),"etiqueta1");
    }

    // Abrir la ActivityRecordsCircuitos cuando se haga click en el botón
    public void onClickBotonEstadisticas(View view) {
        Intent i = new Intent(this, ActivityRecordsCircuitos.class);
        startActivity(i);
    }

    // Abrir la ActivityFCM cuando se haga click en el botón
    public void onClickBotonFCM(View view) {
        Intent i2 = new Intent(this, ActivityFCM.class);
        startActivity(i2);
    }

    // Abrir la ActivityImagenes cuando se haga click en el botón
    public void onClickBotonImagenes(View view) {
        Intent i = new Intent(this, ActivityImagenes.class);
        startActivity(i);
    }
}