package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONObject;

public class research extends AppCompatActivity {

    public static TextView tagrfid, packnum, ordref, color, size, qt, model, operation;
    public static String result;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research);

        if (Serviceresearchtag.ServiceIsRun) {
            //register the services to run in background
            Intent intent = new Intent(this, Serviceresearchtag.class);
            // start the services
            startService(intent);
        }

        tagrfid = (TextView) findViewById(R.id.tagrfid);
        packnum = (TextView) findViewById(R.id.packnum);
        ordref = (TextView) findViewById(R.id.ordref);
        color = (TextView) findViewById(R.id.color);
        size = (TextView) findViewById(R.id.size);
        qt = (TextView) findViewById(R.id.qt);
        model = (TextView) findViewById(R.id.model);
        operation = (TextView) findViewById(R.id.operation);
    }


    public void onBackPressed() {
        queue =  Volley.newRequestQueue(getApplicationContext());
        String url5 = controller.ip2+"/packet/here";
        JsonObjectRequest jsonObjectRequest4 = new JsonObjectRequest
                (Request.Method.DELETE, url5, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });

        queue.add(jsonObjectRequest4);
        finish();
    }
}