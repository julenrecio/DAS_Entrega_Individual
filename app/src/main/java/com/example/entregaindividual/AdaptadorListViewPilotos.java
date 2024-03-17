package com.example.entregaindividual;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.cardview.widget.CardView;

public class AdaptadorListViewPilotos extends BaseAdapter {

    private Context contexto;
    private LayoutInflater inflater;
    private int[] pilotos;

    public AdaptadorListViewPilotos(Context pContext, int[] pPilotos) {
        contexto = pContext;
        pilotos = pPilotos;
        inflater = (LayoutInflater) contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return pilotos.length;
    }

    @Override
    public Object getItem(int i) {
        return pilotos[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=inflater.inflate(R.layout.layout_listview_pilotos,null);
        //ConstraintLayout layout = (ConstraintLayout) view.findViewById(R.id.layout);
        //layout.setBackgroundResource(pilotos[i]);
        CardView cardView = (CardView) view.findViewById(R.id.cardView);
        cardView.setBackgroundResource(pilotos[i]);
        return view;
    }
}