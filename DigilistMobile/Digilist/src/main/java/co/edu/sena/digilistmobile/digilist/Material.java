package co.edu.sena.digilistmobile.digilist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
    private EditText edtNombreMaterial, edtDescripcion;
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

    }

    public boolean validacion(String text) {
        boolean val;
        val = text != null && !text.equals("") && !text.equals(" ");
        return val;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAgregarMate:
                LayoutInflater inflater = getLayoutInflater();
                View v2 = inflater.inflate(R.layout.ingreso_material, null);
                edtNombreMaterial = (EditText) v2.findViewById(R.id.edtNombreMaterial);
                AlertDialog.Builder builder3 = new AlertDialog.Builder(Material.this);
                builder3.setView(v2);
                builder3.setPositiveButton(Material.this.getResources().getText(R.string.Aceptar), null);
                builder3.setNegativeButton(Material.this.getResources().getText(R.string.Cancelar), null);
                final AlertDialog dialog = builder3.create();
                dialog.setTitle("");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Boolean wantToCloseDialog = false;
                        boolean val, val2;
                        val = validacion(edtNombreMaterial.getText().toString());

                        if (val) {
                            wantToCloseDialog = true;

                        } else {
                            edtNombreMaterial.setBackgroundResource(R.drawable.borde_error);
                            edtNombreMaterial.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                }

                                @Override
                                public void onTextChanged(CharSequence s, int start, int before, int count) {
                                    edtNombreMaterial.setBackgroundResource(R.drawable.edittext_rounded_corners);
                                }

                                @Override
                                public void afterTextChanged(Editable s) {

                                }
                            });
                            Toast toast = Toast.makeText(Material.this, Material.this.getResources().getString(R.string.nomvac), Toast.LENGTH_LONG);
                            toast.show();
                        }
                        if (wantToCloseDialog) {
                            dialog.dismiss();
                        }

                    }
                });

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


            return null;
        }

        protected void onPostExecute(String result) {
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
                            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    switch (position) {
                                        case 0:
                                            LayoutInflater inflater = getLayoutInflater();
                                            View v2 = inflater.inflate(R.layout.ingreso_material, null);
                                            ArrayList<String> materiales = material.consultarMateriales(txtNombre.getId() + "");
                                            TextView txtTitulo = (TextView) v2.findViewById(R.id.txtTitulo);
                                            txtTitulo.setText(R.string.Editar_Material);
                                            edtNombreMaterial = (EditText) v2.findViewById(R.id.edtNombreMaterial);
                                            edtNombreMaterial.setText(materiales.get(1));

                                            edtDescripcion = (EditText) v2.findViewById(R.id.edtDescripcion);
                                            edtDescripcion.setText(materiales.get(2));

                                            AlertDialog.Builder builder3 = new AlertDialog.Builder(Material.this);
                                            builder3.setView(v2);
                                            builder3.setPositiveButton(Material.this.getResources().getText(R.string.Aceptar), null);
                                            builder3.setNegativeButton(Material.this.getResources().getText(R.string.Cancelar), null);
                                            final AlertDialog dialog2 = builder3.create();
                                            dialog2.setTitle("");
                                            dialog2.setCanceledOnTouchOutside(false);
                                            dialog2.show();
                                            dialog2.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Boolean wantToCloseDialog = false;
                                                    boolean val, val2;
                                                    val = validacion(edtNombreMaterial.getText().toString());

                                                    if (val) {
                                                        wantToCloseDialog = true;

                                                    } else {
                                                        edtNombreMaterial.setBackgroundResource(R.drawable.borde_error);
                                                        edtNombreMaterial.addTextChangedListener(new TextWatcher() {
                                                            @Override
                                                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                                            }

                                                            @Override
                                                            public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                                edtNombreMaterial.setBackgroundResource(R.drawable.edittext_rounded_corners);
                                                            }

                                                            @Override
                                                            public void afterTextChanged(Editable s) {

                                                            }
                                                        });
                                                        Toast toast = Toast.makeText(Material.this, Material.this.getResources().getString(R.string.nomvac), Toast.LENGTH_LONG);
                                                        toast.show();
                                                    }
                                                    if (wantToCloseDialog) {
                                                        dialog2.dismiss();
                                                    }

                                                }
                                            });
                                            break;
                                        case 1:
                                            AlertDialog dialog;
                                            AlertDialog.Builder builder;
                                            builder = new AlertDialog.Builder(Material.this);
                                            builder.setMessage(Material.this.getResources().getString(R.string.MensajeEliminarMaterial));

                                            builder.setPositiveButton(Material.this.getResources().getString(R.string.Aceptar), null)
                                                    .setNegativeButton(Material.this.getResources().getString(R.string.Cancelar), null);
                                            dialog = builder.create();
                                            dialog.setTitle(Material.this.getResources().getString(R.string.Eliminar));
                                            dialog.show();
                                            break;
                                    }
                                }
                            });
                            builder3.setPositiveButton(Material.this.getResources().getText(R.string.Aceptar), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }

                            });
                            builder3.setNegativeButton(Material.this.getResources().getText(R.string.Cancelar), null);
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
            svMaterial.setVisibility(View.VISIBLE);
            pbMaterial.setVisibility(View.INVISIBLE);

        }
    }
}
