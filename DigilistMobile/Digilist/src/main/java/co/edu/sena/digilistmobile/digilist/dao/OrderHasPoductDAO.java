package co.edu.sena.digilistmobile.digilist.dao;


import android.content.ContentValues;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.edu.sena.digilistmobile.digilist.utils.conexiones.ConexionLocal;
import co.edu.sena.digilistmobile.digilist.utils.conexiones.RequestsAndResponses;

public class OrderHasPoductDAO {
    RequestsAndResponses requestsAndResponses;
    Context c;

    public OrderHasPoductDAO(Context c) {
        this.c = c;
    }

    public JSONArray consultarOrderHasPoductHTTP() {
        requestsAndResponses = new RequestsAndResponses(c);
        return requestsAndResponses.getOrder();
    }

    public String addOrderHasPoductLocal() throws JSONException {
        JSONArray jsonArray = consultarOrderHasPoductHTTP();
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

    public JSONArray addOrderHasPoductHTTP(JSONObject jsonObject) {
        requestsAndResponses = new RequestsAndResponses(c);
        return requestsAndResponses.postOrderHasPoduct(jsonObject);
    }

}
