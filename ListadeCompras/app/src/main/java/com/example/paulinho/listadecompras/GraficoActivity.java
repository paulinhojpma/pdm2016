package com.example.paulinho.listadecompras;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraficoActivity extends AppCompatActivity {

    private ListView graphList;
    FirebaseDatabase base;
    DatabaseReference ref;


    double grao;
    double frio;
    double bebida;
    double carne;
    double higiene;
    double limpeza;
    double verdura;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafico);
        carregaValores();
        createGraph();
    }

    private void createGraph(){

        Log.i("lalala", "entrou gostoso");
        graphList = (ListView)findViewById(R.id.lista_grafico);
        ArrayList<String> labelsArray = new ArrayList<String>();


        GraphView graph = (GraphView) findViewById(R.id.graph);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(1, grao),
                new DataPoint(2, frio),
                new DataPoint(3, bebida),
                new DataPoint(4, carne),
                new DataPoint(5, higiene),
                new DataPoint(6, limpeza),
                new DataPoint(7, verdura),
        });

        Log.i("lalala", ""+grao);
        Log.i("lalala", ""+frio);
        Log.i("lalala", ""+bebida);
        Log.i("lalala", ""+carne);

        labelsArray.add("1 = Grãos");
        labelsArray.add("2 = Frios");
        labelsArray.add("3 = Carnes");
        labelsArray.add("4 = Bebidas");
        labelsArray.add("5 = Higiene");
        labelsArray.add("6 = Limpeza");
        labelsArray.add("7 = Verduras e Frutas");


        Adapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, labelsArray);
        graphList.setAdapter((ListAdapter)adapter);

        graph.addSeries(series);

        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        series.setSpacing(15);
        series.setDrawValuesOnTop(true);
    }

    public void carregaValores(){
       base=FirebaseDatabase.getInstance();
        ref=base.getReference("Supermercados");

        ref.child("produtos").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //Log.i("BLACKLIST", "aki");
                final DataSnapshot d=dataSnapshot;

                dataSnapshot.getKey();
                //Log.i("BLACKLIST", chave);


                //Log.i("BLACKLIST", "chave do produto: "+ref.child("listas/fev-16/produtos/"+ chave).getKey());
                if(ref.child("listas/fev-16/produtos/"+ dataSnapshot.getKey()).getKey().equals(dataSnapshot.getKey())){
                    ref.child("listas/spock/produtos/"+ dataSnapshot.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
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

                                switch (p.getCategoria()){
                                    case "grãos":

                                        if(p.isComprado()){
                                            grao+=p.getPreco()*p.getQuantidade();
                                            Log.i("BLACKLIST", grao+"");
                                        }

                                        break;
                                    case "frios":
                                        if(p.isComprado()){
                                            frio+=p.getPreco()*p.getQuantidade();
                                            Log.i("BLACKLIST",frio+"");
                                        }
                                        break;
                                    case "bebida":
                                        if(p.isComprado()){
                                            bebida+=p.getPreco()*p.getQuantidade();
                                            Log.i("BLACKLIST", bebida+"");
                                        }
                                        break;
                                    case "carnes":
                                        if(p.isComprado()){
                                            carne+=p.getPreco()*p.getQuantidade();
                                            Log.i("BLACKLIST", carne+"");
                                        }
                                        break;
                                    case "higiene":
                                        if(p.isComprado()){
                                            higiene+=p.getPreco()*p.getQuantidade();
                                            Log.i("BLACKLIST", higiene+"");
                                        }
                                        break;
                                    case "limpeza":
                                        if(p.isComprado()){
                                            limpeza+=p.getPreco()*p.getQuantidade();
                                            Log.i("BLACKLIST", limpeza+"");
                                        }
                                        break;
                                    case "frutas e verduras":
                                        if(p.isComprado()){
                                            verdura+=p.getPreco()*p.getQuantidade();
                                            Log.i("BLACKLIST", verdura+"");
                                        }
                                        break;
                                    default:
                                        Log.i("BLACKLIST", "não existe categorias");
                                }
                                createGraph();
                            }



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


}
