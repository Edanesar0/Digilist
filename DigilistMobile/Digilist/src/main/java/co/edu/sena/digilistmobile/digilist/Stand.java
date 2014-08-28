package co.edu.sena.digilistmobile.digilist;


import android.content.Context;

import co.edu.sena.digilistmobile.digilist.util.conexiones.RequestsAndResponses;

public class Stand {
    private int capacidad;
    private String descripcion;
    RequestsAndResponses requestsAndResponses;
    Context c;

    public Stand(Context c) {
        this.c = c;

    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void revisarStand(int identificador) {
        requestsAndResponses = new RequestsAndResponses(c);
        //requestsAndResponses.getMateriales();
    }

    public boolean agregarStan(int capacidad, String descripcion) {

        requestsAndResponses = new RequestsAndResponses(c);
        // requestsAndResponses.postMateriales();
        return false;
    }

    public boolean modificarStan(String criterio, String terminoAModificar) {

        requestsAndResponses = new RequestsAndResponses(c);
        // requestsAndResponses.putMateriales();
        return false;
    }

    public boolean eliminarStand(int identificador) {

        requestsAndResponses = new RequestsAndResponses(c);
        //requestsAndResponses.deleteMateriales();
        return false;
    }

}
