package com.example.paulinho.listadecompras;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    private Button btnFazerCompras;
    private Button btnListas;
    private Button btnEstatisticas;
    private final int COMPRAS=0, LISTAS=1, ESTATISTICAS=2;
    //String v, chave;
    FirebaseDatabase base;
    DatabaseReference ref;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.btnFazerCompras = (Button) findViewById(R.id.btn_fazerCompras_main);
        this.btnListas       = (Button) findViewById(R.id.btn_listas_main);
        this.btnEstatisticas = (Button) findViewById(R.id.btn_info_main);
        this.setIdsOnButtons();
        this.setButtonListeners();





        base=FirebaseDatabase.getInstance();
        ref=base.getReference("Supermercados");
        //Log.i("BLACKLIST", ref.getKey());

        /*ref.child("produtos").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i("BLACKLIST", "aki");
              final DataSnapshot d=dataSnapshot;

                chave=dataSnapshot.getKey();
                Log.i("BLACKLISt", chave);


               if(ref.child("listas/listaBasica/produtos/"+ chave).getKey().equals(chave)){
                    ref.child("listas/listaBasica/produtos/"+ chave).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            v="Nome: "+d.child("nome").getValue().toString()+" ";
                            v+="Categoria: "+d.child("categoria").getValue().toString()+"\n";
                            v+="Chave: "+d.getKey()+"\n";
                            v+="Preco :"+dataSnapshot.child("preco").getValue().toString()+"\n";
                            v+=dataSnapshot.getKey()+"\n";
                            Log.i("BLACKLIST", v);
                            teste.setText(v);
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


        //teste.setText(v);

    }



   private void setIdsOnButtons(){
        this.btnFazerCompras.setId(COMPRAS);
        this.btnListas.setId(LISTAS);
        this.btnEstatisticas.setId(ESTATISTICAS);
    }

    private void setButtonListeners() {
        this.btnFazerCompras.setOnClickListener(new mainScrreenButtonClick());
        this.btnListas.setOnClickListener(new mainScrreenButtonClick());
        this.btnEstatisticas.setOnClickListener(new mainScrreenButtonClick());
    }

    class mainScrreenButtonClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = null;
            Context c = MainActivity.this;
            switch (v.getId()){
                case COMPRAS     : {intent = new Intent(c, FazerComprasActivity.class); break;}
                case LISTAS      : {intent = new Intent(c, ListaDeListasActivity.class);break;}
                case ESTATISTICAS: {intent = new Intent(c, EstatisticasActivity.class); break;}
                default          : {break;}
            }
            c.startActivity(intent);
        }
    }
}
