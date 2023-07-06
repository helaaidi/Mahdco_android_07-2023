package com.example.loginregister;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONObject;

public class ServiceUpdateHere extends IntentService {
    public static boolean ServiceIsRun = true;
    public static String result;
    public static String tagid;
    public static Intent intent;
    public static String[] resultArray;
    RequestQueue requestQueue;
    public ServiceUpdateHere() {
        super("ServiceUpdateHere");
        setIntentRedelivery(true);
    }
    protected void onHandleIntent(Intent workIntent) {
        while (ServiceIsRun) {
            try {
                String url4 = controller.ip2+"/packet/here_next/?of_num="+packet.selectedOf;
                requestQueue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url4, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                String N_pipelette = response.optString("pack_num");
                                String Coloris = response.optString("color");
                                String Taille = response.optString("size");
                                String Qte_a_monter = response.optString("quantity");
                                String Tag_id = response.optString("tag_rfid");
                                packet.packnum.setText(N_pipelette);
                                packet.color.setText(Coloris);
                                packet.size.setText(Taille);
                                packet.qt.setText(Qte_a_monter);
                                packet.tagrfid.setText(Tag_id);
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error

                            }
                        });

                requestQueue.add(jsonObjectRequest);

            } catch (Exception e) {

            }

            try {
                Thread.sleep(1000);
            } catch (Exception ex) {
            }
        }


    }
}
