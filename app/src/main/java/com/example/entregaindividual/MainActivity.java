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

        EditText editTextUsuario = findViewById(R.id.usuarioEditText);
        EditText editTextContraseña = findViewById(R.id.contraseñaEditText);
        String usuario = editTextUsuario.getText().toString();
        String contraseña = editTextContraseña.getText().toString();
        String accion = "iniciosesion";

        Data datos = new Data.Builder()
                .putString("usuario", usuario)
                .putString("contraseña", contraseña)
                .putString("accion", accion)
                .build();

        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(TrabajoInicioSesionYRegistro.class)
                .setInputData(datos)
                .build();

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if(workInfo != null && workInfo.getState().isFinished()){
                            TextView textViewResult = findViewById(R.id.textoResultado);
                            textViewResult.setText(workInfo.getOutputData().getString("resultado"));

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
                });

        WorkManager.getInstance(this).enqueue(otwr);
    }

    public void onClickBotonRegistrarse (View view) {

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

        WorkManager.getInstance(this).enqueue(otwr);
    }

}