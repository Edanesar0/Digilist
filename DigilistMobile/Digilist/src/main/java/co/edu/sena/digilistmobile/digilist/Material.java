package co.edu.sena.digilistmobile.digilist;

import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.edu.sena.digilistmobile.digilist.util.conexiones.ConexionLocal;
import co.edu.sena.digilistmobile.digilist.util.conexiones.RequestsAndResponses;


public class Material {
    private String nombre, descripcion;
    RequestsAndResponses requestsAndResponses;
    Context c;

    public Material(Context c) {
        this.c = c;

    }

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

    public boolean agregarMaterial(Material material, int token) throws JSONException {
        requestsAndResponses = new RequestsAndResponses();
        requestsAndResponses.postMateriales();
        return false;
    }
    public String agregarMaterial() throws JSONException {
        JSONArray jsonArray = consultarMaterial("", "");
        ContentValues cv = new ContentValues();
        ConexionLocal conexionLocal = new ConexionLocal(c);
        String conf = "";
        conexionLocal.abrir();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONArray names = jsonObject.names();
            for (int j = 0; j < names.length(); j++) {
                cv.put(names.getString(j), jsonObject.getString(names.getString(j)));
            }
             conf+= conexionLocal.insert("material", cv);
        }
        conexionLocal.cerrar();

        return conf;
    }

    public boolean eliminarMaterial(String nombre) {
        requestsAndResponses = new RequestsAndResponses();
        requestsAndResponses.deleteMateriales();
        return false;
    }

    public boolean modificarMaterial(String criterio, String terminoModificar) {
        requestsAndResponses = new RequestsAndResponses();
        requestsAndResponses.putMateriales();
        return false;
    }

    public JSONArray consultarMaterial(String criterio, String terminoBuscar) throws JSONException {
        requestsAndResponses = new RequestsAndResponses();
        return requestsAndResponses.getMateriales();

    }


}
