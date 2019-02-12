package com.example.lablocator;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBAccess {

    ResultSet rs;

    DBConfiguration dbc = new DBConfiguration();
    public Connection getConnection(){
        Connection connection = null;
        try{
            Class.forName(dbc.getDriver()).newInstance();
            connection = DriverManager.getConnection(dbc.getConnectionStr() , dbc.getUserName() , dbc.getPassword());
            //Statement stmt = connection.createStatement();
           // ResultSet test = stmt.executeQuery("select * from machine_info where host like '%1202%';");
        }catch (Exception e){
            e.printStackTrace();
          //  Toast.makeText(this , "Connection Failed" , Toast.LENGTH_LONG).show();
        }

        return connection;
    }

    public ResultSet getLabs(String lab){

        Connection conn = this.getConnection();

        this.rs = null;

        try{
            Statement stmt = conn.createStatement();
            String query = "select * from machine_info where host like '%" + lab + "%';";
           this.rs =  stmt.executeQuery(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        return this.rs;
    }

}
