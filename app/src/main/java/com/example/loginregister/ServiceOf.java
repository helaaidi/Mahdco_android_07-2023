package com.example.loginregister;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class ServiceOf extends IntentService {
    public static boolean ServiceIsRun = true;
    public static String result;
    public static String tagid;
    public static Intent intent;
    public static String[] resultArray;
    public ServiceOf() {
        super("ServiceHere");
        setIntentRedelivery(true);
    }
    protected void onHandleIntent(Intent workIntent) {
        while (ServiceIsRun) {
            try {

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[1];
                        field[0] = "state";
                        //Creating array for dataw
                        String[] data = new String[1];
                        data[0] = "true";
                        PutData putData = new PutData(controller.ip+"/LoginRegister2/ofs.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                result = putData.getResult().toString();
                                resultArray = result.split(",");
                            }
                        }
                    }
                });

            } catch (Exception e) {

            }

            try {
                Thread.sleep(2000);
            } catch (Exception ex) {
            }
        }


    }
}
