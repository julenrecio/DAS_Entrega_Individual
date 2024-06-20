package com.example.entregaindividual;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

public class AdaptadorListViewPilotos extends BaseAdapter {

    private final LayoutInflater inflater;
    private final List<ItemListView> items;

    // Constructor del adaptador
    public AdaptadorListViewPilotos(Context pContext, List<ItemListView> pItems) {
        // Atributos
        items = pItems;
        inflater = (LayoutInflater) pContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // Obtener el tamaño de la lista
    @Override
    public int getCount() {
        return items.size();
    }

    // Método para obtener un item
    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    // Método para obtener el indice del item
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        // Obtener el elemento i
        ItemListView item = (ItemListView) getItem(i);
        if (view == null) {

            // Inflar el layout
            view = inflater.inflate(R.layout.layout_listview_pilotos, viewGroup, false);
        }

        // Settear la imagen en el ImageView
        ImageView imageView = view.findViewById(R.id.imageViewPilotos);
        imageView.setImageResource(item.getFondo());
        return view;
    }
}