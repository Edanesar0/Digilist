package co.edu.sena.digilistmobile.digilist.Conexiones;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.util.ArrayList;

public class RequestsAndResponses {
    String URL_connect = "http://192.168.1.138/Digilist/Servicios/mobile";//ruta en donde estan nuestros archivos
    Conexion conexion;

    public JSONArray getMateriales() {
        conexion = new Conexion();
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
        postparameters2send.add(new BasicNameValuePair("token", ""));
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(postparameters2send, URL_connect+"/materiales.php", "GET1", null);
    }
    public JSONArray getTipos() {
        conexion = new Conexion();
        ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
        postparameters2send.add(new BasicNameValuePair("token", ""));
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(postparameters2send, URL_connect+"/tipos.php", "GET1", null);
    }
    public JSONArray getProductos() {
        conexion = new Conexion();
        //realizamos una peticion y como respuesta obtenes un array JSON
        return conexion.getserverdata(null, URL_connect+"/productos.php", "GET1", null);
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
