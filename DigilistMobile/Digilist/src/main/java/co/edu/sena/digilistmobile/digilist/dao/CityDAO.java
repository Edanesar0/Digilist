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
import co.edu.sena.digilistmobile.digilist.vo.ProductVO;

public class CityDAO {
    RequestsAndResponses requestsAndResponses;
    Context c;

    public CityDAO(Context c) {
        this.c = c;

    }

    public String agregarCiudadesLocal() throws JSONException {
        JSONArray jsonArray = consultarCiudadesHTTP("", "");
//        jsonArray = jsonArray.getJSONArray(0);
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
            conf += conexionLocal.insert("city", cv);
        }
        conexionLocal.cerrar();
        return conf;
    }

    public String agregarCiudades(JSONArray jsonArray) throws JSONException {

//        jsonArray = jsonArray.getJSONArray(0);
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
            conf += conexionLocal.insert("city", cv);
        }
        conexionLocal.cerrar();
        return conf;
    }

    public JSONArray consultarCiudadesHTTP(String criterio, String terminoABuscar) {
        requestsAndResponses = new RequestsAndResponses(c);
        return requestsAndResponses.getCities();

    }

    public ArrayList<String> consultarCiudades() {
        ConexionLocal conexionLocal = new ConexionLocal(c);
        conexionLocal.abrir();
        String sql = "select description " +
                "from city order by idcity";

        final ArrayList<String> alist = new ArrayList<String>();
        Cursor ct = conexionLocal.read(sql);
        //recorre y agrega
        for (ct.moveToFirst(); !ct.isAfterLast(); ct.moveToNext()) {
            alist.add(ct.getString(0));
        }
        conexionLocal.cerrar();
        return alist;
    }

    public JSONArray agregarCiudadesHTTP(String city) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("description", city);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        requestsAndResponses = new RequestsAndResponses(c);
        return requestsAndResponses.postCiudades(jsonObject);
    }

    public JSONArray editarCiudadesHTTP(String cityOld, String city) {
        JSONObject jsonObject = new JSONObject();
        ConexionLocal conexionLocal = new ConexionLocal(c);
        conexionLocal.abrir();
        String sql = "select idCity from city where description ='" + cityOld + "'";
        try {
            Cursor ct = conexionLocal.read(sql);
            //recorre y agrega
            ct.moveToFirst();
            jsonObject.put("idCity", ct.getString(0));
            jsonObject.put("description", city);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        conexionLocal.cerrar();
        requestsAndResponses = new RequestsAndResponses(c);
        return requestsAndResponses.putCiudad(jsonObject);
    }

    public JSONArray darBajaCiudad(String idCity) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        ConexionLocal conexionLocal = new ConexionLocal(c);
        conexionLocal.abrir();
        String sql = "SELECT idCity FROM city where description='" + idCity + "'";
        Cursor ct = conexionLocal.read(sql);
        for (ct.moveToFirst(); !ct.isAfterLast(); ct.moveToNext()) {
            jsonObject.put("idCity", ct.getString(0));
        }
        RequestsAndResponses requestsAndResponses = new RequestsAndResponses(c);
        return requestsAndResponses.deleteCity(jsonObject);

    }
}
