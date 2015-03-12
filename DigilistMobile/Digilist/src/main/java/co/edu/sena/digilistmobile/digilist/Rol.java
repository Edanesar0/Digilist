package co.edu.sena.digilistmobile.digilist;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

import java.util.ArrayList;

import co.edu.sena.digilistmobile.digilist.dao.RolDAO;

/**
 * Created by Axxuss on 25/02/2015.
 */
public class Rol extends SherlockActivity {
    private ProgressBar pbRol;
    private TableLayout tRol;
    private static Typeface font;
    ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roles);
        ab = getSupportActionBar();//instancia
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);//Atributos titulo boton home y flecha de acompañamiento de home
        ab.setHomeButtonEnabled(true);//activar el boton home
        ab.setDisplayShowHomeEnabled(true);//se pueda ver el boton home
        ab.setIcon(R.drawable.ic_launcher);//se le adiciona el icono
        font = Typeface.createFromAsset(this.getAssets(), "Station.ttf");
        new asynclogin().execute("1");
    }

    class asynclogin extends AsyncTask<String, String, String> {
        char pos;

        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            return null;

        }

        protected void onPostExecute(String result) {
            cargarCiudades();

        }
    }

    public void cargarCiudades() {
        TableLayout tRol = (TableLayout) findViewById(R.id.tlRol);
        tRol.setStretchAllColumns(true);
        tRol.setShrinkAllColumns(true);
        TextView lblNombre = (TextView) findViewById(R.id.lblNombreRol);
        lblNombre.setTypeface(font);
        RolDAO rol = new RolDAO(this);
        ArrayList<String> roles = rol.consultarRoles();
        int count = 0;
        if (roles.size() != 0) {
            for (int i = 0; i < roles.size(); i++) {
                final TableRow tr = new TableRow(Rol.this);

                if (count % 2 != 0) {
                    tr.setBackgroundResource(R.drawable.row_selector_r);
                    //tr.setBackgroundColor(Color.argb(15, 203, 47, 23));
                } else {
                    tr.setBackgroundResource(R.drawable.row_selector_w);
                    //tr.setBackgroundColor(Color.WHITE);
                }
                final TextView txtNombre = new TextView(Rol.this);
                txtNombre.setTypeface(font);
                txtNombre.setText(roles.get(i));
                txtNombre.setLines(3);
                txtNombre.setTypeface(font);
                txtNombre.setGravity(Gravity.CENTER);
                //txtProducto.setTextSize(20);
                tr.addView(txtNombre);
                tRol.addView(tr, new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.FILL_PARENT));
                count++;
            }
        }
    }
}
