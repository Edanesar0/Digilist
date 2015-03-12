package co.edu.sena.digilistmobile.digilist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
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
    ActionBar ab;
    MaterialDAO material;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.materiales);
        ab = getSupportActionBar();//instancia
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);//Atributos titulo boton home y flecha de acompañamiento de home
        ab.setHomeButtonEnabled(true);//activar el boton home
        ab.setDisplayShowHomeEnabled(true);//se pueda ver el boton home
        ab.setIcon(R.drawable.ic_launcher);//se le adiciona el icono
        ImageButton btnAdd = (ImageButton) findViewById(R.id.btnAgregarMate);
        btnAdd.setOnClickListener(this);
        font = Typeface.createFromAsset(this.getAssets(), "Station.ttf");
        material = new MaterialDAO(this);
        new asynclogin().execute("1");
        ImageButton btnAgregarMaterial =(ImageButton) findViewById(R.id.btnAgregarMate);
        btnAgregarMaterial.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAgregarMate:
                LayoutInflater inflater = getLayoutInflater();
                View v2 = inflater.inflate(R.layout.ingreso_material, null);
                AlertDialog.Builder builder3 = new AlertDialog.Builder(Material.this);
                builder3.setView(v2);
                builder3.setPositiveButton(Material.this.getResources().getText(R.string.Aceptar),null);
                builder3.setNegativeButton(Material.this.getResources().getText(R.string.Cancelar),null);
                Dialog dialog3 = builder3.create();
                dialog3.setTitle("");
                dialog3.show();
                break;
        }

    }

    class asynclogin extends AsyncTask<String, String, String> {
        char pos;

        protected void onPreExecute() {
            svMaterial = (ScrollView) findViewById(R.id.svMateriales);
            tMaterial = (TableLayout) findViewById(R.id.tlMateriales);
            pbMaterial = (ProgressBar) findViewById(R.id.pbMateriales);
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
            ArrayList<String> materiales = material.consultarMateriales("");
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
                    tr.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            LayoutInflater inflater = getLayoutInflater();
                            View v2 = inflater.inflate(R.layout.opciones, null);
                            ListView listview = (ListView) v2.findViewById(R.id.lvOpciones);
                            ArrayList<String> opc = new ArrayList<String>();
                            opc.add(Material.this.getResources().getString(R.string.Editar));
                            opc.add(Material.this.getResources().getString(R.string.Eliminar));
                            ArrayAdapter<String> adpOpc = new ArrayAdapter<String>(Material.this, R.layout.list_center, opc);
                            listview.setAdapter(adpOpc);
                            AlertDialog.Builder builder3 = new AlertDialog.Builder(Material.this);
                            builder3.setView(v2);
                            builder3.setPositiveButton(Material.this.getResources().getText(R.string.Aceptar),null);
                            builder3.setNegativeButton(Material.this.getResources().getText(R.string.Cancelar),null);
                            Dialog dialog3 = builder3.create();
                            dialog3.setTitle(txtNombre.getText());
                            dialog3.show();
                            return false;
                        }
                    });
                    tMaterial.addView(tr, new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.FILL_PARENT,
                            TableLayout.LayoutParams.FILL_PARENT));
                    count++;
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
