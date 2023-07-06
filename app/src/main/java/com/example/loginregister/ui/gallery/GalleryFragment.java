package com.example.loginregister.ui.gallery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginregister.R;
import com.example.loginregister.ServiceNotificationmaint;
import com.example.loginregister.controller;
import com.example.loginregister.databinding.FragmentGalleryBinding;
import com.example.loginregister.scannePanne;
import com.example.loginregister.scannerViewOffmaint;
import com.example.loginregister.scannerViewbox;
import com.example.loginregister.scannerViewmachine;
import com.example.loginregister.scanoffmaint;
import com.example.loginregister.testscan;
import com.example.loginregister.testscanbox;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    private FragmentGalleryBinding binding;
    public static AlertDialog alertDialog1;
    ArrayList<String> prodList = new ArrayList<>();
    ArrayAdapter<String> prodAdapter;
    public static Spinner groupe;
    public static String time;
    public static String monit="";
    public static String sb="";
    public static Button btn01;
    String[] liste_panne ={"aspect (ondulation)","coltage mal coupé","polie de moteur casse","tissu mal coupé","presse ne marche pas","casse fil","point manque","nervure non régulié","craquage ","aspect (ondulation)","disfonctionnement de coupe fil","Déblocage ","point serré","tache","casse aiguille ","décalage tissu","mauvais point","mauvais point ","aspect ","bruit","plateau ne fonctionne pas","disfonctionnement de moteur","disfonctionnement de présostat","fuite d aire au niveau monométre ","manque de vaporisation ","fuite du vapeur","fér à repasser ne rechauffe pas ","mauvaise point","dèpassement cabine","mauvais contact porte rideau","mauvais contact 4 éme ètage","mauvais aspect (élestique silicone)","nervure non regulier","point craqué","blocage","craquage","trace d'aiguille","electrique","mesure non reglier","dépannage électrique","disfonctionnement charge canette","programation","ne foctionne pas","nervure non régulier","centrage gabarie","casse aiguille","échapure"," mauvais point","aspect","mauvaise  point","panne electrique","fér à repasser ne fonctionne pas","fér à repasser ne rechauffe pas","nettoyage ventilateur","nettoyage ventilateur ","panne électrique","manque vapeur","point manque ","mauvais contact 2 éme ètage","changement cable","fuite d'aire au niveau monométre ","réparation électrique ","manque de temperature","changement voyant","roulement","ne marche pas","grand point","casse fils","ondulation"," craquage","casse fil au début","casse fil début","mauvaise aspect(élestique silicone ) ","forme d'élastique","casse fil en cours","mauvaise aspect (élestique silicon)","trace aiguille","casse  fil au début"};
    ArrayAdapter<String> adapter;
    public static String clickItemObj;
    RequestQueue requestQueue;

    private NotificationManagerCompat notificationManager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        Button btn1 = (Button) view.findViewById(R.id.btn1);
        btn01 = (Button) view.findViewById(R.id.btn01);
        Button btn2 = (Button) view.findViewById(R.id.btn2);
        Button btnoff = (Button) view.findViewById(R.id.btnoff);
        groupe=(Spinner) view.findViewById(R.id.groupe);

        if (!ServiceNotificationmaint.ServiceIsRun) {
            ServiceNotificationmaint.ServiceIsRun = true;
            //register the services to run in background
            // start the services
            getActivity().startService(new Intent(getActivity(),ServiceNotificationmaint.class));
        }

        notificationManager = NotificationManagerCompat.from(getActivity());

        requestQueue = Volley.newRequestQueue(getActivity());
        String url = controller.ip2+"/productionLine/prodlines";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    // creating a new json object and
                    // getting each object from our json array.
                    try {
                        // we are getting each json object.
                        JSONObject responseObj = response.getJSONObject(i);

                        String prod = responseObj.optString("prod_line");
                        if(!prodList.contains(prod))
                        {
                            prodList.add(prod);
                        }
                        prodAdapter = new ArrayAdapter<>(getActivity(), R.layout.rowof, prodList);
                        prodAdapter.setDropDownViewResource(R.layout.rowof);
                        groupe.setAdapter(prodAdapter);

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
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), testscan.class));

            }
        });

        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), testscanbox.class));

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setCancelable(true);
                View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.panne,null);
                ListView listView = view1.findViewById(R.id.listView);
                adapter = new ArrayAdapter<>(getActivity(), R.layout.row, liste_panne);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                        clickItemObj = adapterView.getAdapter().getItem(index).toString();
                        startActivity(new Intent(getActivity(), scannePanne.class));
                    }
                });
                builder1.setView(view1);
                alertDialog1 = builder1.create();
                alertDialog1.show();
            }
        });

        btnoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("LAST_SMOKE_DATE", new Date().toString());
                editor.apply();

                String dateStr = preferences.getString("LAST_SMOKE_DATE", "");

                String[] resultArray = dateStr.split(" ");
                // Instructor_arrival_time
                time = resultArray[3];

                /*Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        String[] field = new String[1];
                        field[0] = "state";
                        //Creating array for data
                        String[] data = new String[1];
                        data[0] = "true";
                        PutData putData = new PutData(controller.ip+"/LoginRegister2/notificationrqtmaintoff.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                String[] resultArray = result.split("/");
                                for (int i = 0; i < resultArray.length; i++) {
                                    String[] resultArray2 = resultArray[i].split(",");
                                    monit = resultArray2[0];
                                    sb = resultArray2[1].toString();
                                }
                            }
                        }
                    }
                });*/

                startActivity(new Intent(getActivity(), scanoffmaint.class));


            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}