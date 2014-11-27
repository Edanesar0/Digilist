package co.edu.sena.digilistmobile.digilist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

import co.edu.sena.digilistmobile.digilist.util.conexiones.ConexionLocal;


public class Menu_Inicial extends SherlockActivity implements View.OnClickListener {
    ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        ConexionLocal conexionLocal = new ConexionLocal(this);
        conexionLocal.abrir();
        conexionLocal.limpiar();
        conexionLocal.cerrar();

        ab = getSupportActionBar();//instancia
        // ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);//Atributos titulo boton home y flecha de acompañamiento de home
        ab.setIcon(R.drawable.ic_launcher);//se le adiciona el icono


        RelativeLayout bt = (RelativeLayout) findViewById(R.id.relativeLayout);
        bt.setOnClickListener(this);
        RelativeLayout bt2 = (RelativeLayout) findViewById(R.id.relativeLayout2);
        bt2.setOnClickListener(this);
        RelativeLayout bt3 = (RelativeLayout) findViewById(R.id.relativeLayout3);
        bt3.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                break;

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
            case R.id.relativeLayout:
                Intent i = new Intent(this, Inicio.class);
                startActivity(i);
                break;
            case R.id.relativeLayout2:
                Intent i2 = new Intent(this, Inicio.class);
                i2.putExtra("pos", 1);
                startActivity(i2);
            case R.id.relativeLayout3:
                Intent i3 = new Intent(this, Inicio.class);
                i3.putExtra("pos", 2);
                startActivity(i3);


        }


    }
}