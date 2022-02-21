package net.ivanvega.missensores2022a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    SensorManager sensorManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> lsSensores =
                sensorManager.getSensorList(Sensor.TYPE_ALL);

        for(Sensor sensor : lsSensores){
            String sn = "Nombre: " + sensor.getName();
            sn+= " Frabricante: " ;

            Log.d("SENSORX", sensor.toString());

        }

    }
}