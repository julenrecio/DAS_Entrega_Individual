package com.example.entregaindividual;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

public class FragmentPilotosInfo extends Fragment {

    private static final String arg_nombre = "nombre";
    private static final String arg_puntos = "puntos";

    // Reimplementar el método con un item como parámetro
    public static FragmentPilotosInfo newInstance(ItemListView item) {
        FragmentPilotosInfo fragment = new FragmentPilotosInfo();
        Bundle args = new Bundle();

        // poner los datos en un Bundle
        args.putString(arg_nombre, item.getNombre());
        args.putInt(arg_puntos, item.getPuntos());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout
        View view = inflater.inflate(R.layout.fragment_info_listview, container, false);

        TextView nombreTextView = view.findViewById(R.id.nombreEscuderia);
        TextView puntosTextView = view.findViewById(R.id.puntosEscuderia);

        // Obtener los datos del Bundle
        if (getArguments() != null) {
            String nombre = getArguments().getString(arg_nombre);
            int puntos = getArguments().getInt(arg_puntos);

            // Settear los datos en los TextView
            nombreTextView.setText(nombre);
            puntosTextView.setText(String.valueOf(puntos));
        }
        return view;
    }
}
