package com.masterandroid.backgroundservice.retrofit;

import com.masterandroid.backgroundservice.ResponseModel;
import com.masterandroid.backgroundservice.nearbyResponse.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("nearbysearch/json?")
    Call<Example> getDetails(@Query("location") String Location, @Query("radius") int Radius, @Query("key")String ApiKey);

    @GET("findplacefromtext/json?")
    Call<ResponseModel> getPlaceSearch(@Query("input") String Input, @Query("inputtype")String InputType
    ,@Query("fields") String Fields, @Query("locationbias") String LocationBias, @Query("key") String Api_Key);
}
