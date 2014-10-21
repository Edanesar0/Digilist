package co.edu.sena.digilistmobile.digilist;


import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.edu.sena.digilistmobile.digilist.util.conexiones.ConexionLocal;
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

    public JSONArray revisarStand(int identificador) {
        requestsAndResponses = new RequestsAndResponses(c);
        return requestsAndResponses.getStand();
    }

    public String agregarStand() throws JSONException {
        JSONArray jsonArray = revisarStand(0);
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
            conf += conexionLocal.insert("stan", cv);
        }
        return conf;
    }

    public boolean agregarStand(int capacidad, String descripcion) {

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
