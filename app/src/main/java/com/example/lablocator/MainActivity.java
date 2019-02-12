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
    ResultSet labRS;
    DBConfiguration dbc = new DBConfiguration();
    DBAccess db = new DBAccess();
    private Spinner labs;
    ArrayAdapter<String> labsAdapter;
    ComputerAdapter cAdapter;
    ListView computerListView;
    private String selectedLab;




    @SuppressLint("NewApi")
    private Connection getConnection(String userName , String pass ,  String DB ,  String server){

        connect = null;
        String connUrl = null;
        try{
            Class.forName(dbc.getDriver());
            connUrl = dbc.getConnectionStr();
            connect = DriverManager.getConnection(connUrl);
        }catch(SQLException se){
            se.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return connect;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        populateLabs();
        DBConfiguration dbc = new DBConfiguration();
        connect = getConnection(dbc.getUserName(), dbc.getPassword() , dbc.getConnectionStr() , dbc.getDriver());
}

    public void loadData(View v) throws SQLException {
        //cAdapter.clear();
        String query = "select * from machine_info;";
        try{
            connect = getConnection(dbc.getUserName(), dbc.getPassword() , dbc.getConnectionStr() , dbc.getDriver());
            Statement stmt = connect.createStatement();
            labRS = stmt.executeQuery(query);
            final ArrayList<Computer> computers = new ArrayList<Computer>();
            while (labRS.next()){
                Computer c = new Computer(labRS.getString("ip") , labRS.getString("host") , labRS.getBoolean("occupied"));
                computers.add(c);
            }

            cAdapter = new ComputerAdapter(this , computers);
            computerListView = (ListView)findViewById(R.id.computerList);
            computerListView.setAdapter(cAdapter);

            computerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(getApplicationContext() , computers.get(i).getHost(), Toast.LENGTH_LONG).show();
                }
            });
        }catch (SQLException e){
            e.printStackTrace();
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
