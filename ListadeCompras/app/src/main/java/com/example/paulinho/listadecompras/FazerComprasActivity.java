package com.example.paulinho.listadecompras;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FazerComprasActivity extends AppCompatActivity {

    private Button btnListaDeProdutos;
    private Button btnCalcularRota;
    private final int LISTA_DE_PRODUTOS=0, CALCULAR_ROTAS=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fazer_compras);
        this.btnListaDeProdutos = (Button) findViewById(R.id.btn_verLista_FazerCompras);
        this.btnCalcularRota    = (Button) findViewById(R.id.btn_calcRota_fazerCompras);
        setIdsOnButtons();
        setButtonListeners();
    }

    private void setIdsOnButtons(){
        this.btnListaDeProdutos.setId(LISTA_DE_PRODUTOS);
        this.btnCalcularRota.setId(CALCULAR_ROTAS);
    }

    private void setButtonListeners() {
        this.btnListaDeProdutos.setOnClickListener(new FazerComprasScrreenButtonClick());
       // this.btnCalcularRota.setOnClickListener(new FazerComprasScrreenButtonClick());
    }

    class FazerComprasScrreenButtonClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = null;
            Context c = FazerComprasActivity.this;
            switch (v.getId()){
                case LISTA_DE_PRODUTOS: {intent = new Intent(c, ListasActivity.class); break;}
               // case CALCULAR_ROTAS : {intent = new Intent(c, ListaDeListasActivity.class);break;}
                default: {break;}
            }
            c.startActivity(intent);
        }
    }
}
