package co.edu.sena.digilistmobile.digilist;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;

/**
 * Created by ADMIN on 05/01/2015.
 */
/*public class Administrador /*implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private View v;
    private Context c;
    private Activity act;

    public Administrador(View v, Context c, Activity act) {
        this.v = v;
        this.c = c;
        this.act = act;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
*/

public class Administrador extends SherlockActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int op = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            op = extras.getInt("pos");

        }
        switch (op) {
            case 0:
                setContentView(R.layout.usuarios);
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
        }

    }


}