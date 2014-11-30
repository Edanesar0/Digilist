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
        conexion = new ConexionHTTP();
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
        postparameters2send.add(new BasicNameValuePair("token", ""));
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(postparameters2send, URL_connect2 + "/material/retrieving-records", "GET1", null);
    }

    public JSONArray getTipos() {
        conexion = new ConexionHTTP();
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
        postparameters2send.add(new BasicNameValuePair("token", ""));
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(postparameters2send, URL_connect2 + "/type/retrieving-records", "GET1", null);
    }

    public JSONArray getProductos() {
        conexion = new ConexionHTTP();
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect2 + "/product/retrieving-records", "GET1", null);
    }

    public JSONArray getInventario() throws JSONException {
        conexion = new ConexionHTTP();
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
        postparameters2send.add(new BasicNameValuePair("token", ""));
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(postparameters2send, URL_connect2 + "/stock/retrieving-records", "GET1", null).getJSONArray(0);
    }

    public JSONArray getStand() {
        conexion = new ConexionHTTP();
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
        postparameters2send.add(new BasicNameValuePair("token", ""));
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(postparameters2send, URL_connect2 + "/stand/retrieving-records", "GET1", null);
        //return conexion.getserverdata(postparameters2send, URL_connect + "/stand.php", "GET1", null);
    }

    public JSONArray postMateriales() {
        conexion = new ConexionHTTP();
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect2 + "/product/insert-record", "POST2", null);
    }

    public JSONArray postTipos() {
        conexion = new ConexionHTTP();
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect2 + "/product/insert-record", "POST2", null);
    }

    public JSONArray postProductos() {
        conexion = new ConexionHTTP();
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect2 + "/product/insert-record", "POST", null);
    }

    public JSONArray postInventario() {
        conexion = new ConexionHTTP();
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect2 + "/product/insert-record", "POST2", null);
    }

    public JSONArray putMateriales() {
        conexion = new ConexionHTTP();
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect, "PUT1", null);
    }

    public JSONArray putTipos() {
        conexion = new ConexionHTTP();
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect, "PUT1", null);
    }

    public JSONArray putProductos() {
        conexion = new ConexionHTTP();
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect, "PUT1", null);
    }

    public JSONArray putInventario(JSONObject datos) {
        conexion = new ConexionHTTP();
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect2 + "/stock/update-record-by-id", "PUT", datos);
    }

    public JSONArray deleteMateriales() {
        conexion = new ConexionHTTP();
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect, "DELETE1", null);
    }

    public JSONArray deleteTipos() {
        conexion = new ConexionHTTP();
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect, "DELETE1", null);
    }

    public JSONArray deleteProductos() {
        conexion = new ConexionHTTP();
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect, "DELETE1", null);
    }

    public JSONArray deleteInventario() {
        conexion = new ConexionHTTP();
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect, "DELETE1", null);
    }
}
