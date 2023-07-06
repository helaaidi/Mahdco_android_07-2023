package com.example.loginregister;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginregister.ui.home.HomeFragment;
import com.example.loginregister.util.MPUtil;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class scannerView extends AppCompatActivity implements ZXingScannerView.ResultHandler
{
    ZXingScannerView scannerView;

    public static String qrcode,result1, result2, performance, cur_time;
    public static String idsb="";
    public static String op;
    public static String full_name;
    public static int nb;
    public static String operatrice;
    public static String start="";
    public static String rend="";
    public static String numpacket="";
    public static String numop="";
    public static String end="";
    public static String estimate="";
    public static String statis="";
    public static String[] resultArrayrend, resultArray1, resultArray2, resultArraychart;
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
    public static BarData barData2;
    public static String perfor="";
    public static String time="";
    public static String[] axisData;
    public static String[] arrayperfor;
    public static int[] yAxisData;
    RequestQueue requestQueue;

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

            requestQueue = Volley.newRequestQueue(getApplicationContext());
            String url = controller.ip2+"/operator/performanceHour/"+qrcode;
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    System.out.println(response.toString());
//                    if(!response.toString().equals("{}")) {
                        HomeFragment.lineChartView.setVisibility(View.VISIBLE);
                        nb = response.length();
                        for (int i = 0; i < response.length(); i++) {
                            // creating a new json object and
                            // getting each object from our json array.
                            try {
                                // we are getting each json object.
                                JSONObject responseObj = response.getJSONObject(i);

                                performance = responseObj.optString("performance");
                                cur_time = responseObj.optString("cur_time");
                                full_name = responseObj.optString("full_name");

                                time += cur_time + " ";
                                axisData = time.split(" ");

                                perfor += performance + " ";
                                arrayperfor = perfor.split(" ");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        int size = arrayperfor.length;
                        yAxisData = new int[size];
                        for (int j = 0; j < size; j++) {
                            yAxisData[j] = Math.round(Float.parseFloat(arrayperfor[j]) * 100);
                        }

                        List yAxisValues = new ArrayList();
                        List axisValues = new ArrayList();

                        Line line = new Line(yAxisValues).setColor(Color.parseColor("#0384F4"));

                        for (int h = 0; h < axisData.length; h++) {
                            axisValues.add(h, new AxisValue(h).setLabel(axisData[h]));
                        }

                        for (int l = 0; l < yAxisData.length; l++) {
                            yAxisValues.add(new PointValue(l, yAxisData[l]));
                        }

                        List lines = new ArrayList();
                        lines.add(line);

                        LineChartData data = new LineChartData();
                        data.setLines(lines);

                        Axis axis = new Axis();
                        axis.setValues(axisValues);
                        axis.setTextSize(16);
                        axis.setTextColor(Color.parseColor("#03A9F4"));
                        data.setAxisXBottom(axis);

                        Axis yAxis = new Axis();
                        yAxis.setName("Rendement");
                        yAxis.setTextColor(Color.parseColor("#03A9F4"));
                        yAxis.setTextSize(16);
                        data.setAxisYLeft(yAxis);

                        HomeFragment.lineChartView.setLineChartData(data);
                        Viewport viewport = new Viewport(HomeFragment.lineChartView.getMaximumViewport());
                        viewport.top = 110;
                        HomeFragment.lineChartView.setMaximumViewport(viewport);
                        HomeFragment.lineChartView.setCurrentViewport(viewport);
                        time = "";
                        perfor = "";

                        HomeFragment.scantext.setText(qrcode);
                        HomeFragment.operator.setText(full_name);
                        HomeFragment.nbrp.setText(String.valueOf(nb));

                        HomeFragment.btnoff.setVisibility(View.VISIBLE);
                        HomeFragment.btnon.setVisibility(View.VISIBLE);
                        HomeFragment.tableLayout.setVisibility(View.VISIBLE);
//                    }else {
//                        HomeFragment.btnon.setVisibility(View.VISIBLE);
//                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            requestQueue.add(jsonArrayRequest);


        }
        else {
            Toast.makeText(getApplicationContext(), "Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
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