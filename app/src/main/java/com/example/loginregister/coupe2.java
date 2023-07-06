package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class coupe2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    TextView Model, CodePatronage, lot, client, tissu, qt;
    Spinner spinnerOF;
    TextView eText;
    Button valider;
    String date;
    public static TextView tag;
    DatePickerDialog picker;
    RequestQueue requestQueue;
    ArrayList<String> ModelList = new ArrayList<>();
    ArrayList<String> OFList = new ArrayList<>();
    ArrayAdapter<String> ModelAdapter;
    ArrayAdapter<String> OFAdapter;
    public static String selectedOF, numpip;
    TextInputEditText matela, quantite, num, size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupe2);

        if (ServiceTagCoupe2.ServiceIsRun) {
            //register the services to run in background
            Intent intent = new Intent(this, ServiceTagCoupe2.class);
            // start the services
            startService(intent);
        }

        eText = findViewById(R.id.date);
        tag = findViewById(R.id.tag);
        Model = findViewById(R.id.Modele);
        lot = findViewById(R.id.lot);
        client = findViewById(R.id.client);
        tissu = findViewById(R.id.tissu);
        qt = findViewById(R.id.qt);
        spinnerOF = findViewById(R.id.spinnerOF);
        valider = findViewById(R.id.valider);
        matela = findViewById(R.id.matela);
        quantite = findViewById(R.id.quantite);
        num = findViewById(R.id.num);
        size = findViewById(R.id.size);

        CodePatronage = findViewById(R.id.CodePatronage);

        date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        eText.setText(date);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        String url = controller.ip+"/LoginRegister2/coupe_of.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("ofs");
                    for(int i=0; i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String of = jsonObject.optString("OF");

                        if(!OFList.contains(of))
                        {
                            OFList.add(of);
                        }
                        OFAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.rowof, OFList);
                        OFAdapter.setDropDownViewResource(R.layout.rowof);
                        spinnerOF.setAdapter(OFAdapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
        spinnerOF.setOnItemSelectedListener(this);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                numpip = num.getText().toString()+size.getText().toString()+quantite.getText().toString();
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[11];
                        field[0] = "Tag";
                        field[1] = "OF";
                        field[2] = "Modele";
                        field[3] = "Client";
                        field[4] = "Tissu";
                        field[5] = "Lot";
                        field[6] = "Code_patronage";
                        field[7] = "Qte_a_monter";
                        field[8] = "Matelas";
                        field[9] = "N_T_Quantite";
                        field[10] = "Date";
                        //Creating array for data
                        String[] data = new String[11];
                        data[0] = tag.getText().toString();
                        data[1] = selectedOF;
                        data[2] = Model.getText().toString();
                        data[3] = client.getText().toString();
                        data[4] = tissu.getText().toString();
                        data[5] = lot.getText().toString();
                        data[6] = CodePatronage.getText().toString();
                        data[7] = qt.getText().toString();
                        data[8] = matela.getText().toString();
                        data[9] = numpip;
                        data[10] = date;
                        PutData putData = new PutData(controller.ip+"/LoginRegister2/insert_coupe.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                //End ProgressBar (Set visibility to GONE)
                                if (result.equals("Insertion reussie")) {
                                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                                    tag.setText("");
                                    matela.setText("");
                                    num.setText("");
                                    size.setText("");
                                    quantite.setText("");
                                }


                            }
                        }
                    }
                });

                Handler handler2 = new Handler(Looper.getMainLooper());
                handler2.post(new Runnable() {
                    @Override
                    public void run() {
                        //Starting Write and Read data with URL
                        //Creating array for parameters
                        String[] field = new String[2];
                        field[0] = "OF";
                        field[1] = "N_T_Quantite";
                        //Creating array for data
                        String[] data = new String[2];
                        data[0] = selectedOF;
                        data[1] = numpip;
                        PutData putData = new PutData(controller.ip + "/LoginRegister2/numpip_gamme_coupe.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                //End ProgressBar (Set visibility to GONE)
                                if (result.equals("Mise a jour reussie")) {
                                    //Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    //HomeFragment.alertDialog1.dismiss();

                                }
                            }
                        }
                    }
                });
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.spinnerOF){

            Model.setText("");
            client.setText("");
            tissu.setText("");
            lot.setText("");
            CodePatronage.setText("");
            qt.setText("");
            selectedOF = adapterView.getSelectedItem().toString();
            String url = controller.ip+"/LoginRegister2/coupe_of_detaille.php?OF="+selectedOF;
            requestQueue = Volley.newRequestQueue(getApplicationContext());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("ofs");
                        for(int i=0; i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String CodeModele = jsonObject.optString("Modele");
                            String Client = jsonObject.optString("Client");
                            String Tissu = jsonObject.optString("Tissu");
                            String Lot = jsonObject.optString("Lot");
                            String Code_patronage = jsonObject.optString("Code_patronage");
                            String Quantite = jsonObject.optString("Qte_a_monter");
                            Model.setText(CodeModele);
                            client.setText(Client);
                            tissu.setText(Tissu);
                            lot.setText(Lot);
                            CodePatronage.setText(Code_patronage);
                            qt.setText(Quantite);


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(jsonObjectRequest);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}