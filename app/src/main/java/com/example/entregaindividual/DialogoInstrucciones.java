package com.example.entregaindividual;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class DialogoInstrucciones extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder instBuilder = new AlertDialog.Builder(getActivity());
        instBuilder.setIcon(android.R.drawable.ic_dialog_info);
        instBuilder.setTitle("Instrucciones");
        instBuilder.setMessage("Se pueden explorar las funcionalidades con los botones, para volver atras usar el boton del movil");
        instBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        return instBuilder.create();
    }
}
