package com.example.entregaindividual;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class TrabajoSubirImagen extends Worker {

    public TrabajoSubirImagen(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        // Se hace una peticion HTTP para subir la imagen capturada con la camara
        // al servidor


        // Se obtiene un array de bytes como dato de entrada del trabajo y se
        // convierte en String
        String direccion = "http://34.88.51.200:81";
        String fotoEn64 = getInputData().getString("foto");
        String tituloFoto = getInputData().getString("titulo");
        String parametros = "funcion=insertarImagenBD&foto=" + fotoEn64 + "&titulo=" + tituloFoto;
        HttpURLConnection urlConnection;
        try {
            // Se establece la conexión con la direccion y los parametros especificados
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

        StringBuilder result = null;
        if (statusCode == 200) {
            // Si la respuesta es correcta, se procede a recoger el resultado en una variable
            BufferedInputStream inputStream;
            try {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            BufferedReader bufferedReader;
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            result = new StringBuilder();
            while (true) {
                try {
                    if ((line = bufferedReader.readLine()) == null) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                result.append(line);
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // Se crea un objeto Data para devolver el resultado
        assert result != null;
        Data resultado = new Data.Builder()
                .putString("resultado", result.toString())
                .build();
        return Result.success(resultado);
    }
}