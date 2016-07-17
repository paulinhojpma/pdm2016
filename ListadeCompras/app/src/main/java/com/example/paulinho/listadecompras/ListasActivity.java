package com.example.paulinho.listadecompras;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ListasActivity extends AppCompatActivity {

    TextView total;
    TextView qtTotal;
    TextView nomeLista;
    Produto p;
    String chave;
    ExpandableListView listaPrincipal;
    listaCategorias adapter;
    private List<String> categ;
    private Map<String, List<Produto>> categorias;
    private Lista listaCompras;
    EditText edi;
    private int totalItem;
    private double totalCompra;
    Intent intent;
    String extra;
    String nomeNovaLista;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listas);

        Bundle bundle=getIntent().getExtras();
        extra=bundle.getString("LISTA");
        nomeNovaLista=bundle.getString("NOMENOVALISTA");

        total= (TextView) findViewById(R.id.tv_totalEstimado_listas);
        qtTotal= (TextView) findViewById(R.id.tv_itens_listas);
        nomeLista= (TextView) findViewById(R.id.nome_lista);
        listaPrincipal=(ExpandableListView) findViewById(R.id.lst_listaDeProdutos_listas);
        categ= new ArrayList<String>();

        categorias=new HashMap<String, List<Produto>>();

        carregaCategoria();
        opcoesLista(extra);


        Log.i("BLACKLIST", "antes firebase");








    }
    public void atualizaAdapter(List<String> cat, Map<String, List<Produto>> list ){
        //carregarCategorias(listaCompras.getProdutos());


        adapter= new listaCategorias(cat, list, ListasActivity.this);
        listaPrincipal.setAdapter(adapter);
        Log.i("BLACKLIST", "carregando adapter");

    }
   /* @Override
    protected void onStart(){
        carregarCategorias(listaCompras.getProdutos());
        adapter= new listaCategorias(categ, categorias, this);
        listaPrincipal.setAdapter(adapter);
    }*/



    public Map<String, List<Produto>> carregarCategorias(String categoria, List<Produto> produtos){
       List<Produto> grao=new ArrayList<Produto>();
        List<Produto> frio=new ArrayList<Produto>();
        List<Produto> bebida=new ArrayList<Produto>();
        List<Produto> carne=new ArrayList<Produto>();
        List<Produto> higiene=new ArrayList<Produto>();
        List<Produto> limpeza=new ArrayList<Produto>();
        List<Produto> verdura=new ArrayList<Produto>();

        Map<String, List<Produto>>  mapaProduto = new HashMap<String, List<Produto>>();
       for (Produto p: produtos) {

           switch (p.getCategoria()){
               case "grãos":
                   grao.add(p);
                   //mapaProduto.put(p.getCategoria(), grao);
                   break;
               case "frios":
                   frio.add(p);
                   //mapaProduto.put(p.getCategoria(), frio);
                   break;
               case "bebida":
                   bebida.add(p);
                   //mapaProduto.put(p.getCategoria(), bebida);
                   break;
               case "carnes":
                   carne.add(p);
                   //mapaProduto.put(p.getCategoria(), carne);
                   break;
               case "higiene":
                   higiene.add(p);
                   //mapaProduto.put(p.getCategoria(), higiene);
                   break;
               case "limpeza":
                   limpeza.add(p);
                   //mapaProduto.put(p.getCategoria(), limpeza);
                   break;
               case "frutas e verduras":
                   verdura.add(p);
                   break;
               default:
                   Log.i("BLACKLIST", "não existe categorias");
           }

        }

        mapaProduto.put("grãos", grao);
        mapaProduto.put("frios", frio);
        mapaProduto.put("bebida", bebida);
        mapaProduto.put("carnes", carne);
        mapaProduto.put("higiene", higiene);
        mapaProduto.put("limpeza", limpeza);
        mapaProduto.put("frutas e verduras", verdura);


        return mapaProduto;

    }

    public void carregaCategoria(){
        categ.add("grãos");
        categ.add("frios");
        categ.add("bebida");
        categ.add("carnes");
        categ.add("higiene");
        categ.add("limpeza");
        categ.add("frutas e verduras");
    }

    public void listas(){

    }

    public void opcoesLista(String s){


        switch (s){
            case"Lista Nova":
                criarNovaLista();
                break;



        }


    }

    public void criarNovaLista(){
        edi=new EditText(ListasActivity.this);
        edi.setHint("Nome da lista");
        AlertDialog.Builder builder= new AlertDialog.Builder(ListasActivity.this);
        builder.setTitle("Nova Lista");
        builder.setMessage("De o nome da lista");
        builder.setView(edi);
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Lista listaNova=new Lista();
                listaNova.setNome(edi.getText().toString());
                listaNova.setAberta(true);
                listaNova.setPeriodo("Mar/2016");
                FirebaseDatabase base=FirebaseDatabase.getInstance();
                DatabaseReference ref=base.getReference("Supermercados/listas");
                ref.child(listaNova.getNome()+"/periodo").setValue(listaNova.getPeriodo());
                ref.child(listaNova.getNome()+"/total").setValue(0.0);
                ref.child(listaNova.getNome()+"/aberta").setValue(true);
            }
        });
        builder.create().show();










    }






    /*ref.child("produtos").addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            //Log.i("BLACKLIST", "aki");
            final DataSnapshot d=dataSnapshot;

            chave=dataSnapshot.getKey();
            Log.i("BLACKLIST", chave);


            Log.i("BLACKLIST", "chave do produto: "+ref.child("listas/fev-16/produtos/"+ chave).getKey());
            if(ref.child("listas/fev-16/produtos/"+ chave).getKey().equals(chave)){
                ref.child("listas/fev-16/produtos/"+ chave).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Produto p=new Produto();
                        p.setNome(d.child("nome").getValue().toString());;
                        p.setCategoria(d.child("categoria").getValue().toString());
                        p.setId(d.getKey()) ;
                        if(dataSnapshot.hasChildren()){
                            p.setPreco(dataSnapshot.child("preco").getValue(Double.class).doubleValue());
                            p.setQuantidade(dataSnapshot.child("quantidade").getValue(Integer.class).intValue());
                            p.setComprado(dataSnapshot.child("comprado").getValue(Boolean.class).booleanValue());
                            totalCompra+=p.getPreco()*p.getQuantidade();
                            totalItem+=p.getQuantidade();

                            //Log.i("BLACKLIST", "Produto "+ dataSnapshot.getKey()+" entrou na lista de compras");

                            listaCompras.adcionarProdutos(p);
                            categorias= carregarCategorias(p.getCategoria(), listaCompras.getProdutos());
                            Log.i("BLACKLIST", "Produto inserido: "+p.getNome());
                            Log.i("BLACKLIST", "Produtos na lista: "+listaCompras.getProdutos().size());
                            Log.i("BLACKLIST", "categorias: "+categ.size());
                            Log.i("BLACKLIST", "categorias map: "+categorias.size());

                            ListasActivity.this.atualizaAdapter(categ, categorias);
                        }


                        qtTotal.setText("Quantidade de mercadorias: "+totalItem);
                        total.setText("Valor total atual"+ totalCompra);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }





        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });*/

}
