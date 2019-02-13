package com.example.lablocator;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ComputerAdapter extends ArrayAdapter<Computer>{

    public ComputerAdapter(Context ctx , ArrayList<Computer> computers){
        super(ctx , 0 , computers);
    }

    @Override
    public View getView(int pos , View v , ViewGroup parent){
        Computer c = getItem(pos);

        if(v == null){
          v = LayoutInflater.from(getContext()).inflate(R.layout.computerview, parent , false);

        }

        TextView ip = (TextView)v.findViewById(R.id.ip);
        TextView host = (TextView)v.findViewById(R.id.host);
        TextView occupied = (TextView)v.findViewById(R.id.occupied);
        Button   color  = (Button)v.findViewById(R.id.statusCol);

        ip.setText(c.getIp());
        host.setText(c.getHost());

        if(c.getOccupied()){
            color.setBackgroundColor(Color.RED);
            occupied.setText("Unavailable");
        }else{
            color.setBackgroundColor(Color.GREEN);
            occupied.setText("Available");
        }
        return v;
    }
}
