package com.example.clientecontentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.UserDictionary;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Cursor c = getContentResolver().query(UserDictionary.Words.CONTENT_URI,
                new String[] {UserDictionary.Words.WORD,
                        UserDictionary.Words.LOCALE},
                null,null,null
                );
        if(c!=null) {
            while (c.moveToNext()) {
                Log.d("DICCIONARIOUSARUI",
                        c.getString(0) + " - " + c.getString(1)
                );
            }
        }
        Cursor cursor = getContentResolver().query(
                UsuarioContrato.CONTENT_URI,
                UsuarioContrato.COLUMNS_NAME,
                null,null,null
        );

        if(cursor!=null) {


            while (cursor.moveToNext()) {
                Log.d("USUARIOCONTENTPROVIDER",
                        cursor.getInt(0) + " - " + cursor.getString(1)
                );
            }
        }else{
            Log.d("USUARIOCONTENTPROVIDER",
                    "NO DEVUELVE"
            );
        }

    }
}