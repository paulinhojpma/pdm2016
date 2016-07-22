package com.example.paulinho.listadecompras;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class EstatisticasActivity extends AppCompatActivity {

    private Button btnVaricaoGeral;
    private final int GERAL=1;
    private FirebaseDatabase base;
    private DatabaseReference ref;
    private List<String> listas;
    Intent intent = null;
    String lis;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatisticas);
        this.btnVaricaoGeral = (Button) findViewById(R.id.btn_graficoGeral_estatisticas);
        this.setIdsOnButtons();
        this.setButtonListeners();
        base=FirebaseDatabase.getInstance();
        ref=base.getReference("Supermercados/listas");
        carregarNomeLista();
    }

    private void setIdsOnButtons() {
        btnVaricaoGeral.setId(GERAL);
    }

    private void setButtonListeners() {
        btnVaricaoGeral.setOnClickListener(new estatisticasScrreenButtonClick());
    }

    class estatisticasScrreenButtonClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {



            switch (v.getId()){
                case GERAL     :
                    ListView listaEscolha= new ListView(EstatisticasActivity.this);
                    ArrayAdapter adapter=new ArrayAdapter<String>(EstatisticasActivity.this, android.R.layout.simple_list_item_1,listas);
                    listaEscolha.setAdapter(adapter);
                    listaEscolha.setOnItemClickListener(new NomeLista());
                    AlertDialog.Builder builder=new AlertDialog.Builder(EstatisticasActivity.this);
                    builder.setTitle("Escolha a lista");
                    builder.setView(listaEscolha);
                    builder.setNegativeButton("Cancelar", null);
                    builder.setPositiveButton("Gerar gráfico", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            intent = new Intent(EstatisticasActivity.this, GraficoActivity.class);
                            intent.putExtra("NOME", lis);
                            EstatisticasActivity.this.startActivity(intent);
                        }
                    });
                    builder.create().show();
                { break;}
                default          : {break;}
            }

        }
    }

    public void carregarNomeLista(){
        listas=new ArrayList<String>();
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String t=dataSnapshot.getKey();
                if(!t.equals("listaBasica")){
                    listas.add(t);
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

   private class NomeLista implements ListView.OnItemClickListener{

       @Override
       public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
           lis=adapterView.getAdapter().getItem(i).toString();


           view.setBackgroundColor(R.color.LBlue);


       }
   }
}
