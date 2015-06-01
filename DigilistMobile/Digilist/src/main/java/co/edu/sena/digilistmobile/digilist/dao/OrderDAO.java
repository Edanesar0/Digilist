package co.edu.sena.digilistmobile.digilist.dao;


import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.edu.sena.digilistmobile.digilist.utils.conexiones.ConexionLocal;
import co.edu.sena.digilistmobile.digilist.utils.conexiones.RequestsAndResponses;

public class OrderDAO {
    RequestsAndResponses requestsAndResponses;
    Context c;

    public OrderDAO(Context c) {
        this.c = c;
    }

    public JSONArray consultarOrderHTTP() {
        requestsAndResponses = new RequestsAndResponses(c);
        return requestsAndResponses.getOrder();
    }

    public String addOrderLocal() throws JSONException {
        JSONArray jsonArray = consultarOrderHTTP();
        //jsonArray = jsonArray.getJSONArray(0);
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
            conf += conexionLocal.insert("`order`", cv);
        }
        return conf;
    }

    public JSONArray addOrderHTTP(JSONObject jsonObject) {
        requestsAndResponses = new RequestsAndResponses(c);
        return requestsAndResponses.postOrder(jsonObject);
    }

}
