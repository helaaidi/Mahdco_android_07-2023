package com.example.loginregister;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class scannePanne extends AppCompatActivity implements ZXingScannerView.ResultHandler
{
    ZXingScannerView scannerView;
    public static String qrcode;
    public static String result="";


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

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                //Starting Write and Read data with URL
                //Creating array for parameters
                String[] field = new String[3];
                field[0] = "Failure_type";
                field[1] = "Fullname";
                field[2] = "Machine_id";
                //Creating array for data
                String[] data = new String[3];
                data[0] = GalleryFragment.clickItemObj;
                data[1] = Login.resultArray[2];
                data[2] = qrcode;
                PutData putData = new PutData(controller.ip+"/LoginRegister2/updatepanne.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        result = putData.getResult().toString();
                        if(result.equals("Mise a jour reussie")){
                            Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(),"erreur",Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });

        GalleryFragment.alertDialog1.dismiss();
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