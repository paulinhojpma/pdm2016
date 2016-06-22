package br.edu.ifpb.si.pdm.localiza;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
   public static final int NOVO_LUGAR=1, SOBRE=2, OK_RESULT=1;
    private List<Lugar> lugares;
    private ListView lista;
   public MainActivity(){
       this.lugares=new ArrayList<Lugar>();
   }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.lista= (ListView) findViewById(R.id.listView);
        ArrayAdapter<Lugar> adapter = new ArrayAdapter<Lugar>(this, android.R.layout.simple_list_item_1, this.lugares);
        this.lista.setAdapter(adapter);
        this.lista.setOnItemClickListener(new OnClickList());


    }
    @Override
   public boolean onCreateOptionsMenu(Menu menu){
       menu.add(0,NOVO_LUGAR,1,"Novo Lugar");
       menu.add(0,SOBRE, 2,"Sobre");
        return super.onCreateOptionsMenu(menu);
   }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String msg;
        switch (item.getItemId()){
            case NOVO_LUGAR:
                Intent it=new Intent(MainActivity.this, CadastroActivity.class);
                startActivityForResult(it, OK_RESULT);
                break;
            case SOBRE:
               Intent id=new Intent(MainActivity.this, SobreActivity.class);
                startActivity(id);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==OK_RESULT){
                String nome=data.getStringExtra("NOME");
                String descricao=data.getStringExtra("DESCRICAO");

                Bitmap fota=(Bitmap) data.getParcelableExtra("FOTO");

                lugares.add(new Lugar(nome,descricao, fota));
                ArrayAdapter<Lugar> adapter = new ArrayAdapter<Lugar>(this, android.R.layout.simple_list_item_1, this.lugares);
                this.lista.setAdapter(adapter);
                //Toast.makeText(this, nome, Toast.LENGTH_LONG).show();
            }
        }
    }

    private class OnClickList implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //alert(parent, view, position, id);
            LayoutInflater factory = LayoutInflater.from(MainActivity.this);
            final View wiew = factory.inflate(R.layout.imagem, null);
            ImageView alerta= (ImageView)wiew.findViewById(R.id.alertai);
            Lugar lugar=(Lugar)parent.getAdapter().getItem(position);
            alerta.setImageBitmap(lugar.getFoto());
            AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Lugar");
            builder.setMessage(parent.getAdapter().getItem(position).toString());
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setView(wiew);
            builder.setPositiveButton("ok", null);
            builder.create().show();
            //Toast.makeText(MainActivity.this, parent.getAdapter().getItem(position).toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void alert(AdapterView<?> parent, View view, int position, long id){
        AlertDialog.Builder alertadd = new AlertDialog.Builder( MainActivity.this);
        alertadd.setTitle("Lugar");

        LayoutInflater factory = LayoutInflater.from(MainActivity.this);
        final View wiew = factory.inflate(R.layout.imagem, null);
        ImageView iba= (ImageView) wiew.findViewById(R.id.alertai);
        Lugar lugar=(Lugar)parent.getAdapter().getItem(position);
        iba.setImageBitmap((Bitmap)lugar.getFoto());
        TextView text= (TextView) wiew.findViewById(R.id.alertat);
        text.setText(lugar.toString());
        alertadd.setView(wiew);

        alertadd.setNeutralButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int sumthin) {



            }

        });



        alertadd.show();




    }





}
