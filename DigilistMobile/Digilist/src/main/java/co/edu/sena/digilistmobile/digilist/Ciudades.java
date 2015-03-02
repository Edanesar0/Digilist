package co.edu.sena.digilistmobile.digilist;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;

import org.json.JSONException;

import java.util.ArrayList;

import co.edu.sena.digilistmobile.digilist.dao.CityDAO;

/**
 * Created by Axxuss on 25/02/2015.
 */
public class Ciudades extends SherlockActivity implements View.OnClickListener {
    private ProgressBar pbCiudades;
    private TableLayout tCiudades;
    private ScrollView svCiudades;
    static Typeface font;
    CityDAO ciudades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ciudades);
        ImageButton btnAdd = (ImageButton) findViewById(R.id.btnAgregarCiudades);
        btnAdd.setOnClickListener(this);
        font = Typeface.createFromAsset(this.getAssets(), "Station.ttf");
        ciudades = new CityDAO(this);
        /*try {
            ciudades.agregarCiudadesLocal();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        new asynclogin().execute("1");
    }

    @Override
    public void onClick(View v) {

    }

    class asynclogin extends AsyncTask<String, String, String> {
        char pos;

        protected void onPreExecute() {
            svCiudades = (ScrollView) findViewById(R.id.svCiudad);
            pbCiudades = (ProgressBar) findViewById(R.id.pbCiudad);
            pbCiudades.setVisibility(View.VISIBLE);
            svCiudades.setVisibility(View.INVISIBLE);

        }

        @Override
        protected String doInBackground(String... params) {
          cargarCiudades();
            return null;
        }

        protected void onPostExecute(String result) {
            svCiudades.setVisibility(View.VISIBLE);
            pbCiudades.setVisibility(View.INVISIBLE);

        }
    }
    public void cargarCiudades(){
        tCiudades = (TableLayout) findViewById(R.id.tlCiudad);
        //tCiudades.setStretchAllColumns(true);
        //tCiudades.setShrinkAllColumns(true);
        TextView lblNombre = (TextView) findViewById(R.id.lblNombreCiudad);
        lblNombre.setTypeface(font);
        ArrayList<String> materiales = ciudades.consultarCiudades("");
        int count = 0;
        if (materiales.size() != 0) {
            for (int i = 0; i <= materiales.size() - 2; i = i + 2) {
                final TableRow tr = new TableRow(Ciudades.this);

                if (count % 2 != 0) {
                    tr.setBackgroundResource(R.drawable.row_selector_r);
                    //tr.setBackgroundColor(Color.argb(15, 203, 47, 23));
                } else {
                    tr.setBackgroundResource(R.drawable.row_selector_w);
                    //tr.setBackgroundColor(Color.WHITE);
                }
                final TextView txtNombre = new TextView(Ciudades.this);
                txtNombre.setTypeface(font);
                txtNombre.setId(Integer.parseInt(materiales.get(i)));
                txtNombre.setText(materiales.get(i + 1));
                txtNombre.setLines(3);
                txtNombre.setTypeface(font);
                txtNombre.setGravity(Gravity.CENTER);
                //txtProducto.setTextSize(20);
                tr.addView(txtNombre);
                tCiudades.addView(tr, new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.WRAP_CONTENT,
                        TableLayout.LayoutParams.FILL_PARENT));
            count++;
            }
        }
    }
}

