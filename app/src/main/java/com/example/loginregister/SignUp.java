package com.example.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginregister.ui.dashboard.DashboardFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUp extends AppCompatActivity {

    TextInputEditText textInputEditTextFullname, textInputEditTextUsername, textInputEditTextPassword, textInputEditTextEmail;
    Button buttonSignUp;
    TextView textViewLogin;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        textInputEditTextFullname = findViewById(R.id.fullname);
        textInputEditTextUsername = findViewById(R.id.username);
        textInputEditTextPassword = findViewById(R.id.password);
        textInputEditTextEmail = findViewById(R.id.email);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        textViewLogin = findViewById(R.id.loginText);
        progressBar = findViewById(R.id.progress);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname, username, password, email;
                fullname = String.valueOf(textInputEditTextFullname.getText());
                username = String.valueOf(textInputEditTextUsername.getText());
                password = String.valueOf(textInputEditTextPassword.getText());
                email = String.valueOf(textInputEditTextEmail.getText());
                String url = controller.ip2+"/user/signup";
                if(!fullname.equals("") && !username.equals("") && !password.equals("") && !email.equals("")) {
                    //Start ProgressBar first (Set visibility VISIBLE)
                    progressBar.setVisibility(View.VISIBLE);
                    JSONObject postObject = new JSONObject();
                    RequestQueue queue =  Volley.newRequestQueue(getApplicationContext());

                    try {
                        //historyObject.put("id","1");
                        postObject.put("fullname",fullname);
                        postObject.put("username", username);
                        postObject.put("password",password);
                        postObject.put("email",email);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.e("LoginActivityJsonObject",""+postObject);
                    JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.POST, url,postObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.e("LoginActivity","OnResponse: "+response);
                                    String result = response.optString("message");
                                    if (result.equals("Inscription reussie")) {
                                        Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("OnError", String.valueOf(error.getMessage()));
                        }
                    });

                /*final String digitex = qrcode.trim();
                final String lignegamme = DashboardFragment.resultArray[0].trim();
                final String modele = DashboardFragment.selectedCountry.trim();*/

                    queue.add(objRequest);
                }
                else {
                    Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}