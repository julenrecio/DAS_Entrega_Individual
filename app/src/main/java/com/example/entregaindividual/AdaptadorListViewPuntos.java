package com.example.entregaindividual;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

public class AdaptadorListViewPuntos extends BaseAdapter {

    private Context contexto;
    private LayoutInflater inflater;
    private String[] datos;
    private int[] imagenes;
    private int[] puntuaciones;

    public AdaptadorListViewPuntos(Context pContext, String[] pDatos, int[] pImagenes, int[] pPuntuaciones) {
        contexto = pContext;
        datos = pDatos;
        imagenes = pImagenes;
        puntuaciones = pPuntuaciones;
        inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return datos.length;
    }

    @Override
    public Object getItem(int i) {
        return datos[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=inflater.inflate(R.layout.layout_listview_puntos,null);
        TextView nombre= (TextView) view.findViewById(R.id.nombre);
        ImageView img=(ImageView) view.findViewById(R.id.imagen);
        TextView puntos= (TextView) view.findViewById(R.id.puntos);
        ConstraintLayout layout = (ConstraintLayout) view.findViewById(R.id.layout);
        nombre.setText(datos[i]);
        img.setImageResource(imagenes[i]);
        puntos.setText(String.valueOf(puntuaciones[i]));
        return view;
    }
}