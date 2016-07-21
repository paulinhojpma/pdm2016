package com.example.paulinho.listadecompras;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

/**
 * Created by VaioPro on 7/18/2016.
 */
public class BroadcastReceiver extends android.content.BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        AlertDialog.Builder aDBuilder = new AlertDialog.Builder(context);
        aDBuilder.setTitle("Bateria Fraca");
        aDBuilder.setMessage("A bateria do dispositivo está fraca, desligue o GPS caso já esteja realizando suas compras!");
        AlertDialog alert = aDBuilder.create();
        alert.show();
    }
}
