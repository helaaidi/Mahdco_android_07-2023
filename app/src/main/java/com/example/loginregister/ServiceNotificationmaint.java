package com.example.loginregister;

import static com.example.loginregister.App.CHANNEL_ID;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServiceNotificationmaint extends IntentService {
    public static boolean ServiceIsRun = false;
    public static String msg, digitex, group, id;
    public static Intent intent;
    RequestQueue requestQueue;
    public ServiceNotificationmaint() {
        super("ServiceNotificationmaint");
        setIntentRedelivery(true);
    }
    protected void onHandleIntent(Intent workIntent) {
        while (ServiceIsRun) {
            try {
                requestQueue = Volley.newRequestQueue(getApplicationContext());
                String url = controller.ip2+"/monitor/callMaintainer/true";
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(!response.toString().equals("{}")) {

                            for (int i = 0; i < response.length(); i++) {
                                // creating a new json object and
                                // getting each object from our json array.
                                try {
                                    // we are getting each json object.
                                    JSONObject responseObj = response.getJSONObject(i);

                                    group = responseObj.optString("prod_line");
                                    digitex = responseObj.optString("digitex");
                                    id = responseObj.optString("id");
                                    msg = group;


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                        msg="";
                    }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
                requestQueue.add(jsonArrayRequest);

                /*Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[1];
                        field[0] = "state";
                        //Creating array for data
                        String[] data = new String[1];
                        data[0] = "true";
                        PutData putData = new PutData(controller.ip+"/LoginRegister2/notificationrqtmaintoff.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult().toString();
                                String[] resultArray = result.split("/");
                                msg = resultArray[0];

                            }
                        }
                    }
                });*/
                if(!msg.equals("")){
                    /*Intent intent = new Intent();
                    intent.setAction("com.example.Broadcast");
                    intent.putExtra("msg", msg);
                    sendBroadcast(intent);*/

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                    Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_android)
                            .setContentTitle("call")
                            .setContentText(msg)
                            .build();
                    notificationManager.notify(1, notification);
                }

            } catch (Exception e) {

            }

            try {
                Thread.sleep(3000);
            } catch (Exception ex) {
            }
        }


    }
}
