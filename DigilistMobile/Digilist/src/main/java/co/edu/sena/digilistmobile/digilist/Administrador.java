package co.edu.sena.digilistmobile.digilist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

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
import co.edu.sena.digilistmobile.digilist.utils.Encrypting;
import co.edu.sena.digilistmobile.digilist.utils.conexiones.ConexionLocal;
import co.edu.sena.digilistmobile.digilist.vo.UserVO;

import static android.view.View.OnLongClickListener;


public class Administrador extends SherlockActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {


    Typeface font;
    private ProductDAO producto;
    private TypeDAO type;
    private HistoricalSupplyDAO historical;
    private MaterialDAO material;
    private StandDAO stand;
    private CityDAO city;
    ScrollView lyUsr;
    private UserDAO user;
    ProgressBar pbUsu;
    private RolDAO rol;
    AlertDialog dialog3;
    ProgressBar pbInv;
    ScrollView lyInv;
    Toast toast;
    private EditText edtNombre, edtApellido, edtTelefono, edtDireccion, edtUsuario, edtPass;
    private TextView txtNombres, txtApellidos, txtTelefono, txtDireccion, txtUsuario, txtRol, txtCiudad, txtTitulo, txtPass;
    private Spinner srol, sCiudad;
    ActionBar ab;
    private TableLayout tl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int op = 0;

        ab = getSupportActionBar();//instancia
        // ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);//Atributos titulo boton home y flecha de acompañamiento de home
        ab.setIcon(R.drawable.ic_launcher);//se le adiciona el icono


        font = Typeface.createFromAsset(this.getAssets(), "Station.ttf");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            op = extras.getInt("pos");
        }
        switch (op) {
            case 0:
                try {
                    setContentView(R.layout.usuarios);
                    new asynclogin().execute(op + "");
                    //usuarios();
                    /*type = new TypeDAO(this);
                    material = new MaterialDAO(this);
                    producto = new ProductDAO(this);
                    stand = new StandDAO(this);*/
                    //historical = new HistoricalSupplyDAO(this);
                } catch (Exception e) {
                    toast = Toast.makeText(Administrador.this, e.getMessage(), Toast.LENGTH_LONG);
                    toast.show();
                    e.printStackTrace();
                }
                break;
            case 1:
                setContentView(R.layout.inventario);
                new asynclogin().execute(op + "");
                break;
            case 2:
                setContentView(R.layout.ingreso_usuarios);
                try {
                    agregarUsuarios();
                } catch (Exception e) {
                    toast = Toast.makeText(Administrador.this, e.getMessage(), Toast.LENGTH_LONG);
                    toast.show();
                    e.printStackTrace();
                }
                break;
            case 3:
                break;
        }

    }

    public void usuarios() throws Exception {
        final Typeface font = Typeface.createFromAsset(this.getAssets(), "Station.ttf");

        ImageButton btnAdd = (ImageButton) findViewById(R.id.btnAgregarUsr);
        btnAdd.setOnClickListener(this);
        TableLayout tl = (TableLayout) findViewById(R.id.tlUsuarios);
        tl.setStretchAllColumns(true);
        tl.setShrinkAllColumns(true);
        TextView lblNombre = (TextView) findViewById(R.id.lblNombre);
        lblNombre.setTypeface(font);
        TextView lblApellidos = (TextView) findViewById(R.id.lblApellidos);
        lblApellidos.setTypeface(font);
        TextView lblUsuario = (TextView) findViewById(R.id.lblUsuario);
        lblUsuario.setTypeface(font);
        TextView lblRol = (TextView) findViewById(R.id.lblRol);
        lblRol.setTypeface(font);

        ArrayList<String> usuarios = user.consultarUsuarios();


        int count = 0;
        if (usuarios.size() != 0) {
            for (int i = 0; i <= usuarios.size() - 5; i = i + 5) {
                final TableRow tr = new TableRow(Administrador.this);

                if (count % 2 != 0) {
                    tr.setBackgroundResource(R.drawable.row_selector_r);
                    //tr.setBackgroundColor(Color.argb(15, 203, 47, 23));
                } else {
                    tr.setBackgroundResource(R.drawable.row_selector_w);
                    //tr.setBackgroundColor(Color.WHITE);
                }
                final TextView txtNombre = new TextView(Administrador.this);
                txtNombre.setTypeface(font);
                txtNombre.setId(Integer.parseInt(usuarios.get(i)));
                txtNombre.setText(usuarios.get(i + 1));
                txtNombre.setLines(3);
                txtNombre.setTypeface(font);
                txtNombre.setGravity(Gravity.CENTER);
                //txtProducto.setTextSize(20);
                tr.addView(txtNombre);
                final TextView txtApellido = new TextView(Administrador.this);
                txtApellido.setTypeface(font);
                txtApellido.setLines(3);
                txtApellido.setText(usuarios.get(i + 2));
                txtApellido.setGravity(Gravity.CENTER);
                txtApellido.setTypeface(font);
                txtApellido.setLines(2);
                tr.addView(txtApellido);
                TextView txtUsuario = new TextView(Administrador.this);
                txtUsuario.setTypeface(font);
                txtUsuario.setLines(3);
                txtUsuario.setText(usuarios.get(i + 3));
                txtUsuario.setGravity(Gravity.CENTER);
                txtUsuario.setTypeface(font);
                tr.addView(txtUsuario);
                TextView txtRol = new TextView(Administrador.this);
                txtRol.setTypeface(font);
                txtRol.setLines(3);
                txtRol.setText(usuarios.get(i + 4));
                txtRol.setGravity(Gravity.CENTER);
                txtRol.setTypeface(font);
                tr.addView(txtRol);
                count++;
                tr.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        LayoutInflater inflater = getLayoutInflater();
                        View v2 = inflater.inflate(R.layout.opciones, null);
                        ListView listview = (ListView) v2.findViewById(R.id.lvOpciones);
                        ArrayList<String> opc = new ArrayList<String>();
                        opc.add(Administrador.this.getResources().getString(R.string.Detalles));
                        opc.add(Administrador.this.getResources().getString(R.string.Editar));
                        opc.add(Administrador.this.getResources().getString(R.string.Eliminar));
                        ArrayAdapter<String> adpOpc = new ArrayAdapter<String>(Administrador.this, R.layout.list_center, opc);
                        listview.setAdapter(adpOpc);
                        AlertDialog.Builder builder3 = new AlertDialog.Builder(Administrador.this);
                        builder3.setView(v2);
                        builder3.setPositiveButton("Aceptar", null).setNegativeButton("Cancelar", null);
                        dialog3 = builder3.create();
                        dialog3.setTitle(txtNombre.getText() + " " + txtApellido.getText());
                        dialog3.show();
                        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                try {
                                    final ArrayList<String> us;
                                    LayoutInflater inflater;
                                    LinearLayout llUser;
                                    View v, v2;
                                    final EditText edtNombre, edtApellido, edtTelefono, edtDireccion, edtUsuario;
                                    final TextView txtNombres, txtApellidos, txtTelefono, txtDireccion, txtUsuario, txtRol, txtCiudad;
                                    final Spinner srol, sCiudad;
                                    ArrayAdapter<String> adpRol, adpCiudad;
                                    AlertDialog.Builder builder;
                                    final AlertDialog dialog;
                                    final int[] mensajeAlerta = {0};
                                    ArrayList<String> opc;
                                    us = user.consultarUsuario(txtNombre.getId() + "");

                                    inflater = getLayoutInflater();
                                    v = inflater.inflate(R.layout.ingreso_usuarios, null);

                                    txtNombres = (TextView) v.findViewById(R.id.txtNombres);
                                    txtNombres.setTypeface(font);
                                    txtNombres.setId(Integer.parseInt(us.get(0)));

                                    txtApellidos = (TextView) v.findViewById(R.id.txtApellido);
                                    txtApellidos.setTypeface(font);

                                    txtTelefono = (TextView) v.findViewById(R.id.txtTelefono);
                                    txtTelefono.setTypeface(font);

                                    txtDireccion = (TextView) v.findViewById(R.id.txtDireccion);
                                    txtDireccion.setTypeface(font);

                                    txtUsuario = (TextView) v.findViewById(R.id.txtUsuario);
                                    txtUsuario.setTypeface(font);

                                    txtRol = (TextView) v.findViewById(R.id.txtRol);
                                    txtRol.setTypeface(font);

                                    txtCiudad = (TextView) v.findViewById(R.id.txtCiudad);
                                    txtCiudad.setTypeface(font);

                                    edtNombre = (EditText) v.findViewById(R.id.edtNombres);
                                    edtNombre.setTypeface(font);
                                    edtNombre.setText(us.get(1));


                                    edtApellido = (EditText) v.findViewById(R.id.edtApellidos);
                                    edtApellido.setTypeface(font);
                                    edtApellido.setText(us.get(2));

                                    edtTelefono = (EditText) v.findViewById(R.id.edtTelefono);
                                    edtTelefono.setTypeface(font);
                                    edtTelefono.setText(us.get(3));

                                    edtDireccion = (EditText) v.findViewById(R.id.edtDireccion);
                                    edtDireccion.setTypeface(font);
                                    edtDireccion.setText(us.get(4));

                                    srol = (Spinner) v.findViewById(R.id.sRol);

                                    sCiudad = (Spinner) v.findViewById(R.id.sCiudad);

                                    edtUsuario = (EditText) v.findViewById(R.id.edtUsuario);
                                    edtUsuario.setTypeface(font);
                                    edtUsuario.setText(us.get(6));
                                    switch (position) {
                                        case 0:
                                            //dialog3.dismiss();
                                            llUser = (LinearLayout) v.findViewById(R.id.llUser);
                                            v2 = v.findViewById(R.id.rlButtons);
                                            llUser.removeView(v2);
                                            v2 = v.findViewById(R.id.txtTitulo);
                                            llUser.removeView(v2);

                                            edtNombre.setBackgroundResource(R.drawable.white_button);
                                            edtNombre.setEnabled(false);

                                            edtApellido.setBackgroundResource(R.drawable.white_button);
                                            edtApellido.setEnabled(false);

                                            edtTelefono.setBackgroundResource(R.drawable.white_button);
                                            edtTelefono.setEnabled(false);

                                            edtDireccion.setBackgroundResource(R.drawable.white_button);
                                            edtDireccion.setEnabled(false);
                                            edtDireccion.setTypeface(font);


                                            opc = new ArrayList<String>();
                                            opc.add(us.get(5));
                                            adpRol = new ArrayAdapter<String>(Administrador.this, android.R.layout.simple_list_item_1, opc);
                                            srol.setAdapter(adpRol);
                                            srol.setBackgroundResource(R.drawable.white_button);
                                            srol.setClickable(false);


                                            opc = new ArrayList<String>();
                                            opc.add(us.get(8));
                                            adpCiudad = new ArrayAdapter<String>(Administrador.this, android.R.layout.simple_list_item_1, opc);
                                            sCiudad.setAdapter(adpCiudad);
                                            sCiudad.setBackgroundResource(R.drawable.white_button);
                                            sCiudad.setClickable(false);


                                            edtUsuario.setBackgroundResource(R.drawable.white_button);
                                            edtUsuario.setEnabled(false);
                                            edtUsuario.setTypeface(font);

                                            v2 = v.findViewById(R.id.txtPass);
                                            llUser.removeView(v2);

                                            v2 = v.findViewById(R.id.edtPass);
                                            llUser.removeView(v2);

                                            builder = new AlertDialog.Builder(Administrador.this);
                                            builder.setView(v);
                                            builder.setPositiveButton("Aceptar", null);
                                            dialog = builder.create();
                                            dialog.setTitle("Información");
                                            dialog.show();

                                            break;
                                        case 1:
                                            try {

                                                llUser = (LinearLayout) v.findViewById(R.id.llUser);
                                                v2 = v.findViewById(R.id.rlButtons);
                                                llUser.removeView(v2);
                                                v2 = v.findViewById(R.id.txtTitulo);
                                                llUser.removeView(v2);
                                                opc = rol.consultarRoles();
                                                //opc.add(us.get(5));
                                                adpRol = new ArrayAdapter<String>(Administrador.this, android.R.layout.simple_list_item_1, opc);
                                                srol.setAdapter(adpRol);
                                                srol.setSelection(Integer.parseInt(us.get(7)) - 1);

                                                opc = city.consultarCiudades();
                                                adpCiudad = new ArrayAdapter<String>(Administrador.this, android.R.layout.simple_list_item_1, opc);
                                                sCiudad.setAdapter(adpCiudad);
                                                sCiudad.setSelection(Integer.parseInt(us.get(9)) - 1);

                                                v2 = v.findViewById(R.id.txtPass);
                                                llUser.removeView(v2);
                                                v2 = v.findViewById(R.id.edtPass);
                                                llUser.removeView(v2);
                                                builder = new AlertDialog.Builder(Administrador.this);
                                                builder.setView(v);
                                                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {


                                                    }
                                                }).setNegativeButton("Cancelar", null);
                                                dialog = builder.create();
                                                dialog.setCanceledOnTouchOutside(false);
                                                dialog.setTitle("Editar");
                                                dialog.show();
                                                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        Boolean wantToCloseDialog = false;
                                                        try {
                                                            boolean validacion, validacion2, validacion3, validacion4, validacion5, validacion6;
                                                            validacion = validacion(edtNombre.getText().toString());
                                                            validacion2 = validacion(edtApellido.getText().toString());
                                                            validacion3 = validacion(edtTelefono.getText().toString());
                                                            validacion4 = validacion(edtDireccion.getText().toString());
                                                            validacion5 = validacion(edtUsuario.getText().toString());
                                                            validacion6 = srol.getSelectedItem() != null;
                                                            if (validacion && validacion2 && validacion3 && validacion4 && validacion5 && validacion6) {
                                                                UserVO userVO = new UserVO();
                                                                userVO.setIdUser(txtNombres.getId());
                                                                userVO.setNames(edtNombre.getText().toString());
                                                                userVO.setLast_name(edtApellido.getText().toString());
                                                                userVO.setPhone(edtTelefono.getText().toString());
                                                                userVO.setAddress(edtDireccion.getText().toString());
                                                                userVO.setUser(edtUsuario.getText().toString());
                                                                userVO.setIdRol(srol.getSelectedItemPosition() + 1);
                                                                userVO.setIdCity(sCiudad.getSelectedItemPosition() + 1);
                                                                JSONArray ja = user.modificarUsuario(userVO);
                                                                if (ja != null) {
                                                                    String mensajes = ja.getString(0);
                                                                    if (mensajes.contains("The record has been updated")) {
                                                                        edtNombre.setText("");
                                                                        edtApellido.setText("");
                                                                        edtTelefono.setText("");
                                                                        edtDireccion.setText("");
                                                                        edtUsuario.setText("");
                                                                        mensajeAlerta[0] = 1;
                                                                        Intent it = getIntent();
                                                                        finish();
                                                                        ConexionLocal conexionLocal = new ConexionLocal(Administrador.this);
                                                                        conexionLocal.abrir();
                                                                        conexionLocal.limpiar();
                                                                        conexionLocal.cerrar();
                                                                        startActivity(it);

                                                                    }
                                                                } else {
                                                                    mensajeAlerta[0] = 2;

                                                                }
                                                                wantToCloseDialog = true;

                                                            } else {
                                                                wantToCloseDialog = false;

                                                                if (!validacion) {
                                                                    edtNombre.setBackgroundResource(R.drawable.borde_error);
                                                                    edtNombre.addTextChangedListener(new TextWatcher() {
                                                                        @Override
                                                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                                        }

                                                                        @Override
                                                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                            edtNombre.setBackgroundResource(R.drawable.edittext_rounded_corners);
                                                                        }

                                                                        @Override
                                                                        public void afterTextChanged(Editable s) {

                                                                        }
                                                                    });

                                                                }
                                                                if (!validacion2) {
                                                                    edtApellido.setBackgroundResource(R.drawable.borde_error);
                                                                    edtApellido.addTextChangedListener(new TextWatcher() {
                                                                        @Override
                                                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                                        }

                                                                        @Override
                                                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                            edtApellido.setBackgroundResource(R.drawable.edittext_rounded_corners);
                                                                        }

                                                                        @Override
                                                                        public void afterTextChanged(Editable s) {

                                                                        }
                                                                    });

                                                                }
                                                                if (!validacion3) {

                                                                    edtTelefono.setBackgroundResource(R.drawable.borde_error);
                                                                    edtTelefono.addTextChangedListener(new TextWatcher() {
                                                                        @Override
                                                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                                        }

                                                                        @Override
                                                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                            edtTelefono.setBackgroundResource(R.drawable.edittext_rounded_corners);
                                                                        }

                                                                        @Override
                                                                        public void afterTextChanged(Editable s) {

                                                                        }
                                                                    });
                                                                }
                                                                if (!validacion4) {
                                                                    edtDireccion.setBackgroundResource(R.drawable.borde_error);
                                                                    edtDireccion.addTextChangedListener(new TextWatcher() {
                                                                        @Override
                                                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                                        }

                                                                        @Override
                                                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                            edtDireccion.setBackgroundResource(R.drawable.edittext_rounded_corners);
                                                                        }

                                                                        @Override
                                                                        public void afterTextChanged(Editable s) {

                                                                        }
                                                                    });
                                                                }
                                                                if (!validacion5) {
                                                                    edtUsuario.setBackgroundResource(R.drawable.borde_error);
                                                                    edtUsuario.addTextChangedListener(new TextWatcher() {
                                                                        @Override
                                                                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                                        }

                                                                        @Override
                                                                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                            edtUsuario.setBackgroundResource(R.drawable.edittext_rounded_corners);
                                                                        }

                                                                        @Override
                                                                        public void afterTextChanged(Editable s) {

                                                                        }
                                                                    });
                                                                }
                                                                toast = Toast.makeText(Administrador.this, R.string.llenar_campos, Toast.LENGTH_LONG);
                                                                toast.show();
                                                            }
                                                            //Do stuff, possibly set wantToCloseDialog to true then...
                                                            if (wantToCloseDialog) {
                                                                dialog.dismiss();
                                                            }
                                                        } catch (Exception e) {
                                                            dialog.dismiss();
                                                            dialog3.dismiss();
                                                            toast = Toast.makeText(Administrador.this, e.getMessage(), Toast.LENGTH_LONG);
                                                            toast.show();
                                                            e.printStackTrace();
                                                        }
                                                        if (mensajeAlerta[0] == 1) {
                                                            toast = Toast.makeText(Administrador.this, R.string.Usuario_Actualizado, Toast.LENGTH_LONG);
                                                            toast.show();
                                                        } else {
                                                            toast = Toast.makeText(Administrador.this, R.string.ErrorServidor, Toast.LENGTH_LONG);
                                                            toast.show();
                                                        }


                                                    }
                                                });
                                            } catch (Exception e) {
                                                toast = Toast.makeText(Administrador.this, e.getMessage(), Toast.LENGTH_LONG);
                                                toast.show();
                                                e.printStackTrace();
                                            }

                                            break;
                                        case 2:
                                            builder = new AlertDialog.Builder(Administrador.this);
                                            builder.setMessage(Administrador.this.getResources().getString(R.string.MensajeEliminar));

                                            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    try {
                                                        UserVO userVO = new UserVO();
                                                        userVO.setIdUser(txtNombre.getId());
                                                        JSONArray ja = user.darBajaUsuario(userVO);
                                                        String mensajes = ja.getString(0);
                                                        if (mensajes.contains("The specified record has been deleted")) {
                                                            toast = Toast.makeText(Administrador.this, R.string.Usuario_Eliminado, Toast.LENGTH_LONG);
                                                            toast.show();
                                                            dialog.dismiss();
                                                            dialog3.dismiss();
                                                            Intent it = getIntent();
                                                            finish();
                                                            ConexionLocal conexionLocal = new ConexionLocal(Administrador.this);
                                                            conexionLocal.abrir();
                                                            conexionLocal.limpiar();
                                                            conexionLocal.cerrar();
                                                            startActivity(it);
                                                        }

                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }).setNegativeButton("Cancelar", null);
                                            dialog = builder.create();
                                            dialog.setTitle("Eliminar");
                                            dialog.show();

                                            break;


                                    }
                                } catch (Exception e) {
                                    toast = Toast.makeText(Administrador.this, e.getMessage(), Toast.LENGTH_LONG);
                                    toast.show();
                                    e.printStackTrace();
                                }
                            }
                        });


                        return false;
                    }
                });
                tr.setLayoutParams(new TableRow.LayoutParams(
                        TableLayout.LayoutParams.FILL_PARENT,
                        TableLayout.LayoutParams.FILL_PARENT));
                tl.addView(tr, new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.FILL_PARENT,
                        TableLayout.LayoutParams.FILL_PARENT));

            }
        } else {
            TableRow tr_head = (TableRow) findViewById(R.id.trhead);
            tr_head.removeAllViews();
            //tr_head.setId(0);
            tr_head.setBackgroundColor(Color.rgb(203, 47, 23));
            tr_head.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            TextView lblMensaje = new TextView(Administrador.this);
            //lblMensaje.setId(20);
            lblMensaje.setTypeface(font);
            lblMensaje.setText("Ningun registro Almacenado");
            lblMensaje.setTextColor(Color.WHITE);
            lblMensaje.setTextSize(20);
            lblMensaje.setGravity(Gravity.CENTER);
            lblMensaje.setPadding(3, 3, 3, 3);

            tr_head.addView(lblMensaje);// añadir la columna a la fila de la tabla aquí

                        /*tl.addView(tr_head, new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.WRAP_CONTENT,
                                TableLayout.LayoutParams.WRAP_CONTENT));*/

        }


    }

    public void inventario() {
        pbInv = (ProgressBar) findViewById(R.id.pbInventario);
        tl = (TableLayout) findViewById(R.id.tlInventario);
        tl.setStretchAllColumns(true);
        tl.setShrinkAllColumns(true);

        ArrayList<String> productos = producto.consultarInventarios();
        int count = 0;
        if (productos.size() != 0) {
            for (int i = 0; i <= productos.size() - 6; i = i + 6) {
                TableRow tr = new TableRow(this);
                //tr.setGravity(Gravity.CENTER);
                if (count % 2 != 0) {
                    tr.setBackgroundResource(R.drawable.row_selector_r);
                    //tr.setBackgroundColor(Color.argb(15, 203, 47, 23));
                } else {
                    tr.setBackgroundResource(R.drawable.row_selector_w);
                    //tr.setBackgroundColor(Color.WHITE);
                }
                final TextView txtProducto = new TextView(this);
                txtProducto.setTypeface(font);
                txtProducto.setId(Integer.parseInt(productos.get(i + 5)));
                txtProducto.setText(productos.get(i));
                txtProducto.setGravity(Gravity.CENTER);
                //txtProducto.setTextSize(20);
                tr.addView(txtProducto);
                final TextView txtTipo = new TextView(this);
                txtTipo.setTypeface(font);
                txtTipo.setText(productos.get(i + 1));
                txtTipo.setGravity(Gravity.CENTER);
                txtTipo.setLines(2);
                tr.addView(txtTipo);
                TextView txtTamanio = new TextView(this);
                txtTamanio.setTypeface(font);
                txtTamanio.setText(productos.get(i + 2));
                txtTamanio.setGravity(Gravity.CENTER);
                tr.addView(txtTamanio);
                TextView txtMaterial = new TextView(this);
                txtMaterial.setTypeface(font);
                txtMaterial.setText(productos.get(i + 3));
                txtMaterial.setGravity(Gravity.CENTER);
                tr.addView(txtMaterial);
                TextView txtCantidad = new TextView(this);
                txtCantidad.setTypeface(font);
                txtCantidad.setText(productos.get(i + 4));
                txtCantidad.setGravity(Gravity.CENTER);
                tr.addView(txtCantidad);
                count++;
                tr.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        try {
                            LayoutInflater inflater = getLayoutInflater();
                            View v2 = inflater.inflate(R.layout.opciones, null);
                            ListView listview = (ListView) v2.findViewById(R.id.lvOpciones);
                            ArrayList<String> opc = new ArrayList<String>();
                            opc.add(Administrador.this.getResources().getString(R.string.Detalles));
                            opc.add(Administrador.this.getResources().getString(R.string.Historico));
                            opc.add(Administrador.this.getResources().getString(R.string.Editar));
                            opc.add(Administrador.this.getResources().getString(R.string.Eliminar));
                            ArrayAdapter<String> adpOpc = new ArrayAdapter<String>(Administrador.this, R.layout.list_center, opc);
                            listview.setAdapter(adpOpc);
                            AlertDialog.Builder builder3 = new AlertDialog.Builder(Administrador.this);
                            builder3.setView(v2);
                            builder3.setPositiveButton("Aceptar", null).setNegativeButton("Cancelar", null);
                            dialog3 = builder3.create();
                            dialog3.setTitle(txtProducto.getText() + " " + txtTipo.getText());
                            dialog3.show();
                            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    switch (position) {
                                        case 0:
                                            break;
                                        case 1:
                                            LayoutInflater inflater = getLayoutInflater();
                                            ArrayList<String> historico = historical.consultarHistorico(txtProducto.getId() + "");
                                            int count = 0;
                                            View v2 = inflater.inflate(R.layout.mensaje_producto, null);
                                            TableLayout tl2 = (TableLayout) v2.findViewById(R.id.tlInventario);
                                            tl2.setStretchAllColumns(true);
                                            tl2.setShrinkAllColumns(true);
                                            if (historico.size() != 0) {
                                                for (int i = 0; i <= historico.size() - 4; i = i + 4) {
                                                    TableRow tr = new TableRow(Administrador.this);
                                                    if (count % 2 != 0) {
                                                        tr.setBackgroundResource(R.drawable.row_selector_r);
                                                        //tr.setBackgroundColor(Color.argb(15, 203, 47, 23));
                                                    } else {
                                                        tr.setBackgroundResource(R.drawable.row_selector_w);
                                                        //tr.setBackgroundColor(Color.WHITE);
                                                    }
                                                    TextView txtFecha = new TextView(Administrador.this);
                                                    txtFecha.setTypeface(font);
                                                    txtFecha.setText(historico.get(i));
                                                    txtFecha.setGravity(Gravity.CENTER);
                                                    //txtProducto.setTextSize(20);
                                                    tr.addView(txtFecha);
                                                    TextView txtNCant = new TextView(Administrador.this);
                                                    txtNCant.setTypeface(font);
                                                    txtNCant.setText(historico.get(i + 1));
                                                    txtNCant.setGravity(Gravity.CENTER);
                                                    txtNCant.setLines(2);
                                                    tr.addView(txtNCant);
                                                    TextView txtACant = new TextView(Administrador.this);
                                                    txtACant.setTypeface(font);
                                                    txtACant.setText(historico.get(i + 2));
                                                    txtACant.setGravity(Gravity.CENTER);
                                                    tr.addView(txtACant);
                                                    TextView txtAccion = new TextView(Administrador.this);
                                                    txtAccion.setTypeface(font);
                                                    txtAccion.setText(historico.get(i + 3));
                                                    txtAccion.setGravity(Gravity.CENTER);
                                                    tr.addView(txtAccion);

                                                    count++;
                                                    tl2.addView(tr, new TableLayout.LayoutParams(
                                                            TableLayout.LayoutParams.WRAP_CONTENT,
                                                            TableLayout.LayoutParams.WRAP_CONTENT));
                                                }
                                            }
                                            AlertDialog.Builder builder3 = new AlertDialog.Builder(Administrador.this);
                                            builder3.setView(v2);
                                            builder3.setPositiveButton("Aceptar", null).setNegativeButton("Cancelar", null);
                                            AlertDialog dialog3;
                                            dialog3 = builder3.create();
                                            dialog3.setTitle(txtProducto.getText() + " " + txtTipo.getText());
                                            dialog3.show();
                                            break;
                                    }
                                }
                            });
                            /*

                            */


                        } catch (Exception e) {
                            toast = Toast.makeText(Administrador.this, e.getMessage(), Toast.LENGTH_LONG);
                            toast.show();
                            e.printStackTrace();
                        }

                        return false;
                    }
                });
                tl.addView(tr, new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));

            }
        } else {
            TableRow tr_head = (TableRow) findViewById(R.id.trhead);
            tr_head.removeAllViews();
            //tr_head.setId(0);
            tr_head.setBackgroundColor(Color.rgb(203, 47, 23));
            tr_head.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
            TextView lblMensaje = new TextView(this);
            //lblMensaje.setId(20);
            lblMensaje.setTypeface(font);
            lblMensaje.setText("Ningun registro Almacenado");
            lblMensaje.setTextColor(Color.WHITE);
            lblMensaje.setTextSize(20);
            lblMensaje.setGravity(Gravity.CENTER);
            lblMensaje.setPadding(3, 3, 3, 3);
            tr_head.addView(lblMensaje);// añadir la columna a la fila de la tabla aquí
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAgregarUsr:
                Intent i = getIntent();
                i.putExtra("pos", 2);
                startActivity(i);
                break;
            case R.id.btnLimpiar:
                edtNombre.setText("");
                edtApellido.setText("");
                edtTelefono.setText("");
                edtDireccion.setText("");
                edtUsuario.setText("");
                edtPass.setText("");
                break;
            case R.id.btnAgregarUsuarios:
                boolean validacion, validacion2, validacion3, validacion4, validacion5, validacion6, validacion7, validacion8, validacion9;
                validacion = validacion(edtNombre.getText().toString());
                validacion2 = validacion(edtApellido.getText().toString());
                validacion3 = validacion(edtTelefono.getText().toString());
                validacion4 = validacion(edtDireccion.getText().toString());
                validacion5 = srol.getSelectedItem() != null;
                validacion6 = sCiudad.getSelectedItem() != null;
                validacion7 = validacion(edtUsuario.getText().toString());
                validacion8 = validacion(edtPass.getText().toString());
                Encrypting encrypting = new Encrypting();

                if (validacion && validacion2 && validacion3 && validacion4 && validacion5 && validacion6 && validacion7 && validacion8) {
                    UserVO userVO = new UserVO();

                    try {
                        userVO.setNames(edtNombre.getText().toString());
                        userVO.setLast_name(edtApellido.getText().toString());
                        userVO.setPhone(edtTelefono.getText().toString());
                        userVO.setAddress(edtDireccion.getText().toString());
                        userVO.setUser(edtUsuario.getText().toString());
                        userVO.setPass(encrypting.getStringEncrypted(edtPass.getText().toString()));
                        userVO.setIdRol(srol.getSelectedItemPosition() + 1);
                        userVO.setIdCity(sCiudad.getSelectedItemPosition() + 1);
                        Log.e("user", userVO.toString());
                        JSONArray ja = user.agregarUsuario(userVO);
                        if (ja != null) {
                            String mensaje = ja.getString(0);
                            if (mensaje.contains("The record has been inserted")) {
                                edtNombre.setText("");
                                edtApellido.setText("");
                                edtTelefono.setText("");
                                edtDireccion.setText("");
                                edtUsuario.setText("");
                                edtPass.setText("");
                                toast = Toast.makeText(this, R.string.Usuario_Agregado, Toast.LENGTH_LONG);
                                toast.show();
                            }
                        } else {
                            toast = Toast.makeText(this, R.string.ErrorServidor, Toast.LENGTH_LONG);
                            toast.show();

                        }
                    } catch (Exception e) {
                        toast = Toast.makeText(Administrador.this, e.getMessage(), Toast.LENGTH_LONG);
                        toast.show();
                        e.printStackTrace();
                    }

                } else {
                    if (!validacion) {
                        edtNombre.setBackgroundResource(R.drawable.borde_error);
                        edtNombre.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                edtNombre.setBackgroundResource(R.drawable.edittext_rounded_corners);
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });

                    }
                    if (!validacion2) {
                        edtApellido.setBackgroundResource(R.drawable.borde_error);
                        edtApellido.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                edtApellido.setBackgroundResource(R.drawable.edittext_rounded_corners);
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });

                    }
                    if (!validacion3) {

                        edtTelefono.setBackgroundResource(R.drawable.borde_error);
                        edtTelefono.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                edtTelefono.setBackgroundResource(R.drawable.edittext_rounded_corners);
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
                    }
                    if (!validacion4) {
                        edtDireccion.setBackgroundResource(R.drawable.borde_error);
                        edtDireccion.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                edtDireccion.setBackgroundResource(R.drawable.edittext_rounded_corners);
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
                    }
                    if (!validacion7) {
                        edtUsuario.setBackgroundResource(R.drawable.borde_error);
                        edtUsuario.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                edtUsuario.setBackgroundResource(R.drawable.edittext_rounded_corners);
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
                    }
                    if (!validacion8) {
                        edtPass.setBackgroundResource(R.drawable.borde_error);
                        edtPass.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                edtPass.setBackgroundResource(R.drawable.edittext_rounded_corners);
                            }

                            @Override
                            public void afterTextChanged(Editable s) {

                            }
                        });
                    }
                    toast = Toast.makeText(Administrador.this, R.string.llenar_campos, Toast.LENGTH_LONG);
                    toast.show();


                }
                break;

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    class asynclogin extends AsyncTask<String, String, String> {
        char pos;

        protected void onPreExecute() {
            pbUsu = (ProgressBar) findViewById(R.id.pbUsuarios);
            lyUsr = (ScrollView) findViewById(R.id.svUsuarios);
            pbInv = (ProgressBar) findViewById(R.id.pbInventario);
            lyInv = (ScrollView) findViewById(R.id.svInventario);
            type = new TypeDAO(Administrador.this);
            material = new MaterialDAO(Administrador.this);
            producto = new ProductDAO(Administrador.this);
            stand = new StandDAO(Administrador.this);
            city = new CityDAO(Administrador.this);
            user = new UserDAO(Administrador.this);
            rol = new RolDAO(Administrador.this);
            historical = new HistoricalSupplyDAO(Administrador.this);

        }

        protected String doInBackground(String... params) {
            //enviamos y recibimos y analizamos los datos en segundo plano.
            String mensaje = "";
            pos = params[0].charAt(0);
            try {
                switch (pos) {
                    case '0':
                        lyUsr.setVisibility(View.INVISIBLE);
                        pbUsu.setVisibility(ProgressBar.VISIBLE);

                        rol.agregarRoles();
                        city.agregarCiudadesLocal();
                        user.agregarUsuarios();
                        break;
                    case '1':
                        lyInv.setVisibility(View.INVISIBLE);
                        pbInv.setVisibility(ProgressBar.VISIBLE);
                        type.agregarTipo();
                        material.agregarMaterialLocal();
                        producto.agregarProducto();
                        historical.agregarHistorico();
                        stand.agregarStand();
                        producto.agregarInventario();
                        mensaje = "";
                        break;
                    case '2':
                        break;
                    case '3':

                        break;

                }

            } catch (Exception e) {
                mensaje = e.getMessage();
                e.printStackTrace();

            }
            return mensaje;

        }

        /*Una vez terminado doInBackground segun lo que halla ocurrido
         pasamos a la sig. activity
         o mostramos error*/
        protected void onPostExecute(String result) {

            switch (pos) {
                case '0':
                    try {
                        if (result.equals("")) {
                            usuarios();
                        } else {
                            Toast to = Toast.makeText(Administrador.this, "Error " + result, Toast.LENGTH_LONG);
                            to.show();
                        }

                    } catch (Exception e) {
                        toast = Toast.makeText(Administrador.this, e.getMessage(), Toast.LENGTH_LONG);
                        toast.show();
                        e.printStackTrace();
                    }
                    lyUsr.setVisibility(View.VISIBLE);
                    pbUsu.setVisibility(ProgressBar.INVISIBLE);
                    break;
                case '1':
                    try {
                        inventario();
                        lyInv.setVisibility(View.VISIBLE);
                        pbInv.setVisibility(ProgressBar.INVISIBLE);
                    } catch (Exception e) {
                        toast = Toast.makeText(Administrador.this, e.getMessage(), Toast.LENGTH_LONG);
                        toast.show();
                        e.getMessage();
                    }
                    break;
                case '2':
                    break;
                case '3':
                    break;

            }


        }
    }

    public boolean validacion(String text) {
        boolean val;
        val = text != null && !text.equals("") && !text.equals(" ");
        return val;
    }

    public void agregarUsuarios() throws Exception {
        type = new TypeDAO(Administrador.this);
        material = new MaterialDAO(Administrador.this);
        producto = new ProductDAO(Administrador.this);
        stand = new StandDAO(Administrador.this);
        city = new CityDAO(Administrador.this);
        user = new UserDAO(Administrador.this);
        rol = new RolDAO(Administrador.this);
        Button btnAgregarUsr = (Button) findViewById(R.id.btnAgregarUsuarios);
        btnAgregarUsr.setOnClickListener(this);
        btnAgregarUsr.setTypeface(font);
        Button btnLipiarUsr = (Button) findViewById(R.id.btnLimpiar);
        btnLipiarUsr.setOnClickListener(this);
        btnLipiarUsr.setTypeface(font);

        ArrayAdapter<String> adpRol, adpCiudad;
        AlertDialog.Builder builder;
        AlertDialog dialog;
        ArrayList<String> opc;


        txtTitulo = (TextView) findViewById(R.id.txtTitulo);
        txtTitulo.setTypeface(font);
        txtTitulo.setText(R.string.IngresarUsuario);

        txtNombres = (TextView) findViewById(R.id.txtNombres);
        txtNombres.setTypeface(font);

        txtApellidos = (TextView) findViewById(R.id.txtApellido);
        txtApellidos.setTypeface(font);

        txtTelefono = (TextView) findViewById(R.id.txtTelefono);
        txtTelefono.setTypeface(font);

        txtDireccion = (TextView) findViewById(R.id.txtDireccion);
        txtDireccion.setTypeface(font);

        txtUsuario = (TextView) findViewById(R.id.txtUsuario);
        txtUsuario.setTypeface(font);

        txtRol = (TextView) findViewById(R.id.txtRol);
        txtRol.setTypeface(font);

        txtCiudad = (TextView) findViewById(R.id.txtCiudad);
        txtCiudad.setTypeface(font);

        txtPass = (TextView) findViewById(R.id.txtPass);
        txtPass.setTypeface(font);

        edtNombre = (EditText) findViewById(R.id.edtNombres);
        edtNombre.setTypeface(font);

        edtApellido = (EditText) findViewById(R.id.edtApellidos);
        edtApellido.setTypeface(font);

        edtTelefono = (EditText) findViewById(R.id.edtTelefono);
        edtTelefono.setTypeface(font);

        edtDireccion = (EditText) findViewById(R.id.edtDireccion);
        edtDireccion.setTypeface(font);

        srol = (Spinner) findViewById(R.id.sRol);
        opc = rol.consultarRoles();
        //opc.add(us.get(5));
        adpRol = new ArrayAdapter<String>(Administrador.this, android.R.layout.simple_list_item_1, opc);
        srol.setAdapter(adpRol);

        sCiudad = (Spinner) findViewById(R.id.sCiudad);
        opc = city.consultarCiudades();
        adpCiudad = new ArrayAdapter<String>(Administrador.this, android.R.layout.simple_list_item_1, opc);
        sCiudad.setAdapter(adpCiudad);

        edtUsuario = (EditText) findViewById(R.id.edtUsuario);
        edtUsuario.setTypeface(font);

        edtPass = (EditText) findViewById(R.id.edtPass);
        edtPass.setTypeface(font);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getSupportMenuInflater();
        // inflater.inflate(R.menu.menu2, menu);
        SubMenu subMenu = menu.addSubMenu("Config");
        subMenu.add(R.string.Roles).setIcon(R.drawable.ic_action_users);
        //subMenu.add(R.string.Materiales).setIcon(R.drawable.ic_action_users);
        subMenu.add(R.string.Ciudades).setIcon(R.drawable.ic_location_city_black_48dp);
        MenuItem subMenu1Item = subMenu.getItem();
        subMenu1Item.setIcon(R.drawable.ic_action_core_overflow);
        subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.getItemId();
        if (item.getTitle().equals(getResources().getString(R.string.Ciudades))) {
            Intent i2 = new Intent(this, Ciudades.class);
            startActivity(i2);

        }
        return super.onOptionsItemSelected(item);
    }
}
