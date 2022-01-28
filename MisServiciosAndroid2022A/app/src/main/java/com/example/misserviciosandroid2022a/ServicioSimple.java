

package com.example.misserviciosandroid2022a;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;
import androidx.recyclerview.widget.AsyncDifferConfig;

import java.security.Provider;
import java.util.List;
import java.util.Map;

public class ServicioSimple extends Service {
    private Thread hilo;


    AsyncTask<Integer, Integer, String> miTarea = new AsyncTask<Integer, Integer, String>() {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... integers) {

            for(int i=0; i< integers[0]; i++){
                Log.d("ServicioSimpleAsynTask", "Iteracion: " + 1);
                publishProgress(i);
            }

            return "Finalizada tarea async";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d("ServicioSimpleAsynTask", "Progreso: " + values[0]);
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);

            Log.d("ServicioSimpleAsynTask", "Iteracion: " + s);
        }
    } ;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //Aqui debe iniciarse un subpriceso y en el llevar acabo la tarea prolongada
        // o de bloque


        //Los servicios iniciados deben ser gestionados de manera manual o
        //pueden ser autogestionados

        //paa autogestionar la destruccion del servicio se puede invocar el metodo stopSelf

        miTarea.execute(999999);

         hilo = new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Log.d("ServicioSimpleAsynTask", "Iteracion: antes") ;
                    Thread.sleep(30000);
                    Log.d("ServicioSimpleAsynTask", "Iteracion: despues");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                /*for (int i = 0; i < 99999; i++) {
                    Log.d("ServicioSimpleAsynTask", "Iteracion: " + i);

                }*/

            }
        });

         hilo.start();
        //stopSelf();


        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        Log.d("ServicioSimpleAsynTask", "destruido");

        super.onDestroy();

    }
}