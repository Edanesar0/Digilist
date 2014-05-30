package co.edu.sena.digilistmobile.digilist;

import android.database.Cursor;

/**
 * Created by ADMIN on 28/04/2014.
 */
public class Material {
    private String nombre,descripcion;


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

    public boolean agregarMaterial(Material material){
        return false;
    }

    public boolean eliminarMaterial(String nombre){
        return false;
    }
    public boolean modificarMaterial(String criterio,String terminoModificar){
        return false;
    }
    public Cursor consultarMaterial(String criterio,String terminoBuscar){
        return null;
    }
}
