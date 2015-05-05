package co.edu.sena.digilistmobile.digilist.utils.conexiones;

import android.content.Context;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;


public class RequestsAndResponses {

    ConexionHTTP conexion;
    String URL_connect;
    String URL_connect2;
    Context context;

    public RequestsAndResponses(Context context2) {
        context = context2;
        Properties prop = new Properties();
        String propFileName = "config.properties";
        try {
            InputStream inputStream = context.getAssets().open(propFileName);
            prop.load(inputStream);
            URL_connect = prop.getProperty("URL_connect");
            URL_connect2 = prop.getProperty("URL_connect2");
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public JSONArray getMateriales() {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect2 + "/material/retrieving-records", "GET1", null);
    }

    public JSONArray getTipos() {
        conexion = new ConexionHTTP(context);
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
        postparameters2send.add(new BasicNameValuePair("token", ""));
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(postparameters2send, URL_connect2 + "/type/retrieving-records", "GET1", null);
    }

    public JSONArray getProductos() {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect2 + "/product/retrieving-records", "GET1", null);
    }

    public JSONArray getInventario() throws JSONException {
        conexion = new ConexionHTTP(context);
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
        postparameters2send.add(new BasicNameValuePair("token", ""));
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(postparameters2send, URL_connect2 + "/stock/retrieving-records", "GET1", null);
    }

    public JSONArray getStand() {
        conexion = new ConexionHTTP(context);
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
        postparameters2send.add(new BasicNameValuePair("token", ""));
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(postparameters2send, URL_connect2 + "/stand/retrieving-records", "GET1", null);
        //return conexion.getserverdata(postparameters2send, URL_connect + "/stand.php", "GET1", null);
    }

    public JSONArray getHistoricalSupply() {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect + "/historicalSupply.php", "GET1", null);
    }

    public JSONArray getUsers() {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect2 + "/user/retrieving-records", "GET1", null);
        //return conexion.getserverdata(null, URL_connect + "/users.php", "GET1", null);
    }

    public JSONArray getCities() {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect2 + "/city/retrieving-records", "GET1", null);
        //return conexion.getserverdata(null, URL_connect + "/ciudades.php", "GET1", null);

    }

    public JSONArray getRol() {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect + "/rol.php", "GET1", null);
    }
    public JSONArray getClient() {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect2 + "/client/retrieving-records", "GET1", null);
    }

    public JSONArray postMateriales() {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect2 + "/product/insert-record", "POST2", null);
    }

    public JSONArray postTipos() {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect2 + "/product/insert-record", "POST2", null);
    }

    public JSONArray postProductos(JSONObject productos) {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect2 + "/product/insert-record", "POST2", productos);
    }

    public JSONArray postInventario() {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect2 + "/product/insert-record", "POST2", null);
    }

    public JSONArray postUser(JSONObject productos) {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect2 + "/user/insert-record", "POST2", productos);
    }

    public JSONArray postCiudades(JSONObject productos) {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect2 + "/city/insert-record", "POST2", productos);
    }

    public JSONArray postClient(JSONObject cliente) {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect2 + "/client/insert-record", "POST2", cliente);
    }


    public JSONArray putMateriales() {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect, "PUT1", null);
    }

    public JSONArray putTipos() {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect, "PUT1", null);
    }

    public JSONArray putProductos() {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect, "PUT1", null);
    }

    public JSONArray putInventario(JSONObject datos) {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect2 + "/stock/update-record-by-id", "PUT", datos);
    }

    public JSONArray putUser(JSONObject datos) {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect2 + "/user/update-record-by-id", "PUT", datos);
    }

    public JSONArray putCiudad(JSONObject datos) {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect2 + "/city/update-record-by-id", "PUT", datos);
    }


    public JSONArray deleteMateriales() {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect, "DELETE1", null);
    }

    public JSONArray deleteTipos() {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect, "DELETE1", null);
    }

    public JSONArray deleteProductos() {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect, "DELETE1", null);
    }

    public JSONArray deleteInventario() {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect, "DELETE1", null);
    }

    public JSONArray deleteUser(JSONObject datos) {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect2 + "/user/delete-record-by-id", "DELETE2", datos);
    }

    public JSONArray deleteCity(JSONObject datos) {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect2 + "/city/delete-record-by-id", "DELETE2", datos);
    }

    public JSONArray deleteProducto(JSONObject datos) {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect2 + "/product/delete-record-by-id", "DELETE2", datos);
    }
    public JSONArray deleteStock(JSONObject datos) {
        conexion = new ConexionHTTP(context);
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect2 + "/stock/delete-record-by-id", "DELETE2", datos);
    }
}
