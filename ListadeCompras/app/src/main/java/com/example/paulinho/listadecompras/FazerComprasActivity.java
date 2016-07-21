package com.example.paulinho.listadecompras;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FazerComprasActivity extends AppCompatActivity {

    private TextView tvNomeSupermercado;
    private Button btnListaDeProdutos;
    private Button btnCalcularRota;
    private LocationManager locationManager;
    private Location currLocation;
    private final int LISTA_DE_PRODUTOS = 0, CALCULAR_ROTAS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fazer_compras);
        this.tvNomeSupermercado = (TextView) findViewById(R.id.tv_nomeSupermercado_fazerCompras);
        this.btnListaDeProdutos = (Button) findViewById(R.id.btn_verLista_FazerCompras);
        this.btnCalcularRota = (Button) findViewById(R.id.btn_calcRota_fazerCompras);
       // startGPSService();
        setIdsOnButtons();
        setButtonListeners();
    }

    /*private void startGPSService() {
        this.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new GPSListener());
    }*/

    private void setIdsOnButtons(){
        this.btnListaDeProdutos.setId(LISTA_DE_PRODUTOS);
        this.btnCalcularRota.setId(CALCULAR_ROTAS);
    }

    private void setButtonListeners() {
        this.btnListaDeProdutos.setOnClickListener(new FazerComprasScrreenButtonClick());
        this.btnCalcularRota.setOnClickListener(new FazerComprasScrreenButtonClick());
    }

    private class FazerComprasScrreenButtonClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = null;
            Context c = FazerComprasActivity.this;
            switch (v.getId()){
                case LISTA_DE_PRODUTOS: {intent = new Intent(c, ListaDeListasActivity.class); break;}
                case CALCULAR_ROTAS : {startGoogleMapsIntent(); break;}
                default: {break;}
            }
            if(intent!=null)
                c.startActivity(intent);
        }

        private void startGoogleMapsIntent() {
           // Location l = FazerComprasActivity.this.currLocation;
            //if(l!=null) {
                Uri gMapsUri = Uri.parse("google.navigation:q=" + (-7.1482631) + "," + (-34.8439997));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gMapsUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            //}
        }
    }

    /*private class GPSListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            FazerComprasActivity.this.tvNomeSupermercado.setText("location: " + location.getLatitude());
            currLocation = location;
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {}

        @Override
        public void onProviderEnabled(String s) {}

        @Override
        public void onProviderDisabled(String s) {}
    }*/
}
