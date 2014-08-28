package co.edu.sena.digilistmobile.digilist;

import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.edu.sena.digilistmobile.digilist.util.conexiones.ConexionLocal;
import co.edu.sena.digilistmobile.digilist.util.conexiones.RequestsAndResponses;

/**
 * Created by ADMIN on 28/04/2014.
 */
public class Tipo {
    private String nombre, descripcion;
    private int dimencion;
    RequestsAndResponses requestsAndResponses;
    Context c;

    public Tipo(Context c) {
        this.c=c;

    }

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
        requestsAndResponses = new RequestsAndResponses(c);
        requestsAndResponses.postTipos();
        return false;
    }
    public String  agregarTipo() throws JSONException {
        JSONArray jsonArray=consultarTipo("","");
        ContentValues cv= new ContentValues();
        ConexionLocal conexionLocal=new ConexionLocal(c);
        String conf = "";
        conexionLocal.abrir();
        for (int i=0;i<jsonArray.length();i++){
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            JSONArray names=jsonObject.names();
            for (int j=0;j<names.length();j++){
                cv.put(names.getString(j), jsonObject.getString(names.getString(j)));
            }
            conf+= conexionLocal.insert("tipo",cv);
        }
        conexionLocal.cerrar();
        return conf;
    }

    public boolean eliminarTipo(String nombre) {
        requestsAndResponses = new RequestsAndResponses(c);
        requestsAndResponses.deleteTipos();
        return false;
    }

    public boolean modificarTipo(String criterio, String terminoModificar) {
        requestsAndResponses = new RequestsAndResponses(c);
        requestsAndResponses.putTipos();
        return false;
    }

    public JSONArray consultarTipo(String criterio, String terminoBuscar)  {
        requestsAndResponses = new RequestsAndResponses(c);
        return requestsAndResponses.getTipos();

    }
}
