package com.example.loginregister;

import android.Manifest;
import android.content.Intent;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class scannerViewOff extends AppCompatActivity implements ZXingScannerView.ResultHandler
{
    ZXingScannerView scannerViewOff;
    public static String qrcode;
    public static String idsb="";
    public static String op;
    public static String digitex;
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
    public static String result="";
    public static String result0, result00;
    public static BarData barData2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerViewOff= new ZXingScannerView(this);
        setContentView(scannerViewOff);

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        scannerViewOff.startCamera();
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

        if(qrcode.equals(ServiceNotification.digitex)) {
            String url = controller.ip2+"/monitor/call";
            JSONObject postObject = new JSONObject();
            RequestQueue queue =  Volley.newRequestQueue(getApplicationContext());

            try {
                //historyObject.put("id","1");
                postObject.put("monitor_arrival_time",HomeFragment.time);
                postObject.put("full_name", ServiceNotification.full_name);
                postObject.put("id",ServiceNotification.id);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.e("LoginActivityJsonObject",""+postObject);
            JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.POST, url,postObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("LoginActivity","OnResponse: "+response);
                            String result = response.optString("message");
                            if (result.equals("Mise a jour reussie")) {
                                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                ServiceNotification.msg="";
                            }
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

            /*Handler handler4 = new Handler(Looper.getMainLooper());
            handler4.post(new Runnable() {
                @Override
                public void run() {
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field = new String[3];
                    field[0] = "Instructor_arrival_time";
                    field[1] = "opname";
                    field[2] = "id";
                    //Creating array for data
                    String[] data = new String[3];
                    data[0] = HomeFragment.time;
                    data[1] = ServiceNotification.msg;
                    data[2] = HomeFragment.id;
                    PutData putData = new PutData(controller.ip+"/LoginRegister2/timearrivalinst.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            result0 = putData.getResult().toString();
                            if(result0.equals("Mise a jour reussie")){
                                //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                }
            });

            Handler handler5 = new Handler(Looper.getMainLooper());
            handler5.post(new Runnable() {
                @Override
                public void run() {

                    String[] field1 = new String[3];
                    field1[0] = "state";
                    field1[1] = "opname";
                    field1[2] = "id";
                    //Creating array for data
                    String[] data1 = new String[3];
                    data1[0] = "false";
                    data1[1] = ServiceNotification.msg;
                    data1[2] = HomeFragment.id;
                    PutData putData1 = new PutData(controller.ip+"/LoginRegister2/stateonoff.php", "POST", field1, data1);
                    if (putData1.startPut()) {
                        if (putData1.onComplete()) {
                            String result1 = putData1.getResult();
                            //End ProgressBar (Set visibility to GONE)
                            if(result1.equals("Mise a jour reussie")){
                                Toast.makeText(getApplicationContext(),result1,Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                }
            });
*/

        }
        else {
            Toast.makeText(getApplicationContext(), "Box incorrecte", Toast.LENGTH_SHORT).show();
        }
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerViewOff.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerViewOff.setResultHandler(this);
        scannerViewOff.startCamera();
    }
}