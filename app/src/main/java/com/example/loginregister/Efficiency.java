package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Efficiency extends AppCompatActivity {

    //public static TextView operatorname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_efficiency);

        TextView operatorname = (TextView) findViewById(R.id.operatorname);
        operatorname.setText(scannerView.operatrice);

    }
}