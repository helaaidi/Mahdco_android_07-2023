package com.example.loginregister.ui.home;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginregister.Login;
import com.example.loginregister.MainActivity;
import com.example.loginregister.MyService;
import com.example.loginregister.R;
import com.example.loginregister.ServiceNotification;
import com.example.loginregister.ServiceNotificationmaint;
import com.example.loginregister.controller;
import com.example.loginregister.databinding.FragmentFirstBinding;
import com.example.loginregister.databinding.FragmentHomeBinding;
import com.example.loginregister.menu;
import com.example.loginregister.scannerView;
import com.example.loginregister.scannerViewOff;
import com.example.loginregister.scannerViewOffmaint;
import com.example.loginregister.scannerViewSB;
import com.example.loginregister.scannerViewSBdouble;
import com.example.loginregister.scannerViewUpdate;
import com.example.loginregister.scanoff;
import com.example.loginregister.scanon;
import com.example.loginregister.triage;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import lecho.lib.hellocharts.view.LineChartView;

public class HomeFragment extends Fragment {

    private FragmentFirstBinding binding;

    Button scanbtn, btnperm;
    public static Button btnoff;
    public static Button btnon;
    public static TextView operator;
    public static TextView nbrp;
    public static TextView ylabel_id;
    public static TextView xlabel_id;
    public static TextView scantext;
    public static String operatrice;
    public static String result00;
    public static String id;
    public static String time;
    public static BarChart chart;
    public static String pack;
    public static SwitchCompat switchcompat;
    public static SwitchCompat switchcompatmaint;
    public static PendingIntent pendingIntent;

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
    public static String digitex;
    public static String result0;
    public static String timeon;
    public static TableLayout tableLayout;
    public static LineChartView lineChartView;


    private NotificationManagerCompat notificationManager;


    @SuppressLint("MissingInflatedId")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        scantext=(TextView) view.findViewById(R.id.scantext);
        scanbtn=(Button) view.findViewById(R.id.scanbtn);
        btnoff=(Button) view.findViewById(R.id.btnoff);
        btnon=(Button) view.findViewById(R.id.btnon);
        operator=(TextView) view.findViewById(R.id.operator);
        nbrp=(TextView) view.findViewById(R.id.nbrp);
        lineChartView = (LineChartView) view.findViewById(R.id.chart);
        ylabel_id=(TextView) view.findViewById(R.id.ylabel_id);
        xlabel_id=(TextView) view.findViewById(R.id.xlabel_id);
        tableLayout=(TableLayout) view.findViewById(R.id.tableLayout);

        if (!ServiceNotification.ServiceIsRun) {
            ServiceNotification.ServiceIsRun = true;
            //register the services to run in background
            // start the services
            getActivity().startService(new Intent(getActivity(),ServiceNotification.class));
        }

        notificationManager = NotificationManagerCompat.from(getActivity());

        Intent intent = new Intent(getActivity(), menu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        pendingIntent=PendingIntent.getActivity(getActivity(), 0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), scannerView.class));
            }
        });

        btnoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("LAST_SMOKE_DATE", new Date().toString());
                editor.apply();

                String dateStr = preferences.getString("LAST_SMOKE_DATE", "");

                String[] resultArray = dateStr.split(" ");
                // Instructor_arrival_time
                time = resultArray[3];

                startActivity(new Intent(getActivity(), scanoff.class));

            }
        });

        btnon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                //opname = scannerView.firstname;
                //digitex = scannerView.qrcode;
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("LAST_SMOKE_DATE", new Date().toString());
                editor.apply();

                String dateStr = preferences.getString("LAST_SMOKE_DATE", "");

                String[] resultArray = dateStr.split(" ");
                // Instructor_arrival_time
                timeon = resultArray[3];
                scanon.state=true;
                startActivity(new Intent(getActivity(), scanon.class));


            }
        });

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        IsMyServiceRunning(MyService.class);
    }

    private boolean IsMyServiceRunning(Class<?> serviceClass)
    {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
        {
            if ("com.example.loginregister.MyService".equals(service.service.getClassName()))
                return true;
        }
        return false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}