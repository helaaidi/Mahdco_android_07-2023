package com.example.loginregister;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.loginregister.util.MPUtil;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;
import android.widget.TextView;

import static com.example.loginregister.App.CHANNEL_ID;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button scanbtn;
    public static TextView operator;
    public static TextView nbrp;
    public static TextView ylabel_id;
    public static TextView xlabel_id;
    public static TextView scantext;
    public static String operatrice;
    public static BarChart chart;
    public static String pack;
    public static SwitchCompat switchcompat;

    public static TextView packet;
    public static TextView efficiency;
    AlertDialog alertDialog;
    AlertDialog alertDialog1;
    DialogInterface dialog;
    public static int packselect;
    public static int numopselect;
    public static Notification notification;

    public static String state;
    public static String opname;

    private NotificationManagerCompat notificationManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scantext=(TextView) findViewById(R.id.scantext);
        scanbtn=(Button) findViewById(R.id.scanbtn);
        operator=(TextView) findViewById(R.id.operator);
        nbrp=(TextView) findViewById(R.id.nbrp);
        chart = (BarChart) findViewById(R.id.chart);
        ylabel_id=(TextView) findViewById(R.id.ylabel_id);
        xlabel_id=(TextView) findViewById(R.id.xlabel_id);
        switchcompat = (SwitchCompat) findViewById(R.id.switchcompat);

        if (!ServiceNotification.ServiceIsRun) {
            ServiceNotification.ServiceIsRun = true;
            //register the services to run in background
            Intent intent = new Intent(this, ServiceNotification.class);
            // start the services
            startService(intent);
        }

        notificationManager = NotificationManagerCompat.from(this);

        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),scannerView.class));
            }
        });

        switchcompat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                state = String.valueOf(switchcompat.isChecked());
                opname = scannerView.op;
                if(state.equals("true")) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "state";
                            field[1] = "opname";
                            //Creating array for data
                            String[] data = new String[2];
                            data[0] = state;
                            data[1] = opname;
                            PutData putData = new PutData("http://192.168.200.163/ISA_PHP_File/LoginRegister2/stateonoff.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    //End ProgressBar (Set visibility to GONE)
                                    if(result.equals("Update Success")){
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                }
                else{
                    startActivity(new Intent(getApplicationContext(),scannerViewOff.class));
                }

            }
        });

        operator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setCancelable(true);
                View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.nameop_update,null);
                TextInputEditText newnameop = view1.findViewById(R.id.newnameop);
                Button buttonUpdate = view1.findViewById(R.id.buttonUpdate);
                buttonUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String newname = String.valueOf(newnameop.getText());
                        String numope = scannerView.numope;
                        if(!newname.equals("")) {
                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    //Starting Write and Read data with URL
                                    //Creating array for parameters
                                    String[] field = new String[2];
                                    field[0] = "newname";
                                    field[1] = "numope";
                                    //Creating array for data
                                    String[] data = new String[2];
                                    data[0] = newname;
                                    data[1] = numope;
                                    PutData putData = new PutData("http://192.168.200.163/ISA_PHP_File/LoginRegister2/updatenameop.php", "POST", field, data);
                                    if (putData.startPut()) {
                                        if (putData.onComplete()) {
                                            String result = putData.getResult();
                                            //End ProgressBar (Set visibility to GONE)
                                            if(result.equals("Update Success")){
                                                Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                                alertDialog1.dismiss();
                                                operator.setText(newname);
                                            }
                                        }
                                    }
                                    //End Write and Read data with URL
                                }
                            });
                        }
                    }
                });
                builder1.setView(view1);
                alertDialog1 = builder1.create();
                alertDialog1.show();



            }
        });

        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                float index_f = e.getXIndex();
                int index = Math.round(index_f);
                packselect = scannerView.dataList2.get(index);
                numopselect = scannerView.dataList3.get(index);
                pack = String.valueOf(packselect);
                String numoperation = String.valueOf(numopselect);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.packet_details,null);
                packet = view.findViewById(R.id.packet);
                efficiency = view.findViewById(R.id.efficiency);
                packet.setText("Num Packet : "+pack);
                efficiency.setText("Num Operation : "+numoperation);
                packet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(),scannerViewUpdate.class));
                        alertDialog.dismiss();
                        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                            @SuppressLint("SetTextI18n")
                            @Override
                            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                                float index_f = e.getXIndex();
                                int index = Math.round(index_f);
                                packselect = scannerViewUpdate.dataList2.get(index);
                                numopselect = scannerViewUpdate.dataList3.get(index);
                                pack = String.valueOf(packselect);
                                String numoperation = String.valueOf(numopselect);
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setCancelable(true);
                                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.packet_details,null);
                                packet = view.findViewById(R.id.packet);
                                efficiency = view.findViewById(R.id.efficiency);
                                packet.setText("Num Packet : "+pack);
                                efficiency.setText("Num Operation : "+numoperation);
                                builder.setView(view);
                                alertDialog = builder.create();
                                alertDialog.show();
                            }

                            @Override
                            public void onNothingSelected() {

                            }
                        });

                    }
                });
                builder.setView(view);
                alertDialog = builder.create();
                alertDialog.show();


            }

            @Override
            public void onNothingSelected() {

            }
        });


    }

}