package com.example.entregaindividual;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TrabajoBajarImagen extends Worker {

    public TrabajoBajarImagen(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        String nombreImagen = getInputData().getString("nombre");
        String direccion = "http://34.88.51.200:81/fotos/" + nombreImagen;
        HttpURLConnection urlConnection = null;
        try {
            URL destino = new URL(direccion);
            urlConnection = (HttpURLConnection) destino.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int statusCode;
        try {
            statusCode = urlConnection.getResponseCode();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Bitmap elBitmap = null;
        if (statusCode == 200) {
            BufferedInputStream inputStream = null;
            try {
                elBitmap = BitmapFactory.decodeStream(urlConnection.getInputStream());
                Log.i("bitmap", "asignacion");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        elBitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream);
        byte[] fototransformada = stream.toByteArray();
        String fotoEn64 = Base64.encodeToString(fototransformada, Base64.DEFAULT);
        Data resultado = new Data.Builder()
                .putString("fotoString", fotoEn64)
                .build();
        return Result.success(resultado);
    }
}
