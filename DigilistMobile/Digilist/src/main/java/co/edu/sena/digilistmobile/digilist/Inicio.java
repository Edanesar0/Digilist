package co.edu.sena.digilistmobile.digilist;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

import java.util.ArrayList;


/**
 * Created by ADMIN on 16/04/2014.
 */
public class Inicio extends SherlockActivity {
    ActionBar ab;
    private DrawerLayout mDrawerLayout;
    private LinearLayout navList;
    private View v = null;
    private ViewPager vp;
    private ArrayAdapter<String> adaptadorProductos;
    private ProgressDialog progressDialog = null;
    private AutoCompleteTextView auproducto;
    private TextView lvlTipo, lvlMaterial, lvlTamano;
    private EditText edtcantidad;
    private Button binfo, binfocli, bedit;
    private Producto producto;
    private Typeface font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_almacenista);
        font = Typeface.createFromAsset(getAssets(), "Station.ttf");
        producto = new Producto(Inicio.this);
        ab = getSupportActionBar();//instancia
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);//Atributos titulo boton home y flecha de acompañamiento de home
        ab.setHomeButtonEnabled(true);//activar el boton home
        ab.setDisplayShowHomeEnabled(true);//se pueda ver el boton home
        ab.setIcon(R.drawable.ic_launcher);//se le adiciona el icono
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navList = (LinearLayout) findViewById(R.id.left_drawer);
        vp = (ViewPager) findViewById(R.id.pager);
        vpAdapter2 miAdapter = new vpAdapter2();
        vp.setAdapter(miAdapter);

        /*progressDialog = new ProgressDialog(this);
        progressDialog.show();
        //se ppdrá cerrar simplemente pulsando back
        progressDialog.setCancelable(true);*/


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getSupportMenuInflater();
        // inflater.inflate(R.menu.menu2, menu);
        SubMenu subMenu = menu.addSubMenu("Config");
        subMenu.add("Configuracion").setIcon(R.drawable.ic_action_preferences);
        subMenu.add("Cerrar sesión").setIcon(R.drawable.ic_action_logout2);
        MenuItem subMenu1Item = subMenu.getItem();
        subMenu1Item.setIcon(R.drawable.icon_conf);
        subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mDrawerLayout.isDrawerOpen(navList)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    mDrawerLayout.openDrawer(navList);
                }


                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private class vpAdapter2 extends PagerAdapter {
        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public void finishUpdate(ViewGroup container) {
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            switch (position) {
                case 0:
                    //v = inflater.inflate(R.layout.progress, null);
                    v = inflater.inflate(R.layout.ingreso_inventario, null);
                    new asynclogin().execute(position + "");
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


                                LayoutInflater inflater = Inicio.this.getLayoutInflater();
                                View v2 = inflater.inflate(R.layout.mensaje_producto, null);
                                AlertDialog.Builder builder3 = new AlertDialog.Builder(Inicio.this);
                                builder3.setView(v2).setSingleChoiceItems(pro, -1, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        prodSel[0] = pro[which].substring(0, pro[which].indexOf("-") - 1);

                                    }
                                });
                                builder3.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        ArrayList lis = producto.consultarProducto("producto.nombre", prodSel[0]);
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
                            ArrayList lis = producto.consultarProducto("producto.nombre", auproducto.getText().toString());
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
                    break;
                case 1:
                    v = inflater.inflate(R.layout.ingreso_producto, null);
                    break;
                case 2:
                    v = inflater.inflate(R.layout.inventario, null);
                    break;
                case 3:
                    break;


            }
            ((ViewPager) container).addView(v, 0);
            return v;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(ViewGroup container) {


        }
    }

    class asynclogin extends AsyncTask<String, String, String> {
        String pos;
        ProgressBar pb = (ProgressBar) v.findViewById(R.id.progressBar);
        LinearLayout layoutver = (LinearLayout) v.findViewById(R.id.lyVentas);

        protected void onPreExecute() {
            try {
                layoutver.setVisibility(View.INVISIBLE);
                pb.setVisibility(ProgressBar.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        protected String doInBackground(String... params) {
            //obtnemos usr y pass
            pos = params[0];
            try {
                Tipo tipo = new Tipo(Inicio.this);
                Log.e("tipo", tipo.agregarTipo() + "");
                Material material = new Material(Inicio.this);
                Log.e("material", material.agregarMaterial() + "");
                producto = new Producto(Inicio.this);
                Log.e("producto", producto.agregarProducto() + "");
                ArrayList<String> AProductos = producto.consultarProductos();//retornamos la consulta de productos
                ArrayList<String> Apr = new ArrayList<String>();
                for (int i = 0; i <= AProductos.size() - 4; i = i + 4) {
                    Apr.add(AProductos.get(i));
                }
                adaptadorProductos = new ArrayAdapter<String>(Inicio.this, android.R.layout.simple_list_item_1, Apr);//creamos el adaptador de los spinner agregando los Arraylist
                //enviamos y recibimos y analizamos los datos en segundo plano.

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        /*Una vez terminado doInBackground segun lo que halla ocurrido
         pasamos a la sig. activity
         o mostramos error*/
        protected void onPostExecute(String result) {
            auproducto.setAdapter(adaptadorProductos);
            layoutver.setVisibility(View.VISIBLE);
            pb.setVisibility(ProgressBar.INVISIBLE);

        }
    }


}




