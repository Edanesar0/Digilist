package co.edu.sena.digilistmobile.digilist;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
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
    private Spinner sCiudades;
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
        sCiudades = (Spinner) findViewById(R.id.sCiudades);
        //tCiudades.setStretchAllColumns(true);
        //tCiudades.setShrinkAllColumns(true);

        }
    }


