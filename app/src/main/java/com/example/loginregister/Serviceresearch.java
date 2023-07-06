package com.example.loginregister;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Serviceresearch extends IntentService {
    public static boolean ServiceIsRun = true;
    public static String result;
    public static String tagid;
    public static Intent intent;
    public static String[] resultArray;
    public Serviceresearch() {
        super("Serviceresearch");
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
                        field[0] = "tagid";
                        //Creating array for data
                        String[] data = new String[1];
                        data[0] = controller.tagrfid1.getText().toString();
                        PutData putData = new PutData(controller.ip+"/LoginRegister2/tagresearch.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                result = putData.getResult().toString();
                                resultArray = result.split(",");
                                controller.packnum.setText(resultArray[0]);
                                controller.ordref.setText(resultArray[1]);
                                controller.color.setText(resultArray[2]);
                                controller.size.setText(resultArray[3]);
                                controller.qt.setText(resultArray[4]);

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
