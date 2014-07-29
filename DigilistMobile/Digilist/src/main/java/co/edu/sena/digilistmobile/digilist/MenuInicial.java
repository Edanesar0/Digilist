package co.edu.sena.digilistmobile.digilist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;


public class MenuInicial extends SherlockActivity  implements View.OnClickListener{
    ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        ab = getSupportActionBar();//instancia
       // ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);//Atributos titulo boton home y flecha de acompañamiento de home
        RelativeLayout bt=(RelativeLayout) findViewById(R.id.relativeLayout);
        bt.setOnClickListener(this);
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
    public void onClick(View v) {
        Intent i = new Intent(this, Inicio.class);
        startActivity(i);


    }
}
