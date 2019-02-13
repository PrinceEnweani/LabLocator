package com.example.lablocator;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Connection connect;
    ResultSet computersRS;
    DBConfiguration dbc = new DBConfiguration();
    DBAccess db = new DBAccess();
    private Spinner labs;
    ArrayAdapter<String> labsAdapter;
    ComputerAdapter cAdapter;
    ListView computerListView;
    private String selectedLab;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateLabs();
        DBConfiguration dbc = new DBConfiguration();
        //connect = getConnection(dbc.getUserName(), dbc.getPassword() , dbc.getConnectionStr() , dbc.getDriver());
}

    public void loadData(View v) throws SQLException {
        DBAccess dba = new DBAccess();
        computersRS = dba.getComputers(selectedLab);
        ArrayList<Computer> listOfComputers = new ArrayList<Computer>();
        if (computersRS == null){
            Toast.makeText(this , "Null results" , Toast.LENGTH_LONG).show();
        }else{
            //Create new computers
            while(computersRS.next()){
               Computer c = new Computer(computersRS.getString("ip") , computersRS.getString("host") , computersRS.getBoolean("occupied"));
                listOfComputers.add(c);
                cAdapter = new ComputerAdapter(this , listOfComputers);
                computerListView = (ListView)findViewById(R.id.computerList);
                computerListView.setAdapter(cAdapter);
               // System.out.println("***************Test:" + computersRS.getString("TABLE_CATALOG") + " SCHEMA :" + computersRS.getString("TABLE_SCHEMA") + " NAME: " + computersRS.getString("TABLE_NAME") );
            }

            Toast.makeText(this , listOfComputers.get(0).getHost() , Toast.LENGTH_LONG).show();
        }


    }

    public void populateLabs(){
        labs = (Spinner)findViewById(R.id.labsDropDown);
    ArrayList<Lab> listOfLabs = new ArrayList<Lab>();
    listOfLabs.add(new Lab("1201" , "tbd"));
    listOfLabs.add(new Lab("1202" , "tbd"));
    listOfLabs.add(new Lab("1203" , "tbd"));
    listOfLabs.add(new Lab("1204" , "tbd"));
    listOfLabs.add(new Lab("1303" , "tbd"));
    listOfLabs.add(new Lab("2204" , "tbd"));
    listOfLabs.add(new Lab("2208" , "tbd"));
    listOfLabs.add(new Lab("2210" , "tbd"));
    listOfLabs.add(new Lab("2212" , "tbd"));
    listOfLabs.add(new Lab("3208" , "tbd"));
    listOfLabs.add(new Lab("3210" , "tbd"));
    listOfLabs.add(new Lab("3212" , "tbd"));
    listOfLabs.add(new Lab("3302" , "tbd"));
    listOfLabs.add(new Lab("3314" , "tbd"));

    final ArrayList<String> labNames = new ArrayList<String>();
        for (Lab l: listOfLabs) {
            labNames.add(l.getRoom());
        }
        labsAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_spinner_item, labNames);

        labsAdapter.setDropDownViewResource(R.layout.labspinner);
        labs.setAdapter(labsAdapter);

        labs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedLab = labNames.get(i);
                ((TextView)adapterView.getChildAt(0)).setTextColor(Color.parseColor("#f7f7f7"));
                ((TextView)adapterView.getChildAt(0)).setTextSize(15);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}
