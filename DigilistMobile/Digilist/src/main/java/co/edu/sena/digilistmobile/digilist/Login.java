package co.edu.sena.digilistmobile.digilist;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.edu.sena.digilistmobile.digilist.Conexiones.Conexion;


public class Login extends SherlockActivity {
    private ProgressDialog pDialog;
    Typeface font;
    EditText edtPassw, edtUsuario;
    String URL_connect = "http://192.168.1.182/mobile/acces.php";//ruta en donde estan nuestros archivos
    Conexion conexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        font = Typeface.createFromAsset(this.getAssets(), "Station.ttf");
        Button bo = (Button) findViewById(R.id.Blogin);
        edtUsuario = (EditText) findViewById(R.id.edtUsuario);
        edtPassw = (EditText) findViewById(R.id.edtPassword);
        TextView txtusuario = (TextView) findViewById(R.id.txtUsuario);
        TextView txtpassw = (TextView) findViewById(R.id.txtPassword);
        txtpassw.setTypeface(font);
        txtusuario.setTypeface(font);
        edtPassw.setTypeface(font);
        edtUsuario.setTypeface(font);
        bo.setTypeface(font);
        LinearLayout layout2 = (LinearLayout) findViewById(R.id.layout2);
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        /**Se optiene el ancho y el alto del dipositivo**/

        int ancho = display.getWidth();
        int alto = display.getHeight();
        int porcentaje = (ancho * 70) / 100;
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(porcentaje, LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        /**se cambia el tamaño depediendo de la resolucion del dispositivo */

        switch (metrics.densityDpi) {

            case DisplayMetrics.DENSITY_XHIGH: //HDPI
                if (alto > 800) {
                    params.setMargins(0, 0, 0, 0);
                }

                break;
            case DisplayMetrics.DENSITY_HIGH: //HDPI
                if (alto > 800) {
                    params.setMargins(0, 100, 0, 0);
                }

                break;
            case DisplayMetrics.DENSITY_MEDIUM: //MDPI
                if (alto > 800) {
                    params.setMargins(0, 150, 0, 0);
                }
                break;
            case DisplayMetrics.DENSITY_LOW:  //LDPI
                if (alto > 800) {
                    params.setMargins(0, 150, 0, 0);
                }

                break;
        }
        layout2.setLayoutParams(params);


        bo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String usuario = edtUsuario.getText().toString().toLowerCase();
                String passw = edtPassw.getText().toString();
                if (checklogindata(usuario, passw)) {
                    //si pasamos esa validacion ejecutamos el asynctask pasando el usuario y clave como parametros
                    new asynclogin().execute(usuario, passw);
                } else {
                    //si detecto un error en la primera validacion vibrar y mostrar un Toast con un mensaje de error.
                    err_login(1);
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class asynclogin extends AsyncTask<String, String, String> {
        String user, pass;

        protected void onPreExecute() {
            try {
                //para el progress dialog
                pDialog = new ProgressDialog(Login.this);
                //conexiones.abrir();
                pDialog.setMessage("Iniciando sesión ");
                //conexiones.cerrar();
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
                pDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        protected String doInBackground(String... params) {
            //obtnemos usr y pass
            user = params[0];
            pass = params[1];
            Log.e("", user + pass);
            //enviamos y recibimos y analizamos los datos en segundo plano.
            if (loginstatus(user, pass)) {
                return "ok"; //login valido
            } else {
                return "err"; //login invalido
            }

        }

        /*Una vez terminado doInBackground segun lo que halla ocurrido
         pasamos a la sig. activity
         o mostramos error*/
        protected void onPostExecute(String result) {

            pDialog.dismiss();//ocultamos progess dialog.
            Log.e("onPostExecute=", "" + result);
            if (result.equals("ok")) {
                Intent i = new Intent(Login.this, Inicio.class);
                startActivity(i);
                finish();
                /*if (cargo.equals("Coordinador")) {
                    Intent i = new Intent(Login.this, InicioCoordinadores.class);
                    i.putExtra("user", user);
                    i.putExtra("nombre", nombre);
                    i.putExtra("cargo", cargo);
                    finish();
                    startActivity(i);
                }
                if (cargo.equals("Vendedor") || cargo.equals("Reseller")) {
                    Intent i = new Intent(Login.this, InicioAsesores.class);
                    i.putExtra("user", user);
                    i.putExtra("cargo", cargo);
                    i.putExtra("pos", 0);
                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.cancel(0);
                    finish();
                    startActivity(i);

                }*/

            } else {
                err_login(2);
            }

        }
    }

    //vibra y muestra un Toast
    public void err_login(int op) {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(200);
        Toast toast1;
        if (op == 1)
            toast1 = Toast.makeText(this, "Nombre de usuario o password vacios", Toast.LENGTH_SHORT);
        else
            toast1 = Toast.makeText(this, "Nombre de usuario o password incorrectos", Toast.LENGTH_SHORT);
        toast1.show();
    }

    public boolean checklogindata(String username, String password) {

        if (username.equals("") || password.equals("") || username.equals(" ") || password.equals(" ")) {
            Log.e("Login ui", "checklogindata user or pass error");
            return false;
        } else {
            return true;
        }

    }

    /*Valida el estado del logueo solamente necesita como parametros el usuario y passw*/
    public boolean loginstatus(String username, String password) {
        boolean val = false;
        try {
            int logstatus = -1;
            String nom = "";
            int cant = 0;
            /*Creamos un ArrayList del tipo nombre valor para agregar los datos recibidos por los parametros anteriores
             * y enviarlo mediante POST a nuestro sistema para relizar la validacion*/
            conexion = new Conexion();
            ArrayList<NameValuePair> postparameters2send = new ArrayList<NameValuePair>();
            postparameters2send.add(new BasicNameValuePair("usuario", username));
            postparameters2send.add(new BasicNameValuePair("password", password));
            //realizamos una peticion y como respuesta obtenes un array JSON
            JSONArray jdata = conexion.getserverdata(postparameters2send, URL_connect, "1", null);

            //si lo que obtuvimos no es null
            if (jdata != null && jdata.length() > 0) {
                JSONObject json_data; //creamos un objeto JSON
                try {
                    for (int i = 0; i < jdata.length(); i++) {//se recorre el json
                        json_data = jdata.getJSONObject(0); //leemos el primer segmento en nuestro caso el unico
                        logstatus = json_data.getInt("logstatus");//accedemos al valor
                    }
                    Log.e("loginstatus", "logstatus= " + logstatus);
                    //muestro por log que obtuvimos
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //validamos el valor obtenido
                if (logstatus == 0) {// [{"logstatus":"0"}]
                    Log.e("loginstatus ", "invalido");
                    val = false;
                } else {// [{"logstatus":"1"}]
                    Log.e("loginstatus ", "valido" + nom);
                    val = true;
                }

            } else {    //json obtenido invalido verificar parte WEB.
                Log.e("JSON  ", "ERROR");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return val;


    }
}
