package co.edu.sena.digilistmobile.digilist;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.edu.sena.digilistmobile.digilist.util.conexiones.ConexionLocal;
import co.edu.sena.digilistmobile.digilist.util.conexiones.RequestsAndResponses;


public class Producto {
    private String referecia, nombre, descripcion;
    private Material material;
    private Tipo tipo;
    RequestsAndResponses requestsAndResponses;
    Context c;

    public Producto(Context c) {
        this.c = c;
    }

    public String getReferecia() {
        return referecia;
    }

    public void setReferecia(String referecia) {
        this.referecia = referecia;
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

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public JSONArray consultarProducto() {
        requestsAndResponses = new RequestsAndResponses(c);
        return requestsAndResponses.getProductos();
    }

    public JSONArray consultarInventario() throws JSONException {
        requestsAndResponses = new RequestsAndResponses(c);
        return requestsAndResponses.getInventario();
    }

    public ArrayList<String> consultarProductos() {
        ConexionLocal conexionLocal = new ConexionLocal(c);
        conexionLocal.abrir();
        String sql = "select product.name,type.name,type.dimension,material.name " +
                "from product " +
                "inner join type on type.idType=product.idType " +
                "inner join material on material.idMaterial=product.idMaterial";
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
        return alist;
    }

    public ArrayList<String> consultarProducto(String criterio, String valor) {
        ConexionLocal conexionLocal = new ConexionLocal(c);
        conexionLocal.abrir();
        String sql = "select product.name,type.name,type.dimension,material.name " +
                "from product " +
                "inner join type on type.idType=product.idType " +
                "inner join material on material.idMaterial=product.idMaterial " +
                "WHERE " + criterio + "='" + valor + "'";
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
        return alist;
    }

    public boolean agregarProducto(int capacidad, String descripcion) {
        requestsAndResponses = new RequestsAndResponses(c);
        requestsAndResponses.postProductos();
        return false;
    }

    public String agregarProducto() throws JSONException {
        JSONArray jsonArray = consultarProducto();
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
            conf += conexionLocal.insert("product", cv);
        }
        return conf;
    }

    public String agregarInventario() throws JSONException {
        JSONArray jsonArray = consultarInventario();
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
            conf += conexionLocal.insert("stock", cv);
        }
        return conf;
    }

    public JSONArray agregarInventario(String name, Float amount) throws JSONException {
        ConexionLocal conexionLocal = new ConexionLocal(c);
        conexionLocal.abrir();
        String sql = "select * " +
                "from stock " +
                "where idProduct=(select idProduct from product where name ='" + name + "')";
        ArrayList<String> alist = new ArrayList<String>();
        Cursor ct = conexionLocal.read(sql);
        JSONObject jsonObject = new JSONObject();
        for (ct.moveToFirst(); !ct.isAfterLast(); ct.moveToNext()) {
            jsonObject.put("idStock", ct.getString(ct.getColumnIndex("idStock")));
            jsonObject.put("idProduct", ct.getString(ct.getColumnIndex("idProduct")));
            jsonObject.put("idStan", ct.getString(ct.getColumnIndex("idStan")));
            jsonObject.put("amount", "" + (ct.getInt(ct.getColumnIndex("amount")) + amount));
            Log.e("put", jsonObject.toString());

        }
        ct.close();
        RequestsAndResponses requestsAndResponses = new RequestsAndResponses(c);
        return requestsAndResponses.putInventario(jsonObject);
    }

    public boolean modificarProducto(String criterio, String terminoAModificar) {
        requestsAndResponses = new RequestsAndResponses(c);
        requestsAndResponses.putProductos();
        return false;
    }

    public boolean eliminarProducto(int identificador) {
        requestsAndResponses = new RequestsAndResponses(c);
        requestsAndResponses.deleteProductos();
        return false;
    }

    public ArrayList<String> consultarInventarios() {

        ConexionLocal conexionLocal = new ConexionLocal(c);
        conexionLocal.abrir();
        String sql = "select product.name,type.name,type.dimension,material.name,stock.amount " +
                "from product " +
                "inner join stock on stock.idProduct=product.idProduct " +
                "inner join type on type.idType=product.idType " +
                "inner join material on material.idMaterial=product.idMaterial";
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
        Log.e("Stock", alist.toString());
        return alist;

    }

}
