package co.edu.sena.digilistmobile.digilist;

/**
 * Created by Axxuss on 05/05/2015.
 */


import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

/**
 * Created by Axxuss on 02/05/2015.
 */
public class Report extends SherlockActivity implements View.OnClickListener {
    private ActionBar ab;
    Typeface font;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reportes);
        ab = getSupportActionBar();//instancia
        ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);//Atributos titulo boton home y flecha de acompaamiento de home
        ab.setHomeButtonEnabled(true);//activar el boton home
        ab.setDisplayShowHomeEnabled(true);//se pueda ver el boton home
        ab.setIcon(R.drawable.ic_launcher);//se le adiciona el icono
        font = Typeface.createFromAsset(this.getAssets(), "Station.ttf");
        Bundle extras = getIntent().getExtras();

        //WebView webView= (WebView) findViewById(R.id.webView);
        //webView.loadUrl("http://192.168.43.234:8080//Digilist/Informes");

        RelativeLayout bt = (RelativeLayout) findViewById(R.id.rl);
        bt.setOnClickListener(this);
        RelativeLayout bt2 = (RelativeLayout) findViewById(R.id.rl2);
        bt2.setOnClickListener(this);
        RelativeLayout bt3 = (RelativeLayout) findViewById(R.id.rl3);
        bt3.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl:
                Uri uri = Uri.parse("http://edanesar.com:8080/Digilist/Informes");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.rl2:
                Uri uri2 = Uri.parse("http://edanesar.com:8080/Digilist/Informes2");
                Intent intent2 = new Intent(Intent.ACTION_VIEW, uri2);
                startActivity(intent2);
                break;
            case R.id.rl3:
                Uri uri3 = Uri.parse("http://edanesar.com:8080/Digilist/Informes3");
                Intent intent3 = new Intent(Intent.ACTION_VIEW, uri3);
                startActivity(intent3);

                break;

        }


    }

}

