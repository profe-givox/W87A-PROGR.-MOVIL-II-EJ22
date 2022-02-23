package net.ivanvega.missensores2022a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity
 implements  SensorEventListener{
    SensorManager sensorManager;
    TextView txtProximi, txtLuz;

    Sensor sensorProximidad, sensorLuz;   //sensores de ambiente

    Sensor sensorCampoMagnetico, sensorAcelermeter   ; //Posicionamiento

    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];
    private boolean mLastAccelerometerSet=false;
    private boolean mLastMagnetometerSet=false;

    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];
    private TextView txtOrientarion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtProximi = findViewById(R.id.txtProximidad);
        txtLuz = findViewById(R.id.txtLuz);
         txtOrientarion = findViewById(R.id.txtOrientacion);

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

        sensorAcelermeter = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorCampoMagnetico = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

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

        if(sensorCampoMagnetico!=null){
            sensorManager.registerListener(this,
                    sensorCampoMagnetico, SensorManager.SENSOR_DELAY_UI);
        }
        if(sensorAcelermeter!=null){
            sensorManager.registerListener(this,
                    sensorAcelermeter, SensorManager.SENSOR_DELAY_UI);
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
        switch (sensorEvent.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER:
                  System.arraycopy(sensorEvent.values, 0, 
                          accelerometerReading, 0, accelerometerReading.length);
                  mLastAccelerometerSet = true;
                
            break;

            case Sensor.TYPE_MAGNETIC_FIELD:
                System.arraycopy(sensorEvent.values, 0,
                        magnetometerReading, 0, magnetometerReading.length);
                mLastMagnetometerSet = true;
                break;
            
        }

        if(mLastAccelerometerSet && mLastMagnetometerSet){
            updateOrientationAngles();
        }

    }

    private void updateOrientationAngles() {
        SensorManager.getRotationMatrix(rotationMatrix, null,
                accelerometerReading, magnetometerReading);

        SensorManager.getOrientation(rotationMatrix, orientationAngles );

             float azimuthRadians = orientationAngles[0];

             float azimuthDegrees = (float)(Math.toDegrees(azimuthRadians)+360) % 360;

             txtOrientarion.setText("Orientacion: " + azimuthDegrees );

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}