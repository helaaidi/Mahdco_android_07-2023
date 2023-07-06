package com.example.loginregister;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class scannerViewUpdate extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView scannerViewUpdate;
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
    public static String numope;
    public static String numoperation;
    AlertDialog alertDialog;
    public static TextView packet;
    public static TextView efficiency;
    public static List<Integer> dataList2;
    public static List<Integer> dataList3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerViewUpdate= new ZXingScannerView(this);
        setContentView(scannerViewUpdate);

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        scannerViewUpdate.startCamera();
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
        String pack = HomeFragment.pack;
        if(!qrcode.equals("")) {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    String[] field = new String[1];
                    field[0] = "qrcode";
                    //Creating array for data
                    String[] data = new String[1];
                    data[0] = qrcode;
                    PutData putData = new PutData(controller.ip+"/LoginRegister2/qrcode.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult().toString();
                            String[] resultArray = result.split("/");
                            for (int i = 0; i < resultArray.length; i++) {
                                String[] resultArray2 = resultArray[i].split(",");
                                op = resultArray2[1];
                            }
                        }
                    }
                    //Starting Write and Read data with URL
                    //Creating array for parameters
                    String[] field1 = new String[3];
                    field1[0] = "opname";
                    field1[1] = "qrcode";
                    field1[2] = "pack";
                    //Creating array for data
                    String[] data1 = new String[3];
                    data1[0] = op;
                    data1[1] = qrcode;
                    data1[2] = pack;
                    PutData putData1 = new PutData(controller.ip+"/LoginRegister2/updatepacket.php", "POST", field1, data1);
                    if (putData1.startPut()) {
                        if (putData1.onComplete()) {
                            String result1 = putData1.getResult();
                            //End ProgressBar (Set visibility to GONE)
                            if(result1.equals("Mise a jour reussie")){
                                Toast.makeText(getApplicationContext(),result1,Toast.LENGTH_SHORT).show();
                                //HomeFragment.alertDialog1.dismiss();

                            }
                        }
                    }

                    String[] field2 = new String[1];
                    field2[0] = "qrcode";
                    //Creating array for data
                    String[] data2 = new String[1];
                    data2[0] = qrcode;
                    PutData putData2 = new PutData(controller.ip+"/LoginRegister2/qrcode.php", "POST", field2, data2);
                    if (putData2.startPut()) {
                        if (putData2.onComplete()) {
                            String result2 = putData2.getResult().toString();
                            String[] resultArray = result2.split("/");
                            nb = resultArray.length;
                            for (int i = 0; i < resultArray.length; i++) {
                                String[] resultArray2 = resultArray[i].split(",");
                                idsb = resultArray2[0];
                                op = resultArray2[1];
                                numope = resultArray2[7];
                                /*idsb += resultArray2[5] + " ";
                                String[] resultArray3 = idsb.split(" ");
                                nb = resultArray3.length;*/

                                rend += resultArray2[6] + " ";
                                resultArrayrend = null;
                                resultArrayrend = rend.split(" ");


                                numpacket += resultArray2[5] + " ";
                                resultArraypacket = numpacket.split(" ");

                                numop += resultArray2[7] + " ";
                                resultArraynumop = numop.split(" ");

                                start += resultArray2[2] + " ";
                                String[] resultArray4 = start.split(" ");

                                end += resultArray2[3] + " ";
                                String[] resultArray5 = end.split(" ");

                                estimate += resultArray2[2] + " ";
                                String[] resultArray6 = estimate.split(" ");
                            }

                            rend="";
                            numpacket="";
                            numop="";
                            start="";
                            end="";
                            estimate="";

                            HomeFragment.scantext.setText(idsb);
                            HomeFragment.operator.setText(op);
                            HomeFragment.nbrp.setText(String.valueOf(nb));
                            HomeFragment.btnoff.setVisibility(View.VISIBLE);
                            //HomeFragment.btnon.setVisibility(View.VISIBLE);
                            HomeFragment.chart.setVisibility(View.VISIBLE);
                            HomeFragment.xlabel_id.setVisibility(View.VISIBLE);
                            HomeFragment.ylabel_id.setVisibility(View.VISIBLE);

                            List<Float> dataList = new ArrayList<>();
                            dataList2 = new ArrayList<>();
                            dataList3 = new ArrayList<>();

                            for (int j = 0; j < resultArrayrend.length; j++) {
                                dataList.add(Float.parseFloat(resultArrayrend[j]));
                                dataList2.add(Integer.parseInt(resultArraypacket[j]));
                                dataList3.add(Integer.parseInt(resultArraynumop[j]));

                            }

                            BarData barData2 = new BarData(MPUtil.getXAxisValues(resultArrayrend.length), MPUtil.getDataSet(scannerViewUpdate.this, dataList));
                            MPUtil.drawChart(scannerViewUpdate.this, HomeFragment.chart, barData2);

                        }
                    }

                }
            });
        }
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerViewUpdate.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerViewUpdate.setResultHandler(this);
        scannerViewUpdate.startCamera();
    }
}