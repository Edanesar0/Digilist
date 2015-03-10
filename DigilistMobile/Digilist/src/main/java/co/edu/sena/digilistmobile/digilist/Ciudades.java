package co.edu.sena.digilistmobile.digilist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import co.edu.sena.digilistmobile.digilist.dao.CityDAO;
import co.edu.sena.digilistmobile.digilist.dao.HistoricalSupplyDAO;
import co.edu.sena.digilistmobile.digilist.dao.MaterialDAO;
import co.edu.sena.digilistmobile.digilist.dao.ProductDAO;
import co.edu.sena.digilistmobile.digilist.dao.RolDAO;
import co.edu.sena.digilistmobile.digilist.dao.StandDAO;
import co.edu.sena.digilistmobile.digilist.dao.TypeDAO;
import co.edu.sena.digilistmobile.digilist.dao.UserDAO;
import co.edu.sena.digilistmobile.digilist.utils.conexiones.ConexionLocal;
import co.edu.sena.digilistmobile.digilist.vo.UserVO;

/**
 * Created by Axxuss on 25/02/2015.
 */
public class Ciudades extends SherlockActivity implements View.OnClickListener {

    private LinearLayout lyCiudades;
    private ScrollView svCiudades;
    private static Typeface font;
    private ImageButton btnCrearCiudades, btnEditarCiudad, btnEliminarCiudad;
    private CityDAO ciudades;
    private EditText edtNombreCiudad;
    private Spinner sCiudades;
    private ProgressBar pbCiudad;
    private int op;
    private Toast toast;
    private ProductDAO producto;
    private TypeDAO type;
    private HistoricalSupplyDAO historical;
    private MaterialDAO material;
    private StandDAO stand;
    private CityDAO city;
    private UserDAO user;
    private RolDAO rol;
    JSONArray respuesta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ciudades);
        ImageButton btnAdd = (ImageButton) findViewById(R.id.btnAgregarCiudades);
        btnAdd.setOnClickListener(this);
        font = Typeface.createFromAsset(this.getAssets(), "Station.ttf");
        ciudades = new CityDAO(this);
        new asynclogin().execute("1");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAgregarCiudades:
                btnCrearCiudades.setVisibility(View.INVISIBLE);
                lyCiudades.setVisibility(View.VISIBLE);
                edtNombreCiudad.setEnabled(true);
                op = 0;

                break;
            case R.id.btnGuardarCiudad:
                lyCiudades.setVisibility(View.INVISIBLE);
                btnCrearCiudades.setVisibility(View.VISIBLE);
                edtNombreCiudad.setEnabled(false);
                if (op == 0) {
                    respuesta = ciudades.agregarCiudadesHTTP(edtNombreCiudad.getText().toString());
                    new asynclogin().execute("2");

                } else {
                    respuesta = ciudades.editarCiudadesHTTP(sCiudades.getSelectedItem().toString(), edtNombreCiudad.getText().toString());
                    new asynclogin().execute("3");
                }
                edtNombreCiudad.setText("");
                break;
            case R.id.btnEditarCiudad:
                btnCrearCiudades.setVisibility(View.INVISIBLE);
                lyCiudades.setVisibility(View.VISIBLE);
                edtNombreCiudad.setEnabled(true);
                edtNombreCiudad.setText(sCiudades.getSelectedItem().toString());
                op = 1;
                break;
            case R.id.btnCancelar:
                lyCiudades.setVisibility(View.INVISIBLE);
                btnCrearCiudades.setVisibility(View.VISIBLE);
                edtNombreCiudad.setEnabled(false);
                edtNombreCiudad.setText("");
                break;
            case R.id.btnEliminarCiudad:
                AlertDialog.Builder builder;
                final AlertDialog dialog;
                builder = new AlertDialog.Builder(this);
                builder.setMessage(this.getResources().getString(R.string.MensajeEliminarCiudad));

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            JSONArray ja = city.darBajaCiudad(sCiudades.getSelectedItem().toString());
                            String mensajes = ja.getString(0);
                            if (mensajes.contains("The city has been removed.")) {
                                toast = Toast.makeText(Ciudades.this, R.string.Ciudad_Eliminada, Toast.LENGTH_LONG);
                                toast.show();
                                dialog.dismiss();
                                Intent it = getIntent();
                                finish();
                                ConexionLocal conexionLocal = new ConexionLocal(Ciudades.this);
                                conexionLocal.abrir();
                                conexionLocal.limpiar();
                                conexionLocal.cerrar();

                                type.agregarTipo();
                                material.agregarMaterialLocal();
                                producto.agregarProducto();
                                historical.agregarHistorico();
                                stand.agregarStand();
                                producto.agregarInventario();
                                rol.agregarRoles();
                                city.agregarCiudadesLocal();
                                user.agregarUsuarios();
                                startActivity(it);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).setNegativeButton("Cancelar", null);
                dialog = builder.create();
                dialog.setTitle("Eliminar");
                dialog.show();
                break;
        }

    }

    class asynclogin extends AsyncTask<String, String, String> {
        char pos;

        protected void onPreExecute() {

            svCiudades = (ScrollView) findViewById(R.id.svCiudad);
            pbCiudad = (ProgressBar) findViewById(R.id.pbCiudad);
            svCiudades.setVisibility(View.INVISIBLE);
            pbCiudad.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            pos = params[0].charAt(0);
            type = new TypeDAO(Ciudades.this);
            material = new MaterialDAO(Ciudades.this);
            producto = new ProductDAO(Ciudades.this);
            stand = new StandDAO(Ciudades.this);
            city = new CityDAO(Ciudades.this);
            user = new UserDAO(Ciudades.this);
            rol = new RolDAO(Ciudades.this);
            historical = new HistoricalSupplyDAO(Ciudades.this);
            return null;
        }

        protected void onPostExecute(String result) {
            switch (pos) {
                case '1':
                    cargarCiudades();
                    svCiudades.setVisibility(View.VISIBLE);
                    pbCiudad.setVisibility(View.INVISIBLE);
                    break;
                case '2':
                    try {
                        if (respuesta.length() > 0) {
                            String mensajes = respuesta.getString(0);
                            if (mensajes.contains("The city has been inserted")) {
                                toast = Toast.makeText(Ciudades.this, R.string.Ciudad_Agregada, Toast.LENGTH_LONG);
                                toast.show();
                                Intent it = getIntent();
                                ConexionLocal conexionLocal = new ConexionLocal(Ciudades.this);
                                conexionLocal.abrir();
                                conexionLocal.limpiar();
                                conexionLocal.cerrar();
                                finish();
                                type.agregarTipo();
                                material.agregarMaterialLocal();
                                producto.agregarProducto();
                                historical.agregarHistorico();
                                stand.agregarStand();
                                producto.agregarInventario();
                                rol.agregarRoles();
                                city.agregarCiudadesLocal();
                                user.agregarUsuarios();
                                svCiudades.setVisibility(View.VISIBLE);
                                pbCiudad.setVisibility(View.INVISIBLE);
                                startActivity(it);

                            }
                        }
                    } catch (Exception e) {
                        e.getMessage();
                    }

                    break;
                case '3':
                    try {
                        if (respuesta.length() > 0) {
                            String mensajes = respuesta.getString(0);
                            if (mensajes.contains("The record has been updated")) {
                                toast = Toast.makeText(Ciudades.this, R.string.Ciudad_actualizada, Toast.LENGTH_LONG);
                                toast.show();
                                Intent it = getIntent();
                                finish();
                                ConexionLocal conexionLocal = new ConexionLocal(Ciudades.this);
                                conexionLocal.abrir();
                                conexionLocal.limpiar();
                                conexionLocal.cerrar();
                                type.agregarTipo();
                                material.agregarMaterialLocal();
                                producto.agregarProducto();
                                historical.agregarHistorico();
                                stand.agregarStand();
                                producto.agregarInventario();
                                rol.agregarRoles();
                                city.agregarCiudadesLocal();
                                user.agregarUsuarios();
                                svCiudades.setVisibility(View.VISIBLE);
                                pbCiudad.setVisibility(View.INVISIBLE);
                                startActivity(it);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        toast = Toast.makeText(Ciudades.this, e.getMessage(), Toast.LENGTH_LONG);
                        toast.show();
                    }

                    break;
            }
        }
    }

    public void cargarCiudades() {
        sCiudades = (Spinner) findViewById(R.id.sCiudades);
        lyCiudades = (LinearLayout) findViewById(R.id.lyCiudades);
        ArrayList<String> opc = ciudades.consultarCiudades();
        ArrayAdapter<String> adpCiudad = new ArrayAdapter<String>(Ciudades.this, android.R.layout.simple_list_item_1, opc);
        sCiudades.setAdapter(adpCiudad);
        btnCrearCiudades = (ImageButton) findViewById(R.id.btnAgregarCiudades);
        btnCrearCiudades.setOnClickListener(this);
        btnEliminarCiudad = (ImageButton) findViewById(R.id.btnEliminarCiudad);
        btnEliminarCiudad.setOnClickListener(this);
        Button btnGuardarCiudades = (Button) findViewById(R.id.btnGuardarCiudad);
        btnGuardarCiudades.setOnClickListener(this);
        btnGuardarCiudades.setTypeface(font);
        Button btnCancelar = (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);
        btnCancelar.setTypeface(font);
        edtNombreCiudad = (EditText) findViewById(R.id.edtNombreCiudad);
        edtNombreCiudad.setTypeface(font);
        TextView txtTitulo = (TextView) findViewById(R.id.txtTitulo);
        txtTitulo.setTypeface(font);
        TextView txtNombre = (TextView) findViewById(R.id.txtNombre);
        txtNombre.setTypeface(font);
        btnEditarCiudad = (ImageButton) findViewById(R.id.btnEditarCiudad);
        btnEditarCiudad.setOnClickListener(this);
    }
}


