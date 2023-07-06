package com.example.loginregister;

import android.annotation.SuppressLint;
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

public class ServiceUpdateHereC extends IntentService {
    public static boolean ServiceIsRun = true;
    public static String result, qte, groupe, pipnum, state;
    public static String result1;
    public static String pip;
    public static String of;
    public static String tag, pack_num;
    public static String id, msg;
    public static Intent intent;
    public static String[] resultArray;
    public static String[] resultArray1;
    RequestQueue queue;
    public ServiceUpdateHereC() {
        super("ServiceUpdateHereC");
        setIntentRedelivery(true);
    }
    protected void onHandleIntent(Intent workIntent) {
        while (ServiceIsRun) {
            try {
                queue =  Volley.newRequestQueue(getApplicationContext());
                String url = controller.ip2+"/packet/control/?prod_line="+controller.selectedGroup;
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                if(!response.toString().equals("{}")) {
                                    tag = response.optString("tag_rfid");
                                    pack_num = response.optString("pack_num");
                                    id = response.optString("id");
                                } else {
                                    tag="";
                                    pack_num="";
                                    id ="";
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error

                            }
                        });

                queue.add(jsonObjectRequest);

                if(!tag.equals("")) {
                    String url2 = controller.ip2+"/packet/control/?pack_num="+pack_num+"&prod_line="+controller.selectedGroup;
                    JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest
                            (Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    if(!response.toString().equals("{}")){
                                        qte = response.optString("quantity");
                                        groupe = response.optString("prod_line");
                                        pipnum = response.optString("pack_num");
                                        controller.tagrfid1.setText(tag);
                                        controller.packnum.setText(pipnum);
                                        controller.ordref.setText(response.optString("of_num"));
                                        controller.color.setText(response.optString("color"));
                                        controller.size.setText(response.optString("size"));
                                        controller.qt.setText(response.optString("quantity"));
                                        controller.model.setText(response.optString("model"));

                                    } else {
                                        Toast.makeText(getApplicationContext(),"Opération déjà effectuée",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO: Handle error

                                }
                            });
                    queue.add(jsonObjectRequest2);

                } else {
                    controller.tagrfid1.setText("");
                    controller.packnum.setText("");
                    controller.ordref.setText("");
                    controller.color.setText("");
                    controller.size.setText("");
                    controller.qt.setText("");
                    controller.model.setText("");
                }

                if(!groupe.equals(controller.selectedGroup)){
                    tag="";
                    pack_num="";
                    id ="";
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
