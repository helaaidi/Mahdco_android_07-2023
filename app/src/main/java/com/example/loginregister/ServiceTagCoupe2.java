package com.example.loginregister;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class ServiceTagCoupe2 extends IntentService {
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
    public ServiceTagCoupe2() {
        super("ServiceTagCoupe");
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
                        PutData putData = new PutData(controller.ip+"/LoginRegister2/tagrfid_coupe.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                result0 = putData.getResult().toString();
                                if (!result0.equals("")) {
                                    coupe2.tag.setText(result0);
                                    Handler handler000 = new Handler(Looper.getMainLooper());
                                    handler000.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            //Starting Write and Read data with URL
                                            //Creating array for parameters
                                            String[] field = new String[1];
                                            field[0] = "tag";
                                            //Creating array for data
                                            String[] data = new String[1];
                                            data[0] = result0;
                                            PutData putData = new PutData(controller.ip + "/LoginRegister2/deletetag_coupe.php", "POST", field, data);
                                            if (putData.startPut()) {
                                                if (putData.onComplete()) {
                                                    String result = putData.getResult();
                                                    //End ProgressBar (Set visibility to GONE)
                                                    if (result.equals("Mise a jour reussie")) {
                                                        //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                                        //HomeFragment.alertDialog1.dismiss();
                                                    }
                                                }
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    }
                });



            } catch (Exception e) {

            }

            try {
                Thread.sleep(3000);
            } catch (Exception ex) {
            }
        }


    }
}
