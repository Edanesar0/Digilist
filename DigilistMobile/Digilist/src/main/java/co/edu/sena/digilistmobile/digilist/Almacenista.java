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
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import co.edu.sena.digilistmobile.digilist.dao.MaterialDAO;
import co.edu.sena.digilistmobile.digilist.dao.ProductDAO;
import co.edu.sena.digilistmobile.digilist.dao.StandDAO;
import co.edu.sena.digilistmobile.digilist.dao.TypeDAO;
import co.edu.sena.digilistmobile.digilist.vo.ProductVO;


public class Almacenista implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private View v;
    private Context c;
    private ViewPager vp;
    private ArrayAdapter<String> adaptadorProductos;
    private ProgressDialog progressDialog = null;
    private AutoCompleteTextView auproducto;
    private TextView lvlTipo, lvlMaterial, lvlTamano;
    private EditText edtcantidad, edtNombreProducto, edtReferencia;
    private Button binfo, binfocli, bedit, btnLimpiar, btnAgregar;
    private TableLayout tl;
    private ProductDAO producto;
    private Typeface font;
    private ArrayList<String> aTamanio;
    private Activity act;
    private Spinner sTipo, sMaterial, sTamanio;
    TypeDAO type;
    MaterialDAO material;
    StandDAO stand;

    public Almacenista(View v, Context c, Activity act) {
        this.v = v;
        this.c = c;
        this.act = act;


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
        binfo.setOnClickListener(this);
        lvlMaterial = (TextView) v.findViewById(R.id.lvlMaterial);
        lvlMaterial.setTypeface(font);
        edtcantidad = (EditText) v.findViewById(R.id.edtcantidad);
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


        btnLimpiar = (Button) v.findViewById(R.id.btnLimpiar);
        btnLimpiar.setOnClickListener(this);
        btnAgregar = (Button) v.findViewById(R.id.btnAgregarInve);
        btnAgregar.setOnClickListener(this);

    }

    public void productos() {
        edtNombreProducto = (EditText) v.findViewById(R.id.edtNombreProducto);
        edtReferencia = (EditText) v.findViewById(R.id.edtReferencia);
        sTipo = (Spinner) v.findViewById(R.id.sTipo);
        sTamanio = (Spinner) v.findViewById(R.id.sTamanio);
        sMaterial = (Spinner) v.findViewById(R.id.sMaterial);
        sTamanio.setOnItemSelectedListener(this);
        sTipo.setOnItemSelectedListener(this);
        btnAgregar = (Button) v.findViewById(R.id.btnAgregarProducto);
        btnAgregar.setOnClickListener(this);
        new asynclogin().execute(2 + "");


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
                    type = new TypeDAO(c);
                    //sTamanio = (Spinner) v.findViewById(R.id.sTamanio);
                    aTamanio = type.consultarTiposTamanio(sTipo.getSelectedItem().toString());
                    ArrayAdapter<String> adaptadorTamanio = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, aTamanio);//creamos el adaptador de los spinner agregando los Arraylist
                    sTamanio.setAdapter(adaptadorTamanio);
                } else {
                    ArrayList<String> lis = new ArrayList<String>();
                    lis.add("Seleccione Tipo");
                    ArrayAdapter<String> adaptadorTamanio = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, lis);//creamos el adaptador de los spinner agregando los Arraylist
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


                    LayoutInflater inflater = act.getLayoutInflater();
                    View v2 = inflater.inflate(R.layout.mensaje_productos, null);
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
                break;

            case R.id.btnLimpiar:
                auproducto.setText("");
                lvlTipo.setText("");
                lvlTamano.setText("");
                lvlMaterial.setText("");
                edtcantidad.setText("");

                break;
            case R.id.btnAgregarInve:
                Toast toast = null;
                Vibrator vibrator = (Vibrator) c.getSystemService(Context.VIBRATOR_SERVICE);
                boolean validacion, validacion2;

                if (validacion(auproducto.getText().toString())) {
                    validacion = true;

                } else {
                    vibrator.vibrate(200);
                    LayoutInflater inflater = act.getLayoutInflater();
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
                    LayoutInflater inflater = act.getLayoutInflater();
                    View layout = inflater.inflate(R.layout.custom_toast_error,
                            (ViewGroup) v.findViewById(R.id.toast_layout_root));
                    TextView text = (TextView) layout.findViewById(R.id.text);
                    text.setTextColor(Color.BLACK);
                    text.setText(R.string.canvac);
                    toast = new Toast(c.getApplicationContext());
                    //toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(layout);
                    toast.show();
                    validacion2 = false;
                }
                if (validacion && validacion2) {
                    try {
                        JSONArray jspro = producto.agregarInventario(auproducto.getText().toString(), Float.parseFloat(edtcantidad.getText().toString()));
                        String mensaje = jspro.getString(0);
                        if (mensaje.contains("There stock has been updated.")) {
                            auproducto.setText("");
                            lvlTipo.setText("");
                            lvlTamano.setText("");
                            lvlMaterial.setText("");
                            edtcantidad.setText("");
                            toast = Toast.makeText(c, R.string.Inventario_Agregado, Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            LayoutInflater inflater = act.getLayoutInflater();
                            View layout = inflater.inflate(R.layout.custom_toast_error,
                                    (ViewGroup) v.findViewById(R.id.toast_layout_root));
                            TextView text = (TextView) layout.findViewById(R.id.text);
                            text.setTextColor(Color.BLACK);
                            text.setText(R.string.ErrorServidor);
                            toast = new Toast(c.getApplicationContext());
                            //toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.setView(layout);
                            toast.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                break;

            case R.id.btnAgregarProducto:
                Toast mensaje = null;
                Vibrator vibra = (Vibrator) c.getSystemService(Context.VIBRATOR_SERVICE);
                boolean val1, val2, val3, val4, val5;
                if (validacion(edtNombreProducto.getText().toString())) {
                    val1 = true;
                } else {
                    mensaje = Toast.makeText(c, R.string.nomvac, Toast.LENGTH_SHORT);
                    mensaje.show();
                    val1 = false;

                }
                if (validacion(edtReferencia.getText().toString())) {
                    val2 = true;

                } else {
                    vibra.vibrate(200);
                    mensaje = Toast.makeText(c, R.string.refvac, Toast.LENGTH_SHORT);
                    mensaje.show();
                    val2 = false;

                }
                if (!sTipo.getSelectedItem().toString().equals("Seleccione uno")) {
                    val3 = true;

                } else {
                    vibra.vibrate(200);
                    mensaje = Toast.makeText(c, R.string.tipvac, Toast.LENGTH_SHORT);
                    mensaje.show();
                    val3 = false;

                }
                if (!sTamanio.getSelectedItem().toString().equals("Seleccione uno")) {
                    val4 = true;

                } else {
                    vibra.vibrate(200);
                    mensaje = Toast.makeText(c, R.string.tamvac, Toast.LENGTH_SHORT);
                    mensaje.show();
                    val4 = false;

                }
                if (!sMaterial.getSelectedItem().toString().equals("Seleccione uno")) {
                    val5 = true;

                } else {
                    vibra.vibrate(200);
                    mensaje = Toast.makeText(c, R.string.matvac, Toast.LENGTH_SHORT);
                    mensaje.show();
                    val5 = false;

                }
                if (val1 && val2 && val3 && val4 && val5) {
                    producto = new ProductDAO(c);
                    JSONArray jspro = null;
                    try {
                        ProductVO productVO = new ProductVO();
                        productVO.setName(edtNombreProducto.getText().toString());
                        productVO.setReference(edtReferencia.getText().toString());
                        productVO.setIdType(sTipo.getSelectedItem().toString());
                        productVO.setTamanio(sTamanio.getSelectedItem().toString());
                        productVO.setIdMaterial(sMaterial.getSelectedItem().toString());
                        jspro = producto.agregarProducto(productVO);
                        String mensajes = jspro.getString(0);
                        if (mensajes.contains("The record has been inserted.")) {
                            edtNombreProducto.setText("");
                            edtReferencia.setText("");
                            sTipo.setSelection(0);
                            sTamanio.setSelection(0);
                            sMaterial.setSelection(0);
                            toast = Toast.makeText(c, R.string.Inventario_Agregado, Toast.LENGTH_SHORT);
                            toast.show();
                        } else {
                            LayoutInflater inflater = act.getLayoutInflater();
                            View layout = inflater.inflate(R.layout.custom_toast_error,
                                    (ViewGroup) v.findViewById(R.id.toast_layout_root));
                            TextView text = (TextView) layout.findViewById(R.id.text);
                            text.setTextColor(Color.BLACK);
                            text.setText(R.string.ErrorServidor);
                            toast = new Toast(c.getApplicationContext());
                            //toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 0);
                            toast.setDuration(Toast.LENGTH_SHORT);
                            toast.setView(layout);
                            toast.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                break;


        }

    }

    class asynclogin extends AsyncTask<String, String, String> {
        char pos;
        ProgressBar pb = (ProgressBar) v.findViewById(R.id.progressBar);
        LinearLayout layoutver = (LinearLayout) v.findViewById(R.id.lyVentas);
        ProgressBar pbPro = (ProgressBar) v.findViewById(R.id.progressBarProducto);
        LinearLayout lyPro = (LinearLayout) v.findViewById(R.id.lyProducto);
        ProgressBar pbInv = (ProgressBar) v.findViewById(R.id.pbInventario);
        ScrollView lyInv = (ScrollView) v.findViewById(R.id.svInventario);

        protected void onPreExecute() {
            lyPro = (LinearLayout) v.findViewById(R.id.lyProducto);
            pbPro = (ProgressBar) v.findViewById(R.id.progressBarProducto);
            type = new TypeDAO(c);
            material = new MaterialDAO(c);
            producto = new ProductDAO(c);
            stand = new StandDAO(c);

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
                        lyInv.setVisibility(View.INVISIBLE);
                        pbInv.setVisibility(ProgressBar.VISIBLE);
                        type.agregarTipo();
                        material.agregarMaterial();
                        producto.agregarProducto();
                        stand.agregarStand();
                        producto.agregarInventario();
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
                    layoutver.setVisibility(View.VISIBLE);
                    pb.setVisibility(ProgressBar.INVISIBLE);
                    auproducto.setAdapter(adaptadorProductos);
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
                                tr.setBackgroundResource(R.drawable.row_selector_r);
                                //tr.setBackgroundColor(Color.argb(15, 203, 47, 23));
                            } else {
                                tr.setBackgroundResource(R.drawable.row_selector_w);
                                //tr.setBackgroundColor(Color.WHITE);
                            }
                            TextView txtProducto = new TextView(c);
                            txtProducto.setTypeface(font);
                            txtProducto.setText(productos.get(i));
                            txtProducto.setGravity(Gravity.CENTER);
                            //txtProducto.setTextSize(20);
                            tr.addView(txtProducto);
                            TextView txtTipo = new TextView(c);
                            txtTipo.setTypeface(font);
                            txtTipo.setText(productos.get(i + 1));
                            txtTipo.setGravity(Gravity.CENTER);
                            txtTipo.setLines(2);
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
                            tr.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });

                            tr.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {
                                    try {
                                        final String[] prodSel = {""};
                                        ArrayList<String> lis = null;
                                        LayoutInflater inflater = act.getLayoutInflater();

                                        View v2 = inflater.inflate(R.layout.mensaje_producto, null);
                                        AlertDialog.Builder builder3 = new AlertDialog.Builder(c);
                                        builder3.setView(v2);
                                        builder3.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                            }
                                        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });
                                        AlertDialog dialog3;
                                        dialog3 = builder3.create();
                                        dialog3.setTitle("Productos");
                                        dialog3.show();


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
                    lyInv.setVisibility(View.VISIBLE);
                    pbInv.setVisibility(ProgressBar.INVISIBLE);

                    break;
            }

        }
    }

}
