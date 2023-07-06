package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RH extends AppCompatActivity {

    public static TextView tagrfid;
    public static EditText matricule, firstname, lastname;
    Button btnvalider;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rh);

        tagrfid=(TextView) findViewById(R.id.tagrfid);
        matricule=(EditText) findViewById(R.id.matricule);
        firstname=(EditText) findViewById(R.id.firstname);
        lastname=(EditText) findViewById(R.id.lastname);
        btnvalider=(Button) findViewById(R.id.btnvalider);

        queue =  Volley.newRequestQueue(getApplicationContext());


        if (ServiceMatricule.ServiceIsRun) {
            //register the services to run in background
            Intent intent = new Intent(this, ServiceMatricule.class);
            // start the services
            startService(intent);
        }
        btnvalider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!tagrfid.getText().toString().equals("")) {

                    if (!matricule.getText().toString().equals("") && !firstname.getText().toString().equals("") && !lastname.getText().toString().equals("")) {

                        String fullname = firstname.getText().toString() + " " + lastname.getText().toString();

                        String url2 = controller.ip2 + "/operator/addOperator";
                        JSONObject postObject = new JSONObject();

                        try {
                            postObject.put("card_id", tagrfid.getText().toString());
                            postObject.put("reg_num", matricule.getText().toString());
                            postObject.put("full_name", fullname);
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
                                        tagrfid.setText("");
                                        matricule.setText("");
                                        firstname.setText("");
                                        lastname.setText("");
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


                    } else {
                        Toast.makeText(getApplicationContext(), "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Merci de taguer", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}