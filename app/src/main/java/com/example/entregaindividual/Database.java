package com.example.entregaindividual;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE Circuitos ('Codigo' INTEGER PRIMARY KEY " +
                "AUTOINCREMENT NOT NULL,'Nombre' VARCHAR(255), 'Tipo' VARCHAR(255), 'Abrasividad' VARCHAR(255), " +
                "'Longitud(KM)' INTEGER, 'Inauguracion' INTEGER, 'Vueltas' INTEGER, " +
                "'DistanciaDeCarrera(KM)' REAL, 'TiempoRecord' TEXT, 'CodigoPiloto' INTEGER, " +
                "FOREIGN KEY ('CodigoPiloto') REFERENCES Pilotos ('Codigo'))");

        sqLiteDatabase.execSQL("CREATE TABLE Pilotos ('Codigo' INTEGER PRIMARY KEY " +
                "AUTOINCREMENT NOT NULL, 'NombreApellidos' VARCHAR(255), 'PasoPorCurva' INTEGER, " +
                "'Reacción' INTEGER, 'Defensa' INTEGER, 'Precisión' INTEGER, 'Valoracion' INTEGER)");

        sqLiteDatabase.execSQL("INSERT INTO Pilotos VALUES (1, 'Max Verstappen', 95, 88, 97, 85, 91)");
        sqLiteDatabase.execSQL("INSERT INTO Pilotos VALUES (2, 'Fernando Alonso', 90, 86, 91, 88, 90)");
        sqLiteDatabase.execSQL("INSERT INTO Pilotos VALUES (3, 'Lewis Hamilton', 93, 86, 94, 83, 90)");
        sqLiteDatabase.execSQL("INSERT INTO Pilotos VALUES (4, 'Charles Leclerc', 94, 86, 91, 86, 89)");
        sqLiteDatabase.execSQL("INSERT INTO Pilotos VALUES (5, 'Sergio Perez', 88, 93, 93, 87, 88)");
        sqLiteDatabase.execSQL("INSERT INTO Pilotos VALUES (6, 'Lando Norris', 89, 84, 86, 79, 87)");
        sqLiteDatabase.execSQL("INSERT INTO Pilotos VALUES (7, 'George Russell', 85, 87, 86, 92, 87)");
        sqLiteDatabase.execSQL("INSERT INTO Pilotos VALUES (8, 'Carlos Sainz', 90, 86, 93, 85, 87)");
        sqLiteDatabase.execSQL("INSERT INTO Pilotos VALUES (9, 'Valtteri Bottas', 87, 83, 88, 94, 86)");
        sqLiteDatabase.execSQL("INSERT INTO Pilotos VALUES (10, 'Pierre Gasly', 86, 82, 84, 79, 86)");

        sqLiteDatabase.execSQL("INSERT INTO Circuitos VALUES (1, 'Bahrain International Circuit', 'Power', 'Medium', 5412, 2004, 57, 308.238, '1:31.447', 1)");
        sqLiteDatabase.execSQL("INSERT INTO Circuitos VALUES (2, 'Jeddah Corniche Circuit', 'Balanced', 'Medium', 6174, 2021, 50, 308.450, '1:30.733', 3)");
        sqLiteDatabase.execSQL("INSERT INTO Circuitos VALUES (3, 'Albert Park Circuit', 'Balanced', 'Medium', 5278, 1996, 58, 306.124, '1:20.260', 4)");
        sqLiteDatabase.execSQL("INSERT INTO Circuitos VALUES (4, 'Baku City Circuit', 'Power', 'Low', 6003, 2016, 51, 306.049, '1:43.009', 4)");
        sqLiteDatabase.execSQL("INSERT INTO Circuitos VALUES (5, 'Miami International Autodrome', 'Balanced', 'Medium', 5412, 2022, 57, 308.326, '1:31.361', 1)");
        sqLiteDatabase.execSQL("INSERT INTO Circuitos VALUES (6, 'Autodromo Enzo E Dino Ferrari', 'Balanced', 'Medium', 4909, 1980, 63, 309.049, '1:15.484', 3)");
        sqLiteDatabase.execSQL("INSERT INTO Circuitos VALUES (7, 'Circuit De Monaco', 'Downforce', 'Medium', 3337, 1950, 78, 260.286, '1:12.908', 3)");
        sqLiteDatabase.execSQL("INSERT INTO Circuitos VALUES (8, 'Circuit De Barcelona-Catalunya', 'Downforce', 'High', 4675, 1991, 66, 308.424, '1:14.637', 5)");
        sqLiteDatabase.execSQL("INSERT INTO Circuitos VALUES (9, 'Circuit Gilles-Villeneuve', 'Balanced', 'Medium', 4361, 1978, 70, 305.270, '1:13.078', 9)");
        sqLiteDatabase.execSQL("INSERT INTO Circuitos VALUES (10, 'Red Bull Ring', 'Balanced', 'Medium', 4318, 1970, 71, 306.452, '1:05.618', 8)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
