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

public class TrabajoInicioSesionYRegistro extends Worker {

    public TrabajoInicioSesionYRegistro(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        // Se indica la direccion del servidor
        String direccion = "http://34.88.51.200:81";
        // Se recogen los datos de entrada introducidos por el usuario en la MainActivity
        String usuario = getInputData().getString("usuario");
        String contrasena = getInputData().getString("contraseña");
        String accion = getInputData().getString("accion");
        // Se indica la funcion a usar en el index.php
        String funcion = "inicioSesionYRegistro";
        // Se crea el String que define los parametros de la petición
        String parametros = "username=" + usuario + "&password=" + contrasena + "&accion=" + accion + "&funcion=" + funcion;

        HttpURLConnection urlConnection;
        try {
            // Se establece la conexión
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
            Preferencias.getPreferencias(getApplicationContext()).setSessionId(usuario);
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
        assert result != null;
        Data resultado = new Data.Builder()
                .putString("resultado", result.toString())
                .build();

        return Result.success(resultado);
    }
}

