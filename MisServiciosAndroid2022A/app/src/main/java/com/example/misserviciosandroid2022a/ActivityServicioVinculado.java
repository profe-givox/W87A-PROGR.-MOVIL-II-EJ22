package com.example.misserviciosandroid2022a;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

public class ActivityServicioVinculado extends AppCompatActivity {
    ServicioVinculado mService;
    boolean mBound =false;

    @Override
    protected void onStart() {
        super.onStart();

        //servicio//aqui puedes vincular el servicio
        Intent intent = new Intent(this, ServicioVinculado.class);
        bindService(intent, conecction, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicio_vinculado);

        //servicio//aqui puedes vincular el servicio
        findViewById(R.id.btnSVI).setOnClickListener(view -> {
            if(mBound){
                int n = mService.getNumeroAleatorio();
                Toast.makeText(this, "Numero aleatorio: " + n
                        , Toast.LENGTH_SHORT).show();
            }
        });
    }

    ServiceConnection conecction = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mService =   ((ServicioVinculado.MyBinder)iBinder).getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBound = false;
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(conecction);
        mBound = false;
    }
}