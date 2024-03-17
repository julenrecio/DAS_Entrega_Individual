package com.example.entregaindividual;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.model.TableColumnWeightModel;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class ActivityRecordsCircuitos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records_circuitos);

        Database gestorBD = new Database(this,"Formula 1", null, 1);
        SQLiteDatabase bd = gestorBD.getWritableDatabase();

        // TableView del repositorio:
        // com.github.ISchwarz23:SortableTableView:2.8.1

        TableView tableView = (TableView) findViewById(R.id.tablaDatos);
        String[] cabeceras = {"Piloto", "Circuito"};

        String[][] datos = new String[10][2];
        String nombrePiloto;
        String nombreCircuito;
        Cursor c = bd.rawQuery("SELECT NombreApellidos, Nombre FROM Pilotos inner join Circuitos on Circuitos.CodigoPiloto = Pilotos.Codigo", null);
        int i = 0;
        while (c.moveToNext()){
            nombrePiloto = c.getString(0);
            nombreCircuito = c.getString(1);
            datos[i][0] = nombrePiloto;
            datos[i][1] = nombreCircuito;
            i++;
        }

        TableColumnWeightModel columnModel = new TableColumnWeightModel(2);
        columnModel.setColumnWeight(0, 35);
        columnModel.setColumnWeight(1, 50);
        tableView.setColumnModel(columnModel);
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, cabeceras));
        tableView.setDataAdapter(new SimpleTableDataAdapter(this, datos));

    }

    public void onClickBotonAnadirPiloto (View view) {
        Intent i = new Intent(this, ActivityAnadirPiloto.class);
        startActivity(i);
    }
}