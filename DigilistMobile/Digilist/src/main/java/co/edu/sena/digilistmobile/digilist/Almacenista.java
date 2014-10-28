package co.edu.sena.digilistmobile.digilist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;


public class Almacenista implements AdapterView.OnItemSelectedListener {
    private View v;
    private Context c;
    private ViewPager vp;
    private ArrayAdapter<String> adaptadorProductos;
    private ProgressDialog progressDialog = null;
    private AutoCompleteTextView auproducto;
    private TextView lvlTipo, lvlMaterial, lvlTamano;
    private EditText edtcantidad;
    Button binfo, binfocli, bedit, btnLimpiar, btnAgregar;
    private TableLayout tl;
    private Producto producto;
    private Typeface font;
    ArrayList<String> aTamanio;
    private Activity a;
    private Spinner sTipo, sMaterial, sTamanio;
    Tipo type;
    Material material;
    Stand stand;

    public Almacenista(View v, Context c, Activity a) {
        this.v = v;
        this.c = c;
        this.a = a;


    }

    public void addInventario() {
        new asynclogin().execute(1 + "");
        auproducto = (AutoCompleteTextView) v.findViewById(R.id.acProductos);
        auproducto.setTypeface(font);
        lvlTipo = (TextView) v.findViewById(R.id.lvlTipo);
        lvlTamano = (TextView) v.findViewById(R.id.lvlTamano);
        lvlTamano.setTypeface(font);
        binfo = (Button) v.findViewById(R.id.btnInfo);
        binfo.setTypeface(font);
        binfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final String[] prodSel = {""};
                    ArrayList lis = producto.consultarProductos();
                    final String[] pro = new String[lis.size() / 4];
                    int y = 0;
                    for (int j = 0; j < lis.size(); j = j + 4) {
                        pro[y] = lis.get(j).toString() + " - " + lis.get(j + 2).toString();
                        y++;
                    }


                    LayoutInflater inflater = a.getLayoutInflater();
                    View v2 = inflater.inflate(R.layout.mensaje_producto, null);
                    AlertDialog.Builder builder3 = new AlertDialog.Builder(c);
                    builder3.setSingleChoiceItems(pro, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            prodSel[0] = pro[which].substring(0, pro[which].indexOf("-") - 1);

                        }
                    });
                    builder3.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ArrayList lis = producto.consultarProducto("product.name", prodSel[0]);
                            auproducto.setText(prodSel[0]);
                            lvlTipo.setText("" + lis.get(1));
                            lvlTamano.setText("" + lis.get(2));
                            lvlMaterial.setText("" + lis.get(3));
                            edtcantidad.setText("1");

                        }
                    }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
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
                    e.printStackTrace();
                }


            }
        });
        lvlMaterial = (TextView) v.findViewById(R.id.lvlMaterial);
        lvlMaterial.setTypeface(font);
        edtcantidad = (EditText) v.findViewById(R.id.edtcantidad);
        edtcantidad.setTypeface(font);
        auproducto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList lis = producto.consultarProducto("product.name", auproducto.getText().toString());
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


        btnLimpiar = (Button) v.findViewById(R.id.btnLimpiar);
        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auproducto.setText("");
                lvlTipo.setText("");
                lvlTamano.setText("");
                lvlMaterial.setText("");
                edtcantidad.setText("");
            }
        });
        btnAgregar = (Button) v.findViewById(R.id.btnAgregarInve);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = null;
                Vibrator vibrator = (Vibrator) c.getSystemService(Context.VIBRATOR_SERVICE);
                boolean validacion = false, validacion2 = false;

                if (validacion(auproducto.getText().toString())) {
                    validacion = true;

                } else {
                    vibrator.vibrate(200);
                    LayoutInflater inflater = a.getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custom_toast_error,
                            (ViewGroup) v.findViewById(R.id.toast_layout_root));
                    TextView text = (TextView) layout.findViewById(R.id.text);
                    text.setTextColor(Color.BLACK);
                    text.setText(R.string.prodvac);
                    toast = new Toast(c.getApplicationContext());
                    //toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                    validacion = false;

                }

                if (validacion(edtcantidad.getText().toString())) {
                    validacion2 = true;

                } else {
                    vibrator.vibrate(200);
                    LayoutInflater inflater = a.getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custom_toast_error,
                            (ViewGroup) v.findViewById(R.id.toast_layout_root));
                    TextView text = (TextView) layout.findViewById(R.id.text);
                    text.setTextColor(Color.BLACK);
                    text.setText(R.string.prodvac);
                    toast = new Toast(c.getApplicationContext());
                    //toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                    validacion2 = false;

                }
                if (validacion && validacion2) {

                    try {
                        producto.agregarInventario("");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }


            }
        });

    }

    public void productos() {
        sTipo = (Spinner) v.findViewById(R.id.sTipo);
        sMaterial = (Spinner) v.findViewById(R.id.sMaterial);
        sTamanio = (Spinner) v.findViewById(R.id.sTamanio);
        new asynclogin().execute(2 + "");
        sTamanio.setOnItemSelectedListener(this);
        sTipo.setOnItemSelectedListener(this);


    }

    public void inventario() {
        tl = (TableLayout) v.findViewById(R.id.tlInventario);
        tl.setStretchAllColumns(true);
        tl.setShrinkAllColumns(true);
        TextView lblProducto = (TextView) v.findViewById(R.id.lblProducto);
        lblProducto.setTypeface(font);
        TextView lblTipo = (TextView) v.findViewById(R.id.lblTipo);
        lblTipo.setTypeface(font);
        TextView lblTamanio = (TextView) v.findViewById(R.id.lblTamanio);
        lblTamanio.setTypeface(font);
        TextView lblMaterial = (TextView) v.findViewById(R.id.lblMaterial);
        lblMaterial.setTypeface(font);
        TextView lblCantidad = (TextView) v.findViewById(R.id.lblCantidad);
        lblCantidad.setTypeface(font);
        new asynclogin().execute(3 + "");

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.sTipo:
                if (!sTipo.getSelectedItem().toString().equals("Seleccione uno")) {
                    Tipo type = new Tipo(c);
                    Spinner sTamanio = (Spinner) v.findViewById(R.id.sTamanio);
                    aTamanio = type.consultarTiposTamanio(sTipo.getSelectedItem().toString());
                    ArrayAdapter<String> adaptadorTamanio = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, aTamanio);//creamos el adaptador de los spinner agregando los Arraylist
                    sTamanio.setAdapter(adaptadorTamanio);
                }
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
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

    class asynclogin extends AsyncTask<String, String, String> {
        char pos;
        ProgressBar pb = (ProgressBar) v.findViewById(R.id.progressBar);
        LinearLayout layoutver = (LinearLayout) v.findViewById(R.id.lyVentas);
        ProgressBar pbPro = (ProgressBar) v.findViewById(R.id.progressBarProducto);
        LinearLayout lyPro = (LinearLayout) v.findViewById(R.id.lyProducto);

        protected void onPreExecute() {
            lyPro = (LinearLayout) v.findViewById(R.id.lyProducto);
            pbPro = (ProgressBar) v.findViewById(R.id.progressBarProducto);
            type = new Tipo(c);
            material = new Material(c);
            producto = new Producto(c);
            stand = new Stand(c);

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
                        material.agregarMaterial();
                        producto.agregarProducto();
                        stand.agregarStand();
                        producto.agregarInventario();
                        ArrayList<String> AProductos = producto.consultarProductos();//retornamos la consulta de inventario
                        ArrayList<String> Apr = new ArrayList<String>();
                        for (int i = 0; i <= AProductos.size() - 4; i = i + 4) {
                            Apr.add(AProductos.get(i));
                        }

                        adaptadorProductos = new ArrayAdapter<String>(c, android.R.layout.simple_list_item_1, Apr);//creamos el adaptador de los spinner agregando los Arraylist

                        break;
                    case '2':
                        lyPro.setVisibility(View.INVISIBLE);
                        pbPro.setVisibility(ProgressBar.VISIBLE);
                        type.agregarTipo();
                        material.agregarMaterial();
                        producto.agregarProducto();
                        stand.agregarStand();
                        producto.agregarInventario();
                        break;
                    case '3':
                        break;
                }

            } catch (Exception e) {
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
                    auproducto.setAdapter(adaptadorProductos);
                    layoutver.setVisibility(View.VISIBLE);
                    pb.setVisibility(ProgressBar.INVISIBLE);
                    break;
                case '2':
                    lyPro.setVisibility(View.VISIBLE);
                    pbPro.setVisibility(ProgressBar.INVISIBLE);
                    ArrayList<String> aTypes = type.consultarTipos();//retornamos la consulta
                    ArrayAdapter<String> adaptadorTypes = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, aTypes);//creamos el adaptador de los spinner agregando los Arraylist
                    sTipo.setAdapter(adaptadorTypes);//incluimos el adaptados a los spinner
                    ArrayList<String> aMaterial = material.consultarMateriales();//retornamos la consulta
                    ArrayAdapter<String> adaptadorMaterial = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, aMaterial);//creamos el adaptador de los spinner agregando los Arraylist
                    sMaterial.setAdapter(adaptadorMaterial);//incluimos el adaptados a los spinner

                    break;
                case '3':
                    ArrayList<String> productos = producto.consultarInventarios();
                    int count = 0;
                    if (productos.size() != 0) {
                        for (int i = 0; i <= productos.size() - 5; i = i + 5) {
                            TableRow tr = new TableRow(c);
                            if (count % 2 != 0) {
                                tr.setBackgroundColor(Color.argb(15, 203, 47, 23));
                            } else {
                                tr.setBackgroundColor(Color.WHITE);
                            }
                            TextView txtProducto = new TextView(c);
                            txtProducto.setTypeface(font);
                            txtProducto.setText(productos.get(i));
                            txtProducto.setGravity(Gravity.CENTER);
                            tr.addView(txtProducto);
                            TextView txtTipo = new TextView(c);
                            txtTipo.setTypeface(font);
                            txtTipo.setText(productos.get(i + 1));
                            txtTipo.setGravity(Gravity.CENTER);
                            tr.addView(txtTipo);
                            TextView txtTamanio = new TextView(c);
                            txtTamanio.setTypeface(font);
                            txtTamanio.setText(productos.get(i + 2));
                            txtTamanio.setGravity(Gravity.CENTER);
                            tr.addView(txtTamanio);
                            TextView txtMaterial = new TextView(c);
                            txtMaterial.setTypeface(font);
                            txtMaterial.setText(productos.get(i + 3));
                            txtMaterial.setGravity(Gravity.CENTER);
                            tr.addView(txtMaterial);
                            TextView txtCantidad = new TextView(c);
                            txtCantidad.setTypeface(font);
                            txtCantidad.setText(productos.get(i + 4));
                            txtCantidad.setGravity(Gravity.CENTER);
                            tr.addView(txtCantidad);
                            count++;
                            tl.addView(tr, new TableLayout.LayoutParams(
                                    TableLayout.LayoutParams.WRAP_CONTENT,
                                    TableLayout.LayoutParams.WRAP_CONTENT));

                        }
                    } else {
                        TableRow tr_head = (TableRow) v.findViewById(R.id.trhead);
                        tr_head.removeAllViews();
                        //tr_head.setId(0);
                        tr_head.setBackgroundColor(Color.rgb(203, 47, 23));
                        tr_head.setLayoutParams(new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.MATCH_PARENT,
                                TableLayout.LayoutParams.WRAP_CONTENT));
                        TextView lblMensaje = new TextView(c);
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

                    break;
            }

        }
    }

}
