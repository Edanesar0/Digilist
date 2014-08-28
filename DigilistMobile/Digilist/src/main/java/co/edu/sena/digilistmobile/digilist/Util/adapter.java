package co.edu.sena.digilistmobile.digilist.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import co.edu.sena.digilistmobile.digilist.R;


/**
 * Created by Ox- on 19/08/2014.
 */


public class Adapter extends BaseExpandableListAdapter implements View.OnClickListener {

    Context contexto;
    ArrayList<String> padre, hijos;

    View inflate;
    TextView tv;
    int cout;
    String Select;

    public String getSelect() {
        return Select;
    }

    public void setSelect(String select) {
        Select = select;
    }

    RadioButton radioButton;

    public Adapter(Context contex) {
        this.contexto = contex;
        padre = new ArrayList<String>();
        hijos = new ArrayList<String>();
        padre.add("asd");
        hijos.add("asd2");
    }

    @Override
    public int getGroupCount() {
        return padre.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return i;
    }

    @Override
    public Object getChild(int i, int i2) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i2) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        inflate = View.inflate(contexto, R.layout.radiobutton, null);
        radioButton = (RadioButton) inflate.findViewById(R.id.radioButton);
        radioButton.setId(i);
        radioButton.setText(padre.get(i).toString());
        radioButton.setOnClickListener(this);
//        tv.setText(padre.get(i).toString());
        tv = (TextView) inflate.findViewById(R.id.txtname);
        tv.setId(i);
        tv.setTextSize(20);

        return inflate;
    }

    @Override
    public View getChildView(int i, int i2, boolean b, View view, ViewGroup viewGroup) {
        cout = 0;
        TextView tvh = new TextView(contexto);
        tvh.setText(hijos.get(i).toString());
        tvh.setTextSize(18);
        return tvh;
    }

    @Override
    public boolean isChildSelectable(int i, int i2) {
        return false;
    }

    @Override
    public void onClick(View view) {
        RadioButton ra = (RadioButton) view;
        setSelect(ra.getText().toString());


    }
}