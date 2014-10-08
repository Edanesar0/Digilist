package co.edu.sena.digilistmobile.digilist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class Almacenista {
    private View v;
    private Context c;

    private ViewPager vp;
    private ArrayAdapter<String> adaptadorProductos;
    private ProgressDialog progressDialog = null;
    private AutoCompleteTextView auproducto;
    private TextView lvlTipo, lvlMaterial, lvlTamano;
    private EditText edtcantidad;
    private Button binfo, binfocli, bedit, btnLimpiar, btnAgregar;
    private Producto producto;
    private Typeface font;
    Activity a;

    public Almacenista(View v, Context c, Activity a) {
        this.v = v;
        this.c = c;
        this.a = a;

    }

    public void inventario() {
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
        btnAgregar = (Button) v.findViewById(R.id.btnAgregar);

    }

    public void productos() {
        Spinner sTipo = (Spinner) v.findViewById(R.id.sTipo);
        Spinner sMateral = (Spinner) v.findViewById(R.id.sMaterial);
        new asynclogin().execute(2 + "");

        Tipo type = new Tipo(c);
        ArrayList<String> aTypes = type.consultarTipos();//retornamos la consulta
        ArrayAdapter<String> adaptadorTypes = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, aTypes);//creamos el adaptador de los spinner agregando los Arraylist
        sTipo.setAdapter(adaptadorTypes);//incluimos el adaptados a los spinner

        Material material = new Material(c);
        ArrayList<String> aMaterial = material.consultarMateriales();//retornamos la consulta
        ArrayAdapter<String> adaptadorMaterial = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, aMaterial);//creamos el adaptador de los spinner agregando los Arraylist
        sMateral.setAdapter(adaptadorMaterial);//incluimos el adaptados a los spinner


    }

    class asynclogin extends AsyncTask<String, String, String> {
        char pos;
        ProgressBar pb = (ProgressBar) v.findViewById(R.id.progressBar);
        LinearLayout layoutver = (LinearLayout) v.findViewById(R.id.lyVentas);
        ProgressBar pbPro = (ProgressBar) v.findViewById(R.id.progressBarProducto);
        LinearLayout lyPro = (LinearLayout) v.findViewById(R.id.lyProducto);

        protected void onPreExecute() {
        }

        protected String doInBackground(String... params) {
            pos = params[0].charAt(0);
            try {
                switch (pos) {
                    case '1':
                        layoutver.setVisibility(View.INVISIBLE);
                        pb.setVisibility(ProgressBar.VISIBLE);
                        Tipo tipo = new Tipo(c);
                        tipo.agregarTipo();
                        Material material = new Material(c);
                        material.agregarMaterial();
                        producto = new Producto(c);
                        producto.agregarProducto();
                        ArrayList<String> AProductos = producto.consultarProductos();//retornamos la consulta de inventario
                        ArrayList<String> Apr = new ArrayList<String>();
                        for (int i = 0; i <= AProductos.size() - 4; i = i + 4) {
                            Apr.add(AProductos.get(i));
                        }
                        adaptadorProductos = new ArrayAdapter<String>(c, android.R.layout.simple_list_item_1, Apr);//creamos el adaptador de los spinner agregando los Arraylist
                        //enviamos y recibimos y analizamos los datos en segundo plano.
                        break;
                    case '2':
                        lyPro.setVisibility(View.INVISIBLE);
                        pbPro.setVisibility(ProgressBar.VISIBLE);


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
                    break;
            }

        }
    }
}
