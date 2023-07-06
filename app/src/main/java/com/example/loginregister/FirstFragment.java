package com.example.loginregister;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.loginregister.databinding.FragmentFirstBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.Highlight;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

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
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first, container, false);

        scanbtn=(Button) view.findViewById(R.id.scanbtn);


        scanbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),scannerView.class));

            }
        });

        return view;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}