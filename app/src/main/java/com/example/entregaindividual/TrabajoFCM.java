package com.example.entregaindividual;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.Semaphore;

public class TrabajoFCM extends Worker {

    private String token;

    // Semaforo para resolver el conflicto de asignación del token antes de
    // la obtención del mismo
    private final Semaphore semaphore = new Semaphore(0);

    public TrabajoFCM(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    return;
                }
                // Se recoge el token actual
                token = task.getResult();
                semaphore.release();
            }
        });


        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        // Se hace una peticion HTTP especificando la funcion y el token como parametros
        String direccion = "http://34.88.51.200:81";
        String parametros = "funcion=fcm&token=" + token;
        HttpURLConnection urlConnection = null;
        try {
            // Se establece la conexión
            URL destino = new URL(direccion);
            urlConnection = (HttpURLConnection) destino.openConnection();
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
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

        String result = null;
        if (statusCode == 200) {
            // Si la respuesta es correcta, se procede a recoger el resultado en una variable
            BufferedInputStream inputStream = null;
            try {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            BufferedReader bufferedReader = null;
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            String line;
            result = "";
            while (true) {
                try {
                    if (!((line = bufferedReader.readLine()) != null)) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                result += line;
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        // Se crea un objeto Data para devolver el resultado
        Data resultado = new Data.Builder()
                .putString("resultado", result)
                .build();
        return Result.success(resultado);
    }
}
