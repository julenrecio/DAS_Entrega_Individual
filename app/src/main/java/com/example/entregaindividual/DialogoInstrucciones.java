package com.example.entregaindividual;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DialogoInstrucciones extends DialogFragment {

    // Configuración del dialogo
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder instBuilder = new AlertDialog.Builder(requireActivity());

        // Settear el icono del dialogo
        instBuilder.setIcon(android.R.drawable.ic_dialog_info);

        // Establecer el titulo y mensaje del dialogo
        instBuilder.setTitle("Instrucciones");
        instBuilder.setMessage("Se pueden explorar las funcionalidades con los botones, para volver atras usar el boton del movil");

        // Poner un botón de cancelación del dialogo
        instBuilder.setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.cancel());
        return instBuilder.create();
    }
}

