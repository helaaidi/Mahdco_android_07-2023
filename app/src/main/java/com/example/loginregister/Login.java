package com.example.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONObject;

public class Login extends AppCompatActivity {

    TextInputEditText textInputEditTextUsername, textInputEditTextPassword;
    Button buttonLogin;
    TextView textViewSignUp;
    ProgressBar progressBar;
    public static String[] resultArray;
    public static String fullname;
    public static String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputEditTextUsername = findViewById(R.id.username);
        textInputEditTextPassword = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.buttonLogin);
        RadioButton rbtnmonitrice = (RadioButton)findViewById(R.id.rbtnmonitrice);
        RadioButton rbnmaintenancier = (RadioButton)findViewById(R.id.rbnmaintenancier);
        textViewSignUp = findViewById(R.id.signUpText);
        progressBar = findViewById(R.id.progress);

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = String.valueOf(textInputEditTextUsername.getText());
                password = String.valueOf(textInputEditTextPassword.getText());
                if(!username.equals("") &&!password.equals("")) {
                    //Start ProgressBar first (Set visibility VISIBLE)
                    progressBar.setVisibility(View.VISIBLE);
                    RequestQueue queue =  Volley.newRequestQueue(getApplicationContext());
                    String url = controller.ip2+"/user/login/"+username;
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    String pass = response.optString("password");
                                    fullname = response.optString("fullname");
                                    if(pass.equals(password)){

                                        if(rbtnmonitrice.isChecked()){
                                            Toast.makeText(getApplicationContext(),"Connexion Réussie",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), menu.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else if(rbnmaintenancier.isChecked()){
                                            Toast.makeText(getApplicationContext(),"Connexion Réussie",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), maintainer.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else {
                                            Toast.makeText(getApplicationContext(),"Sélectionnez votre profil",Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(),"Nom d'utilisateur ou mot de passe incorrect",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO: Handle error

                                }
                            });

                    queue.add(jsonObjectRequest);

                }
                else {
                    Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}