package com.masterandroid.backgroundservice.retrofit;

import com.masterandroid.backgroundservice.ResponseModel;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("nearbysearch/json?")
    Call<ResponseModel> getDetails(@Query("location") String Location, @Query("radius") int Radius, @Query("key")String ApiKey);

    @GET("findplacefromtext/json?")
    Call<ResponseModel> getPlaceSearch(@Query("input") String Input, @Query("inputtype")String InputType
    ,@Query("fields") String Fields, @Query("locationbias") String LocationBias, @Query("key") String Api_Key);
}
