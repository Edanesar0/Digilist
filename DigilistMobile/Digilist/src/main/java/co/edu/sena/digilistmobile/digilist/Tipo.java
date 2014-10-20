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

/**
 * Created by ADMIN on 28/04/2014.
 */
public class Tipo {
    private String nombre, descripcion;
    private int dimencion;
    RequestsAndResponses requestsAndResponses;
    Context c;

    public Tipo(Context c) {
        this.c = c;

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

    public String agregarTipo() throws JSONException {
        JSONArray jsonArray = consultarTipo("", "");
        jsonArray = jsonArray.getJSONArray(0);
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
            conf += conexionLocal.insert("type", cv);
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

    public JSONArray consultarTipo(String criterio, String terminoBuscar) {
        requestsAndResponses = new RequestsAndResponses(c);
        return requestsAndResponses.getTipos();

    }

    public ArrayList<String> consultarTipos() {
        requestsAndResponses = new RequestsAndResponses(c);
        ConexionLocal conexionLocal = new ConexionLocal(c);
        conexionLocal.abrir();
        String sql = "select * " +
                "from type group by name order by name";
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

    public ArrayList<String> consultarTiposTamanio(String tipo) {
        requestsAndResponses = new RequestsAndResponses(c);
        ConexionLocal conexionLocal = new ConexionLocal(c);
        conexionLocal.abrir();
        String sql = "select * " +
                "from type where name like '%" + tipo + "%'";
        final ArrayList<String> alist = new ArrayList<String>();
        alist.add("Seleccione uno");
        Cursor ct = conexionLocal.read(sql);
        //recorre y agrega
        for (ct.moveToFirst(); !ct.isAfterLast(); ct.moveToNext()) {
            //alist.add(ct.getString(0));
            alist.add(ct.getString(3));
            //alist.add(ct.getString(2));
            //alist.add(ct.getString(3));
        }
        conexionLocal.cerrar();
        return alist;

    }
}
