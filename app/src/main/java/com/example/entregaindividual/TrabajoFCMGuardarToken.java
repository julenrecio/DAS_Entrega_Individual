package com.example.entregaindividual;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.messaging.FirebaseMessaging;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Semaphore;

public class TrabajoFCMGuardarToken extends Worker {

    private String token;

    // Semaforo para resolver el conflicto de asignación del token antes de
    // la obtención del mismo
    private final Semaphore semaphore = new Semaphore(0);

    public TrabajoFCMGuardarToken(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        String newToken = getInputData().getString("token");
        if (newToken == null) {
            FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    return;
                }
                // Se recoge el token actual
                token = task.getResult();
                semaphore.release();
            });


            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        } else {
            token = newToken;
        }

        // Se hace una peticion HTTP especificando la funcion y el token como parametros
        String direccion = "http://34.88.51.200:81";
        String parametros = "funcion=fcmGuardarToken&token=" + token;
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
