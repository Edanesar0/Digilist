package co.edu.sena.digilistmobile.digilist;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;

import com.actionbarsherlock.app.SherlockActivity;

/**
 * Created by Axxuss on 25/02/2015.
 */
public class Rol extends SherlockActivity {
    private ProgressBar pbRol;
    private TableLayout tRol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roles);
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

        }
    }
}
