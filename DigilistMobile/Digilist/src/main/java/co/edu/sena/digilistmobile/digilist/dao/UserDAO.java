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
import co.edu.sena.digilistmobile.digilist.vo.UserVO;

public class UserDAO {
    RequestsAndResponses requestsAndResponses;
    Context c;

    public UserDAO(Context c) {
        this.c = c;

    }

    public String agregarUsuarios() throws JSONException {
        JSONArray jsonArray = consultarUsuario();
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
            conf += conexionLocal.insert("user", cv);
        }
        conexionLocal.cerrar();
        return conf;
    }

    public JSONArray consultarUsuario() {
        requestsAndResponses = new RequestsAndResponses(c);
        return requestsAndResponses.getUsers();

    }

    public ArrayList<String> consultarUsuarios() {
        ConexionLocal conexionLocal = new ConexionLocal(c);
        conexionLocal.abrir();
        String sql = "SELECT idUser,names,last_name,user,description FROM user inner join role on role.idRol=user.idRol order by 1";
        final ArrayList<String> alist = new ArrayList<String>();
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

    public ArrayList<String> consultarUsuario(String idUser) {
        ConexionLocal conexionLocal = new ConexionLocal(c);
        conexionLocal.abrir();
        String sql = "SELECT idUser,names,last_name,user,description FROM user inner join role on role.idRol=user.idRol where idUser=" + " order by 1";
        final ArrayList<String> alist = new ArrayList<String>();
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


    public boolean agregarUsuario(UserVO Usuario) {

        return false;
    }

    public boolean darBajaUsuario(String identificacion) {

        return false;
    }

    public boolean modificarUsuario(String criterio, String terminoModificar) {

        return false;
    }
}