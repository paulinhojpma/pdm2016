package br.edu.ifpb.si.pdm.localiza;

import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;


public class SobreActivity extends AppCompatActivity {
    TextView texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);
        texto= (TextView) findViewById(R.id.texto);
        texto.setOnClickListener(new ItemClick());

    }


    private class ItemClick implements View.OnClickListener{

        public void onClick(View v){
            Intent it=new Intent(SobreActivity.this,MainActivity.class);
            startActivity(it);
        }
    }

}
