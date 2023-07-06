package com.example.loginregister;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

public class scannerViewSB extends AppCompatActivity implements ZXingScannerView.ResultHandler
{
    ZXingScannerView scannerView;
    RequestQueue requestQueue, requestQueue2;
    public static String qrcode;
    public static String idsb="";
    public static String op;
    public static String firstname;
    public static int nb;
    public static String operatrice;
    public static String start="";
    public static String rend="";
    public static String numpacket="";
    public static String numop="";
    public static String end="";
    public static String estimate="";
    public static String statis="";
    public static String[] resultArrayrend;
    public static String[] resultArraypacket;
    public static String[] resultArraynumop;
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
    public static String machineid;
    public static BarData barData2;
    String url2 = controller.ip2+"/operation/bymodel";

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
            JSONObject postObject = new JSONObject();
            RequestQueue queue =  Volley.newRequestQueue(getApplicationContext());

            try {
                postObject.put("digitex",qrcode);
                postObject.put("operation_code",DashboardFragment.resultArray[0]);
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
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("OnError", String.valueOf(error.getMessage()));
                }
            });

                /*final String digitex = qrcode.trim();
                final String lignegamme = DashboardFragment.resultArray[0].trim();
                final String modele = DashboardFragment.selectedCountry.trim();*/

            queue.add(objRequest);
            DashboardFragment.rend_op.setText("");
            DashboardFragment.alertDialog1.cancel();

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