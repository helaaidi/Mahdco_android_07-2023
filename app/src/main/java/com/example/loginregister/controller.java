package com.example.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.example.loginregister.ui.dashboard.DashboardFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.travijuu.numberpicker.library.NumberPicker;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class controller extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public static String ip ="http://192.168.43.204/ISA_PHP_File";
    public static String ip2 ="http://192.168.201.32/digitex_tab_mahdco/api/v3";
    //http://192.168.201.38
    //http://192.168.1.100
    //http://192.168.200.163/ISA_PHP_File
    public static String tend;
    public static int totalqte = 0;
    public static int totalqte2 = 0;
    RequestQueue requestQueue;
    @SuppressLint("StaticFieldLeak")
    public static Spinner groupe;
    public static String selectedGroup;
    ArrayAdapter<String> groupAdapter;
    ArrayList<String> groupList = new ArrayList<>();

    public static boolean ServiceIsRun = true;
    int minteger = 0;
    int minteger1 = 0;
    int minteger2 = 0;
    int minteger3 = 0;
    int minteger0 = 0;
    int minteger01 = 0;
    int minteger02 = 0;
    int minteger03 = 0;
    int minteger04 = 0;
    int minteger05 = 0;
    int minteger00 = 0;
    int minteger003 = 0;
    int minteger002 = 0;
    int minteger001 = 0;
    int minteger000 = 0;
    int minteger0001 = 0;
    int minteger0002 = 0;
    int minteger0003 = 0;
    int minteger0004 = 0;
    int minteger0005 = 0;
    int minteger0006 = 0;
    int minteger0007 = 0;
    int minteger0008 = 0;
    int minteger0009 = 0;
    int minteger0000 = 0;
    int minteger00001 = 0;
    int minteger00002 = 0;
    int minteger00003 = 0;
    int minteger00004 = 0;
    int minteger00005 = 0;
    int minteger00006 = 0;
    int minteger00000 = 0;
    int minteger000001 = 0;
    int minteger000002 = 0;
    int minteger000003 = 0;
    int minteger000004 = 0;
    int minteger000005 = 0;

    Button buttonvalider;
    Button decrement;
    Button decrement1;
    Button decrement2;
    Button decrement3;
    Button decrement0;
    Button decrement01;
    Button decrement02;
    Button decrement03;
    Button decrement04;
    Button decrement05;
    Button decrement00;
    Button decrement003;
    Button decrement002;
    Button decrement001;
    Button decrement000;
    Button decrement0001;
    Button decrement0002;
    Button decrement0003;
    Button decrement0004;
    Button decrement0005;
    Button decrement0006;
    Button decrement0007;
    Button decrement0008;
    Button decrement0009;
    Button decrement0000;
    Button decrement00001;
    Button decrement00002;
    Button decrement00003;
    Button decrement00004;
    Button decrement00005;
    Button decrement00006;
    Button decrement00000;
    Button decrement000001;
    Button decrement000002;
    Button decrement000003;
    Button decrement000004;
    Button decrement000005;


    Button increment;
    Button increment1;
    Button increment2;
    Button increment3;
    Button increment0;
    Button increment01;
    Button increment02;
    Button increment03;
    Button increment04;
    Button increment05;
    Button increment00;
    Button increment003;
    Button increment002;
    Button increment001;
    Button increment000;
    Button increment0001;
    Button increment0002;
    Button increment0003;
    Button increment0004;
    Button increment0005;
    Button increment0006;
    Button increment0007;
    Button increment0008;
    Button increment0009;
    Button increment0000;
    Button increment00001;
    Button increment00002;
    Button increment00003;
    Button increment00004;
    Button increment00005;
    Button increment00006;
    Button increment00000;
    Button increment000001;
    Button increment000002;
    Button increment000003;
    Button increment000004;
    Button increment000005;

    boolean clicked=false;

    public static int id1;
    public static TextView tagrfid1;
    public static TextView packnum;
    public static TextView ordref;
    public static TextView color, model;
    public static TextView size;
    public static TextView qt;
    public static TextView totale;

    public static String result4;
    public static String result5;
    public static String date, dateStr;
    public static String currentDate, currentTime;

    public static double statistics;
    TextInputEditText qtepiecedef;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        /*if (ServiceHerecont.ServiceIsRun) {
            //register the services to run in background
            Intent intent = new Intent(this, ServiceHerecont.class);
            // start the services
            startService(intent);
        }*/
        if (ServiceUpdateHereC.ServiceIsRun) {
            //register the services to run in background
            Intent intent = new Intent(this, ServiceUpdateHereC.class);
            // start the services
            startService(intent);
        }

        buttonvalider = (Button) findViewById(R.id.buttonvalider);
        decrement = (Button) findViewById(R.id.decrement);
        decrement1 = (Button) findViewById(R.id.decrement1);
        decrement2 = (Button) findViewById(R.id.decrement2);
        decrement3 = (Button) findViewById(R.id.decrement3);
        decrement3 = (Button) findViewById(R.id.decrement3);
        decrement0 = (Button) findViewById(R.id.decrement0);
        decrement01 = (Button) findViewById(R.id.decrement01);
        decrement02 = (Button) findViewById(R.id.decrement02);
        decrement03 = (Button) findViewById(R.id.decrement03);
        decrement04 = (Button) findViewById(R.id.decrement04);
        decrement05 = (Button) findViewById(R.id.decrement05);
        decrement00 = (Button) findViewById(R.id.decrement00);
        decrement001 = (Button) findViewById(R.id.decrement001);
        decrement002 = (Button) findViewById(R.id.decrement002);
        decrement003 = (Button) findViewById(R.id.decrement003);
        decrement000 = (Button) findViewById(R.id.decrement000);
        decrement0001 = (Button) findViewById(R.id.decrement0001);
        decrement0002 = (Button) findViewById(R.id.decrement0002);
        decrement0003 = (Button) findViewById(R.id.decrement0003);
        decrement0004 = (Button) findViewById(R.id.decrement0004);
        decrement0005 = (Button) findViewById(R.id.decrement0005);
        decrement0006 = (Button) findViewById(R.id.decrement0006);
        decrement0007 = (Button) findViewById(R.id.decrement0007);
        decrement0008 = (Button) findViewById(R.id.decrement0008);
        decrement0009 = (Button) findViewById(R.id.decrement0009);
        decrement0000 = (Button) findViewById(R.id.decrement0000);
        decrement00001 = (Button) findViewById(R.id.decrement00001);
        decrement00002 = (Button) findViewById(R.id.decrement00002);
        decrement00003 = (Button) findViewById(R.id.decrement00003);
        decrement00004 = (Button) findViewById(R.id.decrement00004);
        decrement00005 = (Button) findViewById(R.id.decrement00005);
        decrement00006 = (Button) findViewById(R.id.decrement00006);
        decrement00000 = (Button) findViewById(R.id.decrement00000);
        decrement000001 = (Button) findViewById(R.id.decrement000001);
        decrement000002 = (Button) findViewById(R.id.decrement000002);
        decrement000003 = (Button) findViewById(R.id.decrement000003);
        decrement000004 = (Button) findViewById(R.id.decrement000004);
        decrement000005 = (Button) findViewById(R.id.decrement000005);

        increment = (Button) findViewById(R.id.increment);
        increment1 = (Button) findViewById(R.id.increment1);
        increment2 = (Button) findViewById(R.id.increment2);
        increment3 = (Button) findViewById(R.id.increment3);
        increment0 = (Button) findViewById(R.id.increment0);
        increment01 = (Button) findViewById(R.id.increment01);
        increment02 = (Button) findViewById(R.id.increment02);
        increment03 = (Button) findViewById(R.id.increment03);
        increment04 = (Button) findViewById(R.id.increment04);
        increment05 = (Button) findViewById(R.id.increment05);
        increment00 = (Button) findViewById(R.id.increment00);
        increment001 = (Button) findViewById(R.id.increment001);
        increment002 = (Button) findViewById(R.id.increment002);
        increment003 = (Button) findViewById(R.id.increment003);
        increment000 = (Button) findViewById(R.id.increment000);
        increment0001 = (Button) findViewById(R.id.increment0001);
        increment0002 = (Button) findViewById(R.id.increment0002);
        increment0003 = (Button) findViewById(R.id.increment0003);
        increment0004 = (Button) findViewById(R.id.increment0004);
        increment0005 = (Button) findViewById(R.id.increment0005);
        increment0006 = (Button) findViewById(R.id.increment0006);
        increment0007 = (Button) findViewById(R.id.increment0007);
        increment0008 = (Button) findViewById(R.id.increment0008);
        increment0009 = (Button) findViewById(R.id.increment0009);
        increment0000 = (Button) findViewById(R.id.increment0000);
        increment00001 = (Button) findViewById(R.id.increment00001);
        increment00002 = (Button) findViewById(R.id.increment00002);
        increment00003 = (Button) findViewById(R.id.increment00003);
        increment00004 = (Button) findViewById(R.id.increment00004);
        increment00005 = (Button) findViewById(R.id.increment00005);
        increment00006 = (Button) findViewById(R.id.increment00006);
        increment00000 = (Button) findViewById(R.id.increment00000);
        increment000001 = (Button) findViewById(R.id.increment000001);
        increment000002 = (Button) findViewById(R.id.increment000002);
        increment000003 = (Button) findViewById(R.id.increment000003);
        increment000004 = (Button) findViewById(R.id.increment000004);
        increment000005 = (Button) findViewById(R.id.increment000005);

        TextView display1 = (TextView) findViewById(R.id.display1);
        TextView display2 = (TextView) findViewById(R.id.display2);
        TextView display3 = (TextView) findViewById(R.id.display3);
        TextView display = (TextView) findViewById(R.id.display);
        TextView display0 = (TextView) findViewById(R.id.display0);
        TextView display01 = (TextView) findViewById(R.id.display01);
        TextView display02 = (TextView) findViewById(R.id.display02);
        TextView display03 = (TextView) findViewById(R.id.display03);
        TextView display04 = (TextView) findViewById(R.id.display04);
        TextView display05 = (TextView) findViewById(R.id.display05);
        TextView display00 = (TextView) findViewById(R.id.display00);
        TextView display001 = (TextView) findViewById(R.id.display001);
        TextView display002 = (TextView) findViewById(R.id.display002);
        TextView display003 = (TextView) findViewById(R.id.display003);
        TextView display000 = (TextView) findViewById(R.id.display000);
        TextView display0001 = (TextView) findViewById(R.id.display0001);
        TextView display0002 = (TextView) findViewById(R.id.display0002);
        TextView display0003 = (TextView) findViewById(R.id.display0003);
        TextView display0004 = (TextView) findViewById(R.id.display0004);
        TextView display0005 = (TextView) findViewById(R.id.display0005);
        TextView display0006 = (TextView) findViewById(R.id.display0006);
        TextView display0007 = (TextView) findViewById(R.id.display0007);
        TextView display0008 = (TextView) findViewById(R.id.display0008);
        TextView display0009 = (TextView) findViewById(R.id.display0009);
        TextView display0000 = (TextView) findViewById(R.id.display0000);
        TextView display00001 = (TextView) findViewById(R.id.display00001);
        TextView display00002 = (TextView) findViewById(R.id.display00002);
        TextView display00003 = (TextView) findViewById(R.id.display00003);
        TextView display00004 = (TextView) findViewById(R.id.display00004);
        TextView display00005 = (TextView) findViewById(R.id.display00005);
        TextView display00006 = (TextView) findViewById(R.id.display00006);
        TextView display00000 = (TextView) findViewById(R.id.display00000);
        TextView display000001 = (TextView) findViewById(R.id.display000001);
        TextView display000002 = (TextView) findViewById(R.id.display000002);
        TextView display000003 = (TextView) findViewById(R.id.display000003);
        TextView display000004 = (TextView) findViewById(R.id.display000004);
        TextView display000005 = (TextView) findViewById(R.id.display000005);

        tagrfid1 = (TextView) findViewById(R.id.tagrfid1);
        packnum = (TextView) findViewById(R.id.packnum);
        ordref = (TextView) findViewById(R.id.ordref);
        color = (TextView) findViewById(R.id.color);
        model = (TextView) findViewById(R.id.model);
        size = (TextView) findViewById(R.id.size);
        qt = (TextView) findViewById(R.id.qt);
        totale = (TextView) findViewById(R.id.totale);
        qtepiecedef = (TextInputEditText) findViewById(R.id.qtepiecedef);
        groupe=(Spinner) findViewById(R.id.groupe);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = controller.ip2+"/packet/prod_lines";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    // creating a new json object and
                    // getting each object from our json array.
                    try {
                        // we are getting each json object.
                        JSONObject responseObj = response.getJSONObject(i);

                        String countryName = responseObj.optString("prod_line");
                        if(!groupList.contains(countryName))
                        {
                            groupList.add(countryName);
                        }
                        groupAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.rowof, groupList);
                        groupAdapter.setDropDownViewResource(R.layout.rowof);
                        groupe.setAdapter(groupAdapter);

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
        groupe.setOnItemSelectedListener(this);



        buttonvalider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!String.valueOf(qtepiecedef.getText()).equals("")) {
                    currentDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
                    currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("LAST_SMOKE_DATE", new Date().toString());
                    editor.apply();

                    dateStr = preferences.getString("LAST_SMOKE_DATE", "");

                    String[] resultArray = dateStr.split(" ");
                    // time
                    tend = resultArray[3];

                    Workbook wb = new HSSFWorkbook();
                    Cell cell = null;
                    CellStyle cellStyle = wb.createCellStyle();
                    cellStyle.setFillForegroundColor(HSSFColor.AQUA.index);
                    cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

                    //Now we are creating sheet
                    Sheet sheet = null;
                    sheet = wb.createSheet("chaine_201");
                    //Now column and row
                    Row row = sheet.createRow(0);

                    cell = row.createCell(0);
                    cell.setCellValue("CODE DEFAUT");
                    cell.setCellStyle(cellStyle);

                    cell = row.createCell(1);
                    cell.setCellValue("DEFAUT");
                    cell.setCellStyle(cellStyle);

                    cell = row.createCell(2);
                    cell.setCellValue("QTE");
                    cell.setCellStyle(cellStyle);

                    sheet.setColumnWidth(0, (10 * 200));
                    sheet.setColumnWidth(1, (10 * 200));
                    sheet.setColumnWidth(2, (10 * 200));


                    Row row1 = sheet.createRow(1);

                    cell = row1.createCell(0);
                    cell.setCellValue("FM01");

                    cell = row1.createCell(1);
                    cell.setCellValue("conformité coloris");

                    cell = row1.createCell(2);
                    cell.setCellValue(display.getText().toString());

                    Row row2 = sheet.createRow(2);

                    cell = row2.createCell(0);
                    cell.setCellValue("FM02");

                    cell = row2.createCell(1);
                    cell.setCellValue("conformité coupe");

                    cell = row2.createCell(2);
                    cell.setCellValue(display1.getText().toString());

                    Row row3 = sheet.createRow(3);

                    cell = row3.createCell(0);
                    cell.setCellValue("FM03");

                    cell = row3.createCell(1);
                    cell.setCellValue("fils tirés");

                    cell = row2.createCell(2);
                    cell.setCellValue(display2.getText().toString());

                    Row row4 = sheet.createRow(4);

                    cell = row4.createCell(0);
                    cell.setCellValue("FM04");

                    cell = row4.createCell(1);
                    cell.setCellValue("");

                    cell = row4.createCell(2);
                    cell.setCellValue(display3.getText().toString());

                    Row row5 = sheet.createRow(5);

                    cell = row5.createCell(0);
                    cell.setCellValue("BO01");

                    cell = row5.createCell(1);
                    cell.setCellValue("aspect");

                    cell = row5.createCell(2);
                    cell.setCellValue(display0.getText().toString());

                    Row row6 = sheet.createRow(6);

                    cell = row6.createCell(0);
                    cell.setCellValue("BO02");

                    cell = row6.createCell(1);
                    cell.setCellValue("Symétrie");

                    cell = row6.createCell(2);
                    cell.setCellValue(display01.getText().toString());

                    Row row7 = sheet.createRow(7);

                    cell = row7.createCell(0);
                    cell.setCellValue("BO03");

                    cell = row7.createCell(1);
                    cell.setCellValue("valeur couture");

                    cell = row6.createCell(2);
                    cell.setCellValue(display02.getText().toString());

                    Row row8 = sheet.createRow(8);

                    cell = row8.createCell(0);
                    cell.setCellValue("BO04");

                    cell = row8.createCell(1);
                    cell.setCellValue("décalage");

                    cell = row8.createCell(2);
                    cell.setCellValue(display03.getText().toString());

                    Row row9 = sheet.createRow(9);

                    cell = row9.createCell(0);
                    cell.setCellValue("BO05");

                    cell = row9.createCell(1);
                    cell.setCellValue("points (nbre , réglage, sauté)");

                    cell = row9.createCell(2);
                    cell.setCellValue(display04.getText().toString());

                    Row row10 = sheet.createRow(10);

                    cell = row10.createCell(0);
                    cell.setCellValue("BO06");

                    cell = row10.createCell(1);
                    cell.setCellValue("mesure");

                    cell = row10.createCell(2);
                    cell.setCellValue(display05.getText().toString());

                    Row row11 = sheet.createRow(11);

                    cell = row11.createCell(0);
                    cell.setCellValue("BA01");

                    cell = row11.createCell(1);
                    cell.setCellValue("aspect");

                    cell = row11.createCell(2);
                    cell.setCellValue(display00.getText().toString());


                    Row row12 = sheet.createRow(12);

                    cell = row12.createCell(0);
                    cell.setCellValue("BA02");

                    cell = row12.createCell(1);
                    cell.setCellValue("mesure");

                    cell = row12.createCell(2);
                    cell.setCellValue(display001.getText().toString());


                    Row row13 = sheet.createRow(13);

                    cell = row13.createCell(0);
                    cell.setCellValue("BA03");

                    cell = row13.createCell(1);
                    cell.setCellValue("élasticité");

                    cell = row13.createCell(2);
                    cell.setCellValue(display002.getText().toString());


                    Row row14 = sheet.createRow(14);

                    cell = row14.createCell(0);
                    cell.setCellValue("BA04");

                    cell = row14.createCell(1);
                    cell.setCellValue("points (nbre , réglage, sauté)");

                    cell = row14.createCell(2);
                    cell.setCellValue(display003.getText().toString());


                    Row row15 = sheet.createRow(15);

                    cell = row15.createCell(0);
                    cell.setCellValue("M01");

                    cell = row15.createCell(1);
                    cell.setCellValue("valeur couture");

                    cell = row15.createCell(2);
                    cell.setCellValue(display000.getText().toString());

                    Row row16 = sheet.createRow(16);

                    cell = row16.createCell(0);
                    cell.setCellValue("M02");

                    cell = row16.createCell(1);
                    cell.setCellValue("symètrie");

                    cell = row16.createCell(2);
                    cell.setCellValue(display0001.getText().toString());

                    Row row17 = sheet.createRow(17);

                    cell = row17.createCell(0);
                    cell.setCellValue("M03");

                    cell = row17.createCell(1);
                    cell.setCellValue("points (nbre , réglage, sauté)");

                    cell = row17.createCell(2);
                    cell.setCellValue(display0002.getText().toString());

                    Row row18 = sheet.createRow(18);

                    cell = row18.createCell(0);
                    cell.setCellValue("M04");

                    cell = row18.createCell(1);
                    cell.setCellValue("échappée");

                    cell = row18.createCell(2);
                    cell.setCellValue(display0003.getText().toString());

                    Row row19 = sheet.createRow(19);

                    cell = row19.createCell(0);
                    cell.setCellValue("M05");

                    cell = row19.createCell(1);
                    cell.setCellValue("battement");

                    cell = row19.createCell(2);
                    cell.setCellValue(display0004.getText().toString());


                    Row row20 = sheet.createRow(20);

                    cell = row20.createCell(0);
                    cell.setCellValue("M06");

                    cell = row20.createCell(1);
                    cell.setCellValue("embu(fronce)");

                    cell = row20.createCell(2);
                    cell.setCellValue(display0005.getText().toString());

                    Row row21 = sheet.createRow(21);

                    cell = row21.createCell(0);
                    cell.setCellValue("M07");

                    cell = row21.createCell(1);
                    cell.setCellValue("alignement");

                    cell = row21.createCell(2);
                    cell.setCellValue(display0006.getText().toString());


                    Row row22 = sheet.createRow(22);

                    cell = row22.createCell(0);
                    cell.setCellValue("M08");

                    cell = row22.createCell(1);
                    cell.setCellValue("mesure");

                    cell = row22.createCell(2);
                    cell.setCellValue(display0007.getText().toString());

                    Row row23 = sheet.createRow(23);

                    cell = row23.createCell(0);
                    cell.setCellValue("");

                    cell = row23.createCell(1);
                    cell.setCellValue("aspect cuisse");

                    cell = row23.createCell(2);
                    cell.setCellValue(display0008.getText().toString());


                    Row row24 = sheet.createRow(24);

                    cell = row24.createCell(0);
                    cell.setCellValue("M09");

                    cell = row24.createCell(1);
                    cell.setCellValue("pli");

                    cell = row24.createCell(2);
                    cell.setCellValue(display0009.getText().toString());

                    Row row25 = sheet.createRow(25);

                    cell = row25.createCell(0);
                    cell.setCellValue("BR01");

                    cell = row25.createCell(1);
                    cell.setCellValue("longueur");

                    cell = row25.createCell(2);
                    cell.setCellValue(display0000.getText().toString());

                    Row row26 = sheet.createRow(26);

                    cell = row26.createCell(0);
                    cell.setCellValue("BR02");

                    cell = row26.createCell(1);
                    cell.setCellValue("point d'arret");

                    cell = row26.createCell(2);
                    cell.setCellValue(display00001.getText().toString());

                    Row row27 = sheet.createRow(27);

                    cell = row27.createCell(0);
                    cell.setCellValue("BR03");

                    cell = row27.createCell(1);
                    cell.setCellValue("conformité");

                    cell = row27.createCell(2);
                    cell.setCellValue(display00002.getText().toString());

                    Row row28 = sheet.createRow(28);

                    cell = row28.createCell(0);
                    cell.setCellValue("BR04");

                    cell = row28.createCell(1);
                    cell.setCellValue("position");

                    cell = row28.createCell(2);
                    cell.setCellValue(display00003.getText().toString());


                    Row row29 = sheet.createRow(29);

                    cell = row29.createCell(0);
                    cell.setCellValue("BR05");

                    cell = row29.createCell(1);
                    cell.setCellValue("aspect");

                    cell = row29.createCell(2);
                    cell.setCellValue(display00004.getText().toString());

                    Row row30 = sheet.createRow(30);

                    cell = row30.createCell(0);
                    cell.setCellValue("BR06");

                    cell = row30.createCell(1);
                    cell.setCellValue("accessoires bretelles");

                    cell = row30.createCell(2);
                    cell.setCellValue(display00005.getText().toString());


                    Row row31 = sheet.createRow(31);

                    cell = row31.createCell(0);
                    cell.setCellValue("BR07");

                    cell = row31.createCell(1);
                    cell.setCellValue("symètrie");

                    cell = row31.createCell(2);
                    cell.setCellValue(display00006.getText().toString());

                    Row row32 = sheet.createRow(32);

                    cell = row32.createCell(0);
                    cell.setCellValue("FI01");

                    cell = row32.createCell(1);
                    cell.setCellValue("fils et fils tirés");

                    cell = row32.createCell(2);
                    cell.setCellValue(display00000.getText().toString());

                    Row row33 = sheet.createRow(33);

                    cell = row33.createCell(0);
                    cell.setCellValue("FI02");

                    cell = row33.createCell(1);
                    cell.setCellValue("trou");

                    cell = row33.createCell(2);
                    cell.setCellValue(display000001.getText().toString());


                    Row row34 = sheet.createRow(34);

                    cell = row34.createCell(0);
                    cell.setCellValue("FI03");

                    cell = row34.createCell(1);
                    cell.setCellValue("coupe de ciseaux");

                    cell = row34.createCell(2);
                    cell.setCellValue(display000002.getText().toString());


                    Row row35 = sheet.createRow(35);

                    cell = row35.createCell(0);
                    cell.setCellValue("FI04");

                    cell = row35.createCell(1);
                    cell.setCellValue("vignette :inclinaison , conformité % matière, mélange taille");

                    cell = row35.createCell(2);
                    cell.setCellValue(display000003.getText().toString());


                    Row row36 = sheet.createRow(36);

                    cell = row36.createCell(0);
                    cell.setCellValue("FI05");

                    cell = row36.createCell(1);
                    cell.setCellValue("point d'arrêt");

                    cell = row36.createCell(2);
                    cell.setCellValue(display000004.getText().toString());


                    Row row37 = sheet.createRow(37);

                    cell = row37.createCell(0);
                    cell.setCellValue("FI06");

                    cell = row37.createCell(1);
                    cell.setCellValue("tache/tissu abimé après détachage");

                    cell = row37.createCell(2);
                    cell.setCellValue(display000005.getText().toString());

                    Row row38 = sheet.createRow(38);

                    cell = row38.createCell(1);
                    cell.setCellValue("Total defectueux");

                    cell = row38.createCell(2);
                    cell.setCellValue(String.valueOf(totalqte2));

                    File file = new File(getExternalFilesDir(null), "controle.xls");
                    FileOutputStream outputStream = null;

                    try {
                        outputStream = new FileOutputStream(file);
                        wb.write(outputStream);
//                    Toast.makeText(getApplicationContext(),"OK",Toast.LENGTH_LONG).show();
                    } catch (java.io.IOException e) {
                        e.printStackTrace();

//                    Toast.makeText(getApplicationContext(),"NO OK",Toast.LENGTH_LONG).show();
                        try {
                            outputStream.close();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                    String url = controller.ip2+"/packet/control";

                    JSONObject postObject = new JSONObject();
                    RequestQueue queue =  Volley.newRequestQueue(getApplicationContext());

                    try {
                        postObject.put("all_qte",display.getText().toString() + "," + display1.getText().toString() + "," + display2.getText().toString() + "," + display3.getText().toString() + "," + display0.getText().toString() + "," + display01.getText().toString() + "," + display02.getText().toString() + "," + display03.getText().toString() + "," + display04.getText().toString() + "," + display05.getText().toString() + "," + display00.getText().toString() + "," + display001.getText().toString() + "," + display002.getText().toString() + "," + display003.getText().toString() + "," + display000.getText().toString() + "," + display0001.getText().toString() + "," + display0002.getText().toString() + "," + display0003.getText().toString() + "," + display0004.getText().toString() + "," + display0005.getText().toString() + "," + display0006.getText().toString() + "," + display0007.getText().toString() + "," + display0008.getText().toString() + "," + display0009.getText().toString() + "," + display0000.getText().toString() + "," + display00001.getText().toString() + "," + display00002.getText().toString() + "," + display00003.getText().toString() + "," + display00004.getText().toString() + "," + display00005.getText().toString() + "," + display00006.getText().toString() + "," + display00000.getText().toString() + "," + display000001.getText().toString() + "," + display000002.getText().toString() + "," + display000003.getText().toString() + "," + display000004.getText().toString() + "," + display000005.getText().toString() + "," + String.valueOf(totalqte2));
                        postObject.put("codedefaut", "FM01,FM02,FM03,FM04,BO01,BO02,BO03,BO04,BO05,BO06,BA01,BA02,BA03,BA04,M01,M02,M03,M04,M05,M06,M07,M08,M08plus,M09,BR01,BR02,BR03,BR04,BR05,BR06,BR07,FI01,FI02,FI03,FI04,FI05,FI06,defects_num");
                        postObject.put("tag", tagrfid1.getText().toString());
                        postObject.put("pack_num", ServiceUpdateHereC.pipnum);
                        postObject.put("id", ServiceUpdateHereC.id);
                        postObject.put("cur_day", currentDate);
                        postObject.put("cur_time", currentTime);
                        postObject.put("prod_line", ServiceUpdateHereC.groupe);
                        postObject.put("qte", ServiceUpdateHereC.qte);
                        postObject.put("qte_fp", String.valueOf(qtepiecedef.getText()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //

                    Log.e("LoginActivityJsonObject",""+postObject);
                    JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.POST, url,postObject,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Log.e("LoginActivity","OnResponse: "+response);
                                    Toast.makeText(getApplicationContext(), String.valueOf(response), Toast.LENGTH_LONG).show();
                                    display.setText("0");
                                    display1.setText("0");
                                    display2.setText("0");
                                    display3.setText("0");
                                    display0.setText("0");
                                    display01.setText("0");
                                    display02.setText("0");
                                    display03.setText("0");
                                    display04.setText("0");
                                    display05.setText("0");
                                    display00.setText("0");
                                    display001.setText("0");
                                    display002.setText("0");
                                    display003.setText("0");
                                    display000.setText("0");
                                    display0001.setText("0");
                                    display0002.setText("0");
                                    display0003.setText("0");
                                    display0004.setText("0");
                                    display0005.setText("0");
                                    display0006.setText("0");
                                    display0007.setText("0");
                                    display0008.setText("0");
                                    display0009.setText("0");
                                    display0000.setText("0");
                                    display00001.setText("0");
                                    display00002.setText("0");
                                    display00003.setText("0");
                                    display00004.setText("0");
                                    display00005.setText("0");
                                    display00006.setText("0");
                                    display00000.setText("0");
                                    display000001.setText("0");
                                    display000002.setText("0");
                                    display000003.setText("0");
                                    display000004.setText("0");
                                    display000005.setText("0");
                                    minteger = 0;
                                    minteger1 = 0;
                                    minteger2 = 0;
                                    minteger3 = 0;
                                    minteger0 = 0;
                                    minteger01 = 0;
                                    minteger02 = 0;
                                    minteger03 = 0;
                                    minteger04 = 0;
                                    minteger05 = 0;
                                    minteger00 = 0;
                                    minteger001 = 0;
                                    minteger002 = 0;
                                    minteger003 = 0;
                                    minteger000 = 0;
                                    minteger0001 = 0;
                                    minteger0002 = 0;
                                    minteger0003 = 0;
                                    minteger0004 = 0;
                                    minteger0005 = 0;
                                    minteger0006 = 0;
                                    minteger0007 = 0;
                                    minteger0008 = 0;
                                    minteger0009 = 0;
                                    minteger0000 = 0;
                                    minteger00001 = 0;
                                    minteger00002 = 0;
                                    minteger00003 = 0;
                                    minteger00004 = 0;
                                    minteger00005 = 0;
                                    minteger00006 = 0;
                                    minteger00000 = 0;
                                    minteger000001 = 0;
                                    minteger000002 = 0;
                                    minteger000003 = 0;
                                    minteger000004 = 0;
                                    minteger000005 = 0;
                                    totale.setText("0");
                                    totalqte2 = 0;
                                    qtepiecedef.setText("");
                                    tagrfid1.setText("");
                                    packnum.setText("");
                                    ordref.setText("");
                                    color.setText("");
                                    size.setText("");
                                    qt.setText("");
                                    model.setText("");
                                    ServiceUpdateHereC.tag = "";
                                    ServiceUpdateHereC.id = "";
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("OnError", String.valueOf(error.getMessage()));
                        }
                    });

                    queue.add(objRequest);

                } else {
                    Toast.makeText(getApplicationContext(),"Veuillez remplir le nombre de pièces défaillantes",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.groupe){
            selectedGroup = adapterView.getSelectedItem().toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void increaseInteger(View view) {
        minteger = minteger + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display(minteger);

    }
    public void decreaseInteger(View view) {
        if(minteger>0){
            minteger = minteger - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display(minteger);
    }

    private void display(int number) {
        TextView display = (TextView) findViewById(
                R.id.display);
        display.setText("" + number);
    }


    public void increaseInteger1(View view) {
        minteger1 = minteger1 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display1(minteger1);

    }public void decreaseInteger1(View view) {
        if(minteger1>0){
            minteger1 = minteger1 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display1(minteger1);
    }

    private void display1(int number1) {
        TextView display1 = (TextView) findViewById(
                R.id.display1);
        display1.setText("" + number1);
    }


    public void increaseInteger2(View view) {
        minteger2 = minteger2 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display2(minteger2);

    }public void decreaseInteger2(View view) {
        if(minteger2>0){
            minteger2 = minteger2 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display2(minteger2);
    }

    private void display2(int number2) {
        TextView display2 = (TextView) findViewById(
                R.id.display2);
        display2.setText("" + number2);
    }



    public void increaseInteger3(View view) {
        minteger3 = minteger3 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display3(minteger3);

    }public void decreaseInteger3(View view) {
        if(minteger3>0){
            minteger3 = minteger3 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display3(minteger3);
    }

    private void display3(int number3) {
        TextView display3 = (TextView) findViewById(
                R.id.display3);
        display3.setText("" + number3);
    }

    public void increaseInteger0(View view) {
        minteger0 = minteger0 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display0(minteger0);

    }public void decreaseInteger0(View view) {
        if(minteger0>0){
            minteger0 = minteger0 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display0(minteger0);
    }

    private void display0(int number0) {
        TextView display0 = (TextView) findViewById(
                R.id.display0);
        display0.setText("" + number0);
    }


    public void increaseInteger01(View view) {
        minteger01 = minteger01 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display01(minteger01);

    }public void decreaseInteger01(View view) {
        if(minteger01>0){
            minteger01 = minteger01 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display01(minteger01);
    }

    private void display01(int number01) {
        TextView display01 = (TextView) findViewById(
                R.id.display01);
        display01.setText("" + number01);
    }

    public void increaseInteger02(View view) {
        minteger02 = minteger02 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display02(minteger02);

    }public void decreaseInteger02(View view) {
        if(minteger02>0){
            minteger02 = minteger02 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display02(minteger02);
    }

    private void display02(int number02) {
        TextView display02 = (TextView) findViewById(
                R.id.display02);
        display02.setText("" + number02);
    }

    public void increaseInteger03(View view) {
        minteger03 = minteger03 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display03(minteger03);

    }public void decreaseInteger03(View view) {
        if(minteger03>0){
            minteger03 = minteger03 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display03(minteger03);
    }

    private void display03(int number03) {
        TextView display03 = (TextView) findViewById(
                R.id.display03);
        display03.setText("" + number03);
    }

    public void increaseInteger04(View view) {
        minteger04 = minteger04 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display04(minteger04);

    }public void decreaseInteger04(View view) {
        if(minteger04>0){
            minteger04 = minteger04 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display04(minteger04);
    }

    private void display04(int number04) {
        TextView display04 = (TextView) findViewById(
                R.id.display04);
        display04.setText("" + number04);
    }


    public void increaseInteger05(View view) {
        minteger05 = minteger05 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display05(minteger05);

    }public void decreaseInteger05(View view) {
        if(minteger05>0){
            minteger05 = minteger05 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display05(minteger05);
    }

    private void display05(int number05) {
        TextView display05 = (TextView) findViewById(
                R.id.display05);
        display05.setText("" + number05);
    }

    public void increaseInteger00(View view) {
        minteger00 = minteger00 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display00(minteger00);

    }public void decreaseInteger00(View view) {
        if(minteger00>0){
            minteger00 = minteger00 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display00(minteger00);
    }

    private void display00(int number00) {
        TextView display00 = (TextView) findViewById(
                R.id.display00);
        display00.setText("" + number00);
    }

    public void increaseInteger001(View view) {
        minteger001 = minteger001 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display001(minteger001);

    }public void decreaseInteger001(View view) {
        if(minteger001>0){
            minteger001 = minteger001 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display001(minteger001);
    }

    private void display001(int number001) {
        TextView display001 = (TextView) findViewById(
                R.id.display001);
        display001.setText("" + number001);
    }


    public void increaseInteger002(View view) {
        minteger002 = minteger002 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display002(minteger002);

    }public void decreaseInteger002(View view) {
        if(minteger002>0){
            minteger002 = minteger002 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display002(minteger002);
    }

    private void display002(int number002) {
        TextView display002 = (TextView) findViewById(
                R.id.display002);
        display002.setText("" + number002);
    }

    public void increaseInteger003(View view) {
        minteger003 = minteger003 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display003(minteger003);

    }public void decreaseInteger003(View view) {
        if(minteger003>0){
            minteger003 = minteger003 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display003(minteger003);
    }

    private void display003(int number003) {
        TextView display003 = (TextView) findViewById(
                R.id.display003);
        display003.setText("" + number003);
    }

    public void increaseInteger000(View view) {
        minteger000 = minteger000 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display000(minteger000);

    }public void decreaseInteger000(View view) {
        if(minteger000>0){
            minteger000 = minteger000 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display000(minteger000);
    }

    private void display000(int number000) {
        TextView display000 = (TextView) findViewById(
                R.id.display000);
        display000.setText("" + number000);
    }

    public void increaseInteger0001(View view) {
        minteger0001 = minteger000 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display0001(minteger0001);

    }public void decreaseInteger0001(View view) {
        if(minteger0001>0){
            minteger0001 = minteger0001 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display0001(minteger0001);
    }

    private void display0001(int number0001) {
        TextView display0001 = (TextView) findViewById(
                R.id.display0001);
        display0001.setText("" + number0001);
    }

    public void increaseInteger0002(View view) {
        minteger0002 = minteger0002 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display0002(minteger0002);

    }public void decreaseInteger0002(View view) {
        if(minteger0002>0){
            minteger0002 = minteger0002 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display0002(minteger0002);
    }

    private void display0002(int number0002) {
        TextView display0002 = (TextView) findViewById(
                R.id.display0002);
        display0002.setText("" + number0002);
    }


    public void increaseInteger0003(View view) {
        minteger0003 = minteger0003 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display0003(minteger0003);

    }public void decreaseInteger0003(View view) {
        if(minteger0003>0){
            minteger0003 = minteger0003 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display0003(minteger0003);
    }

    private void display0003(int number0003) {
        TextView display0003 = (TextView) findViewById(
                R.id.display0003);
        display0003.setText("" + number0003);
    }

    public void increaseInteger0004(View view) {
        minteger0004 = minteger0004 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display0004(minteger0004);

    }public void decreaseInteger0004(View view) {
        if(minteger0004>0){
            minteger0004 = minteger0004 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display0004(minteger0004);
    }

    private void display0004(int number0004) {
        TextView display0004 = (TextView) findViewById(
                R.id.display0004);
        display0004.setText("" + number0004);
    }

    public void increaseInteger0005(View view) {
        minteger0005 = minteger0005 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display0005(minteger0005);

    }public void decreaseInteger0005(View view) {
        if(minteger0005>0){
            minteger0005 = minteger0005 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display0005(minteger0005);
    }

    private void display0005(int number0005) {
        TextView display0005 = (TextView) findViewById(
                R.id.display0005);
        display0005.setText("" + number0005);
    }

    public void increaseInteger0006(View view) {
        minteger0006 = minteger0006 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display0006(minteger0006);

    }public void decreaseInteger0006(View view) {
        if(minteger0006>0){
            minteger0006 = minteger0006 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display0006(minteger0006);
    }

    private void display0006(int number0006) {
        TextView display0006 = (TextView) findViewById(
                R.id.display0006);
        display0006.setText("" + number0006);
    }

    public void increaseInteger0007(View view) {
        minteger0007 = minteger0007 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display0007(minteger0007);

    }public void decreaseInteger0007(View view) {
        if(minteger0007>0){
            minteger0007 = minteger0007 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display0007(minteger0007);
    }

    private void display0007(int number0007) {
        TextView display0007 = (TextView) findViewById(
                R.id.display0007);
        display0007.setText("" + number0007);
    }

    public void increaseInteger0008(View view) {
        minteger0008 = minteger0008 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display0008(minteger0008);

    }public void decreaseInteger0008(View view) {
        if(minteger0008>0){
            minteger0008 = minteger0008 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display0008(minteger0008);
    }

    private void display0008(int number0008) {
        TextView display0008 = (TextView) findViewById(
                R.id.display0008);
        display0008.setText("" + number0008);
    }

    public void increaseInteger0009(View view) {
        minteger0009 = minteger0009 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display0009(minteger0009);

    }public void decreaseInteger0009(View view) {
        if(minteger0009>0){
            minteger0009 = minteger0009 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display0009(minteger0009);
    }

    private void display0009(int number0009) {
        TextView display0009 = (TextView) findViewById(
                R.id.display0009);
        display0009.setText("" + number0009);
    }

    public void increaseInteger0000(View view) {
        minteger0000 = minteger0000 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display0000(minteger0000);

    }public void decreaseInteger0000(View view) {
        if(minteger0000>0){
            minteger0000 = minteger0000 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display0000(minteger0000);
    }

    private void display0000(int number0000) {
        TextView display0000 = (TextView) findViewById(
                R.id.display0000);
        display0000.setText("" + number0000);
    }

    public void increaseInteger00001(View view) {
        minteger00001 = minteger00001 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display00001(minteger00001);

    }public void decreaseInteger00001(View view) {
        if(minteger00001>0){
            minteger00001 = minteger00001 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display00001(minteger00001);
    }

    private void display00001(int number00001) {
        TextView display00001 = (TextView) findViewById(
                R.id.display00001);
        display00001.setText("" + number00001);
    }

    public void increaseInteger00002(View view) {
        minteger00002 = minteger00002 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display00002(minteger00002);

    }public void decreaseInteger00002(View view) {
        if(minteger00002>0){
            minteger00002 = minteger00002 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display00002(minteger00002);
    }

    private void display00002(int number00002) {
        TextView display00002 = (TextView) findViewById(
                R.id.display00002);
        display00002.setText("" + number00002);
    }

    public void increaseInteger00003(View view) {
        minteger00003 = minteger00003 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display00003(minteger00003);

    }public void decreaseInteger00003(View view) {
        if(minteger00003>0){
            minteger00003 = minteger00003 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display00003(minteger00003);
    }

    private void display00003(int number00003) {
        TextView display00003 = (TextView) findViewById(
                R.id.display00003);
        display00003.setText("" + number00003);
    }

    public void increaseInteger00004(View view) {
        minteger00004 = minteger00004 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display00004(minteger00004);

    }public void decreaseInteger00004(View view) {
        if(minteger00004>0){
            minteger00004 = minteger00004 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display00004(minteger00004);
    }

    private void display00004(int number00004) {
        TextView display00004 = (TextView) findViewById(
                R.id.display00004);
        display00004.setText("" + number00004);
    }

    public void increaseInteger00005(View view) {
        minteger00005 = minteger00005 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display00005(minteger00005);

    }public void decreaseInteger00005(View view) {
        if(minteger00005>0){
            minteger00005 = minteger00005 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display00005(minteger00005);
    }

    private void display00005(int number00005) {
        TextView display00005 = (TextView) findViewById(
                R.id.display00005);
        display00005.setText("" + number00005);
    }

    public void increaseInteger00006(View view) {
        minteger00006 = minteger00006 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display00006(minteger00006);

    }public void decreaseInteger00006(View view) {
        if(minteger00006>0){
            minteger00006 = minteger00006 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display00006(minteger00006);
    }

    private void display00006(int number00006) {
        TextView display00006 = (TextView) findViewById(
                R.id.display00006);
        display00006.setText("" + number00006);
    }

    public void increaseInteger00000(View view) {
        minteger00000 = minteger00000 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display00000(minteger00000);

    }public void decreaseInteger00000(View view) {
        if(minteger00000>0){
            minteger00000 = minteger00000 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display00000(minteger00000);
    }

    private void display00000(int number00000) {
        TextView display00000 = (TextView) findViewById(
                R.id.display00000);
        display00000.setText("" + number00000);
    }

    public void increaseInteger000001(View view) {
        minteger000001 = minteger000001 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display000001(minteger000001);

    }public void decreaseInteger000001(View view) {
        if(minteger000001>0){
            minteger000001 = minteger000001 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display000001(minteger000001);
    }

    private void display000001(int number000001) {
        TextView display000001 = (TextView) findViewById(
                R.id.display000001);
        display000001.setText("" + number000001);
    }


    public void increaseInteger000002(View view) {
        minteger000002 = minteger000002 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display000002(minteger000002);

    }public void decreaseInteger000002(View view) {
        if(minteger000002>0){
            minteger000002 = minteger000002 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display000002(minteger000002);
    }

    private void display000002(int number000002) {
        TextView display000002 = (TextView) findViewById(
                R.id.display000002);
        display000002.setText("" + number000002);
    }

    public void increaseInteger000003(View view) {
        minteger000003 = minteger000003 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display000003(minteger000003);

    }public void decreaseInteger000003(View view) {
        if(minteger000003>0){
            minteger000003 = minteger000003 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display000003(minteger000003);
    }

    private void display000003(int number000003) {
        TextView display000003 = (TextView) findViewById(
                R.id.display000003);
        display000003.setText("" + number000003);
    }


    public void increaseInteger000004(View view) {
        minteger000004 = minteger000004 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display000004(minteger000004);

    }public void decreaseInteger000004(View view) {
        if(minteger000004>0){
            minteger000004 = minteger000004 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display000004(minteger000004);
    }

    private void display000004(int number000004) {
        TextView display000004 = (TextView) findViewById(
                R.id.display000004);
        display000004.setText("" + number000004);
    }

    public void increaseInteger000005(View view) {
        minteger000005 = minteger000005 + 1;
        totalqte2+=1;
        totale.setText(String.valueOf(totalqte2));
        display000005(minteger000005);

    }public void decreaseInteger000005(View view) {
        if(minteger000005>0){
            minteger000005 = minteger000005 - 1;
            totalqte2-=1;
            totale.setText(String.valueOf(totalqte2));
        }
        display000005(minteger000005);
    }

    private void display000005(int number000005) {
        TextView display000005 = (TextView) findViewById(
                R.id.display000005);
        display000005.setText("" + number000005);
    }

   /* public void validate(){

        if(clicked){

            packnum.setText("");
            ordref.setText("");
            color.setText("");
            size.setText("");
            qt.setText("");
        }

    }*/


}