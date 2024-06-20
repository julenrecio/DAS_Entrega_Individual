package com.example.entregaindividual;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class ActivityImagenes extends AppCompatActivity {

    // Se hace uso de un ActivityResultLauncher para captar una imagen de la camara
    // Se convierte la imagen a un array de bytes y se manda al trabajo que
    // se encarga de subir la imagen al servidor

    private final ActivityResultLauncher<Intent> takePictureLauncher =
            registerForActivityResult(new
                    ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK &&
                        result.getData()!= null) {

                    // Se obtiene la imagen en formato Bitmap
                    Bundle bundle = result.getData().getExtras();
                    assert bundle != null;
                    Bitmap laminiatura = (Bitmap) bundle.get("data");

                    // Se convierte a JPEG
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    assert laminiatura != null;
                    laminiatura.compress(Bitmap.CompressFormat.JPEG, 75, stream);

                    // Se codifica a String
                    byte[] fototransformada = stream.toByteArray();
                    String fotoen64 = Base64.encodeToString(fototransformada,Base64.DEFAULT);

                    // Se obtiene el titulo de la foto del EditText
                    EditText editText = findViewById(R.id.tituloFoto);
                    String tituloFoto = editText.getText().toString();

                    // Se crea el objeto Data y se añaden la foto codificada y el titulo de la foto
                    Data datos1 = new Data.Builder()
                            .putString("foto", fotoen64)
                            .putString("titulo", tituloFoto)
                            .build();

                    // Se crea el trabajo
                    OneTimeWorkRequest otwr1 = new OneTimeWorkRequest.Builder(TrabajoSubirImagen.class)
                            .setInputData(datos1)
                            .build();

                    // Se añade un observer para detectar cuando haya un resultado
                    WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr1.getId())
                            .observe(this, workInfo -> {
                                if(workInfo != null && workInfo.getState().isFinished()){

                                    // Se coloca el resultado del trabajo en el TextView
                                    TextView textViewResult = findViewById(R.id.resultadoImagen);
                                    textViewResult.setText(workInfo.getOutputData().getString("resultado"));
                                }
                            });

                    // Se encola el trabajo
                    WorkManager.getInstance(this).enqueue(otwr1);
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

        // Se obtiene el titulo de la foto
        EditText editText = findViewById(R.id.tituloFoto);
        String tituloFoto = editText.getText().toString();

        // Si esta vacio, se lanza un Toast
        if (tituloFoto.isEmpty()) {
            Toast.makeText(this, "Introduce el nombre de la foto primero", Toast.LENGTH_LONG).show();
        } else {
            // Se lanza el ActivityResult
            Intent elIntentFoto= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureLauncher.launch(elIntentFoto);
        }
    }

    public void onClickBotonVerFoto(View view) {

        // Se obtiene el titulo de la foto
        EditText editText = findViewById(R.id.tituloFoto);
        String tituloFoto = editText.getText().toString();

        // Si esta vacio, se lanza un Toast
        if (tituloFoto.isEmpty()) {
            Toast.makeText(this, "Introduce el nombre de la foto primero", Toast.LENGTH_LONG).show();
        } else {

            // Se crea el objeto Data y se añade el titulo de la foto
            Data datos2 = new Data.Builder()
                    .putString("titulo", tituloFoto)
                    .build();

            // Se crea el trabajo
            OneTimeWorkRequest otwr2 = new OneTimeWorkRequest.Builder(TrabajoBajarImagen.class)
                    .setInputData(datos2)
                    .build();

            // Se añade el observer
            WorkManager.getInstance(this).getWorkInfoByIdLiveData(otwr2.getId())
                    .observe(this, workInfo -> {
                        if(workInfo != null && workInfo.getState().isFinished()) {
                            ImageView imageView = findViewById(R.id.imageView);
                            String fotoString = workInfo.getOutputData().getString("fotoString");
                            if (fotoString == null) {
                                Toast.makeText(getApplicationContext(), "No se ha podido decodificar la foto", Toast.LENGTH_LONG).show();
                            } else {
                                byte[] byteArray = Base64.decode(fotoString, Base64.DEFAULT);
                                Bitmap elBitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                                imageView.setImageBitmap(elBitmap);
                            }
                        }
                    });

            // Se encola el trabajo
            WorkManager.getInstance(this).enqueue(otwr2);
        }
    }
}