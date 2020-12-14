package com.masterandroid.backgroundservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.masterandroid.backgroundservice.adapter.nearbyAdapter;
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

        //   nearbyAdapter adapter=new nearbyAdapter(nearbyPlaceList,showPlaceNearby.this);
        //   nearbyRecycler.setAdapter(adapter);
    }

    public void getDetailsFromAPI(String location, final String api_key){
        final ApiInterface apiInterface= ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseModel> call= apiInterface.getDetails(location,20,api_key);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.isSuccessful()){
                    try{

                        Log.d("showPLace success",response.body().toString());
                    }
                    catch (Exception e){
                        Log.d("showPLace err ",e.getMessage());

                    }

                }
                else{
                    Log.d("Else Response: " , response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Log.d("Response: " , t.getMessage());
            }
        });
    }
}