package br.edu.ifpb.si.pdm.localiza;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class CadastroActivity extends AppCompatActivity {
    private EditText nome, descricao;
    private Button botao, btimagem;
    private ImageView imagem;
    private Bitmap fota;
    public static final int FOTO=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        nome= (EditText) findViewById(R.id.nome);
        descricao= (EditText) findViewById(R.id.descricao);
        botao= (Button) findViewById(R.id.botao);
        botao.setOnClickListener(new OnClick());
        btimagem= (Button) findViewById(R.id.btnimagem);
        imagem= (ImageView)findViewById(R.id.imagem);
        btimagem.setOnClickListener(new ImgClick());




    }


    private class OnClick implements View.OnClickListener{
        public void onClick(View v){
            String nome=CadastroActivity.this.nome.getText().toString();
            String descricao=CadastroActivity.this.descricao.getText().toString();
            Intent it=new Intent(CadastroActivity.this, MainActivity.class);
            it.putExtra("NOME", nome);
            it.putExtra("DESCRICAO", descricao);
            it.putExtra("FOTO", fota);
            setResult(RESULT_OK, it);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == FOTO){

                fota= (Bitmap) data.getParcelableExtra("data");
                CadastroActivity.this.imagem.setImageBitmap(fota);
            }
        }
    }

    private class ImgClick implements View.OnClickListener{
        public void onClick(View v){
            Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(it, CadastroActivity.this.FOTO);
        }
    }
}
