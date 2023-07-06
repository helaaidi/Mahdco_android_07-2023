package com.example.loginregister;

import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ServiceMatricule extends IntentService {
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
    RequestQueue queue;
    public ServiceMatricule() {
        super("ServiceMatricule");
        setIntentRedelivery(true);
    }
    protected void onHandleIntent(Intent workIntent) {
        while (ServiceIsRun) {
            try {

                queue =  Volley.newRequestQueue(getApplicationContext());
                String url3 = controller.ip2+"/packet/triage";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.GET, url3, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                tag = response.optString("tag_sort");
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error

                            }
                        });

                queue.add(jsonObjectRequest);

                if (!tag.equals("")) {
                    RH.tagrfid.setText(tag);

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
