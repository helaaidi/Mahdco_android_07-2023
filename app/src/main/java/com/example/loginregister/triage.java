package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class triage extends AppCompatActivity {

    Button btnpack, btnsrchpack, btnonepack, btnrh;
    public static String result;
    public static String[] resultArray;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triage);

        btnpack=(Button) findViewById(R.id.btnpack);
        btnsrchpack=(Button) findViewById(R.id.btnsrchpack);
        btnonepack=(Button) findViewById(R.id.btnonepack);
        btnrh=(Button) findViewById(R.id.btnrh);

        btnpack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), packet.class));
            }
        });

        btnonepack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), onePacket.class));
            }
        });

        btnsrchpack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), research.class));

            }
        });

        btnrh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), RH.class));

            }
        });

    }

    /*public void closeApp (View view) {

        startService( new Intent( this, NotificationService.class )) ;
        android.os.Process.killProcess(android.os.Process.myPid());

    }*/
}