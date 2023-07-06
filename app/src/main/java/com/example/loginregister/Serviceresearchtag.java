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

public class Serviceresearchtag extends IntentService {
    public static boolean ServiceIsRun = true;
    public static String result;
    public static String result1;
    public static String result0;
    public static String result2;
    public static String result3;
    public static String tagid;
    public static String id;
    public static String tag;
    public static Intent intent;
    public static String[] resultArray;
    public static String[] resultArray1;
    RequestQueue queue;
    public Serviceresearchtag() {
        super("Serviceresearchtag");
        setIntentRedelivery(true);
    }
    protected void onHandleIntent(Intent workIntent) {
        while (ServiceIsRun) {
            try {
                queue =  Volley.newRequestQueue(getApplicationContext());
                String url = controller.ip2+"/packet/triage";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                tag = response.optString("tag_rfid");
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error

                            }
                        });

                queue.add(jsonObjectRequest);
                if (!tag.equals("")) {

                    String url2 = controller.ip2+"/packet/research/?tag_id="+tag;
                    JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest
                            (Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    if(!response.toString().equals("{}")) {
                                        research.tagrfid.setText(tag);
                                        research.packnum.setText(response.optString("number"));
                                        research.ordref.setText(response.optString("of_num"));
                                        research.color.setText(response.optString("color"));
                                        research.size.setText(response.optString("size"));
                                        research.qt.setText(response.optString("quantity"));
                                        String mod = response.optString("model");
                                        if(!mod.equals("")){
                                            research.model.setText(response.optString("model"));
                                        } else {
                                            research.model.setText("--");
                                        }
                                        String op = response.optString("operation_num");
                                        if(!op.equals("")){
                                            research.operation.setText(response.optString("operation_num"));
                                        } else {
                                            research.operation.setText("--");
                                        }
                                    } else {
                                        Toast.makeText(getApplicationContext(),"Tag n'est pas affecté",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO: Handle error

                                }
                            });

                    queue.add(jsonObjectRequest1);

                }
            } catch (Exception e) {

            }

            try {
                Thread.sleep(2000);
            } catch (Exception ex) {
            }
        }


    }
}
