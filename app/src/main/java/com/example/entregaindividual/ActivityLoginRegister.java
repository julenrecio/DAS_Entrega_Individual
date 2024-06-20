package com.example.entregaindividual;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.os.Bundle;

import android.widget.EditText;
import android.widget.TextView;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.Objects;

public class ActivityLoginRegister extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
    }

    public void onClickBotonIniciarSesion (View view) {

        // Se recoge el usuario y contraseña de los campos de texto
        EditText editTextUsuario = findViewById(R.id.usuarioEditText);
        EditText editTextContrasena = findViewById(R.id.contrasenaEditText);
        String usuario = editTextUsuario.getText().toString();
        String contrasena = editTextContrasena.getText().toString();

        // Se indica la funcion a ejecutar en el index.php
        String accion = "iniciosesion";

        // Se crea el conjunto de datos de entrada para el trabajo
        Data datos = new Data.Builder()
                .putString("usuario", usuario)
                .putString("contraseña", contrasena)
                .putString("accion", accion)
                .build();

        // Se crea un trabajo de un solo uso
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(TrabajoInicioSesionYRegistro.class)
                .setInputData(datos)
                .build();

        // Se añade un observer para detectar cuando acaba el trabajo y gestionar el resultado
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, workInfo -> {
                    if(workInfo != null && workInfo.getState().isFinished()){
                        TextView textViewResult = findViewById(R.id.textoResultado);
                        textViewResult.setText(workInfo.getOutputData().getString("resultado"));

                        // Se añade un retardo de tres segundos para que se pueda ver que el inicio
                        // de sesion y ha sido correcto y se lanza el intent de la actividad principal
                        if (Objects.equals(workInfo.getOutputData().getString("resultado"), "Inicio de sesion correcto, bienvenido " + usuario)){
                            Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                Intent i = new Intent(getBaseContext(), ActivityMain.class);
                                i.putExtra("usuario", usuario);
                                i.putExtra("contraseña", contrasena);
                                startActivity(i);
                            }, 1000);
                        }

                    }
                });
        // Se encola el trabajo de inicio de sesion
        WorkManager.getInstance(this).enqueue(otwr);
    }

    public void onClickBotonRegistrarse (View view) {

        // Se recogen los datos de los campos de texto
        EditText editTextUsuario = findViewById(R.id.usuarioEditText);
        EditText editTextContrasena = findViewById(R.id.contrasenaEditText);
        String usuario = editTextUsuario.getText().toString();
        String contrasena = editTextContrasena.getText().toString();
        String accion = "registro";

        Data datos = new Data.Builder()
                .putString("usuario", usuario)
                .putString("contraseña", contrasena)
                .putString("accion", accion)
                .build();

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(TrabajoInicioSesionYRegistro.class)
                .setInputData(datos)
                .build();

        // Se muestra en un texto el mensaje imprimido por el index.php al realizar
        // la peticion HTTP en el trabajo
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, workInfo -> {
                    if(workInfo != null && workInfo.getState().isFinished()){
                        TextView textViewResult = findViewById(R.id.textoResultado);
                        textViewResult.setText(workInfo.getOutputData().getString("resultado"));
                    }
                });

        // Se encola el trabajo
        WorkManager.getInstance(this).enqueue(otwr);
    }

}