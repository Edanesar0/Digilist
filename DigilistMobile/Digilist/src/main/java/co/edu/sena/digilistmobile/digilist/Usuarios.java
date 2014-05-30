package co.edu.sena.digilistmobile.digilist;

import android.database.Cursor;


public class Usuarios {
    private String nombre;
    private String apellido;
    private String ciudad;

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    private String identificacion;
    private int telefono;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public Cursor consultarUsuario(String criterio,String terminoABuscar){

        return null;
    }
    public boolean agregarUsuario(Usuarios Usuario){

        return false;
    }
    public boolean darBajaUsuario(String identificacion){

        return false;
    }
    public boolean modificarUsuario(String criterio,String terminoModificar){

        return false;
    }
}
