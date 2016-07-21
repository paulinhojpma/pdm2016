package com.example.paulinho.listadecompras;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EstatisticasActivity extends AppCompatActivity {

    private Button btnVaricaoGeral;
    private final int GERAL=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatisticas);
        this.btnVaricaoGeral = (Button) findViewById(R.id.btn_graficoGeral_estatisticas);
        this.setIdsOnButtons();
        this.setButtonListeners();
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
            Intent intent = null;
            Context c = EstatisticasActivity.this;
            switch (v.getId()){
                case GERAL     : {intent = new Intent(c, GraficoActivity.class); break;}
                default          : {break;}
            }
            c.startActivity(intent);
        }
    }
}
