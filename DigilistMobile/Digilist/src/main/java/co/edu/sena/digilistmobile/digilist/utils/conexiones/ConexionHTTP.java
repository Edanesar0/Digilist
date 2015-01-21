package co.edu.sena.digilistmobile.digilist.utils.conexiones;


import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ConexionHTTP {
    InputStream is = null;
    String result = "";


    public JSONArray getserverdata(ArrayList<NameValuePair> parameters, String urlwebserver, String fun, JSONObject para) {

        //conecta via http y envia un post.
        httppostconnect(parameters, urlwebserver, fun, para);
        if (is != null) {//si obtuvo una respuesta
            getpostresponse();
            return getjsonarray();
        } else {

            return null;

        }

    }

    //peticion HTTP
    private void httppostconnect(ArrayList<NameValuePair> parametros, String urlwebserver, String fun, JSONObject json) {

        //
        try {
            if (fun.equals("POST1")) {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(urlwebserver);
                httppost.setEntity(new UrlEncodedFormEntity(parametros));
                //ejecuto peticion enviando datos por POST
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
            }
            if (fun.equals("POST2")) {
                DefaultHttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(urlwebserver);
                StringEntity se = new StringEntity(json.toString());
                httppost.setEntity(se);
                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();

            }
            if (fun.equals("GET1")) {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet httpget = new HttpGet(urlwebserver);
                httpget.setHeader("user", "almacenista");
                httpget.setHeader("pass", "5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5");
                //ejecuto peticion enviando datos por POST
                HttpResponse response = httpclient.execute(httpget);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
            }
            if (fun.equals("GET2")) {
                DefaultHttpClient httpclient = new DefaultHttpClient();
                HttpGet httpget = new HttpGet(urlwebserver);
                httpget.setHeader("Accept", "application/json");
                httpget.setHeader("Content-type", "application/json");
                HttpResponse response = httpclient.execute(httpget);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();

            }
            if (fun.equals("PUT")) {
                DefaultHttpClient httpclient = new DefaultHttpClient();
                HttpPut httput = new HttpPut(urlwebserver);
                StringEntity se = new StringEntity(json.toString());
                httput.setEntity(se);
                httput.setHeader("Accept", "application/json");
                httput.setHeader("Content-type", "application/json");
                HttpResponse response = httpclient.execute(httput);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();

            }
            if (fun.equals("DELETE1")) {
                HttpClient httpclient = new DefaultHttpClient();
                HttpDelete httpdelete = new HttpDelete(urlwebserver);
                //ejecuto peticion enviando datos por POST
                HttpResponse response = httpclient.execute(httpdelete);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
            }
            if (fun.equals("DELETE2")) {
                DefaultHttpClient httpclient = new DefaultHttpClient();
                HttpDelete httpdelete = new HttpDelete(urlwebserver);
                httpdelete.setHeader("Accept", "application/json");
                httpdelete.setHeader("Content-type", "application/json");
                HttpResponse response = httpclient.execute(httpdelete);
                HttpEntity entity = response.getEntity();
                is = entity.getContent();

            }


        } catch (Exception e) {
            Log.e("log_tag", "Error in http connection " + e.toString());
        }

    }

    public void getpostresponse() {

        //Convierte respuesta a String
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }


            is.close();
            result = sb.toString();
            Log.e("getpostresponse", " result= " + result);
        } catch (Exception e) {
            Log.e("log_tag", "Error converting result " + e.toString());
        }
    }

    public JSONArray getjsonarray() {
        //parse json data
        try {
            //Obtenemos el tipo de un ArrayList
            return new JSONArray(result);

        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
            return null;
        }

    }

}
