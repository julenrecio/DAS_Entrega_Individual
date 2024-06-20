package com.example.entregaindividual;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class TrabajoBajarImagen extends Worker {

    public TrabajoBajarImagen(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        String direccion = "http://34.88.51.200:81";
        String tituloFoto = getInputData().getString("titulo");
        String parametros = "funcion=extraerImagenBD&titulo=" + tituloFoto;
        HttpURLConnection urlConnection;
        try {
            //Se abre la conexión
            URL destino = new URL(direccion);
            urlConnection = (HttpURLConnection) destino.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            // Se especifica el metodo de la petición
            urlConnection.setRequestMethod("POST");
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        }
        // Se configura la conexión para que pueda enviar datos
        urlConnection.setDoOutput(true);
        // Se inidica la forma de enviar los datos
        urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        int statusCode;
        try {
            // Se añaden los parametros a la petición
            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.print(parametros);
            out.close();
            statusCode = urlConnection.getResponseCode();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Bitmap elBitmap = null;
        if (statusCode == 200) {
            try {
                InputStream inputStream = urlConnection.getInputStream();
                elBitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Se transforma el bitmap en String
        if (elBitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            elBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] fototransformada = stream.toByteArray();
            String fotoEn64 = Base64.encodeToString(fototransformada, Base64.DEFAULT);
            Data resultado = new Data.Builder()
                    .putString("fotoString", fotoEn64)
                    .build();
            return Result.success(resultado);
        } else {
            Toast.makeText(getApplicationContext(), "El bitmap descargado esta vacio", Toast.LENGTH_LONG).show();
            return Result.failure();
        }
    }
}