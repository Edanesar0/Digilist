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

        ContentValues cv2 = new ContentValues();
        cv2.put("idStan","1");
        cv2.put("capacity","10000");
        cv2.put("description","Stan 1");
        Log.e("Stan 1",""+conexionLocal.insert("stan", cv2));
        cv2.put("idStan","2");
        cv2.put("capacity","1000");
        cv2.put("description","Stan 2");
        Log.e("Stan 2",""+conexionLocal.insert("stan", cv2));

        ContentValues cv3 = new ContentValues();
        cv3.put("idStock","1");
        cv3.put("idProduct","1");
        cv3.put("idStan","1");
        cv3.put("amount","50");
        Log.e("Stock 1",""+conexionLocal.insert("stock", cv2));
        cv3.put("idStock","2");
        cv3.put("idProduct","2");
        cv3.put("idStan","2");
        cv3.put("amount","10");
        Log.e("Stock 2",""+conexionLocal.insert("stock", cv2));

        conexionLocal.cerrar();

        return conf;
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

    public ArrayList<String> consultarInventario() {
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
        return alist;

    }

}
