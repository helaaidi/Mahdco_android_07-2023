package com.example.loginregister;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.loginregister.ui.dashboard.DashboardFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
//public class MyTaskAdapter extends ArrayAdapter<MyTask> {
//    Context context;
//    public static List<MyTask> arrayListMyData;
//    public static final String TAG = "MyTaskAdapter";
//    public static String[] resultArray2;
//
//    LayoutInflater inflater;
//    public MyTaskAdapter(@NonNull Context context, List<MyTask> arrayListMyData) {
//        super(context, R.layout.task_list_item, arrayListMyData);
//        this.context = context;
//        inflater = LayoutInflater.from(context);
//        this.arrayListMyData = arrayListMyData;
//    }
//
//    @SuppressLint({"MissingInflatedId", "ResourceAsColor"})
//    @NonNull
//    @Override
//    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_list_item, null, true);
//
//        TextView codeText = view.findViewById(R.id.NumTask);
//        CheckBox taskState = view.findViewById(R.id.taskState);
//        MyTask task=arrayListMyData.get(position);
//        taskState.setChecked(task.isChecked());
//        codeText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
//                View view1 = LayoutInflater.from(getContext()).inflate(R.layout.operationaffect, null);
//                builder1.setView(view1);
//                Button buttonMachine = view1.findViewById(R.id.buttonMachine);
//                Button buttonDMachine = view1.findViewById(R.id.buttonDMachine);
//                Button buttonSupp = view1.findViewById(R.id.buttonSupp);
//                DashboardFragment.op_principale = view1.findViewById(R.id.op_principale);
//                DashboardFragment.op_secondaire = view1.findViewById(R.id.op_secondaire);
//                buttonMachine.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        DashboardFragment.resultArray = DashboardFragment.data.getTxtCode().split(" . ");
//                        if (DashboardFragment.op_secondaire.isChecked()) {
//                            Toast.makeText(getContext(), "C'est une opération principale", Toast.LENGTH_LONG).show();
//                        } else {
//                            getContext().startActivity(new Intent(getContext(), scanSB.class));
//                        }
//                    }
//                });
//                buttonDMachine.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        resultArray2 = DashboardFragment.data.getTxtCode().split(" . ");
//                        scanSBdouble.state = true;
//                        getContext().startActivity(new Intent(getContext(), scanSBdouble.class));
//
//                    }
//                });
//
//                buttonSupp.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        DashboardFragment.resultArray2 = DashboardFragment.data.getTxtCode().split(" . ");
//                        Log.d(TAG, "onClick: "+DashboardFragment.data.getTxtCode());
//                        JSONObject postObject = new JSONObject();
//                        RequestQueue queue = Volley.newRequestQueue(getContext());
//
//                        try {
//                            postObject.put("operation_code", DashboardFragment.resultArray3[0]);
//                            postObject.put("model", DashboardFragment.selectedCountry);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        Log.e("LoginActivityJsonObject", "" + postObject);
//                        JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.POST, DashboardFragment.url2, postObject,
//                                new Response.Listener<JSONObject>() {
//                                    @Override
//                                    public void onResponse(JSONObject response) {
//                                        Log.e("LoginActivity", "OnResponse: " + response);
//                                        Toast.makeText(getContext(), String.valueOf(response), Toast.LENGTH_LONG).show();
//                                        DashboardFragment.alertDialog1.cancel();
//                                    }
//                                }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Log.e("OnError", String.valueOf(error.getMessage()));
//                            }
//                        });
//
//                        queue.add(objRequest);
//
//
//                        DashboardFragment.alertDialog1 = builder1.create();
//                        DashboardFragment.alertDialog1.show();
//
//                    }
//                });
//                DashboardFragment.alertDialog1 = builder1.create();
//                DashboardFragment.alertDialog1.show();
//            }
//        });
//
//        taskState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                task.setChecked(isChecked);
//            }
//        });
//        codeText.setText(arrayListMyData.get(position).getTxtCode());
//        return view;
//    }
//
//    public List<MyTask> getCheckedItems() {
//        List<MyTask> checkedItems = new ArrayList<>();
//        for (MyTask item : arrayListMyData) {
//            if (item.isChecked()) {
//                checkedItems.add(item);
//            }
//        }
//        return checkedItems;
//    }

//}