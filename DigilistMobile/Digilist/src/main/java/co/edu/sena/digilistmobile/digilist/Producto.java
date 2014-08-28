package co.edu.sena.digilistmobile.digilist;


import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.edu.sena.digilistmobile.digilist.util.conexiones.ConexionLocal;
import co.edu.sena.digilistmobile.digilist.util.conexiones.RequestsAndResponses;


public class Producto {
    private String referecia, nombre, descripcion;
    private Material material;
    private Tipo tipo;
    RequestsAndResponses requestsAndResponses;
    Context c;

    public Producto(Context c) {
        this.c = c;
    }

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

    public JSONArray consultarProducto(String critertio, String terminoBuscar) {
        requestsAndResponses = new RequestsAndResponses(c);
        return requestsAndResponses.getProductos();
    }

    public ArrayList<String> consultarProducto() {
        ConexionLocal conexionLocal = new ConexionLocal(c);
        conexionLocal.abrir();
        String[] datos = new String[]{"Nombre"};
        ArrayList<String> producto = conexionLocal.readProducto("producto");
        conexionLocal.cerrar();
        return producto;
    }

    public boolean agregarProducto(int capacidad, String descripcion) {
        requestsAndResponses = new RequestsAndResponses(c);
        requestsAndResponses.postProductos();
        return false;
    }

    public String agregarProducto() throws JSONException {
        JSONArray jsonArray = consultarProducto("", "");
        ContentValues cv = new ContentValues();
        ConexionLocal conexionLocal = new ConexionLocal(c);
        conexionLocal.abrir();
        String conf = "";
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONArray names = jsonObject.names();
            for (int j = 0; j < names.length(); j++) {
                cv.put(names.getString(j), jsonObject.getString(names.getString(j)));
            }
            conf += conexionLocal.insert("producto", cv);
        }
        conexionLocal.cerrar();

        return conf;
    }

    public boolean modificarProducto(String criterio, String terminoAModificar) {
        requestsAndResponses = new RequestsAndResponses(c);
        requestsAndResponses.putProductos();
        return false;
    }

    public boolean eliminarProducto(int identificador) {
        requestsAndResponses = new RequestsAndResponses(c);
        requestsAndResponses.deleteProductos();
        return false;
    }

}
