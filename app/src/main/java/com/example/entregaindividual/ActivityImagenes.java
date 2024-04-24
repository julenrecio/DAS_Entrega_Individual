package com.example.entregaindividual;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.Manifest;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class ActivityImagenes extends AppCompatActivity {

    // Se hace uso de un ActivityResultLauncher para captar una imagen de la camara
    // Se convierte la imagen a un array de bytes y se manda al trabajo que
    // se encarga de subir la imagen al servidor

    private String nombreImagen;
    private ActivityResultLauncher<Intent> takePictureLauncher =
            registerForActivityResult(new
                    ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK &&
                        result.getData()!= null) {
                    Bundle bundle = result.getData().getExtras();
                    Bitmap laminiatura = (Bitmap) bundle.get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    laminiatura.compress(Bitmap.CompressFormat.JPEG, 10, stream);
                    byte[] fototransformada = stream.toByteArray();
                    String fotoen64 = Base64.encodeToString(fototransformada,Base64.DEFAULT);

                    Data datos1 = new Data.Builder()
                            .putByteArray("foto", fototransformada)
                            .build();

                    OneTimeWorkRequest otwr1 = new OneTimeWorkRequest.Builder(TrabajoSubirImagen.class)
                            .setInputData(datos1)
                            .build();
                    // Se añade un observer para que detecte cuando se termina el trabajo para poder
                    // recoger el resultado y poner en la etiqueta el nombre de la imagen generado
                    // en el index.php
                    WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr1.getId())
                            .observe(this, new Observer<WorkInfo>() {
                                @Override
                                public void onChanged(WorkInfo workInfo) {
                                    if(workInfo != null && workInfo.getState().isFinished()){
                                        TextView textViewResult = findViewById(R.id.resultadoImagen);
                                        nombreImagen = workInfo.getOutputData().getString("resultado");
                                        textViewResult.setText("La imagen mostrada es: " + nombreImagen);
                                    }
                                }
                            });
                    // Se encola el primer trabajo
                    WorkManager.getInstance(this).enqueue(otwr1);

                    // Se define el objeto Data de entrada para el segundo trabajo
                    Data datos2 = new Data.Builder()
                            .putString("nombre", nombreImagen)
                            .build();
                    // Se define el segundo trabajo y se especifican sus datos de entrada
                    OneTimeWorkRequest otwr2 = new OneTimeWorkRequest.Builder(TrabajoBajarImagen.class)
                            .setInputData(datos2)
                            .build();

                    // Se recoge el resultado del trabajo extrayendolo del outputData
                    // Se transforma el dato en bitmap y se añade al imageView
                    WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr2.getId())
                            .observe(this, new Observer<WorkInfo>() {
                                @Override
                                public void onChanged(WorkInfo workInfo) {
                                    if(workInfo != null && workInfo.getState().isFinished()){
                                        ImageView imageView = findViewById(R.id.imageView);
                                        String fotoString = workInfo.getOutputData().getString("fotoString");
                                        byte[] byteArray = Base64.decode(fotoString, Base64.DEFAULT);
                                        Bitmap elBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                                        imageView.setImageBitmap(elBitmap);
                                    }
                                }
                            });

                    WorkManager.getInstance(this).enqueue(otwr2);
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagenes);


    }

    public void onClickBotonSacarFoto(View view) {

        // Si no los tiene, se piden los permisos para usar la camara
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    200);
        }

        // Se lanza el ActivityResult
        Intent elIntentFoto= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePictureLauncher.launch(elIntentFoto);
    }
}