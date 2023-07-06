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
import com.example.loginregister.ui.dashboard.DashboardFragment;
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

public class scannerViewbox extends AppCompatActivity implements ZXingScannerView.ResultHandler
{
    ZXingScannerView scannerViewbox;
    public static String qrcode;
    public static String idsb="";
    public static String op;
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
    public static String machine, qrmachine;
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
        scannerViewbox= new ZXingScannerView(this);
        setContentView(scannerViewbox);

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        scannerViewbox.startCamera();
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
        qrmachine = testscan.machine;
        if(!qrcode.equals(qrmachine)) {

            queue =  Volley.newRequestQueue(getApplicationContext());
            String url2 = controller.ip2+"/productionLine/bymachine";
            JSONObject postObject = new JSONObject();

            try {
                //historyObject.put("id","1");
                postObject.put("machine_ref",qrmachine);
                postObject.put("digitex", qrcode);
                postObject.put("prod_line", GalleryFragment.groupe.getSelectedItem().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Log.e("LoginActivityJsonObject",""+postObject);
            JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest
                    (Request.Method.POST, url2, postObject, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            String result = response.optString("message");
                            if (result.equals("Mise a jour reussie")) {
                                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error

                        }
                    });

            queue.add(jsonObjectRequest2);

//            queue =  Volley.newRequestQueue(getApplicationContext());
//            String url = controller.ip2+"/productionLine/bymachine/?machine_ref="+qrmachine;
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            if(!response.toString().equals("{}")) {
//                                machine = qrmachine;
//                            } else {
//                                machine="";
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            // TODO: Handle error
//
//                        }
//                    });
//
//            queue.add(jsonObjectRequest);
//            if (!machine.equals("")) {
//                //update line, box
//                String url2 = controller.ip2+"/productionLine/bydigitex";
//                JSONObject postObject = new JSONObject();
//
//                try {
//                    //historyObject.put("id","1");
//                    postObject.put("machine_ref",qrmachine);
//                    postObject.put("digitex", qrcode);
//                    postObject.put("prod_line", GalleryFragment.groupe.getSelectedItem().toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                Log.e("LoginActivityJsonObject",""+postObject);
//                JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest
//                        (Request.Method.PATCH, url2, postObject, new Response.Listener<JSONObject>() {
//
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                String result = response.optString("message");
//                                if (result.equals("Mise a jour reussie")) {
//                                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }, new Response.ErrorListener() {
//
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                // TODO: Handle error
//
//                            }
//                        });
//
//                queue.add(jsonObjectRequest2);
//
//            } else {
//                //insert new line
//                String url3 = controller.ip2+"/productionLine/bydigitex";
//                JSONObject postObject = new JSONObject();
//
//                try {
//                    //historyObject.put("id","1");
//                    postObject.put("machine_ref",qrmachine);
//                    postObject.put("digitex", qrcode);
//                    postObject.put("prod_line", GalleryFragment.groupe.getSelectedItem().toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//                Log.e("LoginActivityJsonObject",""+postObject);
//                JsonObjectRequest jsonObjectRequest3 = new JsonObjectRequest
//                        (Request.Method.POST, url3, postObject, new Response.Listener<JSONObject>() {
//
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                String result = response.optString("message");
//                                if (result.equals("Mise a jour reussie")) {
//                                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }, new Response.ErrorListener() {
//
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                // TODO: Handle error
//
//                            }
//                        });
//
//                queue.add(jsonObjectRequest3);
//
//            }

//            String url = controller.ip2+"/productionLine/bydigitex";
//            JSONObject postObject = new JSONObject();
//
//            try {
//                //historyObject.put("id","1");
//                postObject.put("machine_ref",qrmachine);
//                postObject.put("digitex", qrcode);
//                postObject.put("prod_line", GalleryFragment.groupe.getSelectedItem().toString());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            Log.e("LoginActivityJsonObject",""+postObject);
//            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
//                    (Request.Method.PATCH, url, postObject, new Response.Listener<JSONObject>() {
//
//                        @Override
//                        public void onResponse(JSONObject response) {
//                            String result = response.optString("message");
//                            if (result.equals("Mise a jour reussie")) {
//                                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            // TODO: Handle error
//
//                        }
//                    });
//
//            queue.add(jsonObjectRequest);


            /*Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    String[] field = new String[2];
                    field[0] = "qrcode";
                    field[1] = "qrmachine";
                    //Creating array for data
                    String[] data = new String[2];
                    data[0] = qrcode;
                    data[1] = qrmachine;
                    PutData putData = new PutData(controller.ip+"/LoginRegister2/qrcodeboxmachine.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            //End ProgressBar (Set visibility to GONE)
                            if(result.equals("Affectation reussie")){
                                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });*/
        }
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerViewbox.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerViewbox.setResultHandler(this);
        scannerViewbox.startCamera();
    }
}