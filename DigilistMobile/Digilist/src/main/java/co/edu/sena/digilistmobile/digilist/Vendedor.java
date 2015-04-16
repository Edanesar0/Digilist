package co.edu.sena.digilistmobile.digilist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
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

import java.util.ArrayList;

import co.edu.sena.digilistmobile.digilist.dao.CityDAO;
import co.edu.sena.digilistmobile.digilist.dao.ClientDAO;
import co.edu.sena.digilistmobile.digilist.dao.HistoricalSupplyDAO;
import co.edu.sena.digilistmobile.digilist.dao.MaterialDAO;
import co.edu.sena.digilistmobile.digilist.dao.ProductDAO;
import co.edu.sena.digilistmobile.digilist.dao.StandDAO;
import co.edu.sena.digilistmobile.digilist.dao.TypeDAO;
import co.edu.sena.digilistmobile.digilist.utils.conexiones.ConexionLocal;

/**
 * Created by Axxuss on 12/03/2015.
 */
public class Vendedor extends SherlockActivity implements View.OnClickListener {
    Typeface font;
    private TableLayout tl;
    int op;
    int count = 0;
    Toast toast;
    private ProductDAO producto;
    private TypeDAO type;
    private MaterialDAO material;
    private StandDAO stand;
    private ClientDAO client;
    private ActionBar ab;
    private CityDAO ciudad;
    private Menu menu;
    private boolean visGuar = false;
    private HistoricalSupplyDAO historical;
    private AutoCompleteTextView auproducto, auCliente;
    private ArrayAdapter<String> adaptadorProductos, adpCliente;
    private ArrayList<String> datos = new ArrayList<String>();
    private TextView lvlTipo, lvlMaterial, lvlTamano;
    private EditText edtcantidad, edtNombreProducto, edtReferencia;
    private Button binfo, binfocli, bedit, btnLimpiar, btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ab = getSupportActionBar();//instancia
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);//Atributos titulo boton home y flecha de acompañamiento de home
        ab.setHomeButtonEnabled(true);//activar el boton home
        ab.setDisplayShowHomeEnabled(true);//se pueda ver el boton home
        ab.setIcon(R.drawable.ic_launcher);//se le adiciona el icono
        font = Typeface.createFromAsset(this.getAssets(), "Station.ttf");
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            op = extras.getInt("pos");
        }
        switch (op) {
            case 1:
                setContentView(R.layout.ingreso_ventas);
                addVentas();
                break;
            case 2:
                setContentView(R.layout.inventario);
                inventario();
                break;
        }
    }

    public void addVentas() {
        new asynclogin().execute(1 + "");
        auproducto = (AutoCompleteTextView) findViewById(R.id.acProductos);
        auCliente = (AutoCompleteTextView) findViewById(R.id.acCliente);
        auproducto.setTypeface(font);
        auCliente.setTypeface(font);
        lvlTipo = (TextView) findViewById(R.id.lvlTipo);
        lvlTamano = (TextView) findViewById(R.id.lvlTamano);
        lvlTamano.setTypeface(font);
        binfo = (Button) findViewById(R.id.btnInfo);
        binfo.setTypeface(font);
        binfo.setOnClickListener(Vendedor.this);
        binfocli = (Button) findViewById(R.id.btnInfoCliente);
        binfocli.setTypeface(font);
        binfocli.setOnClickListener(Vendedor.this);
        lvlMaterial = (TextView) findViewById(R.id.lvlMaterial);
        lvlMaterial.setTypeface(font);
        edtcantidad = (EditText) findViewById(R.id.edtcantidad);
        edtcantidad.setTypeface(font);
        auproducto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> lis = producto.consultarProducto("product.name", auproducto.getText().toString());
                lvlTipo.setText("" + lis.get(1));
                lvlTamano.setText("" + lis.get(2));
                lvlMaterial.setText("" + lis.get(3));
                edtcantidad.setText("1");
            }
        });

        auproducto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lvlTipo.setText("");
                lvlTamano.setText("");
                lvlMaterial.setText("");
                edtcantidad.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btnLimpiar = (Button) findViewById(R.id.btnLimpiar);
        btnLimpiar.setOnClickListener(this);
        btnAgregar = (Button) findViewById(R.id.btnAgregarVenta);
        btnAgregar.setOnClickListener(this);

    }

    public void inventario() {
        tl = (TableLayout) findViewById(R.id.tlInventario);
        tl.setStretchAllColumns(true);
        tl.setShrinkAllColumns(true);
        TextView lblProducto = (TextView) findViewById(R.id.lblProducto);
        lblProducto.setTypeface(font);
        TextView lblTipo = (TextView) findViewById(R.id.lblTipo);
        lblTipo.setTypeface(font);
        TextView lblTamanio = (TextView) findViewById(R.id.lblTamanio);
        lblTamanio.setTypeface(font);
        TextView lblMaterial = (TextView) findViewById(R.id.lblMaterial);
        lblMaterial.setTypeface(font);
        TextView lblCantidad = (TextView) findViewById(R.id.lblCantidad);
        lblCantidad.setTypeface(font);
        new asynclogin().execute(3 + "");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnInfo:
                try {
                    final String[] prodSel = {""};
                    ArrayList<String> lis = producto.consultarProductos();
                    final String[] pro = new String[lis.size() / 4];
                    int y = 0;
                    for (int j = 0; j < lis.size(); j = j + 4) {
                        pro[y] = lis.get(j) + " - " + lis.get(j + 2);
                        y++;
                    }


                    LayoutInflater inflater = Vendedor.this.getLayoutInflater();
                    View v2 = inflater.inflate(R.layout.mensaje_productos, null);
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(Vendedor.this);
                    builder3.setSingleChoiceItems(pro, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            prodSel[0] = pro[which].substring(0, pro[which].indexOf("-") - 1);

                        }
                    });
                    builder3.setPositiveButton(Vendedor.this.getResources().getString(R.string.Aceptar), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ArrayList lis = producto.consultarProducto("product.name", prodSel[0]);
                            auproducto.setText(prodSel[0]);
                            lvlTipo.setText("" + lis.get(1));
                            lvlTamano.setText("" + lis.get(2));
                            lvlMaterial.setText("" + lis.get(3));
                            edtcantidad.setText("1");

                        }
                    }).setNegativeButton(Vendedor.this.getResources().getString(R.string.Cancelar), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            auproducto.setText("");
                            lvlTipo.setText("");
                            lvlTamano.setText("");
                            lvlMaterial.setText("");
                            edtcantidad.setText("");
                        }
                    });
                    AlertDialog dialog3;
                    dialog3 = builder3.create();
                    dialog3.setTitle("Productos");
                    dialog3.show();


                } catch (Exception e) {
                    toast = Toast.makeText(Vendedor.this, e.getMessage(), Toast.LENGTH_LONG);
                    toast.show();
                    e.printStackTrace();
                }
                break;


            case R.id.btnInfoCliente:
                try {
                    final String[] prodSel = {""};
                    ArrayList<String> lis = client.consultarClientes();
                    final String[] pro = new String[lis.size()];
                    int y = 0;
                    for (int j = 0; j < lis.size(); j++) {
                        pro[y] = lis.get(j);
                        y++;
                    }


                    LayoutInflater inflater = Vendedor.this.getLayoutInflater();
                    View v2 = inflater.inflate(R.layout.mensaje_productos, null);
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(Vendedor.this);
                    builder3.setSingleChoiceItems(pro, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            prodSel[0] = pro[which].substring(0, pro[which].indexOf("-") - 1);

                        }
                    });
                    builder3.setPositiveButton(Vendedor.this.getResources().getString(R.string.Aceptar), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            try{
                            ArrayList lis = client.consultarCliente("name", prodSel[0]);
                            auCliente.setText(lis.get(1) + " - " + lis.get(2));
                            }catch (Exception e){

                            }

                        }
                    }).setNegativeButton(Vendedor.this.getResources().getString(R.string.Cancelar), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            auproducto.setText("");
                            lvlTipo.setText("");
                            lvlTamano.setText("");
                            lvlMaterial.setText("");
                            edtcantidad.setText("");
                        }
                    });
                    AlertDialog dialog3;
                    dialog3 = builder3.create();
                    dialog3.setTitle("Clientes");
                    dialog3.show();


                } catch (Exception e) {
                    toast = Toast.makeText(Vendedor.this, e.getMessage(), Toast.LENGTH_LONG);
                    toast.show();
                    e.printStackTrace();
                }
                break;
            case R.id.btnAgregarVenta:

                Vibrator vibrator = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
                boolean validacion, validacion2, validacion3;


                if (validacion(auCliente.getText().toString())) {
                    validacion3 = true;

                } else {
                    vibrator.vibrate(200);
                    toast = Toast.makeText(this, "Campo Cliente vacio", Toast.LENGTH_LONG);
                    toast.show();
                    validacion3 = false;
                }

                if (validacion(auproducto.getText().toString())) {
                    validacion = true;

                } else {
                    vibrator.vibrate(200);
                    toast = Toast.makeText(this, getResources().getText(R.string.prodvac) + "", Toast.LENGTH_LONG);
                    toast.show();
                    validacion = false;
                }
                if (validacion(edtcantidad.getText().toString())) {
                    validacion2 = true;
                } else {
                    vibrator.vibrate(200);
                    toast = Toast.makeText(this, getResources().getText(R.string.canvac) + "", Toast.LENGTH_LONG);
                    toast.show();
                    validacion2 = false;
                }
                if (validacion && validacion2) {
                    try {

                        LinearLayout lyventa = (LinearLayout) findViewById(R.id.lyVenta);
                        View view = getLayoutInflater().inflate(R.layout.pedido, lyventa);
                        TextView txtNombre = (TextView) view.findViewById(R.id.txtCliente);
                        txtNombre.setText(auCliente.getText().toString() + "");
                        auCliente.setEnabled(false);
                        TableLayout tabla = (TableLayout) view.findViewById(R.id.tlPedido);
                        tabla.setStretchAllColumns(true);
                        tabla.setShrinkAllColumns(true);

                        final TableRow tr = new TableRow(Vendedor.this);

                        if (count % 2 != 0) {
                            tr.setBackgroundResource(R.drawable.row_selector_r);
                            //tr.setBackgroundColor(Color.argb(15, 203, 47, 23));
                        } else {
                            tr.setBackgroundResource(R.drawable.row_selector_w);
                            //tr.setBackgroundColor(Color.WHITE);
                        }

                        final TextView txtNombres = new TextView(Vendedor.this);
                        txtNombres.setTypeface(font);

                        txtNombres.setLines(3);
                        txtNombres.setTypeface(font);
                        txtNombres.setGravity(Gravity.CENTER);
                        //txtProducto.setTextSize(20);

                        final TextView txtMaterial = new TextView(Vendedor.this);
                        txtMaterial.setTypeface(font);
                        txtMaterial.setLines(3);

                        txtMaterial.setGravity(Gravity.CENTER);
                        txtMaterial.setTypeface(font);
                        txtMaterial.setLines(2);
                        final TextView txtCantidad = new TextView(Vendedor.this);
                        txtCantidad.setTypeface(font);
                        txtCantidad.setId(count);
                        txtCantidad.setLines(3);

                        txtCantidad.setGravity(Gravity.CENTER);
                        txtCantidad.setTypeface(font);
                        txtCantidad.setLines(2);
                        boolean val = true,ban = false;
                        int pos = 0;


                        if (datos.size() != 0) {
                            for (int i = 0; i < datos.size(); i++) {
                                if (datos.get(i).equals(auproducto.getText().toString() + " " + lvlMaterial.getText().toString())) {
                                    pos = i;
                                    val = false;
                                    ban=true;

                                } else {

                                    //i=datos.size();
                                }
                            }
                            if (!ban){
                                datos.add(count, auproducto.getText().toString() + " " + lvlMaterial.getText().toString());
                            }
                        } else
                            datos.add(count, auproducto.getText().toString() + " " + lvlMaterial.getText().toString());
                        if (val) {
                            txtNombres.setText(auproducto.getText());
                            txtMaterial.setText(lvlMaterial.getText());
                            txtCantidad.setText(edtcantidad.getText());

                            tr.addView(txtNombres);
                            tr.addView(txtMaterial);
                            tr.addView(txtCantidad);
                            count++;
                        } else {
                            TextView txtcantidad = (TextView) findViewById(pos);
                            txtcantidad.setText("" + (Integer.parseInt(txtcantidad.getText().toString()) + Integer.parseInt(edtcantidad.getText().toString())));
                        }

                        auproducto.setText("");
                        lvlMaterial.setText("");
                        edtcantidad.setText("");
                        tr.setLayoutParams(new TableRow.LayoutParams(
                                TableLayout.LayoutParams.FILL_PARENT,
                                TableLayout.LayoutParams.FILL_PARENT));
                        tabla.addView(tr, new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.FILL_PARENT,
                                TableLayout.LayoutParams.FILL_PARENT));

                        visGuar = true;

                        onPrepareOptionsMenu(menu);

                        
                        

                       /* JSONArray jspro = null;// producto.agregarInventario(auproducto.getText().toString(), Float.parseFloat(edtcantidad.getText().toString()));
                        String mensaje = jspro.getString(0);
                        if (mensaje.contains("There stock has been updated.")) {
                            auproducto.setText("");
                            lvlTipo.setText("");
                            lvlTamano.setText("");
                            lvlMaterial.setText("");
                            edtcantidad.setText("");
                            toast = Toast.makeText(this, R.string.Inventario_Agregado, Toast.LENGTH_SHORT);
                            Intent it = this.getIntent();
                            finish();
                            ConexionLocal conexionLocal = new ConexionLocal(this);
                            conexionLocal.abrir();
                            conexionLocal.limpiar();
                            conexionLocal.cerrar();
                            startActivity(it);
                            toast.show();
                        } else {
                            toast = Toast.makeText(this, R.string.ErrorServidor + "", Toast.LENGTH_LONG);
                            toast.show();

                        }*/
                    } catch (Exception e) {
                        toast = Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG);
                        toast.show();
                        e.printStackTrace();
                    }

                }
                break;
        }
    }


    class asynclogin extends AsyncTask<String, String, String> {
        char pos;
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        ProgressBar pbPro = (ProgressBar) findViewById(R.id.pbInventario);
        ScrollView lyPro = (ScrollView) findViewById(R.id.svInventario);
        LinearLayout layoutver = (LinearLayout) findViewById(R.id.lyVentas);

        protected void onPreExecute() {

            type = new TypeDAO(Vendedor.this);
            material = new MaterialDAO(Vendedor.this);
            producto = new ProductDAO(Vendedor.this);
            stand = new StandDAO(Vendedor.this);
            historical = new HistoricalSupplyDAO(Vendedor.this);
            client = new ClientDAO(Vendedor.this);
            ciudad = new CityDAO(Vendedor.this);


        }

        protected String doInBackground(String... params) {
            //enviamos y recibimos y analizamos los datos en segundo plano.
            pos = params[0].charAt(0);
            try {
                switch (pos) {
                    case '1':
                        layoutver.setVisibility(View.INVISIBLE);
                        pb.setVisibility(ProgressBar.VISIBLE);
                        type.agregarTipo();
                        material.agregarMaterialLocal();
                        producto.agregarProducto();
                        stand.agregarStand();
                        historical.agregarHistorico();
                        producto.agregarInventario();
                        ciudad.agregarCiudadesLocal();
                        client.agregarClienteLocal();
                        ArrayList<String> AProductos = producto.consultarProductos();//retornamos la consulta de inventario
                        ArrayList<String> Apr = new ArrayList<String>();
                        for (int i = 0; i <= AProductos.size() - 4; i = i + 4) {
                            Apr.add(AProductos.get(i));
                        }
                        adaptadorProductos = new ArrayAdapter<String>(Vendedor.this, android.R.layout.simple_list_item_1, Apr);//creamos el adaptador de los spinner agregando los Arraylist
                        ArrayList<String> aCliente = client.consultarClientes();//retornamos la consulta de inventario
                        ArrayList<String> aCli = new ArrayList<String>();
                        for (int i = 0; i < aCliente.size(); i++) {
                            aCli.add(aCliente.get(i));
                        }
                        adpCliente = new ArrayAdapter<String>(Vendedor.this, android.R.layout.simple_list_item_1, aCli);//creamos el adaptador de los spinner agregando los Arraylist

                        break;
                    case '3':
                        lyPro.setVisibility(View.INVISIBLE);
                        pbPro.setVisibility(ProgressBar.VISIBLE);
                        type.agregarTipo();
                        material.agregarMaterialLocal();
                        producto.agregarProducto();
                        historical.agregarHistorico();
                        stand.agregarStand();
                        producto.agregarInventario();
                        break;

                }

            } catch (Exception e) {
                //toast = Toast.makeText(c, e.getMessage(), Toast.LENGTH_LONG);
                //toast.show();
                e.printStackTrace();
            }
            return null;

        }

        /*Una vez terminado doInBackground segun lo que halla ocurrido
         pasamos a la sig. activity
         o mostramos error*/
        protected void onPostExecute(String result) {
            switch (pos) {
                case '1':
                    layoutver.setVisibility(View.VISIBLE);
                    pb.setVisibility(ProgressBar.INVISIBLE);
                    auproducto.setAdapter(adaptadorProductos);
                    auCliente.setAdapter(adpCliente);
                    break;
                case '3':
                    ArrayList<String> productos = producto.consultarInventarios();
                    int count = 0;
                    if (productos.size() != 0) {
                        for (int i = 0; i <= productos.size() - 6; i = i + 6) {
                            TableRow tr = new TableRow(Vendedor.this);
                            if (count % 2 != 0) {
                                tr.setBackgroundResource(R.drawable.row_selector_r);
                                //tr.setBackgroundColor(Color.argb(15, 203, 47, 23));
                            } else {
                                tr.setBackgroundResource(R.drawable.row_selector_w);
                                //tr.setBackgroundColor(Color.WHITE);
                            }
                            final TextView txtProducto = new TextView(Vendedor.this);
                            txtProducto.setTypeface(font);
                            txtProducto.setId(Integer.parseInt(productos.get(i + 5)));
                            txtProducto.setText(productos.get(i));
                            txtProducto.setGravity(Gravity.CENTER);
                            //txtProducto.setTextSize(20);
                            tr.addView(txtProducto);
                            final TextView txtTipo = new TextView(Vendedor.this);
                            txtTipo.setTypeface(font);
                            txtTipo.setText(productos.get(i + 1));
                            txtTipo.setGravity(Gravity.CENTER);
                            txtTipo.setLines(2);
                            tr.addView(txtTipo);
                            TextView txtTamanio = new TextView(Vendedor.this);
                            txtTamanio.setTypeface(font);
                            txtTamanio.setText(productos.get(i + 2));
                            txtTamanio.setGravity(Gravity.CENTER);
                            tr.addView(txtTamanio);
                            TextView txtMaterial = new TextView(Vendedor.this);
                            txtMaterial.setTypeface(font);
                            txtMaterial.setText(productos.get(i + 3));
                            txtMaterial.setGravity(Gravity.CENTER);
                            tr.addView(txtMaterial);
                            TextView txtCantidad = new TextView(Vendedor.this);
                            txtCantidad.setTypeface(font);
                            txtCantidad.setText(productos.get(i + 4));
                            txtCantidad.setGravity(Gravity.CENTER);
                            tr.addView(txtCantidad);
                            count++;
                            tr.setOnLongClickListener(null);
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
                        TextView lblMensaje = new TextView(Vendedor.this);
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
                    lyPro.setVisibility(View.VISIBLE);
                    pbPro.setVisibility(ProgressBar.INVISIBLE);
                    break;

            }

        }
    }

    public boolean validacion(String text) {
        boolean val;
        if (text != null) {
            if (!text.equals("")) {
                if (!text.equals(" ")) {
                    val = true;
                } else {
                    val = false;
                }
            } else {
                val = false;
            }
        } else {
            val = false;
        }
        return val;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        item.getItemId();
        if (item.getTitle().equals(getResources().getString(R.string.Guardar))) {

        }
        if (item.getTitle().equals(getResources().getString(R.string.Nueva_Venta))) {
            Intent i2 = new Intent(this, Ciudades.class);
            startActivity(i2);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (visGuar) {
            menu.clear();
            SubMenu subMenu = menu.addSubMenu("Op");
            MenuItem subMenu1Item = subMenu.getItem();
            subMenu.add(R.string.Guardar).setIcon(R.drawable.ic_action_save);
            subMenu.add(R.string.Nueva_Venta).setIcon(R.drawable.ic_action_list);
            subMenu1Item.setIcon(R.drawable.ic_action_overflow);
            subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }

        return super.onPrepareOptionsMenu(menu);
    }
}


