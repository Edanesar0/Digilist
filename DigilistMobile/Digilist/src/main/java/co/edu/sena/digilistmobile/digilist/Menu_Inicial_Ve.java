package co.edu.sena.digilistmobile.digilist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

import co.edu.sena.digilistmobile.digilist.utils.conexiones.ConexionLocal;


public class Menu_Inicial_Ve extends SherlockActivity implements View.OnClickListener {
    ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_ve);

        /*ConexionLocal conexionLocal = new ConexionLocal(this);
        conexionLocal.abrir();
        conexionLocal.limpiar();
        conexionLocal.cerrar();*/


        ab = getSupportActionBar();//instancia
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);//Atributos titulo boton home y flecha de acompañamiento de home
        ab.setHomeButtonEnabled(true);//activar el boton home
        ab.setDisplayShowHomeEnabled(true);//se pueda ver el boton home
        ab.setIcon(R.drawable.ic_launcher);//se le adiciona el icono


        RelativeLayout bt = (RelativeLayout) findViewById(R.id.rl);
        bt.setOnClickListener(this);
        RelativeLayout bt2 = (RelativeLayout) findViewById(R.id.rl2);
        bt2.setOnClickListener(this);
        RelativeLayout bt3 = (RelativeLayout) findViewById(R.id.rl3);
        bt3.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getSupportMenuInflater();
        // inflater.inflate(R.menu.menu2, menu);
        SubMenu subMenu = menu.addSubMenu("Config");
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

    @Override
    protected void onResume() {
        ConexionLocal conexionLocal = new ConexionLocal(this);
        conexionLocal.abrir();
        conexionLocal.limpiar();
        conexionLocal.cerrar();
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl:
                Intent i = new Intent(this, Vendedor.class);
                i.putExtra("pos", 1);
                startActivity(i);
                break;
            case R.id.rl2:
                Intent i2 = new Intent(this, Vendedor.class);
                i2.putExtra("pos", 2);
                startActivity(i2);
                break;
            case R.id.rl3:
                Intent i3 = new Intent(this, Vendedor.class);
                i3.putExtra("pos", 3);
                startActivity(i3);
                break;

        }


    }
}
