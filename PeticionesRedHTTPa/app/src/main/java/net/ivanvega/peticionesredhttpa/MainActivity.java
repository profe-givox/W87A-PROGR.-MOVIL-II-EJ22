package net.ivanvega.peticionesredhttpa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    TextView txt ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = findViewById(R.id.txt);

        findViewById(R.id.btnStringReq).setOnClickListener(v->stringRequest());
        findViewById(R.id.btnJsonReq).setOnClickListener(v->jsonRequest());


    }

    private void stringRequest() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://www.google.com";

        // Request a string response from the provided URL.
        StringRequest stringRequest =
                new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        txt.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txt.setText("That didn't work!" + error.toString());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    private void jsonRequest() {
        /*
        Gson gson = new Gson();

        Type listType =
                new TypeToken<List<Album>>() {}.getType();

        List<Album> list = gson.fromJson(response.toString(),
                listType);

    */}
}