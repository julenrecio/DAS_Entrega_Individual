package com.example.entregaindividual;

public class ItemListView {

    // Atributos de clase
    private final int fondo;
    private final String nombre;
    private final int puntos;

    // Constructor
    public ItemListView(int fondo, String nombre, int puntos) {
        this.nombre = nombre;
        this.puntos = puntos;
        this.fondo = fondo;
    }

    // Métodos getters para obtener los atributos
    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getFondo() {
        return fondo;
    }
}
