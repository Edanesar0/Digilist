package co.edu.sena.digilistmobile.digilist;

import android.database.Cursor;

import co.edu.sena.digilistmobile.digilist.Conexiones.RequestsAndResponses;


public class Material {
    private String nombre, descripcion;
    RequestsAndResponses requestsAndResponses;

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean agregarMaterial(Material material) {

        requestsAndResponses= new RequestsAndResponses();
        requestsAndResponses.postMateriales();
        return false;
    }

    public boolean eliminarMaterial(String nombre) {
        requestsAndResponses= new RequestsAndResponses();
        requestsAndResponses.deleteMateriales();
        return false;
    }

    public boolean modificarMaterial(String criterio, String terminoModificar) {
        requestsAndResponses= new RequestsAndResponses();
        requestsAndResponses.putMateriales();
        return false;
    }

    public Cursor consultarMaterial(String criterio, String terminoBuscar){
    requestsAndResponses= new RequestsAndResponses();
    requestsAndResponses.getMateriales();
    return null;
    }




}
