package co.edu.sena.digilistmobile.digilist.dao;

import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public JSONArray consultarRoles(String criterio, String terminoABuscar) {
        requestsAndResponses = new RequestsAndResponses(c);
        return requestsAndResponses.getRol();

    }
}
