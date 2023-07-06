package com.example.loginregister;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

public class ServiceTagOne extends IntentService {
    public static boolean ServiceIsRun = true;
    public static String[] resultArray;
    public static String result;
    public static String result0;
    public static String id, state;
    public static String tag;
    public static String res;
    public static String result1;
    public static Intent intent;
    RequestQueue queue;
    public ServiceTagOne() {
        super("ServiceTagOne");
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

                    onePacket.tagrfid.setText(tag);
                    String url2 = controller.ip2 + "/packet/here";
                    JSONObject postObject = new JSONObject();

                    try {
                        postObject.put("tag_id", tag);
                        postObject.put("pack_num", onePacket.pack);
                        postObject.put("of_num", onePacket.selectedOF);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.e("LoginActivityJsonObject", "" + postObject);
                    JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest
                            (Request.Method.POST, url2, postObject, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.e("LoginActivity", "OnResponse: " + response);
                                    Toast.makeText(getApplicationContext(), String.valueOf(response), Toast.LENGTH_LONG).show();
                                    try {
                                        Thread.sleep(2000);
                                    } catch (Exception ex) {
                                    }
                                    onePacket.packnum.setText("");
                                    onePacket.tagrfid.setText("");
                                    onePacket.color.setText("");
                                    onePacket.size.setText("");
                                    onePacket.qt.setText("");
                                    onePacket.model.setText("");
                                    onePacket.client.setText("");
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO: Handle error

                                }
                            });

                    queue.add(jsonObjectRequest2);


                    String url5 = controller.ip2 + "/packet/here";
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
                }

//                if (!tag.equals("")) {
//                    String url6 = controller.ip2+"/packet/triage?tag_id="+tag;
//                    JsonObjectRequest jsonObjectRequest5 = new JsonObjectRequest
//                            (Request.Method.GET, url6, null, new Response.Listener<JSONObject>() {
//
//                                @Override
//                                public void onResponse(JSONObject response) {
//                                    if(!response.toString().equals("{}")) {
//                                        state = response.optString("state");
//                                    }
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
//                    queue.add(jsonObjectRequest5);
//                    if(!state.equals("busy")) {
//                        onePacket.tagrfid.setText(tag);
//                        String url2 = controller.ip2 + "/packet/here";
//                        JSONObject postObject = new JSONObject();
//
//                        try {
//                            postObject.put("tag_id", tag);
//                            postObject.put("pack_num", onePacket.pack);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        Log.e("LoginActivityJsonObject", "" + postObject);
//                        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest
//                                (Request.Method.POST, url2, postObject, new Response.Listener<JSONObject>() {
//
//                                    @Override
//                                    public void onResponse(JSONObject response) {
//                                        Log.e("LoginActivity", "OnResponse: " + response);
//                                        Toast.makeText(getApplicationContext(), String.valueOf(response), Toast.LENGTH_LONG).show();
//                                        try {
//                                            Thread.sleep(2000);
//                                        } catch (Exception ex) {
//                                        }
//                                        onePacket.packnum.setText("");
//                                        onePacket.tagrfid.setText("");
//                                        onePacket.of.setText("");
//                                        onePacket.color.setText("");
//                                        onePacket.size.setText("");
//                                        onePacket.qt.setText("");
//                                        onePacket.model.setText("");
//                                        onePacket.client.setText("");
//                                    }
//                                }, new Response.ErrorListener() {
//
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//                                        // TODO: Handle error
//
//                                    }
//                                });
//
//                        queue.add(jsonObjectRequest2);
//
//
//                        String url5 = controller.ip2 + "/packet/here";
//                        JsonObjectRequest jsonObjectRequest4 = new JsonObjectRequest
//                                (Request.Method.DELETE, url5, null, new Response.Listener<JSONObject>() {
//
//                                    @Override
//                                    public void onResponse(JSONObject response) {
//                                    }
//                                }, new Response.ErrorListener() {
//
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//                                        // TODO: Handle error
//
//                                    }
//                                });
//
//                        queue.add(jsonObjectRequest4);
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Paquet non validé au niveau du contrôle",Toast.LENGTH_LONG).show();
//                    }
//                }

                /*Handler handler = new Handler(Looper.getMainLooper());
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
                        PutData putData = new PutData(controller.ip+"/LoginRegister2/triagetagrfid.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                result0 = putData.getResult().toString();
                                if (!result0.equals("")) {
                                    onePacket.tagrfid.setText(result0);
                                    Handler handler00 = new Handler(Looper.getMainLooper());
                                    handler00.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            //Starting Write and Read data with URL
                                            //Creating array for parameters
                                            String[] field = new String[2];
                                            field[0] = "N_pipelette";
                                            field[1] = "Tag_id";
                                            //Creating array for data
                                            String[] data = new String[2];
                                            data[0] = onePacket.pack;
                                            data[1] = result0;
                                            PutData putData = new PutData(controller.ip + "/LoginRegister2/updateonetag.php", "POST", field, data);
                                            if (putData.startPut()) {
                                                if (putData.onComplete()) {
                                                    String result = putData.getResult();
                                                    //End ProgressBar (Set visibility to GONE)
                                                    if (result.equals("Mise a jour reussie")) {
                                                        try {
                                                            Thread.sleep(2000);
                                                        } catch (Exception ex) {
                                                        }
                                                        onePacket.packnum.setText("");
                                                        onePacket.tagrfid.setText("");
                                                        onePacket.of.setText("");
                                                        onePacket.color.setText("");
                                                        onePacket.size.setText("");
                                                        onePacket.qt.setText("");
                                                        onePacket.model.setText("");
                                                        onePacket.client.setText("");
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
                        PutData putData = new PutData(controller.ip + "/LoginRegister2/deletetag.php", "POST", field, data);
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
*/




            } catch (Exception e) {

            }

            try {
                Thread.sleep(2000);
            } catch (Exception ex) {
            }
        }


    }
}
