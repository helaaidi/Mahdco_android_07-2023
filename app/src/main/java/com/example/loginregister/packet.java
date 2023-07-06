package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginregister.ui.home.HomeFragment;
import com.example.loginregister.util.MPUtil;
import com.github.mikephil.charting.data.BarData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class packet extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public static TextView packnum;
    public static TextView tagrfid;
    public static String result, selectedGroup, selectedModel, selectedOf;
    public static String tagid="";
    public static String ofs;
    public static Spinner of;
    public static Spinner groupe;
    public static TextView color;
    public static Spinner model;
    public static TextView size;
    public static TextView client;
    public static TextView qt;
    RequestQueue requestQueue;
    ArrayAdapter<String> groupAdapter;
    ArrayAdapter<String> modeleAdapter;
    ArrayAdapter<String> ofAdapter;
    ArrayList<String> groupList = new ArrayList<>();
    ArrayList<String> modeleList = new ArrayList<>();
    ArrayList<String> ofList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packet);

        if (ServiceUpdateHere.ServiceIsRun) {
            //register the services to run in background
            Intent intent = new Intent(this, ServiceUpdateHere.class);
            // start the services
            startService(intent);
        }

        if (ServiceTag.ServiceIsRun) {
            //register the services to run in background
            Intent intent = new Intent(this, ServiceTag.class);
            // start the services
            startService(intent);
        }

        packnum=(TextView) findViewById(R.id.packnum);
        tagrfid=(TextView) findViewById(R.id.tagrfid);
        of=(Spinner) findViewById(R.id.of);
        groupe=(Spinner) findViewById(R.id.groupe);
        color=(TextView) findViewById(R.id.color);
        model=(Spinner) findViewById(R.id.model);
        size=(TextView) findViewById(R.id.size);
        //client=(TextView) findViewById(R.id.client);
        qt=(TextView) findViewById(R.id.qt);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        //spinnerCity = view.findViewById(R.id.spinnerCity);
        String url = controller.ip2+"/import/prod_lines";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    // creating a new json object and
                    // getting each object from our json array.
                    try {
                        // we are getting each json object.
                        JSONObject responseObj = response.getJSONObject(i);

                        String countryName = responseObj.optString("prod_line");
                        if(!groupList.contains(countryName))
                        {
                            groupList.add(countryName);
                        }
                        groupAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.rowof, groupList);
                        groupAdapter.setDropDownViewResource(R.layout.rowof);
                        groupe.setAdapter(groupAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonArrayRequest);
        groupe.setOnItemSelectedListener(this);
        model.setOnItemSelectedListener(this);
        of.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.groupe){
            modeleList.clear();
            selectedGroup = adapterView.getSelectedItem().toString();
            String url2 = controller.ip2+"/import/modelByprod_line/?prod_line="+selectedGroup;
            requestQueue = Volley.newRequestQueue(getApplicationContext());

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url2, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    for (int i = 0; i < response.length(); i++) {
                        // creating a new json object and
                        // getting each object from our json array.
                        try {
                            // we are getting each json object.
                            JSONObject responseObj = response.getJSONObject(i);

                            String cityName = responseObj.optString("model");

                            if(!modeleList.contains(cityName))
                            {
                                modeleList.add(cityName);

                            }
                            modeleAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.rowof, modeleList);
                            modeleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //spinnerCity.setAdapter(cityAdapter);
                            model.setAdapter(modeleAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            requestQueue.add(jsonArrayRequest);
        }
        if(adapterView.getId() == R.id.model){
            ofList.clear();
            selectedModel = adapterView.getSelectedItem().toString();
            String url3 = controller.ip2+"/import/of_num/"+selectedModel+"/";
            requestQueue = Volley.newRequestQueue(getApplicationContext());

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url3, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    for (int i = 0; i < response.length(); i++) {
                        // creating a new json object and
                        // getting each object from our json array.
                        try {
                            // we are getting each json object.
                            JSONObject responseObj = response.getJSONObject(i);

                            String cityName = responseObj.optString("of_num");
                            ofs += responseObj.optString("of_num")+",";

                            if(!ofList.contains(cityName))
                            {
                                ofList.add(cityName);

                            }
                            ofAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.rowof, ofList);
                            ofAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            //spinnerCity.setAdapter(cityAdapter);
                            of.setAdapter(ofAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            requestQueue.add(jsonArrayRequest);
        }

        if(ofs != null) {

            requestQueue = Volley.newRequestQueue(getApplicationContext());
            String url2 = controller.ip2 + "/packet/of_num";
            JSONObject postObject = new JSONObject();

            try {
                postObject.put("of_num", ofs);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.e("LoginActivityJsonObject", "" + postObject);
            JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest
                    (Request.Method.POST, url2, postObject, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            if(!response.toString().equals("{}")) {
                                Log.e("LoginActivity", "OnResponse: " + response);
                                Toast.makeText(getApplicationContext(), String.valueOf(response), Toast.LENGTH_LONG).show();
                            }
                            try {
                                Thread.sleep(2000);
                            } catch (Exception ex) {
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error

                        }
                    });

            requestQueue.add(jsonObjectRequest2);

        }

        if(adapterView.getId() == R.id.of){
            selectedOf = adapterView.getSelectedItem().toString();
            String url4 = controller.ip2+"/packet/here/?of_num="+selectedOf;
            requestQueue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url4, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            String N_pipelette = response.optString("pack_num");
                            String Coloris = response.optString("color");
                            String Taille = response.optString("size");
                            String Qte_a_monter = response.optString("quantity");
                            packet.packnum.setText(N_pipelette);
                            packet.color.setText(Coloris);
                            packet.size.setText(Taille);
                            packet.qt.setText(Qte_a_monter);
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error

                        }
                    });

            requestQueue.add(jsonObjectRequest);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}