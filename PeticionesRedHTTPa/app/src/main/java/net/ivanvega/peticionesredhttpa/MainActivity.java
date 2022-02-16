package net.ivanvega.peticionesredhttpa;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView txt ;
    RequestQueue queue;
    ImageView miImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = findViewById(R.id.txt);
        miImg = findViewById(R.id.imgVoll);
        findViewById(R.id.btnStringReq).setOnClickListener(v->stringRequest());
        findViewById(R.id.btnJsonReq).setOnClickListener(v->jsonRequest());
        findViewById(R.id.btnImgReq).setOnClickListener(v->requestImageMethod());

        queue = Volley.newRequestQueue(this);



    }

    private void stringRequest() {
        // Instantiate the RequestQueue.

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
        JsonRequest jsonRequest = new JsonArrayRequest(
                Request.Method.GET,
                "https://jsonplaceholder.typicode.com/albums",
                null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Gson gson = new Gson();
                        Type lsAlbumType = new TypeToken<List<Album>>(){}.getType();

                        List<Album> lsAlbum =  gson.fromJson(response.toString(), lsAlbumType);

                        for (Album item:  lsAlbum){
                            Log.d("JSONREQUEST", "Id: " + item.id + " title: " +
                                    item.getTitle());
                        }

                        for (int i=0; i< response.length(); i++){
                            try {
                                JSONObject e = response.getJSONObject(i);
                                Log.d("JSONREQUESTMAPA", "Id: " + e.getInt("id") + " title: " +
                                        e.getString("title") );
                            } catch (JSONException jsonException) {
                                jsonException.printStackTrace();
                            }
                        }
                    }
        },
                error -> {
                    error.printStackTrace();
                }
        );

        queue.add(jsonRequest);
        /*
        Gson gson = new Gson();

        Type listType =
                new TypeToken<List<Album>>() {}.getType();

        List<Album> list = gson.fromJson(response.toString(),
                listType);

    */}

    void requestImageMethod(){
        ImageRequest imgReq = new
                ImageRequest("https://via.placeholder.com/150/771796",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        miImg.setImageBitmap(response);
                    }

                    },200,200,
                ImageView.ScaleType.CENTER,
                Bitmap.Config.ALPHA_8,
                error -> {
                    error.printStackTrace();
                }
                );
        MySingleton.getInstance(this).addToRequestQueue(imgReq);
    }

}