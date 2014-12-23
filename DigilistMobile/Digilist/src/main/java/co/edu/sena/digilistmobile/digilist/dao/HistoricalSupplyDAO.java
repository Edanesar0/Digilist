package co.edu.sena.digilistmobile.digilist.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import co.edu.sena.digilistmobile.digilist.utils.conexiones.ConexionLocal;
import co.edu.sena.digilistmobile.digilist.utils.conexiones.RequestsAndResponses;

/**
 * Created by ADMIN on 23/12/2014.
 */
public class HistoricalSupplyDAO {
    RequestsAndResponses requestsAndResponses;
    Context c;

    public HistoricalSupplyDAO(Context c) {
        this.c = c;
    }

    public JSONArray consultarHistoricoHTTP() {
        requestsAndResponses = new RequestsAndResponses(c);
        return requestsAndResponses.getHistoricalSupply();
    }

    public String agregarHistorico() throws Exception {
        JSONArray jsonArray = consultarHistoricoHTTP();
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
            conf += conexionLocal.insert("historicalSupply", cv);
        }
        return conf;
    }

    public ArrayList<String> consultarHistorico(String product) {

        ConexionLocal conexionLocal = new ConexionLocal(c);
        conexionLocal.abrir();
        String sql = "select date,previousAmount,newAmount,description " +
                "from  historicalSupply " +
                "where idProduct=" + product;
        final ArrayList<String> alist = new ArrayList<String>();
        Cursor ct = conexionLocal.read(sql);
        //recorre y agrega
        for (ct.moveToFirst(); !ct.isAfterLast(); ct.moveToNext()) {
            alist.add(ct.getString(0));
            alist.add(ct.getString(1));
            alist.add(ct.getString(2));
            alist.add(ct.getString(3));

        }
        conexionLocal.cerrar();
        Log.e("Stock", alist.toString());
        return alist;

    }


}
