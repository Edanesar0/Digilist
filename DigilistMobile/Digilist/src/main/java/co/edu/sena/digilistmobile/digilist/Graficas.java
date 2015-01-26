package co.edu.sena.digilistmobile.digilist;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockActivity;

/**
 * Created by ADMIN on 12/12/2014.
 */
public class Graficas extends SherlockActivity {
    private View v = null;
    private ViewPager vp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_almacenista);
        vp = (ViewPager) findViewById(R.id.pager);
        vpAdapter2 miAdapter = new vpAdapter2();
        vp.setAdapter(miAdapter);
    }


    private class vpAdapter2 extends PagerAdapter {
        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public void finishUpdate(ViewGroup container) {
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            switch (position) {
                case 0:
                    //v = inflater.inflate(R.layout.progress, null);
                    v = inflater.inflate(R.layout.piechart, null);
                    Almacenista almacenista = new Almacenista(v, Graficas.this, Graficas.this);
                    almacenista.pie();

                    break;
                case 1:
                    v = inflater.inflate(R.layout.ingreso_producto, null);

                    break;
                case 2:
                    v = inflater.inflate(R.layout.inventario, null);

                case 3:
                    break;


            }
            ((ViewPager) container).addView(v, 0);
            return v;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(ViewGroup container) {


        }
    }
}
