package co.edu.sena.digilistmobile.digilist;


import android.database.Cursor;

import co.edu.sena.digilistmobile.digilist.Conexiones.RequestsAndResponses;

public class Producto {
    private String referecia, nombre, descripcion;
    private Material material;
    private Tipo tipo;
    RequestsAndResponses requestsAndResponses;

    public String getReferecia() {
        return referecia;
    }

    public void setReferecia(String referecia) {
        this.referecia = referecia;
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

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Cursor consultarProducto(String critertio, String terminoBuscar) {
        requestsAndResponses= new RequestsAndResponses();
        requestsAndResponses.getProductos();
        return null;
    }
    public boolean agregarProducto(int capacidad, String descripcion) {
        requestsAndResponses= new RequestsAndResponses();
        requestsAndResponses.postProductos();
        return false;
    }

    public boolean modificarProducto(String criterio, String terminoAModificar) {
        requestsAndResponses= new RequestsAndResponses();
        requestsAndResponses.putProductos();
        return false;
    }

    public boolean eliminarProducto(int identificador) {
        requestsAndResponses= new RequestsAndResponses();
        requestsAndResponses.deleteProductos();
        return false;
    }

}
