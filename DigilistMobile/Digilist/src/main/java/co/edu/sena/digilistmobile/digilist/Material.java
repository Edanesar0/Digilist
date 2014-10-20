package co.edu.sena.digilistmobile.digilist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
        requestsAndResponses = new RequestsAndResponses(c);
        requestsAndResponses.postMateriales();
        return false;
    }

    public String agregarMaterial() throws JSONException {
        JSONArray jsonArray = consultarMaterial("", "");
        ContentValues cv = new ContentValues();
        ConexionLocal conexionLocal = new ConexionLocal(c);
        String conf = "";
        jsonArray = jsonArray.getJSONArray(0);
        conexionLocal.abrir();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONArray names = jsonObject.names();
            for (int j = 0; j < names.length(); j++) {
                cv.put(names.getString(j), jsonObject.getString(names.getString(j)));
            }
            conf += conexionLocal.insert("material", cv);
        }
        conexionLocal.cerrar();

        return conf;
    }

    public boolean eliminarMaterial(String nombre) {
        requestsAndResponses = new RequestsAndResponses(c);
        requestsAndResponses.deleteMateriales();
        return false;
    }

    public boolean modificarMaterial(String criterio, String terminoModificar) {
        requestsAndResponses = new RequestsAndResponses(c);
        requestsAndResponses.putMateriales();
        return false;
    }

    public JSONArray consultarMaterial(String criterio, String terminoBuscar) throws JSONException {
        requestsAndResponses = new RequestsAndResponses(c);
        return requestsAndResponses.getMateriales();

    }

    public ArrayList<String> consultarMateriales() {
        requestsAndResponses = new RequestsAndResponses(c);
        ConexionLocal conexionLocal = new ConexionLocal(c);
        conexionLocal.abrir();
        String sql = "select * " +
                "from material";
        final ArrayList<String> alist = new ArrayList<String>();
        alist.add("Seleccione uno");
        Cursor ct = conexionLocal.read(sql);
        //recorre y agrega
        for (ct.moveToFirst(); !ct.isAfterLast(); ct.moveToNext()) {
            //alist.add(ct.getString(0));
            alist.add(ct.getString(1));
            //alist.add(ct.getString(2));
            //alist.add(ct.getString(3));
        }
        conexionLocal.cerrar();
        return alist;

    }


}
