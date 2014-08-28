package co.edu.sena.digilistmobile.digilist.util.conexiones;

import android.content.Context;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;


public class RequestsAndResponses {

    Conexion conexion;
    String URL_connect;
    Context context;

    public RequestsAndResponses(Context context2) {
        context = context2;
        Properties prop = new Properties();
        String propFileName = "config.properties";
        try {
            InputStream inputStream = context.getAssets().open(propFileName);
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public JSONArray getMateriales() {
        conexion = new Conexion();
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
        postparameters2send.add(new BasicNameValuePair("token", ""));
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(postparameters2send, URL_connect + "/materiales.php", "GET1", null);
    }

    public JSONArray getTipos() {
        conexion = new Conexion();
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
        postparameters2send.add(new BasicNameValuePair("token", ""));
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(postparameters2send, URL_connect + "/tipos.php", "GET1", null);
    }

    public JSONArray getProductos() {
        conexion = new Conexion();
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect + "/productos.php", "GET1", null);
    }

    public JSONArray getInventario() {
        conexion = new Conexion();
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
        postparameters2send.add(new BasicNameValuePair("token", ""));
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(postparameters2send, URL_connect, "GET1", null);
    }

    public JSONArray postMateriales() {
        conexion = new Conexion();
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect, "POST2", null);
    }

    public JSONArray postTipos() {
        conexion = new Conexion();
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect, "POST2", null);
    }

    public JSONArray postProductos() {
        conexion = new Conexion();
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect, "POST2", null);
    }

    public JSONArray postInventario() {
        conexion = new Conexion();
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect, "POST2", null);
    }

    public JSONArray putMateriales() {
        conexion = new Conexion();
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect, "PUT1", null);
    }

    public JSONArray putTipos() {
        conexion = new Conexion();
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect, "PUT1", null);
    }

    public JSONArray putProductos() {
        conexion = new Conexion();
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect, "PUT1", null);
    }

    public JSONArray putInventario() {
        conexion = new Conexion();
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect, "PUT1", null);
    }

    public JSONArray deleteMateriales() {
        conexion = new Conexion();
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect, "DELETE1", null);
    }

    public JSONArray deleteTipos() {
        conexion = new Conexion();
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect, "DELETE1", null);
    }

    public JSONArray deleteProductos() {
        conexion = new Conexion();
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect, "DELETE1", null);
    }

    public JSONArray deleteInventario() {
        conexion = new Conexion();
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect, "DELETE1", null);
    }
}
