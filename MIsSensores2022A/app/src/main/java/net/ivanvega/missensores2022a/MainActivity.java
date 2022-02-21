package net.ivanvega.missensores2022a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity
 implements  SensorEventListener{
    SensorManager sensorManager;
    TextView txtProximi, txtLuz;


    Sensor sensorProximidad, sensorLuz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtProximi = findViewById(R.id.txtProximidad);
        txtLuz = findViewById(R.id.txtLuz);

        sensorManager = (SensorManager)
                getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> lsSensores =
                sensorManager.getSensorList(Sensor.TYPE_ALL);

        for(Sensor sensor : lsSensores){
            String sn = "Nombre: " + sensor.getName();
            sn+= " Frabricante: " ;

            Log.d("SENSORX", sensor.toString());

        }

        sensorProximidad =
                sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sensorProximidad!=null) {
            sensorManager.registerListener(sensorEventListenerAmbiental,
                    sensorProximidad, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(sensorLuz!=null){
            sensorManager.registerListener(sensorEventListenerAmbiental,
                    sensorLuz, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorEventListenerAmbiental);
    }

    SensorEventListener sensorEventListenerAmbiental =
            new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {

                    switch (sensorEvent.sensor.getType()){
                        case Sensor.TYPE_PROXIMITY:
                            txtProximi.setText(txtProximi.getText().toString() + ": " + sensorEvent.values[0]);
                            break;
                        case Sensor.TYPE_LIGHT:
                            txtLuz.setText("Sensor de Luz" + ": " + sensorEvent.values[0]);
                            break;
                    }


                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {

                }
            };


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}