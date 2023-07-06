package com.example.loginregister;

import static com.example.loginregister.App.CHANNEL_ID;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginregister.ui.dashboard.DashboardFragment;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

public class ServiceTag extends IntentService {
    public static boolean ServiceIsRun = true;
    public static Intent intent;
    public static String Tag_id;
    public static String N_pipelette;
    String tag;
    RequestQueue queue;
    public ServiceTag() {
        super("ServiceTag");
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
//                    //update tag here
//                    String url4 = controller.ip2+"/packet/here_next/?of_num="+packet.selectedOf;
//                    JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest
//                            (Request.Method.GET, url4, null, new Response.Listener<JSONObject>() {
//
//                                @Override
//                                public void onResponse(JSONObject response) {
//                                    N_pipelette = response.optString("pack_num");
//                                }
//                            }, new Response.ErrorListener() {
//
//                                @Override
//                                public void onErrorResponse(VolleyError error) {
//                                    // TODO: Handle error
//
//                                }
//                            });
//
//                    queue.add(jsonObjectRequest1);

                    String url2 = controller.ip2+"/packet/here";
                    JSONObject postObject = new JSONObject();

                    try {
                        postObject.put("tag_id",tag);
                        postObject.put("of_num",packet.selectedOf);
                        postObject.put("pack_num",packet.packnum.getText().toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.e("LoginActivityJsonObject",""+postObject);
                    JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest
                            (Request.Method.PATCH, url2, postObject, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO: Handle error

                                }
                            });

                    queue.add(jsonObjectRequest2);


                    packet.tagrfid.setText(tag);
                    String url6 = controller.ip2+"/packet/here/?of_num="+packet.selectedOf;
                    JsonObjectRequest jsonObjectRequest3 = new JsonObjectRequest
                            (Request.Method.GET, url6, null, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO: Handle error

                                }
                            });

                    queue.add(jsonObjectRequest3);

                    String url5 = controller.ip2+"/packet/here";
                    JsonObjectRequest jsonObjectRequest4 = new JsonObjectRequest
                            (Request.Method.DELETE, url5, null, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO: Handle error

                                }
                            });

                    queue.add(jsonObjectRequest4);

                    Tag_id="";
                    tag="";


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
