package com.example.entregaindividual;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class ActivityConfiguracion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Se aplica el idioma guardado en las preferencias
        Preferencias.getPreferencias(this).applyLanguage(this);

        // Se establece el layout de la actividad
        setContentView(R.layout.activity_configuracion);

        // Configuración de la action bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }

    // Cuando se haga click en "cerrar sesión" se borra el nombre de usuario
    // de las preferencias, se abre la actividad de inicio de sesión y se cierra
    // la actividad de configuración
    public void onClickBotonCerrarSesion (View view) {
        Preferencias.getPreferencias(this).setSessionId("");
        startActivity(new Intent(ActivityConfiguracion.this, ActivityLoginRegister.class));
        finish();
    }

    // Llamar al método para cambiar el idioma al hacer click en el botón
    public void onClickBotonIdiomaIngles (View view) {
        changeLanguage("en");
    }

    // Llamar al método para cambiar el idioma al hacer click en el botón
    public void onClickBotonIdiomaEspanol (View view) {
        changeLanguage("es");
    }

    // Inicializar la actividad principal y cerrar la configuración al hacer click en "volver"
    public void onClickBotonVolver (View view) {
        startActivity(new Intent(ActivityConfiguracion.this, ActivityMain.class));
        finish();
    }

    // Actualizar el idioma en las freferencias y reiniciar la actividad
    private void changeLanguage(String language) {
        Preferencias.getPreferencias(this).setLanguage(language);
        finish();
        startActivity(getIntent());
    }

    // Método para inflar la action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    // Método para lanzar la actividad de configuración cuando se pulse
    // la opción en la action bar
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_configuracion) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}