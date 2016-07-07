package br.edu.ifpb.si.pdm.localiza;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.List;

public class CadastroActivity extends AppCompatActivity {
    private EditText nome, descricao;
    private Button botao, btimagem, btLocal;
    private ImageView imagem;
    private Bitmap fota;
    public static final int FOTO=1;
    public LocationManager manager;
    private String local;
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
        btLocal= (Button) findViewById(R.id.botaoLocal);
        btLocal.setOnClickListener(new LocalClick());

        this.manager = (LocationManager) getSystemService(LOCATION_SERVICE);




    }


    private class OnClick implements View.OnClickListener{
        public void onClick(View v){
            String nome=CadastroActivity.this.nome.getText().toString();
            String descricao=CadastroActivity.this.descricao.getText().toString();
            Intent it=new Intent(CadastroActivity.this, MainActivity.class);
            it.putExtra("NOME", nome);
            it.putExtra("DESCRICAO", descricao);
            it.putExtra("FOTO", fota);
            it.putExtra("LOCAL", local);

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

    private class GPSListener implements LocationListener{

        @Override
        public void onLocationChanged(Location location) {
            Log.i("GPS", "localizacao pega");
            local=String.format("%f %f", location.getLatitude(), location.getLongitude());
            if (ContextCompat.checkSelfPermission(CadastroActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(CadastroActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
            CadastroActivity.this.manager.removeUpdates(this);

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }

    private class LocalClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Log.i("GPS", "Chamar GPS");
            if (ContextCompat.checkSelfPermission(CadastroActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(CadastroActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
            CadastroActivity.this.manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new GPSListener());
            Log.i("GPS", "Localização solicitada");
        }
    }
}
