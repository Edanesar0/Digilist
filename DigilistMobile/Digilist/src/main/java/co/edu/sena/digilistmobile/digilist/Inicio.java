package co.edu.sena.digilistmobile.digilist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import co.edu.sena.digilistmobile.digilist.dao.ProductDAO;
import co.edu.sena.digilistmobile.digilist.utils.conexiones.ConexionLocal;


/**
 * Created by ADMIN on 16/04/2014.
 */
public class Inicio extends SherlockActivity {
    ActionBar ab;
    private DrawerLayout mDrawerLayout;
    private LinearLayout navList;
    private View v = null;
    private ViewPager vp;
    Almacenista almacenista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_almacenista);
        Typeface font = Typeface.createFromAsset(getAssets(), "Station.ttf");
        ProductDAO producto = new ProductDAO(Inicio.this);
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
        int posicion;
        //obtenemos los datos que se enviaron desde el otro intent
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            posicion = extras.getInt("pos");
            vp.setCurrentItem(posicion);
        }
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
        /*SubMenu subMenu = menu.addSubMenu("Config");
        subMenu.add("Configuracion").setIcon(R.drawable.ic_action_preferences);
        subMenu.add("Cerrar sesión").setIcon(R.drawable.ic_action_logout2);
        MenuItem subMenu1Item = subMenu.getItem();
        subMenu1Item.setIcon(R.drawable.icon_conf);
        subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                /*if (mDrawerLayout.isDrawerOpen(navList)) {
                    mDrawerLayout.closeDrawers();
                } else {
                    mDrawerLayout.openDrawer(navList);
                }*/

                break;
        }


        if (item.getTitle().equals("Cerrar sesión")) {
            Intent i2 = new Intent(this, Login.class);
            finish();
            ConexionLocal conexionLocal = new ConexionLocal(this);
            conexionLocal.abrir();
            conexionLocal.clearAll();
            conexionLocal.cerrar();
            startActivity(i2);
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
                    almacenista = new Almacenista(v, Inicio.this, Inicio.this);
                    almacenista.addInventario();
                    break;
                case 1:
                    v = inflater.inflate(R.layout.ingreso_producto, null);
                    almacenista = new Almacenista(v, Inicio.this, Inicio.this);
                    almacenista.productos();
                    break;
                case 2:
                    v = inflater.inflate(R.layout.inventario, null);
                    almacenista = new Almacenista(v, Inicio.this, Inicio.this);
                    almacenista.inventario();
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


}




