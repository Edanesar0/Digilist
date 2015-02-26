package co.edu.sena.digilistmobile.digilist;

import android.content.DialogInterface;
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

import co.edu.sena.digilistmobile.digilist.dao.MaterialDAO;

/**
 * Created by Axxuss on 25/02/2015.
 */
public class Material extends SherlockActivity implements View.OnClickListener {
    private ProgressBar pbMaterial;
    private TableLayout tMaterial;
    private ScrollView svMaterial;
    static Typeface font;
    MaterialDAO material;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.materiales);
        ImageButton btnAdd = (ImageButton) findViewById(R.id.btnAgregarMate);
        btnAdd.setOnClickListener(this);
        font = Typeface.createFromAsset(this.getAssets(), "Station.ttf");
        material= new MaterialDAO(this);
        new asynclogin().execute("1");
    }

    @Override
    public void onClick(View v) {

    }

    class asynclogin extends AsyncTask<String, String, String> {
        char pos;

        protected void onPreExecute() {
            svMaterial=(ScrollView) findViewById(R.id.svMateriales);
            tMaterial=(TableLayout) findViewById(R.id.tlMateriales);
            pbMaterial=(ProgressBar) findViewById(R.id.pbMateriales);
            try {
                material.agregarMaterialLocal();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... params) {
            svMaterial.setVisibility(View.INVISIBLE);
            pbMaterial.setVisibility(View.VISIBLE);
            tMaterial.setStretchAllColumns(true);
            tMaterial.setShrinkAllColumns(true);
            TextView lblNombre = (TextView) findViewById(R.id.lblNombreMat);
            lblNombre.setTypeface(font);
            TextView lblRol = (TextView) findViewById(R.id.lblDescripcionMat);
            lblRol.setTypeface(font);
            ArrayList<String> materiales=material.consultarMateriales("");
            int count = 0;
            if (materiales.size() != 0) {
                for (int i = 0; i <= materiales.size() - 3; i = i + 3) {
                    final TableRow tr = new TableRow(Material.this);

                    if (count % 2 != 0) {
                        tr.setBackgroundResource(R.drawable.row_selector_r);
                        //tr.setBackgroundColor(Color.argb(15, 203, 47, 23));
                    } else {
                        tr.setBackgroundResource(R.drawable.row_selector_w);
                        //tr.setBackgroundColor(Color.WHITE);
                    }
                    final TextView txtNombre = new TextView(Material.this);
                    txtNombre.setTypeface(font);
                    txtNombre.setId(Integer.parseInt(materiales.get(i)));
                    txtNombre.setText(materiales.get(i + 1));
                    txtNombre.setLines(3);
                    txtNombre.setTypeface(font);
                    txtNombre.setGravity(Gravity.CENTER);
                    //txtProducto.setTextSize(20);
                    tr.addView(txtNombre);
                    final TextView txtDescripcion = new TextView(Material.this);
                    txtDescripcion.setTypeface(font);
                    txtDescripcion.setLines(3);
                    txtDescripcion.setText(materiales.get(i + 2));
                    txtDescripcion.setGravity(Gravity.CENTER);
                    txtDescripcion.setTypeface(font);
                    txtDescripcion.setLines(2);
                    tr.addView(txtDescripcion);
                    tr.setLayoutParams(new TableRow.LayoutParams(
                            TableLayout.LayoutParams.FILL_PARENT,
                            TableLayout.LayoutParams.FILL_PARENT));
                    tMaterial.addView(tr, new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.FILL_PARENT,
                            TableLayout.LayoutParams.FILL_PARENT));
                }
            }

            return null;
        }

        protected void onPostExecute(String result) {
            svMaterial.setVisibility(View.VISIBLE);
            pbMaterial.setVisibility(View.INVISIBLE);

        }
    }
}
