package com.example.loginregister;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginregister.ui.dashboard.DashboardFragment;
import com.example.loginregister.ui.home.HomeFragment;
import com.example.loginregister.util.MPUtil;
import com.github.mikephil.charting.data.BarData;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class scannerViewSBdouble extends AppCompatActivity implements ZXingScannerView.ResultHandler
{
    ZXingScannerView scannerView;

    public static String qrcode;
    public static String machineid, digitex, machine;
    public static int nb;
    public static String operatrice;
    public static String start="";
    public static String rend="";
    public static String numpacket="";
    public static String numop="";
    public static String end="";
    public static String estimate="";
    public static String[] resultArray;
    public static double statistics;
    public static java.util.Date date1 = null;
    public static String pack;
    public static String numope;
    public static String numoperation;
    AlertDialog alertDialog;
    public static TextView packet;
    public static TextView efficiency;
    public static List<Float> dataList;
    public static List<Integer> dataList2;
    public static List<Integer> dataList3;
    public static String result="";
    public static BarData barData2;
    RequestQueue queue;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView= new ZXingScannerView(this);
        setContentView(scannerView);

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void handleResult(Result rawResult) {
        qrcode = String.valueOf(rawResult.getText());
        if(!qrcode.equals("")) {

            queue =  Volley.newRequestQueue(getApplicationContext());
            String url3 = controller.ip2+"/productionLine/bydigitex/"+qrcode;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url3, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            if(!response.toString().equals("{}")) {
                                machineid = response.optString("machine_ref");
                            } else {
                                machineid="";
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error

                        }
                    });

            queue.add(jsonObjectRequest);

            // on below line we are calling a string
            // request method to post the data to our API
            // in this we are calling a post method.

            String url = controller.ip2+"/operation/MultiMachine/?model="+DashboardFragment.selectedCountry+"&operation_code="+DashboardFragment.resultArray2[0];

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    for (int i = 0; i < response.length(); i++) {
                        // creating a new json object and
                        // getting each object from our json array.
                        try {
                            // we are getting each json object.
                            JSONObject responseObj = response.getJSONObject(i);
                            digitex = responseObj.optString("digitex");
                            machine = responseObj.optString("machine_ref");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    if (!digitex.equals("")) {

                        System.out.println(DashboardFragment.rend);

                        String url4 = controller.ip2+"/operation/MultiMachine";

                        JSONObject postObject = new JSONObject();

                        try {
                            //historyObject.put("id","1");
                            postObject.put("digitex",digitex+"/"+qrcode);
                            postObject.put("digitex_prod",qrcode);
                            postObject.put("operation_code",DashboardFragment.resultArray2[0]);
                            postObject.put("model",DashboardFragment.selectedCountry);
                            postObject.put("machine_ref",machine+"/"+machineid);
                            postObject.put("potential",DashboardFragment.rend);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.e("LoginActivityJsonObject",""+postObject);
                        JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.POST, url4,postObject,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.e("LoginActivity","OnResponse: "+response);
                                        Toast.makeText(getApplicationContext(), String.valueOf(response), Toast.LENGTH_LONG).show();
                                        DashboardFragment.alertDialog1.cancel();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("OnError", String.valueOf(error.getMessage()));
                            }
                        });


                        queue.add(objRequest);

                    }  else {

                        String url2 = controller.ip2+"/operation/bymodel";

                        JSONObject postObject = new JSONObject();

                        try {
                            //historyObject.put("id","1");
                            postObject.put("digitex",qrcode);
                            postObject.put("operation_code",DashboardFragment.resultArray2[0]);
                            postObject.put("model",DashboardFragment.selectedCountry);
                            postObject.put("potential",DashboardFragment.rend);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.e("LoginActivityJsonObject",""+postObject);
                        JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.POST, url2,postObject,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.e("LoginActivity","OnResponse: "+response);
                                        Toast.makeText(getApplicationContext(), String.valueOf(response), Toast.LENGTH_LONG).show();
                                        DashboardFragment.alertDialog1.cancel();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("OnError", String.valueOf(error.getMessage()));
                            }
                        });


                        queue.add(objRequest);

                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            queue.add(jsonArrayRequest);

        }
        else {
            Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
        }
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }
}