package com.example.entregaindividual;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class ActivityFCM extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fcm);

        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
    }
    public void onClickBotonMandarMensaje(View view) {

        // Se crea el trabajo definido en la clase TrabajoFCM
        OneTimeWorkRequest otwr = new OneTimeWorkRequest.Builder(TrabajoFCM.class)
                .build();

        // Se añade un observer para detectar cuando haya finalizado el trabajo
        // y recoger el resultado.
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if(workInfo != null && workInfo.getState().isFinished()){
                            TextView textViewResult = findViewById(R.id.resultado);
                            textViewResult.setText(workInfo.getOutputData().getString("resultado"));
                        }
                    }
                });

        WorkManager.getInstance(this).enqueue(otwr);
    }
}