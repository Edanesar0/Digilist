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

public class ProductDAO {
    RequestsAndResponses requestsAndResponses;
    Context c;

    public ProductDAO(Context c) {
        this.c = c;
    }

    public JSONArray consultarProductoHTTP() {
        requestsAndResponses = new RequestsAndResponses(c);
        return requestsAndResponses.getProductos();
    }

    public JSONArray consultarInventarioHTTP() throws JSONException {
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

    public JSONArray agregarProducto(ProductVO produc) {
        ConexionLocal conexionLocal = new ConexionLocal(c);
        conexionLocal.abrir();
        String sql = "select idMaterial from material where name ='" + produc.getIdMaterial() + "'";
        JSONObject jsonObject = new JSONObject();
        try {
            Cursor ct = conexionLocal.read(sql);
            //recorre y agrega
            ct.moveToFirst();
            jsonObject.put("idMaterial", ct.getString(0));
            jsonObject.put("name", produc.getName());
            jsonObject.put("reference", produc.getReference());
            jsonObject.put("description", produc.getName());
            jsonObject.put("price", "0");
            sql = "select idType from type where name ='" + produc.getIdType() + "' and dimension='" + produc.getTamanio() + "'";
            Cursor ct2 = conexionLocal.read(sql);
            ct2.moveToFirst();
            jsonObject.put("idType", ct2.getString(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        conexionLocal.cerrar();
        requestsAndResponses = new RequestsAndResponses(c);
        return requestsAndResponses.postProductos(jsonObject);
    }

    public String agregarProducto() throws JSONException {
        JSONArray jsonArray = consultarProductoHTTP();
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
            conf += conexionLocal.insert("product", cv);
        }
        return conf;
    }

    public String agregarInventario() throws JSONException {
        JSONArray jsonArray = consultarInventarioHTTP();
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
        String sql = "select product.name,type.name,type.dimension,material.name,stock.amount,product.idProduct " +
                "from product " +
                "inner join stock on stock.idProduct=product.idProduct " +
                "inner join type on type.idType=product.idType " +
                "inner join material on material.idMaterial=product.idMaterial " +
                "where stock.amount>0";
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
        }
        conexionLocal.cerrar();

        return alist;

    }

    public ArrayList<String> consultarInventarioGraficas(String criterio) {

        ConexionLocal conexionLocal = new ConexionLocal(c);
        conexionLocal.abrir();
        String sql = "select " + criterio + ",sum(stock.amount)" +
                "from product " +
                "inner join stock on stock.idProduct=product.idProduct " +
                "inner join type on type.idType=product.idType " +
                "inner join material on material.idMaterial=product.idMaterial " +
                "where stock.amount>0 group by " + criterio;
        final ArrayList<String> alist = new ArrayList<String>();
        Cursor ct = conexionLocal.read(sql);
        //recorre y agrega
        for (ct.moveToFirst(); !ct.isAfterLast(); ct.moveToNext()) {
            alist.add(ct.getString(0));
            alist.add(ct.getString(1));

        }
        conexionLocal.cerrar();

        return alist;

    }
}
