package com.example.loginregister.ui.admin;

import android.app.ActivityManager;
import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginregister.Login;
import com.example.loginregister.MyService;
import com.example.loginregister.R;
import com.example.loginregister.ServiceNotification;
import com.example.loginregister.controller;
import com.example.loginregister.databinding.FragmentFirstBinding;
import com.example.loginregister.menu;
import com.example.loginregister.scannerView;
import com.example.loginregister.scannerViewOff;
import com.example.loginregister.scannerViewSB;
import com.example.loginregister.scannerViewSBdouble;
import com.github.mikephil.charting.charts.BarChart;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import lecho.lib.hellocharts.view.LineChartView;

public class AdminFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private FragmentFirstBinding binding;

    Spinner spinnerCountry;
    Spinner groupe;
    //Spinner spinnerCity;
    ListView listView;
    ArrayList<String> countryList = new ArrayList<>();
    ArrayList<String> groupeList = new ArrayList<>();
    ArrayList<String> cityList = new ArrayList<>();
    ArrayList<String> cityNumList = new ArrayList<>();
    ArrayList<String> digiList = new ArrayList<>();
    ArrayAdapter<String> countryAdapter;
    ArrayAdapter<String> groupeAdapter;
    ArrayAdapter<String> cityAdapter;
    RequestQueue requestQueue;
    public static String selectedCountry, result, digitex, selectedgroupe;
    public static AlertDialog alertDialog1;
    public static Integer item;
    public static String[] resultArray;
    public static String[] resultArray2;
    EditText eText;
    DatePickerDialog picker;
    Button valider;


    private NotificationManagerCompat notificationManager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin, container, false);

        requestQueue = Volley.newRequestQueue(getActivity());
        spinnerCountry = view.findViewById(R.id.spinnerCountry);
        groupe = view.findViewById(R.id.groupe);
        //spinnerCity = view.findViewById(R.id.spinnerCity);
        listView = view.findViewById(R.id.listView);
        valider = view.findViewById(R.id.valider);
        eText= view.findViewById(R.id.editText1);
        eText.setInputType(InputType.TYPE_NULL);
        eText.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                eText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        String url = controller.ip2+"/packet/Groupe";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    // creating a new json object and
                    // getting each object from our json array.
                    try {
                        // we are getting each json object.
                        JSONObject responseObj = response.getJSONObject(i);

                        String groupeName = responseObj.optString("Groupe");
                        if(!groupeList.contains(groupeName))
                        {
                            groupeList.add(groupeName);
                        }
                        groupeAdapter = new ArrayAdapter<>(getActivity(), R.layout.rowof, groupeList);
                        groupeAdapter.setDropDownViewResource(R.layout.rowof);
                        groupe.setAdapter(groupeAdapter);

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
        spinnerCountry.setOnItemSelectedListener(this);
        groupe.setOnItemSelectedListener(this);

        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SparseBooleanArray checked = listView.getCheckedItemPositions();
                String itemselected="";
                for (int i = 0; i < listView.getAdapter().getCount(); i++) {
                    if (checked.get(i)) {
                        itemselected+=listView.getItemAtPosition(i)+",";
                    }
                }
            }
        });



        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.groupe){
            countryList.clear();
            selectedgroupe = adapterView.getSelectedItem().toString();
            String url3 = controller.ip2+"/packet/ModeleByGroupe/?Groupe="+selectedgroupe;
            requestQueue = Volley.newRequestQueue(getActivity());
            JsonArrayRequest jsonArrayRequest3 = new JsonArrayRequest(Request.Method.GET, url3, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    for (int i = 0; i < response.length(); i++) {
                        // creating a new json object and
                        // getting each object from our json array.
                        try {
                            // we are getting each json object.
                            JSONObject responseObj = response.getJSONObject(i);

                            String countryName = responseObj.optString("Modele");
                            if(!countryList.contains(countryName))
                            {
                                countryList.add(countryName);
                            }
                            countryAdapter = new ArrayAdapter<>(getActivity(), R.layout.rowof, countryList);
                            countryAdapter.setDropDownViewResource(R.layout.rowof);
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
            requestQueue.add(jsonArrayRequest3);
            //spinnerCountry.setOnItemSelectedListener(this);

        }
        if(adapterView.getId() == R.id.spinnerCountry){
            cityList.clear();
            selectedCountry = adapterView.getSelectedItem().toString();
            String url2 = controller.ip2+"/packet/of/?Modele="+selectedCountry;
            requestQueue = Volley.newRequestQueue(getActivity());

            JsonArrayRequest jsonArrayRequest2 = new JsonArrayRequest(Request.Method.GET, url2, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {

                    for (int i = 0; i < response.length(); i++) {
                        // creating a new json object and
                        // getting each object from our json array.
                        try {
                            // we are getting each json object.
                            JSONObject responseObj = response.getJSONObject(i);
                            String cityNum = responseObj.optString("OF");
                            if(!cityList.contains(cityNum))
                            {
                                cityList.add(cityNum);
                            }
                            cityAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_multiple_choice, cityList);
                            cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            listView.setAdapter(cityAdapter);

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
            requestQueue.add(jsonArrayRequest2);
        }




    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}