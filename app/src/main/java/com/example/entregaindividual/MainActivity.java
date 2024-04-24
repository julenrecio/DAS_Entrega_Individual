package com.example.entregaindividual;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.os.Bundle;

import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickBotonIniciarSesion (View view) {

        // Se recoge el usuario y contraseña de los campos de texto
        EditText editTextUsuario = findViewById(R.id.usuarioEditText);
        EditText editTextContraseña = findViewById(R.id.contraseñaEditText);
        String usuario = editTextUsuario.getText().toString();
        String contraseña = editTextContraseña.getText().toString();

        // Se indica la funcion a ejecutar en el index.php
        String accion = "iniciosesion";

        // Se crea el conjunto de datos de entrada para el trabajo
        Data datos = new Data.Builder()
                .putString("usuario", usuario)
                .putString("contraseña", contraseña)
                .putString("accion", accion)
                .build();

        // Se crea un trabajo de un solo uso
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(TrabajoInicioSesionYRegistro.class)
                .setInputData(datos)
                .build();

        // Se añade un observer para detectar cuando acaba el trabajo y gestionar el resultado
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if(workInfo != null && workInfo.getState().isFinished()){
                            TextView textViewResult = findViewById(R.id.textoResultado);
                            textViewResult.setText(workInfo.getOutputData().getString("resultado"));

                            // Se añade un retardo de tres segundos para que se pueda ver que el inicio
                            // de sesion y ha sido correcto y se lanza el intent de la actividad principal
                            if (workInfo.getOutputData().getString("resultado").equals("Inicio de sesion correcto, bienvenido " + usuario)){
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent i = new Intent(getBaseContext(), ActivityPantallaPrincipal.class);
                                        i.putExtra("usuario", usuario);
                                        i.putExtra("contraseña", contraseña);
                                        startActivity(i);
                                    }
                                }, 3000);
                            }

                        }
                    }
                });
        // Se encola el trabajo de inicio de sesion
        WorkManager.getInstance(this).enqueue(otwr);
    }

    public void onClickBotonRegistrarse (View view) {

        // Se recogen los datos de los campos de texto
        EditText editTextUsuario = findViewById(R.id.usuarioEditText);
        EditText editTextContraseña = findViewById(R.id.contraseñaEditText);
        String usuario = editTextUsuario.getText().toString();
        String contraseña = editTextContraseña.getText().toString();
        String accion = "registro";

        Data datos = new Data.Builder()
                .putString("usuario", usuario)
                .putString("contraseña", contraseña)
                .putString("accion", accion)
                .build();

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(TrabajoInicioSesionYRegistro.class)
                .setInputData(datos)
                .build();

        // Se muestra en un texto el mensaje imprimido por el index.php al realizar
        // la peticion HTTP en el trabajo
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if(workInfo != null && workInfo.getState().isFinished()){
                            TextView textViewResult = findViewById(R.id.textoResultado);
                            textViewResult.setText(workInfo.getOutputData().getString("resultado"));
                        }
                    }
                });

        // Se encola el trabajo
        WorkManager.getInstance(this).enqueue(otwr);
    }

}