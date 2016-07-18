package com.example.paulinho.listadecompras;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListaDeListasActivity extends AppCompatActivity {

    private Button btnListaNovaOuAtual;
    private final int LISTA_NOVA_OU_ATUAL=0;
    FirebaseDatabase base;
    DatabaseReference ref;
    private ListView listaVelhas;
    private List<String> listas;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_listas);
        base= FirebaseDatabase.getInstance();
        ref=base.getReference("Supermercados");
        this.btnListaNovaOuAtual = (Button) findViewById(R.id.btn_listaAtualOuNova_listaDeListas);
        listaVelhas= (ListView) findViewById(R.id.lst_listaDeListas_listasDeListas);
        listas= new ArrayList<String>();


        setIdsOnButtons();
        setButtonListeners();
        carregarListasAntigas();
        criarLista();


    }

    private void setIdsOnButtons(){
        this.btnListaNovaOuAtual.setId(LISTA_NOVA_OU_ATUAL);
    }

    private void setButtonListeners() {
        this.btnListaNovaOuAtual.setOnClickListener(new FazerComprasScrreenButtonClick());
    }

    class FazerComprasScrreenButtonClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            Intent intent = null;
            Context c = ListaDeListasActivity.this;
            switch (v.getId()){

                case LISTA_NOVA_OU_ATUAL: {
                    intent = new Intent(c, ListasActivity.class);

                    intent.putExtra("LISTA",  btnListaNovaOuAtual.getText().toString());
                    break;}
                default: { break;}
            }
            c.startActivity(intent);
        }
    }

    public void criarLista(){

        ChildEventListener listener=new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.child("aberta").getValue()!=null) {
                    if (dataSnapshot.child("aberta").getValue(boolean.class).booleanValue()) {
                        btnListaNovaOuAtual.setText(dataSnapshot.getKey());
                    }
                    //Log.i("BLACKLIST", op);
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
        };

        ref.child("listas").addChildEventListener(listener);
        //ref.child("lista").addChildEventListener(listener);


    }

    public void carregarListasAntigas(){


        ChildEventListener listener=new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               // if(dataSnapshot.getChildren()!=null) {
                Log.i("BLACKLIST",dataSnapshot.getKey());
                Log.i("BLACKLIST",dataSnapshot.child("periodo").getValue(String.class));
                //Log.i("BLACKLIST",dataSnapshot.child("total").getValue(Double.class).doubleValue()+"");
                //Log.i("BLACKLIST",dataSnapshot.child("aberta").getValue(boolean.class).booleanValue()+"");
                    if(dataSnapshot.child("aberta").getValue()!=null){
                    if (!dataSnapshot.child("aberta").getValue(boolean.class)) {
                        Lista l = new Lista();

                        l.setNome(dataSnapshot.getKey());
                        l.setPeriodo(dataSnapshot.child("periodo").getValue(String.class));
                        l.setTotal(dataSnapshot.child("total").getValue(Double.class).doubleValue());


                        if (!l.getNome().equals("listaBasica")) {
                            listas.add(l.toString());
                            adapter = new ArrayAdapter<String>(ListaDeListasActivity.this, android.R.layout.simple_list_item_1, listas);
                            listaVelhas.setAdapter(adapter);
                        }


                    }
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
        };
        ref.child("listas").addChildEventListener(listener);
        //ref.child("listas").removeEventListener(listener);
    }


}
