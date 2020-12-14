package com.masterandroid.backgroundservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.masterandroid.backgroundservice.adapter.nearbyAdapter;

import java.util.ArrayList;
import java.util.List;

public class showPlaceNearby extends AppCompatActivity {
    RecyclerView nearbyRecycler;
    List<nearbyPlace> nearbyPlaceList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_place_nearby);
        nearbyPlaceList=new ArrayList<>();
        nearbyRecycler=findViewById(R.id.nearbyRecycler);
        nearbyRecycler.setLayoutManager(new LinearLayoutManager(this));
        Intent getData=getIntent();

        Double place_latitude = getData.getDoubleExtra("placeLat",0.0);
        Double place_longitude = getData.getDoubleExtra("placeLon",0.0);

        nearbyAdapter adapter=new nearbyAdapter(nearbyPlaceList,showPlaceNearby.this);
        nearbyRecycler.setAdapter(adapter);
    }
}