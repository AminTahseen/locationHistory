package com.masterandroid.backgroundservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.api.Result;
import com.google.gson.JsonArray;
import com.masterandroid.backgroundservice.adapter.nearbyAdapter;
import com.masterandroid.backgroundservice.nearbyResponse.Example;
import com.masterandroid.backgroundservice.retrofit.ApiClient;
import com.masterandroid.backgroundservice.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class showPlaceNearby extends AppCompatActivity {
    RecyclerView nearbyRecycler;
    List<nearbyPlace> nearbyPlaceList;
    ApiClient retrofit;
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

        String mainLatlng=place_longitude.toString()+","+place_latitude.toString();
        Toast.makeText(this, mainLatlng, Toast.LENGTH_SHORT).show();
        getDetailsFromAPI(mainLatlng,"AIzaSyDazjxsJFdohTwZllHdMsacB4P9luVjqyE");


    }

    public void getDetailsFromAPI(String location, final String api_key){
        final ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<Example> call= apiInterface.getDetails(location,20,api_key);
        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if(response.isSuccessful()){
                    try
                    {
                        for(int i=0;i<response.body().getResults().size();i++)
                        {
                            String placeName=response.body().getResults().get(i).getName();
                            Double lat = response.body().getResults().get(i).getGeometry().getLocation().getLat();
                            Double lng = response.body().getResults().get(i).getGeometry().getLocation().getLng();
                            nearbyPlace nearby=new nearbyPlace(placeName,lat,lng);
                            nearbyPlaceList.add(nearby);

                        }
                        nearbyAdapter adapter=new nearbyAdapter(nearbyPlaceList,showPlaceNearby.this);
                        nearbyRecycler.setAdapter(adapter);
                    }
                    catch (Exception er)
                    {
                        Log.d("showPLace err ",er.getMessage());

                    }
                    Log.d("showPLace success",response.body().toString());

                }
                else
                    {
                        Log.d("Else Response: " , response.message());

                    }
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }
}