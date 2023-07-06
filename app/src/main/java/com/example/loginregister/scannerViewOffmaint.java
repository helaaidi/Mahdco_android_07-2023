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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginregister.ui.gallery.GalleryFragment;
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

public class scannerViewOffmaint extends AppCompatActivity implements ZXingScannerView.ResultHandler
{
    ZXingScannerView scannerViewOffmaint;
    public static String qrcode;
    public static String operatrice;
    public static String start="";
    public static String rend="";
    public static String end="";
    public static double statistics;
    public static String pack;
    public static String numope;
    public static String numoperation;
    public static TextView packet;
    public static TextView efficiency;
    public static List<Float> dataList;
    public static List<Integer> dataList2;
    public static List<Integer> dataList3;
    public static String result="";
    public static String machineid, machine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerViewOffmaint= new ZXingScannerView(this);
        setContentView(scannerViewOffmaint);

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        scannerViewOffmaint.startCamera();
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

        if(qrcode.equals(ServiceNotificationmaint.digitex)) {

            String url = controller.ip2+"/monitor/callMaintainer";
            JSONObject postObject = new JSONObject();
            RequestQueue queue =  Volley.newRequestQueue(getApplicationContext());

            try {
                //historyObject.put("id","1");
                postObject.put("maintainer_arrival_time",GalleryFragment.time);
                postObject.put("digitex", ServiceNotificationmaint.digitex);
                postObject.put("full_name", Login.fullname);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.e("LoginActivityJsonObject",""+postObject);
            JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.PATCH, url,postObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("LoginActivity","OnResponse: "+response);
                            String result = response.optString("message");
                            if (result.equals("Mise a jour reussie")) {
                                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                ServiceNotificationmaint.msg="";
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

            /*Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {

                    String[] field1 = new String[2];
                    field1[0] = "sb";
                    field1[1] = "Maintainer_arrival_time";
                    //Creating array for data
                    String[] data1 = new String[2];
                    data1[0] = GalleryFragment.sb;
                    data1[1] = GalleryFragment.time;
                    PutData putData1 = new PutData(controller.ip+"/LoginRegister2/stateoffmaint.php", "POST", field1, data1);
                    if (putData1.startPut()) {
                        if (putData1.onComplete()) {
                            String result = putData1.getResult();
                            //End ProgressBar (Set visibility to GONE)
                            if(result.equals("Mise a jour reussie")){
                                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });*/

            /*Handler handler1 = new Handler(Looper.getMainLooper());
            handler1.post(new Runnable() {
                @Override
                public void run() {

                    String[] field = new String[1];
                    field[0] = "sb";
                    //Creating array for data
                    String[] data = new String[1];
                    data[0] = GalleryFragment.sb;
                    PutData putData1 = new PutData(controller.ip+"/LoginRegister2/selectmachineidinter.php", "POST", field, data);
                    if (putData1.startPut()) {
                        if (putData1.onComplete()) {
                            machineid = putData1.getResult();
                        }
                    }
                }
            });

            Handler handler2 = new Handler(Looper.getMainLooper());
            handler2.post(new Runnable() {
                @Override
                public void run() {

                    String[] field = new String[1];
                    field[0] = "sb";
                    //Creating array for data
                    String[] data = new String[1];
                    data[0] = GalleryFragment.sb;
                    PutData putData1 = new PutData(controller.ip+"/LoginRegister2/selectmachineinter.php", "POST", field, data);
                    if (putData1.startPut()) {
                        if (putData1.onComplete()) {
                            machine = putData1.getResult();
                        }
                    }
                }
            });

            Handler handler3 = new Handler(Looper.getMainLooper());
            handler3.post(new Runnable() {
                @Override
                public void run() {

                    String[] field = new String[3];
                    field[0] = "Fullname";
                    field[1] = "Machine";
                    field[2] = "Machine_id";
                    //Creating array for data
                    String[] data = new String[3];
                    data[0] = Login.resultArray[2];
                    data[1] = machine;
                    data[2] = machineid;
                    PutData putData1 = new PutData(controller.ip+"/LoginRegister2/insertintervention.php", "POST", field, data);
                    if (putData1.startPut()) {
                        if (putData1.onComplete()) {
                            String result1 = putData1.getResult();
                            //End ProgressBar (Set visibility to GONE)
                            if(result.equals("Mise a jour reussie")){
                                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });
*/


        }
        else {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }


        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerViewOffmaint.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerViewOffmaint.setResultHandler(this);
        scannerViewOffmaint.startCamera();
    }
}