package co.edu.sena.digilistmobile.digilist.dao;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

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
        String sql = "SELECT idUser,names,last_name,phone,address,role.description,user,role.idRol,city.description,user.idCity " +
                "FROM user inner join role on role.idRol=user.idRol " +
                "inner join city on user.idCity=city.idCity " +
                "where idUser=" + idUser + " order by 1";
        final ArrayList<String> alist = new ArrayList<String>();
        Cursor ct = conexionLocal.read(sql);
        //recorre y agrega
        for (ct.moveToFirst(); !ct.isAfterLast(); ct.moveToNext()) {
            alist.add(ct.getString(0));
            alist.add(ct.getString(1));
            alist.add(ct.getString(2));
            alist.add(ct.getString(3));
            alist.add(ct.getString(4));
            alist.add(ct.getString(5));
            alist.add(ct.getString(6));
            alist.add(ct.getString(7));
            alist.add(ct.getString(8));
            alist.add(ct.getString(9));

        }
        conexionLocal.cerrar();
        return alist;

    }


    public JSONArray agregarUsuario(UserVO usuario) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("names", usuario.getNames());
        jsonObject.put("last_name", usuario.getLast_name());
        jsonObject.put("idCity", usuario.getIdCity());
        jsonObject.put("idRol", usuario.getIdRol());
        jsonObject.put("phone", usuario.getPhone());
        jsonObject.put("address", usuario.getAddress());
        jsonObject.put("user", usuario.getUser());
        jsonObject.put("password", usuario.getPass());
        jsonObject.put("remember_token", "123");
        Log.e("", jsonObject.toString());
        RequestsAndResponses requestsAndResponses = new RequestsAndResponses(c);
        return requestsAndResponses.postUser(jsonObject);
    }

    public JSONArray darBajaUsuario(UserVO usuario) throws JSONException {
        RequestsAndResponses requestsAndResponses = new RequestsAndResponses(c);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idUser", usuario.getIdUser());
        return requestsAndResponses.deleteUser(jsonObject);

    }

    public JSONArray modificarUsuario(UserVO usuario) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idUser", usuario.getIdUser());
        jsonObject.put("names", usuario.getNames());
        jsonObject.put("last_name", usuario.getLast_name());
        jsonObject.put("idCity", usuario.getIdCity());
        jsonObject.put("idRol", usuario.getIdRol());
        jsonObject.put("phone", usuario.getPhone());
        jsonObject.put("address", usuario.getAddress());
        jsonObject.put("user", usuario.getUser());
        jsonObject.put("password", "");
        RequestsAndResponses requestsAndResponses = new RequestsAndResponses(c);
        return requestsAndResponses.putUser(jsonObject);
    }
}