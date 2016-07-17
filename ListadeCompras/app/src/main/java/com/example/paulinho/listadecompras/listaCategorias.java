package com.example.paulinho.listadecompras;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import android.widget.CheckBox;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Paulinho on 14/07/2016.
 */
public class listaCategorias extends BaseExpandableListAdapter{
    private Context context;
    private List<String> categorias;
    private Map<String, List<Produto>> produtos;


    public listaCategorias(List<String> categorias, Map<String, List<Produto>> produtos,  Context context) {
        this.context = context;
        this.categorias=categorias;

        this.produtos=produtos;
    }

    @Override
    public int getGroupCount() {
        return this.categorias.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return this.produtos.get(this.categorias.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return this.categorias.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return this.produtos.get(this.categorias.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        TextView texto;
        String categoria=(String)getGroup(i);
        View covert;
            if (view == null) {
                LayoutInflater li = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                covert = li.inflate(R.layout.lista_categoria, null);
            }else{
                covert=view;
            }

            texto = (TextView) covert.findViewById(R.id.texto_categoria);

            texto.setText(categoria);


            //view.setBackgroundColor();




        return covert;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        CheckBox check;
        TextView texto;
       Produto p=(Produto) getChild(i, i1);
        View covert;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            covert = li.inflate(R.layout.lista_produto, null);
        }else{
            covert=view;
        }
        texto= (TextView) covert.findViewById(R.id.text_produto);
        check= (CheckBox) covert.findViewById(R.id.check_produto);
        check.setChecked(p.isComprado());

        texto.setText(p.toString());
        return covert;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
