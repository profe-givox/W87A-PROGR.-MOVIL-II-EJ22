package com.example.misserviciosandroid2022a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Intent intent ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         intent = new Intent(this, ServicioSimple.class);

        findViewById(R.id.btnSS).setOnClickListener(view -> {
            //startService(intent);
            //startService(new Intent(this, MiIntentService.class));
            Intent miI = new Intent(this, MiIntentService.class);
            miI.putExtra("arg1", 99999);
            startService(miI);
        });

        findViewById(R.id.btnSP).setOnClickListener(view -> {
            Toast.makeText(this, "Interactuando", Toast.LENGTH_SHORT).show();
            //stopService(intent);
        });

    }
}