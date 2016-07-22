package com.example.paulinho.listadecompras;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.NumberPicker;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
    Lista listaNova;
    FirebaseDatabase base;
    DatabaseReference ref;
    Button botao;
    TextView listaVolta;


    EditText ed;
    NumberPicker num;
    CheckBox check;
    TextView txt;
    Produto p1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listas);

        Bundle bundle=getIntent().getExtras();
        extra=bundle.getString("LISTA");
        Log.i("BLACKLIST","Extra: "+extra);
        nomeNovaLista=bundle.getString("NOMENOVALISTA");

        total= (TextView) findViewById(R.id.tv_totalEstimado_listas);
        qtTotal= (TextView) findViewById(R.id.tv_itens_listas);
        nomeLista= (TextView) findViewById(R.id.nome_lista);
        listaPrincipal=(ExpandableListView) findViewById(R.id.lst_listaDeProdutos_listas);
        botao=(Button) findViewById(R.id.btencerra);
        botao.setOnClickListener(new ClickBotaoFechaCompra());
        categ= new ArrayList<String>();
        listaCompras=new Lista();
        categorias=new HashMap<String, List<Produto>>();
        listaVolta=(TextView) findViewById(R.id.nome_lista);

        listaVolta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(ListasActivity.this, ListaDeListasActivity.class);
                ListasActivity.this.startActivity(intent);
            }
        });

        carregaCategoria();
        opcoesLista(extra);
        listaPrincipal.setOnChildClickListener(new ClickItem());


        Log.i("BLACKLIST", "antes firebase");








    }
    public void atualizaAdapter(List<String> cat, Map<String, List<Produto>> list, String nomeLista ){
        //carregarCategorias(listaCompras.getProdutos());


        adapter= new listaCategorias(cat, list, ListasActivity.this, base, nomeLista);
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



        if(s.equals("Lista Nova"))
            criarNovaLista();
        else{
            carregaListaAtual();

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
                listaNova=new Lista();
                listaNova.setNome(edi.getText().toString());
                extra=edi.getText().toString();
                listaNova.setAberta(true);
                listaNova.setPeriodo("Mar/2016");
                FirebaseDatabase base=FirebaseDatabase.getInstance();
                DatabaseReference ref=base.getReference("Supermercados/listas");
                ref.child(listaNova.getNome()+"/periodo").setValue(listaNova.getPeriodo());
                ref.child(listaNova.getNome()+"/total").setValue(0.0);
                ref.child(listaNova.getNome()+"/aberta").setValue(true);
                carregarItensListaNova();
            }
        });
        builder.create().show();










    }


    public void carregarItensListaNova(){
        base=FirebaseDatabase.getInstance();
        ref=base.getReference("Supermercados");

        ref.child("produtos").addChildEventListener(new ChildEventListener() {
            @Override //aqui pego todos os produto
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Log.i("BLACKLIST", "aki");
                final DataSnapshot d=dataSnapshot;

                chave=dataSnapshot.getKey();
                Log.i("BLACKLIST", chave);


                //Log.i("BLACKLIST", "chave do produto: "+ref.child("listas/fev-16/produtos/"+ chave).getKey());

                if(ref.child("listas/listaBasica/produtos/"+ chave).getKey().equals(chave)){
                    ref.child("listas/listaBasica/produtos/"+ chave).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Produto p=new Produto();
                            p.setNome(d.child("nome").getValue().toString());;
                            p.setCategoria(d.child("categoria").getValue().toString());
                            p.setId(d.getKey()) ;
                            if(dataSnapshot.hasChildren()){
                                p.setPreco(dataSnapshot.child("preco").getValue(Double.class).doubleValue());
                                ref.child("listas/"+listaNova.getNome()+"/produtos/"+p.getId()+"/preco").setValue(p.getPreco());
                                p.setQuantidade(dataSnapshot.child("quantidade").getValue(Integer.class).intValue());
                                ref.child("listas/"+listaNova.getNome()+"/produtos/"+p.getId()+"/quantidade").setValue(p.getQuantidade());
                                p.setComprado(dataSnapshot.child("comprado").getValue(Boolean.class).booleanValue());
                                ref.child("listas/"+listaNova.getNome()+"/produtos/"+p.getId()+"/comprado").setValue(p.isComprado());


                                //Log.i("BLACKLIST", "Produto "+ dataSnapshot.getKey()+" entrou na lista de compras");

                                listaCompras.adcionarProdutos(p);
                                categorias= carregarCategorias(p.getCategoria(), listaCompras.getProdutos());
                                Log.i("BLACKLIST", "<------------------------------->");
                                Log.i("BLACKLIST", "Produto inserido: "+p.getNome());
                                Log.i("BLACKLIST", "Produtos na lista: "+listaCompras.getProdutos().size());
                                Log.i("BLACKLIST", "categorias: "+categ.size());
                                Log.i("BLACKLIST", "categorias map: "+categorias.size());
                                Log.i("BLACKLIST", "                                    ");

                                ListasActivity.this.atualizaAdapter(categ, categorias, listaNova.getNome());
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
        });
    }

    public void carregaListaAtual(){
        base=FirebaseDatabase.getInstance();
        ref=base.getReference("Supermercados");
        //ref.child("listas/"+extra+"/total").setValue(0.0);
        ref.child("produtos").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Log.i("BLACKLIST", "aki");
                final DataSnapshot d=dataSnapshot;

                chave=dataSnapshot.getKey();
                Log.i("BLACKLIST", chave);


                //Log.i("BLACKLIST", "chave do produto: "+ref.child("listas/fev-16/produtos/"+ chave).getKey());
                if(ref.child("listas/"+extra+"/produtos/"+ chave).getKey().equals(chave)){
                    ref.child("listas/"+extra+"/produtos/"+ chave).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Produto p=new Produto();
                            p.setNome(d.child("nome").getValue().toString());;
                            p.setCategoria(d.child("categoria").getValue().toString());
                            p.setId(d.getKey());

                            if(dataSnapshot.hasChildren()){
                                p.setPreco(dataSnapshot.child("preco").getValue(Double.class).doubleValue());
                                p.setQuantidade(dataSnapshot.child("quantidade").getValue(Integer.class).intValue());
                                p.setComprado(dataSnapshot.child("comprado").getValue(Boolean.class).booleanValue());

                                if(p.isComprado()){
                                    totalItem+=p.getQuantidade();
                                    totalCompra+=p.getPreco()*p.getQuantidade();

                                }

                                //Log.i("BLACKLIST", "Produto "+ dataSnapshot.getKey()+" entrou na lista de compras");

                                listaCompras.adcionarProdutos(p);
                                categorias= carregarCategorias(p.getCategoria(), listaCompras.getProdutos());
                                Log.i("BLACKLIST", "Produto inserido: "+p.getNome());
                                Log.i("BLACKLIST", "Produtos na lista: "+listaCompras.getProdutos().size());
                                Log.i("BLACKLIST", "categorias: "+categ.size());
                                Log.i("BLACKLIST", "categorias map: "+categorias.size());

                                ListasActivity.this.atualizaAdapter(categ, categorias, extra);
                            }

                            //ref.child("listas/"+extra+"/total").setValue(totalCompra);
                            qtTotal.setText("Quantidade de mercadorias: "+totalItem);
                            total.setText("Valor total atual: "+ totalCompra);

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
        });
    }

    /*private class ClickItemLista implements ExpandableListView.OnChildClickListener{

        @Override
        public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

            AlertDialog.Builder builder=new AlertDialog.Builder(ListasActivity.this);
            LayoutInflater inflater= (LayoutInflater) ListasActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v= inflater.inflate(R.layout.layout_alert_set_item, null);
            EditText edi= (EditText) v.findViewById(R.id.pr);
            NumberPicker num= (NumberPicker) v.findViewById(R.id.npck);
            num.setMaxValue(10);
            num.setMinValue(1);
            num.setWrapSelectorWheel(false);
            builder.setView(v);
            builder.setTitle("Alterar Preço e quantidade");
            builder.setPositiveButton("sim", null);
            builder.setNegativeButton("cancelar",null);

            builder.create().show();







            return false;
        }
    }*/
    private class ClickItem implements ExpandableListView.OnChildClickListener{

        @Override
        public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

            final int child=i1;
            final int parent=i;
            txt=(TextView)view.findViewById(R.id.text_produto);
            ref=base.getReference("Supermercados/listas");
            AlertDialog.Builder builder=new AlertDialog.Builder(ListasActivity.this);
            LayoutInflater inflater= (LayoutInflater) ListasActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v= inflater.inflate(R.layout.layout_alert_set_item, null);
            ed= (EditText) v.findViewById(R.id.pr);
            num= (NumberPicker) v.findViewById(R.id.npck);
            check= (CheckBox) v.findViewById(R.id.check_produto);
            num.setMaxValue(10);
            num.setMinValue(1);
            num.setWrapSelectorWheel(false);
            p1=categorias.get(categ.get(parent)).get(child);
            num.setValue(p1.getQuantidade());
            ed.setText(p1.getPreco()+"");
            check.setChecked(p1.isComprado());

            check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b==true){
                        check.setChecked(true);
                    }else{
                        check.setChecked(false);
                    }

                }
            });
            builder.setView(v);
            builder.setTitle("Alterar "+p1.getId());
            builder.setPositiveButton("sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if(check.isChecked()==true && p1.isComprado()==true){
                        totalItem+=num.getValue()-p1.getQuantidade();
                        totalCompra+=Double.parseDouble(ed.getText().toString())*num.getValue()- p1.getQuantidade()* p1.getPreco();
                    }else if(check.isChecked()==true && p1.isComprado()==false){
                        totalItem+=num.getValue();
                        totalCompra+=Double.parseDouble(ed.getText().toString())*num.getValue();
                    }else if(check.isChecked()==false && p1.isComprado()==true){
                        totalItem-=p1.getQuantidade();
                        totalCompra-=p1.getQuantidade()* p1.getPreco();
                    }



                    p1.setQuantidade(num.getValue());
                    p1.setPreco(Double.parseDouble(ed.getText().toString()));
                    p1.setComprado(check.isChecked());
                    ref.child(extra+"/produtos/"+p1.getId()+"/preco").setValue(p1.getPreco());
                    ref.child(extra+"/produtos/"+p1.getId()+"/quantidade").setValue(p1.getQuantidade());
                    ref.child(extra+"/produtos/"+p1.getId()+"/comprado").setValue(p1.isComprado());
                    ref.child(extra+"/total").setValue(totalCompra);
                    qtTotal.setText("Quantidade de mercadorias: "+totalItem);
                    //DecimalFormat df=new DecimalFormat("0,##");

                    NumberFormat format=NumberFormat.getInstance();
                    format.setMaximumFractionDigits(2);
                    format.setMinimumFractionDigits(2);
                    format.setRoundingMode(RoundingMode.HALF_UP);
                    double t=Double.valueOf(format.format(totalCompra));
                    total.setText("Valor total atual: "+ t);
                    //txt.setText(p.toString());
                    atualizaAdapter(categ, categorias, extra);


                }
            });
            builder.setNegativeButton("cancelar",null);

            builder.create().show();


            return false;
        }
    }

    private class ClickBotaoFechaCompra implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            base=FirebaseDatabase.getInstance();
            ref=base.getReference("Supermercados/listas/"+extra);

            AlertDialog.Builder builder = new AlertDialog.Builder(ListasActivity.this);
            builder.setTitle("Finalizar a compra");
            builder.setMessage("Tem certeza que quer finalizar a lista "+extra);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setPositiveButton("sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ref.child("aberta").setValue(false);
                    Toast.makeText(ListasActivity.this,"Total da compra "+totalCompra, Toast.LENGTH_LONG).show();
                    Intent intent= new Intent(ListasActivity.this,ListaDeListasActivity.class);
                    ListasActivity.this.startActivity(intent);
                }
            });
            builder.setNegativeButton("não", null);
            builder.create().show();


        }
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
