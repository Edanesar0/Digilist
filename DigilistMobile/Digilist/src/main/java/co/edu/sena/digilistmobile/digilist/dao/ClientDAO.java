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

public class ClientDAO {
    Context c;
    RequestsAndResponses requestsAndResponses;

    public ClientDAO(Context c) {
        this.c = c;
    }

    public JSONArray agregarClientHTTP(JSONObject cliente) throws JSONException {
        ConexionLocal conexionLocal = new ConexionLocal(c);
        conexionLocal.abrir();
        String sql = "SELECT idCity FROM city where description='" + cliente.get("idCity") + "'";
        Cursor ct = conexionLocal.read(sql);
        for (ct.moveToFirst(); !ct.isAfterLast(); ct.moveToNext()) {
            cliente.put("idCity", ct.getString(0));
        }
        conexionLocal.cerrar();
        requestsAndResponses = new RequestsAndResponses(c);
        return requestsAndResponses.postClient(cliente);
    }

    public String agregarClienteLocal() throws JSONException {
        JSONArray jsonArray = consultarClienteHTTP();
        ContentValues cv = new ContentValues();
        ConexionLocal conexionLocal = new ConexionLocal(c);
        String conf = "";
        //jsonArray = jsonArray.getJSONArray(0);
        conexionLocal.abrir();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONArray names = jsonObject.names();
            for (int j = 0; j < names.length(); j++) {
                cv.put(names.getString(j), jsonObject.getString(names.getString(j)));
            }
            conf += conexionLocal.insert("client", cv);
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

    public JSONArray consultarClienteHTTP() throws JSONException {
        requestsAndResponses = new RequestsAndResponses(c);
        return requestsAndResponses.getClient();

    }

    public ArrayList<String> consultarClientesSoloNom() {

        ConexionLocal conexionLocal = new ConexionLocal(c);
        conexionLocal.abrir();
        String sql = "select * " +
                "from client";
        final ArrayList<String> alist = new ArrayList<String>();
        //alist.add(c.getResources().getString(R.string.SeleccioneUno));
        Cursor ct = conexionLocal.read(sql);
        //recorre y agrega
        for (ct.moveToFirst(); !ct.isAfterLast(); ct.moveToNext()) {
            //alist.add(ct.getString(0));
            alist.add(ct.getString(2)+" - "+ct.getString(3));
            //alist.add(ct.getString(3));
            //alist.add(ct.getString(3));
        }
        conexionLocal.cerrar();
        return alist;

    }
    public ArrayList<String> consultarClientes() {

        ConexionLocal conexionLocal = new ConexionLocal(c);
        conexionLocal.abrir();
        String sql = "SELECT `name`,  `phone`,`address`,`description`,`idClient` FROM `client` INNER JOIN city ON client.idCity=city.idCity";
        final ArrayList<String> alist = new ArrayList<String>();
        //alist.add(c.getResources().getString(R.string.SeleccioneUno));
        Cursor ct = conexionLocal.read(sql);
        //recorre y agrega
        for (ct.moveToFirst(); !ct.isAfterLast(); ct.moveToNext()) {
            alist.add(ct.getString(0));
            alist.add(ct.getString(1));
            alist.add(ct.getString(2));
            alist.add(ct.getString(3));
            alist.add(ct.getString(4));
        }
        conexionLocal.cerrar();
        return alist;

    }

    public ArrayList<String> consultarMateriales(String Criterio) {
        ConexionLocal conexionLocal = new ConexionLocal(c);
        conexionLocal.abrir();
        String sql = "select * " +
                "from material where idMaterial like '%" + Criterio + "%'";
        final ArrayList<String> alist = new ArrayList<String>();
        Cursor ct = conexionLocal.read(sql);
        //recorre y agrega
        for (ct.moveToFirst(); !ct.isAfterLast(); ct.moveToNext()) {
            alist.add(ct.getString(2)+" - "+ct.getString(3));
        }
        conexionLocal.cerrar();
        return alist;

    }
    public ArrayList<String> consultarCliente(String criterio, String valor) {
        ConexionLocal conexionLocal = new ConexionLocal(c);
        conexionLocal.abrir();
        String sql = "select * " +
                "from client where  " + criterio + "='" + valor + "'";
        final ArrayList<String> alist = new ArrayList<String>();
        Cursor ct = conexionLocal.read(sql);
        //recorre y agrega
        for (ct.moveToFirst(); !ct.isAfterLast(); ct.moveToNext()) {
            alist.add(ct.getString(0));
            alist.add(ct.getString(2));
            alist.add(ct.getString(3));
        }
        conexionLocal.cerrar();
        return alist;
    }

}
