package co.edu.sena.digilistmobile.digilist;

import android.database.Cursor;

import co.edu.sena.digilistmobile.digilist.Conexiones.RequestsAndResponses;

/**
 * Created by ADMIN on 28/04/2014.
 */
public class Tipo {
    private String nombre, descripcion;
    private int dimencion;
    RequestsAndResponses requestsAndResponses;

    public int getDimencion() {
        return dimencion;
    }

    public void setDimencion(int dimencion) {
        this.dimencion = dimencion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean agregarTipo(Tipo tipo) {
        requestsAndResponses= new RequestsAndResponses();
        requestsAndResponses.postTipos();
        return false;
    }

    public boolean eliminarTipo(String nombre) {
        requestsAndResponses= new RequestsAndResponses();
        requestsAndResponses.deleteTipos();
        return false;
    }

    public boolean modificarTipo(String criterio, String terminoModificar) {
        requestsAndResponses= new RequestsAndResponses();
        requestsAndResponses.putTipos();
        return false;
    }

    public Cursor consultarTipo(String criterio, String terminoBuscar) {
        requestsAndResponses= new RequestsAndResponses();
        requestsAndResponses.getTipos();
        return null;
    }
}
