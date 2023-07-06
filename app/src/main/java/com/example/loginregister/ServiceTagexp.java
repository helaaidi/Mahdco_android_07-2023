package com.example.loginregister;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class ServiceTagexp extends IntentService {
    public static boolean ServiceIsRun = true;
    public static String[] resultArray;
    public static String result;
    public static String result0;
    public static String id;
    public static String tag;
    public static String res;
    public static String result1;
    public static Intent intent;
    public static int id1;
    public ServiceTagexp() {
        super("ServiceTag");
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
                        field[0] = "";
                        //Creating array for data
                        String[] data = new String[1];
                        data[0] = "";
                        PutData putData = new PutData(controller.ip+"/LoginRegister2/triagetagrfidexp.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                result0 = putData.getResult().toString();
                            }
                        }
                    }
                });

                if (!result0.equals("")) {



                    Handler handler2 = new Handler(Looper.getMainLooper());
                    handler2.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[1];
                            field[0] = "tagid";
                            //Creating array for data
                            String[] data = new String[1];
                            data[0] = result0;
                            PutData putData = new PutData(controller.ip+"/LoginRegister2/tagresearch.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    result = putData.getResult().toString();
                                    resultArray = result.split(",");
                                    expedition.tagrfid.setText(result0);
                                    expedition.packnum.setText(resultArray[0]);
                                    expedition.of.setText(resultArray[1]);
                                    expedition.color.setText(resultArray[2]);
                                    expedition.size.setText(resultArray[3]);
                                    expedition.qt.setText(resultArray[4]);
                                    expedition.model.setText(resultArray[6]);
                                    expedition.client.setText(resultArray[7]);
                                }
                            }
                        }
                    });


                }

            } catch (Exception e) {

            }

            try {
                Thread.sleep(1000);
            } catch (Exception ex) {
            }
        }


    }
}
