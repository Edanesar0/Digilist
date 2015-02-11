package co.edu.sena.digilistmobile.digilist.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.edu.sena.digilistmobile.digilist.utils.conexiones.ConexionLocal;
import co.edu.sena.digilistmobile.digilist.utils.conexiones.RequestsAndResponses;

public class RolDAO {
    RequestsAndResponses requestsAndResponses;
    Context c;

    public RolDAO(Context c) {
        this.c = c;

    }

    public String agregarRoles() throws JSONException {
        JSONArray jsonArray = consultarRoles("", "");
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
            conf += conexionLocal.insert("role", cv);
        }
        conexionLocal.cerrar();
        return conf;
    }

    public String agregarRoles(JSONArray jsonArray) throws JSONException {
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
            conf += conexionLocal.insert("role", cv);
        }
        conexionLocal.cerrar();
        return conf;
    }


    public JSONArray consultarRoles(String criterio, String terminoABuscar) {
        requestsAndResponses = new RequestsAndResponses(c);
        return requestsAndResponses.getRol();

    }
    public ArrayList<String> consultarRoles() {
        ConexionLocal conexionLocal = new ConexionLocal(c);
        conexionLocal.abrir();
        String sql = "select description " +
                "from Role order by idRol";

        final ArrayList<String> alist = new ArrayList<String>();
        Cursor ct = conexionLocal.read(sql);
        //recorre y agrega
        for (ct.moveToFirst(); !ct.isAfterLast(); ct.moveToNext()) {
            alist.add(ct.getString(0));
        }
        conexionLocal.cerrar();
        return alist;
    }
}
