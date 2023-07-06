package com.example.loginregister.ui.dashboard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.toolbox.JsonArrayRequest;
import com.example.loginregister.MyTask;
//import com.example.loginregister.MyTaskAdapter;
import com.example.loginregister.R;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginregister.ServiceNotification;
import com.example.loginregister.controller;
import com.example.loginregister.databinding.FragmentGalleryBinding;
import com.example.loginregister.scanSB;
import com.example.loginregister.scanSBdouble;
import com.example.loginregister.scannerView;
import com.example.loginregister.scannerViewSB;
import com.example.loginregister.scannerViewSBdouble;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DashboardFragment extends Fragment{

    AutoCompleteTextView spinnerCountry;
    //Spinner spinnerCity;
    ListView listView;
    ArrayList<String> countryList = new ArrayList<>();
    ArrayList<String> cityList = new ArrayList<>();
    ArrayList<MyTask> cityListTest = new ArrayList<>();
    ArrayList<String> cityNumList = new ArrayList<>();
    ArrayList<String> digiList = new ArrayList<>();
    ArrayAdapter<String> countryAdapter;
    ArrayAdapter<String> cityAdapter;
    RequestQueue requestQueue;
    public static RadioButton op_principale;
    public static RadioButton op_secondaire;
    public static TextInputEditText rend_op;
    public static String selectedCountry, result, digitex;
    public static AlertDialog alertDialog1;
    public static Integer item;
    public static String[] resultArray;
    public static String[] resultArray2;
    public static String[] resultArray3;
    public static String rend;
    int k=1;
    String url2 = controller.ip2+"/operation/bySmartBox";

    private FragmentGalleryBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        requestQueue = Volley.newRequestQueue(getActivity());
        spinnerCountry = view.findViewById(R.id.spinnerCountry);
        //spinnerCity = view.findViewById(R.id.spinnerCity);
        listView = view.findViewById(R.id.listView);
        String url = controller.ip2+"/packet/models";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    // creating a new json object and
                    // getting each object from our json array.
                    try {
                        // we are getting each json object.
                        JSONObject responseObj = response.getJSONObject(i);

                        String countryName = responseObj.optString("model");
                        if(!countryList.contains(countryName))
                        {
                            countryList.add(countryName);
                        }
                        countryAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_expandable_list_item_1, countryList);
                        //countryAdapter.setDropDownViewResource(R.layout.rowof);
                        spinnerCountry.setAdapter(countryAdapter);

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
        //spinnerCountry.setOnItemSelectedListener(this);
        //spinnerCity.setOnItemSelectedListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setCancelable(true);
                View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.operationaffect,null);
                Button buttonMachine = view1.findViewById(R.id.buttonMachine);
                Button buttonDMachine = view1.findViewById(R.id.buttonDMachine);
                Button buttonSupp = view1.findViewById(R.id.buttonSupp);
                op_principale = view1.findViewById(R.id.op_principale);
                op_secondaire = view1.findViewById(R.id.op_secondaire);
                buttonMachine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            String clickItemObj = adapterView.getAdapter().getItem(index).toString();
                            resultArray = clickItemObj.split(" . ");
                            if (DashboardFragment.op_secondaire.isChecked()) {
                                Toast.makeText(getActivity(), "C'est une opération principale", Toast.LENGTH_LONG).show();
                            } else  {
                                startActivity(new Intent(getActivity(), scanSB.class));
                            }
                    }
                });
                buttonDMachine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            String clickItemObj = adapterView.getAdapter().getItem(index).toString();
                            resultArray2 = clickItemObj.split(" . ");
                            scanSBdouble.state=true;
                            startActivity(new Intent(getActivity(), scanSBdouble.class));

                    }
                });

                buttonSupp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String clickItemObj = adapterView.getAdapter().getItem(index).toString();
                        resultArray3 = clickItemObj.split(" . ");

                        JSONObject postObject = new JSONObject();
                        RequestQueue queue = Volley.newRequestQueue(getActivity());

                        try {
                            postObject.put("operation_code", resultArray3[0]);
                            postObject.put("model", selectedCountry);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.e("LoginActivityJsonObject", "" + postObject);
                        JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.POST, url2, postObject,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.e("LoginActivity", "OnResponse: " + response);
                                        Toast.makeText(getActivity(), String.valueOf(response), Toast.LENGTH_LONG).show();
                                        alertDialog1.cancel();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("OnError", String.valueOf(error.getMessage()));
                            }
                        });

                        queue.add(objRequest);


//                        Handler handler1 = new Handler(Looper.getMainLooper());
//                        handler1.post(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                String[] field1 = new String[2];
//                                field1[0] = "Ligne_gamme";
//                                field1[1] = "Modele";
//                                //Creating array for data
//                                String[] data1 = new String[2];
//                                data1[0] = resultArray3[0];
//                                data1[1] = selectedCountry;
//                                PutData putData1 = new PutData(controller.ip+"/LoginRegister2/delete_equi_dy.php", "POST", field1, data1);
//                                if (putData1.startPut()) {
//                                    if (putData1.onComplete()) {
//                                        String result = putData1.getResult();
//                                        if(result.equals("Mise a jour reussie")){
//                                            Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
//                                            alertDialog1.cancel();
//                                        }
//                                    }
//                                }
//                            }
//                        });


                    }
                });

                builder1.setView(view1);
                alertDialog1 = builder1.create();
                alertDialog1.show();

            }
        });

        /*spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Operation_gamme = parent.getSelectedItem().toString();
                startActivity(new Intent(getActivity(), scannerViewSB.class));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        spinnerCountry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCountry= (String) parent.getItemAtPosition(position);
                //cityListTest.clear();
                cityList.clear();
                cityNumList.clear();
                String url = controller.ip2+"/operation/bymodel/"+selectedCountry+"/";
                requestQueue = Volley.newRequestQueue(getActivity());
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            // creating a new json object and
                            // getting each object from our json array.
                            try {
                                // we are getting each json object.
                                JSONObject responseObj = response.getJSONObject(i);

                                String cityName = responseObj.optString("designation");
                                String cityNum = responseObj.optString("operation_num");
                                String box = responseObj.optString("smartbox");

                                //String digitex = jsonObject.optString("DigiTex");
                                //MyTask data = new MyTask();

                                if(!cityNumList.contains(cityNum))
                                {
                                    cityNumList.add(cityNum);
                                    cityList.add(cityNum+" . "+cityName);
//                                    data.setTxtCode(cityNum+" . "+cityName);
//                                    data.setChecked(false);
//                                    cityListTest.add(data);
                                    //digiList.add(digitex);
                                }
//                                if(!box.equals("")){
//                                    data.setChecked(true);
//                                }
                                 //MyTaskAdapter cityAdapter = new MyTaskAdapter(getActivity(), cityListTest);
                                //spinnerCity.setAdapter(cityAdapter);
                                listView.setAdapter(cityAdapter);
                                cityAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_multiple_choice, cityList);
                                cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                //spinnerCity.setAdapter(cityAdapter);
                                listView.setAdapter(cityAdapter);

//                                int len = listView.getCount();
//                                SparseBooleanArray checked = listView.getCheckedItemPositions();
//                                for (int h = 0; h < len; h++) {
//                                    if (checked.get(i)) {
//                                        listView.setItemChecked(h, true);
//                                        listView.setSelection(h);
//                                    }
//
//                                }

                                /*for(int j=0; j < cityList.Count();j++) {
                                    if (!digitex.equals("")) {
                                        listView.setItemChecked(j, true);
                                        listView.setSelection(j);
                                    }
                                }*/

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

            }

        });



        return view;
    }

//    @Override
//    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        if(adapterView.getId() == R.id.spinnerCountry){
//            cityList.clear();
//            cityNumList.clear();
//            selectedCountry = adapterView.getSelectedItem().toString();
//            String url = controller.ip2+"/operation/bymodel/"+selectedCountry+"/";
//            requestQueue = Volley.newRequestQueue(getActivity());
//
//            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
//                @Override
//                public void onResponse(JSONArray response) {
//
//                    for (int i = 0; i < response.length(); i++) {
//                        // creating a new json object and
//                        // getting each object from our json array.
//                        try {
//                            // we are getting each json object.
//                            JSONObject responseObj = response.getJSONObject(i);
//
//                            String cityName = responseObj.optString("designation");
//                            String cityNum = responseObj.optString("operation_num");
//                            //String digitex = jsonObject.optString("DigiTex");
//
//                            if(!cityNumList.contains(cityNum))
//                            {
//                                cityNumList.add(cityNum);
//                                cityList.add(cityNum+" . "+cityName);
//                                //digiList.add(digitex);
//
//                            }
//                            cityAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_multiple_choice, cityList);
//                            cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                            //spinnerCity.setAdapter(cityAdapter);
//                            listView.setAdapter(cityAdapter);
//                            /*for(int j=0; j < cityNumList.Count();j++) {
//                                if (!digitex.equals("")) {
//                                    listView.setItemChecked(j, true);
//                                    listView.setSelection(j);
//                                }
//                            }*/
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                }
//            });
//            requestQueue.add(jsonArrayRequest);
//        }
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> adapterView) {
//
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}