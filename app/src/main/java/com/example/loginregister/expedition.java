package com.example.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class expedition extends AppCompatActivity {

    public static TextView packnum;
    public static TextView tagrfid;
    public static String result;
    public static String packid="";
    public static String tagid="";
    public static String statetag;
    public static TextView of;
    public static TextView color, model;
    public static TextView size;
    public static TextView client;
    public static TextView qt;
    public static String quantitytotal;
    public static String operationnbr;
    public static String productiondate;
    public static String chaineid;
    public static String[] resultArraytagid;
    public static String[] resultArraypackid;
    public static String[] resultArraystatetag;
    public static String[] resultArrayof;
    public static String[] resultArraycolor;
    public static String[] resultArraysize;
    public static String[] resultArrayclient;
    public static String[] resultArrayquantity;
    public static String[] resultArrayquantitytotal;
    public static String[] resultArrayoperationnbr;
    public static String[] resultArrayproductiondate;
    public static String[] resultArraychaineid;
    public static TableLayout packtab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expedition);


        if (ServiceTagexp.ServiceIsRun) {
            //register the services to run in background
            Intent intent = new Intent(this, ServiceTagexp.class);
            // start the services
            startService(intent);
        }


        packnum=(TextView) findViewById(R.id.packnum);
        tagrfid=(TextView) findViewById(R.id.tagrfid);
        of=(TextView) findViewById(R.id.of);
        color=(TextView) findViewById(R.id.color);
        model=(TextView) findViewById(R.id.model);
        size=(TextView) findViewById(R.id.size);
        client=(TextView) findViewById(R.id.client);
        qt=(TextView) findViewById(R.id.qt);




    }
}