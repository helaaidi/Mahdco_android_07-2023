package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class onePacket extends AppCompatActivity{

    public static TextView tagrfid;
    public static String result, id, pack, msg, selectedOF;
    public static String tagid="";
    public static AutoCompleteTextView of;
    public static TextView color, model;
    public static TextView size;
    public static TextView client;
    public static TextView qt;
    public static EditText packnum;
    RequestQueue requestQueue;
    ArrayList<String> OFList = new ArrayList<>();
    ArrayAdapter<String> ModelAdapter;
    ArrayAdapter<String> OFAdapter;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_packet);
        packnum=(EditText) findViewById(R.id.packnum);
        tagrfid=(TextView) findViewById(R.id.tagrfid);
        of=(AutoCompleteTextView) findViewById(R.id.of);
        color=(TextView) findViewById(R.id.color);
        model=(TextView) findViewById(R.id.model);
        size=(TextView) findViewById(R.id.size);
        client=(TextView) findViewById(R.id.client);
        qt=(TextView) findViewById(R.id.qt);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = controller.ip2+"/packet/of_num";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    // creating a new json object and
                    // getting each object from our json array.
                    try {
                        // we are getting each json object.
                        JSONObject responseObj = response.getJSONObject(i);

                        String ofs = responseObj.optString("of_num");
                        if(!OFList.contains(ofs))
                        {
                            OFList.add(ofs);
                        }
                        OFAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_expandable_list_item_1, OFList);
                        //OFAdapter.setDropDownViewResource(R.layout.rowof);
                        of.setAdapter(OFAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonArrayRequest);
        of.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedOF= (String) parent.getItemAtPosition(position);

            }

        });

        packnum.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    pack = packnum.getText().toString();
                    queue =  Volley.newRequestQueue(getApplicationContext());
                    String url3 = controller.ip2+"/packet/pack_num/?pack_num="+pack+"&of_num="+selectedOF;
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.GET, url3, null, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    if(!response.toString().equals("{}")) {
                                        color.setText(response.optString("color"));
                                        size.setText(response.optString("size"));
                                        qt.setText(response.optString("quantity"));
                                        model.setText(response.optString("model"));
                                        client.setText(response.optString("client"));
                                    } else {
                                        Toast.makeText(getApplicationContext(),"Numéro de paquet inconnu",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO: Handle error

                                }
                            });

                    queue.add(jsonObjectRequest);
                    /*Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            String[] field = new String[1];
                            field[0] = "N_pipelette";
                            //Creating array for data
                            String[] data = new String[1];
                            data[0] = pack;
                            PutData putData = new PutData(controller.ip+"/LoginRegister2/researchpacket.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    if(!result.equals("No rows retrieved")){
                                        String[] resultArray = result.split(",");
                                        of.setText(resultArray[0]);
                                        color.setText(resultArray[4]);
                                        size.setText(resultArray[5]);
                                        qt.setText(resultArray[3]);
                                        model.setText(resultArray[2]);
                                        client.setText(resultArray[1]);
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"Numéro de paquet inconnu",Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }
                        }
                    });
*/

                    return true;
                }
                return false;
            }
        });

        if (ServiceTagOne.ServiceIsRun) {
            //register the services to run in background
            Intent intent = new Intent(this, ServiceTagOne.class);
            // start the services
            startService(intent);
        }


    }
//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        if(adapterView.getId() == R.id.of){
//            selectedOF = adapterView.getSelectedItem().toString();
//        }
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }
}