package com.example.entregaindividual;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

public class ActivityPilotos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        int[] fondosPilotos = {R.drawable.redbull_pilotos, R.drawable.ferrari_pilotos, R.drawable.alfaromeo_pilotos, R.drawable.alphatauri_pilotos, R.drawable.alpine_pilotos, R.drawable.astonmartin_pilotos, R.drawable.williams_pilotos, R.drawable.mercedes_pilotos, R.drawable.mclaren_pilotos, R.drawable.haas_pilotos};

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilotos);

        ListView lista = (ListView) findViewById(R.id.lista);
        AdaptadorListViewPilotos adaptador = new AdaptadorListViewPilotos(getApplicationContext(), fondosPilotos);
        lista.setAdapter(adaptador);
    }
}