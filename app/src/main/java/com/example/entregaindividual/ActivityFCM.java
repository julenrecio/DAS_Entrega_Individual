package com.example.entregaindividual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class ActivityFCM extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcm);

        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);

        // Se crea el trabajo definido en la clase TrabajoFCMGuardarToken
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(TrabajoFCMGuardarToken.class).build();
        WorkManager.getInstance(this).enqueue(otwr);
    }
    public void onClickBotonMandarMensaje(View view) {

        // Se obtiene el mensaje del EditText
        EditText editTextMensaje = findViewById(R.id.mensajeEditText);
        String mensaje = editTextMensaje.getText().toString();

        // Se crea el objeto Data y se añade el mensaje
        Data dataMensaje = new Data.Builder()
                .putString("mensaje", mensaje)
                .build();

        // Se crea el otwr y añaden los datos como input
        OneTimeWorkRequest otwr2 = new OneTimeWorkRequest.Builder(TrabajoFCMEnviarMensaje.class)
                .setInputData(dataMensaje)
                .build();

        // Se añade un observer para detectar cuando haya finalizado el trabajo
        // y recoger el resultado
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr2.getId())
                .observe(this, workInfo -> {
                    if(workInfo != null && workInfo.getState().isFinished()){
                        TextView textViewResult = findViewById(R.id.resultado);
                        textViewResult.setText(workInfo.getOutputData().getString("resultado"));
                    }
                });

        // Se encola el trabajo
        WorkManager.getInstance(this).enqueue(otwr2);
    }
}