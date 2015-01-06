package co.edu.sena.digilistmobile.digilist;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;

import java.util.ArrayList;

import co.edu.sena.digilistmobile.digilist.dao.CityDAO;
import co.edu.sena.digilistmobile.digilist.dao.MaterialDAO;
import co.edu.sena.digilistmobile.digilist.dao.ProductDAO;
import co.edu.sena.digilistmobile.digilist.dao.RolDAO;
import co.edu.sena.digilistmobile.digilist.dao.StandDAO;
import co.edu.sena.digilistmobile.digilist.dao.TypeDAO;
import co.edu.sena.digilistmobile.digilist.dao.UserDAO;


public class Administrador extends SherlockActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {


    Typeface font;
    private ProductDAO producto;
    private TypeDAO type;
    private MaterialDAO material;
    private StandDAO stand;
    private CityDAO city;
    ScrollView lyInv;
    private UserDAO user;
    ProgressBar pbUsu;
    private RolDAO rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int op = 0;
        font = Typeface.createFromAsset(this.getAssets(), "Station.ttf");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            op = extras.getInt("pos");

        }
        switch (op) {
            case 0:
                try {
                    setContentView(R.layout.usuarios);
                    city = new CityDAO(this);
                    user = new UserDAO(this);
                    rol = new RolDAO(this);
                    rol.agregarRoles();
                    city.agregarCiudades();
                    user.agregarUsuarios();
                    usuarios();
                    /*type = new TypeDAO(this);
                    material = new MaterialDAO(this);
                    producto = new ProductDAO(this);
                    stand = new StandDAO(this);*/
                    //historical = new HistoricalSupplyDAO(this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }

    }

    public void usuarios() throws Exception {
        pbUsu = (ProgressBar) findViewById(R.id.pbUsuarios);
        lyInv = (ScrollView) findViewById(R.id.svUsuarios);
        lyInv.setVisibility(View.INVISIBLE);
        pbUsu.setVisibility(ProgressBar.VISIBLE);
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
                TableRow tr = new TableRow(Administrador.this);
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
                txtNombre.setTypeface(font);
                txtNombre.setGravity(Gravity.CENTER);
                //txtProducto.setTextSize(20);
                tr.addView(txtNombre);
                final TextView txtApellido = new TextView(Administrador.this);
                txtApellido.setTypeface(font);
                txtApellido.setText(usuarios.get(i + 2));
                txtApellido.setGravity(Gravity.CENTER);
                txtApellido.setTypeface(font);
                txtApellido.setLines(2);
                tr.addView(txtApellido);
                TextView txtUsuario = new TextView(Administrador.this);
                txtUsuario.setTypeface(font);
                txtUsuario.setText(usuarios.get(i + 3));
                txtUsuario.setGravity(Gravity.CENTER);
                txtUsuario.setTypeface(font);
                tr.addView(txtUsuario);
                TextView txtRol = new TextView(Administrador.this);
                txtRol.setTypeface(font);
                txtRol.setText(usuarios.get(i + 4));
                txtRol.setGravity(Gravity.CENTER);
                txtRol.setTypeface(font);
                tr.addView(txtRol);
                count++;
                tr.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        try {
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
                            final AlertDialog dialog3;
                            builder3.setPositiveButton("Aceptar", null).setNegativeButton("Cancelar", null);
                            dialog3 = builder3.create();
                            dialog3.setTitle(txtNombre.getText() + " " + txtApellido.getText());
                            dialog3.show();
                            try {
                                dialog3.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Boolean wantToCloseDialog = false;
                                        try {
                                            if (txtNombre.getText().toString() != null) {
                                                wantToCloseDialog = false;
                                            }
                                            //Do stuff, possibly set wantToCloseDialog to true then...
                                            if (wantToCloseDialog) {
                                                dialog3.dismiss();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            } catch (NullPointerException ee) {
                                ee.printStackTrace();
                            }

                        } catch (Exception e) {
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
        lyInv.setVisibility(View.VISIBLE);
        pbUsu.setVisibility(ProgressBar.INVISIBLE);

    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}