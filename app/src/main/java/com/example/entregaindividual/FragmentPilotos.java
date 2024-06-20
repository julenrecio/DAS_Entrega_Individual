package com.example.entregaindividual;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.ListFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FragmentPilotos extends ListFragment {

    private ListenerDelFragment elListener;

    // Interfaz que se implementa en la Actividad que hace uso del Fragment
    public interface ListenerDelFragment {
        void seleccionarElemento(ItemListView item);
    }

    // Definir el enlace entre la actividad y el contexto del fragment
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            elListener = (ListenerDelFragment) context;
        }
        catch (ClassCastException e){
            throw new ClassCastException("La clase " + context + "debe implementar listenerDelFragment");
        }
    }

    // Crear los items con la información
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<ItemListView> items = new ArrayList<>();
        int[] fondosPilotos = {R.drawable.redbull_pilotos, R.drawable.mercedes_pilotos, R.drawable.ferrari_pilotos, R.drawable.mclaren_pilotos, R.drawable.astonmartin_pilotos, R.drawable.alpine_pilotos, R.drawable.williams_pilotos, R.drawable.alphatauri_pilotos, R.drawable.alfaromeo_pilotos};
        String[] nombres = {"Red Bull", "Mercedes", "Ferrari", "McLaren", "Aston Martin", "Alpine", "Williams", "Aplha Tauri", "Alfa Romeo"};
        int[] puntos = {860, 409, 406, 302, 280, 120, 28, 25, 16};

        for (int i=0; i<fondosPilotos.length; i++) {
            items.add(new ItemListView(fondosPilotos[i], nombres[i], puntos[i]));
        }

        // Establecer el adaptador del ListView
        AdaptadorListViewPilotos adaptador = new AdaptadorListViewPilotos(requireContext(), items);
        setListAdapter(adaptador);
    }

    // Inflar el layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_listview, container, false);
    }

    // Gestionar el clic del elemento
    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        ItemListView item = (ItemListView) Objects.requireNonNull(getListAdapter()).getItem(position);
        elListener.seleccionarElemento(item);
    }
}
