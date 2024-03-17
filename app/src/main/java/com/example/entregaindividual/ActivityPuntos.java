package com.example.entregaindividual;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class ActivityPuntos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int[] escuderias = {R.drawable.redbull, R.drawable.mercedes, R.drawable.ferrari, R.drawable.mclaren, R.drawable.astonmartin, R.drawable.alpine, R.drawable.williams, R.drawable.alphatauri, R.drawable.alfaromeo, R.drawable.haas};
        String[] nombres = {"Red Bull", "Mercedes", "Ferrari", "McLaren", "Aston Martin", "Alpine", "Williams", "Aplha Tauri", "Alfa Romeo", "Haas"};
        int[] puntos = {860, 409, 406, 302, 280, 120, 28, 25, 16, 12};

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puntos);

        ListView lista = (ListView) findViewById(R.id.lista);
        AdaptadorListViewPuntos adaptador = new AdaptadorListViewPuntos(getApplicationContext(), nombres, escuderias, puntos);
        lista.setAdapter(adaptador);
    }
}